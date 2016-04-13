package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.view.Menu;

import com.jimei.xiaolumeimei.base.CommonWebViewActivity;

/**
 * Created by itxuye(www.itxuye.com) on 2016/03/25.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class FansWebViewActivity extends CommonWebViewActivity {

  @Override
  protected void initViews() {
    super.initViews();
    titleView.setName("我的粉丝");
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    return false;
  }
}