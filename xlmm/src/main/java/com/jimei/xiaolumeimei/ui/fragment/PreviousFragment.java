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
import com.jimei.xiaolumeimei.adapter.PreviousAdapter;
import com.jimei.xiaolumeimei.base.BaseFragment;
import com.jimei.xiaolumeimei.data.XlmmApi;
import com.jimei.xiaolumeimei.model.IndexBean;
import com.jimei.xiaolumeimei.model.PostBean;
import com.jimei.xiaolumeimei.okhttp.callback.OkHttpCallback;
import com.jimei.xiaolumeimei.okhttp.request.OkHttpRequest;
import com.jimei.xiaolumeimei.widget.SpaceItemDecoration;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.victor.loading.rotate.RotateLoading;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by itxuye(www.itxuye.com) on 15/12/29.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class PreviousFragment extends BaseFragment {

  private XRecyclerView xRecyclerView;
  private PreviousAdapter mPreviousAdapter;

  private ImageView post1;
  private ImageView post2;
  private RotateLoading loading;
  private View head;

  @Override protected int provideContentViewId() {
    return R.layout.previous_fragment;
  }

  @Override protected void initData() {

    loading.start();
    new OkHttpRequest.Builder().url(XlmmApi.YESTERDAY_URL)
        .get(new OkHttpCallback<IndexBean>() {
          @Override public void onError(Request request, Exception e) {
            Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
          }

          @Override public void onResponse(Response response, IndexBean data) {
            List<IndexBean.product> child_list = data.getChild_list();
            List<IndexBean.product> female_list = data.getFemale_list();
            List<IndexBean.product> list = new ArrayList<>();
            list.addAll(child_list);
            list.addAll(female_list);

            mPreviousAdapter.update(list);
            mPreviousAdapter.notifyDataSetChanged();
            loading.stop();
          }
        });
  }

  @Override protected void initViews() {
    loading = (RotateLoading) view.findViewById(R.id.loading);
    initRecyclerView();
  }

  private void initRecyclerView() {

    xRecyclerView = (XRecyclerView) view.findViewById(R.id.previous_xrv);
    xRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
    xRecyclerView.addItemDecoration(new SpaceItemDecoration(10));
    xRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
    xRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.SemiCircleSpin);
    xRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);

    head = LayoutInflater.from(getActivity())
        .inflate(R.layout.previous_poster_header,
            (ViewGroup) view.findViewById(R.id.head_today), false);

    post1 = (ImageView) head.findViewById(R.id.post_1);
    post2 = (ImageView) head.findViewById(R.id.post_2);

    xRecyclerView.addHeaderView(head);

    new OkHttpRequest.Builder().url(XlmmApi.YESTERDAY_POSTER_URL)
        .get(new OkHttpCallback<PostBean>() {
          @Override public void onError(Request request, Exception e) {
            Log.i("xlmm", e.getMessage());
          }

          @Override public void onResponse(Response response, PostBean data) {
            String picLink = data.getWem_posters().get(0).pic_link;
            String picLink1 = data.getChd_posters().get(0).pic_link;
            Glide.with(getActivity())
                .load(picLink)
                .placeholder(R.drawable.header)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(post1);

            Glide.with(getActivity())
                .load(picLink1)
                .placeholder(R.drawable.header)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(post2);
          }
        });

    mPreviousAdapter = new PreviousAdapter(getActivity());
    xRecyclerView.setAdapter(mPreviousAdapter);

    xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
      @Override public void onRefresh() {
        new OkHttpRequest.Builder().url(XlmmApi.YESTERDAY_URL)
            .get(new OkHttpCallback<IndexBean>() {
              @Override public void onError(Request request, Exception e) {

              }

              @Override public void onResponse(Response response, IndexBean data) {
                List<IndexBean.product> child_list = data.getChild_list();
                List<IndexBean.product> female_list = data.getFemale_list();
                List<IndexBean.product> list = new ArrayList<>();
                list.addAll(child_list);
                list.addAll(female_list);

                mPreviousAdapter.updateWithClear(list);
                mPreviousAdapter.notifyDataSetChanged();
                xRecyclerView.refreshComplete();
              }
            });
      }

      @Override public void onLoadMore() {

        xRecyclerView.postDelayed(xRecyclerView::loadMoreComplete, 1000);
      }
    });
  }
}
