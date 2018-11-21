package com.ybkj.demo.utils;

import android.content.Context;
import android.os.Environment;

import com.ybkj.demo.MyApplication;

import java.io.File;

/**
 * author：rongkui.xiao --2018/6/8
 * email：dovexiaoen@163.com
 * description:
 */

public class FileUtil {

    /**
     * 检查SD卡是否存在
     */
    private static boolean checkSdCard() {

        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取上下文
     */
    public static Context getContext() {
        return MyApplication.getInstance();
    }

    public static String getFileNameFromUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }


    /**
     * 创建文件目录，用于保存文件，便于统一管理,当程序被卸载的时候会自动清除该目录下的所有文件
     *
     * @param filename 文件名
     * @return 文件全路径
     */
    public static String createDir(String dirName, String filename) {
        File externalCacheDir = getContext().getExternalCacheDir();
        File file = new File(externalCacheDir, dirName);
        if (!file.exists()) file.mkdirs();
        // 若不存在，创建目录，可以在应用启动的时候创建
        return file.getAbsolutePath() + "/" + filename;
    }

    public static String getCacheDir() {
        File externalCacheDir = getContext().getExternalCacheDir();
        // 若不存在，创建目录，可以在应用启动的时候创建
        return externalCacheDir != null ? externalCacheDir.getAbsolutePath() : null;
    }

    /**
     * 创建文件目录，用于保存文件，便于统一管理,当程序被卸载的时候会自动清除该目录下的所有文件
     *
     * @param filename 文件名
     * @return 文件全路径
     */
    public static String createSDCardDir(String dirName, String filename) {
        File externalCacheDir = getContext().getFilesDir();
        File file = new File(externalCacheDir, dirName);
        if (!file.exists()) file.mkdirs();
        // 若不存在，创建目录，可以在应用启动的时候创建
        return file.getAbsolutePath() + "/" + filename;
    }
}
