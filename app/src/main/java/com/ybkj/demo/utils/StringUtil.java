package com.ybkj.demo.utils;

import android.text.InputFilter;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串操作
 *
 * @author gql
 */
public class StringUtil {

    /**
     * 字符串空判断
     * 属于空的：（NULL，‘’， ‘null’,'NULL'）
     *
     * @param paramStr 待判断的字符串
     * @return true ：空，false：非空
     */
    public static boolean isNull(String paramStr) {
        return !isNotNull(paramStr);
    }

    /**
     * 字符串非空判断
     * 属于空的：（NULL，‘’， ‘null’,'NULL'）
     *
     * @param paramStr 待判断的字符串
     * @return true ：非空，false：空
     */
    public static boolean isNotNull(String paramStr) {
        if (paramStr == null) {
            return false;
        }
        if (paramStr.isEmpty()) {
            return false;
        }
        paramStr = paramStr.trim();
        if (paramStr.equals("")) {
            return false;
        }
        if (paramStr.equals("null")) {
            return false;
        }
        if (paramStr.equals("NULL")) {
            return false;
        }

        return true;
    }

    /**
     * 对字符串进行处理，把'null'、'NULL'处理成''空字符串，非空的字符串会执行trim
     *
     * @param paramStr 待处理的字符串
     * @return 处理后的字符串 string
     */
    public static String stringNullHandle(String paramStr) {
        if (isNotNull(paramStr)) {
            return paramStr.trim();
        } else {
            return "";
        }
    }

    /**
     * 链地址，链地址中间4位星号替换
     *
     * @param chainAddress the chain address
     * @return the string
     */
    public static String formatChainAddress(String chainAddress) {
        return chainAddress.substring(0, 6) + "****" + chainAddress.substring(chainAddress.length() - 4, chainAddress
                .length());
//        return chainAddress.replaceAll("(\\d{6})(\\d{4})", "$1****$2");
    }

    /**
     * 链地址，链地址中间4位星号替换
     *
     * @param phoneNum the phone num
     * @return the string
     */
    public static String formatPhoneNum(String phoneNum) {
        return phoneNum.substring(0, 3) + "***" + phoneNum.substring(phoneNum.length() - 3, phoneNum.length());
//        return chainAddress.replaceAll("(\\d{6})(\\d{4})", "$1****$2");
    }

    /**
     * 禁止EditText输入特殊字符
     *
     * @param editText the edit text
     * @param length   字符长度
     */
    public static void setEditTextInput(EditText editText, int length) {
        InputFilter filter = new InputFilter() {
            /** * @param source 输入的文字
             * * @param start 输入-0，删除-0
             * * @param end 输入-文字的长度，删除-0
             * * @param dest 原先显示的内容
             ** @param dstart 输入-原光标位置，删除-光标删除结束位置
             * * @param dend 输入-原光标位置，删除-光标删除开始位置
             * */
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String patternString = "[\uD83C\uDC04-\uD83C\uDE1A]|[\uD83D\uDC66-\uD83D\uDC69]" +
                        "|[\uD83D\uDC66\uD83C\uDFFB-\uD83D\uDC69\uD83C\uDFFF]" +
                        "|[\uD83D\uDE45\uD83C\uDFFB-\uD83D\uDE4F\uD83C\uDFFF]" +
                        "|[\uD83C\uDC00-\uD83D\uDFFF]|[\uD83E\uDD10-\uD83E\uDDC0]" +
                        "|[\uD83D\uDE00-\uD83D\uDE4F]|[\uD83D\uDE80-\uD83D\uDEF6]" +
                        "|[`~!$%^()=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？_÷\"×→←-]";
                Pattern pattern = Pattern.compile(patternString);
                Matcher matcher = pattern.matcher(source.toString());
                if (matcher.find()) return "";
                return null;
            }
        };
        editText.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(length)});
    }

    /**
     * 禁止EditText输入特殊字符,可以有标点符号
     *
     * @param editText the edit text
     * @param length   字符长度
     */
    public static void setEditTextInput2(EditText editText, int length) {
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String patternString = "[\uD83C\uDC04-\uD83C\uDE1A]|[\uD83D\uDC66-\uD83D\uDC69]" +
                        "|[\uD83D\uDC66\uD83C\uDFFB-\uD83D\uDC69\uD83C\uDFFF]" +
                        "|[\uD83D\uDE45\uD83C\uDFFB-\uD83D\uDE4F\uD83C\uDFFF]" +
                        "|[\uD83C\uDC00-\uD83D\uDFFF]|[\uD83E\uDD10-\uD83E\uDDC0]" +
                        "|[\uD83D\uDE00-\uD83D\uDE4F]|[\uD83D\uDE80-\uD83D\uDEF6]";
                Pattern pattern = Pattern.compile(patternString);
                Matcher matcher = pattern.matcher(source.toString());
                if (matcher.find()) return "";
                return null;
            }
        };
        editText.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(length)});
    }

    /**
     * 用于格式化金额的小数点后字体大小不同
     *
     * @param s     the s
     * @param size1 the size 1
     * @param size2 the size 2
     * @return the spannable text
     */
    public static SpannableStringBuilder setSpannableText(String s, int size1, int size2) {
        //SpannableStringBuilder实现CharSequence接口
        if (TextUtils.isEmpty(s)) {
            return null;
        }
        SpannableStringBuilder builder = new SpannableStringBuilder("");
        builder.append(s);

        int indexOfPoint = s.indexOf(".");
        builder.setSpan(new AbsoluteSizeSpan(ResourcesUtil.getTextSize(size1)), 0,
                indexOfPoint, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new AbsoluteSizeSpan(ResourcesUtil.getTextSize(size2)), indexOfPoint,
                s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return builder;
    }


    /**
     * Append spannable text spannable string builder.
     *
     * @param stringBuilder the string builder
     * @param s             the s
     * @param size          the size
     * @return the spannable string builder
     */
    public static SpannableStringBuilder appendSpannableText(SpannableStringBuilder stringBuilder, String s, int size) {
        if (TextUtils.isEmpty(s) || stringBuilder == null) {
            return stringBuilder;
        }
        int length = stringBuilder.length();
        stringBuilder.append(" ").append(s);
        stringBuilder.setSpan(new AbsoluteSizeSpan(ResourcesUtil.getTextSize(size)), length,
                stringBuilder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return stringBuilder;
    }
}
