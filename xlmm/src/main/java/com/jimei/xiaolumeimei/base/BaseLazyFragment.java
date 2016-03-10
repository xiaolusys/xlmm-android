package com.jimei.xiaolumeimei.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by itxuye(www.itxuye.com) on 2016/03/10.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public abstract class BaseLazyFragment extends Fragment {

  private boolean isVisible;

  private boolean isPrepared;

  private boolean isFirstLoad = true;

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    isFirstLoad = true;
    View view = initViews(inflater, container, savedInstanceState);
    isPrepared = true;
    lazyLoad();
    return view;
  }

  @Override public void setUserVisibleHint(boolean isVisibleToUser) {
    super.setUserVisibleHint(isVisibleToUser);
    if (getUserVisibleHint()) {
      isVisible = true;
      onVisible();
    } else {
      isVisible = false;
      onInvisible();
    }
  }

  @Override public void onHiddenChanged(boolean hidden) {
    super.onHiddenChanged(hidden);
    if (!hidden) {
      isVisible = true;
      onVisible();
    } else {
      isVisible = false;
      onInvisible();
    }
  }

  protected void onVisible() {
    lazyLoad();
  }

  protected void onInvisible() {

  }

  protected void lazyLoad() {
    if (!isPrepared || !isVisible || !isFirstLoad) {
      return;
    }
    isFirstLoad = false;
    initializeData();
  }

  protected abstract View initViews(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState);

  protected abstract void initializeData();
}
