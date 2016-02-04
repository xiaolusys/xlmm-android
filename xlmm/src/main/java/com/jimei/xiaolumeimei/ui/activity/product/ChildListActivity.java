package com.jimei.xiaolumeimei.ui.activity.product;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.widget.Toast;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.ChildListAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.ChildListBean;
import com.jimei.xiaolumeimei.model.ProductModel;
import com.jimei.xiaolumeimei.widget.SpaceItemDecoration;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.victor.loading.rotate.RotateLoading;
import java.util.List;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye(www.itxuye.com) on 2016/02/03.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class ChildListActivity extends BaseSwipeBackCompatActivity {

  int page_size = 10;
  ProductModel model = new ProductModel();
  private int page = 2;
  private int totalPages;//总的分页数
  private XRecyclerView xRecyclerView;
  private ChildListAdapter mChildListAdapter;
  private RotateLoading loading;

  @Override protected void setListener() {

  }

  @Override protected void initData() {
    loading.start();
    model.getChildList(1, 10)
        .subscribeOn(Schedulers.newThread())
        .subscribe(new ServiceResponse<ChildListBean>() {
          @Override public void onNext(ChildListBean childListBean) {

            try {

              if (childListBean != null) {
                List<ChildListBean.ResultsEntity> results = childListBean.getResults();
                totalPages = childListBean.getCount() / page_size;
                mChildListAdapter.update(results);
              }
            } catch (Exception ex) {
            }
          }

          @Override public void onCompleted() {
            super.onCompleted();
            loading.post(loading::stop);
          }
        });
  }

  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.activty_childlist;
  }

  @Override protected void initViews() {
    loading = (RotateLoading) findViewById(R.id.loading);
    initRecyclerView();
  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }

  private void initRecyclerView() {

    xRecyclerView = (XRecyclerView) findViewById(R.id.childlist_recyclerView);
    xRecyclerView.setLayoutManager(new GridLayoutManager(ChildListActivity.this, 2));

    xRecyclerView.addItemDecoration(new SpaceItemDecoration(10));

    xRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
    xRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.SemiCircleSpin);
    xRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);

    mChildListAdapter = new ChildListAdapter(ChildListActivity.this);
    xRecyclerView.setAdapter(mChildListAdapter);

    xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
      @Override public void onRefresh() {
        model.getChildList(1, page * page_size)
            .subscribeOn(Schedulers.newThread())
            .subscribe(new ServiceResponse<ChildListBean>() {
              @Override public void onNext(ChildListBean childListBean) {
                List<ChildListBean.ResultsEntity> results = childListBean.getResults();
                mChildListAdapter.updateWithClear(results);
              }

              @Override public void onCompleted() {
                super.onCompleted();
                xRecyclerView.post(xRecyclerView::refreshComplete);
              }
            });
      }

      @Override public void onLoadMore() {
        if (page <= totalPages) {
          loadMoreData(page, 10);
          page++;
        } else {
          Toast.makeText(ChildListActivity.this, "没有更多了拉,去购物吧", Toast.LENGTH_SHORT)
              .show();
          xRecyclerView.post(xRecyclerView::loadMoreComplete);
        }
      }
    });
  }

  private void loadMoreData(int page, int page_size) {

    model.getChildList(page, page_size)
        .subscribeOn(Schedulers.newThread())
        .subscribe(new ServiceResponse<ChildListBean>() {
          @Override public void onNext(ChildListBean productListBean) {
            List<ChildListBean.ResultsEntity> results = productListBean.getResults();
            mChildListAdapter.update(results);
          }

          @Override public void onCompleted() {
            super.onCompleted();
            xRecyclerView.post(xRecyclerView::loadMoreComplete);
          }
        });
  }
}
