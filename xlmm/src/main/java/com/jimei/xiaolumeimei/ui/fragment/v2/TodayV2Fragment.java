package com.jimei.xiaolumeimei.ui.fragment.v2;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.TodayAdapter;
import com.jimei.xiaolumeimei.base.BaseFragment;
import com.jimei.xiaolumeimei.entities.ProductListOldBean;
import com.jimei.xiaolumeimei.model.ProductModel;
import com.jimei.xiaolumeimei.utils.RxUtils;
import com.jimei.xiaolumeimei.widget.SpaceItemDecoration;
import com.jimei.xiaolumeimei.widget.loadingdialog.XlmmLoadingDialog;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.umeng.analytics.MobclickAgent;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.iwgang.countdownview.CountdownView;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye(www.itxuye.com) on 2016/04/16.
 * <p>
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class TodayV2Fragment extends BaseFragment {

  private static final java.lang.String TAG = TodayV2Fragment.class.getSimpleName();
  @Bind(R.id.xrcy_todayv2) XRecyclerView xRecyclerView;
  int page_size = 10;
  Thread thread;
  private List<ProductListOldBean.ResultsEntity> list = new ArrayList<>();
  private int page = 2;
  private int totalPages;//总的分页数
  private TodayAdapter mTodayAdapter;
  private Subscription subscribe1;
  private Subscription subscribe3;
  private View head;
  private View view;
  private CountdownView countTime;
  private long left;
  private String upshelfStarttime;
  private XlmmLoadingDialog loadingdialog;

  public static TodayV2Fragment newInstance(String title) {
    TodayV2Fragment todayV2Fragment = new TodayV2Fragment();
    Bundle bundle = new Bundle();
    bundle.putString("keyword", title);
    todayV2Fragment.setArguments(bundle);
    return todayV2Fragment;
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
  }

  @Override public void onStart() {
    super.onStart();
  }

  @Override protected View initViews(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    view = inflater.inflate(R.layout.fragment_todayv2, container, false);
    ButterKnife.bind(this, view);
    initViews();

    return view;
  }

  @Override protected void initData() {
    load(null);
  }

  @Override protected void setDefaultFragmentTitle(String title) {

  }

  @Override public void onResume() {
    super.onResume();
    MobclickAgent.onPageStart(this.getClass().getSimpleName());

    JUtils.Log(TAG, "onResume");

    ProductModel.getInstance()
        .getTodayList(1, 1)
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<ProductListOldBean>() {
          @Override public void onNext(ProductListOldBean productListOldBean) {
            if (null != productListOldBean) {
              JUtils.Log(TAG, Thread.currentThread().getName());
              upshelfStarttime = productListOldBean.getUpshelfStarttime();
              //EventBus.getDefault().post(new v (upshelfStarttime));
            }
          }
        });
  }

  private long calcLeftTime(String crtTime) {
    long left = 0;
    Date now = new Date();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    try {
      crtTime = crtTime.replace("T", " ");
      Date crtdate = format.parse(crtTime);
      JUtils.Log(TAG,
          "crtdate.getTime() ==" + crtdate.getTime() + "     now.getTime()====" + now.getTime());
      if (crtdate.getTime() - now.getTime() > 0) {
        JUtils.Log(TAG,
            "crtdate.getTime() ==" + crtdate.getTime() + "     now.getTime()====" + now.getTime());
        left = crtdate.getTime() - now.getTime();
      }
    } catch (Exception e) {

    }

    return left;
  }

  public void load(SwipeRefreshLayout swipeRefreshLayout) {
    JUtils.Log(TAG, "load");
    list.clear();
    page = 2;
    if (mTodayAdapter != null) {
      mTodayAdapter.updateWithClear(list);
    }
    if (swipeRefreshLayout == null) {
      showIndeterminateProgressDialog(false);
    }
    subscribe1 = ProductModel.getInstance()
        .getTodayList(1, 10)
        .retryWhen(new RxUtils.RetryWhenNoInternet(100, 2000))
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<ProductListOldBean>() {
          @Override public void onError(Throwable e) {
            super.onError(e);
            e.printStackTrace();
          }

          @Override public void onNext(ProductListOldBean productListOldBean) {

            try {

              if (productListOldBean != null) {
                left = calcLeftTime(productListOldBean.getDownshelfDeadline());

                List<ProductListOldBean.ResultsEntity> results = productListOldBean.getResults();
                if (productListOldBean.getCount() % page_size == 0) {
                  totalPages = productListOldBean.getCount() / page_size;
                } else {
                  totalPages = productListOldBean.getCount() / page_size + 1;
                }
                list.clear();
                list.addAll(results);
                mTodayAdapter.updateWithClear(list);
                initLeftTime();
              }
            } catch (Exception ex) {

            }
          }

          @Override public void onCompleted() {
            super.onCompleted();
            //loading.post(loading::stop);
            if (swipeRefreshLayout != null) {
              swipeRefreshLayout.setRefreshing(false);
            } else {
              hideIndeterminateProgressDialog();
            }
          }
        });

    //        subscribe2 = Observable.timer(1, 1, TimeUnit.SECONDS)
    //                .onBackpressureDrop()
    //                .map(aLong -> calcLeftTime(left))
    //                .observeOn(AndroidSchedulers.mainThread())
    //                .subscribe(new Action1<Long>() {
    //                    @Override
    //                    public void call(Long aLong) {
    //                        if (aLong > 0) {
    //                            countTime.updateShow(aLong);
    //                        } else {
    //                            countTime.setVisibility(View.INVISIBLE);
    //                        }
    //                    }
    //                }, Throwable::printStackTrace);
  }

  private void initLeftTime() {
    if (thread == null) {
      thread = new Thread(new Runnable() {
        @Override public void run() {
          while (left > 0) {
            left--;
            SystemClock.sleep(1);
            if (activity != null) {
              activity.runOnUiThread(new Runnable() {
                @Override public void run() {
                  countTime.updateShow(left);
                }
              });
            }
          }
        }
      });
    }
    thread.start();
  }

  private void initViews() {

    head = LayoutInflater.from(getActivity())
        .inflate(R.layout.today_poster_header, (ViewGroup) view.findViewById(R.id.head_today),
            false);
    countTime = (CountdownView) head.findViewById(R.id.countTime);
    GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
    xRecyclerView.setLayoutManager(manager);
    xRecyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);

    xRecyclerView.addItemDecoration(new SpaceItemDecoration(10));

    xRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
    xRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.SemiCircleSpin);
    xRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
    xRecyclerView.setPullRefreshEnabled(false);
    mTodayAdapter = new TodayAdapter(this, getActivity());
    xRecyclerView.addHeaderView(head);
    xRecyclerView.setAdapter(mTodayAdapter);

    xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
      @Override public void onRefresh() {
      }

      @Override public void onLoadMore() {
        if (page <= totalPages) {
          loadMoreData(page, 10);
          page++;
          JUtils.Log(TAG, "page====" + page);
        } else {
          JUtils.Toast("没有更多了拉,去购物吧");
          xRecyclerView.post(xRecyclerView::loadMoreComplete);
        }
      }
    });
  }

  private void loadMoreData(int page, int page_size) {

    subscribe3 = ProductModel.getInstance()
        .getTodayList(page, page_size)
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<ProductListOldBean>() {
          @Override public void onNext(ProductListOldBean productListOldBean) {
            List<ProductListOldBean.ResultsEntity> results = productListOldBean.getResults();
            mTodayAdapter.update(results);
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
      Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
      childFragmentManager.setAccessible(true);
      childFragmentManager.set(this, null);
    } catch (NoSuchFieldException e) {
      throw new RuntimeException(e);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  public void showIndeterminateProgressDialog(boolean horizontal) {
    loadingdialog = XlmmLoadingDialog.create(activity)
        .setStyle(XlmmLoadingDialog.Style.SPIN_INDETERMINATE)
        .setCancellable(!horizontal)
        .show();
  }

  public void hideIndeterminateProgressDialog() {
    try {
      loadingdialog.dismiss();
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

    if (subscribe3 != null && subscribe3.isUnsubscribed()) {
      subscribe3.unsubscribe();
    }
  }

  @Override public void onDestroy() {
    super.onDestroy();
  }

  @Override public void onPause() {
    super.onPause();
    MobclickAgent.onPageEnd(this.getClass().getSimpleName());
  }

  public void goToTop() {
    try {
      xRecyclerView.scrollToPosition(0);
    } catch (NullPointerException e) {
      e.printStackTrace();
    }
  }
}
