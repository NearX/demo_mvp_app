package com.ybkj.demo.utils;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

/**
 * - @Author:  Yi Shan Xiang
 * - @Description:  读取文件工具类
 * - @Time:  2018/8/2
 * - @Emaill:  380948730@qq.com
 */
public class AssetsUtil {


    /**
     * 从assets下读取文本
     *
     * @param context
     * @param fileName
     * @return
     */
    public static String getTextFromAssets(Context context, String fileName) {
        String result = "";
        InputStream is = null;
        try {
            is = context.getAssets().open(fileName);
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            result = new String(buffer, "utf-8");
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}
