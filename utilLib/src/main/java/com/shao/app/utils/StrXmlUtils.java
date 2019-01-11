package com.shao.app.utils;

import android.support.annotation.StringRes;

import com.shao.app.UtilManager;

/**
 * Description:string.xml 转 String 的工具类
 * Company:
 * Author:Zhangshaopeng
 * Email :1377785991@qq.com
 * Data:2018/5/3
 */
public class StrXmlUtils {
    /**
     * @param resId
     * @return
     */
    public static String getString(@StringRes int resId) {
        return UtilManager.getContext().getResources().getString(resId);
    }

    /**
     * @param resId
     * @param formatAr
     * @return
     */
    public static String getString(@StringRes int resId, Object... formatAr) {
        return UtilManager.getContext().getResources().getString(resId, formatAr);
    }

}
