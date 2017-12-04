package com.test.pic.gallery;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.GridView;

import com.soundcloud.android.crop.R;

/**
 * Created by Administrator on 2017/12/4.
 */

public class GalleryActivity extends Activity{
    private GridView grid;
    private GridAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery);

        grid = findViewById(R.id.gridView);
        adapter = new GridAdapter(GalleryActivity.this);
        grid.setAdapter(adapter);

        adapter.setData(DataUtil.getData(GalleryActivity.this));
    }
}
