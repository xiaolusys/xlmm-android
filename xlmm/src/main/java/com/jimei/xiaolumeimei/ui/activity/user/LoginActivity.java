package com.jimei.xiaolumeimei.ui.activity.user;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jimei.library.utils.JUtils;
import com.jimei.library.utils.SHA1Utils;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.XlmmApp;
import com.jimei.xiaolumeimei.base.BaseActivity;
import com.jimei.xiaolumeimei.base.BaseAppManager;
import com.jimei.xiaolumeimei.base.CommonWebViewActivity;
import com.jimei.xiaolumeimei.entities.CodeBean;
import com.jimei.xiaolumeimei.entities.UserInfoBean;
import com.jimei.xiaolumeimei.entities.event.LoginEvent;
import com.jimei.xiaolumeimei.entities.event.SetMiPushEvent;
import com.jimei.xiaolumeimei.service.ServiceResponse;
import com.jimei.xiaolumeimei.ui.activity.main.TabActivity;
import com.jimei.xiaolumeimei.ui.activity.product.ProductDetailActivity;
import com.jimei.xiaolumeimei.util.JumpUtils;
import com.jimei.xiaolumeimei.util.LoginUtils;
import com.mob.tools.utils.UIHandler;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.wechat.friends.Wechat;

public class LoginActivity extends BaseActivity
    implements View.OnClickListener, Handler.Callback {
    public static final String SECRET = "3c7b4e3eb5ae4cfb132b2ac060a872ee";
    private static final int MSG_USERID_FOUND = 1;
    private static final int MSG_LOGIN = 2;
    private static final int MSG_AUTH_CANCEL = 3;
    private static final int MSG_AUTH_ERROR = 4;
    private static final int MSG_AUTH_COMPLETE = 5;
    private static final String TAG = LoginActivity.class.getSimpleName();
    @Bind(R.id.register_button)
    Button registerBtn;
    @Bind(R.id.wx_login)
    ImageView wx;
    @Bind(R.id.sms_login)
    ImageView sms;
    @Bind(R.id.login_button)
    ImageView loginBtn;
    @Bind(R.id.finish_layout)
    LinearLayout finishLayout;
    @Bind(R.id.layout)
    LinearLayout layout;
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
    private int id;
    private Wechat wechat;
    private String login;
    private long firstTime = 0;

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

    @Override
    public void initContentView() {
        setContentView(getContentViewLayoutID());
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    protected void initViews() {
        setSwipeBackEnable(false);
    }

    @Override
    protected void setListener() {
        loginBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
        finishLayout.setOnClickListener(this);
        wx.setOnClickListener(this);
        sms.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        if (!LoginUtils.checkLoginState(getApplicationContext())) {
            removeWX(wechat);
        }
    }

    @Override
    public boolean isNeedShow() {
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 1000) {
                firstTime = secondTime;
                JUtils.Toast("再按一次退出程序!");
            } else {
                BaseAppManager.getInstance().clear();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        this.extras = extras;
        if (null != getIntent() && getIntent().getExtras() != null) {
            login = getIntent().getExtras().getString("login");
            actlink = getIntent().getExtras().getString("actlink");
            id = getIntent().getExtras().getInt("id");
        }
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.setting_login_activity;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button:
                Intent intent1 = new Intent(mContext, PhoneLoginActivity.class);
                if (extras != null) {
                    intent1.putExtras(extras);
                }
                startActivity(intent1);
//                finish();
                break;
            case R.id.register_button:
                Intent intent2 = new Intent(mContext, RegisterActivity.class);
                startActivity(intent2);
//                finish();
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
//                finish();
                break;
            case R.id.finish_layout:
                finish();
                break;
        }
    }

    private void sha1() {
        timestamp = System.currentTimeMillis() / 1000 + "";//时间戳
        noncestr = getRandomString(8);//随机八位字符串
        sign_params = "noncestr=" + noncestr + "&secret=" + SECRET + "&timestamp=" + timestamp;
    }

    private void login(String plat) {
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
                login(plat.getName());
                return;
            }
        }
        plat.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                if (i == Platform.ACTION_USER_INFOR) {
                    UIHandler.sendEmptyMessage(MSG_AUTH_COMPLETE, LoginActivity.this);
                    login(platform.getName());
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

                addSubscription(XlmmApp.getUserInteractor(LoginActivity.this)
                    .wxappLogin(noncestr, timestamp, sign, headimgurl, nickname, openid, unionid, new ServiceResponse<CodeBean>() {
                        @Override
                        public void onNext(CodeBean codeBean) {
                            if (codeBean != null) {
                                int code = codeBean.getRcode();
                                if (0 == code) {
                                    EventBus.getDefault().post(new SetMiPushEvent());
                                    JUtils.Toast("登录成功");
//                                    addSubscription(XlmmApp.getUserInteractor(LoginActivity.this)
//                                        .needSetInfo(new ServiceResponse<NeedSetInfoBean>() {
//                                            @Override
//                                            public void onNext(NeedSetInfoBean needSetInfoBean) {
//                                                hideIndeterminateProgressDialog();
//                                                LoginUtils.saveLoginSuccess(true, getApplicationContext());
//                                                EventBus.getDefault().post(new LoginEvent());
//                                                if (needSetInfoBean.getCode() == 0) {
//                                                    if (login != null) {
//                                                        if (login.equals("push_jump")) {
//                                                            JumpUtils.push_jump_proc(LoginActivity.this, actlink);
//                                                            finish();
//                                                        } else if (login.equals("productdetail")) {
//                                                            Intent intent = new Intent(mContext, ProductDetailActivity.class);
//                                                            Bundle bundle = new Bundle();
//                                                            bundle.putInt("model_id", id);
//                                                            intent.putExtras(bundle);
//                                                            startActivity(intent);
//                                                            finish();
//                                                        } else if (login.equals("h5")) {
//                                                            Intent intent = new Intent(mContext, CommonWebViewActivity.class);
//                                                            SharedPreferences sharedPreferences =
//                                                                getSharedPreferences("xlmmCookiesAxiba",
//                                                                    Context.MODE_PRIVATE);
//                                                            String cookies = sharedPreferences.getString("cookiesString", "");
//                                                            String domain = sharedPreferences.getString("cookiesDomain", "");
//                                                            Bundle bundle = new Bundle();
//                                                            bundle.putString("cookies", cookies);
//                                                            bundle.putString("domain", domain);
//                                                            bundle.putString("actlink", actlink);
//                                                            intent.putExtras(bundle);
//                                                            startActivity(intent);
//                                                            finish();
//                                                        } else if (login.equals("car") || login.equals("my") || login.equals("boutique")) {
//                                                            Bundle bundle = new Bundle();
//                                                            bundle.putString("flag", login);
//                                                            readyGoThenKill(TabActivity.class, bundle);
//                                                        } else {
//                                                            finish();
//                                                        }
//                                                    } else {
//                                                        readyGoThenKill(TabActivity.class);
//                                                    }
//                                                } else if (needSetInfoBean.getCode() == 1) {
//                                                    Intent intent = new Intent(mContext, VerifyPhoneActivity.class);
//                                                    JUtils.Toast(needSetInfoBean.getInfo());
//                                                    startActivity(intent);
//                                                    finish();
//                                                } else if (needSetInfoBean.getCode() == 2) {
//                                                    Intent intent = new Intent(mContext, WxLoginBindPhoneActivity.class);
//                                                    JUtils.Toast(needSetInfoBean.getInfo());
//                                                    startActivity(intent);
//                                                    finish();
//                                                }
//                                            }
//                                        }));
                                    addSubscription(XlmmApp.getMainInteractor(LoginActivity.this)
                                        .getProfile(new ServiceResponse<UserInfoBean>() {
                                            @Override
                                            public void onNext(UserInfoBean userInfoBean) {
                                                hideIndeterminateProgressDialog();
                                                if (userInfoBean != null && userInfoBean.getXiaolumm() != null &&
                                                    userInfoBean.getXiaolumm().getId() != 0) {
                                                    LoginUtils.saveLoginSuccess(true, getApplicationContext());
                                                    EventBus.getDefault().post(new LoginEvent());
                                                    if (login != null) {
                                                        if (login.equals("push_jump")) {
                                                            JumpUtils.push_jump_proc(LoginActivity.this, actlink);
                                                            finish();
                                                        } else if (login.equals("productdetail")) {
                                                            Intent intent = new Intent(mContext, ProductDetailActivity.class);
                                                            Bundle bundle = new Bundle();
                                                            bundle.putInt("model_id", id);
                                                            intent.putExtras(bundle);
                                                            startActivity(intent);
                                                            finish();
                                                        } else if (login.equals("h5")) {
                                                            Intent intent = new Intent(mContext, CommonWebViewActivity.class);
                                                            SharedPreferences sharedPreferences =
                                                                getSharedPreferences("xlmmCookiesAxiba",
                                                                    Context.MODE_PRIVATE);
                                                            String cookies = sharedPreferences.getString("cookiesString", "");
                                                            String domain = sharedPreferences.getString("cookiesDomain", "");
                                                            Bundle bundle = new Bundle();
                                                            bundle.putString("cookies", cookies);
                                                            bundle.putString("domain", domain);
                                                            bundle.putString("actlink", actlink);
                                                            intent.putExtras(bundle);
                                                            startActivity(intent);
                                                            finish();
                                                        } else if (login.equals("car") || login.equals("my") || login.equals("boutique")) {
                                                            Bundle bundle = new Bundle();
                                                            bundle.putString("flag", login);
                                                            readyGoThenKill(TabActivity.class, bundle);
                                                        } else {
                                                            finish();
                                                        }
                                                    } else {
                                                        readyGoThenKill(TabActivity.class);
                                                    }
                                                } else {
                                                    JUtils.Toast("你不是小鹿妈妈，暂无权限登录哦!");
                                                }
                                            }
                                        }));
                                } else {
                                    removeWX(wechat);
                                    hideIndeterminateProgressDialog();
                                    JUtils.Toast(codeBean.getMsg());
                                }
                            }
                        }
                    }));
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                removeWX(wechat);
                JUtils.Toast("授权登录失败!");
                hideIndeterminateProgressDialog();
            }

            @Override
            public void onCancel(Platform platform, int i) {
                removeWX(wechat);
                JUtils.Toast("取消授权登录!");
                hideIndeterminateProgressDialog();
            }
        });
        plat.SSOSetting(true);
        plat.showUser(null);
    }

    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case MSG_USERID_FOUND:
                break;
            case MSG_LOGIN:
                break;
            case MSG_AUTH_CANCEL:
                break;
            case MSG_AUTH_ERROR:
                break;
            case MSG_AUTH_COMPLETE:
                break;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        removeWX(wechat);
    }

    public void removeWX(Platform platform) {
        if (platform != null) {
            platform.removeAccount(true);
            platform.removeAccount();
        }
    }
}

