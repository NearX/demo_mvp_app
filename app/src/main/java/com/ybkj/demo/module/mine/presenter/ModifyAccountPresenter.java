package com.ybkj.demo.module.mine.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.ybkj.demo.R;
import com.ybkj.demo.base.BaseRxPresenter;
import com.ybkj.demo.bean.request.ModifyReq;
import com.ybkj.demo.common.Constants;
import com.ybkj.demo.module.mine.view.ModifyAccountView;
import com.ybkj.demo.utils.ResourcesUtil;
import com.ybkj.demo.utils.ToastUtil;

import javax.inject.Inject;

public class ModifyAccountPresenter extends BaseRxPresenter<ModifyAccountView> {
    @Inject
    public ModifyAccountPresenter(Context context) {
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

    public void modifyAccount(String account) {
        if (TextUtils.isEmpty(account)) {
            ToastUtil.showShort(ResourcesUtil.getString(R.string.input_new_account));
            return;
        }
        ModifyReq req = new ModifyReq();
        req.setUserAccount(account);
        sendHttpRequest(apiService.modifyAccount(req), Constants.REQUEST_CODE_1);
    }
}
