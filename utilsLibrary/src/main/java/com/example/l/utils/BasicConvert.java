package com.example.l.utils;

import java.math.RoundingMode;
import java.text.NumberFormat;

/**
 * Description:java的基本类型转换工具类,主要是加了 try catch ，防止crash.
 * Company:
 * Author:Zhangshaopeng
 * Email :1377785991@qq.com
 * Data:2018/5/3
 */
public class BasicConvert {
    /**
     * str转double
     *
     * @param str 要转换的字符串
     * @return 转换后的double  如果出现异常，则返回  0.00;
     */
    public static double str2double(String str) {
        double d = 0.00;
        try {
            d = Double.parseDouble(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return d;
    }

    /**
     * str转int
     *
     * @param str 要转换的字符串
     * @return str转int  如果出现异常，则返回  0;
     */
    public static int str2int(String str) {
        int i = 0;
        try {
            i = Integer.parseInt(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }


    /**
     * String 转 long
     *
     * @param str 要转换的字符串
     * @return long  如果出现异常，则返回  0;
     */
    public static long str2long(String str) {
        try {
            return Long.parseLong(str);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }


    /**
     * 将double 转换成百分数 没有小数部分
     *
     * @param d 需要转换的 double
     * @return  转换的的百分数
     */
    public static String formatPercent(double d) {
        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMinimumFractionDigits(0);//设置保留小数位
        nf.setRoundingMode(RoundingMode.HALF_UP); //设置舍入模式
        return nf.format(d);
    }

}
