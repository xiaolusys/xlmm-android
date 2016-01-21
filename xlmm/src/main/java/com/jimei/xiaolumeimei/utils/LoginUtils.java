package com.jimei.xiaolumeimei.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import com.jimei.xiaolumeimei.data.XlmmApi;
import com.jimei.xiaolumeimei.entities.UserBean;
import com.jimei.xiaolumeimei.entities.UserInfoBean;
import com.jimei.xiaolumeimei.model.UserModel;
import com.jimei.xiaolumeimei.okhttp.callback.OkHttpCallback;
import com.jimei.xiaolumeimei.okhttp.request.OkHttpRequest;
import com.jimei.xiaolumeimei.ui.activity.user.LoginActivity;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye(www.itxuye.com) on 15/12/29.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class LoginUtils {
  static String TAG = "LoginUtils";
  static SharedPreferences sharedPreferences;
  static SharedPreferences.Editor editor;
  static UserInfoBean userinfo;

  public static void saveLoginInfo(boolean isSuccess, Context context, String username,
      String password) {
    sharedPreferences = context.getSharedPreferences("login_info", Context.MODE_PRIVATE);
    editor = sharedPreferences.edit();
    editor.putString("name", username);
    editor.putString("password", password);
    editor.putBoolean("success", isSuccess);
    editor.apply();
      Log.d(TAG, "save logininfo "  );
  }

  public static void delLoginInfo(Context context) {
    sharedPreferences = context.getSharedPreferences("login_info", Context.MODE_PRIVATE);
    editor = sharedPreferences.edit();
    editor.clear();
    editor.apply();
      Log.d(TAG, "clear logininfo "  );

  }

  //登录
  public static void doLogin(String name, String password) {
    new OkHttpRequest.Builder().url(XlmmApi.LOGIN_URL)
        .addParams("username", name)
        .addParams("password", password)
        .post(new OkHttpCallback<UserBean>() {

          @Override public void onError(Request request, Exception e) {

          }

          @Override public void onResponse(Response response, UserBean data) {
            if (data.getCode() == 0 && data.getResult().equals("login")) {
              //Toast.makeText(context, "登录成功", Toast.LENGTH_SHORT).show();
              Log.i("Login", "登录成功");
            } else {

            }
          }
        });
  }

  //获取用户信息
  public static String[] getLoginInfo(Context context) {

    sharedPreferences = context.getSharedPreferences("login_info", Context.MODE_PRIVATE);

    String username = sharedPreferences.getString("username", "");
    String password = sharedPreferences.getString("password", "");
    boolean success = sharedPreferences.getBoolean("success", false);

    return new String[] { username, password, success + "" };
  }

  //获取用户登录状态
  public static boolean checkLoginState(Context context) {

    sharedPreferences = context.getSharedPreferences("login_info", Context.MODE_PRIVATE);

    boolean success = sharedPreferences.getBoolean("success", false);

    return success;
  }

  //获取用户登录状态
  public static void login(Context context) {
    if(checkLoginState(context)){
      return;
    }
    else{
      //需要跳转到登录界面
      Intent intent = new Intent(context, LoginActivity.class);
      Log.d(TAG, "have not logined, "   + "jump to LoginActivity");
      context.startActivity(intent);
    }
  }

}
