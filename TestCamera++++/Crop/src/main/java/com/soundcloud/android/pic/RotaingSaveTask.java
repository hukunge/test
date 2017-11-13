package com.soundcloud.android.pic;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;

import com.soundcloud.android.crop.CropUtil;

import java.io.File;
import java.lang.ref.WeakReference;

/**
 * Created by Kellan on 2017/11/10.
 */
 public class RotaingSaveTask extends AsyncTask<String, Integer, String> {
    private Activity act;
    private Uri uri;
    private File file;

    public RotaingSaveTask(Activity activity, Uri uri, File file) {
        WeakReference<Activity> weakAct = new WeakReference<>(activity);
        act = weakAct.get();
        this.uri = uri;
        this.file = file;
    }

    @Override
    protected String doInBackground(String... strings) {
        int degree = CropUtil.getExifRotation(file);
        if (degree != 0) {
            Bitmap photo = PicUtil.getBitemapFromFile(file.getAbsolutePath());
            Bitmap roatBitmap = PicUtil.rotaingImageView(degree, photo);
            PicUtil.saveImage(roatBitmap, file);
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        if (!Pic.isDead(act)) {
            Pic.crop(act, uri);
        }
    }
}
