package com.ybkj.demo.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期转换
 *
 * @author gql
 */
public class DateUtil {

    /**
     * 时间字符串格式：yyyy-MM-dd HH:mm:ss.SSS
     */
    public static final String dateFormat1 = "yyyy-MM-dd HH:mm:ss.SSS";

    /**
     * 时间字符串格式：yyyy-MM-dd HH:mm:ss
     */
    public static final String dateFormat2 = "yyyy-MM-dd HH:mm:ss";

    /**
     * 时间字符串格式：yyyy-MM-dd HH:mm
     */
    public static final String dateFormat3 = "yyyy-MM-dd HH:mm";

    /**
     * 时间字符串格式：yyyy-MM-dd
     */
    public static final String dateFormat4 = "yyyy-MM-dd";

    /**
     * 时间字符串格式：HH:mm:ss
     */
    public static final String dateFormat5 = "HH:mm:ss";

    /**
     * 时间字符串格式：yyyyMMddHHmmss
     */
    public static final String dateFormat6 = "yyyyMMddHHmmss";

    /**
     * 时间字符串格式：yyyyMMddHHmm
     */
    public static final String dateFormat7 = "yyyyMMddHHmm";

    /**
     * 时间字符串格式：yyMMddHHmmss
     */
    public static final String dateFormat8 = "yyMMddHHmmss";

    /**
     * 时间字符串格式：yyMMddHHmm
     */
    public static final String dateFormat9 = "yyMMddHHmm";

    /**
     * 时间字符串格式：yyMMdd
     */
    public static final String dateFormat10 = "yyMMdd";

    /**
     * 时间字符串格式：yyyyMMdd
     */
    public static final String dateFormat11 = "yyyyMMdd";

    /**
     * 时间字符串格式：MM:dd
     */
    public static final String dateFormat12 = "MM:dd";

    /**
     * 获取当前时间的时间戳毫秒数
     * 推荐此种方法，执行速度快
     *
     * @return 时间毫秒数
     */
    public static long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

    /**
     * 获取当前时间的时间戳
     * 推荐此种方法，执行速度快
     *
     * @return 时间戳
     */
    public static Timestamp getCurrentTime() {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * 获取当天凌晨的时间戳毫秒数
     *
     * @return 时间毫秒数
     */
    public static long lingchenLong() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String dataStr = formatter.format(date);

        long dateLong = 0;
        try {
            Date newDate = formatter.parse(dataStr);
            dateLong = newDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateLong;
    }

    /**
     * 将时间字符串转换时间格式
     *
     * @param timeStr 时间字符串
     * @return 时间戳
     */
    public static Timestamp stringToTimestamp(String timeStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(timeStr);
            Timestamp timestamp = new Timestamp(date.getTime());
            return timestamp;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 将时间戳毫秒数转换字符串
     *
     * @param timeLong   时间戳毫秒数
     * @param dateFormat 时间字符串格式，如：yyyy-MM-dd HH:mm:ss.SSS
     * @return 时间字符串格式的文本
     */
    public static String longToTimeStr(long timeLong, String dateFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        String timeStr = "";
        if (timeLong > 0) {
            Date date = new Date(timeLong);
            timeStr = formatter.format(date);
        }
        return timeStr;
    }

    /**
     * 将时间字符串转换成时间戳毫秒数
     *
     * @param timeStr 时间字符串，格式：yyyy-MM-dd HH:mm:ss
     * @return 时间戳毫秒数
     */
    public static long timeStrToLong(String timeStr) {
        if (StringUtil.isNotNull(timeStr)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            Date date = null;
            try {
                date = sdf.parse(timeStr);
                long timeL = date.getTime();
                return timeL;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return 0;
    }

    /**
     * 将时间戳毫秒数转换时间戳格式
     *
     * @param timeLong 时间戳毫秒数
     * @return 时间戳
     */
    public static Timestamp longToTimestamp(long timeLong) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date date = new Date(timeLong);
        String dataStr = formatter.format(date);
        Timestamp timestamp = Timestamp.valueOf(dataStr);

        return timestamp;
    }

    /**
     * 将Date对象转换成时间字符串
     *
     * @param time Date类型对象
     * @return 时间字符串
     */
    public static String dateToString(Date time, String dateFormat) {
        if (time == null) {
            return "";
        }
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        String timeString = formatter.format(time);
        return timeString;
    }


    /**
     * 传入秒数，返回格式化后的时间
     *
     * @param time
     * @return
     */
    public static String timeToString(long time) {
        time = time / 1000;
        String stringHour = "0";
        String stringMinutes = "0";
        String stringSecond = "0";
        long hour = time / 60 / 60;
        if (hour != 0 && hour < 10) {
            stringHour = "0" + hour;
        } else {
            stringHour = hour + "";
        }

        long minutes = (time % (60 * 60)) / 60;
        if (minutes < 10) {
            if (minutes == 0) {
                stringMinutes = "00";
            } else {
                stringMinutes = "0" + minutes;
            }
        } else {
            stringMinutes = minutes + "";
        }
        long second = (time % (60 * 60)) % 60;
        if (second < 10) {
            if (second == 0) {
                stringSecond = "00";
            } else {
                stringSecond = "0" + second;
            }
        } else {
            stringSecond = second + "";
        }
        return stringHour + ":" + stringMinutes + ":" + stringSecond;
    }

    /**
     * 转换时间字符串
     *
     * @param dateStr
     * @return
     */
    public static String getTime(String dateStr) {
        String timeStr = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = format.parse(dateStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            String monthStr;
            if ((month + 1) < 10) {
                monthStr = "0" + (month + 1);
            } else {
                monthStr = (month + 1) + "";
            }
            String dayStr;
            if (day < 0) {
                dayStr = "0" + day;
            }
            dayStr = day + "";
            timeStr = year + "." + monthStr + "." + dayStr;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeStr;
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getTimeStr() {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat3);
        Date curDate = new Date(System.currentTimeMillis());
        String str = formatter.format(curDate);
        return str;
    }


    /**
     * 获取带月日的时间
     *
     * @return
     */
    public static String getDateTime(long time) {
        String timeStr = longToTimeStr(time, dateFormat12);
        String returnStr = null;
        if (timeStr != null) {
            String[] str2 = timeStr.split(":");
            if (str2.length == 2) {
                returnStr = str2[0] + "月" + str2[1] + "日";
            }
        }

        return returnStr;
    }
}