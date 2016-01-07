package com.jimei.xiaolumeimei.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import butterknife.Bind;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.TongkuanAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.data.XlmmApi;
import com.jimei.xiaolumeimei.model.ProductBean;
import com.jimei.xiaolumeimei.okhttp.callback.OkHttpCallback;
import com.jimei.xiaolumeimei.okhttp.request.OkHttpRequest;
import com.jimei.xiaolumeimei.widget.SpaceItemDecoration;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.victor.loading.rotate.RotateLoading;
import java.util.List;

/**
 * Created by itxuye(www.itxuye.com) on 15/12/29.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class TongkuanActicity extends BaseSwipeBackCompatActivity {

  String TAG = "tongkuanActivity";
  @Bind(R.id.tool_bar) Toolbar toolbar;
  @Bind(R.id.tongkuan_recyclerview) RecyclerView recyclerView;
  @Bind(R.id.loading) RotateLoading loading;
  private TongkuanAdapter mTongkuanAdapter;
  private int model_id;
  private String name = null;

  @Override protected void setListener() {

  }

  @Override protected void initData() {
    loading.start();
    new OkHttpRequest.Builder().url(XlmmApi.TONGKUAN_URL + model_id)
        .get(new OkHttpCallback<List<ProductBean>>() {
          @Override public void onError(Request request, Exception e) {
            //Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
          }

          @Override public void onResponse(Response response, List<ProductBean> data) {
            mTongkuanAdapter.update(data);
            mTongkuanAdapter.notifyDataSetChanged();
            loading.stop();
          }
        });
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
}
