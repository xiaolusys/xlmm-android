package com.jimei.xiaolumeimei.ui.activity.trade;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;

import butterknife.Bind;

public class RefundResultActivity extends BaseSwipeBackCompatActivity implements View.OnClickListener {
    @Bind(R.id.btn)
    Button button;

    @Override
    protected void setListener() {
        button.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_refund_result;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
