package com.jimei.xiaolumeimei.ui.activity.main;

import android.os.Bundle;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseActivity;

public class TabActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        super.onCreate(savedInstanceState);
        setContentView(getContentViewLayoutID());
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_tab;
    }
}
