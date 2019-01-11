package com.shao.app.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
/**
 * Description:时间的一些格式化
 * Company:
 * Author:Zhangshaopeng
 * Email :1377785991@qq.com
 * Data:2018/5/3
 */
public class DateTool {
    /**
     * @param millisecond 毫秒
     * @return 返回 yy/MM/dd HH:mm
     */
    public static String getYMDHM_1(long millisecond) {
        return getFormatDate("yy/MM/dd HH:mm", millisecond);
    }

    /**
     * @param millisecond 毫秒
     * @return 返回 yy-MM-dd HH:mm
     */
    public static String getYMDHM_2(long millisecond) {
        return getFormatDate("yy-MM-dd HH:mm", millisecond);
    }


    /**
     * @param millisecond 毫秒
     * @return 返回 yy.MM.dd HH:mm
     */
    public static String getYMDHM_3(long millisecond) {
        return getFormatDate("yy.MM.dd HH:mm", millisecond);
    }


    /**
     * 格式化后的日期时间
     *
     * @param pattern
     * @param millisecond 毫秒
     * @return 返回格式化后的日期时间
     */
    public static String getFormatDate(String pattern, long millisecond) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        return dateFormat.format(new Date(millisecond));
    }


    private static final long ONE_MINUTE_MILLISECOND = 60 * 1000;
    private static final long ONE_HOUR_MILLISECOND = 60 * 60 * 1000;
    private static final long ONE_DAY_MILLISECOND = 24 * 60 * 60 * 1000;
    private static final long ONE_MONTH_MILLISECOND = 24 * 60 * 60 * 1000;

    /**
     * 获取友好时间  "刚刚","xx分钟前","xx小时前","xx天前","yyyy-MM-dd"
     *
     * @param millisecond 毫秒
     * @return
     */
    public static String getFriendlyTime(long millisecond) {
        long ago = System.currentTimeMillis() - millisecond;

        if (ago < ONE_MINUTE_MILLISECOND) {
            return "刚刚";
        } else if (ago < ONE_HOUR_MILLISECOND) {
            return (int) (ago / ONE_MINUTE_MILLISECOND) + "分钟前";
        } else if (ago < ONE_DAY_MILLISECOND) {
            return (int) (ago / ONE_HOUR_MILLISECOND) + "小时前";
        } else if (ago < ONE_MONTH_MILLISECOND) {
            return (int) (ago / ONE_DAY_MILLISECOND) + "天前";
        } else {
            return getFormatDate("yyyy-MM-dd", millisecond);
        }
    }



    /**
     * 将时间字符串转为时间戳
     * <p>time格式为pattern</p>
     *
     * @param time    时间字符串
     * @param pattern 时间格式
     * @return 毫秒时间戳
     */
    public static long str2Millis(String time, String pattern) {
        try {
            return new SimpleDateFormat(pattern, Locale.getDefault()).parse(time).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 将时间字符串转为Date类型
     * <p>time格式为pattern</p>
     *
     * @param time    时间字符串
     * @param pattern 时间格式
     * @return Date类型
     */
    public static Date str2Date(String time, String pattern) {
        return new Date(str2Millis(time, pattern));
    }

    /**
     * 将Date类型转为时间字符串
     * <p>格式为pattern</p>
     *
     * @param date    Date类型时间
     * @param pattern 时间格式
     * @return 时间字符串
     */
    public static String date2Str(Date date, String pattern) {
        return new SimpleDateFormat(pattern, Locale.getDefault()).format(date);
    }

    /**
     * 获取手机时间
     *
     * @param flg
     * @return 对应格式时间
     * */
    public static final String getSysTime(String flg) {
        String curTime = "";
        Date currentDate = new Date();
        SimpleDateFormat formatter = null;

        if ("s".equals(flg)) {// 精确到秒
            formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        } else if ("f".equals(flg)) {// 精确到分

            formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        } else if ("d".equals(flg)) {// 精确到天

            formatter = new SimpleDateFormat("yyyy-MM-dd");
        } else if ("m".equals(flg)) {// 精确到月

            formatter = new SimpleDateFormat("yyyy-MM");
        } else if ("Hm".equals(flg)) {// 取 HH:mm

            formatter = new SimpleDateFormat("HH:mm");
        }
        currentDate = Calendar.getInstance().getTime();
        curTime = formatter.format(currentDate);
        return curTime;
    }
    /**
     * @param //int 表示减一天还是加一天
     * @return yyyy-MM-dd
     * */
    public static String getDay(int n, String dateS) {

        Calendar c = Calendar.getInstance();
        c.set(Integer.parseInt(dateS.split("-")[0]),
                Integer.parseInt(dateS.split("-")[1]) - 1,
                Integer.parseInt(dateS.split("-")[2]));
        c.set(Calendar.DATE, c.get(Calendar.DATE) + n);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = c.getTime();
        return sdf.format(date);
    }
    /**
     * @param   //int 间隔的小时数
     * @return yyyy-MM-dd HH:mm
     * */
    public static String getHourString(int n) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR, c.get(Calendar.HOUR) + n);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = c.getTime();
        return sdf.format(date);
    }

    /**
     * @param //int 间隔的小时数
     * @return yyyy-MM-dd HH:mm
     * */
    public static String getHourString(String date, int n) {
        Calendar c = Calendar.getInstance();
        c.set(Integer.parseInt(date.split(" ")[0].split("-")[0]),
                Integer.parseInt(date.split(" ")[0].split("-")[1]) - 1,
                Integer.parseInt(date.split(" ")[0].split("-")[2]),
                Integer.parseInt(date.split(" ")[1].split(":")[0]),
                Integer.parseInt(date.split(" ")[1].split(":")[1]), 0);
        c.set(Calendar.HOUR, c.get(Calendar.HOUR) + n);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(c.getTime());
    }

    /**
     * @param //int 间隔的小时数
     * @return yyyy-MM-dd HH:mm
     * */
    public static String getBlankHours(String date, int n) {
        Calendar c = Calendar.getInstance();
        c.set(Integer.parseInt(date.split(" ")[0].split("-")[0]),
                Integer.parseInt(date.split(" ")[0].split("-")[1]) - 1,
                Integer.parseInt(date.split(" ")[0].split("-")[2]),
                Integer.parseInt(date.split(" ")[1].split(":")[0]),
                Integer.parseInt(date.split(" ")[1].split(":")[1]), 0);
        c.set(Calendar.HOUR, c.get(Calendar.HOUR) + n);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(c.getTime());
    }

    /**
     * 获取星期
     *
     * @param dateS
     *            日期
     * @return 星期*
     * */
    public static String getWeek(String dateS) {

        String weekStr = "";
        int year = Integer.parseInt(dateS.split("-")[0]), month = Integer
                .parseInt(dateS.split("-")[1]), day = Integer.parseInt(dateS
                .split("-")[2]);
        boolean leap = year % 400 == 0 || year % 4 == 0 && year % 100 != 0;
        int total = year - 1980 + (year - 1980 + 3) / 4;
        // 若Month==12，直接求前11个月总天数。
        // 若month==1，直接加上day 所以switch循环条件为month-1
        switch (month - 1) {
            case 11:
                total += 30;
            case 10:
                total += 31;
            case 9:
                total += 30;
            case 8:
                total += 31;
            case 7:
                total += 31;
            case 6:
                total += 30;
            case 5:
                total += 31;
            case 4:
                total += 30;
            case 3:
                total += 31;
            case 2:
                total += leap ? 29 : 28;
            case 1:
                total += 31;
            case 0:
                total += day;
        }
        int week = 1;
        // week=(week+total)%7;
        week = total % 7;

        switch (week) {
            case 0:
                weekStr = "星期一";
                break;
            case 1:
                weekStr = "星期二";
                break;
            case 2:
                weekStr = "星期三";
                break;
            case 3:
                weekStr = "星期四";
                break;
            case 4:
                weekStr = "星期五";
                break;
            case 5:
                weekStr = "星期六";
                break;
            case 6:
                weekStr = "星期日";
                break;

        }

        return weekStr;
    }

}
