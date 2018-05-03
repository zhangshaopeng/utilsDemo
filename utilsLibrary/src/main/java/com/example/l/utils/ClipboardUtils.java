package com.example.l.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import com.example.l.UtilManager;

/**
 * Description:剪切板的一些工具类
 * Company:
 * Author:Zhangshaopeng
 * Email :1377785991@qq.com
 * Data:2018/5/3
 */
public class ClipboardUtils {
    /**
     * 复制文本到剪切板
     *
     * @param text 文本
     */
    public static void copy2Clipboard(String text) {
        ClipboardManager clipboardManager = (ClipboardManager) UtilManager.getContext()
                .getSystemService(Context.CLIPBOARD_SERVICE);
        clipboardManager.setPrimaryClip(ClipData.newPlainText(null, text));
    }

    /**
     * 从剪切板获取文本
     *
     * @return 切板获取文本 ，未获取到时返回""
     */
    public static String getTextFromClipboar() {
        ClipboardManager clipboardManager = (ClipboardManager) UtilManager.getContext()
                .getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboardManager.hasPrimaryClip()) {
            return clipboardManager.getPrimaryClip().getItemAt(0).getText().toString();
        }
        return "";
    }
}
