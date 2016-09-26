package com.jimei.xiaolumeimei.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jimei.xiaolumeimei.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by itxuye(www.itxuye.com) on 2016/03/15.
 * <p>
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class ImageAdapter extends PagerAdapter {

    private List<String> datas = new ArrayList<String>();
    private LayoutInflater inflater;
    private Context context;
    private Activity activity;

    public ImageAdapter(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
        this.inflater = LayoutInflater.from(context);
    }

    public void setDatas(List<String> datas) {
        if (datas != null) this.datas = datas;
    }

    @Override
    public int getCount() {
        if (datas == null) return 0;
        return datas.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = inflater.inflate(R.layout.item_pager_image, container, false);
        if (view != null) {
            final ImageView imageView = (ImageView) view.findViewById(R.id.image);
            final String imgurl = datas.get(position);
            Glide.with(context).load(imgurl)
                    .thumbnail(0.2f).into(imageView);
            imageView.setOnClickListener(v -> activity.finish());
            container.addView(view, 0);
        }
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}
