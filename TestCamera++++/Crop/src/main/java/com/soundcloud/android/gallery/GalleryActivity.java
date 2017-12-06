package com.soundcloud.android.gallery;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.GridView;

import com.soundcloud.android.crop.R;

import java.util.List;

/**
 * Created by kellan on 2017/12/4.
 */

public class GalleryActivity extends Activity {
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

    public void okTest(View view) {
        List<GalleryData> list = adapter.getSelected();
        int a=0;
    }
}
