package com.jimei.xiaolumeimei.ui.debug;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import butterknife.Bind;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;

/**
 * Created by itxuye(www.itxuye.com) on 2016/05/25.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class DebugActivity extends BaseSwipeBackCompatActivity
    implements DebugContract.View, View.OnClickListener,
    RadioGroup.OnCheckedChangeListener {

  @Bind(R.id.edit_debug) EditText editDebug;
  @Bind(R.id.bt_debug) Button btDebug;
  String textDebug;
  String textPass;
  @Bind(R.id.edit_pass) EditText editPass;
  @Bind(R.id.staging) RadioButton staging;
  @Bind(R.id.m) RadioButton m;
  @Bind(R.id.xiuqing) RadioButton xiuqing;
  @Bind(R.id.huang) RadioButton huang;
  @Bind(R.id.lin) RadioButton lin;
  @Bind(R.id.lei) RadioButton lei;
  @Bind(R.id.enjun) RadioButton enjun;
  @Bind(R.id.shawn) RadioButton shawn;
  @Bind(R.id.rg) RadioGroup rg;

  @Override protected void setListener() {
    btDebug.setOnClickListener(this);
    rg.setOnCheckedChangeListener(this);
  }

  @Override protected void initData() {

  }

  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.activity_debug;
  }

  @Override protected void initViews() {
    DaggerDebugComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .debugModule(new DebugModule(this))
        .build()
        .inject(this);
  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }

  @Override public void setPresenter(DebugContract.Presenter presenter) {

  }

  @Override public void onClick(View v) {

  }

  @Override public void onCheckedChanged(RadioGroup group, int checkedId) {
    switch (checkedId) {
      case R.id.staging:
        editDebug.setText(staging.getText().toString().trim());
        break;

      case R.id.m:
        editDebug.setText(m.getText().toString().trim());
        break;

      case R.id.xiuqing:
        editDebug.setText(xiuqing.getText().toString().trim());
        break;

      case R.id.huang:
        editDebug.setText(huang.getText().toString().trim());
        break;

      case R.id.lin:
        editDebug.setText(lin.getText().toString().trim());
        break;

      case R.id.lei:
        editDebug.setText(lei.getText().toString().trim());
        break;

      case R.id.enjun:
        editDebug.setText(enjun.getText().toString().trim());
        break;
      case R.id.shawn:
        editDebug.setText(shawn.getText().toString().trim());
        break;
    }
  }
}
