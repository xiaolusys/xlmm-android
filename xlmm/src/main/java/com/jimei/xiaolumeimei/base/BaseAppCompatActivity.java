package com.jimei.xiaolumeimei.base;

import android.os.Bundle;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.utils.StatusBarUtil;

public abstract class BaseAppCompatActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getContentViewLayoutID() != 0) {
            setContentView(getContentViewLayoutID());
        } else {
            throw new IllegalArgumentException("You must return a right contentView layout resource Id");
        }
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorAccent), 0);
        initViews();
        initData();
        setListener();
    }
}