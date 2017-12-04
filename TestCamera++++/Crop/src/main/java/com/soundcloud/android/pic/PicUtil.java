package com.soundcloud.android.pic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Base64;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by dubin on 2017/4/22.
 */

public class PicUtil {

    private PicUtil(){}

    public static Bitmap getBitemapFromFile(String fileName) {
        try {
            Bitmap bitmap = BitmapFactory.decodeFile(fileName);
            return bitmap;
        } catch (Exception ex) {
            return null;
        }
    }

    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        if (bitmap == null || bitmap.isRecycled())
            return bitmap;

        //旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        if (!bitmap.isRecycled())
            bitmap.recycle();

        return resizedBitmap;
    }

    public static boolean saveImage(Bitmap bmp, File file) {
        if(bmp == null || bmp.isRecycled())
            return false;

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            return true;
        } catch (Exception e) {
            return false;
        } finally {

            try {
                if(fos != null){
                    fos.flush();
                    fos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if(!bmp.isRecycled())
                bmp.recycle();
        }
    }

    public static Bitmap uriToBitmap(Context context, Uri uri) {
        try {
            return MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
        } catch (Exception e) {
            return null;
        }
    }

    public static Bitmap compressIcon(Bitmap b, float l){
        if(b == null || b.isRecycled())
            return null;

        if(l >= 1.0f)
            return b;

        Matrix matrix = new Matrix();
        matrix.setScale(l, l);
        Bitmap bm = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), matrix, true);

        if(!b.isRecycled()){
            b.recycle();
        }

        return bm;
    }

    public static String bitmapToBase64(Bitmap bitmap) {
        if(bitmap == null || bitmap.isRecycled())
            return null;

        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                baos.flush();
                baos.close();
                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if(!bitmap.isRecycled()){
                bitmap.recycle();
            }
        }
        return result;
    }

    public static String getBase64(final Context ctx, final Uri uri, final float f) {
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

    public static Uri getContentUri(Context ctx, String authority, File file) {
        Uri contentUri;
        if (Build.VERSION.SDK_INT >= 24) {
            contentUri = FileProvider.getUriForFile(ctx, authority, file);
        } else {
            contentUri = Uri.fromFile(file);
        }
        return contentUri;
    }
}
