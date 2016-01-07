package com.jimei.xiaolumeimei;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;
import com.jimei.xiaolumeimei.okhttp.OkHttpClientManager;
import com.jimei.xiaolumeimei.utils.LoginUtils;
import com.squareup.okhttp.OkHttpClient;
import com.zhy.autolayout.config.AutoLayoutConifg;
import java.util.concurrent.TimeUnit;

/**
 * Created by ye.xu on 15/12/29.
 *
 * Copyright 2015年 上海己美. All rights reser.
 */
public class XlmmApp extends Application {

  public static boolean isLogin;

  public void isLogin() {
    SharedPreferences mSharedPreferences =
        getSharedPreferences("login_info", MODE_PRIVATE);
    isLogin = mSharedPreferences.getBoolean("success", false);
    Log.i("loginTest", isLogin + "");
  }

  @Override public void onCreate() {
    super.onCreate();
    initOkHttpClient();
    AutoLayoutConifg.getInstance().useDeviceSize();
    isLogin();

    if (isLogin) {
      String[] loginInfo = LoginUtils.getLoginInfo(getApplicationContext());

      LoginUtils.doLogin(getApplicationContext(), loginInfo[0], loginInfo[1]);
    }
    //CrashWoodpecker.fly().to(this);
  }

  //初始化OkHttpClient
  private void initOkHttpClient() {

    OkHttpClient httpClient = OkHttpClientManager.getInstance().getOkHttpClient();
    httpClient.setConnectTimeout(10 * 1000, TimeUnit.MILLISECONDS);
    httpClient.setWriteTimeout(30 * 1000, TimeUnit.MILLISECONDS);
    httpClient.setWriteTimeout(30 * 1000, TimeUnit.MILLISECONDS);
  }
}
