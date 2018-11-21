package com.ybkj.demo.net.observer;

import android.content.Context;

import com.ybkj.demo.MyApplication;
import com.ybkj.demo.R;
import com.ybkj.demo.common.NetResponseCode;
import com.ybkj.demo.manager.SystemManager;
import com.ybkj.demo.net.HttpCallBack;
import com.ybkj.demo.net.exception.HandlerException;
import com.ybkj.demo.ui.dialog.LoadingDialog;
import com.ybkj.demo.utils.LogUtil;
import com.ybkj.demo.utils.ProgressDialogUtil;
import com.ybkj.demo.utils.ResourcesUtil;

import java.lang.ref.WeakReference;

/**
 * - @Author:  Yi Shan Xiang
 * - @Description:  网络请求观察者(刷新样式由子类决定)
 * - @Time:  2018/8/31
 * - @Emaill:  380948730@qq.com
 */
public class BaseShowLoadingObserver<T> extends BaseObserver<T> {

    private final WeakReference<Context> mActivity;
    private HttpCallBack httpCallBack = null;
    private boolean showProgressDialog;

    private boolean isCancel;
    private int tag;

    public BaseShowLoadingObserver(Context context, HttpCallBack httpCallBack, boolean isShowProgress, int tag) {
        this.httpCallBack = httpCallBack;
        this.mActivity = new WeakReference<>(context);
        this.showProgressDialog = isShowProgress;
        this.tag = tag;
    }

    /**
     * 显示loading
     */
    protected void showLoading() {
        ProgressDialogUtil.showWaitDialog();
    }

    /**
     * 隐藏loading
     */
    protected void hideLoading() {
        ProgressDialogUtil.stopWaitDialog();
    }

    /**
     * 取消ProgressDialog的时候，取消对observable的订阅，同时也取消了http请求
     */
    public void onCancelProgress() {
        if (!this.isDisposed())
            this.dispose();
        hideLoading();
        if (httpCallBack != null) httpCallBack.onCancel(tag);
        LogUtil.i("请求已经取消");
    }

    private void initProgressDialog(boolean cancel) {
        Context context = mActivity.get();
        LoadingDialog loadingDialog = ProgressDialogUtil.initProgressDialog(context, cancel);
        if (cancel && loadingDialog != null)
            loadingDialog.setOnCancelListener(dialog -> onCancelProgress());
    }

    @Override
    protected void onRequestStart() {
        super.onRequestStart();
        if (showProgressDialog) {
            initProgressDialog(isCancel);
            showLoading();
        }
        if (!SystemManager.isNetworkAvailable(MyApplication.getInstance())) {
            onRequestError(HandlerException.handleException(new
                    HandlerException.ResponseThrowable(ResourcesUtil.getString(R.string.net_connect_error),
                    NetResponseCode
                            .HMC_NETWORK_ERROR)));

        }
    }

    @Override
    protected void onRequestSuccess(T t) {
        LogUtil.i("成功网络请求返回");
        if (httpCallBack != null) {
            httpCallBack.onNext(t, tag);
        }
        hideLoading();
    }

    @Override
    protected void onRequestError(Throwable e) {
        hideLoading();
        LogUtil.i("订阅出错。异常情况=" + e.toString() + ":" + e.getMessage());
        errorDo(e);
    }

    private void errorDo(Throwable e) {
        Context context = mActivity.get();
        if (context == null) return;
        if (httpCallBack != null) {
            httpCallBack.onError(HandlerException.handleException(e), tag);
        }
    }

    @Override
    protected void onRequestComplete() {
        super.onRequestComplete();
        hideLoading();
    }

    public void setCancel(boolean cancel) {
        isCancel = cancel;
    }

}
