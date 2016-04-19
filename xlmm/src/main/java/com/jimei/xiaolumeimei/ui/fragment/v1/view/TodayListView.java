package com.jimei.xiaolumeimei.ui.fragment.v1.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.TodayAdapter;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.entities.PostActivityBean;
import com.jimei.xiaolumeimei.entities.PostBean;
import com.jimei.xiaolumeimei.entities.ProductListBean;
import com.jimei.xiaolumeimei.model.ActivityModel;
import com.jimei.xiaolumeimei.model.ProductModel;
import com.jimei.xiaolumeimei.ui.activity.main.ActivityWebViewActivity;
import com.jimei.xiaolumeimei.ui.activity.main.MainActivity;
import com.jimei.xiaolumeimei.ui.activity.user.LoginActivity;
import com.jimei.xiaolumeimei.ui.activity.user.WxLoginBindPhoneActivity;
import com.jimei.xiaolumeimei.ui.fragment.view.ViewImpl;
import com.jimei.xiaolumeimei.utils.JumpUtils;
import com.jimei.xiaolumeimei.utils.LoginUtils;
import com.jimei.xiaolumeimei.widget.SpaceItemDecoration;
import com.jimei.xiaolumeimei.widget.banner.Indicators.PagerIndicator;
import com.jimei.xiaolumeimei.widget.banner.SliderLayout;
import com.jimei.xiaolumeimei.widget.banner.SliderTypes.BaseSliderView;
import com.jimei.xiaolumeimei.widget.banner.SliderTypes.DefaultSliderView;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.squareup.okhttp.ResponseBody;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.iwgang.countdownview.CountdownView;
import okhttp3.Call;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye(www.itxuye.com) on 2016/03/10.
 * <p>
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class TodayListView extends ViewImpl {

  private static final String POST_URL = "?imageMogr2/format/jpg/quality/80";
  @Bind(R.id.xrecyclerView) XRecyclerView xRecyclerView;
  List<String> postString = new ArrayList<>();
  List<String> appString = new ArrayList<>();
  List<PostBean.WemPostersEntity> wemPosters = new ArrayList<>();
  List<PostBean.WemPostersEntity> wemPostersEntities = new ArrayList<>();
  Map<String, String> map = new HashMap<>();
  private View head;
  private TodayAdapter mTodayAdapter;
  private ImageView post2;
  private LinearLayout post_activity_layout;
  private int page = 2;
  private int page_size = 10;
  private int totalPages;//总的分页数0
  private CountdownView countTime;
  private SliderLayout mSliderLayout;
  private PagerIndicator mPagerIndicator;
  private Subscription subscribe1;
  private Subscription subscribe3;
  private Subscription subscribe2;
  private String cookies;
  private String domain;
  private SharedPreferences sharedPreferencesMask;
  List<ImageView> imageViewList = new ArrayList<>();
  private Subscription subscribe4;
  private Subscription subscription5;
  private Subscription subscribe6;
  private Subscription subscribe7;
  private MaterialDialog materialDialog;
  private SharedPreferences sharedPreferences;
  private int mask;

  public static int dp2px(Context context, int dp) {
    float scale = context.getResources().getDisplayMetrics().density;
    return (int) (dp * scale + 0.5f);
  }

  @Override public int getLayoutId() {
    return R.layout.today_fragment;
  }

  public void initViews(Fragment fragment, Activity context) {
    sharedPreferencesMask = context.getSharedPreferences("maskActivity", 0);
    mask = sharedPreferencesMask.getInt("mask", 0);
    head = LayoutInflater.from(context)
        .inflate(R.layout.today_poster_header,
            (ViewGroup) mRootView.findViewById(R.id.head_today), false);
    xRecyclerView.setLayoutManager(new GridLayoutManager(context, 2));
    xRecyclerView.addItemDecoration(new SpaceItemDecoration(10));
    xRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
    xRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.SemiCircleSpin);
    xRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
    //post2 = (ImageView) head.findViewById(R.id.post_2);
    post_activity_layout = (LinearLayout) head.findViewById(R.id.post_activity);
    countTime = (CountdownView) head.findViewById(R.id.countTime);
    mSliderLayout = (SliderLayout) head.findViewById(R.id.slider);
    mPagerIndicator = (PagerIndicator) head.findViewById(R.id.pi_header);
    xRecyclerView.addHeaderView(head);
    mTodayAdapter = new TodayAdapter(fragment, context);
    xRecyclerView.setAdapter(mTodayAdapter);
    head.setVisibility(View.INVISIBLE);
    //loading.start();
    showIndeterminateProgressDialog(false, context);
    initData(context);
    xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
      @Override public void onRefresh() {
        initPostRefresh(context);
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
          Toast.makeText(context, "没有更多了拉,去购物吧", Toast.LENGTH_SHORT).show();
          xRecyclerView.post(xRecyclerView::loadMoreComplete);
        }
      }
    });
  }

  private void initData(Activity context) {

    initPost(context);
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
    subscribe1 = ProductModel.getInstance()
        .getTodayList(1, 10)
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<ProductListBean>() {
          @Override public void onError(Throwable e) {
            super.onError(e);
            e.printStackTrace();
            JUtils.Toast("请检查网络状况,尝试下拉刷新");
            //loading.stop();
            hideIndeterminateProgressDialog();
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
            //loading.post(loading::stop);
            hideIndeterminateProgressDialog();
            head.setVisibility(View.VISIBLE);
          }
        });
  }

  private void initPost(Activity context) {
    subscribe2 = ProductModel.getInstance()
        .getTodayPost()
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<PostBean>() {
          @Override public void onNext(PostBean postBean) {

            wemPosters.clear();
            wemPostersEntities.clear();
            postString.clear();
            appString.clear();

            try {

              wemPosters.addAll(postBean.getWemPosters());
              wemPostersEntities.addAll(postBean.getmChdPosters());
              for (PostBean.WemPostersEntity str : wemPosters) {
                postString.add(str.getPicLink() + POST_URL);
                appString.add(str.getAppLink());
              }
              for (PostBean.WemPostersEntity str : wemPostersEntities) {
                postString.add(str.getPicLink() + POST_URL);
                appString.add(str.getAppLink());
              }

              for (int i = 0; i < postString.size(); i++) {
                map.put(postString.get(i), appString.get(i));
              }

              if (mSliderLayout != null) {
                mSliderLayout.removeAllSliders();
              }

              for (String name : map.keySet()) {
                DefaultSliderView textSliderView = new DefaultSliderView(context);
                // initialize a SliderLayout
                textSliderView.image(name)
                    .setScaleType(BaseSliderView.ScaleType.CenterInside);
                //add your extra information
                textSliderView.bundle(new Bundle());
                textSliderView.getBundle().putString("extra", map.get(name));
                mSliderLayout.addSlider(textSliderView);
                mSliderLayout.setDuration(3000);
                mSliderLayout.setCustomIndicator(mPagerIndicator);
                textSliderView.setOnSliderClickListener(
                    new BaseSliderView.OnSliderClickListener() {
                      @Override public void onSliderClick(BaseSliderView slider) {
                        Intent intent;
                        if (slider.getBundle() != null) {
                          String extra = slider.getBundle().getString("extra");
                          if (!TextUtils.isEmpty(extra)) {
                            JumpUtils.JumpInfo jump_info = JumpUtils.get_jump_info(extra);
                            if (extra.startsWith("http://")) {
                              intent = new Intent(context, ActivityWebViewActivity.class);
                              //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                              SharedPreferences sharedPreferences =
                                  context.getSharedPreferences("COOKIESxlmm",
                                      Context.MODE_PRIVATE);
                              String cookies = sharedPreferences.getString("Cookies", "");
                              Bundle bundle = new Bundle();
                              bundle.putString("cookies", cookies);
                              bundle.putString("actlink", extra);
                              intent.putExtras(bundle);
                              context.startActivity(intent);
                            } else {
                              if (jump_info.getType() == XlmmConst.JUMP_PROMOTE_TODAY) {
                                intent = new Intent(context, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("fragment", 1);
                                context.startActivity(intent);
                                context.finish();
                              } else if (jump_info.getType()
                                  == XlmmConst.JUMP_PROMOTE_PREVIOUS) {
                                intent = new Intent(context, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("fragment", 2);
                                context.startActivity(intent);
                                context.finish();
                              } else if (jump_info.getType()
                                  == XlmmConst.JUMP_PRODUCT_CHILDLIST) {
                                intent = new Intent(context, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("fragment", 3);
                                context.startActivity(intent);
                                context.finish();
                              } else if (jump_info.getType()
                                  == XlmmConst.JUMP_PRODUCT_LADYLIST) {
                                intent = new Intent(context, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("fragment", 4);
                                context.startActivity(intent);
                                context.finish();
                              } else {
                                JumpUtils.push_jump_proc(context, extra);
                              }
                            }
                          }
                        }
                      }
                    });
              }
            } catch (NullPointerException ex) {
              ex.printStackTrace();
            }
          }
        });
    subscribe6 = ActivityModel.getInstance()
        .getPostActivity()
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<List<PostActivityBean>>() {
          @Override public void onNext(List<PostActivityBean> postActivityBean) {
            try {
              if (null != postActivityBean) {
                post_activity_layout.setVisibility(View.VISIBLE);
                ImageView imageView;

                for (int i = 0; i < postActivityBean.size(); i++) {
                  imageView = new ImageView(context);
                  imageViewList.add(imageView);
                  post_activity_layout.addView(imageView);
                }
                for (int i = 0; i < postActivityBean.size(); i++) {
                  final int finalI = i;
                  OkHttpUtils.get()
                      .url(postActivityBean.get(i).getActImg())
                      .build()
                      .execute(new BitmapCallback() {
                        @Override public void onError(Call call, Exception e) {
                        }

                        @Override public void onResponse(Bitmap response) {
                          int maxHeight = dp2px(context, 300);
                          if (response != null) {
                            int height =
                                (int) ((float) mRootView.getWidth() / response.getWidth()
                                    * response.getHeight());
                            if (height > maxHeight) height = maxHeight;
                            LinearLayout.LayoutParams layoutParams =
                                new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT, height);
                            layoutParams.setMargins(0, dp2px(context, 10), 0, 0);
                            imageViewList.get(finalI).setLayoutParams(layoutParams);
                            imageViewList.get(finalI).setImageBitmap(response);
                            if (postActivityBean.get(finalI)
                                .getActType()
                                .equals("webview")) {
                              imageViewList.get(finalI)
                                  .setOnClickListener(new View.OnClickListener() {
                                    @Override public void onClick(View v) {
                                      //syncCookie(getActivity(), postBean.getActivity().getActLink());
                                      if (postActivityBean.get(finalI)
                                          .isLoginRequired()) {
                                        if (LoginUtils.checkLoginState(context)
                                            && (null
                                            != context)
                                            && (null
                                            != ((MainActivity) context).getUserInfoBean())
                                            && (null
                                            != ((MainActivity) context).getUserInfoBean()
                                            .getMobile())
                                            && !(((MainActivity) context).getUserInfoBean()
                                            .getMobile()
                                            .isEmpty())) {
                                          Intent intent =
                                              new Intent(context, ActivityWebViewActivity.class);
                                          //sharedPreferences =
                                          //    getActivity().getSharedPreferences("COOKIESxlmm",
                                          //        Context.MODE_PRIVATE);
                                          //String cookies = sharedPreferences.getString("Cookies", "");
                                          //Bundle bundle = new Bundle();
                                          //bundle.putString("cookies", cookies);
                                          sharedPreferences =
                                              context.getSharedPreferences(
                                                  "xlmmCookiesAxiba",
                                                  Context.MODE_PRIVATE);
                                          cookies =
                                              sharedPreferences.getString("cookiesString",
                                                  "");
                                          domain =
                                              sharedPreferences.getString("cookiesDomain",
                                                  "");
                                          Bundle bundle = new Bundle();
                                          bundle.putString("cookies", cookies);
                                          bundle.putString("domain", domain);
                                          bundle.putString("Cookie",
                                              sharedPreferences.getString("Cookie", ""));
                                          bundle.putString("actlink",
                                              postActivityBean.get(finalI).getActLink());
                                          bundle.putInt("id",
                                              postActivityBean.get(finalI).getId());
                                          intent.putExtras(bundle);
                                          context.startActivity(intent);
                                        } else {
                                          if (!LoginUtils.checkLoginState(context)) {
                                            JUtils.Toast("登录并绑定手机号后才可参加活动");
                                            Intent intent =
                                                new Intent(context, LoginActivity.class);
                                            Bundle bundle = new Bundle();
                                            bundle.putString("login", "main");
                                            intent.putExtras(bundle);
                                            context.startActivity(intent);
                                          } else {
                                            JUtils.Toast("登录成功,前往绑定手机号后才可参加活动");
                                            Intent intent = new Intent(context,
                                                WxLoginBindPhoneActivity.class);
                                            if (null
                                                != ((MainActivity) context).getUserInfoBean()) {
                                              Bundle bundle = new Bundle();
                                              bundle.putString("headimgurl",
                                                  ((MainActivity) context).getUserInfoBean()
                                                      .getThumbnail());
                                              bundle.putString("nickname",
                                                  ((MainActivity) context).getUserInfoBean()
                                                      .getNick());
                                              intent.putExtras(bundle);
                                            }
                                            context.startActivity(intent);
                                          }
                                        }
                                      } else {
                                        Intent intent =
                                            new Intent(context, ActivityWebViewActivity.class);
                                        //sharedPreferences =
                                        //    getActivity().getSharedPreferences("COOKIESxlmm",
                                        //        Context.MODE_PRIVATE);
                                        //cookies = sharedPreferences.getString("Cookies", "");
                                        sharedPreferences = context.getSharedPreferences(
                                            "xlmmCookiesAxiba", Context.MODE_PRIVATE);
                                        cookies =
                                            sharedPreferences.getString("cookiesString",
                                                "");
                                        domain =
                                            sharedPreferences.getString("cookiesDomain",
                                                "");
                                        Bundle bundle = new Bundle();
                                        bundle.putInt("id",
                                            postActivityBean.get(finalI).getId());
                                        bundle.putString("cookies", cookies);
                                        bundle.putString("domain", domain);
                                        bundle.putString("actlink",
                                            postActivityBean.get(finalI).getActLink());
                                        intent.putExtras(bundle);
                                        context.startActivity(intent);
                                      }
                                    }
                                  });
                            } else if (postActivityBean.get(finalI)
                                .getActType()
                                .equals("coupon")) {
                              imageViewList.get(finalI)
                                  .setOnClickListener(new View.OnClickListener() {
                                    @Override public void onClick(View v) {
                                      subscribe7 = ActivityModel.getInstance()
                                          .getUsercoupons(postActivityBean.get(finalI)
                                              .getExtras()
                                              .getTemplateId())
                                          .subscribeOn(Schedulers.io())
                                          .subscribe(new ServiceResponse<ResponseBody>() {
                                            @Override public void onNext(
                                                ResponseBody responseBody) {
                                              if (null != responseBody) {
                                                try {
                                                  JUtils.Log("TodayListView",
                                                      responseBody.string());
                                                } catch (IOException e) {
                                                  e.printStackTrace();
                                                }
                                              }
                                            }
                                          });
                                    }
                                  });
                            }
                          }
                        }
                      });
                }

                if (mask != postActivityBean.get(0).getId() && !TextUtils.isEmpty(
                    postActivityBean.get(0).getMaskLink())) {

                  MastFragment test = MastFragment.newInstance("mask");
                  test.show(context.getFragmentManager(), "mask");
                }
              } else {
                post_activity_layout.setVisibility(View.INVISIBLE);
              }
            } catch (NullPointerException e) {
              e.printStackTrace();
            }
          }
        });
  }

  private void initPostRefresh(Activity context) {
    subscribe2 = ProductModel.getInstance()
        .getTodayPost()
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<PostBean>() {
          @Override public void onNext(PostBean postBean) {

            wemPosters.clear();
            wemPostersEntities.clear();
            postString.clear();
            appString.clear();

            try {

              wemPosters.addAll(postBean.getWemPosters());
              wemPostersEntities.addAll(postBean.getmChdPosters());
              for (PostBean.WemPostersEntity str : wemPosters) {
                postString.add(str.getPicLink() + POST_URL);
                appString.add(str.getAppLink());
              }
              for (PostBean.WemPostersEntity str : wemPostersEntities) {
                postString.add(str.getPicLink() + POST_URL);
                appString.add(str.getAppLink());
              }

              for (int i = 0; i < postString.size(); i++) {
                map.put(postString.get(i), appString.get(i));
              }

              if (mSliderLayout != null) {
                mSliderLayout.removeAllSliders();
              }

              for (String name : map.keySet()) {
                DefaultSliderView textSliderView = new DefaultSliderView(context);
                // initialize a SliderLayout
                textSliderView.image(name)
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop);
                //add your extra information
                textSliderView.bundle(new Bundle());
                textSliderView.getBundle().putString("extra", map.get(name));
                mSliderLayout.addSlider(textSliderView);
                mSliderLayout.setDuration(3000);
                mSliderLayout.setCustomIndicator(mPagerIndicator);
                textSliderView.setOnSliderClickListener(
                    new BaseSliderView.OnSliderClickListener() {
                      @Override public void onSliderClick(BaseSliderView slider) {
                        Intent intent;
                        if (slider.getBundle() != null) {
                          String extra = slider.getBundle().getString("extra");
                          if (!TextUtils.isEmpty(extra)) {
                            JumpUtils.JumpInfo jump_info = JumpUtils.get_jump_info(extra);
                            if (extra.startsWith("http://")) {
                              intent = new Intent(context, ActivityWebViewActivity.class);
                              //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                              SharedPreferences sharedPreferences =
                                  context.getSharedPreferences("COOKIESxlmm",
                                      Context.MODE_PRIVATE);
                              String cookies = sharedPreferences.getString("Cookies", "");
                              Bundle bundle = new Bundle();
                              bundle.putString("cookies", cookies);
                              bundle.putString("actlink", extra);
                              intent.putExtras(bundle);
                              context.startActivity(intent);
                            } else {
                              if (jump_info.getType() == XlmmConst.JUMP_PROMOTE_TODAY) {
                                intent = new Intent(context, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("fragment", 1);
                                context.startActivity(intent);
                                context.finish();
                              } else if (jump_info.getType()
                                  == XlmmConst.JUMP_PROMOTE_PREVIOUS) {
                                intent = new Intent(context, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("fragment", 2);
                                context.startActivity(intent);
                                context.finish();
                              } else if (jump_info.getType()
                                  == XlmmConst.JUMP_PRODUCT_CHILDLIST) {
                                intent = new Intent(context, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("fragment", 3);
                                context.startActivity(intent);
                                context.finish();
                              } else if (jump_info.getType()
                                  == XlmmConst.JUMP_PRODUCT_LADYLIST) {
                                intent = new Intent(context, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("fragment", 4);
                                context.startActivity(intent);
                                context.finish();
                              } else {
                                JumpUtils.push_jump_proc(context, extra);
                              }
                            }
                          }
                        }
                      }
                    });
              }
            } catch (NullPointerException ex) {
              ex.printStackTrace();
            }
          }
        });

    for (int i = 0; i < imageViewList.size(); i++) {
      imageViewList.get(i).setVisibility(View.GONE);
    }
    imageViewList.clear();
    subscribe6 = ActivityModel.getInstance()
        .getPostActivity()
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<List<PostActivityBean>>() {
          @Override public void onNext(List<PostActivityBean> postActivityBean) {
            try {
              if (null != postActivityBean) {
                post_activity_layout.setVisibility(View.VISIBLE);
                ImageView imageView;
                for (int i = 0; i < postActivityBean.size(); i++) {
                  imageView = new ImageView(context);
                  imageViewList.add(imageView);
                  post_activity_layout.addView(imageView);
                }
                for (int i = 0; i < postActivityBean.size(); i++) {
                  final int finalI = i;
                  OkHttpUtils.get()
                      .url(postActivityBean.get(i).getActImg())
                      .build()
                      .execute(new BitmapCallback() {
                        @Override public void onError(Call call, Exception e) {
                        }

                        @Override public void onResponse(Bitmap response) {
                          int maxHeight = dp2px(context, 300);
                          if (response != null) {
                            int height =
                                (int) ((float) mRootView.getWidth() / response.getWidth()
                                    * response.getHeight());
                            if (height > maxHeight) height = maxHeight;
                            LinearLayout.LayoutParams layoutParams =
                                new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT, height);
                            layoutParams.setMargins(0, dp2px(context, 10), 0, 0);
                            imageViewList.get(finalI).setLayoutParams(layoutParams);
                            imageViewList.get(finalI).setImageBitmap(response);
                            if (postActivityBean.get(finalI)
                                .getActType()
                                .equals("webview")) {
                              imageViewList.get(finalI)
                                  .setOnClickListener(new View.OnClickListener() {
                                    @Override public void onClick(View v) {
                                      //syncCookie(getActivity(), postBean.getActivity().getActLink());
                                      if (postActivityBean.get(finalI)
                                          .isLoginRequired()) {
                                        if (LoginUtils.checkLoginState(context)
                                            && (null
                                            != context)
                                            && (null
                                            != ((MainActivity) context).getUserInfoBean())
                                            && (null
                                            != ((MainActivity) context).getUserInfoBean()
                                            .getMobile())
                                            && !(((MainActivity) context).getUserInfoBean()
                                            .getMobile()
                                            .isEmpty())) {
                                          Intent intent =
                                              new Intent(context, ActivityWebViewActivity.class);
                                          //sharedPreferences =
                                          //    getActivity().getSharedPreferences("COOKIESxlmm",
                                          //        Context.MODE_PRIVATE);
                                          //String cookies = sharedPreferences.getString("Cookies", "");
                                          //Bundle bundle = new Bundle();
                                          //bundle.putString("cookies", cookies);
                                          sharedPreferences =
                                              context.getSharedPreferences(
                                                  "xlmmCookiesAxiba",
                                                  Context.MODE_PRIVATE);
                                          cookies =
                                              sharedPreferences.getString("cookiesString",
                                                  "");
                                          domain =
                                              sharedPreferences.getString("cookiesDomain",
                                                  "");
                                          Bundle bundle = new Bundle();
                                          bundle.putString("cookies", cookies);
                                          bundle.putString("domain", domain);
                                          bundle.putString("Cookie",
                                              sharedPreferences.getString("Cookie", ""));
                                          bundle.putString("actlink",
                                              postActivityBean.get(finalI).getActLink());
                                          bundle.putInt("id",
                                              postActivityBean.get(finalI).getId());
                                          intent.putExtras(bundle);
                                          context.startActivity(intent);
                                        } else {
                                          if (!LoginUtils.checkLoginState(context)) {
                                            JUtils.Toast("登录并绑定手机号后才可参加活动");
                                            Intent intent =
                                                new Intent(context, LoginActivity.class);
                                            Bundle bundle = new Bundle();
                                            bundle.putString("login", "main");
                                            intent.putExtras(bundle);
                                            if (context != null) {
                                              context.startActivity(intent);
                                            }
                                          } else {
                                            JUtils.Toast("登录成功,前往绑定手机号后才可参加活动");
                                            Intent intent = new Intent(context,
                                                WxLoginBindPhoneActivity.class);
                                            if (((MainActivity) context) != null && null
                                                    != ((MainActivity) context).getUserInfoBean()) {
                                              Bundle bundle = new Bundle();
                                              bundle.putString("headimgurl",
                                                      ((MainActivity) context).getUserInfoBean()
                                                              .getThumbnail());
                                              bundle.putString("nickname",
                                                      ((MainActivity) context).getUserInfoBean()
                                                              .getNick());
                                              intent.putExtras(bundle);
                                            }
                                            if (context != null) {
                                              context.startActivity(intent);
                                            }
                                          }
                                        }
                                      } else {
                                        Intent intent =
                                            new Intent(context, ActivityWebViewActivity.class);
                                        //sharedPreferences =
                                        //    getActivity().getSharedPreferences("COOKIESxlmm",
                                        //        Context.MODE_PRIVATE);
                                        //cookies = sharedPreferences.getString("Cookies", "");
                                        sharedPreferences = context.getSharedPreferences(
                                            "xlmmCookiesAxiba", Context.MODE_PRIVATE);
                                        cookies =
                                            sharedPreferences.getString("cookiesString",
                                                "");
                                        domain =
                                            sharedPreferences.getString("cookiesDomain",
                                                "");
                                        Bundle bundle = new Bundle();
                                        bundle.putInt("id",
                                            postActivityBean.get(finalI).getId());
                                        bundle.putString("cookies", cookies);
                                        bundle.putString("domain", domain);
                                        bundle.putString("actlink",
                                            postActivityBean.get(finalI).getActLink());
                                        intent.putExtras(bundle);
                                        context.startActivity(intent);
                                      }
                                    }
                                  });
                            } else if (postActivityBean.get(finalI)
                                .getActType()
                                .equals("coupon")) {
                              imageViewList.get(finalI)
                                  .setOnClickListener(new View.OnClickListener() {
                                    @Override public void onClick(View v) {
                                      subscribe7 = ActivityModel.getInstance()
                                          .getUsercoupons(postActivityBean.get(finalI)
                                              .getExtras()
                                              .getTemplateId())
                                          .subscribeOn(Schedulers.io())
                                          .subscribe(new ServiceResponse<ResponseBody>() {
                                            @Override public void onNext(
                                                ResponseBody responseBody) {
                                              if (null != responseBody) {
                                                try {
                                                  JUtils.Log("TodayListView",
                                                      responseBody.string());
                                                } catch (IOException e) {
                                                  e.printStackTrace();
                                                }
                                              }
                                            }
                                          });
                                    }
                                  });
                            }
                          }
                        }
                      });
                }
              } else {
                post_activity_layout.setVisibility(View.INVISIBLE);
              }
            } catch (NullPointerException e) {
              e.printStackTrace();
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
    if (subscribe4 != null && subscribe4.isUnsubscribed()) {
      subscribe4.unsubscribe();
    }
    if (subscription5 != null && subscription5.isUnsubscribed()) {
      subscription5.unsubscribe();
    }
    if (subscribe6 != null && subscribe6.isUnsubscribed()) {
      subscribe6.unsubscribe();
    }
    if (subscribe7 != null && subscribe7.isUnsubscribed()) {
      subscribe7.unsubscribe();
    }
  }

  public void showIndeterminateProgressDialog(boolean horizontal, Context context) {
    materialDialog = new MaterialDialog.Builder(context)
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
}
