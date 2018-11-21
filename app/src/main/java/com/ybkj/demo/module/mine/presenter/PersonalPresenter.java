package com.ybkj.demo.module.mine.presenter;

import android.content.Context;

import com.ybkj.demo.base.BaseRxPresenter;
import com.ybkj.demo.bean.response.MineDataRes;
import com.ybkj.demo.common.Constants;
import com.ybkj.demo.module.mine.view.PersonalView;

import javax.inject.Inject;

public class PersonalPresenter extends BaseRxPresenter<PersonalView> {
    @Inject
    public PersonalPresenter(Context context) {
        super(context);
    }

    @Override
    public void onError(String errorMsg) {
        mView.onError(errorMsg);
    }

    @Override
    public void onSuccess(Object response, int tag) {
        switch (tag) {
            case Constants.REQUEST_CODE_1:
                MineDataRes res = (MineDataRes) response;
                if (res != null) {
                    mView.setMinData(res);
                }

                break;
        }
    }

    public void getMineData() {
        //发送请求
        sendHttpRequest(apiService.getMineData(), Constants.REQUEST_CODE_1);
    }
}
