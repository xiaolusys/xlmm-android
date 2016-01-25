package com.jimei.xiaolumeimei.widget.badgelib.mutable;

import com.jimei.xiaolumeimei.widget.badgelib.core.mutable.AbsBadgeMutable;
import com.jimei.xiaolumeimei.widget.badgelib.core.mutable.BadgeMutable;
import com.jimei.xiaolumeimei.widget.badgelib.core.style.BadgeStyle;

/**
 * 角标可变状态
 * Created by dugd on 2015/9/19.
 */
public final class FigureBadge extends AbsBadgeMutable {

  private int figure;

  public FigureBadge(BadgeStyle style) {
    super(style);
    figure = 0;
  }

  @Override public BadgeMutable setFigure(int figure) {
    this.figure = figure;
    notifyObserver();
    return this;
  }

  @Override public int getFigure() {
    return figure;
  }
}
