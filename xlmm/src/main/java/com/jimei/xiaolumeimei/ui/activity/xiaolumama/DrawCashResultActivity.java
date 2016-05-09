package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;

import butterknife.Bind;

public class DrawCashResultActivity extends BaseSwipeBackCompatActivity implements View.OnClickListener {
    @Bind(R.id.finish_btn)
    Button finishBtn;
    @Override
    protected void setListener() {
        finishBtn.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_draw_cash_result;
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
        switch (v.getId()) {
            case R.id.finish_btn:
                finish();
                break;
        }
    }
}
