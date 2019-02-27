package com.ybkj.demo.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.ybkj.demo.MyApplication;


/**
 * author：rongkui.xiao --2018/6/8
 * email：dovexiaoen@163.com
 * description:用于管理资源res的工具类
 */
public class ResourcesUtil {

    /**
     * 获取颜色
     *
     * @param colorId the color
     * @return the color
     */
    public static int getColor(int colorId) {
        return getContext().getResources().getColor(colorId);
    }

    /**
     * Gets text size.
     *
     * @param dimenId the id
     * @return the text size
     */
    public static int getTextSize(int dimenId) {
        return getContext().getResources().getDimensionPixelOffset(dimenId);
    }

    /**
     * 获取字符串
     *
     * @param textId the text id
     * @return the string
     */
    public static String getString(int textId) {
        return getContext().getResources().getString(textId);
    }

    /**
     * Gets drawable.
     *
     * @param res the res
     * @return the drawable
     */
    public static Drawable getDrawable(int res) {
        return getContext().getResources().getDrawable(res);
    }

    /**
     * Gets bitmap.
     *
     * @param res the res
     * @return the bitmap
     */
    public static Bitmap getBitmap(int res) {
        return BitmapFactory.decodeResource(getContext().getResources(), res);
    }

    /**
     * 获取颜色
     *
     * @param drawableId the color
     * @return the color darwable
     */
    public static Drawable getColorDarwable(int drawableId) {
        return getContext().getResources().getDrawable(drawableId);
    }

    /**
     * Get string arr string [ ].
     *
     * @param strArrId the str arr id
     * @return the string [ ]
     */
    public static String[] getStringArr(int strArrId) {
        return getContext().getResources().getStringArray(strArrId);
    }

    /**
     * xml转换view
     *
     * @param layoutId the layout id
     * @return xml view
     */
    public static View getXmlView(int layoutId) {
        return View.inflate(getContext(), layoutId, null);
    }

    /**
     * dp与px转换
     * 1dp----0.5px
     * 1dp----1px；
     * 1dp----1.25pd
     * 1dp---1.75px;
     *
     * @param dp the dp
     * @return int int
     */
    public static int dp2px(int dp) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5);
    }

    /**
     * Px 2 dp int.
     *
     * @param px the px
     * @return the int
     */
    public static int px2dp(int px) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (px / density + 0.5);
    }

    /**
     * 获取上下文
     *
     * @return the mContext
     */
    public static Context getContext() {
        return MyApplication.getInstance();
    }

    /**
     * Sp 2 px int.
     *
     * @param spValue the sp value
     * @return the int
     */
    public static int sp2px(float spValue) {
        float fontScale = getContext().getResources().getDisplayMetrics()
                .scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * Px 2 sp int.
     *
     * @param pxValue the px value
     * @return the int
     */
    public static int px2sp(float pxValue) {
        float fontScale = getContext().getResources().getDisplayMetrics()
                .scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * Gets dimen.
     *
     * @param id the id
     * @return the dimen
     */
    public static float getDimen(int id) {
        return getContext().getResources().getDimension(id);
    }

    /**
     * Gets rbg color.
     *
     * @param r the r
     * @param g the g
     * @param b the b
     * @return the rbg color
     */
    public static int getRBGColor(int r, int g, int b) {
        return Color.rgb(r, g, b);
    }

    /**
     * Convert view to bitmap bitmap.
     *
     * @param view the view
     * @return the bitmap
     */
    public static Bitmap convertViewToBitmap(View view) {
        view.setDrawingCacheEnabled(true);
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap cacheBitmap = view.getDrawingCache();
        return Bitmap.createBitmap(cacheBitmap);
    }
}
