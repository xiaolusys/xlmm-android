package com.jimei.xiaolumeimei.ui.activity.product;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import butterknife.Bind;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.TongkuanAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.ProductBean;
import com.jimei.xiaolumeimei.model.ProductModel;
import com.jimei.xiaolumeimei.widget.SpaceItemDecoration;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import java.util.List;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye(www.itxuye.com) on 15/12/29.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class TongkuanActivity extends BaseSwipeBackCompatActivity {

  String TAG = "tongkuanActivity";
  @Bind(R.id.tool_bar) Toolbar toolbar;
  @Bind(R.id.tongkuan_recyclerview) RecyclerView recyclerView;
  private TongkuanAdapter mTongkuanAdapter;
  private int model_id;
  private String name = null;

  @Override protected void setListener() {

  }

  @Override protected void initData() {
    //loading.start();
    showIndeterminateProgressDialog(false);
   Subscription  subscribe = ProductModel.getInstance()
        .getTongkuanList(model_id)
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<List<ProductBean>>() {
          @Override public void onNext(List<ProductBean> productBeans) {

            try {
              if (productBeans != null) {
                mTongkuanAdapter.update(productBeans);
              }
            } catch (Exception e) {
            }
          }

          @Override public void onCompleted() {
            super.onCompleted();
            //loading.post(loading::stop);
            hideIndeterminateProgressDialog();
          }
        });

    addSubscription(subscribe);
  }

  @Override protected void getBundleExtras(Bundle extras) {

    model_id = extras.getInt("model_id");
    name = extras.getString("name");
    Log.d(TAG, model_id + "");
  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.tonghuan_activity;
  }

  @Override protected void initViews() {
    toolbar.setTitle(name);
    setSupportActionBar(toolbar);
    finishBack(toolbar);

    initRecyclerView();
  }

  private void initRecyclerView() {

    recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    recyclerView.addItemDecoration(new SpaceItemDecoration(10));

    mTongkuanAdapter = new TongkuanAdapter(this);
    recyclerView.setAdapter(mTongkuanAdapter);
  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }

  @Override protected void onStop() {
    super.onStop();
  }
}
