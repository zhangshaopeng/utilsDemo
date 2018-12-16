package com.shao.utilslibrary.utils;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Description:吐司工具类，带图
 * Company:
 * Author:Zhangshaopeng
 * Email :1377785991@qq.com
 * Data:2018/12/5
 */
@SuppressLint("SimpleDateFormat")
public class ToastUtils2 {
    private static final boolean isDebug = true;
    @SuppressWarnings("unused")
    private static final boolean useLocalFontTypeFace = false;
    public static final int type_alertdef = 0;
    public static final int type_alertSucc = 1;
    public static final int type_alertError = 2;
    public static final int type_alertInfo = 3;
    //特殊字体
    public static final String fontTypeFace_fangzhengyuan = "fangzhengyuan.ttf";

    public static void alert(Context context, String msg, int type) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.toast_alert, null);
        ImageView img = (ImageView) view.findViewById(R.id.toast_logo);
        TextView tv = (TextView) view.findViewById(R.id.toast_text);
        switch (type) {
            case type_alertdef:
                img.setImageResource(AppInfo.getPackageInfo(context).applicationInfo.icon);
                break;
            case type_alertError:
                img.setImageResource(R.drawable.alert_err_icon);
                break;
            case type_alertSucc:
                img.setImageResource(R.drawable.alert_ok_icon);
                break;
            case type_alertInfo:
                img.setVisibility(View.GONE);
                break;
            default:
                img.setImageResource(AppInfo.getPackageInfo(context).applicationInfo.icon);
        }

        setFontTypeface(context, tv, "fangzhengyuan.ttf");

        tv.setText(msg);

        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        //设置toast弹出位置
        toast.setGravity(17, 0, DensityUtils.dip2px(120.0F));
        toast.setView(view);
        toast.show();

        Animation alpAnimation = new AlphaAnimation(0.1F, 1.0F);
        alpAnimation.setDuration(1000L);
        alpAnimation.setFillAfter(true);
        view.startAnimation(alpAnimation);
    }

    /**
     * 用户确认提示框
     *
     * @param context
     * @param msg     弹出的消息
     */

    public static void alertAsk(final Context context, String titleText,
                                final String msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle(titleText);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {


            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }

        });
        builder.create().show();

    }

    public static void debugAlert(Context context, String msg) {
        if (isDebug) {
            Toast.makeText(context, msg + "", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 调试弹出信息
     *
     * @param context 使用的activity本身对象
     * @param msg     准备弹出的消息 *
     */
    public static void debugAlert(Context context, int msg) {
        if (isDebug) {
            Toast.makeText(context, msg + "", Toast.LENGTH_SHORT).show();
        }
    }


    public static void setFontTypeface(Context context, TextView tv,
                                       String fontName) {
    }


}