package com.jimei.xiaolumeimei.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by itxuye(www.itxuye.com) on 15/12/29.
 * <p>
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    private int space;
    private int left, right, top, bottom;
    boolean flag;

    public SpaceItemDecoration(int left, int right, int top, int bottom) {
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
        flag = false;
    }

    public SpaceItemDecoration(int space) {
        this.space = space;
        flag = true;
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        if (flag) {
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;
            outRect.top = space;
        } else {
            outRect.left = left;
            outRect.right = right;
            outRect.bottom = bottom;
            outRect.top = top;
        }
    }
}
