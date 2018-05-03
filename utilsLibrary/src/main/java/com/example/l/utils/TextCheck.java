package com.example.l.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description:文本检查、正则判断等工具类
 * Company:
 * Author:Zhangshaopeng
 * Email :1377785991@qq.com
 * Data:2018/5/3
 */
public class TextCheck {
    /**
     * 手机号校验
     *
     * @param phoneNumber 手机号
     * @return true 是手机号，false 不是。
     */
    public static boolean isPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("[1][34578]\\d{9}");
    }

    /**
     * 邮箱校验
     *
     * @param email 邮箱
     * @return true 是邮箱，false 不是。
     */
    public static boolean isEmail(String email) {
        String rex = "[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?";
        return email.matches(rex);
    }


    /**
     * 密码校验  6-16位的字母或者数字
     *
     * @param password
     * @return
     */
    public static boolean isPassword_1(String password) {

//        String regEx = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
//        return password.matches(regEx);

        String regExA = "[a-zA-Z0-9]{6,16}";
        return password.matches(regExA);//6-16位数字或者字母
    }

    /**
     * 密码校验  6-16位的字母和数字的组合
     *
     * @param password
     * @return
     */
    public static boolean isPassword_2(String password) {

        String regEx = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
        return password.matches(regEx);
    }


    /**
     * 校验金额格式
     *
     * @param str
     * @return
     */
    public static boolean isMoney(String str) {
        String regEx = "^(([1-9]\\d*)|0)(\\.\\d{1,2})?$";
        return Pattern.matches(regEx, str);
    }


    /**
     * 姓名过滤 只能输入中英文
     *
     * @param str
     * @return
     */
    public static String nameFilter(String str) { // 过滤特殊字符

        String regEx = "[^a-z^A-Z^\u4e00-\u9fa5]+";

        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    /**
     * 地址过滤 只能输入中英文、数字、空格
     *
     * @param str
     * @return
     */
    public static String addressFilter(String str) { // 过滤特殊字符

        String regEx = "[^\\s^a-z^A-Z^0-9^\u4e00-\u9fa5]+";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("");
    }

    /**
     * 验证用户名只包含字母，数字，中文
     *
     * @param account
     * @return
     */
    public static boolean checkAccountMark(String account) {
        String all = "^[a-zA-Z0-9\\u4e00-\\u9fa5]+$";
        Pattern pattern = Pattern.compile(all);
        return pattern.matches(all, account);
    }
    /**
     * 昵称过滤 只能输入中英文、数字、
     *
     * @param str
     * @return
     */
    public static String nicknameFilter(String str) { // 过滤特殊字符
        String regEx = "[^a-z^A-Z^0-9^\u4e00-\u9fa5]+";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("");
    }


//    /**
//     *  身份证校验
//     * @param idCard
//     * @return
//     */
//    public static boolean checkIdCard(String idCard) {
//        String regex = "[1-9]\\d{13,16}[a-zA-Z0-9]{1}";
//        return Pattern.matches(regex, idCard);
//    }

    /**
     * 字符是否是中文
     *
     * @param c
     * @return
     */
    public static boolean isChinese(char c) {
        String regEx = "[\\u4e00-\\u9fa5]";
        return Pattern.matches(regEx, String.valueOf(c));
    }


    /**
     * 查询字符数
     *
     * @param str
     * @return
     */
    public static int characterNum(String str) {
        int characterNum = 0;
        for (int i = 0; i < str.length(); i++) {
            // 判断是否有中文
            if (isChinese(str.charAt(i))) {
                characterNum += 2;
            } else {
                characterNum++;
            }
        }
        return characterNum;
    }

}
