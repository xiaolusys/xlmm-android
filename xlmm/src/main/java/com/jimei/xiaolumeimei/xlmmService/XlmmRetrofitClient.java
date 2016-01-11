package com.jimei.xiaolumeimei.xlmmService;

import com.jimei.xiaolumeimei.okhttp.OkHttpClientManager;
import com.squareup.okhttp.OkHttpClient;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by itxuye(www.itxuye.com) on 15/12/29.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class XlmmRetrofitClient {

  private static XlmmService mService;

  public static XlmmService getService() {
    if (mService == null) {
      createService();
    }
    return mService;
  }

  private static void createService() {
    mService = createAdapter().create(XlmmService.class);
  }

  private static Retrofit createAdapter() {
    OkHttpClient httpClient = OkHttpClientManager.getInstance().getOkHttpClient();
    httpClient.setConnectTimeout(10 * 1000, TimeUnit.MILLISECONDS);
    httpClient.setWriteTimeout(30 * 1000, TimeUnit.MILLISECONDS);
    httpClient.setWriteTimeout(30 * 1000, TimeUnit.MILLISECONDS);
    httpClient.setCookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ALL));

    return new Retrofit.Builder().baseUrl("http://api.xiaolumeimei.com/rest/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .client(httpClient)
        .build();
  }
}
