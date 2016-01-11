package com.jimei.xiaolumeimei.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.Bind;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.data.XlmmApi;
import com.jimei.xiaolumeimei.entities.UserBean;
import com.jimei.xiaolumeimei.okhttp.callback.OkHttpCallback;
import com.jimei.xiaolumeimei.okhttp.request.OkHttpRequest;
import com.jimei.xiaolumeimei.utils.LoginUtils;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public class LoginActivity extends BaseSwipeBackCompatActivity
    implements View.OnClickListener {
  String login_name_value;//登录名
  String login_pass_value;//登录密码

  //boolean isLogin;//判断是否登录
  @Bind(R.id.set_login_name) EditText nameEditText;
  @Bind(R.id.set_login_password) EditText passEditText;
  @Bind(R.id.set_login_button) Button login_button;
  @Bind(R.id.set_register_button) Button set_register;

  @Bind(R.id.toolbar) Toolbar toolbar;

  String TAG = "LoginActivity";

  private SharedPreferences sharedPreferences;
  private SharedPreferences.Editor editor;

  @Override protected void setListener() {
    login_button.setOnClickListener(this);

    set_register.setOnClickListener(this);
  }

  @Override protected void initData() {

    sharedPreferences =
        getApplicationContext().getSharedPreferences("login_info", Context.MODE_PRIVATE);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      finish();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.setting_login_activity;
  }

  @Override protected void initViews() {
    toolbar.setTitle("");
    setSupportActionBar(toolbar);
  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }

  //save user information

  @Override public void onClick(View v) {
    switch (v.getId()) {
      case R.id.set_login_button:
        MaterialDialog dialog = new MaterialDialog.Builder(this).content("正在登录......")
            .theme(Theme.LIGHT)
            .build();

        login_name_value = nameEditText.getText().toString().trim();
        login_pass_value = passEditText.getText().toString().trim();
        new OkHttpRequest.Builder().url(XlmmApi.LOGIN_URL)
            .addParams("username", login_name_value)
            .addParams("password", login_pass_value)
            .post(new OkHttpCallback<UserBean>() {

              @Override public void onBefore(Request request) {
                super.onBefore(request);
                dialog.show();
              }

              @Override public void onProgress(float progress) {
                super.onProgress(progress);
              }

              @Override public void onError(Request request, Exception e) {
              }

              @Override public void onResponse(Response response, UserBean data) {
                Log.i(TAG, data.toString());
                Headers headers = response.headers();
                //String s = headers.get("asaa");
                //Log.i("我来测试的", s);
                for (int i = 0; i < headers.size(); i++) {
                  Log.i("我来测试的", headers.name(i) + ": " + headers.value(i));
                }
                if (data.getCode() == 0 && data.getResult().equals("login")) {

                  LoginUtils.saveLoginInfo(true, getApplicationContext(),
                      login_name_value, login_pass_value);

                  Toast.makeText(mContext, "登录成功", Toast.LENGTH_SHORT).show();
                  Intent intent = new Intent(mContext, MainActivity.class);
                  startActivity(intent);
                  dialog.dismiss();
                  finish();
                } else {

                  LoginUtils.saveLoginInfo(false, getApplicationContext(), "", "");

                  dialog.dismiss();
                  Toast.makeText(mContext, "用户名或者密码错误,请检查", Toast.LENGTH_SHORT).show();
                }
              }
            });

        break;
      case R.id.set_register_button:
        Intent intent = new Intent(mContext, SettingRegisterActivity.class);
        startActivity(intent);
        finish();

        break;
    }
  }
}

