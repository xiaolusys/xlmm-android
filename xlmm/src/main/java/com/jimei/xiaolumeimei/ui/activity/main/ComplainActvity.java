package com.jimei.xiaolumeimei.ui.activity.main;

import android.os.Bundle;
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
public class ComplainActvity extends BaseSwipeBackCompatActivity
    implements View.OnClickListener {
  @Bind(R.id.tijiao) Button confirm;
  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.complain_text) EditText complainText;

  @Override protected void setListener() {
confirm.setOnClickListener(this);
  }

  @Override protected void initData() {

  }

  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.complain_activity;
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
    // TODO: add setContentView(...) invocation
    ButterKnife.bind(this);
  }

  @Override public void onClick(View v) {

    if (v.getId() == R.id.tijiao) {
      String text = complainText.getText().toString().trim();



    }
  }
}
