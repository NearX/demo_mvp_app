package com.ybkj.demo.module;

import android.content.Context;

import com.ybkj.demo.base.BaseRxPresenter;
import com.ybkj.demo.common.Constants;

import javax.inject.Inject;

/**
 * - @Author:  Yi Shan Xiang
 * - @Description:  登录示例
 * - @Time:  2018/8/31
 * - @Emaill:  380948730@qq.com
 */

public class MainPresenter extends BaseRxPresenter<IMainAtView> {

    @Inject
    public MainPresenter(Context context) {
        super(context);
    }

    @Override
    public void onError(String errorMsg) {
        mView.onError(errorMsg);
    }


    @Override
    public void onSuccess(Object response, int tag) {
        mView.onSuccess(tag);
        switch (tag) {
            case Constants.REQUEST_CODE_1: //登录返回成功
                break;
        }
    }

}
