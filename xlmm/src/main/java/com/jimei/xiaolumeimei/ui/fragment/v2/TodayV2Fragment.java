package com.jimei.xiaolumeimei.ui.fragment.v2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import com.jimei.xiaolumeimei.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by itxuye(www.itxuye.com) on 2016/04/16.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class TodayV2Fragment extends Fragment {

  private List<String> list = new ArrayList<>();

  public static TodayV2Fragment newInstance(String title) {
    TodayV2Fragment todayV2Fragment = new TodayV2Fragment();
    Bundle bundle = new Bundle();
    bundle.putString("keyword", title);
    todayV2Fragment.setArguments(bundle);
    return todayV2Fragment;
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

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_todayv2, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  private void initViews(View view) {

  }
}
