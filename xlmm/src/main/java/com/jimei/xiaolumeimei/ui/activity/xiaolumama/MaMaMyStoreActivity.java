package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import butterknife.Bind;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.MaMaStoreAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.MMChooselistBean;
import com.jimei.xiaolumeimei.model.MMProductModel;
import com.jimei.xiaolumeimei.widget.DividerItemDecoration;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.victor.loading.rotate.RotateLoading;
import java.util.List;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye(www.itxuye.com) on 2016/02/15.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class MaMaMyStoreActivity extends BaseSwipeBackCompatActivity {
  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.store_rcyView) RecyclerView storeRcyView;
  @Bind(R.id.loading) RotateLoading loading;
  private MaMaStoreAdapter maMaStoreAdapter;

  @Override protected void setListener() {

  }

  @Override protected void initData() {

    MMProductModel.getInstance()
        .getMMStoreList()
        .subscribeOn(Schedulers.newThread())
        .subscribe(new ServiceResponse<List<MMChooselistBean>>() {
          @Override public void onNext(List<MMChooselistBean> mmChooselistBeans) {
            super.onNext(mmChooselistBeans);
            try {
              if (mmChooselistBeans != null) {
                maMaStoreAdapter.update(mmChooselistBeans);
              }
            } catch (NullPointerException ex) {
            }
          }

          @Override public void onCompleted() {
            super.onCompleted();
          }

          @Override public void onError(Throwable e) {
            super.onError(e);
          }
        });
  }

  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.activity_store;
  }

  @Override protected void initViews() {
    initRecyclerView();
  }

  private void initRecyclerView() {
    storeRcyView.setLayoutManager(new LinearLayoutManager(this));
    storeRcyView.addItemDecoration(
        new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

    maMaStoreAdapter = new MaMaStoreAdapter(this);
    storeRcyView.setAdapter(maMaStoreAdapter);
  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }
}
