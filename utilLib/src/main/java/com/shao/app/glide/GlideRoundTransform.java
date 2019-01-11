package com.shao.app.glide;

import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;

/**
 * Description:圆形图片
 * Company:
 * Author:Zhangshaopeng
 * Email :1377785991@qq.com
 * Data:2018/5/3
 */
public class GlideRoundTransform extends BitmapTransformation {
    private  float radius = 0f;

    public GlideRoundTransform(Context context) {
        this(context, 5);
    }

    public GlideRoundTransform(Context context, int dp) {
        super(context);
        this.radius= context.getResources().getDisplayMetrics().density*dp;
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return roundCrop(pool, toTransform);
    }

    private  Bitmap roundCrop(BitmapPool pool, Bitmap source) {
        if (source == null) return null;
        Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
        canvas.drawRoundRect(rectF, radius, radius, paint);
        return result;
    }

    @Override
    public String getId() {
        return getClass().getName() + Math.round(radius);
    }
}
