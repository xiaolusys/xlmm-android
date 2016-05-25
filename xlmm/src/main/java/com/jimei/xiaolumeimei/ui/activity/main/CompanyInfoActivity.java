package com.jimei.xiaolumeimei.ui.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.data.XlmmConst;

import butterknife.Bind;


/**
 * Created by itxuye(www.itxuye.com) on 2016/01/19.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class CompanyInfoActivity extends BaseSwipeBackCompatActivity implements View.OnClickListener {

  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.tv_version) TextView tv_version;
  @Bind(R.id.imageView2)
  ImageView imageView2;
  private int num;

  @Override protected void setListener() {
    imageView2.setOnClickListener(this);
  }

  @Override protected void initData() {

  }

  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.companyinfo_activity;
  }

  @Override protected void initViews() {

    toolbar.setTitle("");
    setSupportActionBar(toolbar);
    finishBack(toolbar);
    tv_version.setText(XlmmConst.VERSION);
  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public void onClick(View v) {
    if (v.getId() == R.id.imageView2) {
      num++;
      if (num == 8) {
        Intent intent = new Intent(this, DebugActivity.class);
        startActivity(intent);
      } else if (num == 9) {
        num = 0;
      }
    }
  }
}
