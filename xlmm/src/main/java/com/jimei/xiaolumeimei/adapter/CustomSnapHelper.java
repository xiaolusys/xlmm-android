package com.jimei.xiaolumeimei.adapter;

import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by wisdom on 17/2/18.
 */

public class CustomSnapHelper extends LinearSnapHelper {
    private MainTabAdapter adapter;

    public CustomSnapHelper(MainTabAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {
        int position = super.findTargetSnapPosition(layoutManager, velocityX, velocityY);
        if (position > 0 && position < adapter.getItemCount()) {
            adapter.setCurrentPosition(position);
        } else {
            View curView = findSnapView(layoutManager);
            if (curView != null) {
                int curPosition = layoutManager.getPosition(curView);
                adapter.setCurrentPosition(curPosition);
            }
        }
        return position;
    }
}
