package com.jimei.xiaolumeimei.ui.fragment;

import android.support.v7.widget.GridLayoutManager;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.ChildListAdapter;
import com.jimei.xiaolumeimei.base.BaseFragment;
import com.jimei.xiaolumeimei.data.XlmmApi;
import com.jimei.xiaolumeimei.model.ChildListBean;
import com.jimei.xiaolumeimei.okhttp.callback.OkHttpCallback;
import com.jimei.xiaolumeimei.okhttp.request.OkHttpRequest;
import com.jimei.xiaolumeimei.widget.SpaceItemDecoration;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.victor.loading.rotate.RotateLoading;
import java.util.List;

/**
 * Created by itxuye(www.itxuye.com) on 15/12/29.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class ChildListFragment extends BaseFragment {

  private XRecyclerView xRecyclerView;
  private ChildListAdapter mChildListAdapter;
  private RotateLoading loading;

  @Override protected int provideContentViewId() {
    return R.layout.childlist_fragment;
  }

  @Override protected void initData() {
loading.start();
    new OkHttpRequest.Builder().url(XlmmApi.CHILD_URL)
        .get(new OkHttpCallback<ChildListBean>() {
          @Override public void onError(Request request, Exception e) {
          loading.stop();
          }

          @Override public void onResponse(Response response, ChildListBean data) {
            List<ChildListBean.ResultsEntity> results = data.getResults();
            //Log.i("xlmm", results.toString());
            mChildListAdapter.update(results);
            mChildListAdapter.notifyDataSetChanged();
            loading.stop();

          }
        });
  }

  @Override protected void initViews() {
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
        new OkHttpRequest.Builder().url(XlmmApi.CHILD_URL)
            .get(new OkHttpCallback<ChildListBean>() {
              @Override public void onError(Request request, Exception e) {

              }

              @Override public void onResponse(Response response, ChildListBean data) {
                List<ChildListBean.ResultsEntity> results = data.getResults();
                //Log.i("xlmm", results.toString());
                mChildListAdapter.updateWithClear(results);
                mChildListAdapter.notifyDataSetChanged();
                xRecyclerView.refreshComplete();
              }
            });


      }

      @Override public void onLoadMore() {
        xRecyclerView.postDelayed(new Runnable() {
          @Override public void run() {
            xRecyclerView.loadMoreComplete();
          }
        }, 1000);
      }
    });
  }
}
