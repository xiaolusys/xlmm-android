package com.jimei.xiaolumeimei.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.CodeBean;
import com.jimei.xiaolumeimei.model.UserModel;
import com.jimei.xiaolumeimei.ui.xlmmmain.MainActivity;
import com.jimei.xiaolumeimei.utils.LoginUtils;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.umeng.analytics.MobclickAgent;

import butterknife.Bind;
import rx.schedulers.Schedulers;

public class EditPasswordActivity extends BaseSwipeBackCompatActivity
    implements View.OnClickListener {
  String TAG = "SettingPasswordActivity";
  @Bind(R.id.toolbar) Toolbar toolbar;
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
  }

  @Override protected void getBundleExtras(Bundle extras) {
    username = extras.getString("username");
    valid_code = extras.getString("valid_code");
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

  private void changePassword(String username, String valid_code, String password1,
      String password2) {
    UserModel.getInstance()
        .reset_password(username, password1, password2, valid_code)
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<CodeBean>() {
          @Override public void onNext(CodeBean codeBean) {
            Log.d(TAG,
                "user.getCode() " + codeBean.getRcode() + ", user.getResult() " + codeBean
                    .getMsg());

            if (codeBean.getRcode() == 0) {
              UserModel.getInstance()
                  .passwordlogin(username, password1, null)
                  .subscribeOn(Schedulers.io())
                  .subscribe(new ServiceResponse<CodeBean>() {
                    @Override public void onNext(CodeBean codeBean1) {
                      Log.d(TAG, "user.getCode() "
                          + codeBean1.getRcode()
                          + ", user.getResult() "
                          + codeBean1.getMsg());
                      if (codeBean1.getRcode() == 0) {
                        LoginUtils.saveLoginInfo(true, getApplicationContext(), username,
                            password1);
                        JUtils.Toast("注册成功,已经登录");
                        Intent intent = new Intent(mContext, MainActivity.class);
                        startActivity(intent);
                        finish();
                      } else {
                        JUtils.Toast(codeBean1.getMsg());
                      }
                    }
                  });
            } else {
              JUtils.Toast("修改失败");
            }
          }

          @Override public void onCompleted() {
            super.onCompleted();
          }
        });
  }

  private boolean checkInputSame(String pass1, String pass2) {
    if (!pass1.equals(pass2)) {
      JUtils.Toast("两次密码不一致");
      return false;
    }

    return true;
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
