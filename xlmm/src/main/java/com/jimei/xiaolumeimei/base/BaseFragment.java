package com.jimei.xiaolumeimei.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by 优尼世界 on 15/12/29.
 * <p/>
 * Copyright 2015年 上海己美. All rights reserved.
 */
public abstract class BaseFragment extends Fragment {

  public Activity activity;
  public View view;

  private CompositeSubscription mCompositeSubscription;

  public CompositeSubscription getCompositeSubscription() {
    if (this.mCompositeSubscription == null) {
      this.mCompositeSubscription = new CompositeSubscription();
    }

    return this.mCompositeSubscription;
  }

  public void addSubscription(Subscription s) {
    if (this.mCompositeSubscription == null) {
      this.mCompositeSubscription = new CompositeSubscription();
    }

    this.mCompositeSubscription.add(s);
  }

  abstract protected int provideContentViewId();

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    activity = getActivity();
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    if (null == view) {
      view = inflater.inflate(provideContentViewId(), container, false);

      initViews();

      initData();
    }

    ButterKnife.bind(this, view);

    return view;
  }

  protected abstract void initData();//初始化data

  protected abstract void initViews();//初始化view

  @Override public void onStop() {
    super.onStop();
    if (this.mCompositeSubscription != null) {
      this.mCompositeSubscription.unsubscribe();
    }
  }
}
