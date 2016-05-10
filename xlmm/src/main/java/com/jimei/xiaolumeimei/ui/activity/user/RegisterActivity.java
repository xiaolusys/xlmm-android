package com.jimei.xiaolumeimei.ui.activity.user;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import butterknife.Bind;
import com.jimei.library.rx.RxCountDown;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.CodeBean;
import com.jimei.xiaolumeimei.model.UserModel;
import com.jimei.xiaolumeimei.ui.activity.main.MainActivity;
import com.jimei.xiaolumeimei.utils.LoginUtils;
import com.jimei.xiaolumeimei.widget.ClearEditText;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

public class RegisterActivity extends BaseSwipeBackCompatActivity
        implements View.OnClickListener {

    @Bind(R.id.register_name)
    ClearEditText editTextMobile;
    @Bind(R.id.register_password)
    EditText editTextInvalid_code;
    @Bind(R.id.register_button)
    Button register_button;
    @Bind(R.id.getCheckCode)
    Button getCheckCode;
    private String mobile, invalid_code;
    private Subscription subscribe;

    @Override
    protected void setListener() {
        getCheckCode.setOnClickListener(this);
        register_button.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.register_activity;
    }

    @Override
    protected void initViews() {
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    public boolean checkMobileInput(String mobile) {

        if (mobile == null || mobile.trim().trim().equals("")) {
            JUtils.Toast("请输入手机号");
        } else {
            if (mobile.length() != 11) {
                JUtils.Toast("请输入正确的手机号");
            } else {
                return true;
            }
        }

        return false;
    }

    public boolean checkInput(String mobile, String checkcode) {

        if (mobile == null || mobile.trim().trim().equals("")) {
            JUtils.Toast("请输入手机号");
        } else {
            if (mobile.length() != 11) {
                JUtils.Toast("请输入正确的手机号");
            } else if (checkcode == null || checkcode.trim().trim().equals("")) {
                JUtils.Toast("验证码不能为空");
            } else {
                return true;
            }
        }

        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.getCheckCode:
                mobile = editTextMobile.getText().toString().trim();
                if (checkMobileInput(mobile)) {

                    RxCountDown.countdown(60).doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                            getCheckCode.setClickable(false);
                            getCheckCode.setBackgroundColor(Color.parseColor("#f3f3f4"));

                            subscribe = UserModel.getInstance()
                                    .getCodeBean(mobile, "register")
                                    .subscribeOn(Schedulers.io())
                                    .subscribe(new ServiceResponse<CodeBean>() {
                                        @Override
                                        public void onNext(CodeBean codeBean) {
                                            JUtils.Toast(codeBean.getMsg());
                                        }
                                    });
                        }
                    }).subscribe(new Subscriber<Integer>() {
                        @Override
                        public void onCompleted() {
                            getCheckCode.setText("获取验证码");
                            getCheckCode.setClickable(true);
                            getCheckCode.setBackgroundResource(R.drawable.shape_getcheckcode);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(Integer integer) {
                            getCheckCode.setText(integer + "s后重新获取");
                        }
                    });
                }

                break;
            case R.id.register_button:
                mobile = editTextMobile.getText().toString().trim();
                invalid_code = editTextInvalid_code.getText().toString().trim();

                if (checkInput(mobile, invalid_code)) {

                    subscribe = UserModel.getInstance()
                            .verify_code(mobile,"register", invalid_code)
                            .subscribeOn(Schedulers.io())
                            .subscribe(new ServiceResponse<CodeBean>() {
                                @Override
                                public void onNext(CodeBean codeBean) {
                                    if (codeBean != null) {
                                        int result = codeBean.getRcode();
                                        if (result == 0) {
//                                            Intent intent =
//                                                    new Intent(RegisterActivity.this, EditPasswordActivity.class);
//                                            Bundle bundle = new Bundle();
//                                            bundle.putString("username", mobile);
//                                            bundle.putString("valid_code", invalid_code);
//                                            intent.putExtras(bundle);
                                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                            LoginUtils.saveLoginSuccess(true,getApplicationContext());
                                            startActivity(intent);
                                            finish();
                                        }else{
                                            JUtils.Toast(codeBean.getMsg());
                                        }
                                    }
                                }
                            });
                }

                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (subscribe != null && subscribe.isUnsubscribed()) {
            subscribe.unsubscribe();
        }
    }
}
