package com.jimei.xiaolumeimei;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.entities.UserInfoBean;
import com.jimei.xiaolumeimei.mipush.XiaoMiMessageReceiver;
import com.jimei.xiaolumeimei.model.UserModel;
import com.jimei.xiaolumeimei.okhttp.PersistentCookieStore;
import com.jimei.xiaolumeimei.ui.activity.main.MainActivity;
import com.jimei.xiaolumeimei.utils.LoginUtils;
import com.jimei.xiaolumeimei.utils.NetWorkUtil;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.squareup.okhttp.OkHttpClient;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.zhy.autolayout.config.AutoLayoutConifg;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.schedulers.Schedulers;

//import com.squareup.leakcanary.LeakCanary;

/**
 * Created by ye.xu on 15/12/29.
 *
 * Copyright 2015年 上海己美. All rights reser.
 */
public class XlmmApp extends MultiDexApplication {

  public static OkHttpClient client;

  public static Context getmContext() {
    return mContext;
  }

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

    //LeakCanary.install(this);
//    Thread.setDefaultUncaughtExceptionHandler(new MyUnCaughtExceptionHandler());

    //OkHttpUtils.getInstance().setConnectTimeout(10 * 1000, TimeUnit.MILLISECONDS);

    mContext = getApplicationContext();
    cookiePrefs = getSharedPreferences("COOKIESxlmm", 0);
    client = initOkHttpClient();
    JUtils.initialize(this);
    JUtils.setDebug(true, "xlmm");
    //CrashWoodpecker.fly(false).to(this);
    //ShareSDK.initSDK(this);
    AutoLayoutConifg.getInstance().useDeviceSize();
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

      UserModel.getInstance()
          .getUserInfo()
          .subscribeOn(Schedulers.io())
          .subscribe(new ServiceResponse<UserInfoBean>() {
            @Override public void onNext(UserInfoBean user) {
              Log.d("XlmmApp", "getUserInfo: " + user.toString());
            }

            @Override public void onCompleted() {
              super.onCompleted();
            }

            @Override public void onError(Throwable e) {
              LoginUtils.delLoginInfo(mContext);
              e.printStackTrace();
              Log.e("XlmmApp", "error getUserInfo");
              super.onError(e);
            }
          });
    }
  }

  //初始化OkHttpClient
  private OkHttpClient initOkHttpClient() {

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

//    httpClient.interceptors().add(new Interceptor() {
//      @Override public Response intercept(Chain chain) throws IOException {
//        Response response = null;
//        try {
//          Request request = chain.request();
//          response = chain.proceed(request);
//          int code = response.code();//status code
//          if (403 == code) {
//
//          }
//        } catch (IOException e) {
//          e.printStackTrace();
//        }
//        return response;
//      }
//    });

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

  //异常退出的时候,自动重启
  class MyUnCaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    @Override public void uncaughtException(Thread thread, Throwable ex) {
      ex.printStackTrace();
      Intent intent = new Intent(XlmmApp.this, MainActivity.class);
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      XlmmApp.this.startActivity(intent);
      android.os.Process.killProcess(android.os.Process.myPid());
      System.exit(1);
    }
  }
}
