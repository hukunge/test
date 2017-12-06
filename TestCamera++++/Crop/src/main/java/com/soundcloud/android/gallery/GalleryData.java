package com.soundcloud.android.gallery;

import android.net.Uri;

import java.io.File;
import java.io.Serializable;

/**
 * Created by kellan on 2017/12/4.
 */

public class GalleryData implements Serializable{
    public File file;
    public Uri uri;
    public boolean isSelected;

    public GalleryData(Uri uri) {
        this.uri = uri;
    }
}
