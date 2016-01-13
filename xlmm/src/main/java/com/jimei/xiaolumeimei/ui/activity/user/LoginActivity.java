package com.jimei.xiaolumeimei.ui.activity.user;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.Bind;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.UserBean;
import com.jimei.xiaolumeimei.model.UserModel;
import com.jimei.xiaolumeimei.ui.activity.MainActivity;
import com.jimei.xiaolumeimei.utils.LoginUtils;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import rx.schedulers.Schedulers;

public class LoginActivity extends BaseSwipeBackCompatActivity
    implements View.OnClickListener {
  String login_name_value;//登录名
  String login_pass_value;//登录密码

  //boolean isLogin;//判断是否登录
  @Bind(R.id.set_login_name) EditText nameEditText;
  @Bind(R.id.set_login_password) EditText passEditText;
  @Bind(R.id.set_login_button) Button login_button;
  @Bind(R.id.set_register_button) Button set_register;
  UserModel model = new UserModel();
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

        login_name_value = nameEditText.getText().toString().trim();
        login_pass_value = passEditText.getText().toString().trim();

        if (login_name_value.length() != 11) {
          login_button.setClickable(false);
          JUtils.Toast("请输入正确手机号");
          return;
        } else {
          login_button.setClickable(true);
        }

        if (login_pass_value.length() < 6 || login_pass_value.length() > 12) {
          login_button.setClickable(false);
          JUtils.Toast("请输入6-12位密码");
          return;
        } else {
          login_button.setClickable(true);
        }

        model.login(login_name_value, login_pass_value)
            .subscribeOn(Schedulers.newThread())
            .subscribe(new ServiceResponse<UserBean>() {
              @Override public void onNext(UserBean user) {
                if (user.getCode() == 0 && user.getResult().equals("login")) {

                  LoginUtils.saveLoginInfo(true, getApplicationContext(),
                      login_name_value, login_pass_value);

                  Toast.makeText(mContext, "登录成功", Toast.LENGTH_SHORT).show();
                  Intent intent = new Intent(mContext, MainActivity.class);
                  startActivity(intent);
                  finish();
                } else {

                  LoginUtils.saveLoginInfo(false, getApplicationContext(), "", "");

                  Toast.makeText(mContext, "用户名或者密码错误,请检查", Toast.LENGTH_SHORT).show();
                }
              }

              @Override public void onCompleted() {
                super.onCompleted();
              }
            });
        break;
      case R.id.set_register_button:
        Intent intent = new Intent(mContext, RegisterActivity.class);
        startActivity(intent);
        finish();

        break;
    }
  }
}

