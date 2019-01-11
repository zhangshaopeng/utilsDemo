package com.shao.app.utils;

import android.content.Context;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.widget.EditText;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description:字符串颜色工具类
 * Company:
 * Author:Zhangshaopeng
 * Email :1377785991@qq.com
 * Data:2018/5/3
 */
public class TextColorUtils {
    private static String priceRegex = "^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$";

    private static TextColorUtils stringUtils;
    private Context mContext;

    private TextColorUtils() {
    }

    private TextColorUtils(Context context) {
        this.mContext = context;
    }

    public static TextColorUtils getStringUtils(Context context) {
        if (stringUtils == null) {
            stringUtils = new TextColorUtils(context);
        }
        return stringUtils;
    }

    /**
     * 给字体加颜色 橙色 #FF9400
     *
     * @param str
     * @return
     */
    public static String addColorOrange(String str) {
        str = "<font color=\"#FF9400\">" + str + "</font>";
        return str;
    }


    /**
     * 给字体加颜色 蓝色 #44B2F6
     *
     * @param str
     * @return
     */
    public static String addColorBlue(String str) {
        str = "<font color=\"#44B2F6\">" + str + "</font>";
        return str;
    }

    /**
     * 给字体加颜色 灰色 #333333
     *
     * @param str
     * @return
     */
    public static String addColorGray33(String str) {
        str = "<font color=\"#333333\">" + str + "</font>";
        return str;
    }

    /**
     * 设置String.xml里面%S的内容
     *
     * @param resId :资源ID
     * @param str   :第一个%S的内容
     */
    public String getRString(int resId, String str) {
        String msg = String.format(mContext.getResources().getString(resId), str);
        return msg;
    }

    /**
     * 设置String.xml里面%S的内容
     *
     * @param resId :资源ID
     * @param str1  :第一个%S的内容
     * @param str2  :第二个%S的内容
     */
    public String getRString(int resId, String str1, String str2) {
        String msg = String.format(mContext.getResources().getString(resId), str1, str2);
        return msg;
    }

    /**
     * 根据id得到String.xml里面内容
     *
     * @param resId :资源ID
     */
    public String getRString(int resId) {
        String msg = mContext.getResources().getString(resId);
        return msg;
    }

    /**
     * 将输入框的光标放置文字末尾
     */
    public static void setCursorEnd(EditText et) {
        Editable etext = et.getText();
        Selection.setSelection(etext, etext.length());
    }

    public String addTokenToUrl(String url, String token) {
        return Constants.webHost + url + "?token=" + token;
    }

    public static boolean parsePriceStyle(String price) {
        Pattern pattern = Pattern.compile(priceRegex);
        Matcher matcher = pattern.matcher(price);
        return matcher.matches();
    }

    // 截取前n个字符，中文算两个
    public static String getLengthAb(String str, int n) {

        //字符长度
        int length = 0;
        // 确定要截取的位置
        int position = 0;

        StringBuffer result = new StringBuffer();

        char[] c = str.toCharArray();
        for (int i = 0; i < c.length; i++) {
            String len = Integer.toBinaryString(c[i]);
            if (len.length() > 8) {
                length += 2;
            } else {
                length++;
            }
            if (length == n) {
                position = i + 1;
                break;
            } else if (length > n) {
                position = i;
                break;
            }
        }
        if (length < n) {
            return str;
        }
        if (length == n && str.length() == position) {
            return str;
        }
        result.append(str.substring(0, position));
        return result.toString();
    }

    // 截取前n个字符，中文算两个
    public static String getLengthAbChinese(String str, int n) {

        //字符长度
        int length = 0;
        // 确定要截取的位置
        int position = 0;

        StringBuffer result = new StringBuffer();

        char[] c = str.toCharArray();
        for (int i = 0; i < c.length; i++) {
            String len = Integer.toBinaryString(c[i]);
            if (len.length() > 8) {
                length += 2;
            } else {
                length++;
            }
            if (length == n) {
                position = i + 1;
                break;
            } else if (length > n) {
                position = i;
                break;
            }
        }
        if (length < n) {
            return str;
        }
        if (length == n && str.length() == position) {
            return str;
        }
        result.append(str.substring(0, position)).append("...");
        return result.toString();
    }

    // 截取前十个字符，中文算两个
    public String getLengthAbChinese(String str) {

        //字符长度
        int length = 0;
        // 确定要截取的位置
        int position = 0;

        StringBuffer result = new StringBuffer();

        // 如果大于10长度，先截取前10个（这个是长度，不是字符长度）
        if (str.length() > 10) {
            str = str.substring(0, 10);
        }

        char[] c = str.toCharArray();
        for (int i = 0; i < c.length; i++) {
            String len = Integer.toBinaryString(c[i]);
            if (len.length() > 8) {
                length += 2;
            } else {
                length++;
            }
            if (length == 10) {
                position = i + 1;
                break;
            } else if (length > 10) {
                position = i;
                break;
            }
        }
        if (length < 11) {
            return str;
        }
        result.append(str.substring(0, position)).append("......");
        return result.toString();
    }

    public static String parsePriceByDouble(double value) {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(value);
    }

    /**
     * 获取字符串的长度
     */
    public static int getAbStrLength(String msg) {
        int length = 0;
        char[] c = msg.toCharArray();
        for (int i = 0; i < c.length; i++) {
            String len = Integer.toBinaryString(c[i]);
            if (len.length() > 8) {
                length += 2;
            } else {
                length++;
            }
        }
        return length;
    }

    /**
     * 验证用户名只包含字母，中文
     *
     * @param account
     * @return
     */
    public static boolean checkChineseAndLetter(String account) {
        String all = "^[a-zA-Z\\u4e00-\\u9fa5]+$";
        Pattern pattern = Pattern.compile(all);
        return pattern.matches(all, account);
    }

    /**
     * 验证用户名只包含中文
     *
     * @param account
     * @return
     */
    public static boolean checkChinese(String account) {
        String all = "^[\\u4e00-\\u9fa5]+$";
        Pattern pattern = Pattern.compile(all);
        return pattern.matches(all, account);
    }

    /**
     * 判断内容是否为空
     */
    public static boolean isEmptyContent(String msg) {
        String str = msg.replace(" ", "");
        if (str.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * 金额保留2位小数
     */
    public static String getPrice(double price) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(price);
    }

    /**
     * 根据-截取字符串
     */
    public static String getLengthByLine(String string) {
        String url = "";
        if (!TextUtils.isEmpty(string)) {
            String[] split = string.split("-");
            url = split[1];
        }

        return url;
    }

    /**
     * 根据.截取字符串
     */
    public static String getLengthByDots(String string) {
        String url = "";
        if (!TextUtils.isEmpty(string)) {
            String[] split = string.split("\\.");
            url = split[0];
        }

        return url;
    }

    /**
     * 消除空格
     *
     * @param str
     * @return
     */

    public static String getNoSpaceStr(String str) {
        String s = "";
        if (str.contains(" ")) {
            s = str.replace(" ", "");
            return s;
        }
        return str;
    }
    /**
     * 截取字符串
     *
     * @param
     * @return
     */
    public static String getSort(int num, String string) {
        String result = string;
        if (string.length() > 15) {
            result = string.substring(0, num) + "...";
        }
        return result;
    }
}
