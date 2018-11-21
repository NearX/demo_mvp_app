package com.ybkj.demo.base;

/**
 * - @Author:  Yi Shan Xiang
 * - @Description:  BasePresenter
 * - @Time:  2018/8/31
 * - @Emaill:  380948730@qq.com
 */
public interface BasePresenter<V extends BaseView> {


    //解除绑定
    void detachView();

    //绑定View
    void attachView(V view);
}
