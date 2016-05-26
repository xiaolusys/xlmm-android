package com.jimei.xiaolumeimei.base;

import android.support.annotation.NonNull;

/**
 * Created by itxuye(www.itxuye.com) on 2016/05/25.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public interface BasePresenter<T extends BaseView> {
  void attachView(@NonNull T view);

  void detachView();
}
