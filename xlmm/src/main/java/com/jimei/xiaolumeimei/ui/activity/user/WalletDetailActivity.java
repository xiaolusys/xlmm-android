package com.jimei.xiaolumeimei.ui.activity.user;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.BudgetDetailBean;
import com.umeng.analytics.MobclickAgent;

import butterknife.Bind;

public class WalletDetailActivity extends BaseSwipeBackCompatActivity {

    @Bind(R.id.tv_money)
    TextView moneyTv;
    @Bind(R.id.date_1)
    TextView dateTv1;
    @Bind(R.id.date_2)
    TextView dateTv2;
    @Bind(R.id.date_3)
    TextView dateTv3;
    @Bind(R.id.tv_type)
    TextView typeTv;
    @Bind(R.id.tv_date)
    TextView dateTv;
    @Bind(R.id.tv_number)
    TextView numberTv;
    @Bind(R.id.tv_account)
    TextView accountTv;
    @Bind(R.id.iv_ok)
    ImageView okImg;
    @Bind(R.id.view_line)
    View lineView;
    @Bind(R.id.tv_ok)
    TextView okTv;
    private BudgetDetailBean.ResultsEntity entity;

    @Override
    protected void getBundleExtras(Bundle extras) {
        entity = ((BudgetDetailBean.ResultsEntity) extras.getSerializable("entity"));
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_wallet_detail;
    }

    @Override
    protected void initViews() {
        moneyTv.setText(entity.getBudegetDetailCash() + "");
        dateTv1.setText(entity.getBudgetDate());
        dateTv2.setText(entity.getBudgetDate());
        typeTv.setText(entity.getDesc());
        dateTv.setText(entity.getBudgetDate());
        if (entity.getStatus() == 0) {
            lineView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            okImg.setImageDrawable(getResources().getDrawable(R.drawable.wallet_ok));
            okTv.setTextColor(getResources().getColor(R.color.colorAccent));
            dateTv3.setText(entity.getBudgetDate());
        } else if (entity.getStatus() == 1) {
            okTv.setText("已取消");
            dateTv3.setText(entity.getBudgetDate());
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
