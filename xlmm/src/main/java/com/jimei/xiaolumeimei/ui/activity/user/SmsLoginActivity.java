package com.jimei.xiaolumeimei.ui.activity.user;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Bind;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.CodeBean;
import com.jimei.xiaolumeimei.entities.NeedSetInfoBean;
import com.jimei.xiaolumeimei.entities.SmsLoginBean;
import com.jimei.xiaolumeimei.entities.SmsLoginUserBean;
import com.jimei.xiaolumeimei.model.UserModel;
import com.jimei.library.rx.RxCountDown;
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

  @Bind(R.id.tx_title) TextView txTitle;
  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.register_name) ClearEditText registerName;
  @Bind(R.id.checkcode) EditText checkcode;
  @Bind(R.id.getCheckCode) Button getCheckCode;
  @Bind(R.id.confirm) Button confirm;

  private String mobile, invalid_code;
  private Subscription subscribe;

  @Override protected void setListener() {
    getCheckCode.setOnClickListener(this);
    confirm.setOnClickListener(this);
    toolbar.setOnClickListener(this);
  }

  @Override protected void initData() {

  }

  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.activity_sms;
  }

  @Override protected void initViews() {
    toolbar.setTitle("");
    setSupportActionBar(toolbar);
    finishBack(toolbar);
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
                  .getCodeBean(mobile,"sms_login")
                  .subscribeOn(Schedulers.io())
                  .subscribe(new ServiceResponse<CodeBean>(){
                    @Override
                    public void onNext(CodeBean codeBean) {
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
              .verify_code(mobile, "sms_login",invalid_code)
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
                              JUtils.Toast("登录成功");

                              //set xiaomi push useraccount
                              LoginUtils.setPushUserAccount(SmsLoginActivity.this,
                                  MiPushClient.getRegId(getApplicationContext()));
                              finish();
                            } else {
                              JUtils.Toast(needSetInfoBean.getInfo());
                            }
                          }
                        });
                  } else{
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
