package com.shao.utilslibrary.utils;

import android.os.Handler;
import android.os.Looper;

/**
 * Description:线程切换
 * Company:
 * Author:Zhangshaopeng
 * Email :1377785991@qq.com
 * Data:2018/5/24
 */


public class MainLooper extends Handler {
    private static MainLooper instance = new MainLooper(Looper.getMainLooper());

    private MainLooper(Looper looper) {
        super(looper);
    }

    public static MainLooper getInstance() {
        return instance;
    }

    /**
     * 调用此可以执行 ui操作（切换到主线程）
     *
     * @param runnable
     */
    public static void runOnUiThread(Runnable runnable) {
        if (Looper.getMainLooper().equals(Looper.myLooper())) {
            runnable.run();
        } else {
            instance.post(runnable);
        }
    }
}
