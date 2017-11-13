package com.soundcloud.android.pic;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Kellan on 2017/11/8.
 */

public class Pic {
    public static final int REQUEST_CODE_CAMERA = 0x100;
    private static final String AUTHORITY = ".fileprovider";
    private File picFile;

    private Pic() {
    }

    public static Pic of() {
        return new Pic();
    }

    public Pic file(File file) {
        this.picFile = file;
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        return this;
    }

    public void takePic(Activity act) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE_SECURE);
        // 下面这句指定调用相机拍照后的照片存储的路径
        Uri imageUri;
        if (android.os.Build.VERSION.SDK_INT >= 24) {//android7.0特殊处理
            imageUri = FileProvider.getUriForFile(act, act.getPackageName() + AUTHORITY, picFile);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            imageUri = Uri.fromFile(picFile);
        }

        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        act.startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }

    public void takePic(Context ctx, Fragment fm) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE_SECURE);
        // 下面这句指定调用相机拍照后的照片存储的路径
        Uri imageUri;
        if (android.os.Build.VERSION.SDK_INT >= 24) {//android7.0特殊处理
            imageUri = FileProvider.getUriForFile(ctx, ctx.getPackageName() + AUTHORITY, picFile);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            imageUri = Uri.fromFile(picFile);
        }

        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        fm.startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }

    static void crop(Activity act, Uri source) {
        Uri destination = Uri.fromFile(new File(act.getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().start(act);
    }

    public static void actvityResult(Activity act, File file, int requestCode, int resultCode, Intent data, float f, ResultInfo<String> info) {
        if (requestCode == Crop.REQUEST_PICK) {//pick pic
            if (resultCode == act.RESULT_OK) {
                crop(act, data.getData());
            } else {
                if (info != null) info.err("裁剪图片出现异常");
            }
        } else if (requestCode == Crop.REQUEST_CROP) {//after crop
            if (resultCode == act.RESULT_OK) {
                String s = getBase64(act, Crop.getOutput(data), f);
                if (info != null) info.result(s);
            } else if (resultCode == Crop.RESULT_ERROR) {
                if (info != null) info.err(Crop.getError(data).getMessage());
            }
        } else if (requestCode == REQUEST_CODE_CAMERA) {//after camera
            if(resultCode == act.RESULT_OK){
                Uri uri = getContentUri(act, act.getPackageName() + AUTHORITY, file);
                new RotaingSaveTask(act, uri, file).execute();
            }else {
                if (info != null) info.err("请打开权限");
            }
        }
    }

    private static String getBase64(final Context ctx, final Uri uri, final float f) {
        ExecutorService pool = Executors.newCachedThreadPool();
        try {
            Callable call = new Callable<String>() {
                @Override
                public String call() throws Exception {
                    String tp;

                    Bitmap b1 = PicUtil.uriToBitmap(ctx, uri);

                    if(f >= 1.0f){//不压缩
                        tp = PicUtil.bitmapToBase64(b1);
                    }else {
                        Bitmap b2 = PicUtil.compressIcon(b1, f);
                        tp = PicUtil.bitmapToBase64(b2);
                    }

                    return tp;
                }
            };

            Future<String> future = pool.submit(call);
            pool.shutdown();

            if (future.get() == null) {
                return null;
            } else {
                return future.get();
            }
        } catch (Exception e) {
            return null;
        }
    }

    private static Uri getContentUri(Context ctx, String authority, File file) {
        Uri contentUri;
        if (Build.VERSION.SDK_INT >= 24) {
            contentUri = FileProvider.getUriForFile(ctx, authority, file);
        } else {
            contentUri = Uri.fromFile(file);
        }
        return contentUri;
    }

    @SuppressLint("NewApi")
    static boolean isDead(Activity act) {
        return act == null || act.isFinishing() || act.isDestroyed();
    }
}
