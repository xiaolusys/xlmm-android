package com.jimei.xiaolumeimei.xlmmService;

import com.jimei.xiaolumeimei.XlmmApp;
import com.jimei.xiaolumeimei.data.XlmmApi;
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

    //Interceptor interceptor = chain -> {
    //
    //  Request request = chain.request()
    //      .newBuilder()
    //      .addHeader("Content-Type", "application/json;charset=utf-8")
    //      .build();
    //
    //  return chain.proceed(request);
    //};

    //OkHttpClient client = new OkHttpClient(); //create OKHTTPClient
    ////create a cookieManager so your client can be cookie persistant
    //client.setConnectTimeout(10 * 1000, TimeUnit.MILLISECONDS);
    //client.setWriteTimeout(30 * 1000, TimeUnit.MILLISECONDS);
    //client.setWriteTimeout(30 * 1000, TimeUnit.MILLISECONDS);
    //client.interceptors().add(new ReceivedCookiesInterceptor());
    //client.setCookieHandler(
    //    new CookieManager(new PersistentCookieStore(getApplicationContext()),
    //        CookiePolicy.ACCEPT_ALL));

    //XlmmApp.client.interceptors().add(new ReceivedCookiesInterceptor());

    return new Retrofit.Builder().baseUrl(XlmmApi.URL_BASE)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .client(XlmmApp.client)
        .build();
  }
  //
  //private static Context getApplicationContext() {
  //  return XlmmApp.getInstance();
  //}

  //static class ReceivedCookiesInterceptor implements Interceptor {
  //  @Override public Response intercept(Chain chain) throws IOException {
  //    Response originalResponse = chain.proceed(chain.request());
  //    List<String> cookieList = originalResponse.headers("Set-Cookie");
  //    if (cookieList != null) {
  //      JUtils.Log("XLMMAPP", cookieList.get(0));
  //      for (String s : cookieList) {//Cookie的格式为:cookieName=cookieValue;path=xxx
  //        //保存你需要的cookie数据
  //        JUtils.Log("XLMMAPP", s);
  //      }
  //    }
  //    return originalResponse;
  //  }
  //}
}
