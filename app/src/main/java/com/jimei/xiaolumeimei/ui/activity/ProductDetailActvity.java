package com.jimei.xiaolumeimei.ui.activity;

import android.os.Bundle;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;

/**
 * Created by itxuye(www.itxuye.com) on 15/12/29.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class ProductDetailActvity extends BaseSwipeBackCompatActivity {

  @Override protected void initData() {
    //new OkHttpRequest.Builder().url().get(new OkHttpCallback() {
    //  @Override public void onError(Request request, Exception e) {
    //
    //  }
    //
    //  @Override public void onResponse(Response response, Object data) {
    //
    //  }
    //});
  }

  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    return 0;
  }

  @Override protected void initViews() {

  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }
}
