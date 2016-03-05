package com.jimei.xiaolumeimei.behavior;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import com.jimei.xiaolumeimei.utils.Utils;
import com.zhy.autolayout.AutoRelativeLayout;

/**
 * Created by 优尼世界 on 15/12/29.
 * <p/>
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class ScrollingFabBehavior//滑动的行为，用于tablayout
    extends CoordinatorLayout.Behavior<AutoRelativeLayout> {
  private int toolbarHeight;

  public ScrollingFabBehavior(Context context, AttributeSet attrs) {
    super(context, attrs);
    this.toolbarHeight = Utils.getToolbarHeight(context);
  }

  @Override
  public boolean layoutDependsOn(CoordinatorLayout parent, AutoRelativeLayout fab,
      View dependency) {
    return dependency instanceof AppBarLayout;
  }

  @Override public boolean onDependentViewChanged(CoordinatorLayout parent,
      AutoRelativeLayout fab, View dependency) {
    if (dependency instanceof AppBarLayout) {
      CoordinatorLayout.LayoutParams lp =
          (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
      int fabBottomMargin = lp.bottomMargin;
      int distanceToScroll = fab.getHeight() + fabBottomMargin;
      float ratio = dependency.getY() / (float) toolbarHeight;
      fab.setTranslationY(-distanceToScroll * ratio);
    }
    return true;
  }
}
