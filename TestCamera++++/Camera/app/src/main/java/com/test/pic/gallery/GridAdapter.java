package com.test.pic.gallery;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.soundcloud.android.crop.R;
import com.test.pic.App;

import java.io.File;
import java.util.List;

/**
 * Created by Administrator on 2017/12/4.
 */

public class GridAdapter extends BaseAdapter {
    private final Activity ctx;
    private List<GalleryData> data;

    public GridAdapter(Activity ctx) {
        this.ctx = ctx;
    }

    public void setData(List<GalleryData> data) {
        this.data = data;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data ==null ? 0 : data.size();
    }

    @Override
    public GalleryData getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View v, ViewGroup viewGroup) {
        ViewHolder h;

        if (v == null) {
            v = View.inflate(ctx, R.layout.gallery_item, null);
            h = new ViewHolder();

            h.imageView = v.findViewById(R.id.image_view_image_select);
            h.view = v.findViewById(R.id.view_alpha);

            v.setTag(h);
        } else {
            h = (ViewHolder) v.getTag();
        }

        GalleryData gd = data.get(position);
//        if (gd.isSelected) {
//            viewHolder.view.setAlpha(0.5f);
//        } else {
//            viewHolder.view.setAlpha(0.0f);
//        }

        Glide.with(ctx)
                .load(gd.uri)
//                .load(new File(gd.uri.getPath()))
                .into(h.imageView);

        return v;
    }

    private static class ViewHolder {
        public ImageView imageView;
        public View view;
    }
}
