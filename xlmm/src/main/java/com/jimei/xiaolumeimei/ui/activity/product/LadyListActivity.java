package com.jimei.xiaolumeimei.ui.activity.product;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.widget.Toast;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.LadyListAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.LadyListBean;
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
public class LadyListActivity extends BaseSwipeBackCompatActivity {

  int page_size = 10;
  ProductModel model = new ProductModel();
  private int page = 2;
  private int totalPages;//总的分页数
  private XRecyclerView xRecyclerView;
  private LadyListAdapter mLadyListAdapter;
  private RotateLoading loading;

  @Override protected void setListener() {

  }

  @Override protected void initData() {
    loading.start();

    model.getLadyList(1, 10)
        .subscribeOn(Schedulers.newThread())
        .subscribe(new ServiceResponse<LadyListBean>() {
          @Override public void onNext(LadyListBean ladyListBean) {

            try {
              if (ladyListBean != null) {
                List<LadyListBean.ResultsEntity> results = ladyListBean.getResults();
                totalPages = ladyListBean.getCount() / page_size;
                mLadyListAdapter.update(results);
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
    return R.layout.activity_ladylist;
  }

  @Override protected void initViews() {
    loading = (RotateLoading) findViewById(R.id.loading);
    initRecyclerView();
  }

  private void initRecyclerView() {

    xRecyclerView = (XRecyclerView) findViewById(R.id.childlist_recyclerView);
    GridLayoutManager layoutManager = new GridLayoutManager(LadyListActivity.this, 2);

    xRecyclerView.setLayoutManager(layoutManager);

    xRecyclerView.addItemDecoration(new SpaceItemDecoration(10));

    xRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
    xRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.SemiCircleSpin);
    xRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);

    mLadyListAdapter = new LadyListAdapter(LadyListActivity.this);
    xRecyclerView.setAdapter(mLadyListAdapter);

    xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
      @Override public void onRefresh() {
        model.getLadyList(1, page * page_size)
            .subscribeOn(Schedulers.newThread())
            .subscribe(new ServiceResponse<LadyListBean>() {
              @Override public void onNext(LadyListBean ladyListBean) {
                List<LadyListBean.ResultsEntity> results = ladyListBean.getResults();
                mLadyListAdapter.updateWithClear(results);
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
          Toast.makeText(LadyListActivity.this, "没有更多了拉,去购物吧", Toast.LENGTH_SHORT)
              .show();
          xRecyclerView.post(xRecyclerView::loadMoreComplete);
        }
      }
    });
  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }

  private void loadMoreData(int page, int page_size) {

    model.getLadyList(page, page_size)
        .subscribeOn(Schedulers.newThread())
        .subscribe(new ServiceResponse<LadyListBean>() {
          @Override public void onNext(LadyListBean productListBean) {
            List<LadyListBean.ResultsEntity> results = productListBean.getResults();
            mLadyListAdapter.update(results);
          }

          @Override public void onCompleted() {
            super.onCompleted();
            xRecyclerView.post(xRecyclerView::loadMoreComplete);
          }
        });
  }
}
