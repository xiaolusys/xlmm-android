package com.jimei.xiaolumeimei.xlmmService;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.jimei.xiaolumeimei.BuildConfig;
import com.jimei.xiaolumeimei.XlmmApp;
import com.jimei.xiaolumeimei.data.XlmmApi;
import com.jimei.xiaolumeimei.okhttp3.PersistentCookieJar;
import com.jimei.xiaolumeimei.okhttp3.cache.SetCookieCache;
import com.jimei.xiaolumeimei.okhttp3.persistence.SharedPrefsCookiePersistor;
import com.jimei.xiaolumeimei.utils.NetUtil;
import com.jude.utils.JUtils;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by itxuye(www.itxuye.com) on 15/12/29.
 * <p>
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class XlmmRetrofitClient {

  private static XlmmService mService;
  static SharedPreferences sharedPreferences;
  private static OkHttpClient mOkHttpClient;

  static {
    initOkHttpClient();
  }

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

    sharedPreferences =
        XlmmApp.getmContext().getSharedPreferences("APICLIENT", Context.MODE_PRIVATE);
    if (!TextUtils.isEmpty(sharedPreferences.getString("BASE_URL", ""))) {

      String baseUrl = "http://" + sharedPreferences.getString("BASE_URL", "");

      JUtils.Log("InformationActivity", "baseurl====" + baseUrl);
      return new Retrofit.Builder().baseUrl(baseUrl)
          .addConverterFactory(GsonConverterFactory.create())
          .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
          .client(mOkHttpClient)
          .build();
    }

    JUtils.Log("InformationActivity", "baseurl====");
    return new Retrofit.Builder().baseUrl(XlmmApi.APP_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .client(mOkHttpClient)
        .build();
  }

  /**
   * 初始化OKHttpClient
   */
  private static void initOkHttpClient() {

    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    if (mOkHttpClient == null) {
      synchronized (XlmmRetrofitClient.class) {
        if (mOkHttpClient == null) {
          //设置Http缓存
          Cache cache = new Cache(new File(XlmmApp.getmContext().getCacheDir(), "OkHttpCache"),
              1024 * 1024 * 100);

          mOkHttpClient = new OkHttpClient.Builder().readTimeout(60000, TimeUnit.MILLISECONDS)
              .connectTimeout(60000, TimeUnit.MILLISECONDS)
              .writeTimeout(6000, TimeUnit.MILLISECONDS)
              .retryOnConnectionFailure(true)
              .cache(cache)
              .addInterceptor(interceptor)
              .addInterceptor(new Interceptor() {
                @Override public Response intercept(Chain chain) throws IOException {
                  Request originalRequest = chain.request();
                  Request requestWithUserAgent = originalRequest.newBuilder()
                      .header("User-Agent", "Android/"
                          + android.os.Build.VERSION.RELEASE
                          + " xlmmApp/"
                          + String.valueOf(BuildConfig.VERSION_CODE)
                          + " Mobile/"
                          + android.os.Build.MODEL
                          + " NetType/"
                          + NetUtil.getNetType(XlmmApp.getmContext()))
                      .build();

                  return chain.proceed(requestWithUserAgent);
                }
              })
              //.addNetworkInterceptor(new StethoInterceptor())
              .cookieJar(new PersistentCookieJar(new SetCookieCache(),
                  new SharedPrefsCookiePersistor(XlmmApp.getmContext())))
              .build();
        }
      }
    }
  }
}
