package com.ybkj.demo;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.support.multidex.MultiDex;

import com.bumptech.glide.Glide;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.interfaces.BetaPatchListener;
import com.tencent.tinker.loader.app.DefaultApplicationLike;
import com.ybkj.demo.common.Constants;
import com.ybkj.demo.dagger.component.AppComponent;
import com.ybkj.demo.dagger.component.DaggerAppComponent;
import com.ybkj.demo.dagger.module.AppModule;
import com.ybkj.demo.dagger.module.HttpModule;
import com.ybkj.demo.utils.DensityHelper;
import com.ybkj.demo.utils.ToastUtil;

import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 自定义ApplicationLike类.
 * 注意：这个类是Application的代理类，以前所有在Application的实现必须要全部拷贝到这里
 */
public class SampleApplicationLike extends DefaultApplicationLike {
    private static MyApplication mContext;
    private static AppComponent mAppComponent;

    public SampleApplicationLike(Application application,
                                 int tinkerFlags,
                                 boolean tinkerLoadVerifyFlag,
                                 long applicationStartElapsedTime,
                                 long applicationStartMillisTime,
                                 Intent tinkerResultIntent) {
        super(application, tinkerFlags,
                tinkerLoadVerifyFlag, applicationStartElapsedTime,
                applicationStartMillisTime, tinkerResultIntent);
        mContext = (MyApplication) application;
    }


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
        //初始化bugly
        initBugly();
        //初始化log
        initLog();
        //激活XT屏幕适配方案
        initScreenAdapter();


        // 设置是否开启热更新能力，默认为true
        Beta.enableHotfix = true;
        // 设置是否自动下载补丁，默认为true
        Beta.canAutoDownloadPatch = true;
        // 设置是否自动合成补丁，默认为true
        Beta.canAutoPatch = true;
        // 设置是否提示用户重启，默认为false
        Beta.canNotifyUserRestart = true;
        // 补丁回调接口
        Beta.betaPatchListener = new BetaPatchListener() {
            @Override
            public void onPatchReceived(String patchFile) {
                ToastUtil.showShort("补丁下载地址" + patchFile);
            }

            @Override
            public void onDownloadReceived(long savedLength, long totalLength) {
                ToastUtil.showShort(
                        String.format(Locale.getDefault(), "%s %d%%",
                                Beta.strNotificationDownloading,
                                (int) (totalLength == 0 ? 0 : savedLength * 100 / totalLength)));
            }

            @Override
            public void onDownloadSuccess(String msg) {
                ToastUtil.showShort("补丁下载成功");
            }

            @Override
            public void onDownloadFailure(String msg) {
                ToastUtil.showShort("补丁下载失败");
            }

            @Override
            public void onApplySuccess(String msg) {
                ToastUtil.showShort("补丁应用成功");
            }

            @Override
            public void onApplyFailure(String msg) {
                ToastUtil.showShort("补丁应用失败");
            }

            @Override
            public void onPatchRollback() {
            }
        };

        // 设置开发设备，默认为false，上传补丁如果下发范围指定为“开发设备”，需要调用此接口来标识开发设备
        Bugly.setIsDevelopmentDevice(getApplication(), true);
        // 多渠道需求塞入
        // String channel = WalleChannelReader.getChannel(getApplication());
        // Bugly.setAppChannel(getApplication(), channel);
    }

    //初始化XT适配方案
    private void initScreenAdapter() {
        new DensityHelper(mContext, BuildConfig.DESIGN_WIDTH).activate();
    }

    /**
     * 设置app字体不随系统改变
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.fontScale != 1)//非默认值
            mContext.getResources();
        super.onConfigurationChanged(newConfig);
    }

    /**
     * app退出时调用，
     */
    @Override
    public void onTerminate() {
        super.onTerminate();

        //清空图片缓存
        Observable.create(e -> Glide.get(mContext).clearDiskCache()).subscribeOn(Schedulers.io()).subscribeOn
                (AndroidSchedulers.mainThread()).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                Glide.get(mContext).clearMemory();
            }
        });

        //取消初始化
        Beta.unInit();
    }

    /**
     * 初始化Bugly(APP异常捕获)
     */
    private void initBugly() {
//        CrashReport.initCrashReport(getApplicationContext(), Constants.BUGLY_APP_ID, BuildConfig.DEBUG);
        //参数3：是否开启debug模式，true表示打开debug模式，false表示关闭调试模式
        Bugly.init(mContext, Constants.BUGLY_APP_ID, true);
    }

    /**
     * 初始化Log
     */
    private void initLog() {
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        // you must install multiDex whatever tinker is installed!
        MultiDex.install(base);

        // 安装tinker
        // TinkerManager.installTinker(this); 替换成下面Bugly提供的方法
        Beta.installTinker(this);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void registerActivityLifecycleCallback(Application.ActivityLifecycleCallbacks callbacks) {
        getApplication().registerActivityLifecycleCallbacks(callbacks);
    }
}
