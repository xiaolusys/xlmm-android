package com.jimei.xiaolumeimei.ui.activity.user;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import butterknife.Bind;

public class CustomProblemActivity extends BaseSwipeBackCompatActivity {
    @Bind(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_custom_problem;
    }

    @Override
    protected void initViews() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        finishBack(toolbar);
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }
}
