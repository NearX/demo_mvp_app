package com.ybkj.demo.base;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ybkj.demo.MyApplication;
import com.ybkj.demo.R;
import com.ybkj.demo.dagger.component.ActivityComponent;
import com.ybkj.demo.dagger.component.DaggerActivityComponent;
import com.ybkj.demo.dagger.module.ActivityModule;
import com.ybkj.demo.manager.ActivityManager;
import com.ybkj.demo.module.login.activity.LoginActivity;
import com.ybkj.demo.ui.dialog.TipDialog;
import com.ybkj.demo.utils.LogUtil;
import com.ybkj.demo.utils.ResourcesUtil;

import javax.inject.Inject;


/**
 * - @Author:  Yi Shan Xiang
 * - @Description:  基础的采用mvp模式建立的 BaseMvpActivity
 * - @Time:  2018/8/31
 * - @Emaill:  380948730@qq.com
 */
public abstract class BaseMvpActivity<T extends BaseRxPresenter> extends BaseActivity implements BaseView {

    /**
     * 对象要给子类用，所以要在其实例化的地方进行注解
     */
    @Inject
    public T presenter;
    private TipDialog loginTipsDialog; //登录提示弹出框

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        injectPresenter();
        if (presenter != null) presenter.attachView(this);
        super.onCreate(savedInstanceState);
    }

    /**
     * 这种是通过dagger2方式创建percent  暂时先不使用
     *
     * @return
     */
    public ActivityComponent getActivityComponent() {
        return DaggerActivityComponent.builder()
                .appComponent(MyApplication.getAppComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.detachView();
        }
        presenter = null;
    }


    /**
     * 初始化presenter
     *
     * @return
     */
    protected abstract void injectPresenter();

    /**
     * 请求成功
     *
     * @param tag 网络请求标记，作为返回
     */
    @Override
    public void onSuccess(int tag) {
    }

    /**
     * 登录过期，弹出提示框
     */
    @Override
    public void onLoginError() {
        if (loginTipsDialog == null) {
            loginTipsDialog = new TipDialog(mContext);
            loginTipsDialog.setContentText(ResourcesUtil.getString(R.string.login_tips));
            loginTipsDialog.setOnConfirmButtonClickListener((Dialog dialog, View view) -> {
                ActivityManager.gotoActivity(mContext, LoginActivity.class);
                loginTipsDialog.dismiss();
            });
            loginTipsDialog.show();
        } else {
            if (!loginTipsDialog.isShowing()) loginTipsDialog.show();
        }
    }

    /**
     * 请求失败
     *
     * @param errorMsg 网络请求失败的返回结果
     */
    @Override
    public void onError(String errorMsg) {
        LogUtil.i("接口请求失败" + ";errorMsg=" + errorMsg);
        toast(errorMsg);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (loginTipsDialog != null && loginTipsDialog.isShowing()) loginTipsDialog.dismiss();
    }
}
