package com.shao.app.utils;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.shao.app.UtilManager;
import com.shao.app.glide.GlideCircleTransform;
import com.shao.app.glide.GlideRoundTransform;

/**
 * Description:图片加载工具类
 * Company:
 * Author:Zhangshaopeng
 * Email :1377785991@qq.com
 * Data:2018/5/3
 */
public class ImageTool {

    /**
     * 加载资源图
     *
     * @param context
     * @param resId
     * @param imageView
     */
    public static void load(Context context, @DrawableRes int resId, ImageView imageView) {
        Glide.with(context)
                .load(resId)
                .placeholder(R.drawable.shape_default_range_bg)
                .error(R.drawable.shape_default_range_bg)
                .into(imageView);
    }

    /**
     * 加载图片
     *
     * @param imgUrl
     * @param imageView
     */
    public static void load(String imgUrl, ImageView imageView) {
        load(UtilManager.getContext(), imgUrl, imageView);
    }

    /**
     * 加载图片
     *
     * @param context
     * @param imgUrl
     * @param imageView
     */
    public static void load(Context context, String imgUrl, ImageView imageView) {
        load(context, imgUrl, R.drawable.shape_default_range_bg, imageView);
    }

    /**
     * 加载图片
     *
     * @param context
     * @param imgUrl
     * @param placeholder 展位图和错误图
     * @param imageView
     */
    public static void load(Context context, String imgUrl, @DrawableRes int placeholder, ImageView imageView) {
        Glide.with(context)
                .load(imgUrl)
                .placeholder(placeholder)
                .error(placeholder)
                .into(imageView);
    }


    /**
     * 加载圆角图
     *
     * @param imgUrl
     * @param imageView
     * @param radiusDp
     */
    public static void loadRound(String imgUrl, ImageView imageView, int radiusDp) {
        loadRound(UtilManager.getContext(), imgUrl, imageView, radiusDp);
    }


    /**
     * 加载圆角图
     *
     * @param context
     * @param imgUrl
     * @param imageView
     * @param radiusDp
     */
    public static void loadRound(Context context, String imgUrl, ImageView imageView, int radiusDp) {
        loadRound(context, imgUrl, R.drawable.shape_default_round_bg, imageView, radiusDp);
    }

    /**
     * 加载圆角图
     *
     * @param context
     * @param imgUrl
     * @param placeholder
     * @param imageView
     * @param radiusDp
     */
    public static void loadRound(Context context, String imgUrl, @DrawableRes int placeholder, ImageView imageView, int radiusDp) {
        Glide.with(context)
                .load(imgUrl)
                .placeholder(placeholder)
                .error(placeholder)
                .transform(new CenterCrop(context), new GlideRoundTransform(context, radiusDp))
                .into(imageView);
    }


    /**
     * 加载圆图
     *
     * @param imgUrl
     * @param imageView
     */
    public static void loadCircle(String imgUrl, ImageView imageView) {
        loadCircle(UtilManager.getContext(), imgUrl, imageView);
    }

    /**
     * 加载圆图
     *
     * @param context
     * @param imgUrl
     * @param imageView
     */
    public static void loadCircle(Context context, String imgUrl, ImageView imageView) {
        loadCircle(context, imgUrl, R.drawable.shape_default_circle_bg, imageView);
    }

    /**
     * 加载圆图
     *
     * @param context
     * @param imgUrl
     * @param placeholder
     * @param imageView
     */
    public static void loadCircle(Context context, String imgUrl, @DrawableRes int placeholder, ImageView imageView) {
        Glide.with(context)
                .load(imgUrl)
                .error(placeholder)
                .placeholder(placeholder)
                .transform(new GlideCircleTransform(context))
                .into(imageView);
    }
}
