package com.jimei.xiaolumeimei.ui.debug;

import dagger.Module;
import dagger.Provides;

/**
 * Created by itxuye(www.itxuye.com) on 2016/05/25.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
@Module public class DebugModule {
  private final DebugContract.View mView;

  public DebugModule(DebugContract.View view) {
    mView = view;
  }

  @Provides DebugContract.View provideStatisticsContractView() {
    return mView;
  }
}
