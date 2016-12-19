package com.jimei.xiaolumeimei.base;

import android.os.Bundle;

import com.jimei.xiaolumeimei.R;


public abstract class BaseSwipeBackCompatActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.right_in, R.anim.right_out);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.right_in, R.anim.right_out);
    }

    @Override
    public void initContentView() {
        setContentView(getContentViewLayoutID());
    }
}
