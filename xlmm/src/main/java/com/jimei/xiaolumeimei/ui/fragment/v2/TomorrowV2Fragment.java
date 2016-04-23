package com.jimei.xiaolumeimei.ui.fragment.v2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.jimei.xiaolumeimei.base.BaseFragment;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by itxuye(www.itxuye.com) on 2016/04/16.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class TomorrowV2Fragment extends BaseFragment {

  private List<String> list = new ArrayList<>();

  public static TomorrowV2Fragment newInstance(String title) {
    TomorrowV2Fragment tomorrowV2Fragment = new TomorrowV2Fragment();
    Bundle bundle = new Bundle();
    bundle.putString("keyword", title);
    tomorrowV2Fragment.setArguments(bundle);
    return tomorrowV2Fragment;
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

  @Override public View getScrollableView() {
    return null;
  }
}
