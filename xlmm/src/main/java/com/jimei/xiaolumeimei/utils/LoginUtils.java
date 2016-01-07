package com.jimei.xiaolumeimei.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;
import com.jimei.xiaolumeimei.data.XlmmApi;
import com.jimei.xiaolumeimei.model.UserBean;
import com.jimei.xiaolumeimei.okhttp.callback.OkHttpCallback;
import com.jimei.xiaolumeimei.okhttp.request.OkHttpRequest;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

/**
 * Created by itxuye(www.itxuye.com) on 15/12/29.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class LoginUtils {

  static SharedPreferences sharedPreferences;
  static SharedPreferences.Editor editor;

  public static void saveLoginInfo(boolean isSuccess, Context context, String username,
      String password) {
    sharedPreferences = context.getSharedPreferences("login_info", Context.MODE_PRIVATE);
    editor = sharedPreferences.edit();
    editor.putString("name", username);
    editor.putString("password", password);
    editor.putBoolean("success", isSuccess);
    editor.apply();
  }

  //登录
  public static void doLogin(Context context, String name, String password) {
    new OkHttpRequest.Builder().url(XlmmApi.LOGIN_URL)
        .addParams("username", name)
        .addParams("password", password)
        .post(new OkHttpCallback<UserBean>() {

          @Override public void onError(Request request, Exception e) {

          }

          @Override public void onResponse(Response response, UserBean data) {
            if (data.getCode() == 0 && data.getResult().equals("login")) {
              Toast.makeText(context, "登录成功", Toast.LENGTH_SHORT).show();
              saveLoginInfo(true, context, name, password);
            } else {

              saveLoginInfo(false, context, name, password);
            }
          }
        });
  }

  //获取用户信息
  public static String[] getLoginInfo(Context context) {

    sharedPreferences = context.getSharedPreferences("login_info", Context.MODE_PRIVATE);

    String username = sharedPreferences.getString("username", "");
    String password = sharedPreferences.getString("password", "");

    return new String[] { username, password };
  }
}
