package com.ybkj.demo.base;

public interface BaseView<T> {

    /**
     * @param errorMsg 网络请求失败的返回结果
     */
    void onError(String errorMsg);

    void onLoginError();

    /**
     * @param tag 网络请求标记，作为返回
     */
    void onSuccess(int tag);

}
