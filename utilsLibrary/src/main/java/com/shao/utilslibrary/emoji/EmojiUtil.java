package com.shao.utilslibrary.emoji;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.shao.utilslibrary.emoji.Emoji;
import com.shao.utilslibrary.utils.R;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description:表情工具类（输入框添加）
 * Company:
 * Author:Zhangshaopeng
 * Email :1377785991@qq.com
 * Data:2018/6/27
 */
public class EmojiUtil {
    private static String TAG = "EmojiUtil";

    private static List<Emoji> emojis = null; // 表情集合

    public static List<Emoji> getEmojiList(Context context) {
        try {
            InputStream inputStream = context.getResources().getAssets()
                    .open("brow.xml"); // 取得assets中的borw.xml文件
            emojis = ParserBrowXml.getInfo(inputStream); // 解析borw.xml
        } catch (IOException e) {
            e.printStackTrace();
        }
        return emojis;
    }

    /**
     * 输入框里加入表情
     *
     * @param ET_content
     * @param context
     * @param position
     */
    public static void ET_addEmoji(EditText ET_content, Context context, int position) {
        if (emojis == null) {
            getEmojiList(context);
        }
        //首先得到当前用户点击的表情的信息
        Emoji emoji = emojis.get(position);
        //得到当前CURSOR位置
        int cursor = ET_content.getSelectionStart();
        Field f;
        try {
            //根据资源名字得到Resource和对应的Drawable
            f = (Field) R.mipmap.class.getDeclaredField(emoji
                    .getName());
            int j = f.getInt(R.mipmap.class);
            Drawable d = context.getResources().getDrawable(j);
            WindowManager wm = (WindowManager) context
                    .getSystemService(Context.WINDOW_SERVICE);

            int width = wm.getDefaultDisplay().getWidth();
            int size = (int) (width * 0.056);
            d.setBounds(0, 0, size, size);//设置表情图片的显示大小

            //显示在EditText中
            String str = emoji.getCode();
            SpannableString ss = new SpannableString(str);
            ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BOTTOM);
            ss.setSpan(span, 0, str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            ET_content.getText().insert(cursor, ss);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 将包含表情的文字解析在TextView上
     *
     * @param comment
     * @param content
     * @param context
     * @throws IOException
     */
    public static void handlerEmojiText(TextView comment, String content, Context context) {
        if (emojis == null) {
            getEmojiList(context);
        }
        if (!TextUtils.isEmpty(content)) {
            SpannableStringBuilder ss = new SpannableStringBuilder(content);
            SpannableStringBuilder sb = new SpannableStringBuilder(content);
            String regex = "\\[(\\S+?)\\]";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(content);
            Iterator<Emoji> iterator;
            Emoji emoji = null;
            while (m.find()) {
                iterator = emojis.iterator();
                String tempText = m.group();
                while (iterator.hasNext()) {
                    emoji = iterator.next();
                    if (tempText.equals(emoji.getCode())) {
                        int imageUrl = 0;
                        try {
                            Field f;
                            //根据资源名字得到Resource和对应的Drawable
                            f = (Field) R.mipmap.class.getDeclaredField(emoji
                                    .getName());
                            imageUrl = f.getInt(R.mipmap.class);
                        } catch (NoSuchFieldException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        Drawable d = context.getResources().getDrawable(imageUrl);
                        WindowManager wm = (WindowManager) context
                                .getSystemService(Context.WINDOW_SERVICE);

                        int width = wm.getDefaultDisplay().getWidth();
                        int size = (int) (width * 0.056);
                        d.setBounds(0, 0, size, size);//设置表情图片的显示大小

                        //显示在EditText中
//                    String str = emoji.getCode();
//                    ss= new SpannableString(str);
                        ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BOTTOM);
                        ss.setSpan(span, m.start(), m.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    //转换为Span并设置Span的大小
//                    L.i(TAG,"m.start():"+m.start()+",m.end():"+m.end()+",dip2px:"+EmojiUtil.dip2px(context, 300));
//                    sb.setSpan(new ImageSpan(context, EmojiUtil.decodeSampledBitmapFromResource(context.getResources(), imageUrl
//                                    , EmojiUtil.dip2px(context, 18), EmojiUtil.dip2px(context, 18))),
//                            m.start(), m.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        break;
                    }
                }
            }
            comment.setText(ss);
        }
    }

    /**
     * 返回包含表情的文字
     *
     * @param content
     * @param context
     * @throws IOException
     */
    public static SpannableStringBuilder getEmojiText(String content, Context context) {
        if (emojis == null) {
            getEmojiList(context);
        }
        SpannableStringBuilder ss = null;
        if (!TextUtils.isEmpty(content)) {
            ss = new SpannableStringBuilder(content);
//            SpannableStringBuilder sb = new SpannableStringBuilder(content);
            String regex = "\\[(\\S+?)\\]";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(content);
            Iterator<Emoji> iterator;
            Emoji emoji = null;
            while (m.find()) {
                iterator = emojis.iterator();
                String tempText = m.group();
                while (iterator.hasNext()) {
                    emoji = iterator.next();
                    if (tempText.equals(emoji.getCode())) {
                        int imageUrl = 0;
                        try {
                            Field f;
                            //根据资源名字得到Resource和对应的Drawable
                            f = (Field) R.mipmap.class.getDeclaredField(emoji
                                    .getName());
                            imageUrl = f.getInt(R.mipmap.class);
                        } catch (NoSuchFieldException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        Drawable d = context.getResources().getDrawable(imageUrl);
                        WindowManager wm = (WindowManager) context
                                .getSystemService(Context.WINDOW_SERVICE);

                        int width = wm.getDefaultDisplay().getWidth();
                        int size = (int) (width * 0.056);
                        d.setBounds(0, 0, size, size);//设置表情图片的显示大小

                        //显示在EditText中
//                    String str = emoji.getCode();
//                    ss= new SpannableString(str);
                        ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BOTTOM);
                        ss.setSpan(span, m.start(), m.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    //转换为Span并设置Span的大小
//                    L.i(TAG,"m.start():"+m.start()+",m.end():"+m.end()+",dip2px:"+EmojiUtil.dip2px(context, 300));
//                    sb.setSpan(new ImageSpan(context, EmojiUtil.decodeSampledBitmapFromResource(context.getResources(), imageUrl
//                                    , EmojiUtil.dip2px(context, 18), EmojiUtil.dip2px(context, 18))),
//                            m.start(), m.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        break;
                    }
                }
            }
        }
        return ss;
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // 计算出实际宽高和目标宽高的比率
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            // 一定都会大于等于目标的宽和高。
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        // 调用上面定义的方法计算inSampleSize值
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }


    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /*    public static void handlerEmojiText(TextView comment, String content, Context context) throws IOException {
        SpannableStringBuilder sb = new SpannableStringBuilder(content);
        String regex = "\\[(\\S+?)\\]";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(content);
        Iterator<Emoji> iterator;
        Emoji emoji = null;
        while (m.find()) {
            iterator = emojiList.iterator();
            String tempText = m.group();
            while (iterator.hasNext()) {
                emoji = iterator.next();
                if (tempText.equals(emoji.getContent())) {
                    //转换为Span并设置Span的大小
                    sb.setSpan(new ImageSpan(context, decodeSampledBitmapFromResource(context.getResources(), emoji.getImageUri()
                                    , dip2px(context, 18), dip2px(context, 18))),
                            m.start(), m.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    break;
                }
            }
        }
        comment.setText(sb);
    }*/
}
