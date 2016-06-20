package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;

import com.cpoopc.scrollablelayoutlib.ScrollableHelper;
import com.cpoopc.scrollablelayoutlib.ScrollableLayout;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.ShoppingListAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.OderCarryBean;
import com.jimei.xiaolumeimei.model.MMProductModel;
import com.jimei.xiaolumeimei.widget.DividerItemDecoration;
import com.jimei.xiaolumeimei.widget.DividerItemDecorationForFooter;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.umeng.analytics.MobclickAgent;

import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye(www.itxuye.com) on 2016/02/18.
 * <p>
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class MMShoppingListActivity extends BaseSwipeBackCompatActivity implements ScrollableHelper.ScrollableContainer {
    @Bind(R.id.tv_count)
    TextView tvCount;
    @Bind(R.id.shoppinglist_xry)
    XRecyclerView shoppinglistXry;
    @Bind(R.id.scrollable_layout)
    ScrollableLayout scrollableLayout;
    private int page = 2;
    private ShoppingListAdapter adapter;
    private String order;

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {

        tvCount.setText(order);

        showIndeterminateProgressDialog(false);
        Subscription subscribe = MMProductModel.getInstance()
                .getMamaAllOderCarryLogs("direct", "1")
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<OderCarryBean>() {

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        hideIndeterminateProgressDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(OderCarryBean shoppingListBean) {
                        super.onNext(shoppingListBean);
                        if (shoppingListBean != null) {
                            adapter.update(shoppingListBean.getResults());
                            if (null == shoppingListBean.getNext()) {
                                shoppinglistXry.setLoadingMoreEnabled(false);
                            }
                        }
                    }
                });
        addSubscription(subscribe);
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        order = extras.getString("order");
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_mmshoppinglist;
    }

    @Override
    protected void initViews() {
        initRecyclerView();
    }

    private void initRecyclerView() {

        shoppinglistXry.setLayoutManager(new LinearLayoutManager(this));
        shoppinglistXry.addItemDecoration(
                new DividerItemDecorationForFooter(this, DividerItemDecoration.VERTICAL_LIST));
        shoppinglistXry.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        shoppinglistXry.setLoadingMoreProgressStyle(ProgressStyle.SemiCircleSpin);
        shoppinglistXry.setArrowImageView(R.drawable.iconfont_downgrey);
        shoppinglistXry.setPullRefreshEnabled(false);
        shoppinglistXry.setLoadingMoreEnabled(true);

        adapter = new ShoppingListAdapter(this);
        shoppinglistXry.setAdapter(adapter);

        shoppinglistXry.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                loadMoreData(page + "");
                page++;
            }
        });
        scrollableLayout.getHelper().setCurrentScrollableContainer(this);
    }

    private void loadMoreData(String page) {
        Subscription subscribe = MMProductModel.getInstance()
                .getMamaAllOderCarryLogs("direct", page)
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<OderCarryBean>() {
                    @Override
                    public void onNext(OderCarryBean shoppingListBean) {
                        if (shoppingListBean != null) {
                            adapter.update(shoppingListBean.getResults());
                            if (null != shoppingListBean.getNext()) {
                            } else {
                                Toast.makeText(MMShoppingListActivity.this, "没有更多了", Toast.LENGTH_SHORT)
                                        .show();
                                shoppinglistXry.post(shoppinglistXry::loadMoreComplete);
                            }
                        }
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        try {
                            shoppinglistXry.post(shoppinglistXry::loadMoreComplete);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
        addSubscription(subscribe);
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
    protected void onStop() {
        super.onStop();
    }

    @Override
    public View getScrollableView() {
        return shoppinglistXry;
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
