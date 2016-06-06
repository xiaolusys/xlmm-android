package com.jimei.xiaolumeimei.ui.activity.user;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import butterknife.Bind;
import com.jimei.library.rx.RxCountDown;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.base.CommonWebViewActivity;
import com.jimei.xiaolumeimei.entities.CodeBean;
import com.jimei.xiaolumeimei.entities.NeedSetInfoBean;
import com.jimei.xiaolumeimei.model.UserModel;
import com.jimei.xiaolumeimei.ui.activity.main.ActivityWebViewActivity;
import com.jimei.xiaolumeimei.ui.activity.main.MainActivity;
import com.jimei.xiaolumeimei.ui.activity.product.ProductPopDetailActvityWeb;
import com.jimei.xiaolumeimei.ui.activity.trade.CartActivity;
import com.jimei.xiaolumeimei.utils.JumpUtils;
import com.jimei.xiaolumeimei.utils.LoginUtils;
import com.jimei.xiaolumeimei.widget.ClearEditText;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.xiaomi.mipush.sdk.MiPushClient;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye(www.itxuye.com) on 2016/01/22.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class SmsLoginActivity extends BaseSwipeBackCompatActivity
    implements View.OnClickListener {
  private static final String TAG = SmsLoginActivity.class.getSimpleName();

  @Bind(R.id.register_name) ClearEditText registerName;
  @Bind(R.id.checkcode) EditText checkcode;
  @Bind(R.id.getCheckCode) Button getCheckCode;
  @Bind(R.id.confirm) Button confirm;

  private String mobile, invalid_code;
  private Subscription subscribe;
  private String actlink;
  private String title;
  private int id;

  @Override protected void setListener() {
    getCheckCode.setOnClickListener(this);
    confirm.setOnClickListener(this);
  }

  @Override protected void initData() {

  }

  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.activity_sms;
  }

  @Override protected void initViews() {
  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }

  @Override public void onClick(View v) {
    switch (v.getId()) {
      case R.id.getCheckCode:

        mobile = registerName.getText().toString().trim();
        if (checkMobileInput(mobile)) {
          RxCountDown.countdown(60).doOnSubscribe(new Action0() {
            @Override public void call() {
              getCheckCode.setClickable(false);
              getCheckCode.setBackgroundColor(Color.parseColor("#f3f3f4"));

              subscribe = UserModel.getInstance()
                  .getCodeBean(mobile, "sms_login")
                  .subscribeOn(Schedulers.io())
                  .subscribe(new ServiceResponse<CodeBean>() {
                    @Override public void onNext(CodeBean codeBean) {
                      JUtils.Toast(codeBean.getMsg());
                    }
                  });
            }
          }).unsafeSubscribe(new Subscriber<Integer>() {
            @Override public void onCompleted() {
              getCheckCode.setText("获取验证码");
              getCheckCode.setClickable(true);
              getCheckCode.setBackgroundResource(R.drawable.shape_getcheckcode);
            }

            @Override public void onError(Throwable e) {

            }

            @Override public void onNext(Integer integer) {
              getCheckCode.setText(integer + "s后重新获取");
            }
          });
        }

        break;
      case R.id.confirm:
        mobile = registerName.getText().toString().trim();
        invalid_code = checkcode.getText().toString().trim();
        if (checkInput(mobile, invalid_code)) {
          subscribe = UserModel.getInstance()
              .verify_code(mobile, "sms_login", invalid_code)
              .subscribeOn(Schedulers.io())
              .subscribe(new ServiceResponse<CodeBean>() {
                @Override public void onNext(CodeBean codeBean) {
                  int code = codeBean.getRcode();

                  if (code == 0) {

                    subscribe = UserModel.getInstance()
                        .need_set_info()
                        .subscribeOn(Schedulers.io())
                        .subscribe(new ServiceResponse<NeedSetInfoBean>() {
                          @Override public void onNext(NeedSetInfoBean needSetInfoBean) {
                            super.onNext(needSetInfoBean);
                            int codeInfo = needSetInfoBean.getCode();
                            if (0 == codeInfo) {
                              LoginUtils.saveLoginSuccess(true, getApplicationContext());

                              //set xiaomi push useraccount
                              LoginUtils.setPushUserAccount(SmsLoginActivity.this,
                                  MiPushClient.getRegId(getApplicationContext()));
                              String login = null;
                              if (null != getIntent()
                                  && getIntent().getExtras() != null) {
                                login = getIntent().getExtras().getString("login");
                                actlink = getIntent().getExtras().getString("actlink");
                                title = getIntent().getExtras().getString("title");
                                id = getIntent().getExtras().getInt("id");
                              }

                              if (null != login) {
                                if (login.equals("cart")) {
                                  Intent intent =
                                      new Intent(mContext, CartActivity.class);
                                  startActivity(intent);
                                  finish();
                                } else if (login.equals("product")) {
                                  finish();
                                } else if (login.equals("main")) {
                                  Intent intent =
                                      new Intent(mContext, MainActivity.class);
                                  startActivity(intent);
                                  finish();
                                } else if (login.equals("point")) {
                                  Intent intent =
                                      new Intent(mContext, MembershipPointActivity.class);
                                  startActivity(intent);
                                  finish();
                                } else if (login.equals("money")) {
                                  Intent intent =
                                      new Intent(mContext, WalletActivity.class);
                                  startActivity(intent);
                                  finish();
                                } else if (login.equals("axiba")) {
                                  Intent intent =
                                      new Intent(mContext, MainActivity.class);
                                  startActivity(intent);
                                  finish();
                                } else if (login.equals("coupon")) {
                                  Intent intent =
                                      new Intent(mContext, CouponActivity.class);
                                  startActivity(intent);
                                  finish();
                                } else if (login.equals("productdetail")) {
                                  finish();
                                } else if (login.equals("h5")) {
                                  //Intent intent = new Intent(mContext, CommonWebViewActivity.class);
                                  ////intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                  //SharedPreferences sharedPreferences =
                                  //        getSharedPreferences("xlmmCookiesAxiba", Context.MODE_PRIVATE);
                                  //String cookies = sharedPreferences.getString("Cookie", "");
                                  //Bundle bundle = new Bundle();
                                  //bundle.putString("cookies", cookies);
                                  //bundle.putString("actlink", actlink);
                                  //intent.putExtras(bundle);
                                  //startActivity(intent);
                                  JumpUtils.jumpToWebViewWithCookies(mContext, actlink,
                                      -1, CommonWebViewActivity.class);
                                  finish();
                                } else if (login.equals("prodcutweb")) {

                                  JumpUtils.jumpToWebViewWithCookies(mContext, actlink,
                                      -1, ProductPopDetailActvityWeb.class);
                                  finish();
                                } else if (login.equals("goactivity")) {
                                  JumpUtils.jumpToWebViewWithCookies(mContext, actlink,
                                      id, ActivityWebViewActivity.class,title);
                                  finish();
                                }
                              }
                            } else {
                              JUtils.Toast(needSetInfoBean.getInfo());
                            }
                          }
                        });
                  } else {
                    JUtils.Toast(codeBean.getMsg());
                  }
                }
              });
        }

        break;
    }
  }

  public boolean checkMobileInput(String mobile) {

    if (mobile == null || mobile.trim().trim().equals("")) {
      JUtils.Toast("请输入手机号");
    } else {
      if (mobile.length() != 11) {
        JUtils.Toast("请输入正确的手机号");
      } else {
        return true;
      }
    }

    return false;
  }

  public boolean checkInput(String mobile, String checkcode) {

    if (mobile == null || mobile.trim().trim().equals("")) {
      JUtils.Toast("请输入手机号");
    } else {
      if (mobile.length() != 11) {
        JUtils.Toast("请输入正确的手机号");
      } else if (checkcode == null || checkcode.trim().trim().equals("")) {
        JUtils.Toast("验证码不能为空");
      } else {
        return true;
      }
    }

    return false;
  }

  @Override protected void onStop() {
    super.onStop();
    if (subscribe != null && subscribe.isUnsubscribed()) {
      subscribe.unsubscribe();
    }
  }
}
