package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import butterknife.Bind;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.NinePicAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.NinePicBean;
import com.jimei.xiaolumeimei.model.MMProductModel;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import java.util.List;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye(www.itxuye.com) on 2016/02/29.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class MMNinePicActivity extends BaseSwipeBackCompatActivity
    implements SwipeRefreshLayout.OnRefreshListener {

  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.circleLv) ListView circleLv;
  @Bind(R.id.mRefreshLayout) SwipeRefreshLayout mRefreshLayout;

  private NinePicAdapter mAdapter;

  @Override protected void setListener() {
    mRefreshLayout.setOnRefreshListener(this);
  }

  @Override protected void initData() {

    loadData();
  }

  private void loadData() {
    MMProductModel.getInstance()
        .getNinePic()
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<List<NinePicBean>>() {
          @Override public void onNext(List<NinePicBean> ninePicBean) {
            if (ninePicBean != null) {

              mAdapter.setDatas(ninePicBean);
              mAdapter.notifyDataSetChanged();
            }
          }
        });
  }

  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.activity_ninepic;
  }

  @Override protected void initViews() {

    toolbar.setTitle("");
    setSupportActionBar(toolbar);
    finishBack(toolbar);

    mRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
        android.R.color.holo_green_light, android.R.color.holo_orange_light,
        android.R.color.holo_red_light);

    mAdapter = new NinePicAdapter(this);
    circleLv.setAdapter(mAdapter);
  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }

  @Override public void onRefresh() {
    new Handler().postDelayed(new Runnable() {
      @Override public void run() {
        loadData();
        mRefreshLayout.setRefreshing(false);
      }
    }, 2000);
  }
}
