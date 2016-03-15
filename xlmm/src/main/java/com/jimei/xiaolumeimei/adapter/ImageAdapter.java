package com.jimei.xiaolumeimei.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.jimei.xiaolumeimei.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by itxuye(www.itxuye.com) on 2016/03/15.
 *
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

  @Override public int getCount() {
    if (datas == null) return 0;
    return datas.size();
  }

  @Override public Object instantiateItem(ViewGroup container, final int position) {
    View view = inflater.inflate(R.layout.item_pager_image, container, false);
    if (view != null) {
      final ImageView imageView = (ImageView) view.findViewById(R.id.image);
      //预览imageView
      //final ImageView smallImageView = new ImageView(context);
      //
      //smallImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
      //((FrameLayout) view).addView(smallImageView);
      //loading
      final ProgressBar loading = new ProgressBar(context);
      FrameLayout.LayoutParams loadingLayoutParams =
          new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
              ViewGroup.LayoutParams.WRAP_CONTENT);
      loadingLayoutParams.gravity = Gravity.CENTER;
      loading.setLayoutParams(loadingLayoutParams);
      ((FrameLayout) view).addView(loading);

      final String imgurl = datas.get(position);

      Picasso.with(context)
          .load(imgurl + "?imageMogr2/thumbnail/350/format/jpg/quality/70")
          .into(imageView, new Callback() {
            @Override public void onSuccess() {
              loading.setVisibility(View.GONE);
              //smallImageView.setVisibility(View.GONE);
            }

            @Override public void onError() {
            }
          });

      imageView.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          activity.finish();
        }
      });

      container.addView(view, 0);
    }
    return view;
  }

  @Override public void destroyItem(ViewGroup container, int position, Object object) {
    container.removeView((View) object);
  }

  @Override public boolean isViewFromObject(View view, Object object) {
    return view.equals(object);
  }

  @Override public void restoreState(Parcelable state, ClassLoader loader) {
  }

  @Override public Parcelable saveState() {
    return null;
  }
}
