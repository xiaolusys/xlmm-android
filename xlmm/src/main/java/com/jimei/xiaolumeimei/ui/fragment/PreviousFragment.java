package com.jimei.xiaolumeimei.ui.fragment;

import android.os.AsyncTask;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.iwgang.countdownview.CountdownView;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.PreviousAdapter;
import com.jimei.xiaolumeimei.base.BaseFragment;
import com.jimei.xiaolumeimei.entities.PostBean;
import com.jimei.xiaolumeimei.entities.ProductListBean;
import com.jimei.xiaolumeimei.model.ProductModel;
import com.jimei.xiaolumeimei.utils.ViewUtils;
import com.jimei.xiaolumeimei.widget.SpaceItemDecoration;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.victor.loading.rotate.RotateLoading;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye(www.itxuye.com) on 15/12/29.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class PreviousFragment extends BaseFragment {

  int page_size = 10;
  private int page = 2;
  private int totalPages;//总的分页数
  private XRecyclerView xRecyclerView;
  private PreviousAdapter mPreviousAdapter;
  private ImageView post1;
  private ImageView post2;
  private RotateLoading loading;
  private View head;
  private CountdownView countTime;
  private Subscription subscription;
  private TextView tv_tomorrow;
  private Subscription subscribe;

  @Override protected int provideContentViewId() {
    return R.layout.previous_fragment;
  }

  @Override protected void initData() {

    loading.start();
    subscribe = ProductModel.getInstance()
        .getPreviousList(1, 10)
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<ProductListBean>() {
          @Override public void onNext(ProductListBean productListBean) {

            try {

              if (productListBean != null) {

                List<ProductListBean.ResultsEntity> results =
                    productListBean.getResults();
                totalPages = productListBean.getCount() / page_size;
                mPreviousAdapter.update(results);
              }
            } catch (NullPointerException ex) {

            }
          }

          @Override public void onCompleted() {
            super.onCompleted();
            loading.post(loading::stop);
          }
        });

    new AsyncTask<Void, Long, Void>() {
      @Override protected Void doInBackground(Void... params) {
        long time = calcLeftTime();
        while (true) {
          try {
            Thread.sleep(1000);
            time -= 1000;
            publishProgress(time);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }

      @Override protected void onProgressUpdate(Long... values) {
        super.onProgressUpdate(values);
        countTime.updateShow(values[0]);
      }
    }.execute();
  }

  @Override protected void initViews() {
    loading = (RotateLoading) view.findViewById(R.id.loading);
    initRecyclerView();
  }

  @Override public void onStop() {
    super.onStop();
    if (subscription != null && subscription.isUnsubscribed()) {
      subscription.unsubscribe();
    }

    if (subscribe != null && subscribe.isUnsubscribed()) {
      subscribe.unsubscribe();
    }
  }

  @Override public void onResume() {
    super.onResume();

    countTime = (CountdownView) head.findViewById(R.id.countTime);

    subscription = Observable.timer(1, 1, TimeUnit.SECONDS)
        .map(aLong -> calcLeftTime())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(new Action1<Long>() {
          @Override public void call(Long aLong) {
            if (aLong > 0) {
              countTime.updateShow(aLong);
            } else {
              countTime.setVisibility(View.INVISIBLE);
              tv_tomorrow.setVisibility(View.VISIBLE);
            }
          }
        }, throwable -> {
        });
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
    countTime = (CountdownView) head.findViewById(R.id.countTime);
    tv_tomorrow = (TextView) head.findViewById(R.id.tv_tomorrow);
    xRecyclerView.addHeaderView(head);

    subscribe = ProductModel.getInstance()
        .getYestdayPost()
        .subscribeOn(Schedulers.newThread())
        .subscribe(new ServiceResponse<PostBean>() {
                     @Override public void onNext(PostBean postBean) {
                       try {
                         String picLink = postBean.getWemPosters().get(0).getPicLink();
                         String picLink1 = postBean.getmChdPosters().get(0).getPicLink();

                         post1.post(new Runnable() {
                           @Override public void run() {

                             ViewUtils.loadImgToImgViewPost(getActivity(), post1,
                                 picLink);
                           }
                         });

                         post2.post(new Runnable() {
                           @Override public void run() {
                             ViewUtils.loadImgToImgViewPost(getActivity(), post2,
                                 picLink1);
                           }
                         });
                       } catch (NullPointerException ex) {
                         ex.printStackTrace();
                       }
                     }
                   }

        );

    mPreviousAdapter = new PreviousAdapter(getActivity());
    xRecyclerView.setAdapter(mPreviousAdapter);

    xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
                                       @Override public void onRefresh() {
                                         subscribe = ProductModel.getInstance()
                                             .getTodayList(1, page * page_size)
                                             .subscribeOn(Schedulers.newThread())
                                             .subscribe(
                                                 new ServiceResponse<ProductListBean>() {
                                                   @Override public void onNext(
                                                       ProductListBean productListBean) {
                                                     List<ProductListBean.ResultsEntity>
                                                         results =
                                                         productListBean.getResults();
                                                     mPreviousAdapter.updateWithClear(
                                                         results);
                                                   }

                                                   @Override public void onCompleted() {
                                                     super.onCompleted();
                                                     xRecyclerView.post(
                                                         xRecyclerView::refreshComplete);
                                                   }
                                                 });
                                       }

                                       @Override public void onLoadMore() {

                                         if (page <= totalPages) {
                                           loadMoreData(page, 10);
                                           page++;
                                         } else {
                                           Toast.makeText(activity, "没有更多了拉,去购物吧",
                                               Toast.LENGTH_SHORT).show();
                                           xRecyclerView.post(
                                               xRecyclerView::loadMoreComplete);
                                         }
                                       }
                                     }

    );
  }

  private void loadMoreData(int page, int page_size) {

    subscribe = ProductModel.getInstance()
        .getPreviousList(page, page_size)
        .subscribeOn(Schedulers.newThread())
        .subscribe(new ServiceResponse<ProductListBean>() {
          @Override public void onNext(ProductListBean productListBean) {
            List<ProductListBean.ResultsEntity> results = productListBean.getResults();
            mPreviousAdapter.update(results);
          }

          @Override public void onCompleted() {
            super.onCompleted();
            xRecyclerView.post(xRecyclerView::loadMoreComplete);
          }
        });
  }

  private long calcLeftTime() {

    Date now = new Date();
    Date nextDay14PM = new Date();
    Calendar calendar = Calendar.getInstance();

    calendar.setTime(nextDay14PM);
    calendar.set(Calendar.HOUR_OF_DAY, 14);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);

    nextDay14PM = calendar.getTime();

    long left;
    if (nextDay14PM.getTime() - now.getTime() > 0) {
      left = nextDay14PM.getTime() - now.getTime();
      return left;
    } else {
      return 0;
    }
  }
}
