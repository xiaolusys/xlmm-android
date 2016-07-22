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
import com.jimei.xiaolumeimei.okhttp3.PersistentCookieJar;
import com.jimei.xiaolumeimei.okhttp3.cache.SetCookieCache;
import com.jimei.xiaolumeimei.okhttp3.persistence.SharedPrefsCookiePersistor;
import com.jimei.xiaolumeimei.ui.activity.main.MainActivity;
import com.jimei.xiaolumeimei.utils.LoginUtils;
import com.jimei.xiaolumeimei.utils.NetWorkUtil;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.zhy.autolayout.config.AutoLayoutConifg;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.schedulers.Schedulers;

//import com.squareup.leakcanary.LeakCanary;

/**
 * Created by ye.xu on 15/12/29.
 *
 * Copyright 2015年 上海己美. All rights reser.
 */
public class XlmmApp extends MultiDexApplication {

  public static OkHttpClient client;
  private static Context mContext;
  private static XiaoMiMessageReceiver.XiaoMiPushHandler handler = null;
  private SharedPreferences cookiePrefs;

  public static Context getmContext() {
    return mContext;
  }

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
    mContext = getApplicationContext();
    cookiePrefs = getSharedPreferences("xlmmCookiesAxiba", 0);
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

    return new OkHttpClient.Builder().readTimeout(60000, TimeUnit.MILLISECONDS)
        .connectTimeout(60000, TimeUnit.MILLISECONDS)
        .writeTimeout(6000, TimeUnit.MILLISECONDS)
        .addInterceptor(
            new UserAgentInterceptor("Android " + String.valueOf(BuildConfig.VERSION_CODE)))
        .cookieJar(
            new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(mContext)))
        .build();
  }

  @Override protected void attachBaseContext(Context base) {
    super.attachBaseContext(base);
    MultiDex.install(this);
  }

  private boolean shouldInit() {
    ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
    List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
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

  /* This interceptor adds a custom User-Agent. */
  public class UserAgentInterceptor implements Interceptor {

    private final String userAgent;

    public UserAgentInterceptor(String userAgent) {
      this.userAgent = userAgent;
    }

    @Override public Response intercept(Chain chain) throws IOException {
      Request originalRequest = chain.request();
      Request requestWithUserAgent =
          originalRequest.newBuilder().header("User-Agent", userAgent).build();
      return chain.proceed(requestWithUserAgent);
    }
  }
}
