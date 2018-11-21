package com.ybkj.demo;

import android.app.Application;
import android.content.res.Configuration;

import com.bumptech.glide.Glide;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.tencent.bugly.crashreport.CrashReport;
import com.ybkj.demo.common.Constants;
import com.ybkj.demo.dagger.component.AppComponent;
import com.ybkj.demo.dagger.component.DaggerAppComponent;
import com.ybkj.demo.dagger.module.AppModule;
import com.ybkj.demo.dagger.module.HttpModule;
import com.ybkj.demo.utils.DensityHelper;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * - @Author:  Yi Shan Xiang
 * - @Description:  MyApplication
 * - @Time:  2018/7/26
 * - @Emaill:  380948730@qq.com
 */
public class MyApplication extends Application {
    private static MyApplication mContext;
    private static AppComponent mAppComponent;

    //获取application的context
    public static MyApplication getInstance() {
        return mContext;
    }

    /**
     * 获取应用全局对象的component(dagger方式)
     * 此处的httpModule 可以不自己创建，因为无参构造，component内部会自动创建，
     *
     * @return
     */
    public static AppComponent getAppComponent() {
        if (mAppComponent == null) {
            mAppComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(mContext))
                    .httpModule(new HttpModule())
                    .build();
        }
        return mAppComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        //初始化bugly
        initBugly();
        //初始化log
        initLog();
        //激活XT屏幕适配方案
        initScreenAdapter();
    }

    //初始化XT适配方案
    private void initScreenAdapter() {
        new DensityHelper(this, BuildConfig.DESIGN_WIDTH).activate();
    }

    /**
     * 设置app字体不随系统改变
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.fontScale != 1)//非默认值
            getResources();
        super.onConfigurationChanged(newConfig);
    }

    /**
     * app退出时调用，清空图片缓存
     */
    @Override
    public void onTerminate() {
        super.onTerminate();

        Observable.create(e -> Glide.get(mContext).clearDiskCache()).subscribeOn(Schedulers.io()).subscribeOn
                (AndroidSchedulers.mainThread()).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                Glide.get(mContext).clearMemory();
            }
        });
    }

    /**
     * 初始化Bugly(APP异常捕获)
     */
    private void initBugly() {
        CrashReport.initCrashReport(getApplicationContext(), Constants.BUGLY_APP_ID, BuildConfig.DEBUG);
    }

    /**
     * 初始化Log
     */
    private void initLog() {
        Logger.addLogAdapter(new AndroidLogAdapter());
    }


}
