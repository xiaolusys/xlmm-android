package com.jimei.xiaolumeimei.ui.fragment.v1.view;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import cn.iwgang.countdownview.CountdownView;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.PreviousAdapter;
import com.jimei.xiaolumeimei.entities.PostBean;
import com.jimei.xiaolumeimei.entities.ProductListBean;
import com.jimei.xiaolumeimei.model.ProductModel;
import com.jimei.xiaolumeimei.ui.fragment.view.ViewImpl;
import com.jimei.xiaolumeimei.utils.ViewUtils;
import com.jimei.xiaolumeimei.widget.SpaceItemDecoration;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
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
 * Created by itxuye(www.itxuye.com) on 2016/03/10.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class PreviousListView extends ViewImpl {
  @Bind(R.id.loading) RotateLoading loading;
  @Bind(R.id.previous_xrv) XRecyclerView xRecyclerView;
  int page_size = 10;
  private int page = 2;
  private int totalPages;//总的分页数
  private PreviousAdapter mPreviousAdapter;
  private ImageView post1;
  private ImageView post2;
  private View head;
  private CountdownView countTime;
  private TextView tv_tomorrow;
  private Subscription subscribe1;
  private Subscription subscription2;
  private Subscription subscribe3;
  private Subscription subscribe4;
  private Subscription subscribe5;

  @Override public int getLayoutId() {
    return R.layout.previous_fragment;
  }

  @Override public void destroy() {
    ButterKnife.unbind(this);

    if (subscribe1 != null && subscribe1.isUnsubscribed()) {
      subscribe1.unsubscribe();
    }
    if (subscription2 != null && subscription2.isUnsubscribed()) {
      subscription2.unsubscribe();
    }

    if (subscribe3 != null && subscribe3.isUnsubscribed()) {
      subscribe3.unsubscribe();
    }
    if (subscribe4 != null && subscribe4.isUnsubscribed()) {
      subscribe4.unsubscribe();
    }
    if (subscribe5 != null && subscribe5.isUnsubscribed()) {
      subscribe5.unsubscribe();
    }
  }

  public void initViews(Fragment fragment, Context context) {
    xRecyclerView.setLayoutManager(new GridLayoutManager(context, 2));
    xRecyclerView.addItemDecoration(new SpaceItemDecoration(10));
    xRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
    xRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.SemiCircleSpin);
    xRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);

    head = LayoutInflater.from(context)
        .inflate(R.layout.previous_poster_header,
            (ViewGroup) mRootView.findViewById(R.id.head_today), false);

    post1 = (ImageView) head.findViewById(R.id.post_1);
    post2 = (ImageView) head.findViewById(R.id.post_2);
    countTime = (CountdownView) head.findViewById(R.id.countTime);
    tv_tomorrow = (TextView) head.findViewById(R.id.tv_tomorrow);
    xRecyclerView.addHeaderView(head);

    initPost(context);

    mPreviousAdapter = new PreviousAdapter(fragment, context);
    xRecyclerView.setAdapter(mPreviousAdapter);

    head.setVisibility(View.INVISIBLE);
    loading.start();

    subscription2 = Observable.timer(1, 1, TimeUnit.SECONDS)
        .onBackpressureDrop()
        .map(aLong -> calcLeftTime())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action1<Long>() {
          @Override public void call(Long aLong) {
            if (aLong > 0) {
              countTime.updateShow(aLong);
            } else {
              countTime.setVisibility(View.INVISIBLE);
              tv_tomorrow.setVisibility(View.VISIBLE);
            }
          }
        }, Throwable::printStackTrace);
    subscribe1 = ProductModel.getInstance()
        .getPreviousList(1, 10)
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<ProductListBean>() {
          @Override public void onError(Throwable e) {
            super.onError(e);
            e.printStackTrace();

            loading.stop();
            JUtils.Toast("请检查网络状况,尝试下拉刷新");
          }

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
            head.setVisibility(View.VISIBLE);
          }
        });

    xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
                                       @Override public void onRefresh() {

                                         initPost(context);

                                         subscribe4 = ProductModel.getInstance()
                                             .getTodayList(1, page * page_size)
                                             .subscribeOn(Schedulers.io())
                                             .subscribe(new ServiceResponse<ProductListBean>() {
                                               @Override public void onNext(ProductListBean productListBean) {
                                                 List<ProductListBean.ResultsEntity> results =
                                                     productListBean.getResults();
                                                 mPreviousAdapter.updateWithClear(results);
                                               }

                                               @Override public void onCompleted() {
                                                 super.onCompleted();
                                                 head.setVisibility(View.VISIBLE);
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
                                     }

    );
  }

  private void initPost(Context context) {
    subscribe3 = ProductModel.getInstance()
        .getYestdayPost()
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<PostBean>() {
                     @Override public void onNext(PostBean postBean) {
                       try {
                         String picLink = postBean.getWemPosters().get(0).getPicLink();
                         String picLink1 = postBean.getmChdPosters().get(0).getPicLink();

                         post1.post(new Runnable() {
                           @Override public void run() {

                             try {
                               ViewUtils.loadImgToImgViewPost(context, post1, picLink);
                             } catch (Exception e) {
                               e.printStackTrace();
                             }
                           }
                         });

                         post2.post(new Runnable() {
                           @Override public void run() {
                             try {
                               ViewUtils.loadImgToImgViewPost(context, post2, picLink1);
                             } catch (Exception e) {
                               e.printStackTrace();
                             }
                           }
                         });
                       } catch (NullPointerException ex) {
                         ex.printStackTrace();
                       }
                     }
                   }

        );
  }

  private void loadMoreData(int page, int page_size) {

    subscribe5 = ProductModel.getInstance()
        .getPreviousList(page, page_size)
        .subscribeOn(Schedulers.io())
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
