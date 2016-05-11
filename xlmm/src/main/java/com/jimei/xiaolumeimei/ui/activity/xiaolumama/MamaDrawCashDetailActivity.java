package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.os.Bundle;
import android.widget.TextView;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.BudgetdetailBean;
import com.jimei.xiaolumeimei.entities.MamaFortune;
import com.jimei.xiaolumeimei.model.MamaInfoModel;
import com.jimei.xiaolumeimei.model.UserNewModel;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;

import butterknife.Bind;
import rx.Subscription;
import rx.schedulers.Schedulers;

public class MamaDrawCashDetailActivity extends BaseSwipeBackCompatActivity {
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mama_draw_cash_detail);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
        Subscription subscribe = UserNewModel.getInstance()
                .budGetdetailBean("1")
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<BudgetdetailBean>() {
                    @Override
                    public void onNext(BudgetdetailBean budgetdetailBean) {
                        BudgetdetailBean.ResultsEntity entity = budgetdetailBean.getResults().get(0);
                        String str = ((int) entity.getBudegetDetailCash()) + "";
                        drawMoneyTv.setText(str);
                        dateTv.setText(entity.getBudgetDate());
                        dateTv1.setText(entity.getBudgetDate());
                        dateTv2.setText(entity.getBudgetDate());
                        dateTv3.setText(entity.getBudgetDate());
                    }
                });
        Subscription subscribe1 = MamaInfoModel.getInstance()
                .getMamaFortune()
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<MamaFortune>() {
                    @Override
                    public void onNext(MamaFortune mamaFortune) {
                        String moneyText = mamaFortune.getMama_fortune().getCash_value() + "";
                        String activityText = mamaFortune.getMama_fortune().getActive_value_num() + "";
                        moneyTv.setText(moneyText);
                        activityTv.setText(activityText);
                    }
                });
        addSubscription(subscribe);
        addSubscription(subscribe1);
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_mama_draw_cash_detail;
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
}
