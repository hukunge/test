package com.test.pic;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by dubin on 2017/4/22.
 */

public class SdCardUtils {

    public static boolean isSDCardEnable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /*@保存头像的路径: mnt/sd/Android/data/。如果sd卡不存在，保存在/data/data/com.my.app/files */
    public static String getPicturePath(Context context) {
        String touXiangPath;
        if (isSDCardEnable()) {
            touXiangPath = Environment.getExternalStorageDirectory().getPath() + File.separator + Environment.DIRECTORY_PICTURES + File.separator;
        } else {
            if (context.getCacheDir() != null) {
                touXiangPath = context.getCacheDir().getPath() + File.separator;
            } else if (context.getFilesDir() != null) {
                touXiangPath = context.getFilesDir().getPath() + File.separator;
            } else {
                touXiangPath = File.separator + "data" + File.separator + "data" + File.separator + context.getPackageName() + File.separator;
            }
        }
        return touXiangPath;
    }
}
