package com.ybkj.demo.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by Yun on 2018/1/9.
 * 压缩图片工具类
 */
public class BitmapCompressUtil {

    /**
     * 压缩图片
     *
     * @return 压缩至500k以内的图片byte[]
     */
    /*public static byte[] compress(Bitmap bitmap) {
        double maxSize = 500 * 1024 * 1024;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, (int) Math.min(maxSize / bitmap.getByteCount() / 8 * 100, 100),
        stream);
        return stream.toByteArray();
    }*/

    /**
     * 压缩图片
     *
     * @return 压缩至500k以内的图片byte[]
     */
    public static byte[] compress(Bitmap bitmap) {
        if (bitmap == null || bitmap.isRecycled()) {
            LogUtil.i("图片的bitmap为空或被回收，无法压缩，请重新选者");
            return null;
        }
        double maxSize = 500;
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        // scale
        int options = 100;
        // Store the bitmap into output stream(no compress)
        bitmap.compress(Bitmap.CompressFormat.JPEG, options, os);
        // Compress by loop
        while (os.toByteArray().length / 1024 > maxSize) {
            // Clean up os
            os.reset();
            // interval 10
            options -= 10;
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, os);
        }
        byte[] compressBytes = os.toByteArray();
        try {
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return compressBytes;
    }

    /**
     * 根据路径获得突破并压缩返回bitmap用于显示
     *
     * @param reqWidth  要求的图片的像素
     * @param reqHeight 要求的图片的像素
     * @return
     */
    public static Bitmap getSmallBitmap(Bitmap bitMap, int reqWidth, int reqHeight) {
        int width = bitMap.getWidth();
        int height = bitMap.getHeight();
        LogUtil.i("width=" + width + ";height=" + height);
// 计算缩放比例
        float scaleWidth = ((float) reqWidth) / width;
        float scaleHeight = ((float) reqHeight) / height;
// 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
// 得到新的图片
        return Bitmap.createBitmap(bitMap, 0, 0, width, height, matrix, true);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {

        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }
}
