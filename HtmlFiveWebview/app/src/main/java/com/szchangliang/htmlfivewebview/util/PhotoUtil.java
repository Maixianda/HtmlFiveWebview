package com.szchangliang.htmlfivewebview.util;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

import com.szchangliang.htmlfivewebview.config.Config;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by 麦显达 on 2017/4/10.
 */
public class PhotoUtil {
    public static final int TAKE_PHOTO_VALUS = 1111;
    public static final int CHOSE_PHOTO_GALLERY_VALUES = 2222;
    public static final int CROP_PHOTO_VALUES = 3333;
    private static File takeTempFile, cropTempFile;
    private static String tempPath;

    /**
     * 获得一个图片暂存文件
     *
     * @return
     */
    public static File getTakeTempFile() {
        tempPath = Config.getImagePath().toString() + System.currentTimeMillis() + "_temp.jpg";
        File tempFile = new File(tempPath);
        return tempFile;
    }

    /**
     * 获取文件的路径
     *
     * @return
     */
    public static File getPicPath() {
        return takeTempFile;
    }

    /**
     * 获取文件的路径
     *
     * @return
     */
    public static File getCropPicPath() {
        return cropTempFile;
    }

    /**
     * 获取文件名字
     *
     * @return
     */
    public static String getFileName() {
        return tempPath;
    }

    /**
     * 清除文件的路径
     *
     * @return
     */
    public static void cleanPicPath() {
        takeTempFile = null;
        cropTempFile = null;
    }

    /**
     * 调用相机拍照
     *
     * @param activity
     * @return
     */
    public static void takePhoto(Activity activity) {
        takeTempFile = getTakeTempFile();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(takeTempFile));
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        intent.putExtra("noFaceDetection", true);
        activity.startActivityForResult(intent, TAKE_PHOTO_VALUS);
    }

    public static void takePhoto(Activity activity,int requestCode)
    {
        takeTempFile = getTakeTempFile();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(takeTempFile));
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        intent.putExtra("noFaceDetection", true);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 裁剪图片
     *
     * @param context
     * @param uri
     * @param outputXY
     * @return
     */
    public static void cropPhoto(Activity context, Uri uri, int outputXY) {
        cropTempFile = getTakeTempFile();
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", true);
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", outputXY);
        intent.putExtra("outputY", outputXY);
        if (cropTempFile == null) {
            cropTempFile = getTakeTempFile();
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cropTempFile));
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        context.startActivityForResult(intent, CROP_PHOTO_VALUES);
    }

    /**
     * 从相册选择图片
     *
     * @param activity
     */
    public static void choseGalleryPhoto(Activity activity) {
        //方法一：
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
//        intent.setType("image/jpeg");
//        intent.putExtra("return-data", true);
//        activity.startActivityForResult(intent, CHOSE_PHOTO_GALLERY_VALUES);

        //方法二：
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        intent.putExtra("return-data", true);
        activity.startActivityForResult(intent, CHOSE_PHOTO_GALLERY_VALUES);
    }


    /**
     * 保存bitmap到file
     */
    public static void saveBitmapToFile(File file, ContentResolver resolver, Uri uri) {
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(resolver.openInputStream(uri));
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 获得一个图片暂存文件
     *
     * @return
     */
    public static File getTempFile() {
        String tempPath = Config.getImagePath().getPath() + System.currentTimeMillis() + "_temp.jpg";
        File tempFile = new File(tempPath);
        return tempFile;
    }

}
