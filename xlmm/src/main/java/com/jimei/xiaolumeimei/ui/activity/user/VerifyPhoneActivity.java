package com.jimei.xiaolumeimei.ui.activity.user;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jimei.library.rx.RxCountDown;
import com.jimei.library.widget.XlmmTitleView;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.CodeBean;
import com.jimei.xiaolumeimei.model.UserModel;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.umeng.analytics.MobclickAgent;

import butterknife.Bind;
import rx.Subscriber;
import rx.Subscription;

public class VerifyPhoneActivity extends BaseSwipeBackCompatActivity
        implements View.OnClickListener {
    @Bind(R.id.title_view)
    XlmmTitleView titleView;
    @Bind(R.id.register_name)
    EditText editTextMobile;
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
    protected int getContentViewLayoutID() {
        return R.layout.register_activity;
    }

    @Override
    protected void initViews() {
        titleView.setName("设置密码");
        register_button.setText("下一步");
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

                    RxCountDown.countdown(60).doOnSubscribe(() -> {
                        getCheckCode.setClickable(false);
                        getCheckCode.setBackgroundColor(Color.parseColor("#f3f3f4"));

                        subscribe = UserModel.getInstance()
                                .getCodeBean(mobile, "change_pwd")
                                .subscribe(new ServiceResponse<CodeBean>() {
                                    @Override
                                    public void onNext(CodeBean codeBean) {
                                        if (codeBean != null) {
                                            JUtils.Toast(codeBean.getMsg());
                                        }
                                    }
                                });
                    }).subscribe(new Subscriber<Integer>() {
                        @Override
                        public void onCompleted() {
                            getCheckCode.setText("获取验证码");
                            getCheckCode.setClickable(true);
                            getCheckCode.setBackgroundResource(R.drawable.btn_common_white);
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
                            .verify_code(mobile, "change_pwd", invalid_code)
                            .subscribe(new ServiceResponse<CodeBean>() {
                                @Override
                                public void onNext(CodeBean codeBean) {
                                    if (codeBean != null) {
                                        int result = codeBean.getRcode();
                                        JUtils.Log("修改密码", result + "");

                                        if (result == 0) {
                                            Intent intent = new Intent(VerifyPhoneActivity.this,
                                                    SettingPasswordForgetActivity.class);
                                            Bundle bundle = new Bundle();
                                            bundle.putString("mobile", mobile);
                                            bundle.putString("valid_code", invalid_code);

                                            intent.putExtras(bundle);
                                            startActivity(intent);
                                            finish();
                                        } else {
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
