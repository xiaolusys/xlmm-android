package com.jimei.xiaolumeimei.ui.fragment.v1.view;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.entities.PostActivityBean;
import com.jimei.xiaolumeimei.model.ActivityModel;
import com.jimei.xiaolumeimei.ui.activity.main.ActivityWebViewActivity;
import com.jimei.xiaolumeimei.ui.activity.main.MainActivity;
import com.jimei.xiaolumeimei.ui.activity.user.LoginActivity;
import com.jimei.xiaolumeimei.ui.activity.user.WxLoginBindPhoneActivity;
import com.jimei.xiaolumeimei.utils.LoginUtils;
import com.jimei.xiaolumeimei.widget.timecircleview.utils.DisplayUtil;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import java.io.IOException;
import java.util.List;
import okhttp3.Call;
import okhttp3.ResponseBody;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye(www.itxuye.com) on 2016/03/30.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class MastFragment extends DialogFragment {

  private static final String SHARE_MASK = "maskActivity";
  SharedPreferences sharedPreferences;
  @Bind(R.id.mask_image) ImageView maskImage;
  @Bind(R.id.clear) ImageView close;
  private Context context;
  private String cookies;
  private String domain;
  private Subscription subscribe2;
  private Subscription subscribe;
  private Activity mActivity;

  public static MastFragment newInstance(String title) {
    MastFragment todayFragment = new MastFragment();
    Bundle bundle = new Bundle();
    bundle.putString("keyword", title);
    todayFragment.setArguments(bundle);
    return todayFragment;
  }

  @Override public void onAttach(Activity activity) {
    super.onAttach(activity);
    mActivity = activity;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    sharedPreferences = getActivity().getSharedPreferences(SHARE_MASK, 0);

    sharedPreferences.getInt("mask", 0);
    int style = DialogFragment.STYLE_NO_TITLE;
    setStyle(style, 0);
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.masklayout, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override public Dialog onCreateDialog(Bundle savedInstanceState) {
    return super.onCreateDialog(savedInstanceState);
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    if (close != null) {
      close.setVisibility(View.INVISIBLE);
    }
    subscribe = ActivityModel.getInstance()
        .getPostActivity()
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<List<PostActivityBean>>() {
          @Override public void onNext(List<PostActivityBean> postActivityBean) {

            if (postActivityBean != null) {
              if (!TextUtils.isEmpty(postActivityBean.get(0).getMaskLink())) {
                try {
                  OkHttpUtils.get()
                      .url(postActivityBean.get(0).getMaskLink())
                      .build()
                      .execute(new BitmapCallback() {
                        @Override public void onError(Call call, Exception e) {
                          e.printStackTrace();
                        }

                        @Override public void onResponse(Bitmap response) {
                          if (null != response) {
                            JUtils.Log("Mask", "height:"
                                + response.getHeight()
                                + "  width:"
                                + response.getWidth());
                            if (close != null) {
                              close.setVisibility(View.VISIBLE);
                            }
                            FrameLayout.LayoutParams layoutParams =
                                new FrameLayout.LayoutParams(
                                    DisplayUtil.dip2px(mActivity, response.getWidth()),
                                    DisplayUtil.dip2px(mActivity, response.getHeight()));
                            maskImage.setLayoutParams(layoutParams);
                            maskImage.setImageBitmap(response);

                            SharedPreferences.Editor edit = sharedPreferences.edit();
                            edit.putInt("mask", postActivityBean.get(0).getId());
                            edit.apply();

                            if (postActivityBean.get(0).getActType().equals("webview")) {
                              maskImage.setOnClickListener(new View.OnClickListener() {
                                @Override public void onClick(View v) {
                                  //syncCookie(getActivity(), postBean.getActivity().getActLink());
                                  if (postActivityBean.get(0).isLoginRequired()) {
                                    if (LoginUtils.checkLoginState(mActivity)
                                        && (null
                                        != mActivity)
                                        && (null
                                        != ((MainActivity) mActivity).getUserInfoBean())
                                        && (null
                                        != ((MainActivity) mActivity).getUserInfoBean()
                                        .getMobile())
                                        && !(((MainActivity) mActivity).getUserInfoBean()
                                        .getMobile()
                                        .isEmpty())) {
                                      dismiss();

                                      Intent intent = new Intent(mActivity,
                                          ActivityWebViewActivity.class);
                                      //sharedPreferences =
                                      //    getActivity().getSharedPreferences("COOKIESxlmm",
                                      //        Context.MODE_PRIVATE);
                                      //String cookies = sharedPreferences.getString("Cookies", "");
                                      //Bundle bundle = new Bundle();
                                      //bundle.putString("cookies", cookies);
                                      sharedPreferences = mActivity.getSharedPreferences(
                                          "xlmmCookiesAxiba", Context.MODE_PRIVATE);
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
                                          postActivityBean.get(0).getActLink());
                                      bundle.putInt("id",
                                          postActivityBean.get(0).getId());
                                      intent.putExtras(bundle);
                                      mActivity.startActivity(intent);
                                    } else {
                                      if (!LoginUtils.checkLoginState(mActivity)) {
                                        JUtils.Toast("登录并绑定手机号后才可参加活动");
                                        Intent intent =
                                            new Intent(mActivity, LoginActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putString("login", "goactivity");
                                        bundle.putString("actlink",
                                            postActivityBean.get(0).getActLink());
                                        bundle.putInt("id",
                                            postActivityBean.get(0).getId());
                                        intent.putExtras(bundle);
                                        intent.putExtras(bundle);
                                        intent.putExtras(bundle);
                                        mActivity.startActivity(intent);
                                      } else {
                                        JUtils.Toast("登录成功,前往绑定手机号后才可参加活动");
                                        Intent intent = new Intent(mActivity,
                                            WxLoginBindPhoneActivity.class);
                                        if (null
                                            != ((MainActivity) mActivity).getUserInfoBean()) {
                                          Bundle bundle = new Bundle();
                                          bundle.putString("headimgurl",
                                              ((MainActivity) mActivity).getUserInfoBean()
                                                  .getThumbnail());
                                          bundle.putString("nickname",
                                              ((MainActivity) mActivity).getUserInfoBean()
                                                  .getNick());
                                          intent.putExtras(bundle);
                                        }
                                        mActivity.startActivity(intent);
                                      }
                                    }
                                  } else {
                                    Intent intent = new Intent(mActivity,
                                        ActivityWebViewActivity.class);
                                    //sharedPreferences =
                                    //    getActivity().getSharedPreferences("COOKIESxlmm",
                                    //        Context.MODE_PRIVATE);
                                    //cookies = sharedPreferences.getString("Cookies", "");
                                    sharedPreferences =
                                        mActivity.getSharedPreferences("xlmmCookiesAxiba",
                                            Context.MODE_PRIVATE);
                                    cookies =
                                        sharedPreferences.getString("cookiesString", "");
                                    domain =
                                        sharedPreferences.getString("cookiesDomain", "");
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("id", postActivityBean.get(0).getId());
                                    bundle.putString("cookies", cookies);
                                    bundle.putString("domain", domain);
                                    bundle.putString("actlink",
                                        postActivityBean.get(0).getActLink());
                                    intent.putExtras(bundle);
                                    mActivity.startActivity(intent);
                                  }
                                }
                              });
                            } else if (postActivityBean.get(0)
                                .getActType()
                                .equals("coupon")) {
                              maskImage.setOnClickListener(new View.OnClickListener() {
                                @Override public void onClick(View v) {
                                  subscribe2 = ActivityModel.getInstance()
                                      .getUsercoupons(postActivityBean.get(0)
                                          .getExtras()
                                          .getTemplateId())
                                      .subscribeOn(Schedulers.io())
                                      .subscribe(new ServiceResponse<ResponseBody>() {
                                        @Override
                                        public void onNext(ResponseBody responseBody) {
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
                } catch (NullPointerException e) {
                  e.printStackTrace();
                }
              }
            }
          }
        });

    close.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        dismiss();
      }
    });

    //maskImage.setOnClickListener(new View.OnClickListener() {
    //  @Override public void onClick(View v) {
    //
    //  }
    //});
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    ButterKnife.unbind(this);
  }

  @Override public void onStop() {
    super.onStop();

    if (subscribe != null && subscribe.isUnsubscribed()) {
      subscribe.unsubscribe();
    }
    if (subscribe2 != null && subscribe2.isUnsubscribed()) {
      subscribe2.unsubscribe();
    }
  }
}
