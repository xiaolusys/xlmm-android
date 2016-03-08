package com.jimei.xiaolumeimei.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import cn.iwgang.countdownview.CountdownView;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.TodayAdapter;
import com.jimei.xiaolumeimei.base.BaseFragment;
import com.jimei.xiaolumeimei.entities.PostBean;
import com.jimei.xiaolumeimei.entities.ProductListBean;
import com.jimei.xiaolumeimei.model.ProductModel;
import com.jimei.xiaolumeimei.ui.activity.main.MainActivity;
import com.jimei.xiaolumeimei.ui.activity.main.WebViewActivity;
import com.jimei.xiaolumeimei.ui.activity.product.ChildListActivity;
import com.jimei.xiaolumeimei.ui.activity.product.LadyListActivity;
import com.jimei.xiaolumeimei.ui.activity.user.LoginActivity;
import com.jimei.xiaolumeimei.ui.activity.user.WxLoginBindPhoneActivity;
import com.jimei.xiaolumeimei.utils.LoginUtils;
import com.jimei.xiaolumeimei.utils.ViewUtils;
import com.jimei.xiaolumeimei.widget.SpaceItemDecoration;
import com.jimei.xiaolumeimei.widget.banner.Indicators.PagerIndicator;
import com.jimei.xiaolumeimei.widget.banner.SliderLayout;
import com.jimei.xiaolumeimei.widget.banner.SliderTypes.BaseSliderView;
import com.jimei.xiaolumeimei.widget.banner.SliderTypes.DefaultSliderView;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.victor.loading.rotate.RotateLoading;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
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
  //private long l;
  private SharedPreferences sharedPreferences;
  private String cookies;
  private String domain;
  private Subscription subscribe1;
  private Subscription subscribe2;
  private Subscription subscribe3;
  private Subscription subscribe4;
  private Subscription subscription5;

  public static int dp2px(Context context, int dp) {
    float scale = context.getResources().getDisplayMetrics().density;
    return (int) (dp * scale + 0.5f);
  }

  @Override protected int provideContentViewId() {
    return R.layout.today_fragment;
  }

  @Override protected void initData() {
    head.setVisibility(View.INVISIBLE);
    loading.start();
    subscribe1 = ProductModel.getInstance()
        .getTodayList(1, 10)
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<ProductListBean>() {
          @Override public void onError(Throwable e) {
            super.onError(e);
            e.printStackTrace();

            JUtils.Toast("请检查网络状况,尝试下拉刷新");
            loading.stop();
          }

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
            head.setVisibility(View.VISIBLE);
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

    initPost();

    mTodayAdapter = new TodayAdapter(getActivity());
    xRecyclerView.setAdapter(mTodayAdapter);

    xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
      @Override public void onRefresh() {

        initPost();

        subscribe3 = ProductModel.getInstance()
            .getTodayList(1, page * page_size)
            .subscribeOn(Schedulers.io())
            .subscribe(new ServiceResponse<ProductListBean>() {
              @Override public void onNext(ProductListBean productListBean) {
                List<ProductListBean.ResultsEntity> results =
                    productListBean.getResults();
                mTodayAdapter.updateWithClear(results);
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
          Toast.makeText(activity, "没有更多了拉,去购物吧", Toast.LENGTH_SHORT).show();
          xRecyclerView.post(xRecyclerView::loadMoreComplete);
        }
      }
    });
  }

  private void initPost() {
    subscribe2 = ProductModel.getInstance()
        .getTodayPost()
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<PostBean>() {
          @Override public void onNext(PostBean postBean) {
            try {

              String picLink =
                  ViewUtils.getDecodeUrl(postBean.getWemPosters().get(0).getPicLink());
              String picLink1 =
                  ViewUtils.getDecodeUrl(postBean.getmChdPosters().get(0).getPicLink());

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

              if (null != postBean.getActivity()) {

                post2.setVisibility(View.VISIBLE);
                post2.setClickable(true);

                //Glide.with(getActivity())
                //    .load(postBean.getActivity().getActImg())
                //    .diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(
                //    R.drawable.header)
                //    //.centerCrop()
                //    .into(post2);

                OkHttpUtils.get()
                    .url(postBean.getActivity().getActImg())
                    .build()
                    .execute(new BitmapCallback() {
                      @Override public void onError(Call call, Exception e) {

                      }

                      @Override public void onResponse(Bitmap response) {
                        int maxHeight = dp2px(getActivity(), 300);

                        if (response != null) {
                          int height =
                              (int) ((float) view.getWidth() / response.getWidth()
                                  * response.getHeight());
                          if (height > maxHeight) height = maxHeight;
                          LinearLayout.LayoutParams layoutParams =
                              new LinearLayout.LayoutParams(
                                  LinearLayout.LayoutParams.MATCH_PARENT, height);
                          layoutParams.setMargins(0, dp2px(getActivity(), 10
                          ), 0, 0);
                          post2.setLayoutParams(layoutParams);
                          post2.setImageBitmap(response);
                        }
                      }
                    });

                post2.setOnClickListener(new View.OnClickListener() {
                  @Override public void onClick(View v) {

                    //syncCookie(getActivity(), postBean.getActivity().getActLink());

                    if (postBean.getActivity().isLoginRequired()) {
                      if (LoginUtils.checkLoginState(getActivity())
                          && (null
                          != getActivity())
                          && (null
                          != ((MainActivity) getActivity()).getUserInfoBean())
                          && (null
                          != ((MainActivity) getActivity()).getUserInfoBean().getMobile())
                          && !(((MainActivity) getActivity()).getUserInfoBean()
                          .getMobile()
                          .isEmpty())) {
                        Intent intent = new Intent(getActivity(), WebViewActivity.class);
                        //sharedPreferences =
                        //    getActivity().getSharedPreferences("COOKIESxlmm",
                        //        Context.MODE_PRIVATE);
                        //String cookies = sharedPreferences.getString("Cookies", "");
                        //Bundle bundle = new Bundle();
                        //bundle.putString("cookies", cookies);

                        sharedPreferences =
                            getActivity().getSharedPreferences("xlmmCookiesAxiba",
                                Context.MODE_PRIVATE);
                        cookies = sharedPreferences.getString("cookiesString", "");
                        domain = sharedPreferences.getString("cookiesDomain", "");

                        Bundle bundle = new Bundle();
                        bundle.putString("cookies", cookies);
                        bundle.putString("domain", domain);
                        bundle.putString("Cookie",
                            sharedPreferences.getString("Cookie", ""));
                        bundle.putString("actlink", postBean.getActivity().getActLink());
                        intent.putExtras(bundle);
                        startActivity(intent);
                      } else {

                        JUtils.Log(TAG, "entry activity,Need login");
                        if (!LoginUtils.checkLoginState(getActivity())) {
                          JUtils.Toast("登录并绑定手机号后才可参加活动");
                          Intent intent = new Intent(getContext(), LoginActivity.class);
                          Bundle bundle = new Bundle();
                          bundle.putString("login", "main");
                          intent.putExtras(bundle);
                          startActivity(intent);
                        } else {
                          JUtils.Toast("登录成功,前往绑定手机号后才可参加活动");
                          Intent intent =
                              new Intent(getContext(), WxLoginBindPhoneActivity.class);
                          if (null != ((MainActivity) getActivity()).getUserInfoBean()) {
                            Bundle bundle = new Bundle();
                            bundle.putString("headimgurl",
                                ((MainActivity) getActivity()).getUserInfoBean()
                                    .getThumbnail());
                            bundle.putString("nickname",
                                ((MainActivity) getActivity()).getUserInfoBean()
                                    .getNick());
                            intent.putExtras(bundle);
                          }
                          startActivity(intent);
                        }
                      }
                    } else {
                      Intent intent = new Intent(getActivity(), WebViewActivity.class);
                      //sharedPreferences =
                      //    getActivity().getSharedPreferences("COOKIESxlmm",
                      //        Context.MODE_PRIVATE);
                      //cookies = sharedPreferences.getString("Cookies", "");

                      sharedPreferences =
                          getActivity().getSharedPreferences("xlmmCookiesAxiba",
                              Context.MODE_PRIVATE);
                      cookies = sharedPreferences.getString("cookiesString", "");
                      domain = sharedPreferences.getString("cookiesDomain", "");
                      Bundle bundle = new Bundle();
                      bundle.putString("cookies", cookies);
                      bundle.putString("domain", domain);

                      bundle.putString("actlink", postBean.getActivity().getActLink());
                      intent.putExtras(bundle);
                      startActivity(intent);
                    }
                  }
                });
              } else {

                post2.setVisibility(View.GONE);
                post2.setClickable(false);
              }
            } catch (NullPointerException ex) {
              ex.printStackTrace();
            }
          }
        });
  }

  private void loadMoreData(int page, int page_size) {

    subscribe4 = ProductModel.getInstance()
        .getTodayList(page, page_size)
        .subscribeOn(Schedulers.io())
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

    subscription5 = Observable.timer(1, 1, TimeUnit.SECONDS)
        .onBackpressureDrop()
        .map(aLong -> calcLeftTime())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action1<Long>() {
          @Override public void call(Long aLong) {
            if (aLong > 0) {
              countTime.updateShow(aLong);
            } else {
              countTime.setVisibility(View.INVISIBLE);
            }
          }
        }, Throwable::printStackTrace);
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
    if (subscribe4 != null && subscribe4.isUnsubscribed()) {
      subscribe4.unsubscribe();
    }
    if (subscription5 != null && subscription5.isUnsubscribed()) {
      subscription5.unsubscribe();
    }
  }
}

