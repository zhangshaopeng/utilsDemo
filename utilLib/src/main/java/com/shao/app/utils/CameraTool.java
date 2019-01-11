package com.shao.app.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * p>Describe:带裁剪拍照相册工具
 * p>Company:Dyne
 * p>@Author:zsp
 * p>Data:2019/1/11.
 */
public class CameraTool {
    private Context mContext;
    private final String TAG = "CameraUtil";
    /**
     * 创建一个以当前时间为名称的文件
     */
    private File mTempFile;
    // 拍照
    public static final int PHOTO_REQUEST_TAKEPHOTO = 1;
    // 从相册中选择
    public static final int PHOTO_REQUEST_GALLERY = 2;
    // 结果
    public static final int PHOTO_REQUEST_CUT = 3;

    private boolean isCrop = true;
    private int aspectX = 1, aspectY = 1, outWidth = 150, outHeight = 150;

    private OnPhotoListener onPhotoListener;

    public interface OnPhotoListener {
        void getPhotoPath(String filePath);

        void getPhotoCropData(byte[] data);

        void getPhotoFile(File file);//裁剪后返回文件
    }

    public CameraTool(Context context) {
        mContext = context;
    }

    public void setTempFile(File file) {
        mTempFile = file;
    }


    public File getTakePhotoFile() {
        return mTempFile;
    }

    public void setOnPhotoListener(OnPhotoListener onPhotoListener) {
        this.onPhotoListener = onPhotoListener;
    }

    public void setConfig(boolean isCrop, int aspectX, int aspectY, int outWidth, int outHeight) {
        this.isCrop = isCrop;
        this.aspectX = aspectX;
        this.aspectY = aspectY;
        this.outWidth = outWidth;
        this.outHeight = outHeight;
    }

    public void setUpTempFile() {
        if (null != mTempFile) {
            FileTool.deleteFile(mTempFile.getPath());
        }
        mTempFile = new File(FileTool.getFilePath(mContext, Constants.TEMP_PHOTO_PATH + getPhotoFileName()));
    }

    /**
     * 使用系统当前日期加以调整作为照片的名称
     */
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".jpg";
    }

    public void tackPhoto() {
        setUpTempFile();
        // 调用系统的拍照功能
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(mContext.getPackageManager()) != null) {
            mTempFile = FileTool.createTmpFile(mContext);
            if (Build.VERSION.SDK_INT >= 24) {
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Uri contentUri = FileProvider.getUriForFile(mContext, "com.phsxy.footballbaby.fileprovider", mTempFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
            } else {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mTempFile));
            }
            ((Activity) mContext).startActivityForResult(intent, PHOTO_REQUEST_TAKEPHOTO);
        } else {
            ToastTool.show("没有相机");
        }

    }

    private Uri getUri() {
        //由于一些Android 7.0以下版本的手机在剪裁保存到URI会有问题，所以根据版本处理下兼容性
        if (Build.VERSION.SDK_INT >= 24) {
            return FileProvider.getUriForFile(mContext, "com.phsxy.footballbaby.fileprovider", getTakePhotoFile());
        } else {
            return Uri.fromFile(mTempFile);
        }
    }

    public void pickPhoto() {
        setUpTempFile();
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        ((Activity) mContext).startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case CameraTool.PHOTO_REQUEST_TAKEPHOTO:
                    if (isCrop) {
                        Logger.e("回调", "去裁剪" + data);
                        startPhotoZoom(getUri());
                    } else {
                        if (null != onPhotoListener) {
                            onPhotoListener.getPhotoPath(getUriToPath(Uri.fromFile(getTakePhotoFile())));
                        }
                    }
                    break;

                case CameraTool.PHOTO_REQUEST_GALLERY:
                    if (data != null) {
                        if (isCrop) {
                            try {
                                startPhotoZoom(data.getData());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            if (null != onPhotoListener) {
                                onPhotoListener.getPhotoPath(getUriToPath(data.getData()));
                            }
                        }
                    }
                    break;

                case CameraTool.PHOTO_REQUEST_CUT:
                    Logger.e("回调", "裁剪结果" + data);
                    if (data != null) {
                        byte[] bytes = getDataToBytes(data);
                        if (null != bytes) {
                            if (null != onPhotoListener) {
                                Logger.e("回调", "裁剪结果" + "");
                                FileTool.createFile(mTempFile, bytes);
                                onPhotoListener.getPhotoCropData(bytes);
                                Logger.e("回调", "裁剪结果" + "----------------" + mTempFile.getPath());
                                onPhotoListener.getPhotoPath(mTempFile.getPath());
                                onPhotoListener.getPhotoFile(mTempFile);
                            }
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public void startPhotoZoom(Uri uri) {
        try {
            Logger.e("回调", "----------------裁剪--------------------");
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(uri, "image/*");
            // crop为true是设置在开启的intent中设置显示的view可以剪裁
            intent.putExtra("crop", "true");
            // aspectX aspectY 是宽高的比例
            intent.putExtra("aspectX", aspectX);
            intent.putExtra("aspectY", aspectY);
            // outputX,outputY 是剪裁图片的宽高
            intent.putExtra("outputX", outWidth);
            intent.putExtra("outputY", outHeight);
            intent.putExtra("return-data", true);
            ((Activity) mContext).startActivityForResult(intent, PHOTO_REQUEST_CUT);
        } catch (Exception a) {
            String errorMessage = "Your device doesn't support the crop action!";
            Logger.e("回调", errorMessage);
        }
    }

    private String getUriToPath(Uri uri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = ((Activity) mContext).managedQuery(uri, proj, null, null, null);
        int actual_image_column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(actual_image_column_index);
    }

    public Uri getPathToURI(String path) {
        if (path != null) {
            path = Uri.decode(path);
            ContentResolver cr = mContext.getContentResolver();
            StringBuffer buff = new StringBuffer();
            buff.append("(").append(MediaStore.Images.ImageColumns.DATA).append("=")
                    .append("'" + path + "'").append(")");
            Cursor cur = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    new String[]{MediaStore.Images.ImageColumns._ID}, buff.toString(), null, null);
            int index = 0;
            for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
                // set _id value
                index = cur.getInt(index);
            }
            if (index == 0) {
                //do nothing
            } else {
                Uri uri_temp = Uri.parse("content://media/external/images/media/" + index);
                if (uri_temp != null) {
                    return uri_temp;
                }
            }
        }
        return null;
    }

    /**
     * 将进行剪裁后的图片显示到UI界面上
     *
     * @param picdata
     * @return
     */
    private byte[] getDataToBytes(Intent picdata) {
        Bundle bundle = picdata.getExtras();
        if (bundle != null) {
            // Constants.drawable = new BitmapDrawable(mIconBitmap);//太占用内存
            byte[] bytes = BitmapTool.bitmap2Bytes((Bitmap) bundle.getParcelable("data"), 100);
            return bytes;
        } else {
            ToastTool.show("取消了选择");
        }
        return null;
    }

    public void clearTemp() {
        FileTool.deleteFile(mTempFile.getPath());
    }

}
