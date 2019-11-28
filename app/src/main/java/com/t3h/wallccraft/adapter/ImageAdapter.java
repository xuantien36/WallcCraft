package com.t3h.wallccraft.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.t3h.wallccraft.R;
import com.t3h.wallccraft.model.ListImage;

import java.util.ArrayList;

public class ImageAdapter extends PagerAdapter {
    private ArrayList<ListImage> data;
    private LayoutInflater inflater;
    private Context context;

    public ImageAdapter(Context context) {
        this.context = context;
    }

    public void setDataList(ArrayList<ListImage> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.item_image_viewpaper, container, false);
        ImageView view;
        view = v.findViewById(R.id.im_new);
        Glide.with(view).load(data.get(position).getThumbUrl()).into(view);
        (container).addView(v, 0);
        return v;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}



