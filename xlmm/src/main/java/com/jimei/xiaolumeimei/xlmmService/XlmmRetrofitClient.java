package com.jimei.xiaolumeimei.xlmmService;

import com.squareup.okhttp.OkHttpClient;
import java.net.CookieManager;
import java.net.CookiePolicy;
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

    OkHttpClient client = new OkHttpClient(); //create OKHTTPClient
    //create a cookieManager so your client can be cookie persistant
    CookieManager cookieManager = new CookieManager();
    cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
    client.setCookieHandler(cookieManager);


    return new Retrofit.Builder().baseUrl("http://api.xiaolumeimei.com/rest/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .client(client)
        .build();
  }
}
