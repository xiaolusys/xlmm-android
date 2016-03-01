package com.jimei.xiaolumeimei;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.multidex.MultiDex;
import android.util.Log;
import cn.sharesdk.framework.ShareSDK;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.entities.UserInfoBean;
import com.jimei.xiaolumeimei.mipush.XiaoMiMessageReceiver;
import com.jimei.xiaolumeimei.model.UserModel;
import com.jimei.xiaolumeimei.okhttp.PersistentCookieStore;
import com.jimei.xiaolumeimei.utils.LoginUtils;
import com.jimei.xiaolumeimei.utils.NetWorkUtil;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.tencent.bugly.crashreport.CrashReport;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.zhy.autolayout.config.AutoLayoutConifg;
import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.List;
import java.util.concurrent.TimeUnit;
import rx.schedulers.Schedulers;

/**
 * Created by ye.xu on 15/12/29.
 *
 * Copyright 2015年 上海己美. All rights reser.
 */
public class XlmmApp extends Application {

  public static OkHttpClient client;
  private static Context mContext;
  private static XiaoMiMessageReceiver.XiaoMiPushHandler handler = null;
  private SharedPreferences cookiePrefs;

  public static Context getInstance() {
    return mContext;
  }

  public static XiaoMiMessageReceiver.XiaoMiPushHandler getHandler() {
    return handler;
  }

  @Override public void onCreate() {
    super.onCreate();
    LeakCanary.install(this);
    CrashReport.initCrashReport(getApplicationContext(),"900020489", false);
    mContext = this;
    cookiePrefs = getSharedPreferences("COOKIESxlmm", 0);
    client = initOkHttpClient();
    //receivedCookiesInterceptor =
    //   new ReceivedCookiesInterceptor();
    JUtils.initialize(this);
    JUtils.setDebug(true, "xlmm");
    //CrashWoodpecker.fly(false).to(this);
    ShareSDK.initSDK(this);
    AutoLayoutConifg.getInstance().useDeviceSize();
    //CustomActivityOnCrash.install(this);
    //Stetho.initializeWithDefaults(this);
    //初始化push推送服务
    if (shouldInit()) {
      JUtils.Log("XlmmApp", "reg xiaomi push");
      MiPushClient.registerPush(getApplicationContext(), XlmmConst.XIAOMI_APP_ID,
          XlmmConst.XIAOMI_APP_KEY);
    }

    if (handler == null) {
      handler = new XiaoMiMessageReceiver.XiaoMiPushHandler(getApplicationContext());
    }

    //获取用户信息失败，说明要重新登陆
    if (NetWorkUtil.isNetWorkConnected(this)) {

      UserModel.getInstance().getUserInfo()
          .subscribeOn(Schedulers.newThread())
          .subscribe(new ServiceResponse<UserInfoBean>() {
            @Override public void onNext(UserInfoBean user) {
              Log.d("XlmmApp", "getUserInfo:, " + user.toString());
            }

            @Override public void onCompleted() {
              super.onCompleted();
            }

            @Override public void onError(Throwable e) {
              LoginUtils.delLoginInfo(mContext);
              Log.e("XlmmApp", "error:, " + e.toString());
              super.onError(e);
            }
          });
    }
  }

  //初始化OkHttpClient
  private OkHttpClient initOkHttpClient() {

    //OkHttpClient httpClient = OkHttpClientManager.getInstance().getOkHttpClient();
    //httpClient.setConnectTimeout(10 * 1000, TimeUnit.MILLISECONDS);
    //httpClient.setWriteTimeout(30 * 1000, TimeUnit.MILLISECONDS);
    //httpClient.setWriteTimeout(30 * 1000, TimeUnit.MILLISECONDS);
    //
    //httpClient.setCookieHandler(
    //    new CookieManager(new PersistentCookieStore(getApplicationContext()),
    //        CookiePolicy.ACCEPT_ALL));
    //httpClient.interceptors().add(receivedCookiesInterceptor);

    CookieManager cookieManager =
        new CookieManager(new PersistentCookieStore(getApplicationContext()),
            CookiePolicy.ACCEPT_ALL);
    OkHttpClient httpClient = new OkHttpClient(); //create OKHTTPClient
    //create a cookieManager so your client can be cookie persistant
    httpClient.setConnectTimeout(10 * 1000, TimeUnit.MILLISECONDS);
    httpClient.setWriteTimeout(30 * 1000, TimeUnit.MILLISECONDS);
    httpClient.setWriteTimeout(30 * 1000, TimeUnit.MILLISECONDS);
    //httpClient.interceptors().add(new Interceptor() {
    //  @Override public Response intercept(Chain chain) throws IOException {
    //    Request request = chain.request()
    //        .newBuilder()
    //        .addHeader("Content-Type", "application/json;charset=utf-8")
    //        .build();
    //
    //    Response originalResponse = chain.proceed(request);
    //
    //    List<String> cookieList = originalResponse.headers("Set-Cookie");
    //    if (cookieList != null) {
    //      //JUtils.Log("XLMMAPP", cookieList.get(0));
    //      for (String s : cookieList) {//Cookie的格式为:cookieName=cookieValue;path=xxx
    //        //保存你需要的cookie数据
    //        JUtils.Log("XLMMAPP", cookieList.get(0));
    //        editor.putString("Cookies", s);
    //        editor.apply();
    //      }
    //    }
    //    return originalResponse;
    //  }
    //});

    //httpClient.networkInterceptors().add(new StethoInterceptor());

    httpClient.interceptors().add(new Interceptor() {
      @Override public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        int code = response.code();//status code
        if (403 == code) {

        }
        return response;
      }
    });

    httpClient.setCookieHandler(cookieManager);
    return httpClient;
  }

  @Override protected void attachBaseContext(Context base) {
    super.attachBaseContext(base);
    MultiDex.install(this);
  }

  private boolean shouldInit() {
    ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
    List<ActivityManager.RunningAppProcessInfo> processInfos =
        am.getRunningAppProcesses();
    String mainProcessName = getPackageName();
    int myPid = android.os.Process.myPid();
    for (ActivityManager.RunningAppProcessInfo info : processInfos) {
      if (info.pid == myPid && mainProcessName.equals(info.processName)) {
        return true;
      }
    }
    return false;
  }
}
