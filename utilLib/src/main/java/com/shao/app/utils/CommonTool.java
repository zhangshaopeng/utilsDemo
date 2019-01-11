package com.shao.app.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.SystemClock;

import com.shao.app.UtilManager;

/**
 * Description:提取共有的公共方法
 * Company:
 * Author:Zhangshaopeng
 * Email :1377785991@qq.com
 * Data:2018/5/22
 */
public class CommonTool {
    /**
     * 两次点击按钮之间的点击间隔不能少于1000毫秒
     */
    private static final int MIN_CLICK_DELAY_TIME = 500;
    private static long lastClickTime = 0L;
    /**
     * 连续点击进入设置
     */
    private static long[] mHits = new long[3];
    private static final int MAX_TIME = 600;

    /**
     * 防(重复点击)止快速点击
     *
     * @return
     */
    public static boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }

    /**
     * n秒点击后操作
     */
    public static void onClick() {
        System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
        mHits[mHits.length - 1] = SystemClock.uptimeMillis();
        if (SystemClock.uptimeMillis() - mHits[0] < MAX_TIME) {
            //  TODO:
        }
    }

    /**
     * 获取系统剪贴板
     *
     * @param content
     */
    public static void copy(String content) {
        // 获取系统剪贴板
        ClipboardManager clipboard = (ClipboardManager) UtilManager.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建一个剪贴数据集，包含一个普通文本数据条目（需要复制的数据）
        ClipData clipData = ClipData.newPlainText(null, content);
        // 复制到剪贴板
        clipboard.setPrimaryClip(clipData);
        ToastTool.show("复制成功");
    }


}
