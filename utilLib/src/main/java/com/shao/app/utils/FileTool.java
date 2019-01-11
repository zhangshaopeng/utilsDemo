package com.shao.app.utils;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.support.v4.content.FileProvider;

import com.shao.app.UtilManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Description:文件工具类
 * Company:
 * Author:Zhangshaopeng
 * Email :1377785991@qq.com
 * Data:2018/5/3
 */
public class FileTool {
    private static final String FD_FLASH = "-ext";

    /**
     * derectory为想用存储的完整自定义路径如(/aa/bb/cc.apk)
     * 此方法可以判断是存储在sdk下还是data/data/包名
     *
     * @param context
     * @param derectory
     * @return
     */
    public static String getFilePath(Context context, String derectory) {
        if (derectory.startsWith("/")) {
            derectory = derectory.substring(derectory.indexOf("/") + 1);
        }
        String path = "";
        if (avaiableMedia(context)) {
            if (isHasFlashMemory()) {
                path = Environment.getExternalStorageDirectory().getPath()
                        + FD_FLASH + "/" + derectory;
            } else {
                path = Environment.getExternalStorageDirectory().getPath()
                        + "/" + derectory;
            }
        } else {
            if (null != context) {
                path = context.getCacheDir().getPath() + "/" + derectory;
            }
        }
        return path;
    }

    /**
     * 获取系统存储路径
     *
     * @return 系统存储路径
     */
    public static String getRootDirPath() {
        return Environment.getRootDirectory().getAbsolutePath();
    }


    /**
     * 查询设备是否有不可被移除的sd卡
     *
     * @return true 有，false 没有
     */
    public static boolean hasSDCard() {

        return Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED) && !Environment.isExternalStorageRemovable();
    }

    /**
     * 得到SD的目录路径
     *
     * @return SD的目录路径
     */
    public static String getSDPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    /**
     * 获取SD卡的剩余容量 单位byte
     *
     * @return 如果有sd卡 则返回实际剩余容量，没有则返回 0
     */
    public static long getSDAvailableSize() {

        if (hasSDCard()) {
            StatFs stat = new StatFs(getSDPath());
            // 获取空闲的数据块的数量
            long availableBlocks = stat.getAvailableBlocks() - 4;
            // 获取单个数据块的大小（byte）
            long freeBlocks = stat.getAvailableBlocks();
            return freeBlocks * availableBlocks;
        }
        return 0;
    }

    /**
     * 得到SDCard/Android/data/应用的包名/files/ 目录
     *
     * @return SDCard/Android/data/应用的包名/files/目录 未获取到则返回null
     */
    public static String getExternalFilesDir() {
        String path = null;
        File file = UtilManager.getContext().getExternalFilesDir(null);
        if (file != null && file.exists()) {
            path = file.getAbsolutePath();
        }
        return path;
    }

    /**
     * 得到SDCard/Android/data/应用的包名/cache/ 目录
     *
     * @return SDCard/Android/data/应用的包名/cache/目录，未获取到则返回null
     */
    public static String getExternalCacheDir() {

        String path = null;
        File file = UtilManager.getContext().getExternalCacheDir();
        if (file != null && file.exists()) {
            path = file.getAbsolutePath();
        }
        return path;
    }

    /**
     * 返回一个内置存储 data/data/包名/files/目录
     *
     * @return 内置存储 data/data/包名/files/目录
     */
    public static String getFilesDir() {
        return UtilManager.getContext().getFilesDir().getAbsolutePath();
    }


    /**
     * 返回一个内置存储 data/data/包名/cache/目录
     *
     * @return 内置存储 data/data/包名/cache/目录
     */
    public static String getCacheDir() {
        return UtilManager.getContext().getCacheDir().getAbsolutePath();
    }


    /**
     * 文件是否存在
     *
     * @param filePath 文件路径
     * @return true 存在，false不存在
     */
    public static boolean fileIsExists(String filePath) {
        try {
            return new File(filePath).exists();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 创建文件并写入字节
    public static boolean createFile(File file, byte[] buffer) {
        if (null == file) {
            return false;
        }
        if (createFile(file)) {
            FileOutputStream fos;
            try {
                fos = new FileOutputStream(file);
                fos.write(buffer);
                fos.flush();
                fos.close();
                buffer = null;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    // 创建文件
    public static boolean createFile(File file) {
        Logger.d("===createFile====" + file.getPath());
        try {
            if (file.exists()) {
                return true;
            } else {
                if (file.getParentFile().isDirectory()) {
                    file.getParentFile().mkdirs();
                }
                File parentFile = file.getParentFile();
                if (!parentFile.exists()) {
                    parentFile.mkdirs();
                }
                file.createNewFile();
            }

            if (!file.exists()) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static File createTmpFile(Context context) {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            // 已挂载
            File pic = Environment.getExternalStorageDirectory();
            //Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new Date());
            String fileName = "multi_image_" + timeStamp + "";
            File tmpFile = new File(pic, fileName + ".jpg");
            return tmpFile;
        } else {
            File cacheDir = context.getCacheDir();
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new Date());
            String fileName = "multi_image_" + timeStamp + "";
            File tmpFile = new File(cacheDir, fileName + ".jpg");
            return tmpFile;
        }
    }

    /**
     * 复制文件
     *
     * @param fromFile
     * @param toFile
     */
    public static boolean copyFile(File fromFile, File toFile) {

        try {
            FileInputStream ins = new FileInputStream(fromFile);
            FileOutputStream out = new FileOutputStream(toFile);
            byte[] b = new byte[1024];
            int n = 0;
            while ((n = ins.read(b)) != -1) {
                out.write(b, 0, n);
            }

            ins.close();
            out.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    /**
     * 删除文件/文件夹。
     * 如果需要删除文件夹，需要注意耗时问题和递归问题
     *
     * @param filePath 文件路径
     */
    public static void deleteFile(String filePath) {

        if (!FileTool.fileIsExists(filePath)) {
            return;
        }

        File rootFile = new File(filePath);
        if (rootFile.isDirectory()) {
            File[] files = rootFile.listFiles();
            if (files != null) {
                for (File file : files) {
                    deleteFile(file.getAbsolutePath());
                }
            }
        }
        rootFile.delete();
    }

    /**
     * 将文件转换成uri(支持7.0)
     *
     * @param mContext
     * @param file
     * @return
     */
    public static Uri getUriForFile(Context mContext, File file) {
        Uri fileUri = null;
        if (Build.VERSION.SDK_INT >= 24) {
            fileUri = FileProvider.getUriForFile(mContext, mContext.getPackageName() + ".fileprovider", file);
        } else {
            fileUri = Uri.fromFile(file);
        }
        return fileUri;
    }

    /**
     * 判断位置可用空间
     *
     * @param path
     * @return
     */
    public static long getAvaiableSize(String path) {
        StatFs stat = new StatFs(path);
        long blockSize = stat.getBlockSize();
        long blockCount = stat.getBlockCount();
        return blockCount * blockSize;
    }

    /**
     * @return
     */
    public static boolean avaiableMedia(Context context) {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            ToastTool.show("没有内存卡");
            return false;
        }
    }

    /**
     * @return
     */
    private static boolean isHasFlashMemory() {
        String path = Environment.getExternalStorageDirectory().getPath()
                + FD_FLASH;
        File file = new File(path);
        if (file.exists()) {
            if (getAvaiableSize(path) == 0) {
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

}
