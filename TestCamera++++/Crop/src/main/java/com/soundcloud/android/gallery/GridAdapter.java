package com.soundcloud.android.gallery;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.soundcloud.android.crop.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kellan on 2017/12/4.
 */

public class GridAdapter extends BaseAdapter {
    private final Activity ctx;
    private List<GalleryData> data;
    private List<GalleryData> selected;

    public GridAdapter(Activity ctx) {
        this.ctx = ctx;
        this.selected = new ArrayList<>();
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
            h.view = v.findViewById(R.id.select);

            h.view.setOnClickListener(view -> {
                GalleryData gd = data.get(position);
                gd.isSelected = !gd.isSelected;
                notifyDataSetChanged();

                addOrRemove(gd);
            });

            v.setTag(h);
        } else {
            h = (ViewHolder) v.getTag();
        }

        GalleryData gd = data.get(position);

        if(gd.isSelected){
            h.view.setImageResource(R.drawable.crop__ic_done);
        }else {
            h.view.setImageResource(R.drawable.crop__ic_cancel);
        }

        Glide.with(ctx)
                .load(gd.uri)
                .into(h.imageView);

        return v;
    }

    private void addOrRemove(GalleryData d){
        if(d.isSelected){
            selected.add(d);
        }else {
            selected.remove(d);
        }
    }

    public List<GalleryData> getSelected(){
        return selected;
    }

    private static class ViewHolder {
        public ImageView imageView;
        public ImageView view;
    }
}
