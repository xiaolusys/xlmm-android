package com.jimei.xiaolumeimei.widget.badgelib.mutable;

import com.jimei.xiaolumeimei.widget.badgelib.core.mutable.AbsBadgeMutable;
import com.jimei.xiaolumeimei.widget.badgelib.core.mutable.BadgeMutable;
import com.jimei.xiaolumeimei.widget.badgelib.core.style.BadgeStyle;

/**
 * 小圆点角标
 * Created by dugd on 2015/9/19.
 */
public final class DotBadge extends AbsBadgeMutable {

  public DotBadge(BadgeStyle style) {
    super(style);
  }

  @Override public BadgeMutable setFigure(int figure) {
    return this;
  }

  @Override public int getFigure() {
    return -1;
  }
}
