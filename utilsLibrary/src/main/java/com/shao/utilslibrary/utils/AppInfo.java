package com.shao.utilslibrary.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;


import com.shao.utilslibrary.UtilManager;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Description:APP信息工具类
 * Company:
 * Author:Zhangshaopeng
 * Email :1377785991@qq.com
 * Data:2018/5/3
 */
public class AppInfo {

    /**
     * 获取当前应用 的包名
     *
     * @return
     */
    public static String getPackageName() {
        return UtilManager.getContext().getPackageName();
    }


    /**
     * 获取当前应用的版本名称
     *
     * @return 失败时返回 null
     */
    public static String getVersionName() {

        try {
            return UtilManager.getContext().getPackageManager().getPackageInfo(
                    UtilManager.getContext().getPackageName(), 0).versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取当前应用的icon
     *
     * @return 失败时返回null
     */
    public static Drawable getAppIcon() {

        try {
            return UtilManager.getContext().getPackageManager().getPackageInfo(
                    UtilManager.getContext().getPackageName(), 0).applicationInfo.loadIcon(UtilManager.getContext().getPackageManager());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 得到当前应用的应用名
     *
     * @return 失败时返回null
     */
    public static String getAppName() {
        try {
            PackageManager packageManager = UtilManager.getContext().getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(UtilManager.getContext().getPackageName(), 0);
            return (String) packageManager.getApplicationLabel(applicationInfo);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }


    /**
     * 获取当前应用的版本号
     *
     * @return 失败时返回-1
     */
    public static int getVersionCode() {

        try {
            return UtilManager.getContext().getPackageManager().getPackageInfo(
                    UtilManager.getContext().getPackageName(), 0).versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 获取当前app的权限列表
     *
     * @return
     */
    public static List<String> getPermissionsList() {
        List<String> permissionsList = new ArrayList<>();
        try {
            PackageInfo packageInfo = UtilManager.getContext().getPackageManager()
                    .getPackageInfo(UtilManager.getContext().getPackageName(),
                            PackageManager.GET_PERMISSIONS);
            String[] permissions = packageInfo.requestedPermissions;
            Collections.addAll(permissionsList, permissions);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return permissionsList;
    }


    /**
     * 获取manifest里的meta-data 数据，可以用来获取渠道名称等
     *
     * @return 失败时会返回null
     */
    public static String getMetaData(String keyName) {

        try {
            ApplicationInfo packageInfo = UtilManager.getContext().getPackageManager()
                    .getApplicationInfo(UtilManager.getContext().getPackageName(),
                            PackageManager.GET_META_DATA);
            Object o = packageInfo.metaData.get(keyName);
            if (null != o) {
                return (String) o;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 判断app当前是否在前台运行
     *
     * @return true 在前台运行，false 不在前台运行。
     */
    public static boolean isRunningForeground() {
        ActivityManager am = (ActivityManager) UtilManager.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        String packageName = cn.getPackageName();
        return TextUtils.equals(packageName, getPackageName());
    }

    /**
     * 停止服务.
     *
     * @param ctx       the ctx
     * @param className the class name
     * @return true, if successful
     */
    public static boolean stopRunningService(Context ctx, String className) {
        Intent intent_service = null;
        boolean ret = false;
        try {
            intent_service = new Intent(ctx, Class.forName(className));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (intent_service != null) {
            ret = ctx.stopService(intent_service);
        }
        return ret;
    }
    /**
     * 获取当前进程名
     */
    public static String getCurrentProcessName() {
        int pid = android.os.Process.myPid();
        String processName = "";
        ActivityManager manager = (ActivityManager) UtilManager.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        if (manager != null) {
            for (ActivityManager.RunningAppProcessInfo process : manager.getRunningAppProcesses()) {
                if (process.pid == pid) {
                    processName = process.processName;
                }
            }
        }
        return processName;
    }

    /**
     * 包名判断是否为主进程
     */
    public static boolean isMainProcess() {
        return UtilManager.getContext().getPackageName().equals(getCurrentProcessName());
    }

    /**
     * 描述：打开并安装文件.
     *
     * @param context the context
     * @param file    apk文件路径
     */
    public static void installApk(Context context, File file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    /**
     * 描述：卸载程序.
     *
     * @param context     the context
     * @param packageName 包名
     */
    public static void uninstallApk(Context context, String packageName) {
        Intent intent = new Intent(Intent.ACTION_DELETE);
        Uri packageURI = Uri.parse("package:" + packageName);
        intent.setData(packageURI);
        context.startActivity(intent);
    }
}
