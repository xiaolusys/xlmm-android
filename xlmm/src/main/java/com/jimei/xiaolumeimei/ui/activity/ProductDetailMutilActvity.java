package com.jimei.xiaolumeimei.ui.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import butterknife.Bind;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.data.XlmmApi;
import com.jimei.xiaolumeimei.model.ProductDetailMutilBean;
import com.jimei.xiaolumeimei.okhttp.callback.OkHttpCallback;
import com.jimei.xiaolumeimei.okhttp.request.OkHttpRequest;
import com.jimei.xiaolumeimei.utils.DensityUtils;
import com.jimei.xiaolumeimei.utils.DisplayUtils;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.zhy.view.flowlayout.TagFlowLayout;
import java.util.List;

/**
 * Created by itxuye(www.itxuye.com) on 15/12/29.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class ProductDetailMutilActvity extends BaseSwipeBackCompatActivity {

  @Bind(R.id.titleImage) ImageView titleImage;
  //@Bind(R.id.toolbar) Toolbar toolbar;
  //@Bind(R.id.collapsing_toolbar_layout) CollapsingToolbarLayout collapsingToolbarLayout;
  //@Bind(R.id.coordinatorLayout) CoordinatorLayout coordinatorLayout;
  @Bind(R.id.productdetail_coordinatorlayout) RelativeLayout
      productdetailCoordinatorlayout;
  @Bind(R.id.id_flowlayout) TagFlowLayout idFlowlayout;
  @Bind(R.id.image_detail) ImageView imageDetail;
  private String productId;

  @Override protected void setListener() {

  }

  @Override protected void initData() {
    new OkHttpRequest.Builder().url(XlmmApi.PRODUCT_URL + productId + "/details")
        .get(new OkHttpCallback<ProductDetailMutilBean>() {
          @Override public void onError(Request request, Exception e) {
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.i("什么错误啊", e.getMessage());

          }

          @Override public void onResponse(Response response, ProductDetailMutilBean data) {
            String headImg = data.getPicPath();
            List<String> contentImgs = data.getProductModel().getContentImgs();
            Log.i("ProductDetailActivity", contentImgs.get(0));
            Log.i("ProductDetailActivity", headImg);
            Glide.with(mContext)
                .load(headImg)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(titleImage);

            Glide.with(mContext)
                .load(contentImgs.get(0))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .override(DisplayUtils.getScreenW(ProductDetailMutilActvity.this),
                    DensityUtils.dip2px(getApplicationContext(), 2500))
                .centerCrop()
                .into(imageDetail);
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

    Window window = getWindow();
    //4.4版本及以上
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      window.setFlags(
          WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
          WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }
    //5.0版本及以上
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
          | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
      window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
          | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
          | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
      window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
      window.setStatusBarColor(Color.TRANSPARENT);
      window.setNavigationBarColor(Color.TRANSPARENT);
  }}
  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }
}
