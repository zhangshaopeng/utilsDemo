package com.shao.app;

import android.content.Context;

/**
 * Description:
 * Company:
 * Author:Zhangshaopeng
 * Email :1377785991@qq.com
 * Data:2018/5/3
 */
public class UtilManager {
    private static Context mContext;
    private static boolean isOpend = true;
    /**
     * 初始化工具类集合
     *
     * @param context context
     */
    public static void init(Context context) {
        mContext = context.getApplicationContext();
    }

    /**
     * 初始化工具类集合  控制日志开关
     *
     * @param context context
     */
    public static void initLog(Context context, boolean isOpen) {
        mContext = context.getApplicationContext();
        isOpend = isOpen;
    }

    public static Context getContext() {
        return mContext;
    }


    public static boolean getIsLogger() {
        return isOpend;
    }
}
