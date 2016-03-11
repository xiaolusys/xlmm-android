package com.jimei.xiaolumeimei.ui.fragment.v2.view;

import android.content.Context;
import android.support.v4.app.Fragment;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.ui.fragment.view.ViewImpl;

/**
 * Created by itxuye(www.itxuye.com) on 2016/03/11.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class MMCarryLogAllView extends ViewImpl {
  @Bind(R.id.carrylogall_xry) XRecyclerView carrylogallXry;

  @Override public int getLayoutId() {
    return R.layout.fragment_carrylogall;
  }

  public void initViews(Fragment fragment, Context context) {

  }

  @Override public void destroy() {
    ButterKnife.unbind(this);
  }
}
