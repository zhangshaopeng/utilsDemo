package com.shao.app.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.media.FaceDetector;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * p>Describe:人脸检测并裁剪
 * p>Company:Dyne
 * p>@Author:zsp
 * p>Data:2018/11/30.
 */
public final class FaceTool {
    private static BitmapFactory.Options BitmapFactoryOptionsbfo;
    private static ByteArrayOutputStream out;
    private static byte[] data;
    private static FaceDetector.Face[] myFace;
    private static FaceDetector myFaceDetect;
    private static int tx = 0;
    private static int ty = 0;
    private static int bx = 0;
    private static int by = 0;
    private static int width = 0;
    private static int height = 0;
    private static float wuchax = 0;
    private static float wuchay = 0;
    private static FaceDetector.Face face;
    private static PointF myMidPoint;
    private static float myEyesDistance;

    public static Bitmap cutFace(Bitmap bitmap, Context context) {
        BitmapFactoryOptionsbfo = new BitmapFactory.Options();
        // 构造位图生成的参数，必须为565。类名+enum
        BitmapFactoryOptionsbfo.inPreferredConfig = Bitmap.Config.RGB_565;
        out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, out);
        data = out.toByteArray();
        bitmap = BitmapFactory.decodeByteArray(data, 0, data.length,
                BitmapFactoryOptionsbfo);
        try {
            out.flush();
            out.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        width = bitmap.getWidth();
        height = bitmap.getHeight();
        // 分配人脸数组空间
        myFace = new FaceDetector.Face[5];
        myFaceDetect = new FaceDetector(bitmap.getWidth(), bitmap.getHeight(), 5);
        int numberOfFaceDetected = myFaceDetect.findFaces(bitmap, myFace);
        // FaceDetector构造实例并解析人脸
        if (numberOfFaceDetected <= 0) {
            bitmap.recycle();
            return null;
        }

        for (int i = 0; i < numberOfFaceDetected; i++) {
            face = myFace[i];
            myMidPoint = new PointF();
            face.getMidPoint(myMidPoint);
            //得到人脸中心点和眼间距离参数，并对每个人脸进行画框
            myEyesDistance = face.eyesDistance();
            wuchax = myEyesDistance / 2 + myEyesDistance;
            wuchay = myEyesDistance * 2 / 3 + myEyesDistance;
            //判断左边是否出界
            if (myMidPoint.x - wuchax < 0) {
                tx = 0;
            } else {
                tx = (int) (myMidPoint.x - wuchax);
            }
            //判断右边是否出界
            if (myMidPoint.x + wuchax > width) {
                bx = width;
            } else {
                bx = (int) (myMidPoint.x + wuchax);
            }
            //判断上边是否出界
            if (myMidPoint.y - wuchay < 0) {
                ty = 0;
            } else {
                ty = (int) (myMidPoint.y - wuchay);
            }
            //判断下边是否出界
            if (myMidPoint.y + wuchay > height) {
                by = height;
            } else {
                by = (int) (myMidPoint.y + wuchay);
            }

            try {
                //这里可以自行调整裁剪宽高
                int i1 = bx - tx + 5;
                int i2 = by - ty + 25;
                return Bitmap.createBitmap(bitmap, tx, ty, i1, i2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

}