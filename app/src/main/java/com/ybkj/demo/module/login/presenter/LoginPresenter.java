package com.ybkj.demo.module.login.presenter;

import android.content.Context;

import com.ybkj.demo.base.BaseRxPresenter;
import com.ybkj.demo.bean.request.LoginReq;
import com.ybkj.demo.bean.response.LoginRes;
import com.ybkj.demo.common.Constants;
import com.ybkj.demo.manager.UserDataManager;
import com.ybkj.demo.module.login.view.ILoginAtView;
import com.ybkj.demo.net.exception.HandlerException;
import com.ybkj.demo.utils.FormCheckUtil;

import javax.inject.Inject;

/**
 * - @Author:  Yi Shan Xiang
 * - @Description:  登录示例
 * - @Time:  2018/8/31
 * - @Emaill:  380948730@qq.com
 */

public class LoginPresenter extends BaseRxPresenter<ILoginAtView> {

    @Inject
    public LoginPresenter(Context context) {
        super(context);
    }


    @Override
    public void onError(HandlerException.ResponseThrowable e, int tag) {
        super.onError(e, tag);
        if (e.getCode() == 12) {
            mView.gotoModifyPwdActivity();
        }
    }

    @Override
    public void onSuccess(Object response, int tag) {
        mView.onSuccess(tag);
        switch (tag) {
            case Constants.REQUEST_CODE_1: //登录返回成功
                if (response != null) {
                    LoginRes loginRes = (LoginRes) response;
                    UserDataManager.saveLoginInfo(loginRes);
                    mView.gotoMainActivity();

                }
                break;
        }
    }


    public void login(String area, String phone, String pwd) {
        if (FormCheckUtil.phoneCheck(area, phone)) return;
        if (FormCheckUtil.checkAccountAndPwd(pwd)) return;
        LoginReq req = new LoginReq();
        req.setPhoneCode(area);
        req.setPhoneNumber(phone);
        req.setPassword(pwd);
        //发送请求
        sendHttpRequest(apiService.startLogin(req), Constants.REQUEST_CODE_1);

    }

}
