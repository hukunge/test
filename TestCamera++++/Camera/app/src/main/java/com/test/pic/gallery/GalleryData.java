package com.test.pic.gallery;

import android.net.Uri;

import java.io.File;

/**
 * Created by Administrator on 2017/12/4.
 */

public class GalleryData {
    public File file;
    public Uri uri;
    public boolean isSelected;

    public GalleryData(Uri uri) {
        this.uri = uri;
    }

//    public GalleryData(File file) {
//        this.file = file;
//    }
}
