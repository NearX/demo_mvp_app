package com.ybkj.demo.utils;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

/**
 * - @Author:  Yi Shan Xiang
 * - @Description:  生成二维码工具类
 * - @Time:  2018/8/24
 * - @Emaill:  380948730@qq.com
 */

public class ZXingUtils {

    public Bitmap createBitmap(String str, int WIDTH, int HEIGHT) {
        MultiFormatWriter formatWriter = new MultiFormatWriter();
        try {
            // 按照指定的宽度，高度和附加参数对字符串进行编码
            BitMatrix bitMatrix = formatWriter.encode(str, BarcodeFormat.QR_CODE, WIDTH, HEIGHT/*, hints*/);
            Bitmap bitmap = bitMatrix2Bitmap(bitMatrix);
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Bitmap bitMatrix2Bitmap(BitMatrix matrix) {
        int w = matrix.getWidth();
        int h = matrix.getHeight();
        int[] rawData = new int[w * h];
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                int color = Color.WHITE;
                if (matrix.get(i, j)) {
                    color = Color.BLACK;
                }
                rawData[i + (j * w)] = color;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565);
        bitmap.setPixels(rawData, 0, w, 0, 0, w, h);
        return bitmap;

    }
}
