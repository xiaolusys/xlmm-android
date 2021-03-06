package com.jimei.xiaolumeimei.ui.activity.user;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jimei.library.utils.JUtils;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.XlmmApp;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.BudgetDetailBean;
import com.jimei.xiaolumeimei.service.ServiceResponse;

import butterknife.Bind;

public class DrawCashDetailActivity extends BaseSwipeBackCompatActivity {
    @Bind(R.id.tv_money_draw)
    TextView drawMoneyTv;
    @Bind(R.id.date_1)
    TextView dateTv1;
    @Bind(R.id.date_2)
    TextView dateTv2;
    @Bind(R.id.date_3)
    TextView dateTv3;
    @Bind(R.id.tv_date)
    TextView dateTv;
    @Bind(R.id.tv_money)
    TextView moneyTv;
    @Bind(R.id.tv_activity)
    TextView activityTv;
    @Bind(R.id.account_tv)
    TextView accountTv;
    @Bind(R.id.ll_account)
    LinearLayout accountLayout;
    @Bind(R.id.iv_ok)
    ImageView okImg;
    @Bind(R.id.view_line)
    View lineView;
    @Bind(R.id.tv_ok)
    TextView okTv;
    @Bind(R.id.layout)
    LinearLayout layout;
    private String account;

    @Override
    protected void initData() {
        addSubscription(XlmmApp.getUserInteractor(this)
            .budgetDetailBean(1, new ServiceResponse<BudgetDetailBean>() {
                @Override
                public void onNext(BudgetDetailBean budgetDetailBean) {
                    if (budgetDetailBean.getResults().size() > 0) {
                        BudgetDetailBean.ResultsEntity entity = budgetDetailBean.getResults().get(0);
                        String str = ((int) entity.getBudegetDetailCash()) + "";
                        drawMoneyTv.setText(str);
                        dateTv.setText(entity.getBudgetDate());
                        dateTv1.setText(entity.getBudgetDate());
                        dateTv2.setText(entity.getBudgetDate());
                        if (entity.getStatus() == 0) {
                            lineView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                            okImg.setImageDrawable(getResources().getDrawable(R.drawable.wallet_ok));
                            okTv.setTextColor(getResources().getColor(R.color.colorAccent));
                            dateTv3.setText(entity.getBudgetDate());
                        } else if (entity.getStatus() == 1) {
                            okTv.setText("已取消");
                            dateTv3.setText(entity.getBudgetDate());
                        }
                        if (!"".equals(account)) {
                            accountTv.setText(account);
                        } else {
                            accountLayout.setVisibility(View.GONE);
                        }
                    } else {
                        JUtils.Toast("查询失败!");
                    }
                }
            }));
        addSubscription(XlmmApp.getVipInteractor(this)
            .getMamaFortune()
            .subscribe(mamaFortune -> {
                String moneyText = mamaFortune.getMamaFortune().getCashValue() + "";
                String activityText = mamaFortune.getMamaFortune().getActiveValueNum() + "";
                moneyTv.setText(moneyText);
                activityTv.setText(activityText);
            }, e -> JUtils.Log(e.getMessage())));
    }

    @Override
    public View getLoadingView() {
        return layout;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        account = extras.getString("account");
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_draw_cash_detail;
    }

}
