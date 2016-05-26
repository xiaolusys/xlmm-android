package com.jimei.xiaolumeimei.ui.debug;

import android.os.Bundle;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.di.scope.HasComponent;

/**
 * Created by itxuye(www.itxuye.com) on 2016/05/25.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class DebugActivity extends BaseSwipeBackCompatActivity
    implements DebugContract.View, HasComponent<DebugComponent> {
  private DebugComponent debugComponent;

  @Override protected void setListener() {

  }

  @Override protected void initData() {

  }

  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    return 0;
  }

  @Override protected void initViews() {
    debugComponent = DaggerDebugComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .debugModule(new DebugModule())
        .build();
    debugComponent.inject(this);
  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }

  @Override public DebugComponent getComponent() {
    return debugComponent;
  }
}
