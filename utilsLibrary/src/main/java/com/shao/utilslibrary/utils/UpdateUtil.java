package com.shao.utilslibraray.utils;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import app.dyne.com.upgrade.R;
import cn.droidlover.xdroid.util.CommonTools;

/**
 * 检查更新
 *
 * @author zsp
 */
public class UpdateUtil {

    private Context mContext;
    /**
     * 返回的安装包url
     */
    /* private String apkUrl = "http://dsapp.asc-wines.com/downloadapp/ds-app.apk";*/
    private String apkUrl = "";
    /**
     * 下载包安装路径
     */
    private static final String savePath = "/sdcard/updata/";
    private static String saveFileName = savePath + "app.apk";
    /**
     * 进度条与通知ui刷新的handler和msg常量
     */
    private ProgressBar mProgress;
    private TextView pro_tv;

    private static final int DOWN_UPDATE = 1;
    private static final int DOWN_OVER = 2;
    private static final int DOWN_ERROR = 3;
    private int progress;
    private Dialog noticeDialog;
    private Dialog downloadDialog;
    private Thread downLoadThread;
    private boolean interceptFlag = false;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWN_UPDATE:
                    mProgress.setProgress(progress);
                    pro_tv.setText(progress + "/100");
                    break;
                case DOWN_OVER:
                    downloadDialog.dismiss();
                    installApk();
                    break;
                case DOWN_ERROR:
                    downloadDialog.dismiss();
                    CommonTools.alert(mContext, "下载错误", CommonTools.type_alertWarn);
                    break;
                default:
                    break;
            }
        }
    };

    public UpdateUtil(Context context) {
        this.mContext = context;
    }

    /**
     * 外部接口让主Activity调用
     *
     * @param url
     * @param softName
     */
    public void checkUpdateInfo(String url, String softName) {
        saveFileName = savePath + softName + ".apk";
        if (!fileIsExists(saveFileName) || (fileIsExists(saveFileName) && !isUninatllApkInfo(saveFileName))) {
            apkUrl = url;
            if (TextUtils.isEmpty(apkUrl)) {
                CommonTools.alert(mContext, "下载路径错误，请重试", CommonTools.type_alertWarn);
                return;
            }
            showNoticeDialog();
        }
    }

    private void showNoticeDialog() {
        Builder builder = new Builder(mContext);
        builder.setTitle("软件下载");
        builder.setMessage("有最新的软件包，点击下载");
        builder.setPositiveButton("下载", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                showDownloadDialog();
            }
        });
        builder.setNegativeButton("以后再说", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        noticeDialog = builder.create();
        noticeDialog.show();
    }

    private void showDownloadDialog() {
        Builder builder = new Builder(mContext);
        builder.setTitle("软件下载");

        final LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.progress, null);
        mProgress = (ProgressBar) v.findViewById(R.id.progress);
        pro_tv = (TextView) v.findViewById(R.id.pro_tv);

        builder.setView(v);
        builder.setPositiveButton("隐藏", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                interceptFlag = false;
            }
        });
        builder.setNegativeButton("取消", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                interceptFlag = true;
            }
        });
        downloadDialog = builder.create();
        downloadDialog.show();

        downloadApk();
    }

    private Runnable mdownApkRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                URL url = new URL(apkUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.connect();
                int length = conn.getContentLength();
                InputStream is = conn.getInputStream();

                File file = new File(savePath);
                if (!file.exists()) {
                    file.mkdir();
                }
                String apkFile = saveFileName;
                File ApkFile = new File(apkFile);
                FileOutputStream fos = new FileOutputStream(ApkFile);

                int count = 0;
                byte buf[] = new byte[1024];

                do {
                    int numread = is.read(buf);
                    count += numread;
                    progress = (int) (((float) count / length) * 100);
                    //更新进度 
                    mHandler.sendEmptyMessage(DOWN_UPDATE);
                    if (numread <= 0) {
                        //下载完成通知安装 
                        mHandler.sendEmptyMessage(DOWN_OVER);
                        break;
                    }
                    fos.write(buf, 0, numread);
                    //点击取消就停止下载.
                } while (!interceptFlag);

                fos.close();
                is.close();
            } catch (MalformedURLException e) {
                mHandler.sendEmptyMessage(DOWN_ERROR);
                e.printStackTrace();
            } catch (IOException e) {
                mHandler.sendEmptyMessage(DOWN_ERROR);
                e.printStackTrace();
            }

        }
    };

    /**
     * 下载apk
     *
     * @param
     */

    private void downloadApk() {
        downLoadThread = new Thread(mdownApkRunnable);
        downLoadThread.start();
    }

    /**
     * 安装apk
     *
     * @param
     */
    private void installApk() {
        File apkfile = new File(saveFileName);
        if (!apkfile.exists()) {
            return;
        }
        Intent i = new Intent(Intent.ACTION_VIEW); 
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");  
        mContext.startActivity(i); 
    }

    /**
     * 判断文件是否存在
     *
     * @param strFile
     * @return
     */
    public boolean fileIsExists(String strFile) {
        try {
            File f = new File(strFile);
            if (!f.exists()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 判断apk是否完整
     *
     * @param filePath
     * @return
     */
    public boolean isUninatllApkInfo(String filePath) {
        boolean result = false;
        try {
            PackageManager pm = mContext.getPackageManager();
            PackageInfo info = pm.getPackageArchiveInfo(filePath, PackageManager.GET_ACTIVITIES);
            if (info != null) {
                result = true;
            }
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

}
