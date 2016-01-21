package com.jimei.xiaolumeimei;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.jimei.xiaolumeimei.entities.UserInfoBean;
import com.jimei.xiaolumeimei.model.UserModel;
import com.jimei.xiaolumeimei.okhttp.OkHttpClientManager;
import com.jimei.xiaolumeimei.okhttp.PersistentCookieStore;
import com.jimei.xiaolumeimei.utils.LoginUtils;
import com.jimei.xiaolumeimei.utils.NetWorkUtil;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.squareup.okhttp.OkHttpClient;
import com.zhy.autolayout.config.AutoLayoutConifg;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;

import rx.schedulers.Schedulers;

/**
 * Created by ye.xu on 15/12/29.
 *
 * Copyright 2015年 上海己美. All rights reser.
 */
public class XlmmApp extends Application {

  private static Context mContext;
  public static OkHttpClient client;

  public static Context getInstance() {
    return mContext;
  }

  @Override public void onCreate() {
    super.onCreate();
    mContext = this;

    client = initOkHttpClient();
    JUtils.initialize(this);
    JUtils.setDebug(true, "xlmm");
    //CrashWoodpecker.fly(false).to(this);
    AutoLayoutConifg.getInstance().useDeviceSize();
    //CustomActivityOnCrash.install(this);

    //获取用户信息失败，说明要重新登陆
    if(NetWorkUtil.isNetWorkConnected(this)){
      UserModel model = new UserModel();
      model.getUserInfo()
              .subscribeOn(Schedulers.newThread())
              .subscribe(new ServiceResponse<UserInfoBean>() {
                @Override
                public void onNext(UserInfoBean user) {
                  Log.d("XlmmApp", "getUserInfo:, "   + user.getResults().get(0).toString());
                }

                @Override
                public void onCompleted() {
                  super.onCompleted();
                }

                @Override
                public void onError(Throwable e) {
                  LoginUtils.delLoginInfo(mContext);
                  Log.e("XlmmApp", "error:, "   + e.toString());
                  super.onError(e);
                }
              });


    }
  }

  //初始化OkHttpClient
  private OkHttpClient initOkHttpClient() {

    //okhttp3.OkHttpClient okHttpClient = OkHttpUtils.getInstance().getOkHttpClient();

    OkHttpClient httpClient = OkHttpClientManager.getInstance().getOkHttpClient();
    httpClient.setConnectTimeout(10 * 1000, TimeUnit.MILLISECONDS);
    httpClient.setWriteTimeout(30 * 1000, TimeUnit.MILLISECONDS);
    httpClient.setWriteTimeout(30 * 1000, TimeUnit.MILLISECONDS);
    httpClient.setCookieHandler(
        new CookieManager(new PersistentCookieStore(getApplicationContext()),
            CookiePolicy.ACCEPT_ALL));
    return httpClient;
  }
}
