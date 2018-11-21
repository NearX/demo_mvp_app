package com.ybkj.demo.utils;

import android.graphics.Bitmap;
import android.os.AsyncTask;

/**
 * Created by Yun on 2018/1/9.
 * 压缩图片Task
 */
public class BitmapCompressTask extends AsyncTask<Bitmap, Void, byte[]> {
    private OnCompressFinishListener onCompressFinishListener;

    public void setOnCompressFinishListener(OnCompressFinishListener onCompressFinishListener) {
        this.onCompressFinishListener = onCompressFinishListener;
    }

    @Override
    protected byte[] doInBackground(Bitmap... bitmaps) {
        return BitmapCompressUtil.compress(bitmaps[0]);
    }

    @Override
    protected void onPostExecute(byte[] bytes) {
        super.onPostExecute(bytes);
        if (onCompressFinishListener != null) {
            onCompressFinishListener.onFinish(bytes);
        }
    }

    public interface OnCompressFinishListener {
        void onFinish(byte[] bytes);
    }
}
