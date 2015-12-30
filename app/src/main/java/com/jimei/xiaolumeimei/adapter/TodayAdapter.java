package com.jimei.xiaolumeimei.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by itxuye(www.itxuye.com) on 15/12/29.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class TodayAdapter extends RecyclerView.Adapter<TodayAdapter.TodayVH> {

  @Override public TodayVH onCreateViewHolder(ViewGroup parent, int viewType) {
    return null;
  }

  @Override public void onBindViewHolder(TodayVH holder, int position) {

  }

  @Override public int getItemCount() {
    return 0;
  }

  static class TodayVH extends RecyclerView.ViewHolder {

    public TodayVH(View itemView) {
      super(itemView);
    }
  }
}
