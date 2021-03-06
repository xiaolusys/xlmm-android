package com.jimei.xiaolumeimei.ui.activity.user;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jimei.library.rx.RxCountDown;
import com.jimei.library.utils.JUtils;
import com.jimei.library.widget.XlmmTitleView;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.XlmmApp;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.CodeBean;
import com.jimei.xiaolumeimei.service.ServiceResponse;

import butterknife.Bind;
import rx.Subscriber;

public class VerifyPhoneForgetActivity extends BaseSwipeBackCompatActivity
    implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, TextWatcher {
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
    @Bind(R.id.sb)
    SeekBar seekBar;
    @Bind(R.id.tv)
    TextView textView;
    @Bind(R.id.view_first)
    View viewFirst;
    @Bind(R.id.frame_layout)
    FrameLayout frameLayout;
    @Bind(R.id.layout)
    LinearLayout layout;
    private String mobile, invalid_code;

    @Override
    protected void setListener() {
        getCheckCode.setOnClickListener(this);
        register_button.setOnClickListener(this);
        seekBar.setOnSeekBarChangeListener(this);
        editTextMobile.addTextChangedListener(this);
        viewFirst.setOnClickListener(this);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.register_activity;
    }

    @Override
    protected void initViews() {
        titleView.setName("请验证手机");
        register_button.setText("下一步");
    }

    @Override
    public View getLoadingView() {
        return layout;
    }

    public boolean checkMobileInput(String mobile) {
        if (mobile == null || mobile.trim().equals("")) {
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
        if (checkMobileInput(mobile)) {
            if (checkcode == null || checkcode.trim().equals("")) {
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
                    if (seekBar.getProgress() == seekBar.getMax()) {
                        RxCountDown.countdown(60).doOnSubscribe(() -> {
                            getCheckCode.setClickable(false);
                            getCheckCode.setBackgroundColor(Color.parseColor("#f3f3f4"));
                            addSubscription(XlmmApp.getUserInteractor(VerifyPhoneForgetActivity.this)
                                .getCodeBean(mobile, "find_pwd", new ServiceResponse<CodeBean>() {
                                    @Override
                                    public void onNext(CodeBean codeBean) {
                                        JUtils.Toast(codeBean.getMsg());
                                    }
                                }));
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
                    } else {
                        JUtils.Toast("请先拖动滑块验证");
                    }
                }
                break;
            case R.id.register_button:
                mobile = editTextMobile.getText().toString().trim();
                invalid_code = editTextInvalid_code.getText().toString().trim();
                if (checkInput(mobile, invalid_code)) {
                    addSubscription(XlmmApp.getUserInteractor(VerifyPhoneForgetActivity.this)
                        .verifyCode(mobile, "find_pwd", invalid_code, new ServiceResponse<CodeBean>() {
                            @Override
                            public void onNext(CodeBean codeBean) {
                                int result = codeBean.getRcode();
                                if (result == 0) {
                                    Intent intent = new Intent(VerifyPhoneForgetActivity.this,
                                        EditPasswordForgetActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("username", mobile);
                                    bundle.putString("valid_code", invalid_code);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    JUtils.Toast(codeBean.getMsg());
                                }
                            }
                        }));
                }
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        textView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        seekBar.setThumbOffset(0);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mobile = editTextMobile.getText().toString().trim();
        if (seekBar.getProgress() != seekBar.getMax()) {
            seekBar.setProgress(0);
            textView.setVisibility(View.VISIBLE);
            textView.setTextColor(Color.GRAY);
            textView.setText("向右滑动验证");
        } else if (!checkMobileInput(mobile)) {
            seekBar.setProgress(0);
            textView.setVisibility(View.VISIBLE);
            textView.setTextColor(Color.GRAY);
            textView.setText("向右滑动验证");
        } else {
            textView.setVisibility(View.VISIBLE);
            textView.setTextColor(Color.WHITE);
            textView.setText("完成验证");
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() < 11) {
            frameLayout.setVisibility(View.GONE);
            seekBar.setProgress(0);
            textView.setVisibility(View.VISIBLE);
            textView.setTextColor(Color.GRAY);
            textView.setText("向右滑动验证");
        } else {
            frameLayout.setVisibility(View.VISIBLE);
        }
    }
}
