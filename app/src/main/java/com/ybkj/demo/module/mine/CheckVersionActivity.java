package com.ybkj.demo.module.mine;


import android.view.View;
import android.widget.TextView;

import com.ybkj.demo.R;
import com.ybkj.demo.base.BaseMvpActivity;
import com.ybkj.demo.bean.response.VersionRes;
import com.ybkj.demo.manager.ActivityManager;
import com.ybkj.demo.module.mine.presenter.CheckVersionPresenter;
import com.ybkj.demo.module.mine.view.CheckVersionView;
import com.ybkj.demo.ui.dialog.VersionDialog;
import com.ybkj.demo.utils.AppUpdateVersionCheckUtil;
import com.ybkj.demo.utils.ResourcesUtil;

import butterknife.BindView;

/**
 * 版本检测
 */
public class CheckVersionActivity extends BaseMvpActivity<CheckVersionPresenter> implements CheckVersionView {

    @BindView(R.id.new_version_tv)
    TextView newVersionTv;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.update_content_tv)
    TextView updateContentTv;
    private boolean canUpdate;

    @Override
    protected void injectPresenter() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initTitle() {
        setTitleText(ResourcesUtil.getString(R.string.mine_check_version));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_check_version;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        presenter.getVersion(true);

    }

    @Override
    public void LoadData(VersionRes res) {
        if (res != null) {

            try {
                canUpdate = AppUpdateVersionCheckUtil.compareVersion(res.getNewestVersion());
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (!canUpdate) {
                newVersionTv.setText("当前已是最新版本");
                titleTv.setVisibility(View.GONE);
                updateContentTv.setVisibility(View.GONE);
                toast("当前已是最新版本");
                return;
            }
            newVersionTv.setText(res.getNewestVersion());
            titleTv.setText("更新说明");
            updateContentTv.setText(res.getUpdateExplain());
            VersionDialog updateDialog = new VersionDialog(mContext, res.getNewestVersion(), res.getUpdateExplain());

            if (res.getForceStatus() == 1) {// 1 强制更新 2不强制更新
                updateDialog.setCancelButtonText("退出应用");
            } else {
                updateDialog.setCancelButtonText("取消更新");
            }
            updateDialog.setOnCancelButtonClickListener((dialog, view) -> {
                if (res.getForceStatus() == 1) {// 1 强制更新 2不强制更新
                    ActivityManager.exit();
                } else {
                    updateDialog.dismiss();
                }
            });
            updateDialog.setOnConfirmButtonClickListener((dialog, view) -> {
                updateDialog.cancel();
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
                        if (res.getForceStatus() == 1) {// 1 强制更新 2不强制更新
                            toast("应用正在下载，请稍后");
                        } else {
                            updateDialog.dismiss();
                            toast("应用转入后台下载");
                        }
                    }
                }).downLoadApk(mContext, res.getAppUrl());
            });
            updateDialog.show();
        }
    }

}
