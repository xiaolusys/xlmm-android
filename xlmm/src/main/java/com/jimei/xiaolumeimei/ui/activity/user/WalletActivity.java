package com.jimei.xiaolumeimei.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
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
  @Bind(R.id.ll_wallet_empty) RelativeLayout ll_wallet_empty;
  private Double money;
  private UserWalletAdapter adapter;

  private boolean get_money = false;

  @Override protected void setListener() {

  }

  @Override protected void initData() {

    Subscription subscribe = UserNewModel.getInstance()
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
    addSubscription(subscribe);

    if(!get_money){
      Subscription subscribe1 = UserNewModel.getInstance()
          .getProfile()
          .subscribeOn(Schedulers.io())
          .subscribe(new ServiceResponse<UserInfoBean>() {
          @Override public void onNext(UserInfoBean userNewBean) {

            if (userNewBean != null) {
              if (null != userNewBean.getUserBudget()) {
                money = userNewBean.getUserBudget().getBudgetCash();
              }
              tvMoney.setText((float)(Math.round(money *100))/100 + "");
            }
          }
        });
      addSubscription(subscribe1);

    }
    else {
      tvMoney.setText((float)(Math.round(money *100))/100 + "");
    }
  }

  @Override protected void getBundleExtras(Bundle extras) {
    if(null != extras) {
      money = extras.getDouble("money");
      JUtils.Log(TAG, "money:"+money);
      get_money = true;
    }

  }

  @Override protected void onStop() {
    super.onStop();

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
        Intent intent = new Intent(this, UserWithdrawCashActivity.class);
        Bundle bundle = new Bundle();
        bundle.putDouble("money", money);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
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
