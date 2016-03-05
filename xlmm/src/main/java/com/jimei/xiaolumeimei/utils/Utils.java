package com.jimei.xiaolumeimei.utils;

/**
 * Created by itxuye(www.itxuye.com) on 15/12/29.
 * <p/>
 * Copyright 2015年 上海己美. All rights reserved.
 */

import android.content.Context;
import android.content.res.TypedArray;
import com.jimei.xiaolumeimei.R;

public class Utils {

    public static int getToolbarHeight(Context context) {
        final TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(
                new int[]{ R.attr.actionBarSize});
        int toolbarHeight = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();

        return toolbarHeight;
    }
}