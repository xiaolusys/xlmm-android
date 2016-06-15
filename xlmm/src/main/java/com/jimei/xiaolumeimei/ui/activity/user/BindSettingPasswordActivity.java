package com.jimei.xiaolumeimei.ui.activity.user;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.BindInfoBean;
import com.jimei.xiaolumeimei.model.UserModel;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.umeng.analytics.MobclickAgent;

import butterknife.Bind;
import rx.schedulers.Schedulers;

public class BindSettingPasswordActivity extends BaseSwipeBackCompatActivity
    implements View.OnClickListener {
  String TAG = "SettingPasswordActivity";
  @Bind(R.id.set_password) EditText etPassword;
  @Bind(R.id.set_password2) EditText etPassword2;
  @Bind(R.id.set_commit_button) Button commit_button;
  String username;
  String valid_code;

  @Override protected void setListener() {
    commit_button.setOnClickListener(this);
  }

  @Override protected void initData() {
  }

  @Override protected void getBundleExtras(Bundle extras) {
    username = extras.getString("username");
    valid_code = extras.getString("valid_code");
  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.setting_password_activity;
  }

  @Override protected void initViews() {
  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }

  @Override public void onClick(View v) {
    switch (v.getId()) {
      case R.id.set_commit_button:

        String password1 = etPassword.getText().toString().trim();
        String password2 = etPassword2.getText().toString().trim();
        Log.d(TAG, "password " + password1 + " " + password2);

        if (checkInput(password1) && checkInput(password2)) {

          if (checkInputSame(password1, password2)) {
            changePassword(username, valid_code, password1, password2);
          }
        } else {
          Toast.makeText(mContext, "密码长度或者字符错误,请检查", Toast.LENGTH_SHORT).show();
        }

        break;
    }
  }

  private boolean checkInput(String name) {
    if (name.length() < 4 || name.length() > 20) {
      JUtils.Toast("请输入6-16位密码");
      return false;
    }

    return true;
  }

  private boolean checkInputSame(String pass1, String pass2) {
    if (!pass1.equals(pass2)) {
      JUtils.Toast("两次密码不一致");
      return false;
    }

    return true;
  }

  private void changePassword(String username, String valid_code, String password1,
      String password2) {

    JUtils.Log(TAG, "username=" + username + " valid_code=" + valid_code);
    UserModel.getInstance().bang_mobile(username, password1, password2, valid_code)
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<BindInfoBean>() {
          @Override public void onNext(BindInfoBean bindInfoBean) {
            JUtils.Log(TAG, bindInfoBean.toString());
            int code = bindInfoBean.getCode();
            JUtils.Toast(bindInfoBean.getInfo());
            if (0 == code) {
              finish();
            } else if (1 == code) {

            }
          }

          @Override public void onCompleted() {
            super.onCompleted();
          }
        });
  }

  @Override
  protected void onResume() {
    super.onResume();
    MobclickAgent.onPageStart(this.getClass().getSimpleName());
    MobclickAgent.onResume(this);
  }

  @Override
  protected void onPause() {
    super.onPause();
    MobclickAgent.onPageEnd(this.getClass().getSimpleName());
    MobclickAgent.onPause(this);
  }
}
