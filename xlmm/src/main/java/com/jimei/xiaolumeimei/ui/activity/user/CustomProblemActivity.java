package com.jimei.xiaolumeimei.ui.activity.user;


import android.view.Menu;

import com.jimei.xiaolumeimei.base.CommonWebViewActivity;

public class CustomProblemActivity extends CommonWebViewActivity {
    @Override
    protected void initViews() {
        super.initViews();
        titleView.setName("常见问题");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }
}
