package com.jimei.xiaolumeimei.ui.activity.user;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import butterknife.Bind;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.base.CommonWebViewActivity;
import com.jimei.xiaolumeimei.entities.CodeBean;
import com.jimei.xiaolumeimei.entities.GetCouponbean;
import com.jimei.xiaolumeimei.entities.NeedSetInfoBean;
import com.jimei.xiaolumeimei.event.UserInfoEmptyEvent;
import com.jimei.xiaolumeimei.model.UserModel;
import com.jimei.xiaolumeimei.ui.activity.main.ActivityWebViewActivity;
import com.jimei.xiaolumeimei.ui.activity.product.CollectionActivity;
import com.jimei.xiaolumeimei.ui.activity.product.ProductPopDetailActvityWeb;
import com.jimei.xiaolumeimei.ui.activity.trade.CartActivity;
import com.jimei.xiaolumeimei.utils.JumpUtils;
import com.jimei.xiaolumeimei.utils.LoginUtils;
import com.jimei.xiaolumeimei.utils.SHA1Utils;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.mob.tools.utils.UIHandler;
import com.umeng.analytics.MobclickAgent;
import com.xiaomi.mipush.sdk.MiPushClient;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.greenrobot.eventbus.EventBus;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
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
  @Bind(R.id.register_button) Button registerBtn;
  @Bind(R.id.wx_login) ImageView wx;
  @Bind(R.id.sms_login) ImageView sms;
  @Bind(R.id.login_button) ImageView loginBtn;
  @Bind(R.id.iv_close) ImageView closeIv;
  private String timestamp;
  private String noncestr;
  private String sign_params;
  private String sign;
  private String headimgurl;
  private String nickname;
  private String openid;
  private String unionid;
  private Bundle extras;
  private String actlink;
  private String title;
  private int id;
  private Wechat wechat;

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
    loginBtn.setOnClickListener(this);
    registerBtn.setOnClickListener(this);
    closeIv.setOnClickListener(this);
    wx.setOnClickListener(this);
    sms.setOnClickListener(this);
  }

  @Override protected void onResume() {
    super.onResume();
    MobclickAgent.onPageStart(this.getClass().getSimpleName());
    MobclickAgent.onResume(this);
  }

  @Override protected void initData() {
    if (!LoginUtils.checkLoginState(getApplicationContext())) {
      removeWX(wechat);
    }
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      finish();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override protected void getBundleExtras(Bundle extras) {
    this.extras = extras;
  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.setting_login_activity;
  }

  @Override protected void initViews() {
    ShareSDK.initSDK(this);
  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }

  @Override public void onClick(View v) {
    switch (v.getId()) {
      case R.id.login_button:
        Intent intent1 = new Intent(mContext, PhoneLoginActivity.class);
        if (extras != null) {
          intent1.putExtras(extras);
        }
        startActivity(intent1);
        finish();
        break;
      case R.id.register_button:
        Intent intent2 = new Intent(mContext, RegisterActivity.class);
        startActivity(intent2);
        finish();
        break;
      case R.id.wx_login:
        sha1();
        //sha1 签名
        sign = SHA1Utils.hex_sha1(sign_params);
        JUtils.Log(TAG, "sign=" + sign);
        wechat = new Wechat(this);
        authorize(wechat);
        break;
      case R.id.sms_login:
        Intent intent = new Intent(LoginActivity.this, SmsLoginActivity.class);
        if (extras != null) {
          intent.putExtras(extras);
        }
        startActivity(intent);
        finish();
        break;
      case R.id.iv_close:
        finish();
        break;
    }
  }

  private void sha1() {
    timestamp = System.currentTimeMillis() / 1000 + "";//时间戳
    noncestr = getRandomString(8);//随机八位字符串
    sign_params = "noncestr=" + noncestr + "&secret=" + SECRET + "&timestamp=" + timestamp;
  }

  private void login(String plat, String userId, HashMap<String, Object> userInfo) {
    Message msg = new Message();
    msg.what = MSG_LOGIN;
    msg.obj = plat;
    UIHandler.sendMessage(msg, this);
  }

  private void authorize(Platform plat) {
    showIndeterminateProgressDialog(false);
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
      @Override public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
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

        Subscription subscription = UserModel.getInstance()
            .wxapp_login(noncestr, timestamp, sign, headimgurl, nickname, openid, unionid)
            .subscribeOn(Schedulers.io())
            .subscribe(new ServiceResponse<CodeBean>() {

              @Override public void onNext(CodeBean codeBean) {

                if (codeBean != null) {
                  int code = codeBean.getRcode();
                  if (0 == code) {
                  EventBus.getDefault().post(new UserInfoEmptyEvent());
                    JUtils.Toast("登录成功");
                    Subscription subscribe = UserModel.getInstance()
                        .need_set_info()
                        .subscribeOn(Schedulers.io())
                        .subscribe(new ServiceResponse<NeedSetInfoBean>() {

                          @Override public void onNext(NeedSetInfoBean needSetInfoBean) {
                            //set xiaomi push useraccount
                            LoginUtils.setPushUserAccount(LoginActivity.this,
                                      MiPushClient.getRegId(getApplicationContext()));
                            hideIndeterminateProgressDialog();
                            LoginUtils.saveLoginSuccess(true, getApplicationContext());
                            if (needSetInfoBean.getCode()==0) {
                              String login = null;
                              if (null != getIntent() && getIntent().getExtras() != null) {
                                login = getIntent().getExtras().getString("login");
                                actlink = getIntent().getExtras().getString("actlink");
                                title = getIntent().getExtras().getString("title", "");
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
                                  finish();
                                } else if (login.equals("collect")){
                                  Intent intent = new Intent(mContext, CollectionActivity.class);
                                  startActivity(intent);
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
                                  finish();
                                } else if (login.equals("coupon")) {
                                  Intent intent = new Intent(mContext, AllCouponActivity.class);
                                  startActivity(intent);
                                  finish();
                                } else if (login.equals("productdetail")) {
                                  finish();
                                } else if (login.equals("h5")) {
                                  Intent intent = new Intent(mContext, CommonWebViewActivity.class);
                                  //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                  SharedPreferences sharedPreferences =
                                          getSharedPreferences("xlmmCookiesAxiba", Context.MODE_PRIVATE);
                                  String cookies = sharedPreferences.getString("cookiesString", "");
                                  String domain = sharedPreferences.getString("cookiesDomain", "");
                                  Bundle bundle = new Bundle();
                                  bundle.putString("cookies", cookies);
                                  bundle.putString("domain", domain);
                                  bundle.putString("actlink", actlink);
                                  intent.putExtras(bundle);
                                  startActivity(intent);
                                  finish();
                                } else if (login.equals("prodcutweb")) {
                                  JumpUtils.jumpToWebViewWithCookies(mContext, actlink, -1,
                                          ProductPopDetailActvityWeb.class);
                                  finish();
                                } else if (login.equals("goactivity")) {
                                  JumpUtils.jumpToWebViewWithCookies(mContext, actlink, id,
                                          ActivityWebViewActivity.class, title);
                                  finish();
                                } else if (login.equals("getCoupon")) {
                                  Subscription subscription1 = UserModel.getInstance()
                                          .getCouPon()
                                          .subscribeOn(Schedulers.io())
                                          .subscribe(new ServiceResponse<Response<GetCouponbean>>() {
                                            @Override public void onNext(
                                                    Response<GetCouponbean> getCouponbeanResponse) {
                                              JUtils.Log("getCoupon", "onnext");
                                              if (getCouponbeanResponse != null) {
                                                if (getCouponbeanResponse.isSuccessful()) {

                                                  JUtils.Log("getCoupon",
                                                          "onnext == " + getCouponbeanResponse.body()
                                                                  .toString());
                                                  JUtils.Toast(getCouponbeanResponse.body().getInfo());

                                                  finish();
                                                }
                                              }
                                            }

                                            @Override public void onError(Throwable e) {
                                              super.onError(e);
                                              if (e instanceof HttpException) {
                                                JUtils.Toast("优惠券领取失败");
                                              }
                                            }
                                          });
                                  addSubscription(subscription1);
                                }
                              }
                            }else {
                              Intent intent = new Intent(mContext, WxLoginBindPhoneActivity.class);
                              JUtils.Toast(needSetInfoBean.getInfo());
                              startActivity(intent);
                              finish();
                            }
                          }
                        });
                    addSubscription(subscribe);
                  } else {
                    removeWX(wechat);
                    hideIndeterminateProgressDialog();
                    JUtils.Toast(codeBean.getMsg());
                  }
                }
              }
            });
        addSubscription(subscription);
        //show.dismiss();
      }

      @Override public void onError(Platform platform, int i, Throwable throwable) {
        removeWX(wechat);
      }

      @Override public void onCancel(Platform platform, int i) {
        removeWX(wechat);
        hideIndeterminateProgressDialog();
      }
    });
    plat.SSOSetting(true);
    plat.showUser(null);
  }

  public boolean handleMessage(Message msg) {
    switch (msg.what) {
      case MSG_USERID_FOUND: {
        //                JUtils.Toast(getResources().getString(R.string.userid_found));
      }
      break;
      case MSG_LOGIN: {
        //                JUtils.Toast(getResources().getString(R.string.logining));
      }
      break;
      case MSG_AUTH_CANCEL: {
        //                JUtils.Toast(getResources().getString(R.string.auth_cancel));
      }
      break;
      case MSG_AUTH_ERROR: {
        //                JUtils.Toast(getResources().getString(R.string.auth_error));
      }
      break;
      case MSG_AUTH_COMPLETE: {
        //                JUtils.Toast(getResources().getString(R.string.auth_complete));
      }
      break;
    }
    return false;
  }

  @Override protected void onStop() {
    super.onStop();
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    removeWX(wechat);
    ShareSDK.stopSDK(this);
  }

  public void removeWX(Platform platform) {
    if (platform != null) {
      platform.removeAccount(true);
      platform.removeAccount();
    }
  }

  @Override protected void onPause() {
    super.onPause();
    MobclickAgent.onPageEnd(this.getClass().getSimpleName());
    MobclickAgent.onPause(this);
  }

}

