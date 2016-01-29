package com.jimei.xiaolumeimei.ui.fragment;

import android.support.v7.widget.GridLayoutManager;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.ChildListAdapter;
import com.jimei.xiaolumeimei.base.BaseFragment;
import com.jimei.xiaolumeimei.entities.ChildListBean;
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
public class ChildListFragment extends BaseFragment {

  ProductModel model = new ProductModel();
  private XRecyclerView xRecyclerView;
  private ChildListAdapter mChildListAdapter;
  private RotateLoading loading;
  //private TextView mNormal, mOrder;

  @Override protected int provideContentViewId() {
    return R.layout.childlist_fragment;
  }

  @Override protected void initData() {
    loading.start();
    model.getChildList()
        .subscribeOn(Schedulers.newThread())
        .subscribe(new ServiceResponse<ChildListBean>() {
          @Override public void onNext(ChildListBean childListBean) {
            List<ChildListBean.ResultsEntity> results = childListBean.getResults();
            mChildListAdapter.update(results);
          }

          @Override public void onCompleted() {
            super.onCompleted();
            loading.post(loading::stop);
          }
        });
  }

  @Override protected void initViews() {
    //mNormal = (TextView) view.findViewById(R.id.normal);
    //mOrder = (TextView) view.findViewById(R.id.order);

    loading = (RotateLoading) view.findViewById(R.id.loading);
    initRecyclerView();
  }

  private void initRecyclerView() {

    xRecyclerView = (XRecyclerView) view.findViewById(R.id.childlist_recyclerView);
    xRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

    xRecyclerView.addItemDecoration(new SpaceItemDecoration(10));

    xRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
    xRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.SemiCircleSpin);
    xRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);

    mChildListAdapter = new ChildListAdapter(getActivity());
    xRecyclerView.setAdapter(mChildListAdapter);

    xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
      @Override public void onRefresh() {
        model.getChildList()
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
        xRecyclerView.postDelayed(xRecyclerView::loadMoreComplete, 1000);
      }
    });
  }
}
