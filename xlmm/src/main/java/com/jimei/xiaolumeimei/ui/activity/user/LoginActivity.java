package com.jimei.xiaolumeimei.ui.activity.user;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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
import com.afollestad.materialdialogs.MaterialDialog;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.NeedSetInfoBean;
import com.jimei.xiaolumeimei.entities.UserBean;
import com.jimei.xiaolumeimei.entities.WxLogininfoBean;
import com.jimei.xiaolumeimei.model.UserModel;
import com.jimei.xiaolumeimei.ui.activity.main.MainActivity;
import com.jimei.xiaolumeimei.ui.activity.trade.CartActivity;
import com.jimei.xiaolumeimei.utils.LoginUtils;
import com.jimei.xiaolumeimei.utils.SHA1Utils;
import com.jimei.xiaolumeimei.widget.ClearEditText;
import com.jimei.xiaolumeimei.widget.PasswordEditText;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.mob.tools.utils.UIHandler;
import com.xiaomi.mipush.sdk.MiPushClient;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import rx.Subscription;
import rx.schedulers.Schedulers;

public class LoginActivity extends BaseSwipeBackCompatActivity
    implements View.OnClickListener, Handler.Callback {
  public static final String SECRET = "3c7b4e3eb5ae4cfb132b2ac060a872ee";
  private static final int MSG_USERID_FOUND = 1;
  private static final int MSG_LOGIN = 2;
  private static final int MSG_AUTH_CANCEL = 3;
  private static final int MSG_AUTH_ERROR = 4;
  private static final int MSG_AUTH_COMPLETE = 5;
  private static final String TAG = LoginActivity.class.getSimpleName();
  String login_name_value;//登录名
  String login_pass_value;//登录密码
  //boolean isLogin;//判断是否登录
  @Bind(R.id.set_login_name) ClearEditText nameEditText;
  @Bind(R.id.set_login_password) PasswordEditText passEditText;
  @Bind(R.id.set_login_button) Button login_button;
  @Bind(R.id.set_register_button) Button set_register;
  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.forgetTextView) TextView forGetTextView;
  @Bind(R.id.wx_login) ImageView wx;
  @Bind(R.id.sms_login) ImageView sms;
  private SharedPreferences sharedPreferences;
  private SharedPreferences.Editor editor;
  private String timestamp;
  private String noncestr;
  private String sign_params;
  private String sign;
  private String headimgurl;
  private String nickname;
  private String openid;
  private String unionid;

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

  @Override protected void onResume() {
    super.onResume();
  }

  @Override protected void initData() {

    if (!LoginUtils.checkLoginState(getApplicationContext())) {
      removeWX(new Wechat(this));
    }
    //UMWXHandler wxHandler =
    //    new UMWXHandler(this, "wx25fcb32689872499", "3c7b4e3eb5ae4cfb132b2ac060a872ee");
    //wxHandler.;
    sharedPreferences =
        getApplicationContext().getSharedPreferences("login_info", Context.MODE_PRIVATE);
    String[] loginInfo = LoginUtils.getLoginInfo(getApplicationContext());

    JUtils.Log(TAG, loginInfo[0] + "=====" + loginInfo[1]);
    nameEditText.setText(loginInfo[0]);
    passEditText.setText(loginInfo[1]);
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
    finishBack(toolbar);

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
          Subscription subscribe = UserModel.getInstance()
              .login(login_name_value, login_pass_value)
              .subscribeOn(Schedulers.io())
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

                    //set xiaomi push useraccount
                    LoginUtils.setPushUserAccount(LoginActivity.this,
                        MiPushClient.getRegId(getApplicationContext()));
                    String login;
                    if (null != getIntent()) {
                      login = getIntent().getExtras().getString("login");
                    } else {
                      return;
                    }

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
                    }else if (login.equals("productdetail")) {
                      finish();
                    }
                  } else if (2 == user.getCode()) {

                    LoginUtils.saveLoginInfo(false, getApplicationContext(), "", "");

                    Toast.makeText(mContext, "用户名或者密码错误,请检查", Toast.LENGTH_SHORT).show();
                  } else if (5 == user.getCode()) {
                    LoginUtils.saveLoginInfo(false, getApplicationContext(), "", "");
                    JUtils.Toast("未设置密码,请选择短信登陆");
                  }
                }

                @Override public void onCompleted() {
                  super.onCompleted();
                }
              });

          addSubscription(subscribe);
        }
        break;
      case R.id.set_register_button:
        Intent intent = new Intent(mContext, RegisterActivity.class);
        startActivity(intent);
        finish();

        break;

      case R.id.wx_login:
        sha1();
        //sha1 签名
        sign = SHA1Utils.hex_sha1(sign_params);
        JUtils.Log(TAG, "sign=" + sign);

        //authorize(new Wechat(this));

        authorize(new Wechat(this));

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
    noncestr = getRandomString(8);//随机八位字符串
    sign_params =
        "noncestr=" + noncestr + "&secret=" + SECRET + "&timestamp=" + timestamp;
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

  private void login(String plat, String userId, HashMap<String, Object> userInfo) {
    Message msg = new Message();
    msg.what = MSG_LOGIN;
    msg.obj = plat;
    UIHandler.sendMessage(msg, this);
  }

  private void authorize(Platform plat) {

    MaterialDialog.Builder content =
        new MaterialDialog.Builder(this).content("正在加载.....");

    MaterialDialog show = content.show();

    if (plat == null) {
      return;
    }

    if (plat.isAuthValid()) {
      String userId = plat.getDb().getUserId();
      if (!TextUtils.isEmpty(userId)) {
        UIHandler.sendEmptyMessage(MSG_USERID_FOUND, this);
        login(plat.getName(), userId, null);
        return;
      }
    }
    plat.setPlatformActionListener(new PlatformActionListener() {
      @Override
      public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        show.dismiss();
        if (i == Platform.ACTION_USER_INFOR) {
          UIHandler.sendEmptyMessage(MSG_AUTH_COMPLETE, LoginActivity.this);
          login(platform.getName(), platform.getDb().getUserId(), hashMap);
        }
        for (Map.Entry<String, Object> entry : hashMap.entrySet()) {
          String key = entry.getKey();
          Object value = entry.getValue();
          JUtils.Log(TAG, key + "=====" + value);
        }
        JUtils.Log(TAG, "------User Name ---------" + platform.getDb().getUserName());
        JUtils.Log(TAG, "------User ID ---------" + platform.getDb().getUserId());

        headimgurl = (String) hashMap.get("headimgurl");
        nickname = (String) hashMap.get("nickname");
        openid = (String) hashMap.get("openid");
        unionid = (String) hashMap.get("unionid");

        JUtils.Log(TAG, "------noncestr---------" + noncestr);
        JUtils.Log(TAG, "------timestamp---------" + timestamp);
        JUtils.Log(TAG, "------sign---------" + sign);
        JUtils.Log(TAG, "------headimgurl---------" + headimgurl);
        JUtils.Log(TAG, "------nickname---------" + nickname);
        JUtils.Log(TAG, "------openid---------" + openid);
        JUtils.Log(TAG, "------unionid---------" + unionid);

        //MaterialDialog.Builder content =
        //    new MaterialDialog.Builder(LoginActivity.this).content("正在加载.....");
        //
        //MaterialDialog show = content.show();

        Subscription subscription = UserModel.getInstance()
            .wxapp_login(noncestr, timestamp, sign, headimgurl, nickname, openid, unionid)
            .subscribeOn(Schedulers.io())
            .subscribe(new ServiceResponse<WxLogininfoBean>() {

              @Override public void onCompleted() {
                super.onCompleted();
              }

              @Override public void onError(Throwable e) {
                super.onError(e);
                show.dismiss();
              }

              @Override public void onNext(WxLogininfoBean wxLogininfoBean) {

                if (wxLogininfoBean != null) {
                  int code = wxLogininfoBean.getCode();
                  if (0 == code) {
                    Subscription subscribe = UserModel.getInstance()
                        .need_set_info()
                        .subscribeOn(Schedulers.io())
                        .subscribe(new ServiceResponse<NeedSetInfoBean>() {

                          @Override public void onError(Throwable e) {
                            super.onError(e);
                            show.dismiss();
                          }

                          @Override public void onNext(NeedSetInfoBean needSetInfoBean) {
                            //set xiaomi push useraccount
                            LoginUtils.setPushUserAccount(LoginActivity.this,
                                MiPushClient.getRegId(getApplicationContext()));

                            int codeInfo = needSetInfoBean.getCode();
                            if (0 == codeInfo) {
                              LoginUtils.saveLoginSuccess(true, getApplicationContext());
                              JUtils.Toast("登录成功");
                              Intent intent =
                                  new Intent(LoginActivity.this, MainActivity.class);
                              startActivity(intent);

                              finish();
                            } else if (1 == codeInfo) {
                              LoginUtils.saveLoginSuccess(true, getApplicationContext());
                              JUtils.Toast("登录成功，已绑定手机号");
                              JUtils.Log(TAG, "code=1,login succ,need reset pwd");
                /*Intent intent = new Intent(LoginActivity.this,
                    WxLoginBindPhoneActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("headimgurl", headimgurl);
                bundle.putString("nickname", nickname);
                intent.putExtras(bundle);
                startActivity(intent);*/
                              Intent intent =
                                  new Intent(LoginActivity.this, MainActivity.class);
                              startActivity(intent);

                              finish();
                            } else if (2 == codeInfo) {
                              show.dismiss();
                              LoginUtils.saveLoginSuccess(true, getApplicationContext());
                              JUtils.Toast("登录成功,前往绑定手机");
                              Intent intent = new Intent(LoginActivity.this,
                                  WxLoginBindPhoneActivity.class);
                              Bundle bundle = new Bundle();
                              bundle.putString("headimgurl", headimgurl);
                              bundle.putString("nickname", nickname);
                              intent.putExtras(bundle);
                              startActivity(intent);

                              finish();
                            }
                          }
                        });

                  } else if (1 == code) {
                    JUtils.Toast("签名错误");
                  } else if (2 == code) {
                    JUtils.Toast("非法用户");
                  }
                }
              }
            });
        addSubscription(subscription);
        //show.dismiss();
      }

      @Override public void onError(Platform platform, int i, Throwable throwable) {
        show.dismiss();
      }

      @Override public void onCancel(Platform platform, int i) {
        show.dismiss();
      }
    });
    plat.SSOSetting(true);
    plat.showUser(null);
  }

  public boolean handleMessage(Message msg) {
    switch (msg.what) {
      case MSG_USERID_FOUND: {
        //Toast.makeText(this, R.string.userid_found, Toast.LENGTH_SHORT).show();
      }
      break;
      case MSG_LOGIN: {

        //String text = getString(R.string.logining, msg.obj);
        //Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
      }
      break;
      case MSG_AUTH_CANCEL: {
        //Toast.makeText(this, R.string.auth_cancel, Toast.LENGTH_SHORT).show();
      }
      break;
      case MSG_AUTH_ERROR: {
        //Toast.makeText(this, R.string.auth_error, Toast.LENGTH_SHORT).show();
      }
      break;
      case MSG_AUTH_COMPLETE: {
        //Toast.makeText(this, R.string.auth_complete, Toast.LENGTH_SHORT).show();
      }
      break;
    }
    return false;
  }

  /*
  @Override
  public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
    if (i == Platform.ACTION_USER_INFOR) {
      UIHandler.sendEmptyMessage(MSG_AUTH_COMPLETE, this);
      login(platform.getName(), platform.getDb().getUserId(), hashMap);
    }
    for (Map.Entry<String, Object> entry : hashMap.entrySet()) {
      String key = entry.getKey();
      Object value = entry.getValue();
      JUtils.Log(TAG, key + "=====" + value);
    }
    JUtils.Log(TAG, "------User Name ---------" + platform.getDb().getUserName());
    JUtils.Log(TAG, "------User ID ---------" + platform.getDb().getUserId());
    headimgurl = (String) hashMap.get("headimgurl");
    nickname = (String) hashMap.get("nickname");
    openid = (String) hashMap.get("openid");
    unionid = (String) hashMap.get("unionid");

    model.wxapp_login(noncestr, timestamp, sign, headimgurl, nickname, openid, unionid)
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<WxLogininfoBean>() {
          @Override public void onNext(WxLogininfoBean wxLogininfoBean) {
            super.onNext(wxLogininfoBean);
            if (wxLogininfoBean != null) {
              int code = wxLogininfoBean.getCode();
              if (0 == code) {

                LoginUtils.saveLoginSuccess(true, getApplicationContext());
                JUtils.Toast("登录成功,前往绑定手机");
                Intent intent =
                    new Intent(LoginActivity.this, WxLoginBindPhoneActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("headimgurl", headimgurl);
                bundle.putString("nickname", nickname);
                intent.putExtras(bundle);
                startActivity(intent);

                finish();
              } else {
                JUtils.Toast("登陆有误,请检查你的微信版本");
              }
            }
          }
        });
  }

  @Override public void onError(Platform platform, int i, Throwable throwable) {

  }

  @Override public void onCancel(Platform platform, int i) {

  }
*/
  public void removeWX(Platform platform) {
    if (platform != null) {
      platform.removeAccount(true);
      platform.removeAccount();
    }
  }

  @Override protected void onStop() {
    super.onStop();
    removeWX(new Wechat(this));
    ShareSDK.stopSDK(this);
  }
}

