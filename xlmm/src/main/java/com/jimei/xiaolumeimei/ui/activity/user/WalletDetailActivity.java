package com.jimei.xiaolumeimei.ui.activity.user;

import android.os.Bundle;
import android.widget.TextView;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.BudgetdetailBean;
import com.jude.utils.JUtils;

import butterknife.Bind;

public class WalletDetailActivity extends BaseSwipeBackCompatActivity {

    private BudgetdetailBean.ResultsEntity entity;
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

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        entity = ((BudgetdetailBean.ResultsEntity) extras.getSerializable("entity"));
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
        dateTv3.setText(entity.getBudgetDate());
        typeTv.setText(entity.getBudgetLogType());
        dateTv.setText(entity.getBudgetDate());
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }
}
