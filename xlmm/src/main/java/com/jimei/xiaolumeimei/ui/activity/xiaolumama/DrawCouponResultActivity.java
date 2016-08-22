package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.umeng.analytics.MobclickAgent;

import butterknife.Bind;

public class DrawCouponResultActivity extends BaseSwipeBackCompatActivity implements View.OnClickListener {
    @Bind(R.id.tv_msg)
    TextView msgTv;
    @Bind(R.id.finish_btn)
    Button button;
    private String num;

    @Override
    protected void setListener() {
        button.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        num = extras.getString("num");
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_draw_coupon_result;
    }

    @Override
    protected void initViews() {
        msgTv.setText("恭喜您获得一张价值" + num + "元的优惠券，请及时使用哦");
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

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(this.getClass().getSimpleName());
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
        MobclickAgent.onPause(this);
    }
}
