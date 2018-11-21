package com.ybkj.demo.net.observer;

import io.reactivex.observers.DisposableObserver;

/**
 * - @Author:  Yi Shan Xiang
 * - @Description:   网络请求观察者（activity关闭时同时取消网络订阅）
 * - @Time:  2018/8/31
 * - @Emaill:  380948730@qq.com
 */
public abstract class BaseObserver<T> extends DisposableObserver<T> {

    public BaseObserver() {
    }

    /**
     * 网络请求开始
     */
    protected void onRequestStart() {

    }

    /**
     * 网络请求成功 业务处理成功code = 1
     */
    protected abstract void onRequestSuccess(T response);

    /**
     * 网络请求成功 业务处理失败code != 1
     */
    protected abstract void onRequestError(Throwable response);

    /**
     * 网络请求完成
     */
    protected void onRequestComplete() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        onRequestStart();
    }

    @Override
    public void onNext(T t) {
        onRequestSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        onRequestError(e);
    }

    @Override
    public void onComplete() {
        onRequestComplete();
    }
}
