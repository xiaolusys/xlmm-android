package com.jimei.xiaolumeimei.ui.activity.product;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.LadyListActivityAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.LadyListBean;
import com.jimei.xiaolumeimei.model.ProductModel;
import com.jimei.xiaolumeimei.widget.SpaceItemDecoration;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import java.util.List;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye(www.itxuye.com) on 2016/02/03.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class LadyListActivity extends BaseSwipeBackCompatActivity {

  int page_size = 10;
  private int page = 2;
  private int totalPages;//总的分页数
  private XRecyclerView xRecyclerView;
  private LadyListActivityAdapter mLadyListAdapter;

  @Override protected void setListener() {

  }

  @Override protected void initData() {
    showIndeterminateProgressDialog(false);
    Subscription subscribe = ProductModel.getInstance()
        .getLadyList(1, 10)
        .subscribeOn(Schedulers.io())
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
            hideIndeterminateProgressDialog();
          }
        });
    addSubscription(subscribe);
  }

  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.activity_ladylist;
  }

  @Override protected void initViews() {
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

    mLadyListAdapter = new LadyListActivityAdapter(LadyListActivity.this);
    xRecyclerView.setAdapter(mLadyListAdapter);

    xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
      @Override public void onRefresh() {
        Subscription subscribe = ProductModel.getInstance()
            .getLadyList(1, page * page_size)
            .subscribeOn(Schedulers.io())
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
        addSubscription(subscribe);
      }

      @Override public void onLoadMore() {
        if (page <= totalPages) {
          loadMoreData(page, 10);
          page++;
        } else {
          Toast.makeText(LadyListActivity.this, "没有更多了拉,去购物吧", Toast.LENGTH_SHORT).show();
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

    Subscription subscribe = ProductModel.getInstance()
        .getLadyList(page, page_size)
        .subscribeOn(Schedulers.io())
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
    addSubscription(subscribe);
  }

  @Override protected void onStop() {
    super.onStop();
  }
}
