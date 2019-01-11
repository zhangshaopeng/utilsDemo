package com.shaopeng.utilsdemo;

import android.app.Application;

import com.shao.app.UtilManager;

/**
 * Description:
 * Company:
 * Author:Zhangshaopeng
 * Email :1377785991@qq.com
 * Data:2018/5/3
 */
public class APP extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        UtilManager.init(this);
    }
}
