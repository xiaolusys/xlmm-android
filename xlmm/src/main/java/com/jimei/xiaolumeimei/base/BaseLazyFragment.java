package com.jimei.xiaolumeimei.base;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by itxuye(www.itxuye.com)  on 2016/08/27.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public abstract class BaseLazyFragment<T extends ViewDataBinding> extends Fragment {

  protected T b;
  private CompositeSubscription mCompositeSubscription;
  protected Activity mActivity;
  private boolean mIsHidden = true;
  private static final String FRAGMENT_STORE = "STORE";
  private boolean isVisible = false;
  private boolean isInitView = false;
  private boolean isFirstLoad = true;

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (savedInstanceState != null) {
      mIsHidden = savedInstanceState.getBoolean(FRAGMENT_STORE);
    }

    if (restoreInstanceState()) {
      processRestoreInstanceState(savedInstanceState);
    }
  }

  private void processRestoreInstanceState(Bundle savedInstanceState) {
    if (savedInstanceState != null) {
      FragmentTransaction ft = getFragmentManager().beginTransaction();
      if (isSupportHidden()) {
        ft.hide(this);
      } else {
        ft.show(this);
      }
      ft.commit();
    }
  }

  public boolean isSupportHidden() {
    return mIsHidden;
  }

  protected boolean restoreInstanceState() {
    return true;
  }

  @Override public void onAttach(Context context) {
    super.onAttach(context);
    mActivity = (Activity) context;
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    if (getContentViewId() != 0) {
      b = DataBindingUtil.inflate(inflater, getContentViewId(), container, false);
    } else {
      throw new IllegalArgumentException("You must return a right contentView layout resource Id");
    }
    initViews();
    isInitView = true;
    lazyLoadData();
    return b.getRoot();
  }

  @Override
  public void setUserVisibleHint(boolean isVisibleToUser) {
    if (isVisibleToUser) {
      isVisible = true;
      lazyLoadData();

    } else {
      isVisible = false;
    }
    super.setUserVisibleHint(isVisibleToUser);
  }

  private void lazyLoadData() {
    if (isFirstLoad) {
    } else {
    }
    if (!isFirstLoad || !isVisible || !isInitView) {
      return;
    }
    initData();
    isFirstLoad = false;
  }

  protected abstract void initData();

  protected abstract void initViews();

  protected abstract int getContentViewId();

  @Override public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putBoolean(FRAGMENT_STORE, isHidden());
  }

  public void addSubscription(Subscription s) {
    if (this.mCompositeSubscription == null) {
      this.mCompositeSubscription = new CompositeSubscription();
    }
    this.mCompositeSubscription.add(s);
  }

  @Override public void onDestroyView() {
    try {
      if (this.mCompositeSubscription != null) {
        this.mCompositeSubscription.unsubscribe();
        this.mCompositeSubscription = null;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    super.onDestroyView();
  }
}
