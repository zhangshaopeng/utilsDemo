package com.example.l.utils;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * Description:MD5工具类
 * Company:
 * Author:Zhangshaopeng
 * Email :1377785991@qq.com
 * Data:2018/5/3
 */
public class MD5Utils {
    /**
     * 生成md5
     * @param str
     * @return 返回md5 错误时返回 "".
     */
    public static String getMD5(String str) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
            md5.update(str.getBytes());
            byte[] m = md5.digest();
            return getString(m);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 32位 小写
     *
     * @param hash
     * @return
     */
    private static String getString(byte[] hash) {
        StringBuffer sb = new StringBuffer();
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10)
                sb.append("0");
            sb.append(Integer.toHexString(b & 0xFF));
        }
        return sb.toString();
    }

}
