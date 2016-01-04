package com.jimei.xiaolumeimei.ui.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.TodayAdapter;
import com.jimei.xiaolumeimei.base.BaseFragment;
import com.jimei.xiaolumeimei.data.XlmmApi;
import com.jimei.xiaolumeimei.model.PostBean;
import com.jimei.xiaolumeimei.model.ProductListBean;
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
public class TodayFragment extends BaseFragment {
  int page_size = 10;
  private XRecyclerView xRecyclerView;
  private TodayAdapter mTodayAdapter;
  private View head;
  private ImageView post1;
  private ImageView post2;
  private RotateLoading loading;
  private int page = 2;
  private int totalPages;//总的分页数

  @Override protected int provideContentViewId() {
    return R.layout.today_fragment;
  }

  @Override protected void initData() {
    loading.start();
    new OkHttpRequest.Builder().url(XlmmApi.TODAY_URL + "?page=1&page_size=" + page_size)
        .get(new OkHttpCallback<ProductListBean>() {
          @Override public void onError(Request request, Exception e) {
            Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
          }

          @Override public void onResponse(Response response, ProductListBean data) {
            List<ProductListBean.ResultsEntity> list = data.getResults();
            totalPages = data.getCount() / page_size + 1;
            mTodayAdapter.update(list);
            mTodayAdapter.notifyDataSetChanged();
            loading.stop();
          }
        });
  }

  @Override protected void initViews() {
    loading = (RotateLoading) view.findViewById(R.id.loading);
    initRecyclerView();
  }

  private void initRecyclerView() {

    xRecyclerView = (XRecyclerView) view.findViewById(R.id.xrecyclerView);
    xRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
    xRecyclerView.addItemDecoration(new SpaceItemDecoration(10));
    xRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
    xRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.SemiCircleSpin);
    xRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);

    head = LayoutInflater.from(getActivity())
        .inflate(R.layout.today_poster_header,
            (ViewGroup) view.findViewById(R.id.head_today), false);

    post1 = (ImageView) head.findViewById(R.id.post_1);
    post2 = (ImageView) head.findViewById(R.id.post_2);

    xRecyclerView.addHeaderView(head);

    new OkHttpRequest.Builder().url(XlmmApi.TODAY_POSTER_URL)
        .get(new OkHttpCallback<PostBean>() {
          @Override public void onError(Request request, Exception e) {
            Log.i("xlmm", e.getMessage());
          }

          @Override public void onResponse(Response response, PostBean data) {
            String picLink = data.getWem_posters().get(0).pic_link;
            String picLink1 = data.getChd_posters().get(0).pic_link;
            Glide.with(getActivity())
                .load(picLink)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(post1);

            Glide.with(getActivity())
                .load(picLink1)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(post2);
          }
        });

    mTodayAdapter = new TodayAdapter(getActivity());
    xRecyclerView.setAdapter(mTodayAdapter);

    xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
      @Override public void onRefresh() {
        new OkHttpRequest.Builder().url(
            XlmmApi.TODAY_URL + "?page=1&page_size=" + page * page_size)
            .get(new OkHttpCallback<ProductListBean>() {
              @Override public void onError(Request request, Exception e) {

              }

              @Override public void onResponse(Response response, ProductListBean data) {
                List<ProductListBean.ResultsEntity> list = data.getResults();

                mTodayAdapter.updateWithClear(list);
                mTodayAdapter.notifyDataSetChanged();
                xRecyclerView.refreshComplete();
              }
            });
      }

      @Override public void onLoadMore() {
        if (page <= totalPages) {
          loadMoreData(page, 10);
          page++;
        } else {
          xRecyclerView.post(() -> {
            Toast.makeText(activity, "没有更多数据啦", Toast.LENGTH_SHORT).show();
            xRecyclerView.loadMoreComplete();
          });
        }
      }
    });
  }

  private void loadMoreData(int page, int page_size) {
    new OkHttpRequest.Builder().url(
        XlmmApi.TODAY_URL + "?page=" + page + "&page_size=" + page_size)
        .get(new OkHttpCallback<ProductListBean>() {
          @Override public void onError(Request request, Exception e) {
            //Log.i("xlmm", e.getMessage());
          }

          @Override public void onResponse(Response response, ProductListBean data) {
            List<ProductListBean.ResultsEntity> list = data.getResults();

            mTodayAdapter.update(list);
            mTodayAdapter.notifyDataSetChanged();
            xRecyclerView.loadMoreComplete();
          }
        });
  }
}

