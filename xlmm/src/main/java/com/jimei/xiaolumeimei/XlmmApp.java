package com.jimei.xiaolumeimei;

import android.app.Application;
import com.jimei.xiaolumeimei.okhttp.OkHttpClientManager;
import com.squareup.okhttp.OkHttpClient;
import com.zhy.autolayout.config.AutoLayoutConifg;
import java.util.concurrent.TimeUnit;

/**
 * Created by ye.xu on 15/12/29.
 *
 * Copyright 2015年 上海己美. All rights reser.
 */
public class XlmmApp extends Application {

  @Override public void onCreate() {
    super.onCreate();
    initOkHttpClient();
    AutoLayoutConifg.getInstance().useDeviceSize();
  }

  //初始化OkHttpClient
  private void initOkHttpClient() {

    OkHttpClient httpClient = OkHttpClientManager.getInstance().getOkHttpClient();
    httpClient.setConnectTimeout(10 * 1000, TimeUnit.MILLISECONDS);
    httpClient.setWriteTimeout(30 * 1000, TimeUnit.MILLISECONDS);
    httpClient.setWriteTimeout(30 * 1000, TimeUnit.MILLISECONDS);
  }
}
