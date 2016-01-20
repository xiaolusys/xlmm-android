package com.jimei.xiaolumeimei.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.RegisterBean;
import com.jimei.xiaolumeimei.model.UserModel;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import rx.schedulers.Schedulers;

public class RegisterActivity extends BaseSwipeBackCompatActivity
    implements View.OnClickListener {

  @Bind(R.id.register_name) EditText editTextMobile;
  @Bind(R.id.register_password) EditText editTextInvalid_code;
  @Bind(R.id.register_button) Button register_button;
  UserModel model = new UserModel();
  @Bind(R.id.getCheckCode) Button getCheckCode;
  private String mobile, invalid_code;

  @Override protected void setListener() {
    getCheckCode.setOnClickListener(this);
    register_button.setOnClickListener(this);
  }

  @Override protected void initData() {

  }

  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.register_activity;
  }

  @Override protected void initViews() {

  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // TODO: add setContentView(...) invocation
    ButterKnife.bind(this);
  }

  public void checkInput() {
    if (mobile.length() != 11) {
      JUtils.Toast("请输入正确的手机号");
      return;
    }
  }

  @Override public void onClick(View v) {
    switch (v.getId()) {
      case R.id.getCheckCode:
        mobile = editTextMobile.getText().toString().trim();
        checkInput();
        model.getRegisterCheckCode(mobile)
            .subscribeOn(Schedulers.io())
            .subscribe(new ServiceResponse<RegisterBean>() {
              @Override public void onNext(RegisterBean registerBean) {
                super.onNext(registerBean);
                String result = registerBean.getResult();
                if (result.equals("0")) {
                  JUtils.Toast("已经注册过该手机号");
                } else if (result.equals("7")) {
                  return;
                }
              }
            });
        break;
      case R.id.register_button:
        mobile = editTextMobile.getText().toString().trim();
        invalid_code = editTextInvalid_code.getText().toString().trim();
        model.check_code_user(mobile, invalid_code)
            .subscribeOn(Schedulers.io())
            .subscribe(new ServiceResponse<RegisterBean>() {
              @Override public void onNext(RegisterBean registerBean) {
                super.onNext(registerBean);
                String result = registerBean.getResult();
                if (result.equals("7")) {
                  Intent intent =
                      new Intent(RegisterActivity.this, SettingPasswordActivity.class);
                  Bundle bundle = new Bundle();
                  bundle.putString("username", mobile);
                  bundle.putString("valid_code", invalid_code);

                  intent.putExtras(bundle);
                  startActivity(intent);
                  finish();

                }
              }
            });
        break;
    }
  }
}
