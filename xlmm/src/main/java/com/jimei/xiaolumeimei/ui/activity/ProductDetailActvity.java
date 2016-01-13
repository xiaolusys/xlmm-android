package com.jimei.xiaolumeimei.ui.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ecloud.pulltozoomview.PullToZoomScrollViewEx;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.ProductDetailBean;
import com.jimei.xiaolumeimei.model.ProductModel;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye(www.itxuye.com) on 15/12/29.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class ProductDetailActvity extends BaseSwipeBackCompatActivity {

  ProductModel model = new ProductModel();
  ImageView titleImage;
  PullToZoomScrollViewEx scrollView;
  ////@Bind(R.id.toolbar) Toolbar toolbar;
  ////@Bind(R.id.collapsing_toolbar_layout) CollapsingToolbarLayout collapsingToolbarLayout;
  ////@Bind(R.id.coordinatorLayout) CoordinatorLayout coordinatorLayout;
  //@Bind(R.id.productdetail_coordinatorlayout) RelativeLayout
  //    productdetailCoordinatorlayout;
  //@Bind(R.id.id_flowlayout) TagFlowLayout idFlowlayout;
  //@Bind(R.id.image_detail) ImageView imageDetail;
  private String productId;

  @Override protected void setListener() {

  }

  @Override protected void initData() {

    model.getProductDetails(productId)
        .subscribeOn(Schedulers.newThread())
        .subscribe(new ServiceResponse<ProductDetailBean>() {

          @Override public void onNext(ProductDetailBean productDetailBean) {
            String headImg = productDetailBean.getPicPath();
            //List<String> contentImgs =
            //    productDetailBean.getProductModel().getContentImgs();
            //Log.i("ProductDetailActivity", contentImgs.get(0));
            //Log.i("ProductDetailActivity", headImg);

            titleImage.post(() -> Glide.with(mContext)
                .load(headImg)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(titleImage));

            //imageDetail.post(() -> Glide.with(mContext)
            //    .load(contentImgs.get(0))
            //    .diskCacheStrategy(DiskCacheStrategy.ALL)
            //    .override(DisplayUtils.getScreenW(ProductDetailActvity.this),
            //        DensityUtils.dip2px(getApplicationContext(), 2500))
            //    .centerCrop()
            //    .into(imageDetail));
          }
        });
  }

  @Override protected void getBundleExtras(Bundle extras) {
    productId = extras.getString("product_id");
    //Log.i("ProductDetailActivity", productId);
  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.productdetail_activity;
  }

  @Override protected void initViews() {
    loadViewForCode();

    Window window = getWindow();
    //4.4版本及以上
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
          WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }
    //5.0版本及以上
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
          | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
      window.getDecorView()
          .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
              | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
              | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
      window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
      window.setStatusBarColor(Color.TRANSPARENT);
      window.setNavigationBarColor(Color.TRANSPARENT);
    }

     scrollView =
        (PullToZoomScrollViewEx) findViewById(R.id.pulltoZoomScrollView);

    DisplayMetrics localDisplayMetrics = new DisplayMetrics();
    getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
    int mScreenHeight = localDisplayMetrics.heightPixels;
    int mScreenWidth = localDisplayMetrics.widthPixels;
    LinearLayout.LayoutParams localObject = new LinearLayout.LayoutParams(mScreenWidth,
        (int) (9.0F * (mScreenWidth / 16.0F)));
    scrollView.setHeaderLayoutParams(localObject);
  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }

  private void loadViewForCode() {
    PullToZoomScrollViewEx scrollView =
        (PullToZoomScrollViewEx) findViewById(R.id.pulltoZoomScrollView);
    View headView =
        LayoutInflater.from(this).inflate(R.layout.productdetail_head_image, null, false);
    View zoomView =
        LayoutInflater.from(this).inflate(R.layout.productdetail_zoom_image, null, false);
    //View zoomView = LayoutInflater.from(this).inflate(R.layout.profile_zoom_view, null, false);
    //View contentView = LayoutInflater.from(this).inflate(R.layout.profile_content_view, null, false);
    scrollView.setHeaderView(headView);

    titleImage = (ImageView) zoomView.findViewById(R.id.iv_zoom);
    scrollView.setZoomView(zoomView);
    //scrollView.setScrollContentView(contentView);
  }
}
