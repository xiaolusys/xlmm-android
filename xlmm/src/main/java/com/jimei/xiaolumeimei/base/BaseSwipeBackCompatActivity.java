package com.jimei.xiaolumeimei.base;

import android.os.Bundle;

import com.jimei.xiaolumeimei.R;

import butterknife.ButterKnife;

public abstract class BaseSwipeBackCompatActivity extends BaseActivity {

    @Override
    public void initContentView() {
        setContentView(getContentViewLayoutID());
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.right_in, R.anim.right_out);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.right_in, R.anim.right_out);
    }

}
