package com.jimei.xiaolumeimei.ui.activity.user;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import butterknife.Bind;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.UserWalletAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.BudgetdetailBean;
import com.jimei.xiaolumeimei.model.UserNewModel;
import com.jimei.xiaolumeimei.widget.DividerItemDecoration;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye(www.itxuye.com) on 2016/02/26.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class WalletActivity extends BaseSwipeBackCompatActivity {
  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.tv_momey) TextView tvMomey;
  @Bind(R.id.wallet_rcv) RecyclerView walletRcv;
  private String money;
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

            if (budgetdetailBean != null) {
              adapter.update(budgetdetailBean.getResults());
            }
          }
        });
  }

  @Override protected void getBundleExtras(Bundle extras) {
    money = extras.getString("money");
  }

  @Override protected void onStop() {
    super.onStop();
    if (subscribe != null && subscribe.isUnsubscribed()) {
      subscribe.unsubscribe();
    }
  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.acticity_userawllet;
  }

  @Override protected void initViews() {
    toolbar.setTitle("");
    setSupportActionBar(toolbar);
    finishBack(toolbar);

    tvMomey.setText(money);

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
}
