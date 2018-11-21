package com.ybkj.demo.utils;

import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * - @Author:  Yi Shan Xiang
 * - @Description:  数字工具类
 * - @Time:  2018/11/21
 * - @Emaill:  380948730@qq.com
 */
public class NumberUtil {
    /**
     * double精度（直接截取，不舍入）
     *
     * @param doubleValue 要处理的值
     * @param accuracy    保留的小数点位数
     * @return 返回精度的double string
     */
    public static String doubleFormat(double doubleValue, int accuracy) {
        BigDecimal bigDecimal = new BigDecimal(doubleValue + "");
        BigDecimal resultValue = bigDecimal.setScale(accuracy, BigDecimal.ROUND_DOWN);
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setGroupingUsed(false); //false则不使用分组方式显示数据。
        numberFormat.setMinimumFractionDigits(0);//设置数值的小数部分允许的最小位数。
        numberFormat.setMaximumFractionDigits(accuracy); // 设置数值的小数部分允许的最大位数。
        numberFormat.setMaximumIntegerDigits(10); //设置数值的整数部分允许的最大位数。
        numberFormat.setMinimumIntegerDigits(1); //设置数值的整数部分允许的最小位数.
        return numberFormat.format(Double.valueOf(resultValue.toString()));
    }

    /**
     * 加法
     *
     * @param var1 the var 1
     * @param var2 the var 2
     * @return double
     */
    public static double add(double var1, double var2) {
        BigDecimal b1 = new BigDecimal(Double.toString(var1));
        BigDecimal b2 = new BigDecimal(Double.toString(var2));
        return b1.add(b2).doubleValue();

    }

    /**
     * 减法
     *
     * @param var1 the var 1
     * @param var2 the var 2
     * @return double
     */
    public static double sub(double var1, double var2) {
        BigDecimal b1 = new BigDecimal(Double.toString(var1));
        BigDecimal b2 = new BigDecimal(Double.toString(var2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 乘法
     *
     * @param var1 the var 1
     * @param var2 the var 2
     * @return double
     */
    public static double mul(double var1, double var2) {
        BigDecimal b1 = new BigDecimal(Double.toString(var1));
        BigDecimal b2 = new BigDecimal(Double.toString(var2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 除法
     *
     * @param v1    the v 1
     * @param v2    the v 2
     * @param scale 精度，到小数点后几位
     * @return double
     */
    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or ");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();

    }


}
