package com.jimei.xiaolumeimei.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import com.afollestad.materialdialogs.MaterialDialog;
import com.jimei.xiaolumeimei.R;

/**
 * Created by 优尼世界 on 15/12/29.
 * <p/>
 * Copyright 2015年 上海己美. All rights reserved.
 */
public abstract class BaseFragment extends Fragment {

  public Activity activity;
  public View view;
  private MaterialDialog materialDialog;

  abstract protected int provideContentViewId();

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    activity = getActivity();
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    view = inflater.inflate(provideContentViewId(), container, false);
    ButterKnife.bind(this, view);
    if (view == null) {
      initViews();
      initData();
    }

    return view;
  }

  protected abstract void initData();//初始化data

  protected abstract void initViews();//初始化view

  @Override public void onStop() {
    super.onStop();
  }

  private void showIndeterminateProgressDialog(boolean horizontal) {
    materialDialog = new MaterialDialog.Builder(activity)
        //.title(R.string.progress_dialog)
        .content(R.string.please_wait)
        .progress(true, 0)
        .widgetColorRes(R.color.colorAccent)
        .progressIndeterminateStyle(horizontal)
        .show();
  }

  private void hideIndeterminateProgressDialog(boolean horizontal) {
    materialDialog.dismiss();
  }
}
