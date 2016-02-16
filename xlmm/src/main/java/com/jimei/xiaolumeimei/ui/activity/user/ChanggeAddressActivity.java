package com.jimei.xiaolumeimei.ui.activity.user;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import butterknife.Bind;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;

/**
 * Created by itxuye(www.itxuye.com) on 2016/01/29.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class ChanggeAddressActivity extends BaseSwipeBackCompatActivity{
  @Bind(R.id.toolbar) Toolbar toolbar;
  @Override protected void setListener() {

  }

  @Override protected void initData() {

  }

  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.activity_changgeaddress;
  }

  @Override protected void initViews() {
    toolbar.setTitle("");
    setSupportActionBar(toolbar);
    finishBack(toolbar);
  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }
}
