package com.jimei.xiaolumeimei;

import android.app.Application;
import android.content.Context;
import com.jimei.xiaolumeimei.okhttp.OkHttpClientManager;
import com.jimei.xiaolumeimei.okhttp.PersistentCookieStore;
import com.jude.utils.JUtils;
import com.squareup.okhttp.OkHttpClient;
import com.zhy.autolayout.config.AutoLayoutConifg;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;

/**
 * Created by ye.xu on 15/12/29.
 *
 * Copyright 2015年 上海己美. All rights reser.
 */
public class XlmmApp extends Application {

  private static Context mContext;

  public static Context getInstance() {
    return mContext;
  }

  @Override public void onCreate() {
    super.onCreate();
    mContext = this;

    initOkHttpClient();
    JUtils.initialize(this);
    JUtils.setDebug(true, "xlmm");
    //CrashWoodpecker.fly(false).to(this);
    AutoLayoutConifg.getInstance().useDeviceSize();
    //CustomActivityOnCrash.install(this);
  }

  //初始化OkHttpClient
  private void initOkHttpClient() {
    OkHttpClient httpClient = OkHttpClientManager.getInstance().getOkHttpClient();
    httpClient.setConnectTimeout(10 * 1000, TimeUnit.MILLISECONDS);
    httpClient.setWriteTimeout(30 * 1000, TimeUnit.MILLISECONDS);
    httpClient.setWriteTimeout(30 * 1000, TimeUnit.MILLISECONDS);
    httpClient.setCookieHandler(
        new CookieManager(new PersistentCookieStore(getApplicationContext()),
            CookiePolicy.ACCEPT_ALL));
  }
}
