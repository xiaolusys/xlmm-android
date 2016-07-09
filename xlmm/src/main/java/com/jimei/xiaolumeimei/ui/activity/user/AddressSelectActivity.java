package com.jimei.xiaolumeimei.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.AddressSelectAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.AddressBean;
import com.jimei.xiaolumeimei.model.AddressModel;
import com.jimei.xiaolumeimei.widget.DividerItemDecoration;
import com.jimei.xiaolumeimei.widget.RecycleViewDivider;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import butterknife.Bind;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye(www.itxuye.com) on 2016/01/18.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class AddressSelectActivity extends BaseSwipeBackCompatActivity
    implements View.OnClickListener {

  @Bind(R.id.address_recyclerView) RecyclerView addressRecyclerView;
  @Bind(R.id.addAdress) Button addAdress;
  private AddressSelectAdapter adapter;

  @Override protected void setListener() {
    addAdress.setOnClickListener(this);
  }

  @Override protected void initData() {
  }

  @Override protected void onResume() {
    super.onResume();
    MobclickAgent.onPageStart(this.getClass().getSimpleName());
    MobclickAgent.onResume(this);
    Subscription subscribe = AddressModel.getInstance()
        .getAddressList()
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<List<AddressBean>>() {
          @Override public void onNext(List<AddressBean> list) {
            super.onNext(list);
            if (list != null) {
              adapter.updateWithClear(list);
            }
          }
        });
    addSubscription(subscribe);
  }

  @Override
  protected void onPause() {
    super.onPause();
    MobclickAgent.onPageEnd(this.getClass().getSimpleName());
    MobclickAgent.onPause(this);
  }

  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.address_activity;
  }

  @Override protected void initViews() {
    addressRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    RecycleViewDivider divider = new RecycleViewDivider(RecycleViewDivider.VERTICAL);
    divider.setSize(3);
    divider.setColor(getResources().getColor(R.color.bg_grey));
    addressRecyclerView.addItemDecoration(divider);
    adapter = new AddressSelectAdapter(this);
    addressRecyclerView.setAdapter(adapter);
  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }

  @Override public void onClick(View v) {
    switch (v.getId()) {
      case R.id.addAdress:

        startActivity(new Intent(AddressSelectActivity.this, AddNoAddressActivity.class));
        break;
    }
  }


}
