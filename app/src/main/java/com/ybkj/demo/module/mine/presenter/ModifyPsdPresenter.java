package com.ybkj.demo.module.mine.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.ybkj.demo.R;
import com.ybkj.demo.base.BaseRxPresenter;
import com.ybkj.demo.bean.request.ModifyReq;
import com.ybkj.demo.common.Constants;
import com.ybkj.demo.module.mine.view.ModifyPsdView;
import com.ybkj.demo.utils.ResourcesUtil;
import com.ybkj.demo.utils.ToastUtil;

import javax.inject.Inject;

public class ModifyPsdPresenter extends BaseRxPresenter<ModifyPsdView> {
    @Inject
    public ModifyPsdPresenter(Context context) {
        super(context);
    }

    @Override
    public void onError(String errorMsg) {
        mView.onError(errorMsg);
    }

    @Override
    public void onSuccess(Object response, int tag) {
        mView.modifySuccess();
    }

    public void modifyPsd(String oldPassword, String newPassword, String repeatPassword) {
        if (checkInput(oldPassword, newPassword, repeatPassword)) {
            return;
        }
        //发送请求
        ModifyReq req = new ModifyReq();
        req.setOldPassword(oldPassword);
        req.setNewPassword(newPassword);
        req.setRepeatNewPassword(repeatPassword);
        sendHttpRequest(apiService.modifyPsd(req), Constants.REQUEST_CODE_1);
    }

    private boolean checkInput(String oldPsd, String newPsd, String againPsd) {
        if (TextUtils.isEmpty(oldPsd)) {
            ToastUtil.showShort(ResourcesUtil.getString(R.string.mine_old_password_hint));
            return true;
        }
        if (TextUtils.isEmpty(newPsd)) {
            ToastUtil.showShort(ResourcesUtil.getString(R.string.mine_new_password_hint));
            return true;
        }
        if (TextUtils.isEmpty(againPsd)) {
            ToastUtil.showShort(ResourcesUtil.getString(R.string.mine_again_password_hint));
            return true;
        }
        if (!newPsd.equals(againPsd)) {
            ToastUtil.showShort(ResourcesUtil.getString(R.string.register_pass_equals_again));
            return true;
        }
        return false;
    }
}
