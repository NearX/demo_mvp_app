package com.ybkj.demo.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * - @Author:  Yi Shan Xiang
 * - @Description:  所有需要关闭资源的公共方法，
 * 需要关闭的资源必须是要实现了Closeable抽象类的
 * - @Time:  2019/1/29
 * - @Emaill:  380948730@qq.com
 */
public class CloseUtil {

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
