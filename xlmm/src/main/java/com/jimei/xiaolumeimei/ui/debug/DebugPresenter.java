package com.jimei.xiaolumeimei.ui.debug;

import javax.inject.Inject;

/**
 * Created by itxuye(www.itxuye.com) on 2016/05/25.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
final class DebugPresenter implements DebugContract.Presenter {

  private final DebugContract.View mDebugView;

  @Inject DebugPresenter(DebugContract.View debugView) {

    this.mDebugView = debugView;
  }

  @Inject void setupListeners() {
    mDebugView.setPresenter(this);
  }



}
