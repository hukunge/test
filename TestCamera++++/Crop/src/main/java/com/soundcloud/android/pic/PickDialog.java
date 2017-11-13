package com.soundcloud.android.pic;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;

/**
 * Created by Kellan on 2017/11/8.
 */

public class PickDialog {

    public static void showPickDialog(Activity activity, final PickInfo info) {
        AlertDialog.Builder build;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            build = new AlertDialog.Builder(activity, AlertDialog.THEME_HOLO_LIGHT);
        } else {
            build = new AlertDialog.Builder(activity);
        }
        build.setMessage("选择图片").setPositiveButton("相册",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (info != null) info.gallery();
                    }
                })
                .setNegativeButton("拍照", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (info != null) info.takePic();
                    }
                })
                .create()
                .show();
    }
}
