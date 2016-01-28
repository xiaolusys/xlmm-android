package com.jimei.xiaolumeimei.ui.activity.product;

import android.os.Bundle;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.ImageViewAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.ProductDetailBean;
import com.jimei.xiaolumeimei.model.ProductModel;
import com.jimei.xiaolumeimei.widget.NestedListView;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import java.util.ArrayList;
import java.util.List;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye(www.itxuye.com) on 2016/01/25.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class SizeActivity extends BaseSwipeBackCompatActivity {

  @Bind(R.id.longimageview_list) NestedListView longimageviewList;
  private ImageViewAdapter imageViewAdapter;
  ProductModel model = new ProductModel();
  private List<String> list;


  @Override protected void setListener() {

  }

  @Override protected void initData() {
    list = new ArrayList<>();

    model.getProductDetails("32095")
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<ProductDetailBean>() {
          @Override public void onNext(ProductDetailBean productDetailBean) {
            super.onNext(productDetailBean);

            List<String> contentImgs =
                productDetailBean.getProductModel().getContentImgs();
            imageViewAdapter.update(contentImgs);

          }
        });
  }

  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.activity_size;
  }

  @Override protected void initViews() {
    imageViewAdapter = new ImageViewAdapter(this,list);
    longimageviewList.setAdapter(imageViewAdapter);

  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // TODO: add setContentView(...) invocation
    ButterKnife.bind(this);
  }
}
