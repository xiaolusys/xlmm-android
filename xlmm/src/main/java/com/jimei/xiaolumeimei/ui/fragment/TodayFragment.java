package com.jimei.xiaolumeimei.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import cn.iwgang.countdownview.CountdownView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.TodayAdapter;
import com.jimei.xiaolumeimei.base.BaseFragment;
import com.jimei.xiaolumeimei.entities.PostBean;
import com.jimei.xiaolumeimei.entities.ProductListBean;
import com.jimei.xiaolumeimei.model.ProductModel;
import com.jimei.xiaolumeimei.ui.activity.main.WebViewActivity;
import com.jimei.xiaolumeimei.ui.activity.product.ChildListActivity;
import com.jimei.xiaolumeimei.ui.activity.product.LadyListActivity;
import com.jimei.xiaolumeimei.utils.ViewUtils;
import com.jimei.xiaolumeimei.widget.SpaceItemDecoration;
import com.jimei.xiaolumeimei.widget.banner.Indicators.PagerIndicator;
import com.jimei.xiaolumeimei.widget.banner.SliderLayout;
import com.jimei.xiaolumeimei.widget.banner.SliderTypes.BaseSliderView;
import com.jimei.xiaolumeimei.widget.banner.SliderTypes.DefaultSliderView;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.victor.loading.rotate.RotateLoading;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class TodayFragment extends BaseFragment {
  private static final String TAG = TodayFragment.class.getSimpleName();

  int page_size = 10;
  ProductModel model = new ProductModel();
  private XRecyclerView xRecyclerView;
  private TodayAdapter mTodayAdapter;
  private ImageView post2;
  private RotateLoading loading;
  private int page = 2;
  private int totalPages;//总的分页数
  private CountdownView countTime;
  private SliderLayout mSliderLayout;
  private PagerIndicator mPagerIndicator;
  private View head;
  private Subscription subscription;
  //private long l;

  @Override protected int provideContentViewId() {
    return R.layout.today_fragment;
  }

  @Override protected void initData() {
    loading.start();
    model.getTodayList(1, 10)
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<ProductListBean>() {
          @Override public void onNext(ProductListBean productListBean) {

            try {
              if (productListBean != null) {
                List<ProductListBean.ResultsEntity> results =
                    productListBean.getResults();
                totalPages = productListBean.getCount() / page_size;
                mTodayAdapter.update(results);
              }
            } catch (NullPointerException ex) {
            }
          }

          @Override public void onCompleted() {
            super.onCompleted();
            loading.post(loading::stop);
          }
        });
    if (null != countTime) {
      long remainTime = countTime.getRemainTime();
      JUtils.Log(TAG, remainTime + "");
    }
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

    post2 = (ImageView) head.findViewById(R.id.post_2);
    countTime = (CountdownView) head.findViewById(R.id.countTime);
    mSliderLayout = (SliderLayout) head.findViewById(R.id.slider);
    mPagerIndicator = (PagerIndicator) head.findViewById(R.id.pi_header);

    xRecyclerView.addHeaderView(head);

    model.getTodayPost()
        .subscribeOn(Schedulers.newThread())
        .subscribe(new ServiceResponse<PostBean>() {
          @Override public void onNext(PostBean postBean) {
            try {
              String picLink =
                  ViewUtils.getDecodeUrl(postBean.getWem_posters().get(0).pic_link);
              String picLink1 =
                  ViewUtils.getDecodeUrl(postBean.getChd_posters().get(0).pic_link);

              Map<String, String> map = new HashMap<>();
              map.put("post1", picLink);
              map.put("post2", picLink1);

              for (String name : map.keySet()) {
                DefaultSliderView textSliderView = new DefaultSliderView(getActivity());
                // initialize a SliderLayout
                textSliderView.image(map.get(name))
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop);

                //add your extra information
                textSliderView.bundle(new Bundle());
                textSliderView.getBundle().putString("extra", name);
                mSliderLayout.addSlider(textSliderView);
                mSliderLayout.setDuration(3000);
                mSliderLayout.setCustomIndicator(mPagerIndicator);
                textSliderView.setOnSliderClickListener(
                    new BaseSliderView.OnSliderClickListener() {
                      @Override public void onSliderClick(BaseSliderView slider) {

                        String extra = slider.getBundle().getString("extra");
                        assert extra != null;
                        if (extra.equals("post2")) {
                          startActivity(
                              new Intent(getActivity(), ChildListActivity.class));
                        } else if (extra.equals("post1")) {
                          startActivity(
                              new Intent(getActivity(), LadyListActivity.class));
                        }
                      }
                    });
              }

              post2.post(new Runnable() {
                @Override public void run() {
                  Glide.with(getActivity())
                      .load(picLink1)
                      .diskCacheStrategy(DiskCacheStrategy.ALL)
                      .placeholder(R.drawable.header)
                      .centerCrop()
                      .into(post2);
                }
              });
            } catch (NullPointerException ex) {
              ex.printStackTrace();
            }
          }
        });

    mTodayAdapter = new TodayAdapter(getActivity());
    xRecyclerView.setAdapter(mTodayAdapter);

    xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
      @Override public void onRefresh() {
        model.getTodayList(1, page * page_size)
            .subscribeOn(Schedulers.newThread())
            .subscribe(new ServiceResponse<ProductListBean>() {
              @Override public void onNext(ProductListBean productListBean) {
                List<ProductListBean.ResultsEntity> results =
                    productListBean.getResults();
                mTodayAdapter.updateWithClear(results);
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
          Toast.makeText(activity, "没有更多了拉,去购物吧", Toast.LENGTH_SHORT).show();
          xRecyclerView.post(xRecyclerView::loadMoreComplete);
        }
      }
    });

    post2.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        startActivity(new Intent(getActivity(), WebViewActivity.class));
      }
    });
  }

  private void loadMoreData(int page, int page_size) {

    model.getTodayList(page, page_size)
        .subscribeOn(Schedulers.newThread())
        .subscribe(new ServiceResponse<ProductListBean>() {
          @Override public void onNext(ProductListBean productListBean) {
            List<ProductListBean.ResultsEntity> results = productListBean.getResults();
            mTodayAdapter.update(results);
          }

          @Override public void onCompleted() {
            super.onCompleted();
            xRecyclerView.post(xRecyclerView::loadMoreComplete);
          }
        });
  }

  @Override public void onDestroy() {
    super.onDestroy();
  }

  private long calcLeftTime() {

    Date now = new Date();
    Date nextDay14PM = new Date();
    Calendar calendar = Calendar.getInstance();

    calendar.setTime(nextDay14PM);
    calendar.add(Calendar.DATE, 1);
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
            }
          }
        });

  }

  @Override public void onStop() {
    super.onStop();
    if (subscription != null) {
      subscription.unsubscribe();
    }
  }
}

