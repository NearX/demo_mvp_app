package com.ybkj.demo.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.CookieSyncManager;

import com.ybkj.demo.SampleApplicationLike;


/**
 * author：rongkui.xiao --2018/6/12
 * email：dovexiaoen@163.com
 * description:获取设备属性
 */
public class SystemUtil {
    private static final int INVALID_VAL = -1;

    /**
     * 返回当前程序版本名
     *
     * @param context the mContext
     * @return the app version name
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        int versioncode = 0;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            versioncode = pi.versionCode;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            LogUtil.i(e.getMessage());
        }
        return versionName;
    }

    /**
     * 获取系统版本号
     *
     * @return the android pix
     */
    public static String getAndroidSystemVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取设备信息
     *
     * @return the android pix
     */
    public static String getAndroidDevice() {
        return Build.MODEL;
    }

    /**
     * 获取分辨率
     *
     * @param context the mContext
     * @return the android pix
     */
    public static int getAndroidPixWidth(Activity context) {
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 获取分辨率
     *
     * @param context the mContext
     * @return the android pix
     */
    public static int getAndroidPixHeigth(Activity context) {
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    /**
     * 设置沉浸式状态栏
     *
     * @param activity    the activity
     * @param statusColor the status color
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusBar(Activity activity, int statusColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0以上版本
            if (statusColor != INVALID_VAL) {
                activity.getWindow().setStatusBarColor(statusColor);
            }
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build
                .VERSION_CODES.LOLLIPOP) {//4.4-5.0
            // 设置状态栏透明
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            int color = Color.parseColor("#20000000");
            if (statusColor != INVALID_VAL) {
                color = statusColor;
            }
            View statusBarView = new View(activity);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams
                    .MATCH_PARENT, getStatusBarHeight());
            statusBarView.setBackgroundColor(color);
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            decorView.addView(statusBarView, lp);
            //给父容器设置参数
            ViewGroup contentView = activity.findViewById(android.R.id.content);
            contentView.getChildAt(0).setFitsSystemWindows(true);
            contentView.setClipToPadding(true);
        }
    }

    /**
     * 获取状态栏高度
     *
     * @return px status bar height
     */
    public static int getStatusBarHeight() {
        int resourceId = SampleApplicationLike.getInstance().getResources().getIdentifier("status_bar_height", "dimen",
                "android");
        return resourceId > 0 ? SampleApplicationLike.getInstance().getResources().getDimensionPixelSize(resourceId) : 0;
    }

    /**
     * Aysnc web view.
     *
     * @param context the mContext
     * @param url     the url
     */
    public static void aysncWebView(Context context, String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        CookieSyncManager.createInstance(context);
//        CookieManager cookieManager = CookieManager.getInstance();
//        String cookieString = UserDataManager.getToken();
//        cookieManager.setCookie(url, cookieString);
        CookieSyncManager.getInstance().sync();
    }
}
