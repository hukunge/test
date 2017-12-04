package com.test.pic.gallery;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Administrator on 2017/12/4.
 */

public class DataUtil {

    public static List<GalleryData> getData(Context ctx) {
        ExecutorService pool = Executors.newCachedThreadPool();
        try {
            Callable call = new Callable<List<GalleryData>>() {
                @Override
                public List<GalleryData> call() throws Exception {
                    return getImagesFromGallary(ctx);
                }
            };

            Future<List<GalleryData>> future = pool.submit(call);
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

    private static List<GalleryData> getImagesFromGallary(Context context) {
        List<GalleryData> images = new ArrayList<>();
        Cursor c = null;
        try {
            String[] columns = {MediaStore.Images.Media.DATA, MediaStore.Images.ImageColumns.ORIENTATION};
            String orderBy = MediaStore.Images.Media.DATE_ADDED + " DESC";

            c = context.getApplicationContext()
                    .getContentResolver()
                    .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, orderBy);

            while (c.moveToNext()) {
                Uri uri = Uri.parse(c.getString(c.getColumnIndex(MediaStore.Images.Media.DATA)));
                images.add(new GalleryData(uri));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null && !c.isClosed()) {
                c.close();
            }
        }

        return images;
    }
}
