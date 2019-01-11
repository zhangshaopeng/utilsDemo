package com.shao.app.utils;

import com.shao.app.UtilManager;

/**
 * Description:屏幕密度工具类
 * Company:
 * Author:Zhangshaopeng
 * Email :1377785991@qq.com
 * Data:2018/5/3
 */
public class DensityUtils {
    /**
     * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
     * @param dpValue  dip
     * @return px
     */
    public static int dip2px( float dpValue) {
        final float scale = UtilManager.getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp(dip)
     * @param pxValue px
     * @return dp
     */
    public static int px2dip( float pxValue) {
        final float scale = UtilManager.getContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将px值转换为 sp 值，保证文字大小不变
     * @param pxValue px
     * @return sp
     */
    public static int px2sp(float pxValue) {
        final float scaledDensity = UtilManager.getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / scaledDensity + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue sp值
     * @return px
     */
    public static int sp2px(float spValue) {
        final float scaledDensity = UtilManager.getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * scaledDensity + 0.5f);
    }
}
