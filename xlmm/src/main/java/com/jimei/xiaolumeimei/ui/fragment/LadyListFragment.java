package com.jimei.xiaolumeimei.ui.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.widget.Toast;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.LadyListAdapter;
import com.jimei.xiaolumeimei.base.BaseFragment;
import com.jimei.xiaolumeimei.entities.LadyListBean;
import com.jimei.xiaolumeimei.model.ProductModel;
import com.jimei.xiaolumeimei.widget.SpaceItemDecoration;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.victor.loading.rotate.RotateLoading;
import java.util.List;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye(www.itxuye.com) on 15/12/29.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class LadyListFragment extends BaseFragment {
  int page_size = 10;
  private int page = 2;
  private int totalPages;//总的分页数
  private XRecyclerView xRecyclerView;
  private LadyListAdapter mLadyListAdapter;
  private RotateLoading loading;
  private Subscription subscribe1;
  private Subscription subscribe2;
  private Subscription subscribe3;

  @Override protected int provideContentViewId() {
    return R.layout.ladylist_fragment;
  }

  @Override protected void initData() {
    loading.start();

    subscribe1 = ProductModel.getInstance()
        .getLadyList(1, 10)
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

  @Override protected void initViews() {
    loading = (RotateLoading) view.findViewById(R.id.loading);
    initRecyclerView();
  }

  private void initRecyclerView() {

    xRecyclerView = (XRecyclerView) view.findViewById(R.id.childlist_recyclerView);
    GridLayoutManager layoutManager = new GridLayoutManager(activity, 2);

    xRecyclerView.setLayoutManager(layoutManager);

    xRecyclerView.addItemDecoration(new SpaceItemDecoration(10));

    xRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
    xRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.SemiCircleSpin);
    xRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);

    mLadyListAdapter = new LadyListAdapter(getActivity());
    xRecyclerView.setAdapter(mLadyListAdapter);

    xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
      @Override public void onRefresh() {
        subscribe2 = ProductModel.getInstance()
            .getLadyList(1, page * page_size)
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
          Toast.makeText(activity, "没有更多了拉,去购物吧", Toast.LENGTH_SHORT).show();
          xRecyclerView.post(xRecyclerView::loadMoreComplete);
        }
      }
    });
  }

  private void loadMoreData(int page, int page_size) {

    subscribe3 = ProductModel.getInstance()
        .getLadyList(page, page_size)
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

  @Override public void onStop() {
    super.onStop();
    if (subscribe1 != null && subscribe1.isUnsubscribed()) {
      subscribe1.unsubscribe();
    }
    if (subscribe2 != null && subscribe2.isUnsubscribed()) {
      subscribe2.unsubscribe();
    }
    if (subscribe3 != null && subscribe3.isUnsubscribed()) {
      subscribe3.unsubscribe();
    }
  }
}
