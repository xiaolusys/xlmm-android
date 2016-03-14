package com.jimei.xiaolumeimei.ui.fragment.v1.view;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.LadyListAdapter;
import com.jimei.xiaolumeimei.entities.LadyListBean;
import com.jimei.xiaolumeimei.model.ProductModel;
import com.jimei.xiaolumeimei.ui.fragment.view.ViewImpl;
import com.jimei.xiaolumeimei.widget.SpaceItemDecoration;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.victor.loading.rotate.RotateLoading;
import java.util.List;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye(www.itxuye.com) on 2016/03/10.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class LadyListView extends ViewImpl {

  @Bind(R.id.loading) RotateLoading loading;
  @Bind(R.id.childlist_recyclerView) XRecyclerView xRecyclerView;

  int page_size = 10;
  private int page = 2;
  private int totalPages;//总的分页数
  private LadyListAdapter mLadyListAdapter;
  private Subscription subscribe1;
  private Subscription subscribe2;
  private Subscription subscribe3;

  @Override public int getLayoutId() {
    return R.layout.ladylist_fragment;
  }

  @Override public void destroy() {
    ButterKnife.unbind(this);
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

  public void initViews(Fragment fragment, Context context) {
    GridLayoutManager layoutManager = new GridLayoutManager(context, 2);

    xRecyclerView.setLayoutManager(layoutManager);

    xRecyclerView.addItemDecoration(new SpaceItemDecoration(10));

    xRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
    xRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.SemiCircleSpin);
    xRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);

    mLadyListAdapter = new LadyListAdapter(context, fragment);
    xRecyclerView.setAdapter(mLadyListAdapter);

    loading.start();

    subscribe1 = ProductModel.getInstance()
        .getLadyList(1, 10)
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<LadyListBean>() {
          @Override public void onError(Throwable e) {
            super.onError(e);
            e.printStackTrace();
            JUtils.Toast("请检查网络状况,尝试下拉刷新");
            loading.stop();
          }

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

    xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
      @Override public void onRefresh() {
        subscribe2 = ProductModel.getInstance()
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
      }

      @Override public void onLoadMore() {
        if (page <= totalPages) {
          loadMoreData(page, 10);
          page++;
        } else {
          Toast.makeText(context, "没有更多了拉,去购物吧", Toast.LENGTH_SHORT).show();
          xRecyclerView.post(xRecyclerView::loadMoreComplete);
        }
      }
    });
  }

  private void loadMoreData(int page, int page_size) {

    subscribe3 = ProductModel.getInstance()
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
  }
}
