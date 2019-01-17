package com.ybkj.demo.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.ImageView;

import com.ybkj.demo.R;
import com.ybkj.demo.base.GlideApp;

/**
 * Description 图片加载管理类工具
 * Author Ren Xingzhi
 * Created on 2019/1/17.
 * Email 15384030400@163.com
 */
public class ImageLoadUtils {
    /**
     * 加载网络图片
     * @param mContext 上下文
     * @param url 图片URL
     * @param imageView 目标控件
     */
    public static void loadUrlImage(Context mContext, String url, ImageView imageView) {
        if (TextUtils.isEmpty(url)) {
            imageView.setImageResource(R.mipmap.img_default);
            return;
        }
        GlideApp.with(mContext)
                .load(url)
                .placeholder(R.mipmap.img_default)
                .error(R.mipmap.img_default)
                .into(imageView);
    }

    /**
     * 加载bitmap图片
     * @param mContext 上下文
     * @param bitmap bitmap
     * @param imageView 目标控件
     */
    public static void loadBitmapImage(Context mContext, Bitmap bitmap, ImageView imageView) {
        if (bitmap == null || bitmap.isRecycled()) {
            imageView.setImageResource(R.mipmap.img_default);
            return;
        }
        GlideApp.with(mContext)
                .load(bitmap)
                .placeholder(R.mipmap.img_default)
                .error(R.mipmap.img_default)
                .into(imageView);
    }
    /**
     * 加载字节数组图片
     * @param mContext 上下文
     * @param bytes bytes
     * @param imageView 目标控件
     */
    public static void loadBytesImage(Context mContext, byte[] bytes, ImageView imageView) {
        if (bytes == null || bytes.length==0) {
            imageView.setImageResource(R.mipmap.img_default);
            return;
        }
        GlideApp.with(mContext)
                .load(bytes)
                .placeholder(R.mipmap.img_default)
                .error(R.mipmap.img_default)
                .into(imageView);
    }
    /**
     * 加载本地图片
     * @param mContext 上下文
     * @param path 图片路径
     * @param imageView 目标控件
     */
    public static void loadLocalImage(Context mContext, String  path, ImageView imageView){
        if (TextUtils.isEmpty(path)){
            imageView.setImageResource(R.mipmap.img_default);
            return;
        }
        GlideApp.with(mContext)
                .load("file://" + path)
                .placeholder(R.mipmap.img_default)
                .error(R.mipmap.img_default)
                .into(imageView);
    }
    /**
     * 加载网络圆形图片
     * @param mContext 上下文
     * @param url 图片URL
     * @param imageView 目标控件
     */
    public static void loadCircleUrlImage(Context mContext, String url, ImageView imageView) {
        if (TextUtils.isEmpty(url)) {
            imageView.setImageResource(R.mipmap.img_default);
            return;
        }
        GlideApp.with(mContext)
                .load(url)
                .placeholder(R.mipmap.img_default)
                .error(R.mipmap.img_default)
                .transform(new GlideCircleTransform(mContext))
                .into(imageView);
    }

    /**
     * 加载bitmap圆形图片
     * @param mContext 上下文
     * @param bitmap bitmap
     * @param imageView 目标控件
     */
    public static void loadCircleBitmapImage(Context mContext, Bitmap bitmap, ImageView imageView) {
        if (bitmap == null || bitmap.isRecycled()) {
            imageView.setImageResource(R.mipmap.img_default);
            return;
        }
        GlideApp.with(mContext)
                .load(bitmap)
                .placeholder(R.mipmap.img_default)
                .error(R.mipmap.img_default)
                .transform(new GlideCircleTransform(mContext))
                .into(imageView);
    }
    /**
     * 加载字节数组圆形图片
     * @param mContext 上下文
     * @param bytes bytes
     * @param imageView 目标控件
     */
    public static void loadCircleBytesImage(Context mContext, byte[] bytes, ImageView imageView) {
        if (bytes == null || bytes.length==0) {
            imageView.setImageResource(R.mipmap.img_default);
            return;
        }
        GlideApp.with(mContext)
                .load(bytes)
                .placeholder(R.mipmap.img_default)
                .error(R.mipmap.img_default)
                .transform(new GlideCircleTransform(mContext))
                .into(imageView);
    }
    /**
     * 加载本地圆形图片
     * @param mContext 上下文
     * @param path 图片路径
     * @param imageView 目标控件
     */
    public static void loadCircleLocalImage(Context mContext, String  path, ImageView imageView){
        if (TextUtils.isEmpty(path)){
            imageView.setImageResource(R.mipmap.img_default);
            return;
        }
        GlideApp.with(mContext)
                .load("file://" + path)
                .placeholder(R.mipmap.img_default)
                .error(R.mipmap.img_default)
                .transform(new GlideCircleTransform(mContext))
                .into(imageView);
    }
}
