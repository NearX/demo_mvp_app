package com.ybkj.demo.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.ybkj.demo.BuildConfig;
import com.ybkj.demo.R;
import com.ybkj.demo.bean.ProgressMessage;
import com.ybkj.demo.utils.MD5Util;
import com.ybkj.demo.utils.StorageUtils;
import com.ybkj.demo.utils.StringUtil;
import com.ybkj.demo.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * APP下载安装服务
 */
public class ApkDownInstallService extends IntentService {

    private static final int BUFFER_SIZE = 10 * 1024; //缓存大小
    private static final String TAG = "DownloadService";

    private static final int NOTIFICATION_ID = 0;
    private NotificationManager mNotifyManager;
    /**
     * 是否显示下载更新通知
     */
    private static final boolean SHOW_NOTIFY = false;
    private String md5Str;//后台返回的Md5
    private String urlStr;//下载地址
    private int downTimer = 0;//下载次数
    private File apkFile;
    private Notification.Builder mBuilder;
    private NotificationChannel channel;
    private String eventTag = "";

    public ApkDownInstallService() {
        super("DownloadApkService");
    }

    /**
     * 在onHandleIntent中下载apk文件
     *
     * @param intent
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        urlStr = intent.getStringExtra(ProgressMessage.UPDATE_URL);
        md5Str = intent.getStringExtra(ProgressMessage.UPDATE_MD5);
        eventTag = intent.getStringExtra(ProgressMessage.UPDATE_TAG);
        EventBus.getDefault().post(new ProgressMessage(eventTag, ProgressMessage.SHOW, 0));
        if (SHOW_NOTIFY) {
            //初始化通知，用于显示下载进度
            mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                channel = new NotificationChannel("1", "普通通知", NotificationManager.IMPORTANCE_DEFAULT);
                channel.enableVibration(false);
                channel.setSound(null, null);
                mNotifyManager.createNotificationChannel(channel);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mBuilder = new Notification.Builder(this, "1");
            } else {
                mBuilder = new Notification.Builder(this);
            }
            String appName = getString(getApplicationInfo().labelRes);
            mBuilder.setContentTitle(appName).setSmallIcon(R.mipmap.ic_launcher);
        }
        downLoadApk(urlStr);

    }

    private void downLoadApk(String urlStr) {
        InputStream in = null;
        FileOutputStream out = null;
        try {
            //建立下载连接
            URL url = new URL(urlStr);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(false);
            urlConnection.setConnectTimeout(10 * 1000);
            urlConnection.setReadTimeout(10 * 1000);
            urlConnection.setRequestProperty("Connection", "Keep-Alive");
            urlConnection.setRequestProperty("Charset", "UTF-8");
            urlConnection.setRequestProperty("Accept-Encoding", "gzip, deflate");
            urlConnection.connect();

            Log.e(TAG, "downLoadApk: " + Thread.currentThread().getName());
            //以文件流读取数据
            long bytetotal = urlConnection.getContentLength(); //取得文件长度
            long bytesum = 0;
            int byteread = 0;
            in = urlConnection.getInputStream();
            File dir = StorageUtils.getCacheDirectory(this); //取得应用缓存目录
            String apkName = this.urlStr.substring(this.urlStr.lastIndexOf("/") + 1, this.urlStr.length());//取得apK文件名
            apkFile = new File(dir, apkName);
            out = new FileOutputStream(apkFile);
            byte[] buffer = new byte[BUFFER_SIZE];
            int limit = 0;
            int oldProgress = 0;
            while ((byteread = in.read(buffer)) != -1) {
                bytesum += byteread;
                out.write(buffer, 0, byteread);
                int progress = (int) (bytesum * 100L / bytetotal);
                // 如果进度与之前进度相等，则不更新，如果更新太频繁，则会造成界面卡顿
                if (progress != oldProgress) {
                    updateProgress(progress);
                }
                oldProgress = progress;
            }
            out.flush();
            urlConnection.disconnect();
            // 下载完成,调用installAPK开始安装文件
            installAPk(apkFile);
        } catch (Exception e) {
            Log.e(TAG, "download apk file error");
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ignored) {

                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ignored) {

                }
            }
        }

    }

    /**
     * 实时更新下载进度条显示
     *
     * @param progress
     */
    private void updateProgress(int progress) {
        if (SHOW_NOTIFY) {
            mBuilder.setContentText("正在下载:" + progress + "%").setProgress(100, progress, false);
            PendingIntent pendingintent = PendingIntent.getActivity(this, 0, new Intent(), PendingIntent.FLAG_CANCEL_CURRENT);
            mBuilder.setContentIntent(pendingintent);
            mNotifyManager.notify(NOTIFICATION_ID, mBuilder.build());
        } else {
            EventBus.getDefault().post(new ProgressMessage(eventTag, ProgressMessage.UPDATE, progress));
        }
    }


    /**
     * 调用系统安装程序安装下载好的apk
     *
     * @param apkFile
     */
    private void installAPk(File apkFile) {
        compareMd5(md5Str, MD5Util.getSignMd5Str(apkFile), apkFile);
    }

    /**
     * 比较两次MD5
     *
     * @param md5Str     服务器返回的MD5
     * @param signMd5Str 获取下载apk的MD5
     * @param apkFile
     */
    private void compareMd5(String md5Str, String signMd5Str, File apkFile) {

        if (StringUtil.isNotNull(md5Str) && StringUtil.isNotNull(signMd5Str)) {
            Log.e("apk的Md5值", md5Str + "   " + signMd5Str);
            if (md5Str.equals(signMd5Str)) {
                if (SHOW_NOTIFY) {
                    mNotifyManager.cancel(NOTIFICATION_ID);
                }
                EventBus.getDefault().post(new ProgressMessage(eventTag, ProgressMessage.COMPLETE, 100));
                Intent intent = new Intent(Intent.ACTION_VIEW);
                //如果没有设置SDCard写权限，或者没有sdcard,apk文件保存在内存中，需要授予权限才能安装
                try {
                    String[] command = {"chmod", "777", apkFile.toString()}; //777代表权限 rwxrwxrwx
                    ProcessBuilder builder = new ProcessBuilder(command);
                    builder.start();
                } catch (IOException ignored) {
                }
                Uri fileUri;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    fileUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileProvider", apkFile);//通过FileProvider创建一个content类型的Uri
                } else {
                    fileUri = Uri.fromFile(apkFile);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
                }
                intent.setDataAndType(fileUri, "application/vnd.android.package-archive");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } else {
                downTimer++;
                if (downTimer < 3) {
                    if (apkFile.exists()) {
                        apkFile.delete();
                    }
                    downLoadApk(urlStr);
                } else {
                    if (SHOW_NOTIFY) {
                        mNotifyManager.cancel(NOTIFICATION_ID);
                    }
                    ToastUtil.showShort("服务器安装包出现异常，下载失败");
                    EventBus.getDefault().post(new ProgressMessage(eventTag, ProgressMessage.COMPLETE, 100));
                    return;
                }
            }
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "下载Service停止");
    }
}