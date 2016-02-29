package com.jimei.xiaolumeimei.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.UserWalletAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.BudgetdetailBean;
import com.jimei.xiaolumeimei.entities.UserInfoBean;
import com.jimei.xiaolumeimei.model.UserNewModel;
import com.jimei.xiaolumeimei.widget.DividerItemDecoration;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye(www.itxuye.com) on 2016/02/26.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class WalletActivity extends BaseSwipeBackCompatActivity {
  String TAG = "WalletActivity";

  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.tv_money) TextView tvMoney;
  @Bind(R.id.wallet_rcv) RecyclerView walletRcv;
  @Bind(R.id.ll_wallet_empty) LinearLayout ll_wallet_empty;
  private Double money;
  private UserWalletAdapter adapter;
  private Subscription subscribe;

  @Override protected void setListener() {

  }

  @Override protected void initData() {

    subscribe = UserNewModel.getInstance()
        .budGetdetailBean()
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<BudgetdetailBean>() {
          @Override public void onNext(BudgetdetailBean budgetdetailBean) {

            if ((budgetdetailBean != null)
                && (budgetdetailBean.getResults() != null)
                && (budgetdetailBean.getResults().size() > 0)) {
              walletRcv.setVisibility(View.VISIBLE);
              ll_wallet_empty.setVisibility(View.INVISIBLE);
              adapter.update(budgetdetailBean.getResults());
            }
            else{
              walletRcv.setVisibility(View.INVISIBLE);
              ll_wallet_empty.setVisibility(View.VISIBLE);
            }
          }
        });
  }

  @Override protected void getBundleExtras(Bundle extras) {
    if(null != extras) {
      money = extras.getDouble("money");
      tvMoney.setText(Math.round(money *100)/100 + "");
    }
    else{
      UserNewModel.getInstance()
          .getProfile()
          .subscribeOn(Schedulers.io())
          .unsafeSubscribe(new Subscriber<UserInfoBean>() {
            @Override public void onCompleted() {

            }

            @Override public void onError(Throwable e) {

            }

            @Override public void onNext(UserInfoBean userNewBean) {
              if (userNewBean != null) {
                if (null != userNewBean.getUserBudget()) {
                  money = userNewBean.getUserBudget().getBudgetCash();
                }
                tvMoney.setText(Math.round(money *100)/100 + "");
              }
            }
          });

    }
  }

  @Override protected void onStop() {
    super.onStop();
    if (subscribe != null && subscribe.isUnsubscribed()) {
      subscribe.unsubscribe();
    }
  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.activity_userwallet;
  }

  @Override protected void initViews() {
    toolbar.setTitle("");
    setSupportActionBar(toolbar);
    finishBack(toolbar);



    initRecyclerView();
  }

  private void initRecyclerView() {
    walletRcv.setLayoutManager(new LinearLayoutManager(this));
    walletRcv.addItemDecoration(
        new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

    adapter = new UserWalletAdapter(this);
    walletRcv.setAdapter(adapter);
  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_withdraw:
        JUtils.Log(TAG, "withdraw cash entry");
        startActivity(new Intent(this, UserWithdrawCashActivity.class));
        break;
      default:
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_wallet, menu);
    return super.onCreateOptionsMenu(menu);
  }
}
