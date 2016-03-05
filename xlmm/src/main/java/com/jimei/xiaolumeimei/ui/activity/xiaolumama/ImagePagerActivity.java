package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import butterknife.Bind;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by itxuye(www.itxuye.com) on 2016/02/29.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class ImagePagerActivity extends BaseSwipeBackCompatActivity {

  public static final String INTENT_IMGURLS = "imgurls";
  public static final String INTENT_POSITION = "position";
  @Bind(R.id.pager) ViewPager viewPager;
  @Bind(R.id.guideGroup) LinearLayout guideGroup;
  //public static ImageSize imageSize;
  private List<View> guideViewList = new ArrayList<>();

  public static void startImagePagerActivity(Context context, List<String> imgUrls,
      int position) {
    Intent intent = new Intent(context, ImagePagerActivity.class);
    intent.putStringArrayListExtra(INTENT_IMGURLS, new ArrayList<>(imgUrls));
    intent.putExtra(INTENT_POSITION, position);
    context.startActivity(intent);
  }

  @Override protected void setListener() {

  }

  @Override protected void initData() {

  }

  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.activity_imagepager;
  }

  @Override protected void initViews() {

    int startPos = getIntent().getIntExtra(INTENT_POSITION, 0);
    ArrayList<String> imgUrls = getIntent().getStringArrayListExtra(INTENT_IMGURLS);

    ImageAdapter mAdapter = new ImageAdapter(this);
    mAdapter.setDatas(imgUrls);
    viewPager.setAdapter(mAdapter);
    viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

      @Override public void onPageScrolled(int position, float positionOffset,
          int positionOffsetPixels) {

      }

      @Override public void onPageSelected(int position) {
        for (int i = 0; i < guideViewList.size(); i++) {
          guideViewList.get(i).setSelected(i == position ? true : false);
        }
      }

      @Override public void onPageScrollStateChanged(int state) {

      }
    });
    viewPager.setCurrentItem(startPos);

    addGuideView(guideGroup, startPos, imgUrls);
  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }

  private void addGuideView(LinearLayout guideGroup, int startPos,
      ArrayList<String> imgUrls) {
    if (imgUrls != null && imgUrls.size() > 0) {
      guideViewList.clear();
      for (int i = 0; i < imgUrls.size(); i++) {
        View view = new View(this);
        view.setBackgroundResource(R.drawable.selector_guide_bg);
        view.setSelected(i == startPos ? true : false);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
            getResources().getDimensionPixelSize(R.dimen.gudieview_width),
            getResources().getDimensionPixelSize(R.dimen.gudieview_heigh));
        layoutParams.setMargins(10, 0, 0, 0);
        guideGroup.addView(view, layoutParams);
        guideViewList.add(view);
      }
    }
  }

  private static class ImageAdapter extends PagerAdapter {

    private List<String> datas = new ArrayList<String>();
    private LayoutInflater inflater;
    private Context context;

    public ImageAdapter(Context context) {
      this.context = context;
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
        final ImageView smallImageView = new ImageView(context);

        smallImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ((FrameLayout) view).addView(smallImageView);
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
            .load(imgurl + "?imageMogr2/thumbnail/578/format/jpg/quality/90")
            .into(imageView, new Callback() {
              @Override public void onSuccess() {
                loading.setVisibility(View.GONE);
                smallImageView.setVisibility(View.GONE);
              }

              @Override public void onError() {
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
}
