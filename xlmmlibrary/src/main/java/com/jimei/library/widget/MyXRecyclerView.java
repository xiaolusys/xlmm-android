package com.jimei.library.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.cpoopc.scrollablelayoutlib.ScrollableHelper;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

/**
 * Created by wisdom on 16/4/19.
 */
public class MyXRecyclerView extends XRecyclerView implements ScrollableHelper.ScrollableContainer{
    public MyXRecyclerView(Context context) {
        super(context);
    }

    public MyXRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyXRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public View getScrollableView() {
        return this;
    }
}
