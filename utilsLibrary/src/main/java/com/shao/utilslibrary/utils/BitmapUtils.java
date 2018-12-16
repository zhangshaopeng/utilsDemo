package com.shao.utilslibrary.utils;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Base64;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.shao.utilslibrary.UtilManager;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import static com.shao.utilslibrary.emoji.EmojiUtil.calculateInSampleSize;

/**
 * Description:关于图片转换的一些工具类
 * Company:
 * Author:Zhangshaopeng
 * Email :1377785991@qq.com
 * Data:2018/5/3
 */
public class BitmapUtils {
    /**
     * 把bitmap转换成String
     *
     * @param bitmap 需要转换的图片
     * @return 转换后的base64转码的字符串
     */
    public static String bitmap2Str(Bitmap bitmap) {
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 30, arrayOutputStream);
        byte[] b = arrayOutputStream.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    /**
     * string转成bitmap
     *
     * @param str 需要转换的字符串
     * @return 失败时为null
     */
    public static Bitmap str2Bitmap(String str) {
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray = Base64.decode(str, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
                    bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    /**
     * 缩放图片
     *
     * @param bitmap
     * @param wf
     * @param hf
     * @return
     */
    public static Bitmap zoomImg(Bitmap bitmap, float wf, float hf) {
        Matrix matrix = new Matrix();
        matrix.postScale(wf, hf);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
    }

    /**
     * 图片圆角处理
     *
     * @param bitmap
     * @param roundPX
     * @return
     */
    public static Bitmap getRoundBitmap(Bitmap bitmap, float roundPX) {
        // RCB means
        // Rounded
        // Corner Bitmap
        Bitmap dstbmp = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(dstbmp);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPX, roundPX, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return dstbmp;
    }

    /**
     * 压缩图片
     *
     * @param b 要压缩的图片
     * @return 返回压缩后的图片，失败后返回null
     */
    public static Bitmap compressBitmap(Bitmap b) {

        Bitmap bitmap = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());

        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(inputStream, null, options);
            inputStream.close();
            int i = 0;
            while (true) {
                if ((options.outWidth >> i <= 1000)
                        && (options.outHeight >> i <= 1000)) {
                    options.inSampleSize = (int) Math.pow(2.0D, i);
                    options.inJustDecodeBounds = false;

                    inputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
                    bitmap = BitmapFactory.decodeStream(inputStream, null, options);
                    inputStream.close();
                    break;
                }
                i += 1;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    /**
     * 压缩图片
     *
     * @param path 图片路径
     * @return 返回压缩后的图片，
     */
    public static Bitmap compressBitmap(String path) {
        Bitmap bitmap;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        int i = 0;

        while (true) {
            if ((options.outWidth >> i <= 1000)
                    && (options.outHeight >> i <= 1000)) {
                options.inSampleSize = (int) Math.pow(2.0D, i);
                options.inJustDecodeBounds = false;
                bitmap = BitmapFactory.decodeFile(path, options);
                break;
            }
            i += 1;
        }
        return bitmap;
    }


    /**
     * 获取图片的旋转角度
     *
     * @param imgPath 图片路径
     * @return 0，90，180，270
     */
    public static int getRotateDegrees(String imgPath) {
        int degrees = 0;
        try {
            ExifInterface exif = new ExifInterface(imgPath);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degrees = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degrees = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degrees = 270;
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return degrees;
    }
    /**
     * 得到本地或者网络上的bitmap url - 网络或者本地图片的绝对路径,比如:
     * <p/>
     * A.网络路径: url="http://blog.foreverlove.us/girl2.png" ;
     * <p/>
     * B.本地路径:url="file://mnt/sdcard/photo/image.png";
     * <p/>
     * C.支持的图片格式 ,png, jpg,bmp,gif等等
     *
     * @param url
     * @return
     */
    public static Bitmap GetLocalOrNetBitmap(String url) {
        Bitmap bitmap = null;
        InputStream in = null;
        BufferedOutputStream out = null;
        try {
            in = new BufferedInputStream(new URL(url).openStream(), 1024);
            final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
            out = new BufferedOutputStream(dataStream, 1024);
            copy(in, out);
            out.flush();
            byte[] data = dataStream.toByteArray();
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            data = null;
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    private static void copy(InputStream in, OutputStream out) throws IOException {
        byte[] b = new byte[1024];
        int read;
        while ((read = in.read(b)) != -1) {
            out.write(b, 0, read);
        }
    }
    /**
     * 旋转图片
     *
     * @param imgPath 图片路径
     * @return 得到旋转后的bitmap 失败后会返回null
     */
    @Nullable
    public static Bitmap rotateBitmap(String imgPath) {

        Bitmap bitmap;
        try {
            //根据Path读取资源图片
            bitmap = BitmapFactory.decodeFile(imgPath);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }

        Matrix matrix = new Matrix();
        //翻转90度
        matrix.postRotate(+90);
        try {
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    /**
     * 保存图片
     *
     * @param bitmap
     * @param path   保存文件的绝对路径
     * @return true 成功 ，false 失败
     */
    public static Boolean saveBitmap(Bitmap bitmap, String path) {

        File f = new File(path);
        try {
            FileOutputStream out = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据路径查看是否是图片 支持（.jpg", ".jpeg", ".png", ".bmp"）
     *
     * @param path 路径
     * @return true 是，false 不是。
     */
    private boolean isImageFile(String path) {
        if (TextUtils.isEmpty(path)) {
            return false;
        }
        String[] imgSuffix = {".jpg", ".jpeg", ".png", ".bmp"};
        for (String str : imgSuffix) {
            if (path.toLowerCase().endsWith(str)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 下载并保存图片（需子线程调用）
     *
     * @param url      图片url
     * @param dirPath  存储路径
     * @param fileName 图片名称
     * @return 下载结果
     */
    public static boolean downloadImg(String url, String dirPath, String fileName) {

        try {
            File imgFile = Glide.with(UtilManager.getContext()).load(url).downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();

            if (imgFile == null) {
                return false;
            }

            File dir = new File(dirPath);
            if (!dir.exists()) {
                boolean b = dir.mkdir();
                if (!b) {
                    return false;
                }
            }
            File file = new File(dirPath, fileName);
            boolean b = FileUtils.copyFile(imgFile, file);
            if (b) {
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri uri = Uri.fromFile(file);
                intent.setData(uri);
                UtilManager.getContext().sendBroadcast(intent);
            }

            return b;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    /**
     * bitmap转byteArr
     *
     * @param bitmap bitmap对象
     * @param format 格式
     * @return 字节数组
     */
    public static byte[] bitmap2Bytes(Bitmap bitmap, Bitmap.CompressFormat format) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(format, 100, baos);
        return baos.toByteArray();
    }
    /**
     * byteArr转bitmap
     *
     * @param bytes 字节数组
     * @return bitmap对象
     */
    public static Bitmap bytes2Bitmap(byte[] bytes) {
        if (bytes.length != 0) {
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        } else {
            return null;
        }
    }
    /**
     * drawable转bitmap
     *
     * @param drawable drawable对象
     * @return bitmap对象
     */
    public static Bitmap drawable2Bitmap(Drawable drawable) {
        // 取 drawable 的长宽
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();

        // 取 drawable 的颜色格式
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        // 建立对应 bitmap
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        // 建立对应 bitmap 的画布
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        // 把 drawable 内容画到画布中
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * bitmap转drawable
     *
     * @param res    resources对象
     * @param bitmap bitmap对象
     * @return drawable对象
     */
    public static Drawable bitmap2Drawable(Resources res, Bitmap bitmap) {
        return new BitmapDrawable(res, bitmap);
    }

    public static Drawable bitmap2Drawable(Bitmap bitmap) {
        return new BitmapDrawable(bitmap);
    }

    /**
     * drawable转byteArr
     *
     * @param drawable drawable对象
     * @param format   格式
     * @return 字节数组
     */
    public static byte[] drawable2Bytes(Drawable drawable, Bitmap.CompressFormat format) {
        Bitmap bitmap = drawable2Bitmap(drawable);
        return bitmap2Bytes(bitmap, format);
    }

    /**
     * byteArr转drawable
     *
     * @param res   resources对象
     * @param bytes 字节数组
     * @return drawable对象
     */
    public static Drawable bytes2Drawable(Resources res, byte[] bytes) {
        Bitmap bitmap = bytes2Bitmap(bytes);
        Drawable drawable = bitmap2Drawable(res, bitmap);
        return drawable;
    }

    public static Drawable bytes2Drawable(byte[] bytes) {
        Bitmap bitmap = bytes2Bitmap(bytes);
        Drawable drawable = bitmap2Drawable(bitmap);
        return drawable;
    }
    /**
     * 获取bitmap
     *
     * @param file 文件
     * @return bitmap
     */
    public static Bitmap getBitmap(File file) throws IOException {
        if (file == null) {
            return null;
        }
        InputStream is = null;
        try {
            is = new BufferedInputStream(new FileInputStream(file));
            return BitmapFactory.decodeStream(is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } finally {
            is.close();
        }
    }

    /**
     * 获取bitmap
     *
     * @param filePath 文件路径
     * @return bitmap
     */
    public static Bitmap getBitmap(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return null;
        }
        return BitmapFactory.decodeFile(filePath);
    }
    /**
     * 获取bitmap
     *
     * @param resId     资源id
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return bitmap
     */
    public static Bitmap getBitmap(int resId, int maxWidth, int maxHeight) {
        if (UtilManager.getContext() == null) {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        InputStream is = UtilManager.getContext().getResources().openRawResource(resId);
        BitmapFactory.decodeStream(is, null, options);
        options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(is, null, options);
    }
    /**
     * 获取bitmap
     *
     * @param res 资源对象
     * @param id  资源id
     * @return bitmap
     */
    public static Bitmap getBitmap(Resources res, int id) {
        if (res == null) {
            return null;
        }
        return BitmapFactory.decodeResource(res, id);
    }
    /**
     * 获取bitmap
     *
     * @param res       资源对象
     * @param id        资源id
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return bitmap
     */
    public static Bitmap getBitmap(Resources res, int id, int maxWidth, int maxHeight) {
        if (res == null) {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, id, options);
        options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, id, options);
    }
}
