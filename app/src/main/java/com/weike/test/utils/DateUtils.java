package com.weike.test.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DateUtils {

    public static final String DEFAULT_DATE_FORMAT = "yyyy/MM/dd";
    public static final String DEFAULT_DATETIME_FORMAT = "yyyy/MM/dd HH:mm:ss";
    public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";
    public static final String CHINA_DATETIME_FORMAT = "yyyy年MM月dd日 aHH:mm";

    /**
     * 将日期转换为指定格式的字符串
     *
     * @param date   日期对象
     * @param format 目标格式字符串
     * @return 转换后的字符串
     */
    public static String format(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
        return dateFormat.format(date);
    }

    /**
     * 将日期转换为中文格式的字符串
     *
     * @param date   日期对象
     * @param format 目标格式字符串
     * @return 转换后的字符串
     */
    public static String formatChina(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.CHINA);
        return dateFormat.format(date);
    }

    /**
     * 将字符串解析为日期对象
     *
     * @param dateString 字符串日期
     * @param format     字符串日期的格式
     * @return 解析后的日期对象
     * @throws ParseException 如果解析失败则抛出此异常
     */
    public static Date parse(String dateString, String format) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
        return dateFormat.parse(dateString);
    }

    /**
     * 将字符串日期转换为默认格式的日期对象
     *
     * @param dateString 字符串日期
     * @return 转换后的日期对象
     * @throws ParseException 如果解析失败则抛出此异常
     */
    public static Date parse(String dateString) throws ParseException {
        return parse(dateString, DEFAULT_DATE_FORMAT);
    }

    /**
     * 将时间戳转换为中文格式的日期字符串
     *
     * @param timestamp 时间戳
     * @return 转换后的日期字符串
     */
    public static String formatTimestampChina(long timestamp) {
        Date date = new Date(timestamp);
        return formatChina(date, CHINA_DATETIME_FORMAT);
    }

    /**
     * 将时间戳转换为指定格式的日期字符串
     *
     * @param timestamp 时间戳
     * @param format    目标格式字符串
     * @return 转换后的日期字符串
     */
    public static String formatTimestamp(long timestamp, String format) {
        Date date = new Date(timestamp);
        return format(date, format);
    }

    /**
     * 将时间戳转换为默认格式的日期字符串
     *
     * @param timestamp 时间戳
     * @return 转换后的日期字符串
     */
    public static String formatTimestamp(long timestamp) {
        return formatTimestamp(timestamp, DEFAULT_DATETIME_FORMAT);
    }

    /**
     * 时间戳提取并转换为带“上午，下午”的时分格式
     *
     * @param timestamp 时间戳
     * @return 转换后的时间字符串
     */
    public static String hourMin(long timestamp){
        // 使用SimpleDateFormat格式化输出，带上午下午标识和时分
        SimpleDateFormat dateFormat = new SimpleDateFormat("ahh:mm", Locale.CHINA);
        return dateFormat.format(timestamp);
    }

    public static String millis2Time(long millis){
        long totalSeconds = millis / 1000;
        long hours = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;
        return String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
    }

    public static String millisToChinaTime(long millis){
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis) % 60;
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis) % 60;
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        StringBuilder sb = new StringBuilder();

        if (hours > 0) {
            sb.append(hours).append("时");
        }

        if (minutes > 0) {
            sb.append(minutes).append("分");
        }

        if (seconds > 0) {
            sb.append(seconds).append("秒");
        }

        return sb.toString().trim();
    }
}

