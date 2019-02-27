package com.ybkj.demo;

import android.content.Context;
import android.support.annotation.Keep;

import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixApplication;
import com.taobao.sophix.SophixEntry;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;
import com.ybkj.demo.common.Constants;
import com.ybkj.demo.utils.LogUtil;

import static com.alibaba.mtl.appmonitor.AppMonitor.TAG;

/**
 * - @Author:  Yi Shan Xiang
 * - @Description:  阿里热更新application
 * - @Time:  2019/1/22
 * - @Emaill:  380948730@qq.com
 */

public class SophixStubApplication extends SophixApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        //补丁查询
        SophixManager.getInstance().queryAndLoadNewPatch();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        String appVersion = "0.0.0";
        try {
            appVersion = this.getPackageManager()
                    .getPackageInfo(this.getPackageName(), 0)
                    .versionName;
        } catch (Exception e) {
        }
        final SophixManager sophixManager = SophixManager.getInstance();
        sophixManager.setContext(this)
                .setAppVersion(appVersion)
                .setSecretMetaData(Constants.ALIBABA_APP_KEY,
                        Constants.ALIBABA_APP_SECRET,
                        Constants.ALIBABA_APP_RSASECRET)
                .setEnableDebug(true)
                .setEnableFullLog()
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(int mode, int code, String info, int handlePatchVersion) {
                        if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                            LogUtil.i(TAG, "sophix load patch success!");
                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                            // 如果需要在后台重启，建议此处用SharePreference保存状态。
                            LogUtil.i(TAG, "sophix preload patch success. restart app to make effect.");
                        }
                    }
                })
                .initialize();

    }

    @Keep
    @SophixEntry(MyApplication.class)
    static class RealApplicationStub {
    }
}
