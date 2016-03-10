package com.jimei.xiaolumeimei.ui.fragment.view;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.ChildListAdapter;
import com.jimei.xiaolumeimei.entities.ChildListBean;
import com.jimei.xiaolumeimei.model.ProductModel;
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
public class ChildListView extends ViewImpl {
  @Bind(R.id.loading) RotateLoading loading;
  @Bind(R.id.childlist_recyclerView) XRecyclerView xRecyclerView;

  int page_size = 10;
  private int page = 2;
  private int totalPages;//总的分页数
  private ChildListAdapter mChildListAdapter;
  //private TextView mNormal, mOrder;
  private Subscription subscribe1;
  private Subscription subscribe2;
  private Subscription subscribe3;

  @Override public int getLayoutId() {
    return R.layout.childlist_fragment;
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
    xRecyclerView.setLayoutManager(new GridLayoutManager(context, 2));

    xRecyclerView.addItemDecoration(new SpaceItemDecoration(10));

    xRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
    xRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.SemiCircleSpin);
    xRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);

    mChildListAdapter = new ChildListAdapter(fragment, context);
    xRecyclerView.setAdapter(mChildListAdapter);


    loading.start();
    subscribe1 = ProductModel.getInstance()
        .getChildList(1, 10)
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<ChildListBean>() {
          @Override public void onError(Throwable e) {
            super.onError(e);
            e.printStackTrace();
            JUtils.Toast("请检查网络状况,尝试下拉刷新");
            loading.stop();
          }

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

    xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
      @Override public void onRefresh() {
        subscribe2 = ProductModel.getInstance()
            .getChildList(1, page * page_size)
            .subscribeOn(Schedulers.io())
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
          Toast.makeText(context, "没有更多了拉,去购物吧", Toast.LENGTH_SHORT).show();
          xRecyclerView.post(xRecyclerView::loadMoreComplete);
        }
      }
    });
  }

  private void loadMoreData(int page, int page_size) {

    subscribe3 = ProductModel.getInstance()
        .getChildList(page, page_size)
        .subscribeOn(Schedulers.io())
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
