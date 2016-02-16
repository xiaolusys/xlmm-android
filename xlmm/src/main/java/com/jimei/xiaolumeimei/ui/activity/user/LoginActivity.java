package com.jimei.xiaolumeimei.ui.activity.user;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.Bind;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.UserBean;
import com.jimei.xiaolumeimei.model.UserModel;
import com.jimei.xiaolumeimei.ui.activity.main.MainActivity;
import com.jimei.xiaolumeimei.ui.activity.trade.CartActivity;
import com.jimei.xiaolumeimei.utils.LoginUtils;
import com.jimei.xiaolumeimei.widget.ClearEditText;
import com.jimei.xiaolumeimei.widget.PasswordEditText;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;
import rx.schedulers.Schedulers;

public class LoginActivity extends BaseSwipeBackCompatActivity
    implements View.OnClickListener {
  public static final String SECRET = "3c7b4e3eb5ae4cfb132b2ac060a872ee";
  String login_name_value;//登录名
  String login_pass_value;//登录密码
  //boolean isLogin;//判断是否登录
  @Bind(R.id.set_login_name) ClearEditText nameEditText;
  @Bind(R.id.set_login_password) PasswordEditText passEditText;
  @Bind(R.id.set_login_button) Button login_button;
  @Bind(R.id.set_register_button) Button set_register;
  UserModel model = new UserModel();
  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.forgetTextView) TextView forGetTextView;
  @Bind(R.id.wx_login) ImageView wx;
  @Bind(R.id.sms_login) ImageView sms;
  String TAG = "LoginActivity";
  private SharedPreferences sharedPreferences;
  private SharedPreferences.Editor editor;
  private String timestamp;
  private String randomString;

  public static String getRandomString(int length) {
    //length表示生成字符串的长度
    String base = "abcdefghijklmnopqrstuvwxyz0123456789";
    Random random = new Random();
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < length; i++) {
      int number = random.nextInt(base.length());
      sb.append(base.charAt(number));
    }
    return sb.toString();
  }

  @Override protected void setListener() {
    login_button.setOnClickListener(this);

    set_register.setOnClickListener(this);

    toolbar.setOnClickListener(this);

    wx.setOnClickListener(this);
    sms.setOnClickListener(this);

    forGetTextView.setOnClickListener(this);
  }

  @Override protected void initData() {

    sharedPreferences =
        getApplicationContext().getSharedPreferences("login_info", Context.MODE_PRIVATE);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      finish();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override protected void getBundleExtras(Bundle extras) {
  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.setting_login_activity;
  }

  @Override protected void initViews() {
    toolbar.setTitle("");
    setSupportActionBar(toolbar);
    toolbar.setNavigationIcon(R.drawable.back);
    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        finish();
      }
    });

    forGetTextView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  //save user information

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }

  @Override public void onClick(View v) {
    switch (v.getId()) {
      case R.id.set_login_button:

        login_name_value = nameEditText.getText().toString().trim();
        login_pass_value = passEditText.getText().toString().trim();

        if (checkInput(login_name_value, login_pass_value)) {
          model.login(login_name_value, login_pass_value)
              .subscribeOn(Schedulers.newThread())
              .subscribe(new ServiceResponse<UserBean>() {
                @Override public void onNext(UserBean user) {
                  Log.d(TAG, "user.getCode() "
                      + user.getCode()
                      + ", user.getResult() "
                      + user.getResult());
                  if (user.getCode() == 0 && user.getResult().equals("login")) {

                    LoginUtils.saveLoginInfo(true, getApplicationContext(),
                        login_name_value, login_pass_value);

                    Toast.makeText(mContext, "登录成功", Toast.LENGTH_SHORT).show();

                    String login = getIntent().getExtras().getString("login");

                    assert login != null;
                    if (login.equals("cart")) {
                      Intent intent = new Intent(mContext, CartActivity.class);
                      startActivity(intent);
                      finish();
                    } else if (login.equals("product")) {
                      finish();
                    } else if (login.equals("main")) {
                      finish();
                    } else if (login.equals("point")) {
                      Intent intent = new Intent(mContext, MembershipPointActivity.class);
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
                    }
                  } else {

                    LoginUtils.saveLoginInfo(false, getApplicationContext(), "", "");

                    Toast.makeText(mContext, "用户名或者密码错误,请检查", Toast.LENGTH_SHORT).show();
                  }
                }

                @Override public void onCompleted() {
                  super.onCompleted();
                }
              });
        }
        break;
      case R.id.set_register_button:
        Intent intent = new Intent(mContext, RegisterActivity.class);
        startActivity(intent);
        finish();

        break;

      case R.id.wx_login:

        sha1();

        Platform wechat = ShareSDK.getPlatform(getApplicationContext(), Wechat.NAME);
        wechat.setPlatformActionListener(new PlatformActionListener() {
          @Override public void onComplete(Platform platform, int i,
              HashMap<String, Object> hashMap) {

            Set<String> keys = hashMap.keySet();

            for (String key : keys) {

              String value = (String) hashMap.get(key);
              JUtils.Log("LoginActivity", key + "========" + value);
            }
          }

          @Override public void onError(Platform platform, int i, Throwable throwable) {

          }

          @Override public void onCancel(Platform platform, int i) {

          }
        });
        wechat.authorize();

        break;

      case R.id.sms_login:
        startActivity(new Intent(LoginActivity.this, SmsLoginActivity.class));
        finish();
        break;

      case R.id.forgetTextView:

        startActivity(new Intent(LoginActivity.this, VerifyPhoneForgetActivity.class));
        finish();
        break;
    }
  }

  private void sha1() {
    timestamp = System.currentTimeMillis() / 1000 + "";//时间戳
    randomString = getRandomString(8);//随机八位字符串
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
}

