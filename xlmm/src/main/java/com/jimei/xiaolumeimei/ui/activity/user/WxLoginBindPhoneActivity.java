package com.jimei.xiaolumeimei.ui.activity.user;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jimei.library.rx.RxCountDown;
import com.jimei.library.utils.JUtils;
import com.jimei.library.utils.ViewUtils;
import com.jimei.library.widget.CircleImageView;
import com.jimei.library.widget.ClearEditText;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.CodeBean;
import com.jimei.xiaolumeimei.model.UserModel;
import com.jimei.xiaolumeimei.service.ServiceResponse;
import com.jimei.xiaolumeimei.ui.activity.main.MainActivity;

import butterknife.Bind;
import rx.Subscriber;

/**
 * Created by itxuye(www.itxuye.com) on 2016/02/05.
 * <p>
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class WxLoginBindPhoneActivity extends BaseSwipeBackCompatActivity
        implements View.OnClickListener {

    private static final String TAG = WxLoginBindPhoneActivity.class.getSimpleName();
    @Bind(R.id.headimage)
    CircleImageView headimage;
    @Bind(R.id.tv_nickname)
    TextView tvNickname;
    @Bind(R.id.register_name)
    ClearEditText registerName;
    @Bind(R.id.checkcode)
    EditText checkcode;
    @Bind(R.id.getCheckCode)
    Button getCheckCode;
    @Bind(R.id.next)
    Button next;
    @Bind(R.id.pass)
    Button pass;
    @Bind(R.id.layout)
    LinearLayout layout;
    private String mobile;

    @Override
    protected void setListener() {
        next.setOnClickListener(this);
        pass.setOnClickListener(this);
        getCheckCode.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        UserModel.getInstance()
                .getUserInfo()
                .subscribe(user -> {
                    if (user != null) {
                        if (user.getThumbnail() != null && !"".equals(user.getThumbnail())) {
                            ViewUtils.loadImgToImgView(WxLoginBindPhoneActivity.this, headimage, user.getThumbnail());
                        }
                        tvNickname.setText("微信账号： " + user.getNick());
                    }
                });
    }

    @Override
    public View getLoadingView() {
        return layout;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_wxlogin;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next:
                mobile = registerName.getText().toString().trim();
                String invalid_code = checkcode.getText().toString().trim();
                if (checkInput(mobile, invalid_code)) {
                    bindMobilePhone(mobile, invalid_code);
                }
                break;
            case R.id.pass:
                startActivity(new Intent(WxLoginBindPhoneActivity.this, MainActivity.class));
                finish();
                break;
            case R.id.getCheckCode:
                mobile = registerName.getText().toString().trim();
                if (checkMobileInput(mobile)) {
                    RxCountDown.countdown(60).doOnSubscribe(() -> {
                        getCheckCode.setClickable(false);
                        getCheckCode.setBackgroundColor(Color.parseColor("#f3f3f4"));
                        addSubscription(UserModel.getInstance()
                                .getCodeBean(mobile, "bind")
                                .subscribe(new ServiceResponse<CodeBean>() {
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
                }
                break;
        }
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

    private void bindMobilePhone(String username, String valid_code) {
        addSubscription(UserModel.getInstance()
                .verify_code(username, "bind", valid_code)
                .subscribe(new ServiceResponse<CodeBean>() {
                    @Override
                    public void onNext(CodeBean codeBean) {
                        int code = codeBean.getRcode();
                        if (0 == code) {
                            startActivity(new Intent(WxLoginBindPhoneActivity.this, MainActivity.class));
                            finish();
                        } else {
                            JUtils.Toast(codeBean.getMsg());
                        }
                    }
                }));
    }
}
