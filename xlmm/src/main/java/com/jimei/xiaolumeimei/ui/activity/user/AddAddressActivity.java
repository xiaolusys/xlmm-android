package com.jimei.xiaolumeimei.ui.activity.user;

import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;

/**
 * Created by itxuye(www.itxuye.com) on 2016/01/19.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class AddAddressActivity extends BaseSwipeBackCompatActivity
    implements View.OnClickListener {

  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.name) EditText name;
  @Bind(R.id.mobile) EditText mobile;
  @Bind(R.id.address) EditText address;
  @Bind(R.id.clear_address) EditText clearAddress;
  @Bind(R.id.switch_button) SwitchCompat switchButton;
  @Bind(R.id.save) Button save;

  @Override protected void setListener() {
    save.setOnClickListener(this);
  }

  @Override protected void initData() {

  }

  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.addaddress_activity;
  }

  @Override protected void initViews() {

  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ButterKnife.bind(this);
  }

  @Override public void onClick(View v) {

  }
}
