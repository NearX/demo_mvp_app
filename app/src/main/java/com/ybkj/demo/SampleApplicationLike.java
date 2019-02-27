package com.ybkj.demo;

/**
 * 自定义ApplicationLike类.
 * 注意：这个类是Application的代理类，以前所有在Application的实现必须要全部拷贝到这里
 */
public class SampleApplicationLike {
//public class SampleApplicationLike extends DefaultApplicationLike {
//    private static MyApplication mContext;
//    private static AppComponent mAppComponent;
//
//    public SampleApplicationLike(Application application,
//                                 int tinkerFlags,
//                                 boolean tinkerLoadVerifyFlag,
//                                 long applicationStartElapsedTime,
//                                 long applicationStartMillisTime,
//                                 Intent tinkerResultIntent) {
//        super(application, tinkerFlags,
//                tinkerLoadVerifyFlag, applicationStartElapsedTime,
//                applicationStartMillisTime, tinkerResultIntent);
//        mContext = (MyApplication) application;
//    }
//
//
//    //获取application的context
//    public static MyApplication getInstance() {
//        return mContext;
//    }
//
//    /**
//     * 获取应用全局对象的component(dagger方式)
//     * 此处的httpModule 可以不自己创建，因为无参构造，component内部会自动创建，
//     *
//     * @return
//     */
//    public static AppComponent getAppComponent() {
//        if (mAppComponent == null) {
//            mAppComponent = DaggerAppComponent.builder()
//                    .appModule(new AppModule(mContext))
//                    .httpModule(new HttpModule())
//                    .build();
//        }
//        return mAppComponent;
//    }
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        //初始化bugly
//        initBugly();
//        //初始化log
//        initLog();
//        //激活屏幕适配方案
//        initScreenAdapter();
//
//
//        // 设置是否开启热更新能力，默认为true
//        Beta.enableHotfix = true;
//        // 设置是否自动下载补丁，默认为true
//        Beta.canAutoDownloadPatch = true;
//        // 设置是否自动合成补丁，默认为true
//        Beta.canAutoPatch = true;
//        // 设置是否提示用户重启，默认为false
//        Beta.canNotifyUserRestart = true;
//        // 补丁回调接口
//        Beta.betaPatchListener = new BetaPatchListener() {
//            @Override
//            public void onPatchReceived(String patchFile) {
//                ToastUtil.showShort("补丁下载地址" + patchFile);
//            }
//
//            @Override
//            public void onDownloadReceived(long savedLength, long totalLength) {
//                ToastUtil.showShort(
//                        String.format(Locale.getDefault(), "%s %d%%",
//                                Beta.strNotificationDownloading,
//                                (int) (totalLength == 0 ? 0 : savedLength * 100 / totalLength)));
//            }
//
//            @Override
//            public void onDownloadSuccess(String msg) {
//                ToastUtil.showShort("补丁下载成功");
//            }
//
//            @Override
//            public void onDownloadFailure(String msg) {
//                ToastUtil.showShort("补丁下载失败");
//            }
//
//            @Override
//            public void onApplySuccess(String msg) {
//                ToastUtil.showShort("补丁应用成功");
//            }
//
//            @Override
//            public void onApplyFailure(String msg) {
//                ToastUtil.showShort("补丁应用失败");
//            }
//
//            @Override
//            public void onPatchRollback() {
//            }
//        };
//
//        // 设置开发设备，默认为false，上传补丁如果下发范围指定为“开发设备”，需要调用此接口来标识开发设备
//        Bugly.setIsDevelopmentDevice(getApplication(), true);
//        // 多渠道需求塞入
//        // String channel = WalleChannelReader.getChannel(getApplication());
//        // Bugly.setAppChannel(getApplication(), channel);
//    }
//
//    //初始化XT适配方案
//    private void initScreenAdapter() {
////        new DensityHelper(mContext, BuildConfig.DESIGN_WIDTH).activate();
//
//
//        //当 App 中出现多进程, 并且您需要适配所有的进程, 就需要在 App 初始化时调用 initCompatMultiProcess()
//        //在 Demo 中跳转的三方库中的 DefaultErrorActivity 就是在另外一个进程中, 所以要想适配这个 Activity 就需要调用 initCompatMultiProcess()
//        AutoSize.initCompatMultiProcess(mContext);
//        /**
//         * 以下是 AndroidAutoSize 可以自定义的参数, {@link AutoSizeConfig} 的每个方法的注释都写的很详细
//         * 使用前请一定记得跳进源码，查看方法的注释, 下面的注释只是简单描述!!!
//         */
//        AutoSizeConfig.getInstance()
//                //是否让框架支持自定义 Fragment 的适配参数, 由于这个需求是比较少见的, 所以须要使用者手动开启
//                //如果没有这个需求建议不开启
//                .setCustomFragment(false)
//                //是否屏蔽系统字体大小对 AndroidAutoSize 的影响, 如果为 true, App 内的字体的大小将不会跟随系统设置中字体大小的改变
//                //如果为 false, 则会跟随系统设置中字体大小的改变, 默认为 false
////                .setExcludeFontScale(true)
//                //屏幕适配监听器
//                .setOnAdaptListener(new onAdaptListener() {
//                    @Override
//                    public void onAdaptBefore(Object target, Activity activity) {
//                        //使用以下代码, 可支持 Android 的分屏或缩放模式, 但前提是在分屏或缩放模式下当用户改变您 App 的窗口大小时
//                        //系统会重绘当前的页面, 经测试在某些机型, 某些情况下系统不会重绘当前页面, ScreenUtils.getScreenSize(activity) 的参数一定要不要传 Application!!!
////                        AutoSizeConfig.getInstance().setScreenWidth(ScreenUtils.getScreenSize(activity)[0]);
////                        AutoSizeConfig.getInstance().setScreenHeight(ScreenUtils.getScreenSize(activity)[1]);
//                        LogUtils.d(String.format(Locale.ENGLISH, "%s onAdaptBefore!", target.getClass().getName()));
//                    }
//
//                    @Override
//                    public void onAdaptAfter(Object target, Activity activity) {
//                        LogUtils.d(String.format(Locale.ENGLISH, "%s onAdaptAfter!", target.getClass().getName()));
//                    }
//                });
//
//        //是否打印 AutoSize 的内部日志, 默认为 true, 如果您不想 AutoSize 打印日志, 则请设置为 false
////                .setLog(false)
//        //是否使用设备的实际尺寸做适配, 默认为 false, 如果设置为 false, 在以屏幕高度为基准进行适配时
//        //AutoSize 会将屏幕总高度减去状态栏高度来做适配
//        //设置为 true 则使用设备的实际屏幕高度, 不会减去状态栏高度
////                .setUseDeviceSize(true)
//        //是否全局按照宽度进行等比例适配, 默认为 true, 如果设置为 false, AutoSize 会全局按照高度进行适配
////                .setBaseOnWidth(false)
//        //设置屏幕适配逻辑策略类, 一般不用设置, 使用框架默认的就好
////                .setAutoAdaptStrategy(new AutoAdaptStrategy())
//    }
//
//
//    /**
//     * 设置app字体不随系统改变
//     */
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        if (newConfig.fontScale != 1)//非默认值
//            mContext.getResources();
//        super.onConfigurationChanged(newConfig);
//    }
//
//    /**
//     * app退出时调用，
//     */
//    @Override
//    public void onTerminate() {
//        super.onTerminate();
//
//        //清空图片缓存
//        Observable.create(e -> Glide.get(mContext).clearDiskCache()).subscribeOn(Schedulers.io()).subscribeOn
//                (AndroidSchedulers.mainThread()).subscribe(new Consumer<Object>() {
//            @Override
//            public void accept(Object o) throws Exception {
//                Glide.get(mContext).clearMemory();
//            }
//        });
//
//        //取消初始化
//        Beta.unInit();
//    }
//
//    /**
//     * 初始化Bugly(APP异常捕获)
//     */
//    private void initBugly() {
////        CrashReport.initCrashReport(getApplicationContext(), Constants.BUGLY_APP_ID, BuildConfig.DEBUG);
//        //参数3：是否开启debug模式，true表示打开debug模式，false表示关闭调试模式
//        Bugly.init(mContext, Constants.BUGLY_APP_ID, true);
//    }
//
//    /**
//     * 初始化Log
//     */
//    private void initLog() {
//        Logger.addLogAdapter(new AndroidLogAdapter());
//    }
//
//    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
//    @Override
//    public void onBaseContextAttached(Context base) {
//        super.onBaseContextAttached(base);
//        // you must install multiDex whatever tinker is installed!
//        MultiDex.install(base);
//
//        // 安装tinker
//        // TinkerManager.installTinker(this); 替换成下面Bugly提供的方法
//        Beta.installTinker(this);
//    }
//
//    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
//    public void registerActivityLifecycleCallback(Application.ActivityLifecycleCallbacks callbacks) {
//        getApplication().registerActivityLifecycleCallbacks(callbacks);
//    }
}
