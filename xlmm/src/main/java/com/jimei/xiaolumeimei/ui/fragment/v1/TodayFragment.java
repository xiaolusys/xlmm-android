package com.jimei.xiaolumeimei.ui.fragment.v1;

import android.os.Bundle;
import com.jimei.xiaolumeimei.ui.fragment.presenter.FragmentPresenter;
import com.jimei.xiaolumeimei.ui.fragment.v1.view.TodayListView;

/**
 * Created by itxuye(www.itxuye.com) on 2016/03/10.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class TodayFragment extends FragmentPresenter<TodayListView> {

  public static TodayFragment newInstance(String title) {
    TodayFragment todayFragment = new TodayFragment();
    Bundle bundle = new Bundle();
    bundle.putString("keyword", title);
    todayFragment.setArguments(bundle);
    return todayFragment;
  }

  @Override protected void lazyData() {
    super.lazyData();
    mView.initViews(TodayFragment.this, getActivity());
  }
}
