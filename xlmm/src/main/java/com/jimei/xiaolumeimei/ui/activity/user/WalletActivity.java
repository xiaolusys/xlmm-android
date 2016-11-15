package com.jimei.xiaolumeimei.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cpoopc.scrollablelayoutlib.ScrollableLayout;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.library.utils.JUtils;
import com.jimei.library.widget.DividerItemDecoration;
import com.jimei.library.widget.MyXRecyclerView;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.UserWalletAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.BudgetDetailEntity;
import com.jimei.xiaolumeimei.entities.UserInfoBean;
import com.jimei.xiaolumeimei.model.UserModel;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.umeng.analytics.MobclickAgent;

import butterknife.Bind;
import rx.Subscription;

/**
 * Created by itxuye(www.itxuye.com) on 2016/02/26.
 * <p>
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class WalletActivity extends BaseSwipeBackCompatActivity {
    String TAG = "WalletActivity";

    @Bind(R.id.tv_money)
    TextView tvMoney;
    @Bind(R.id.wallet_rcv)
    MyXRecyclerView walletRcv;
    @Bind(R.id.ll_wallet_empty)
    LinearLayout ll_wallet_empty;
    @Bind(R.id.scrollable_layout)
    ScrollableLayout scrollableLayout;
    private Double money = 0d;
    private UserWalletAdapter adapter;
    Subscription subscribe;

    private int page = 2;

    @Override
    protected void onResume() {
        super.onResume();
        showIndeterminateProgressDialog(false);
        MobclickAgent.onPageStart(this.getClass().getSimpleName());
        MobclickAgent.onResume(this);
        addSubscription(UserModel.getInstance()
                .budGetDetailBean("1")
                .subscribe(new ServiceResponse<BudgetDetailEntity>() {
                    @Override
                    public void onNext(BudgetDetailEntity budgetdetailEntity) {
                        if ((budgetdetailEntity != null)
                                && (budgetdetailEntity.getResults() != null)
                                && (budgetdetailEntity.getResults().size() > 0)) {
                            walletRcv.setVisibility(View.VISIBLE);
                            ll_wallet_empty.setVisibility(View.INVISIBLE);
                            adapter.updateWithClear(budgetdetailEntity.getResults());
                            if (budgetdetailEntity.getNext() == null) {
                                walletRcv.setLoadingMoreEnabled(false);
                            }
                        } else {
                            walletRcv.setVisibility(View.INVISIBLE);
                            ll_wallet_empty.setVisibility(View.VISIBLE);
                        }
                        addSubscription(UserModel.getInstance()
                                .getUserInfo()
                                .subscribe(new ServiceResponse<UserInfoBean>() {
                                    @Override
                                    public void onNext(UserInfoBean userNewBean) {

                                        if (userNewBean != null) {
                                            if (null != userNewBean.getUserBudget()) {
                                                money = userNewBean.getUserBudget().getBudgetCash();
                                            }
                                            tvMoney.setText((float) (Math.round(money * 100)) / 100 + "");
                                            if (money > 0) {
                                                ll_wallet_empty.setVisibility(View.INVISIBLE);
                                            }
                                        }

                                        hideIndeterminateProgressDialog();
                                    }
                                }));
                    }
                }));
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_userwallet;
    }

    @Override
    protected void initViews() {
        initRecyclerView();
        scrollableLayout.getHelper().setCurrentScrollableContainer(walletRcv);
    }

    private void initRecyclerView() {
        walletRcv.setRefreshProgressStyle(ProgressStyle.BallPulse);
        walletRcv.setLoadingMoreProgressStyle(ProgressStyle.BallPulse);
        walletRcv.setArrowImageView(R.drawable.iconfont_downgrey);
        walletRcv.setLayoutManager(new LinearLayoutManager(this));
        walletRcv.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        walletRcv.setPullRefreshEnabled(false);
        adapter = new UserWalletAdapter(this);
        walletRcv.setAdapter(adapter);
        walletRcv.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                loadMoreData(page);
                page++;
            }
        });
    }

    private void loadMoreData(int page) {
        JUtils.Log(TAG, "load page " + page);
        addSubscription(UserModel.getInstance()
                .budGetDetailBean(Integer.toString(page))
                .subscribe(budgetDetailBean -> {
                            if ((budgetDetailBean != null)
                                    && (budgetDetailBean.getResults() != null)
                                    && (budgetDetailBean.getResults().size() > 0)) {
                                walletRcv.setVisibility(View.VISIBLE);
                                ll_wallet_empty.setVisibility(View.INVISIBLE);
                                adapter.update(budgetDetailBean.getResults());
                                if (budgetDetailBean.getNext() == null) {
                                    Toast.makeText(WalletActivity.this, "没有更多了", Toast.LENGTH_SHORT).show();
                                    walletRcv.post(walletRcv::loadMoreComplete);
                                    walletRcv.setLoadingMoreEnabled(false);
                                }
                            }

                        }, e -> {
                            walletRcv.post(walletRcv::loadMoreComplete);
                        }
                ));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_withdraw:
                JUtils.Log(TAG, "withdraw cash entry");
                Intent intent = new Intent(this, UserDrawCashActivity.class);
                Bundle bundle = new Bundle();
                bundle.putDouble("money", money);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_wallet, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
        MobclickAgent.onPause(this);
    }
}
