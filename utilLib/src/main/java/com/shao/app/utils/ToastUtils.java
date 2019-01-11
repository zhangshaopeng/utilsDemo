package com.shao.app.utils;

import android.support.annotation.StringRes;
import android.view.Gravity;
import android.widget.Toast;

import com.shao.app.UtilManager;

/**
 * Description:吐司工具类
 * Company:
 * Author:Zhangshaopeng
 * Email :1377785991@qq.com
 * Data:2018/5/3
 */
public class ToastUtils {
    private static Toast toast;


    /**
     * Make a standard toast that just contains a text view with the text from a resource.
     *
     * @param resId The resource id of the string resource to use.  Can be formatted tex
     */
    public static void showCenter(@StringRes int resId) {
        showCenter(UtilManager.getContext().getResources().getString(resId));
    }

    /**
     * Make a standard toast that just contains a text view.
     *
     * @param text Toast str
     */
    public static void showCenter(String text) {
        if (toast == null) {
            toast = Toast.makeText(UtilManager.getContext(), "", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
        }
        toast.setText(text);
        toast.show();
    }


    /**
     * Make a standard toast that just contains a text view with the text from a resource.
     *
     * @param resId The resource id of the string resource to use.  Can be formatted tex
     */
    public static void show(@StringRes int resId) {
        show(UtilManager.getContext().getResources().getString(resId));
    }

    /**
     * Make a standard toast that just contains a text view.
     *
     * @param text Toast str
     */
    public static void show(String text) {
        if (toast == null) {
            toast = Toast.makeText(UtilManager.getContext(), "", Toast.LENGTH_SHORT);
        }
        toast.setText(text);
        toast.show();
    }
}
