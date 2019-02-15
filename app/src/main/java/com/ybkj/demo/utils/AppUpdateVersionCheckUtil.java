package com.ybkj.demo.utils;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import com.ybkj.demo.BuildConfig;
import com.ybkj.demo.SampleApplicationLike;
import com.ybkj.demo.service.ApkDownInstallService;

import java.io.File;
import java.util.List;

/**
 * APP更新版本检测下载安装
 *
 */
public class AppUpdateVersionCheckUtil {
    private OnDownLoadListener listener;

    private AppUpdateVersionCheckUtil() {
    }

    public static AppUpdateVersionCheckUtil getInstance() {
        return AppUpdateVersionCheckUtilHolder.instance;
    }


    /**
     * 检测能否更新
     *
     * @param newestVersion 最新版本号
     * @return true 更新 false 不更新
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static boolean checkCanUpdate(String oldVersion, String newestVersion) {
        //获取当前版本号
        //转换成小写字符串
        String nowVersion = oldVersion.toLowerCase();
        newestVersion = newestVersion.toLowerCase();
        //去除v
        if (newestVersion.contains("v")) {
            newestVersion.replace("v", "");
        }
        if (nowVersion.contains("v")) {
            nowVersion.replace("v", "");
        }
        String[] newestVersions = newestVersion.split("\\.");
        String[] nowVersions = nowVersion.split("\\.");
        //版本号格式不符则直接跳过不更新
        if (newestVersions.length != 3 || nowVersions.length != 3) {
            return false;
        }
        //从高位逐位比较
        try {
            for (int i = 0; i < 3; i++) {
                if (Integer.parseInt(newestVersions[i]) > Integer.parseInt(nowVersions[i])) {
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    /**
     * 版本号比较
     *
     * @param version1 新版本号
     *                 //     * @param version2 旧版本号
     * @return true代表version1大于version2，需要升级;false代表不需要升级
     */
    public static boolean compareVersion(String version1) throws Exception {
        String version2 = getVersion();
        LogUtil.i("new version1" + version1 + "oldversion2=" + version2);
        if (version1.equalsIgnoreCase(version2)) {
            return false;
        }
        if (!TextUtils.isEmpty(version1) && (version1.startsWith("V") || version1
                .startsWith("v"))) {
            version1 = version1.substring(1, version1.length());
        }
        if (!TextUtils.isEmpty(version2) && (version2.startsWith("V") || version2
                .startsWith("v"))) {
            version2 = version2.substring(1, version2.length());
        }

        String[] version1Array = version1.split("\\.");
        String[] version2Array = version2.split("\\.");
        int index = 0;
        // 获取最小长度值
        int minLen = Math.min(version1Array.length, version2Array.length);
        int diff = 0;
        // 循环判断每位的大小
        while (index < minLen && (diff = Integer.parseInt(version1Array[index]) - Integer
                .parseInt(version2Array[index])) == 0) {
            index++;
        }
        if (diff == 0) {
            // 如果位数不一致，比较多余位数
            for (int i = index; i < version1Array.length; i++) {
                if (Integer.parseInt(version1Array[i]) > 0) {
                    return true;
                }
            }
            for (int i = index; i < version2Array.length; i++) {
                if (Integer.parseInt(version2Array[i]) > 0) {
                    return false;
                }
            }
            return false;
        } else {
            return diff > 0;
        }
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static String getVersion() {
        try {
            PackageManager manager = SampleApplicationLike.getInstance().getPackageManager();
            PackageInfo info = manager.getPackageInfo(SampleApplicationLike.getInstance().getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
    }

    public AppUpdateVersionCheckUtil setOnDownLoadListener(OnDownLoadListener listener) {
        this.listener = listener;
        return this;
    }

    /**
     * 下载app
     *
     * @param mContext
     * @param url
     */
    public void downLoadApk(Activity mContext, String url) {
        LogUtil.i("开始下载url=" + url);
        RxPermissionUtils.getInstance(mContext).setPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE).
                setOnPermissionCallBack(new RxPermissionUtils.OnPermissionListener() {

                    @Override
                    public void onPermissionGranted(String name) {
                        LogUtil.i("onPermissionGranted name=" + name);
                    }

                    @Override
                    protected void onPermissionException(Throwable e) {
                        super.onPermissionException(e);
                        ToastUtil.showShort("权限申请失败" + e.getMessage());
                    }

                    @Override
                    protected void onAllPermissionFinish() {
                        Intent intent = new Intent(mContext, ApkDownInstallService.class);
                        intent.putExtra("apkUrl", url);
                        mContext.startService(intent);
                        listener.onBegin();
                    }
                }).start();

    }

    /**
     * 描述：打开并安装文件.
     *
     * @param context the mContext
     * @param file    apk文件路径
     */
    public void installApk(Context context, File file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri fileUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileProvider",
                    file);//android 7.0以上
            intent.setDataAndType(fileUri, "application/vnd.android.package-archive");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            grantUriPermission(context, fileUri, intent);
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android" + "" +
                    ".package-archive");
        }
        context.startActivity(intent);
    }

    private void grantUriPermission(Context context, Uri fileUri, Intent intent) {
        List<ResolveInfo> resInfoList = context.getPackageManager().queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolveInfo : resInfoList) {
            String packageName = resolveInfo.activityInfo.packageName;
            context.grantUriPermission(packageName, fileUri, Intent
                    .FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
    }

    private static class AppUpdateVersionCheckUtilHolder {
        public static AppUpdateVersionCheckUtil instance = new AppUpdateVersionCheckUtil();
    }

    public static class OnDownLoadListener {
        protected void onSuccess() {
        }

        protected void onFailed() {
        }

        protected void onBegin() {
        }

        protected void onProgress(int progrss) {
        }
    }
}
