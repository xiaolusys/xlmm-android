package com.jimei.xiaolumeimei.ui.fragment.v2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by itxuye(www.itxuye.com) on 2016/04/16.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class YesterdayV2Fragment extends Fragment {

  private List<String> list = new ArrayList<>();

  public static YesterdayV2Fragment newInstance(String title) {
    YesterdayV2Fragment yesterdayV2Fragment = new YesterdayV2Fragment();
    Bundle bundle = new Bundle();
    bundle.putString("keyword", title);
    yesterdayV2Fragment.setArguments(bundle);
    return yesterdayV2Fragment;
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
  }

  @Override public void setUserVisibleHint(boolean isVisibleToUser) {
    super.setUserVisibleHint(isVisibleToUser);
    if (isVisibleToUser && list.size() == 0) {
      load();
    }
  }

  private void load() {

  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initViews(view);
  }

  private void initViews(View view) {

  }
}
