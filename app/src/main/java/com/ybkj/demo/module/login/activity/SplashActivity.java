package com.ybkj.demo.module.login.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.ybkj.demo.R;
import com.ybkj.demo.base.BaseMvpActivity;
import com.ybkj.demo.bean.ProgressMessage;
import com.ybkj.demo.bean.response.LoginRes;
import com.ybkj.demo.bean.response.VersionRes;
import com.ybkj.demo.manager.ActivityManager;
import com.ybkj.demo.manager.UserDataManager;
import com.ybkj.demo.module.MainActivity;
import com.ybkj.demo.module.mine.presenter.CheckVersionPresenter;
import com.ybkj.demo.module.mine.view.CheckVersionView;
import com.ybkj.demo.service.ApkDownInstallService;
import com.ybkj.demo.ui.dialog.TipDialog;
import com.ybkj.demo.ui.dialog.UpdateProgressDialog;
import com.ybkj.demo.ui.dialog.VersionDialog;
import com.ybkj.demo.utils.AppUpdateVersionCheckUtil;
import com.ybkj.demo.utils.LogUtil;
import com.ybkj.demo.utils.RxPermissionUtils;
import com.ybkj.demo.utils.StringUtil;
import com.ybkj.demo.utils.SystemUtil;
import com.ybkj.demo.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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

    private UpdateProgressDialog progressDialog;//更新下载进度dialog
    private String newestVersion;
    private VersionDialog updateDialog;//是否更新询问弹出框

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
        EventBus.getDefault().register(this);
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
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
        goMianActivity();
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
                                goMianActivity();
                            } else {
                                TipDialog tipDialog = new TipDialog(mContext);
                                tipDialog.setTittleText("为保障APP的正常运行，需要进行权限授予");
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
    private void goMianActivity() {
        if (timeFinish && permissionFinish && !isUpdate) {
            LoginRes token = UserDataManager.getLoginInfo();
            isLogin = true;
//            if (token != null) {
            ActivityManager.gotoActivity(mContext, MainActivity.class);
//            } else {
//                ActivityManager.gotoActivity(mContext, LoginActivity.class);
//
//            }
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
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void loadData(VersionRes.AppVersionBean appUpdateRes) {
        if (appUpdateRes == null) {
            isUpdate = false;
            goMianActivity();
            return;
        }
        String oldVersion = SystemUtil.getAppVersionName(mContext);
        newestVersion = appUpdateRes.getVersionNumber();
        //最新强制更新版本号
        String forceUpdateVersion = appUpdateRes.getForceUpdatingVersionNumber();
        boolean canUpdate = false;
        boolean canForceUpdate = false;//是否低于最新的强制更新版本号
        try {
            canUpdate = AppUpdateVersionCheckUtil.compareVersion(newestVersion);
            if (StringUtil.isNotNull(forceUpdateVersion)) {
                canForceUpdate = AppUpdateVersionCheckUtil.compareVersion(forceUpdateVersion);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!canUpdate) {
            timeFinish = true;
            goMianActivity();
            return;
        }
        updateDialog = new VersionDialog(mContext, appUpdateRes.getVersionNumber(), appUpdateRes.getUpdateExplain());
        updateDialog.setConfirmButtonText("立即更新");
        if (appUpdateRes.getType() == 2 || canForceUpdate) {// 1：可用更新，2：强制更新
            updateDialog.setCancelButtonText("退出应用");
        } else {
            updateDialog.setCancelButtonText("暂不更新");
        }
        isUpdate = true;
        boolean finalCanForceUpdate = canForceUpdate;
        updateDialog.setOnCancelButtonClickListener((dialog, view) -> {
            if (appUpdateRes.getType() == 2 || finalCanForceUpdate) {// 1：可用更新，2：强制更新
                ActivityManager.exit();
            } else {
                updateDialog.dismiss();
                timeFinish = true;
                isUpdate = false;
                goMianActivity();
            }
        });
        updateDialog.setOnConfirmButtonClickListener((dialog, view) -> {
            RxPermissionUtils.getInstance(mContext).setPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE).
                    setOnPermissionCallBack(new RxPermissionUtils.OnPermissionListener() {

                        @Override
                        public void onPermissionGranted(String name) {
                            LogUtil.i("onPermissionGranted name=" + name);
                        }

                        @Override
                        protected void onPermissionException(Throwable e) {
                            super.onPermissionException(e);
                            ToastUtil.showShort("权限申请失败" + e.getMessage());
                        }

                        @Override
                        protected void onAllPermissionFinish() {
                            Intent intent = new Intent(mContext, ApkDownInstallService.class);
                            intent.putExtra("intent_md5", appUpdateRes.getUpdateKey());
                            intent.putExtra("apkUrl", appUpdateRes.getAppUrl());
                            intent.putExtra("tag", "splashUpdate");
                            startService(intent);
                            if (appUpdateRes.getType() == 2 || finalCanForceUpdate) {// 1：可用更新，2：强制更新
                                toast("应用正在下载，请稍后");
                                updateDialog.dismiss();
                            } else {
                                updateDialog.dismiss();
                                toast("开始下载");
                            }
                        }
                    }).start();
        });

        updateDialog.show();
    }

    private void setTimeCountDown(final int splashTotalCountdownTime) {
        subscribe = Observable.interval(0, 1, TimeUnit.SECONDS).subscribeOn(AndroidSchedulers
                .mainThread()).map(increaseTime -> splashTotalCountdownTime
                - increaseTime.intValue()).take(splashTotalCountdownTime + 1).subscribe(integer -> {

            if (integer == 0 && isLogin == false) {
                timeFinish = true;
                goMianActivity();
            }
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveMsg(ProgressMessage event) {
        String tag = event.getTag();
        String message = event.getMsg();
        int progress = event.getProgress();
        if (!ProgressMessage.SPLASH_UPDATE.equals(tag)) {
            return;
        }
        switch (message) {
            case ProgressMessage.SHOW:
                if (progressDialog == null) {
                    progressDialog = new UpdateProgressDialog(mContext, newestVersion);
                }
                progressDialog.show();
                break;
            case ProgressMessage.UPDATE:
                if (progressDialog == null) {
                    progressDialog = new UpdateProgressDialog(mContext, newestVersion);
                    progressDialog.show();
                }
                progressDialog.updateProgress(progress);
                break;
            case ProgressMessage.COMPLETE:
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
                if (updateDialog != null) {
                    updateDialog.show();
                }
                break;
            default:
                break;
        }
    }
}

