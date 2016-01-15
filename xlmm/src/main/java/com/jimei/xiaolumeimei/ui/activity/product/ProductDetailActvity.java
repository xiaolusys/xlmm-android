package com.jimei.xiaolumeimei.ui.activity.product;

import android.content.Intent;
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
import android.widget.TextView;
import butterknife.Bind;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ecloud.pulltozoomview.PullToZoomScrollViewEx;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.AddCartsBean;
import com.jimei.xiaolumeimei.entities.ProductDetailBean;
import com.jimei.xiaolumeimei.model.CartsModel;
import com.jimei.xiaolumeimei.model.ProductModel;
import com.jimei.xiaolumeimei.ui.activity.user.LoginActivity;
import com.jimei.xiaolumeimei.utils.LoginUtils;
import com.jimei.xiaolumeimei.widget.FlowLayout;
import com.jimei.xiaolumeimei.widget.TagAdapter;
import com.jimei.xiaolumeimei.widget.TagFlowLayout;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye(www.itxuye.com) on 15/12/29.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class ProductDetailActvity extends BaseSwipeBackCompatActivity
    implements View.OnClickListener, TagFlowLayout.OnSelectListener,
    TagFlowLayout.OnTagClickListener {

  ProductModel model = new ProductModel();
  @Bind(R.id.shopping_image) ImageView shopping_imageView;
  List<ProductDetailBean.NormalSkusEntity> normalSkus = new ArrayList<>();
  CartsModel cartsModel = new CartsModel();
  private ImageView titleImage, detailImage;
  private PullToZoomScrollViewEx scrollView;
  private TagFlowLayout tagFlowLayout;
  private String productId;
  private LayoutInflater mInflater;
  private String item_id;
  private String sku_id;

  @Override protected void setListener() {

  }

  @Override protected void initData() {

    model.getProductDetails(productId)
        .subscribeOn(Schedulers.newThread())
        .subscribe(new ServiceResponse<ProductDetailBean>() {

          @Override public void onNext(ProductDetailBean productDetailBean) {
            String headImg = productDetailBean.getPicPath();
            List<String> contentImgs =
                productDetailBean.getProductModel().getContentImgs();

            List<String> contentImgs1 = productDetailBean.getDetails().getContentImgs();

            titleImage.post(() -> Glide.with(mContext)
                .load(headImg)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(titleImage));

            //if (contentImgs != null) {
            detailImage.post(() -> Glide.with(mContext)
                .load(contentImgs.get(0))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(detailImage));
            //} else {
            //  detailImage.post(() -> Glide.with(mContext)
            //      .load(contentImgs1.get(0))
            //      .diskCacheStrategy(DiskCacheStrategy.ALL)
            //      .centerCrop()
            //      .into(detailImage));
            //}

            item_id = productDetailBean.getId();

            normalSkus.addAll(productDetailBean.getNormalSkus());

            tagFlowLayout.setAdapter(
                new TagAdapter<ProductDetailBean.NormalSkusEntity>(normalSkus) {

                  @Override public View getView(FlowLayout parent, int position,
                      ProductDetailBean.NormalSkusEntity normalSkusEntity) {
                    TextView tv =
                        (TextView) mInflater.inflate(R.layout.tv, tagFlowLayout, false);
                    tv.setText(normalSkus.get(position).getName());
                    return tv;
                  }
                });

            tagFlowLayout.setOnTagClickListener(ProductDetailActvity.this);
            tagFlowLayout.setOnSelectListener(ProductDetailActvity.this);
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
    mInflater = LayoutInflater.from(this);
    Window window = getWindow();
    //4.4版本及以上
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
          WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }
    //5.0版本及以上
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
      window.getDecorView()
          .setSystemUiVisibility(
              View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
      window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
      window.setStatusBarColor(Color.TRANSPARENT);
      //window.setNavigationBarColor(Color.TRANSPARENT);
    }

    scrollView = (PullToZoomScrollViewEx) findViewById(R.id.pulltoZoomScrollView);
    detailImage =
        (ImageView) scrollView.getPullRootView().findViewById(R.id.image_details);

    tagFlowLayout =
        (TagFlowLayout) scrollView.getPullRootView().findViewById(R.id.id_flowlayout);

    DisplayMetrics localDisplayMetrics = new DisplayMetrics();
    getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
    int mScreenHeight = localDisplayMetrics.heightPixels;
    int mScreenWidth = localDisplayMetrics.widthPixels;
    LinearLayout.LayoutParams localObject = new LinearLayout.LayoutParams(mScreenWidth,
        (int) (9.0F * (mScreenWidth / 16.0F)));
    scrollView.setHeaderLayoutParams(localObject);

    shopping_imageView.setOnClickListener(this);
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
    View contentView =
        LayoutInflater.from(this).inflate(R.layout.pull_zoom_contentview, null, false);
    scrollView.setHeaderView(headView);

    //detailImage = (ImageView) contentView.findViewById(R.id.image_details);
    titleImage = (ImageView) zoomView.findViewById(R.id.iv_zoom);
    scrollView.setZoomView(zoomView);
    scrollView.setScrollContentView(contentView);
  }

  @Override public void onClick(View v) {
    switch (v.getId()) {
      case R.id.shopping_image:

        String[] loginInfo = LoginUtils.getLoginInfo(getApplicationContext());
        boolean b = Boolean.parseBoolean(loginInfo[2]);
        if (!b) {
          startActivity(new Intent(ProductDetailActvity.this, LoginActivity.class));
        } else if (sku_id == null) {
          JUtils.Toast("请选择尺码");
        } else {
          JUtils.Log(sku_id + "这尺寸可以");

          cartsModel.addCarts(item_id, sku_id)
              .subscribeOn(Schedulers.newThread())
              .subscribe(new ServiceResponse<AddCartsBean>() {
                @Override public void onNext(AddCartsBean addCartsBean) {
                  super.onNext(addCartsBean);
                  JUtils.Log("成功加入购物车");
                }
              });
        }

        break;
    }
  }

  @Override public void onSelected(Set<Integer> selectPosSet) {

  }

  @Override public boolean onTagClick(View view, int position, FlowLayout parent) {
    sku_id = normalSkus.get(position).getId();
    JUtils.Toast(sku_id + "   ,...." + item_id);
    return true;
  }
}
