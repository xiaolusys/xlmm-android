package com.jimei.xiaolumeimei.ui.activity.user;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.RegisterBean;
import com.jimei.xiaolumeimei.model.UserModel;
import com.jimei.xiaolumeimei.utils.LoginUtils;
import com.jimei.xiaolumeimei.widget.ClearEditText;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye(www.itxuye.com) on 2016/01/22.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class SmsLoginActivity extends BaseSwipeBackCompatActivity
    implements View.OnClickListener {

  UserModel model = new UserModel();
  @Bind(R.id.tx_title) TextView txTitle;
  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.register_name) ClearEditText registerName;
  @Bind(R.id.checkcode) EditText checkcode;
  @Bind(R.id.getCheckCode) Button getCheckCode;
  @Bind(R.id.confirm) Button confirm;

  private String mobile, invalid_code;

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
    toolbar.setNavigationIcon(R.drawable.back);
  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // TODO: add setContentView(...) invocation
    ButterKnife.bind(this);
  }

  @Override public void onClick(View v) {
    switch (v.getId()) {
      case R.id.getCheckCode:
        mobile = registerName.getText().toString().trim();
        checkInput();
        model.getSmsCheckCode(mobile)
            .subscribeOn(Schedulers.io())
            .subscribe(new ServiceResponse<RegisterBean>() {
              @Override public void onNext(RegisterBean registerBean) {
                super.onNext(registerBean);
                String result = registerBean.getResult();
                if (result.equals("0")) {
                  JUtils.Toast("获取成功");
                } else if (result.equals("2")) {
                  JUtils.Toast("手机号码不合法");
                  return;
                }
              }
            });
        break;
      case R.id.confirm:
        mobile = registerName.getText().toString().trim();
        invalid_code = checkcode.getText().toString().trim();
        model.smsLogin(mobile, invalid_code)
            .subscribeOn(Schedulers.io())
            .subscribe(new ServiceResponse<RegisterBean>() {
              @Override public void onNext(RegisterBean registerBean) {
                super.onNext(registerBean);
                String result = registerBean.getResult();
                if (result.equals("0")) {
                  LoginUtils.saveLoginSuccess(true, getApplicationContext());
                  JUtils.Toast("登录成功");
                  finish();
                } else if (result.equals("1")) {
                  LoginUtils.saveLoginSuccess(false, getApplicationContext());
                  JUtils.Toast("登录验证失败");
                  return;
                } else if (result.equals("3")) {
                  LoginUtils.saveLoginSuccess(false, getApplicationContext());
                  JUtils.Toast("验证码有误;");
                  return;
                }
              }
            });
        break;

      case R.id.toolbar:
        finish();

        break;
    }
  }

  public void checkInput() {
    if (mobile.length() != 11) {
      JUtils.Toast("请输入正确的手机号");
      return;
    }
  }
}
