package com.jimei.xiaolumeimei.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.Bind;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.UserBean;
import com.jimei.xiaolumeimei.model.UserModel;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import rx.schedulers.Schedulers;

public class SettingPasswordActivity extends BaseSwipeBackCompatActivity
    implements View.OnClickListener {
  String TAG = "SettingPasswordActivity";
  @Bind(R.id.toolbar) Toolbar toolbar;
  UserModel model = new UserModel();
  @Bind(R.id.set_password) EditText etPassword;
  @Bind(R.id.set_password2) EditText etPassword2;
  @Bind(R.id.set_commit_button) Button commit_button;
  @Bind(R.id.check_agree) AppCompatCheckBox checkBox;
  String username;
  String valid_code;
  private boolean isShow = true;

  @Override protected void setListener() {
    commit_button.setOnClickListener(this);
    toolbar.setOnClickListener(this);
  }

  @Override protected void initData() {
    username = getIntent().getExtras().getString("username");
    valid_code = getIntent().getExtras().getString("valid_code");
  }

  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.setting_password_activity;
  }

  @Override protected void initViews() {

    toolbar.setTitle("");
    setSupportActionBar(toolbar);
    finishBack(toolbar);
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

        if (checkBox.isChecked()) {
          if (checkInput(password1) && checkInput(password2)) {

            if (checkInputSame(password1, password2)) {
              changePassword(username, valid_code, password1, password2);
            }
          } else {
            Toast.makeText(mContext, "密码长度或者字符错误,请检查", Toast.LENGTH_SHORT).show();
          }
        } else {
          JUtils.Toast("请选择是否同意条款");
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
    model.changePassword(username, valid_code, password1, password2)
        .subscribeOn(Schedulers.newThread())
        .subscribe(new ServiceResponse<UserBean>() {
          @Override public void onNext(UserBean user) {
            Log.d(TAG, "user.getCode() "
                + user.getCode()
                + ", user.getResult() "
                + user.getResult());

            if (user.getCode() == 0) {
              Toast.makeText(mContext, "修改成功", Toast.LENGTH_SHORT).show();
              Intent intent = new Intent(mContext, SettingActivity.class);
              startActivity(intent);
              finish();
            } else {

              Toast.makeText(mContext, "修改失败", Toast.LENGTH_SHORT).show();
            }
          }

          @Override public void onCompleted() {
            super.onCompleted();
          }
        });
  }
}
