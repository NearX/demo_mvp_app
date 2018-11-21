package com.ybkj.demo.module.login.presenter;

import android.content.Context;

import com.ybkj.demo.base.BaseRxPresenter;
import com.ybkj.demo.bean.request.ModifyPassReq;
import com.ybkj.demo.common.Constants;
import com.ybkj.demo.module.login.view.IForgetPassView;
import com.ybkj.demo.utils.FormCheckUtil;

import javax.inject.Inject;

/**
 * - @Author:  Yi Shan Xiang
 * - @Description:  验证手机验证码
 * - @Time:  2018/11/1
 * - @Emaill:  380948730@qq.com
 */
public class ForgetPassPresenter extends BaseRxPresenter<IForgetPassView> {

    @Inject
    public ForgetPassPresenter(Context context) {
        super(context);
    }

    @Override
    public void onError(String errorMsg) {

    }

    @Override
    public void onSuccess(Object response, int tag) {
        switch (tag) {
            case Constants.REQUEST_CODE_1:
                mView.updatePassSuccess();
                break;
            case Constants.REQUEST_CODE_2:
                mView.updatePassSuccess();
                break;
        }
    }


    /**
     * 发送找回密码请求
     */
    public void forgetPass(String area, String phone, String newPass, String oldPass) {

        if (FormCheckUtil.phoneCheck(area, phone)) return;
        ModifyPassReq modifyPassReq = new ModifyPassReq();
        modifyPassReq.setNewPassword(newPass);
        modifyPassReq.setPhoneCode(area);
        modifyPassReq.setPhoneNumber(phone);
        modifyPassReq.setRepeatNewPassword(newPass);
        sendHttpRequest(apiService.forgetPass(modifyPassReq), Constants.REQUEST_CODE_1);

    }

    /**
     * 发送重置密码请求
     */
    public void modifyPwd(String area, String phone, String newPass, String oldPass) {
        if (FormCheckUtil.phoneCheck(area, phone)) return;
        ModifyPassReq modifyPassReq = new ModifyPassReq();
        modifyPassReq.setNewPassword(newPass);
        modifyPassReq.setPhoneCode(area);
        modifyPassReq.setPhoneNumber(phone);
        modifyPassReq.setRepeatNewPassword(newPass);
        sendHttpRequest(apiService.modifyNormalPwd(modifyPassReq), Constants.REQUEST_CODE_2);

    }


}
