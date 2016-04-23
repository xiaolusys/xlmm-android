package com.jimei.xiaolumeimei.ui.fragment.v2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.afollestad.materialdialogs.MaterialDialog;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.PreviousAdapter;
import com.jimei.xiaolumeimei.base.BaseFragment;
import com.jimei.xiaolumeimei.entities.ProductListBean;
import com.jimei.xiaolumeimei.model.ProductModel;
import com.jimei.xiaolumeimei.widget.SpaceItemDecoration;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye(www.itxuye.com) on 2016/04/16.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class YesterdayV2Fragment extends BaseFragment {

  @Bind(R.id.xrcy_yesterdayv2) XRecyclerView xRecyclerView;
  int page_size = 10;
  private List<ProductListBean.ResultsEntity> list = new ArrayList<>();
  private MaterialDialog materialDialog;
  private int page = 2;
  private int totalPages;//总的分页数
  private PreviousAdapter mPreviousAdapter;
  //private TextView mNormal, mOrder;
  private Subscription subscribe1;
  private Subscription subscribe2;
  private Subscription subscribe3;

  public static YesterdayV2Fragment newInstance(String title) {
    YesterdayV2Fragment yesterdayV2Fragment = new YesterdayV2Fragment();
    Bundle bundle = new Bundle();
    bundle.putString("keyword", title);
    yesterdayV2Fragment.setArguments(bundle);
    return yesterdayV2Fragment;
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
  }



  @Override protected View initViews(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_yesterdayv2, container, false);
    ButterKnife.bind(this, view);
    initViews();
    return view;
  }

  @Override protected void initData() {
    load();
  }

  @Override protected void setDefaultFragmentTitle(String title) {

  }

  private void load() {
    showIndeterminateProgressDialog(false);
    subscribe1 = ProductModel.getInstance()
        .getPreviousList(1, 10)
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<ProductListBean>() {
          @Override public void onError(Throwable e) {
            super.onError(e);
            e.printStackTrace();
          }

          @Override public void onNext(ProductListBean productListBean) {

            try {

              if (productListBean != null) {
                List<ProductListBean.ResultsEntity> results =
                    productListBean.getResults();
                totalPages = productListBean.getCount() / page_size;
                list.addAll(results);
                mPreviousAdapter.update(list);
              }
            } catch (Exception ex) {
            }
          }

          @Override public void onCompleted() {
            super.onCompleted();
            //loading.post(loading::stop);
            hideIndeterminateProgressDialog();
          }
        });
  }

  //@Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
  //  super.onViewCreated(view, savedInstanceState);
  //
  //}

  private void initViews() {

    GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
    //manager.setOrientation(GridLayoutManager.VERTICAL);
    //manager.setSmoothScrollbarEnabled(true);
    xRecyclerView.setLayoutManager(manager);
    xRecyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);

    xRecyclerView.addItemDecoration(new SpaceItemDecoration(10));

    xRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
    xRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.SemiCircleSpin);
    xRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
    xRecyclerView.setPullRefreshEnabled(false);
    mPreviousAdapter = new PreviousAdapter(this, getActivity());
    xRecyclerView.setAdapter(mPreviousAdapter);

    xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
      @Override public void onRefresh() {
        //subscribe2 = ProductModel.getInstance()
        //    .getChildList(1, page * page_size)
        //    .subscribeOn(Schedulers.io())
        //    .subscribe(new ServiceResponse<ChildListBean>() {
        //      @Override public void onNext(ChildListBean childListBean) {
        //        List<ChildListBean.ResultsEntity> results = childListBean.getResults();
        //        mChildListAdapter.updateWithClear(results);
        //      }
        //
        //      @Override public void onCompleted() {
        //        super.onCompleted();
        //        try {
        //          xRecyclerView.post(xRecyclerView::refreshComplete);
        //        } catch (Exception e) {
        //          e.printStackTrace();
        //        }
        //      }
        //    });
      }

      @Override public void onLoadMore() {
        if (page <= totalPages) {
          loadMoreData(page, 10);
          page++;
        } else {
          Toast.makeText(getActivity(), "没有更多了拉,去购物吧", Toast.LENGTH_SHORT).show();
          xRecyclerView.post(xRecyclerView::loadMoreComplete);
        }
      }
    });
  }

  private void loadMoreData(int page, int page_size) {

    subscribe3 = ProductModel.getInstance()
        .getPreviousList(page, page_size)
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<ProductListBean>() {
          @Override public void onNext(ProductListBean productListBean) {
            List<ProductListBean.ResultsEntity> results = productListBean.getResults();
            mPreviousAdapter.update(results);
          }

          @Override public void onCompleted() {
            super.onCompleted();
            try {
              xRecyclerView.post(xRecyclerView::loadMoreComplete);
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
        });
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    ButterKnife.unbind(this);
  }

  @Override public void onDetach() {
    super.onDetach();
    try {
      Field childFragmentManager =
          Fragment.class.getDeclaredField("mChildFragmentManager");
      childFragmentManager.setAccessible(true);
      childFragmentManager.set(this, null);
    } catch (NoSuchFieldException e) {
      throw new RuntimeException(e);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }


  public void showIndeterminateProgressDialog(boolean horizontal) {
    materialDialog = new MaterialDialog.Builder(getActivity())
        //.title(R.string.progress_dialog)
        .content(R.string.please_wait)
        .progress(true, 0)
        .widgetColorRes(R.color.colorAccent)
        .progressIndeterminateStyle(horizontal)
        .show();
  }

  public void hideIndeterminateProgressDialog() {
    try {
      materialDialog.dismiss();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override public View getScrollableView() {
    return xRecyclerView;
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
