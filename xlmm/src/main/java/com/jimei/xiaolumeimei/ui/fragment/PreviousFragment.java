package com.jimei.xiaolumeimei.ui.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.PreviousAdapter;
import com.jimei.xiaolumeimei.base.BaseFragment;
import com.jimei.xiaolumeimei.entities.IndexBean;
import com.jimei.xiaolumeimei.entities.PostBean;
import com.jimei.xiaolumeimei.model.ProductModel;
import com.jimei.xiaolumeimei.widget.SpaceItemDecoration;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.victor.loading.rotate.RotateLoading;
import java.util.ArrayList;
import java.util.List;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye(www.itxuye.com) on 15/12/29.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class PreviousFragment extends BaseFragment {

  ProductModel model = new ProductModel();
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
    model.getPreviousList()
        .subscribeOn(Schedulers.newThread())
        .subscribe(new ServiceResponse<IndexBean>() {
          @Override public void onNext(IndexBean indexBean) {
            List<IndexBean.product> child_list = indexBean.getChild_list();
            List<IndexBean.product> female_list = indexBean.getFemale_list();
            List<IndexBean.product> list = new ArrayList<>();
            list.addAll(child_list);
            list.addAll(female_list);
            mPreviousAdapter.update(list);
            mPreviousAdapter.notifyDataSetChanged();
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

    model.getYestdayPost()
        .subscribeOn(Schedulers.newThread())
        .subscribe(new ServiceResponse<PostBean>() {
          @Override public void onNext(PostBean postBean) {

            String picLink = postBean.getWem_posters().get(0).pic_link;
            String picLink1 = postBean.getChd_posters().get(0).pic_link;

            post1.post(() -> Glide.with(getActivity())
                .load(picLink)
                .placeholder(R.drawable.header)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(post1));

            post2.post(() -> Glide.with(getActivity())
                .load(picLink1)
                .placeholder(R.drawable.header)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(post2));
          }
        });

    mPreviousAdapter = new PreviousAdapter(getActivity());
    xRecyclerView.setAdapter(mPreviousAdapter);

    xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
      @Override public void onRefresh() {
        model.getPreviousList()
            .subscribeOn(Schedulers.newThread())
            .subscribe(new ServiceResponse<IndexBean>() {
              @Override public void onNext(IndexBean indexBean) {
                List<IndexBean.product> child_list = indexBean.getChild_list();
                List<IndexBean.product> female_list = indexBean.getFemale_list();
                List<IndexBean.product> list = new ArrayList<>();
                list.addAll(child_list);
                list.addAll(female_list);
                mPreviousAdapter.updateWithClear(list);
                mPreviousAdapter.notifyDataSetChanged();
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
