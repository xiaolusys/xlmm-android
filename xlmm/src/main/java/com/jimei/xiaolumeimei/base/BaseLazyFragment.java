package com.jimei.xiaolumeimei.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by 优尼世界 on 15/12/29.
 * <p/>
 * Copyright 2015年 上海己美. All rights reserved.
 */
public abstract class BaseLazyFragment extends Fragment {

  /**
   * 是否可见
   */
  protected boolean isVisiable;
  /**
   * 是否已经调用onCreatView
   */
  protected boolean isViewCreated;

  /**
   * 先于onCreateView
   * @param isVisibleToUser
   */
  @Override
  public void setUserVisibleHint(boolean isVisibleToUser) {
    super.setUserVisibleHint(isVisibleToUser);
    isVisiable = isVisibleToUser;
    //可以根据需要修改
    if (isVisiable) {
      onVisible();
    } else {
      onInvisible();
    }
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    isViewCreated = true;
  }

  /**
   * 懒加载 在可见的状态下会被调用
   */
  protected abstract void lazyload();

  protected void onVisible() {
    if (isViewCreated && isVisiable) {
      lazyload();
    }
  }

  protected void onInvisible() {

  }

}
