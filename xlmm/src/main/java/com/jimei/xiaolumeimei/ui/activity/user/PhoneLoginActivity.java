package com.jimei.xiaolumeimei.ui.activity.user;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import butterknife.Bind;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.base.CommonWebViewActivity;
import com.jimei.xiaolumeimei.entities.CodeBean;
import com.jimei.xiaolumeimei.model.UserModel;
import com.jimei.xiaolumeimei.ui.activity.main.MainActivity;
import com.jimei.xiaolumeimei.ui.activity.product.ProductPopDetailActvityWeb;
import com.jimei.xiaolumeimei.ui.activity.trade.CartActivity;
import com.jimei.xiaolumeimei.utils.JumpUtils;
import com.jimei.xiaolumeimei.utils.LoginUtils;
import com.jimei.xiaolumeimei.widget.ClearEditText;
import com.jimei.xiaolumeimei.widget.PasswordEditText;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.xiaomi.mipush.sdk.MiPushClient;
import rx.Subscription;
import rx.schedulers.Schedulers;

public class PhoneLoginActivity extends BaseSwipeBackCompatActivity
    implements View.OnClickListener, TextWatcher {

  private static final String TAG = PhoneLoginActivity.class.getSimpleName();
  @Bind(R.id.set_login_name) ClearEditText nameEditText;
  @Bind(R.id.set_login_password) PasswordEditText passEditText;
  @Bind(R.id.set_login_button) Button login_button;
  @Bind(R.id.forgetTextView) TextView forGetTextView;
  @Bind(R.id.cb_pwd) CheckBox cbPwd;
  String login_name_value;//登录名
  String login_pass_value;//登录密码
  private String actlink;
  private int id;

  @Override protected void setListener() {
    login_button.setOnClickListener(this);
    login_button.setOnClickListener(this);
    forGetTextView.setOnClickListener(this);
    nameEditText.addTextChangedListener(this);
  }

  @Override protected void initData() {
    if (!LoginUtils.checkLoginState(getApplicationContext())) {
      removeWX(new Wechat(this));
    }
    String[] loginInfo = LoginUtils.getLoginInfo(getApplicationContext());

    JUtils.Log(TAG, loginInfo[0] + "=====" + loginInfo[1]);
    nameEditText.setText(loginInfo[0]);
    passEditText.setText(loginInfo[1]);
  }

  public void removeWX(Platform platform) {
    if (platform != null) {
      platform.removeAccount(true);
      platform.removeAccount();
    }
  }

  @Override protected void getBundleExtras(Bundle extras) {
  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.activity_phone_login;
  }

  @Override protected void initViews() {
    ShareSDK.initSDK(this);
    forGetTextView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }

  @Override public void onClick(View v) {
    switch (v.getId()) {
      case R.id.set_login_button:
        login_name_value = nameEditText.getText().toString().trim();
        login_pass_value = passEditText.getText().toString().trim();
        if (checkInput(login_name_value, login_pass_value)) {
          showIndeterminateProgressDialog(false);
          SharedPreferences sharedPreferences =
              getSharedPreferences("password", Context.MODE_PRIVATE);
          SharedPreferences.Editor editor = sharedPreferences.edit();
          if (cbPwd.isChecked()) {
            editor.putString(login_name_value, login_pass_value);
          } else {
            editor.remove(login_name_value);
          }
          editor.apply();
          Subscription subscribe = UserModel.getInstance()
              .passwordlogin(login_name_value, login_pass_value, null)
              .subscribeOn(Schedulers.io())
              .subscribe(new ServiceResponse<CodeBean>() {
                @Override public void onNext(CodeBean codeBean) {
                  Log.d(TAG, "user.getCode() "
                      + codeBean.getRcode()
                      + ", user.getResult() "
                      + codeBean.getMsg());
                  if (codeBean.getRcode() == 0) {
                    hideIndeterminateProgressDialog();
                    LoginUtils.saveLoginInfo(true, getApplicationContext(),
                        login_name_value, login_pass_value);
                    JUtils.Toast("登录成功!");
                    LoginUtils.setPushUserAccount(PhoneLoginActivity.this,
                        MiPushClient.getRegId(getApplicationContext()));
                    String login = null;
                    if (null != getIntent() && getIntent().getExtras() != null) {
                      login = getIntent().getExtras().getString("login");
                      actlink = getIntent().getExtras().getString("actlink");
                      id = getIntent().getExtras().getInt("id");
                    }

                    if (null != login) {
                      if (login.equals("cart")) {
                        Intent intent = new Intent(mContext, CartActivity.class);
                        startActivity(intent);
                        finish();
                      } else if (login.equals("product")) {
                        finish();
                      } else if (login.equals("main")) {
                        Intent intent = new Intent(mContext, MainActivity.class);
                        startActivity(intent);
                        finish();
                      } else if (login.equals("point")) {
                        Intent intent =
                            new Intent(mContext, MembershipPointActivity.class);
                        startActivity(intent);
                        finish();
                      } else if (login.equals("money")) {
                        Intent intent = new Intent(mContext, WalletActivity.class);
                        startActivity(intent);
                        finish();
                      } else if (login.equals("axiba")) {
                        Intent intent = new Intent(mContext, MainActivity.class);
                        startActivity(intent);
                        finish();
                      } else if (login.equals("coupon")) {
                        Intent intent = new Intent(mContext, CouponActivity.class);
                        startActivity(intent);
                        finish();
                      } else if (login.equals("productdetail")) {
                        finish();
                      } else if (login.equals("h5")) {
                        //Intent intent = new Intent(mContext, CommonWebViewActivity.class);
                        ////intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        //SharedPreferences sharedPreferences =
                        //    getSharedPreferences("xlmmCookiesAxiba", Context.MODE_PRIVATE);
                        //String cookies = sharedPreferences.getString("Cookie", "");
                        //Bundle bundle = new Bundle();
                        //bundle.putString("cookies", cookies);
                        //bundle.putString("actlink", actlink);
                        //intent.putExtras(bundle);
                        //startActivity(intent);
                        JumpUtils.jumpToWebViewWithCookies(mContext, actlink, -1,
                            CommonWebViewActivity.class);
                        finish();
                      } else if (login.equals("prodcutweb")) {
                        //Intent intent = new Intent(mContext, ProductPopDetailActvityWeb.class);
                        ////intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        //SharedPreferences sharedPreferences =
                        //    getSharedPreferences("xlmmCookiesAxiba", Context.MODE_PRIVATE);
                        //String cookies = sharedPreferences.getString("Cookie", "");
                        //Bundle bundle = new Bundle();
                        //bundle.putString("cookies", cookies);
                        //bundle.putString("actlink", actlink);
                        //intent.putExtras(bundle);
                        //startActivity(intent);
                        JumpUtils.jumpToWebViewWithCookies(mContext, actlink, -1,
                            ProductPopDetailActvityWeb.class);
                        finish();
                      } else if (login.equals("goactivity")) {
                        JumpUtils.jumpToWebViewWithCookies(mContext, actlink, id,
                            ProductPopDetailActvityWeb.class);
                      }
                    }
                  } else {
                    hideIndeterminateProgressDialog();
                    LoginUtils.saveLoginInfo(false, getApplicationContext(), "", "");
                    JUtils.Toast(codeBean.getMsg());
                  }
                }

                @Override public void onCompleted() {
                  super.onCompleted();
                }
              });

          addSubscription(subscribe);
        }
        break;
      case R.id.forgetTextView:
        startActivity(new Intent(this, VerifyPhoneForgetActivity.class));
        finish();
        break;
    }
  }

  public boolean checkInput(String mobile, String password) {
    if (mobile == null || mobile.trim().trim().equals("")) {
      JUtils.Toast("请输入手机号");
    } else {
      if (mobile.length() != 11) {
        JUtils.Toast("请输入正确的手机号");
      } else if (password == null || password.trim().trim().equals("")) {
        JUtils.Toast("密码不能为空");
      } else {
        return true;
      }
    }
    return false;
  }

  @Override protected void onStop() {
    super.onStop();
    removeWX(new Wechat(this));
    ShareSDK.stopSDK(this);
  }

  @Override
  public void beforeTextChanged(CharSequence s, int start, int count, int after) {

  }

  @Override public void onTextChanged(CharSequence s, int start, int before, int count) {

  }

  @Override public void afterTextChanged(Editable s) {
    String phone = s.toString();
    SharedPreferences sharedPreferences =
        getSharedPreferences("password", Context.MODE_PRIVATE);
    String password = sharedPreferences.getString(phone, "");
    if (!password.equals("")) {
      passEditText.setText(password);
      cbPwd.setChecked(true);
    } else {
      passEditText.setText("");
      cbPwd.setChecked(false);
    }
  }
}
