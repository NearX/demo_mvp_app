package com.ybkj.demo.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;

import com.ybkj.demo.SampleApplicationLike;

import java.io.File;
import java.io.IOException;

import static android.os.Environment.MEDIA_MOUNTED;

/**
 * 文件操作工具类
 */
public class FileUtil {
    private static final String TAG = "FileUtil";
    private static final String EXTERNAL_STORAGE_PERMISSION = "android.permission.WRITE_EXTERNAL_STORAGE";


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
        return SampleApplicationLike.getInstance();
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


    /**
     * Returns application cache directory. Cache directory will be created on SD card
     * ("/Android/data/[app_package_name]/cache") if card is mounted and app has appropriate permission. Else -
     * Android defines cache directory on device's file system.
     */
    public static File getCacheDirectory(Context context) {
        File appCacheDir = null;
        if (MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) && hasExternalStoragePermission(context)) {
            appCacheDir = getExternalCacheDir(context);
        }
        if (appCacheDir == null) {
            appCacheDir = context.getCacheDir();
        }
        if (appCacheDir == null) {
            Log.w(TAG, "Can't define system cache directory! The app should be re-installed.");
        }
        return appCacheDir;
    }

    private static File getExternalCacheDir(Context context) {
        File dataDir = new File(new File(Environment.getExternalStorageDirectory(), "Android"), "data");
        File appCacheDir = new File(new File(dataDir, context.getPackageName()), "cache");
        if (!appCacheDir.exists()) {
            if (!appCacheDir.mkdirs()) {
                Log.w(TAG, "Unable to create external cache directory");
                return null;
            }
            try {
                new File(appCacheDir, ".nomedia").createNewFile();
            } catch (IOException e) {
                Log.i(TAG, "Can't create \".nomedia\" file in application external cache directory");
            }
        }
        return appCacheDir;
    }

    /**
     * 是否有外部存储权限
     *
     * @param context
     * @return
     */
    private static boolean hasExternalStoragePermission(Context context) {
        int perm = context.checkCallingOrSelfPermission(EXTERNAL_STORAGE_PERMISSION);
        return perm == PackageManager.PERMISSION_GRANTED;
    }
}
