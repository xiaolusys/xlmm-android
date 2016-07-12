package com.jimei.xiaolumeimei.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.utils.DisplayUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import okhttp3.Call;

/**
 * Created by itxuye(www.itxuye.com) on 2016/04/29.
 * <p>
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class BrandView extends LinearLayout {
    private ImageView brandtitleImage;
    private ImageView brandListImage;
    private TextView brandText;
    private TextView brandTitle;
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

    private void init(Context context, AttributeSet attrs) {
        View view = LayoutInflater.from(context).inflate(R.layout.brandview, this, true);
        brandtitleImage = (ImageView) view.findViewById(R.id.brandtitle_image);
        brandListImage = (ImageView) view.findViewById(R.id.brandList_image);
        brandText = (TextView) view.findViewById(R.id.brand_text);
        brandTitle = (TextView) view.findViewById(R.id.brand_title);

        TypedArray a = null;
        if (attrs != null) {
            try {

                a = context.obtainStyledAttributes(attrs, R.styleable.BrandView);
                brandText.setText(a.getString(R.styleable.BrandView_brand_des));
                brandTitle.setText(a.getString(R.styleable.BrandView_brand_title));

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

    public void setBrandListImage(String picPath) {
        OkHttpUtils
            .get()
            .url(picPath)
            .build()
            .execute(new BitmapCallback() {
                @Override public void onError(Call call, Exception e) {
                    e.printStackTrace();
                }

                @Override public void onResponse(Bitmap response) {
                    brandListImage.setAdjustViewBounds(true);
                    int screenWidth = DisplayUtils.getScreenW((Activity) mContext);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(screenWidth,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                    brandListImage.setLayoutParams(lp);
                    brandListImage.setMaxWidth(screenWidth);
                    brandListImage.setMaxHeight(screenWidth * 5);
                    brandListImage.setImageBitmap(response);
                }
            });
    }

    public void setBrandDesText(String text) {
        brandText.setText(text);
    }


    public void setBrandTitle(String text) {
        brandTitle.setText(text);
    }

}
