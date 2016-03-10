package com.jimei.xiaolumeimei.ui.fragment;

import android.os.Bundle;
import com.jimei.xiaolumeimei.ui.fragment.presenter.FragmentPresenter;
import com.jimei.xiaolumeimei.ui.fragment.view.PreviousListView;
import com.jimei.xiaolumeimei.ui.fragment.view.TodayListView;

/**
 * Created by itxuye(www.itxuye.com) on 2016/03/10.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class PreviousFragment extends FragmentPresenter<PreviousListView> {

  public static PreviousFragment newInstance(String title) {
    PreviousFragment todayFragment = new PreviousFragment();
    Bundle bundle = new Bundle();
    bundle.putString("keyword", title);
    todayFragment.setArguments(bundle);
    return todayFragment;
  }

  @Override protected void lazyData() {
    super.lazyData();
    mView.initViews(PreviousFragment.this, getActivity());
  }
}
