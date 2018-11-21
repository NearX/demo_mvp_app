package com.ybkj.demo.base;

import android.content.Context;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.ybkj.demo.common.NetResponseCode;
import com.ybkj.demo.net.HttpCallBack;
import com.ybkj.demo.net.api.ApiService;
import com.ybkj.demo.net.api.BaseNetFunction;
import com.ybkj.demo.net.exception.HandlerException;
import com.ybkj.demo.net.observer.BaseShowLoadingObserver;
import com.ybkj.demo.utils.ProgressDialogUtil;
import com.ybkj.demo.utils.RxUtil;

import java.lang.ref.SoftReference;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * - @Author:  Yi Shan Xiang
 * - @Description:  rx网络请求的基类
 * - @Time:  2018/8/31
 * - @Emaill:  380948730@qq.com
 */
public abstract class BaseRxPresenter<T extends BaseView> implements BasePresenter<T>, HttpCallBack {

    @Inject
    public ApiService apiService;


    public Context mContext;
    /**
     * 绑定的view
     */
    protected T mView;
    protected SoftReference<Context> contextSoftReference;
    @Inject
    BaseNetFunction baseNetFunction;
    private CompositeDisposable mCompositeDisposable;

    public BaseRxPresenter(Context context) {

        if ((context instanceof RxAppCompatActivity)) {
            this.contextSoftReference = new SoftReference<>(context);
            mContext = contextSoftReference.get();
        }
    }

    /**
     * 解除activity和view的绑定
     * 取消的时候徐娇接触RXjava的订阅和弹出框的销毁
     */
    @Override
    public void detachView() {
        this.mView = null;
        unSubscribe();
        ProgressDialogUtil.stopWaitDialog();
    }

    /**
     * 绑定View
     *
     * @param view
     */
    @Override
    public void attachView(T view) {
        this.mView = view;
    }


    /**
     * 取消订阅
     */
    private void unSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }


    /**
     * 添加RXjava的Disposable订阅事件到集合中
     *
     * @param subscription the subscription
     */
    protected void addSubscribe(Disposable subscription) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(subscription);
    }

    /**
     * 处理http请求，所有的http请求入口
     *
     * @param mObservable    the m observable
     * @param isShowProgress 是否在网络请求时显示进度框
     * @param isCancel       是否可以取消对话框
     * @param tag            the tag
     */
    @SuppressWarnings("unchecked")
    protected void sendHttpRequest(Observable mObservable, int tag, boolean isShowProgress, boolean isCancel) {

        BaseShowLoadingObserver progressObserver = new BaseShowLoadingObserver<>(mContext, this,
                isShowProgress, tag);


        progressObserver.setCancel(isCancel);

        mObservable.compose(RxUtil.rxSchedulerHelper())
                .map(baseNetFunction)
                .subscribe(progressObserver);

        addSubscribe(progressObserver);
    }

    /**
     * Send http request.默认不可以取消对话框，显示进度框
     *
     * @param mObservable the m observable
     * @param tag         the tag 请求标记
     */
    protected void sendHttpRequest(Observable mObservable, int tag) {
        sendHttpRequest(mObservable, tag, true, false);
    }

    /**
     * Send http request.
     *
     * @param mObservable    the m observable
     * @param isShowProgress the is show progress
     * @param tag            the tag 请求标记
     */
    protected void sendHttpRequest(Observable mObservable, int tag, boolean isShowProgress) {
        sendHttpRequest(mObservable, tag, isShowProgress, false);
    }

    /**
     * 请求成功，通知View 返回成功
     * <p>
     *
     * @param o   封装后的实体类
     * @param tag the tag
     */
    @Override
    public void onNext(Object o, int tag) {
        onSuccess(o, tag); //presenter自己方法
        mView.onSuccess(tag);
    }


    /**
     * 获取数据失败
     *
     * @param e   the e
     * @param tag
     */
    @Override
    public void onError(HandlerException.ResponseThrowable e, int tag) {
        switch (e.getCode()) {
            case NetResponseCode.HMC_SUCCESS_NULL:
                onNext(null, tag);
                break;
            default:
                switch (e.getCode()) {
                    case NetResponseCode.HMC_NO_LOGIN:
                        mView.onLoginError();
                        break;
                    default:
                        mView.onError(e.getMessage());
                        onError(e.getMessage(), tag);
                        onError(e.getMessage());
                        break;
                }
                break;
        }
    }


    @Override
    public void onCancel(int tag) {

    }

    /**
     * @param errorMsg 网络请求失败的返回结果
     */
    public void onError(String errorMsg, int tag) {

    }

    /**
     * @param errorMsg 网络请求失败的返回结果
     */
    public void onError(String errorMsg) {

    }

    /**
     * @param tag 网络请求标记，作为返回
     */
    public abstract void onSuccess(Object response, int tag);

}
