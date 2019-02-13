package com.ybkj.demo.module.mine.presenter;

import android.content.Context;

import com.ybkj.demo.base.BaseRxPresenter;
import com.ybkj.demo.bean.response.VersionRes;
import com.ybkj.demo.common.Constants;
import com.ybkj.demo.module.mine.view.CheckVersionView;

import javax.inject.Inject;

/**
 * - @Author:  Yi Shan Xiang
 * - @Description:  检查版本
 * - @Time:  2018/11/9
 * - @Emaill:  380948730@qq.com
 */
public class CheckVersionPresenter extends BaseRxPresenter<CheckVersionView> {
    @Inject
    public CheckVersionPresenter(Context context) {
        super(context);
    }

    @Override
    public void onError(String errorMsg) {
        mView.onError(errorMsg);
    }

    @Override
    public void onSuccess(Object response, int tag) {
        VersionRes res = (VersionRes) response;
        if (res != null) {
            mView.loadData(res.getAppVersion());
        }
    }

    public void getVersion(boolean showDialog) {
        sendHttpRequest(apiService.getVersion(), Constants.REQUEST_CODE_1, showDialog);
    }
}
