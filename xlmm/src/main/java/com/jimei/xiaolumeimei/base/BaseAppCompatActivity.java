package com.jimei.xiaolumeimei.base;

import android.os.Bundle;

public abstract class BaseAppCompatActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getContentViewLayoutID() != 0) {
            setContentView(getContentViewLayoutID());
        } else {
            throw new IllegalArgumentException("You must return a right contentView layout resource Id");
        }
        initViews();
        initData();
        setListener();
    }
}