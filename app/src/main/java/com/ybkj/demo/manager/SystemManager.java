package com.ybkj.demo.manager;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.ybkj.demo.MyApplication;
import com.ybkj.demo.R;
import com.ybkj.demo.utils.LogUtil;
import com.ybkj.demo.utils.ResourcesUtil;

/**
 * - @Author:  Yi Shan Xiang
 * - @Description:  系统的工具类
 * - @Time:  2018/8/31
 * - @Emaill:  380948730@qq.com
 */
public class SystemManager {
    private static final int INVALID_VAL = -1;

    /**
     * 是否全屏显示
     *
     * @param context the context
     * @param isShow  the is show
     */
    public static void setStatusBarInvisible(RxAppCompatActivity context, boolean isShow) {
        int option;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = context.getWindow().getDecorView();

            if (isShow) {
                option = View.SYSTEM_UI_FLAG_VISIBLE | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                setStatusBar(context, ResourcesUtil.getColor(R.color.status_bar_color));
            } else {
                option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                //设置状态栏颜色为透明
                context.getWindow().setStatusBarColor(Color.TRANSPARENT);
            }
            decorView.setSystemUiVisibility(option);

        } else {
            setStatusBar(context, ResourcesUtil.getColor(R.color.status_bar_color));
        }
        //隐藏标题栏
        android.support.v7.app.ActionBar actionBar = context.getSupportActionBar();
        if (actionBar != null)
            actionBar.hide();
    }

    /**
     * 隐藏键盘
     *
     * @param context the context
     */
    public static void hideInputWindow(Activity context) {
        if (context == null) {
            return;
        }
        final View v = context.getWindow().peekDecorView();
        if (v != null && v.getWindowToken() != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null)
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
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
//                activity.getWindow().setStatusBarColor(statusColor);
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
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
        int resourceId = MyApplication.getInstance().getResources().getIdentifier("status_bar_height", "dimen",
                "android");
        return resourceId > 0 ? MyApplication.getInstance().getResources().getDimensionPixelSize(resourceId) : 0;
    }


    /**
     * Hide light window.
     *
     * @param context the context
     * @param isHide  the is hide
     */
    public static void hideLightWindow(Activity context, boolean isHide) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = isHide ? 0.5f : 1.0f;
        lp.dimAmount = isHide ? 0.5f : 1.0f;
        context.getWindow().setAttributes(lp);
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    /**
     * 获取屏幕宽
     *
     * @param context 上下文
     * @return 返回屏幕宽度 screen width
     */
    public static int getScreenWidth(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR2) {
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            return display.getWidth();
        } else {
            return getScreenPoint(context).x;
        }
    }

    /**
     * 获取屏幕高
     *
     * @param context 上下文
     * @return 返回屏幕高度密度 screen height
     */
    @SuppressLint("ObsoleteSdkInt")
    public static int getScreenHeight(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR2) {
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = null;
            if (wm != null) {
                display = wm.getDefaultDisplay();
            }
            return display != null ? display.getHeight() : 0;
        } else {
            return getScreenPoint(context).y;
        }
    }

    /**
     * Gets screen point.
     *
     * @param context the context
     * @return the screen point
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public static Point getScreenPoint(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        Display display = wm.getDefaultDisplay();
        display.getSize(point);
        return point;
    }

    /**
     * 获取设备id
     *
     * @param cxt the cxt
     * @return the phone imei
     */
    @SuppressLint("MissingPermission")
    public static String getPhoneIMEI(final Activity cxt) {
        TelephonyManager tm = (TelephonyManager) cxt.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    /**
     * 检查是否是移动网络
     *
     * @param context the context
     * @return the boolean
     */
    public static boolean isMobile(Context context) {

        NetworkInfo info = getNetworkInfo(context);
        if (info != null) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) return true;
        }
        return false;
    }

    private static NetworkInfo getNetworkInfo(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context
                .CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }

    /**
     * 检查是否有网络
     *
     * @param context the context
     * @return the boolean
     */
    public static boolean isNetworkAvailable(Context context) {

        NetworkInfo info = getNetworkInfo(context);
        return info != null && info.isAvailable();
    }

    /**
     * 检查是否是WIFI
     *
     * @param context the context
     * @return the boolean
     */
    public static boolean isWifi(Context context) {

        NetworkInfo info = getNetworkInfo(context);
        if (info != null) {
            if (info.getType() == ConnectivityManager.TYPE_WIFI) return true;
        }
        return false;
    }


    /**
     * 获取渠道编号
     *
     * @param context the context
     * @param key     the key
     * @return 异常情况返回null channel code
     */
    public static String getChannelCode(Context context, String key) {
        if (context == null || TextUtils.isEmpty(key)) {
            return null;
        }
        String channelName = null;
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo
                    (context.getPackageName(), PackageManager.GET_META_DATA);
            if (applicationInfo != null) {
                if (applicationInfo.metaData != null) {
                    channelName = applicationInfo.metaData.get(key) + "";
                }
            }

        } catch (Exception e) {
            LogUtil.i("获取设备市场渠道异常=" + e.getMessage());
        }
        return channelName;
    }

    /**
     * 隐藏键盘
     *
     * @param activity 上下文
     */
//键盘
    public static void hideKeyBoard(Activity activity) {
        try {
            ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 返回当前程序版本名
     *
     * @param context the context
     * @return the app version code
     */
    public static int getAppVersionCode(Context context) {
        int versioncode = -1;
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versioncode = pi.versionCode;
        } catch (Exception e) {
            LogUtil.i(e.getMessage());
        }
        return versioncode;
    }

    /**
     * 返回当前程序版本名
     *
     * @param context the context
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
     * 获取分辨率
     *
     * @param context the context
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
     * @param context the context
     * @return the android pix
     */
    public static int getAndroidPixHeigth(Activity context) {
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

}
