package com.example.l.utils;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.ColorInt;
/**
 * Description:创建 Shape工具类
 * Company:
 * Author:Zhangshaopeng
 * Email :1377785991@qq.com
 * Data:2018/5/3
 */
public class ShapeFactory {
    /**
     *
     * @param solidColor 填充色
     * @return GradientDrawable
     */
    public static GradientDrawable createShapeDrawable(@ColorInt int solidColor) {
        return createShapeDrawable(solidColor, 0);
    }

    /**
     *
     * @param solidColor 填充色
     * @param cornersRadius 圆角 px
     * @return GradientDrawable
     */
    public static GradientDrawable createShapeDrawable(@ColorInt int solidColor, float cornersRadius) {
        return createShapeDrawable(solidColor, cornersRadius, 0, Color.TRANSPARENT);
    }

    /**
     *
     * @param solidColor 填充色
     * @param cornersRadius 圆角 px
     * @param strokeWidth 描边粗细 px
     * @param strokeColor 描边颜色
     * @return GradientDrawable
     */
    public static GradientDrawable createShapeDrawable(@ColorInt int solidColor, float cornersRadius, int strokeWidth, @ColorInt int strokeColor) {
        return createShapeDrawable(solidColor, cornersRadius, strokeWidth, strokeColor,0,0);
    }

    /**
     *
     * @param solidColor 填充色
     * @param cornersRadius 圆角 px
     * @param strokeWidth 描边粗细 px
     * @param strokeColor 描边颜色
     * @param dashWidth 描边线的宽度 px
     * @param dashGap 描边线间隙的宽度 px
     * @return GradientDrawable
     */
    public static GradientDrawable createShapeDrawable(@ColorInt int solidColor, float cornersRadius, int strokeWidth, @ColorInt int strokeColor, float dashWidth, float dashGap) {
        return createShapeDrawable(solidColor, cornersRadius, strokeWidth, strokeColor,dashWidth,dashGap,GradientDrawable.Orientation.TOP_BOTTOM,null);
    }


    /**
     *
     * @param solidColor 填充色
     * @param cornersRadius 圆角 px
     * @param strokeWidth 描边粗细 px
     * @param strokeColor 描边颜色
     * @param dashWidth 描边线的宽度 px
     * @param dashGap 描边线间隙的宽度 px
     * @param orientation 渐变方向
     * @param colors 渐变颜色
     * @return GradientDrawable
     */
    public static GradientDrawable createShapeDrawable(@ColorInt int solidColor, float cornersRadius, int strokeWidth, @ColorInt int strokeColor,float dashWidth, float dashGap, GradientDrawable.Orientation orientation, @ColorInt int[] colors) {
        GradientDrawable gradientDrawable = new GradientDrawable(orientation, colors);
        gradientDrawable.setColor(solidColor);
        gradientDrawable.setCornerRadius(cornersRadius);
        gradientDrawable.setStroke(strokeWidth, strokeColor,dashWidth,dashGap);
        return gradientDrawable;
    }

}
