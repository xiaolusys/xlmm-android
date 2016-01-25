package com.jimei.xiaolumeimei.widget.badgelib.core.mutable;

import com.jimei.xiaolumeimei.widget.badgelib.core.BadgeObserver;
import com.jimei.xiaolumeimei.widget.badgelib.core.style.BadgeStyle;

/**
 * 角标可变状态接口
 * Created by dugd on 2015/9/19.
 */
public interface BadgeMutable {

    /**
     * 显示角标
     */
    BadgeMutable show();

    /**
     * 隐藏角标
     */
    BadgeMutable hide();

    /**
     * 判断角标显隐性
     */
    boolean isShown();

    /**
     * 分离角标
     */
    void detach();

    /**
     * 分离角标（可选择是否通知观察者）
     */
    void detach(boolean notifyObserver);

    /**
     * 判断附着状态
     */
    boolean isAttached();

    /**
     * 获得角标样式
     */
    BadgeStyle getStyle();

    /**
     * 设置观察者
     */
    void setObserver(BadgeObserver observer);

    /**
     * 移除观察者
     */
    void removeObserver();

    BadgeMutable setFigure(int figure);

    int getFigure();
}
