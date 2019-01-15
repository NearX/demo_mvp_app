package com.ybkj.demo.module.login.activity;

import android.Manifest;
import android.annotation.SuppressLint;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.ybkj.demo.R;
import com.ybkj.demo.base.BaseMvpActivity;
import com.ybkj.demo.bean.response.LoginRes;
import com.ybkj.demo.bean.response.VersionRes;
import com.ybkj.demo.manager.ActivityManager;
import com.ybkj.demo.manager.UserDataManager;
import com.ybkj.demo.module.MainActivity;
import com.ybkj.demo.module.mine.presenter.CheckVersionPresenter;
import com.ybkj.demo.module.mine.view.CheckVersionView;
import com.ybkj.demo.ui.dialog.TipDialog;
import com.ybkj.demo.ui.dialog.VersionDialog;
import com.ybkj.demo.utils.AppUpdateVersionCheckUtil;
import com.ybkj.demo.utils.RxPermissionUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;


/**
 * - @Author:  Yi Shan Xiang
 * - @Description:   启动页
 * - @Time:  2018/8/23
 * - @Emaill:  380948730@qq.com
 */
public class SplashActivity extends BaseMvpActivity<CheckVersionPresenter> implements CheckVersionView {

    private Disposable subscribe;
    private boolean timeFinish = false;
    private boolean permissionFinish = false;
    private boolean isUpdate = false;

    private boolean isLogin = false;


    @Override
    protected void injectPresenter() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initTitle() {
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {

    }

    @Override
    public boolean isImmersiveStatusBar() {
        return true;
    }

    @Override
    protected void initData() {
        setTimeCountDown(3);
        checkPermission();
        presenter.getVersion(false);

    }

    @Override
    public void onError(String errorMsg) {
        super.onError(errorMsg);
        timeFinish = true;
        goMainActivity();
    }

    /**
     * 检测必备权限
     */
    @SuppressLint("CheckResult")
    private void checkPermission() {
        new RxPermissions(this).request(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(aBoolean -> {
                            if (aBoolean) {
                                //true表示获取权限成功（android6.0以下默认为true）
                                permissionFinish = true;
                                goMainActivity();
                            } else {
                                TipDialog tipDialog = new TipDialog(mContext);
                                tipDialog.setMessageText("为保障APP的正常运行，需要进行权限授予");
                                tipDialog.setConfirmButtonText("授予权限");
                                tipDialog.setCancelButtonText("退出APP");
                                tipDialog.setOnCancelButtonClickListener((dialog, view) -> {
                                    finish();
                                });
                                tipDialog.setOnConfirmButtonClickListener((dialog, view) -> {
                                    dialog.dismiss();
                                    checkPermission();
                                });
                                tipDialog.show();
                            }
                        },
                        Throwable::printStackTrace
                );
    }


    /**
     * 跳转主页面
     * 根据token判断是否需要用户登录
     */
    private void goMainActivity() {
        if (timeFinish && permissionFinish && !isUpdate) {
            LoginRes token = UserDataManager.getLoginInfo();
            isLogin = true;
            if (token != null) {
                ActivityManager.gotoActivity(mContext, MainActivity.class);
            } else {
                ActivityManager.gotoActivity(mContext, LoginActivity.class);

            }
            finish();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxPermissionUtils.destory();
        if (subscribe != null && !subscribe.isDisposed()) {
            subscribe.dispose();
            subscribe = null;
        }
    }

    @Override
    public void LoadData(VersionRes.AppVersionBean appUpdateRes) {
        if (appUpdateRes == null) { //更新信息为空
            isUpdate = false;
            goMainActivity();
            return;
        }
        String newestVersion = appUpdateRes.getVersionNumber(); //获取平台最新版本号
        boolean canUpdate = false;
        try {
            canUpdate = AppUpdateVersionCheckUtil.compareVersion(newestVersion);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!canUpdate) {
            timeFinish = true;
            toast("当前已是最新版本");
            goMainActivity();
            return;
        }
        VersionDialog updateDialog = new VersionDialog(mContext, appUpdateRes.getVersionNumber(), appUpdateRes.getUpdateExplain());

        if (appUpdateRes.getType() == AppUpdateVersionCheckUtil.APK_VERSION_UPDATE_FORCE) {
            updateDialog.setCancelButtonText("退出应用");
        } else {
            updateDialog.setCancelButtonText("取消更新");
        }
        isUpdate = true;
        updateDialog.setOnCancelButtonClickListener((dialog, view) -> {
            if (appUpdateRes.getType() == AppUpdateVersionCheckUtil.APK_VERSION_UPDATE_FORCE) {
                ActivityManager.exit();
            } else {
                updateDialog.dismiss();
                timeFinish = true;
                isUpdate = false;
                goMainActivity();
            }
        });
        updateDialog.setOnConfirmButtonClickListener((dialog, view) -> {
            AppUpdateVersionCheckUtil.getInstance().setOnDownLoadListener(new AppUpdateVersionCheckUtil
                    .OnDownLoadListener() {
                @Override
                protected void onSuccess() {

                }

                @Override
                protected void onProgress(int progrss) {

                }

                @Override
                protected void onBegin() {
                    if (appUpdateRes.getType() == AppUpdateVersionCheckUtil.APK_VERSION_UPDATE_FORCE) {
                        toast("应用正在下载，请稍后");
                    } else {
                        updateDialog.dismiss();
                        toast("应用转入后台下载");
                        timeFinish = true;
                        isUpdate = false;
                        goMainActivity();
                    }
                }
            }).downLoadApk(mContext, appUpdateRes.getAppUrl());
        });

        updateDialog.show();
    }

    private void setTimeCountDown(final int splashTotalCountdownTime) {
        subscribe = Observable.interval(0, 1, TimeUnit.SECONDS).subscribeOn(AndroidSchedulers
                .mainThread()).map(increaseTime -> splashTotalCountdownTime
                - increaseTime.intValue()).take(splashTotalCountdownTime + 1).subscribe(integer -> {

            if (integer == 0 && isLogin == false) {
                timeFinish = true;
                goMainActivity();
            }
        });
    }
}

