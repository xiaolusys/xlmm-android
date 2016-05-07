package com.jimei.xiaolumeimei.ui.activity.product;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.ChildListActivityAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.ChildListBean;
import com.jimei.xiaolumeimei.model.ProductModel;
import com.jimei.xiaolumeimei.widget.SpaceItemDecoration;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;

import java.util.List;

import butterknife.Bind;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye(www.itxuye.com) on 2016/02/03.
 * <p>
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class ChildListActivity extends BaseSwipeBackCompatActivity implements View.OnClickListener {
    @Bind(R.id.text_1)
    TextView textView1;
    @Bind(R.id.text_2)
    TextView textView2;
    @Bind(R.id.xrv_by)
    XRecyclerView byXRecyclerView;

    int page_size = 10;
    private int page1 = 2;
    private int page2 = 2;
    private int totalPages;//总的分页数
    private int totalPages2;
    private XRecyclerView xRecyclerView;
    private ChildListActivityAdapter mChildListAdapter;
    private ChildListActivityAdapter mChildListAdapter2;

    @Override
    protected void setListener() {
        textView1.setOnClickListener(this);
        textView2.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        showIndeterminateProgressDialog(false);
        Subscription subscribe = ProductModel.getInstance()
                .getChildList(1, 10)
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<ChildListBean>() {
                    @Override
                    public void onNext(ChildListBean childListBean) {

                        try {

                            if (childListBean != null) {
                                List<ChildListBean.ResultsEntity> results = childListBean.getResults();
                                totalPages = childListBean.getCount() / page_size;
                                mChildListAdapter.update(results);
                            }
                        } catch (Exception ex) {
                        }
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        hideIndeterminateProgressDialog();
                    }
                });
        Subscription subscribe2 = ProductModel.getInstance()
                .getChildList(1, 10,"price")
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<ChildListBean>() {
                    @Override
                    public void onNext(ChildListBean childListBean) {

                        try {

                            if (childListBean != null) {
                                List<ChildListBean.ResultsEntity> results = childListBean.getResults();
                                totalPages2 = childListBean.getCount() / page_size;
                                mChildListAdapter2.update(results);
                            }
                        } catch (Exception ex) {
                        }
                    }});
        addSubscription(subscribe);
        addSubscription(subscribe2);
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activty_childlist;
    }

    @Override
    protected void initViews() {
        initRecyclerView();
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    private void initRecyclerView() {

        xRecyclerView = (XRecyclerView) findViewById(R.id.childlist_recyclerView);
        xRecyclerView.setLayoutManager(new GridLayoutManager(ChildListActivity.this, 2));

        xRecyclerView.addItemDecoration(new SpaceItemDecoration(10));

        xRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        xRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.SemiCircleSpin);
        xRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);

        mChildListAdapter = new ChildListActivityAdapter(ChildListActivity.this);
        xRecyclerView.setAdapter(mChildListAdapter);
        byXRecyclerView.setLayoutManager(new GridLayoutManager(ChildListActivity.this, 2));

        byXRecyclerView.addItemDecoration(new SpaceItemDecoration(10));

        byXRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        byXRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.SemiCircleSpin);
        byXRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);

        mChildListAdapter2 = new ChildListActivityAdapter(ChildListActivity.this);
        byXRecyclerView.setAdapter(mChildListAdapter2);

        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                Subscription subscribe = ProductModel.getInstance()
                        .getChildList(1, page1 * page_size)
                        .subscribeOn(Schedulers.io())
                        .subscribe(new ServiceResponse<ChildListBean>() {
                            @Override
                            public void onNext(ChildListBean childListBean) {
                                List<ChildListBean.ResultsEntity> results = childListBean.getResults();
                                mChildListAdapter.updateWithClear(results);
                            }

                            @Override
                            public void onCompleted() {
                                super.onCompleted();
                                xRecyclerView.post(xRecyclerView::refreshComplete);
                            }
                        });
                addSubscription(subscribe);
            }

            @Override
            public void onLoadMore() {
                if (page1 <= totalPages) {
                    loadMoreData(page1, 10,null);
                    page1++;
                } else {
                    Toast.makeText(ChildListActivity.this, "没有更多了拉,去购物吧", Toast.LENGTH_SHORT)
                            .show();
                    xRecyclerView.post(xRecyclerView::loadMoreComplete);
                }
            }
        });
        byXRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                Subscription subscribe = ProductModel.getInstance()
                        .getChildList(1, page2 * page_size,"price")
                        .subscribeOn(Schedulers.io())
                        .subscribe(new ServiceResponse<ChildListBean>() {
                            @Override
                            public void onNext(ChildListBean childListBean) {
                                List<ChildListBean.ResultsEntity> results = childListBean.getResults();
                                mChildListAdapter2.updateWithClear(results);
                            }

                            @Override
                            public void onCompleted() {
                                super.onCompleted();
                                byXRecyclerView.post(byXRecyclerView::refreshComplete);
                            }
                        });
                addSubscription(subscribe);
            }

            @Override
            public void onLoadMore() {
                if (page2 <= totalPages2) {
                    loadMoreData(page2, 10,"price");
                    page2++;
                } else {
                    Toast.makeText(ChildListActivity.this, "没有更多了拉,去购物吧", Toast.LENGTH_SHORT)
                            .show();
                    byXRecyclerView.post(byXRecyclerView::loadMoreComplete);
                }
            }
        });
    }

    private void loadMoreData(int page, int page_size,String orderBy) {
        if (orderBy != null) {
            Subscription subscribe = ProductModel.getInstance()
                    .getChildList(page, page_size,orderBy)
                    .subscribeOn(Schedulers.io())
                    .subscribe(new ServiceResponse<ChildListBean>() {
                        @Override
                        public void onNext(ChildListBean productListBean) {
                            List<ChildListBean.ResultsEntity> results = productListBean.getResults();
                            mChildListAdapter2.update(results);
                        }
                        @Override
                        public void onCompleted() {
                            super.onCompleted();
                            byXRecyclerView.post(byXRecyclerView::loadMoreComplete);
                        }
                    });
            addSubscription(subscribe);
        }else{
            Subscription subscribe = ProductModel.getInstance()
                    .getChildList(page, page_size)
                    .subscribeOn(Schedulers.io())
                    .subscribe(new ServiceResponse<ChildListBean>() {
                        @Override
                        public void onNext(ChildListBean productListBean) {
                            List<ChildListBean.ResultsEntity> results = productListBean.getResults();
                            mChildListAdapter.update(results);
                        }
                        @Override
                        public void onCompleted() {
                            super.onCompleted();
                            xRecyclerView.post(xRecyclerView::loadMoreComplete);
                        }
                    });
            addSubscription(subscribe);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_1:
                textView1.setTextColor(getResources().getColor(R.color.colorAccent));
                textView2.setTextColor(getResources().getColor(R.color.text_color_9B));
                xRecyclerView.setVisibility(View.VISIBLE);
                byXRecyclerView.setVisibility(View.GONE);
                break;
            case R.id.text_2:
                textView2.setTextColor(getResources().getColor(R.color.colorAccent));
                textView1.setTextColor(getResources().getColor(R.color.text_color_9B));
                byXRecyclerView.setVisibility(View.VISIBLE);
                xRecyclerView.setVisibility(View.GONE);
                break;
        }
    }
}
