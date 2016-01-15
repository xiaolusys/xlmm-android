package com.jimei.xiaolumeimei.xlmmService;

import android.content.Context;
import com.jimei.xiaolumeimei.XlmmApp;
import com.jimei.xiaolumeimei.okhttp.PersistentCookieStore;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
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

    Interceptor interceptor = chain -> {

      Request request = chain.request()
          .newBuilder()
          .addHeader("Content-Type", "application/json;charset=utf-8")
          .build();

      return chain.proceed(request);
    };

    OkHttpClient client = new OkHttpClient(); //create OKHTTPClient
    //create a cookieManager so your client can be cookie persistant
    client.setConnectTimeout(10 * 1000, TimeUnit.MILLISECONDS);
    client.setWriteTimeout(30 * 1000, TimeUnit.MILLISECONDS);
    client.setWriteTimeout(30 * 1000, TimeUnit.MILLISECONDS);
    client.interceptors().add(interceptor);
    client.setCookieHandler(
        new CookieManager(new PersistentCookieStore(getApplicationContext()),
            CookiePolicy.ACCEPT_ALL));

    return new Retrofit.Builder().baseUrl("http://api.xiaolumeimei.com/rest/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .client(client)
        .build();
  }

  private static Context getApplicationContext() {
    return XlmmApp.getInstance();
  }
}
