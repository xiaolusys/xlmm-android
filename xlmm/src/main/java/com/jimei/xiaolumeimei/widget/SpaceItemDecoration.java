package com.jimei.xiaolumeimei.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by itxuye(www.itxuye.com) on 15/12/29.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
  private int space;

  public SpaceItemDecoration(int space) {
    this.space = space;
  }

  @Override public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
      RecyclerView.State state) {
    outRect.left = space;
    outRect.right = space;
    outRect.bottom = space;

    //改成使用上面的间隔来设置

    outRect.top = space;
  }
}
