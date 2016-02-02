package com.jimei.xiaolumeimei.ui.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.widget.Toast;
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
  private int page = 2;
  private int totalPages;//总的分页数
  int page_size = 10;
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
    model.getChildList(1,10)
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
          Toast.makeText(activity, "没有更多了拉,去购物吧", Toast.LENGTH_SHORT).show();
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
