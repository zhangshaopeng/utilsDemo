package com.example.l;

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

    /**
     * 初始化工具类集合
     * @param context  context
     */
    public static void init(Context context) {
        mContext = context.getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }
}
