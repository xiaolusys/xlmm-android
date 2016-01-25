package com.jimei.xiaolumeimei.widget.badgelib.core;

import android.view.View;
import com.jimei.xiaolumeimei.widget.badgelib.core.mutable.BadgeMutable;
import com.jimei.xiaolumeimei.widget.badgelib.core.style.DotStyle;
import com.jimei.xiaolumeimei.widget.badgelib.core.style.FigureStyle;
import com.jimei.xiaolumeimei.widget.badgelib.mutable.DotBadge;
import com.jimei.xiaolumeimei.widget.badgelib.mutable.FigureBadge;

/**
 * 角标管理器接口
 * Created by dugd on 2015/9/19.
 */
public interface BadgeManager {

    /**
     * 创建数字角标（自定义样式）
     * @param viewId 要绑定的目标view的id
     * @param badgeStyle 角标样式
     * @return 数字角标对象
     */
    FigureBadge createFigureBadge(int viewId, Class<? extends FigureStyle> badgeStyle);

    FigureBadge createFigureBadge(View view, Class<? extends FigureStyle> badgeStyle);

    /**
     * 创建圆点角标（自定义样式）
     * @param viewId 要绑定的目标view的id
     * @param badgeStyle 角标样式
     * @return 圆点角标对象
     */
    DotBadge createDotBadge(int viewId, Class<? extends DotStyle> badgeStyle);

    DotBadge createDotBadge(View view, Class<? extends DotStyle> badgeStyle);

    /**
     * 找到某个角标
     * @param viewId 目标控件的布局id
     */
    BadgeMutable findBadge(int viewId);

    /**
     * 找到某个角标
     * @param view 目标控件引用
     */
    BadgeMutable findBadge(View view);

    /**
     * 清除所有角标
     */
    void clearAllBadges();
}
