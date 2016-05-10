package com.jimei.xiaolumeimei.ui.activity.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Process;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import butterknife.Bind;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.CodeBean;
import com.jimei.xiaolumeimei.entities.LogOutBean;
import com.jimei.xiaolumeimei.model.UserModel;
import com.jimei.xiaolumeimei.ui.activity.user.LoginActivity;
import com.jimei.xiaolumeimei.utils.LoginUtils;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.xiaomi.mipush.sdk.MiPushClient;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye on 16/5/9.
 */
public class DebugActivity extends BaseSwipeBackCompatActivity
    implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

  @Bind(R.id.edit_debug) EditText editDebug;
  @Bind(R.id.bt_debug) Button btDebug;

  SharedPreferences sharedPreferences;
  String textDebug;
  String textPass;
  @Bind(R.id.edit_pass) EditText editPass;
  @Bind(R.id.staging) RadioButton staging;
  @Bind(R.id.m) RadioButton m;
  @Bind(R.id.xiuqing) RadioButton xiuqing;
  @Bind(R.id.huang) RadioButton huang;
  @Bind(R.id.lin) RadioButton lin;
  @Bind(R.id.lei) RadioButton lei;
  @Bind(R.id.enjun) RadioButton enjun;
  @Bind(R.id.rg) RadioGroup rg;

  @Override protected void setListener() {

    btDebug.setOnClickListener(this);
    rg.setOnCheckedChangeListener(this);
  }

  @Override protected void initData() {

    sharedPreferences = getSharedPreferences("APICLIENT", MODE_PRIVATE);
  }

  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.activity_debug;
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
    if (v.getId() == R.id.bt_debug) {
      if (TextUtils.isEmpty(editDebug.getText())) {
        JUtils.Toast("请输入API地址");
        return;
      }

      if (TextUtils.isEmpty(editPass.getText())) {
        JUtils.Toast("请输入密码");
        return;
      }

      textDebug = editDebug.getText().toString().trim();
      textPass = editPass.getText().toString().trim();

      UserModel.getInstance()
          .openDebug(textPass)
          .subscribeOn(Schedulers.io())
          .subscribe(new ServiceResponse<CodeBean>() {
            @Override public void onNext(CodeBean codeBean) {
              if (null != codeBean) {
                if (codeBean.getRcode() == 0) {
                  SharedPreferences.Editor editor = sharedPreferences.edit();
                  editor.putString("BASE_URL", textDebug);
                  boolean isPut = editor.commit();
                  if (isPut) {

                    final String finalAccount =
                        LoginUtils.getUserAccount(getApplicationContext());
                    UserModel.getInstance()
                        .customer_logout()
                        .subscribeOn(Schedulers.io())
                        .subscribe(new ServiceResponse<LogOutBean>() {
                          @Override public void onNext(LogOutBean responseBody) {
                            super.onNext(responseBody);
                            if (responseBody.getCode() == 0) {
                              JUtils.Toast("成功进入debug模式");
                              if ((finalAccount != null) && ((!finalAccount.isEmpty()))) {
                                MiPushClient.unsetUserAccount(getApplicationContext(),
                                    finalAccount, null);
                              }

                              LoginUtils.delLoginInfo(getApplicationContext());

                              Intent intent =
                                  new Intent(DebugActivity.this, LoginActivity.class);
                              intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                              startActivity(intent);
                              Process.killProcess(Process.myPid());
                              System.exit(1);
                            }
                          }
                        });
                  }
                }

                JUtils.Toast(codeBean.getMsg());
              }
            }
          });
    }
  }

  @Override public void onCheckedChanged(RadioGroup group, int checkedId) {
    switch (checkedId) {
      case R.id.staging:
        editDebug.setText(staging.getText().toString().trim());
        break;

      case R.id.m:
        editDebug.setText(m.getText().toString().trim());
        break;

      case R.id.xiuqing:
        editDebug.setText(xiuqing.getText().toString().trim());
        break;

      case R.id.huang:
        editDebug.setText(huang.getText().toString().trim());
        break;

      case R.id.lin:
        editDebug.setText(lin.getText().toString().trim());
        break;

      case R.id.lei:
        editDebug.setText(lei.getText().toString().trim());
        break;

      case R.id.enjun:
        editDebug.setText(enjun.getText().toString().trim());
        break;
    }
  }
}
