package com.jimei.xiaolumeimei.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.BrandlistAdapter;

/**
 * Created by itxuye(www.itxuye.com) on 2016/04/29.
 * <p>
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class BrandView extends LinearLayout {
    private ImageView brandtitleImage;
    private TextView brandText;
    private RecyclerView brandRcy;
    private Context mContext;

    public BrandView(Context context) {
        this(context, null);
        this.mContext = context;
    }

    public BrandView(Context context, AttributeSet attrs) {
        this(context, null, 0);
        this.mContext = context;
    }

    public BrandView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init(context, attrs);
    }

    public void addItemDecoration(int decoration) {
        brandRcy.addItemDecoration(new SpaceItemDecoration(decoration));
    }

    public void setAdapter(BrandlistAdapter adapter) {

        brandRcy.setAdapter(adapter);
    }

    private void init(Context context, AttributeSet attrs) {
        View view = LayoutInflater.from(context).inflate(R.layout.brandview, this, true);
        brandtitleImage = (ImageView) view.findViewById(R.id.brandtitle_image);
        brandText = (TextView) view.findViewById(R.id.brand_text);
        brandRcy = (RecyclerView) view.findViewById(R.id.brand_rcy);

        TypedArray a = null;
        if (attrs != null) {
            try {

                a = context.obtainStyledAttributes(attrs, R.styleable.BrandView);
                brandText.setText(a.getString(R.styleable.BrandView_brand_des));

            } finally {

                if (a != null) {
                    a.recycle();
                }
            }

        }
    }

    public void setBrandtitleImage(String picpath) {
        Glide.with(mContext)
                .load(picpath)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(brandtitleImage);
    }

    public void setBrandDesText(String text) {
        brandText.setText(text);
    }

}
