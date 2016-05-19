package com.jimei.xiaolumeimei.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import butterknife.Bind;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.AddressSelectAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.AddressBean;
import com.jimei.xiaolumeimei.model.AddressModel;
import com.jimei.xiaolumeimei.widget.DividerItemDecoration;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import java.util.List;
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

  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.address_activity;
  }

  @Override protected void initViews() {
    addressRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    addressRecyclerView.addItemDecoration(
        new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
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
