package com.jimei.xiaolumeimei.ui.fragment;

import android.support.v7.widget.GridLayoutManager;
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
import rx.schedulers.Schedulers;

/**
 * Created by itxuye(www.itxuye.com) on 15/12/29.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class LadyListFragment extends BaseFragment {

  ProductModel model = new ProductModel();
  private XRecyclerView xRecyclerView;
  private LadyListAdapter mLadyListAdapter;
  private RotateLoading loading;

  @Override protected int provideContentViewId() {
    return R.layout.ladylist_fragment;
  }

  @Override protected void initData() {
    loading.start();

    model.getLadyList()
        .subscribeOn(Schedulers.newThread())
        .subscribe(new ServiceResponse<LadyListBean>() {
          @Override public void onNext(LadyListBean ladyListBean) {

            try {
              if (ladyListBean != null) {
                List<LadyListBean.ResultsEntity> results = ladyListBean.getResults();
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
        model.getLadyList()
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
        xRecyclerView.postDelayed(xRecyclerView::loadMoreComplete, 1000);
      }
    });
  }
}
