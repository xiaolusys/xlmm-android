package com.jimei.xiaolumeimei.ui.activity.user;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jimei.library.rx.RxCountDown;
import com.jimei.library.utils.JUtils;
import com.jimei.library.widget.ClearEditText;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.base.CommonWebViewActivity;
import com.jimei.xiaolumeimei.entities.CodeBean;
import com.jimei.xiaolumeimei.entities.GetCouponbean;
import com.jimei.xiaolumeimei.entities.NeedSetInfoBean;
import com.jimei.xiaolumeimei.entities.event.SetMiPushEvent;
import com.jimei.xiaolumeimei.model.UserModel;
import com.jimei.xiaolumeimei.ui.activity.main.ActivityWebViewActivity;
import com.jimei.xiaolumeimei.ui.activity.product.ProductDetailActivity;
import com.jimei.xiaolumeimei.utils.JumpUtils;
import com.jimei.xiaolumeimei.utils.LoginUtils;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * Created by itxuye(www.itxuye.com) on 2016/01/22.
 * <p>
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class SmsLoginActivity extends BaseSwipeBackCompatActivity
        implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, TextWatcher {

    @Bind(R.id.register_name)
    ClearEditText registerName;
    @Bind(R.id.checkcode)
    EditText checkcode;
    @Bind(R.id.getCheckCode)
    Button getCheckCode;
    @Bind(R.id.confirm)
    Button confirm;
    @Bind(R.id.sb)
    SeekBar seekBar;
    @Bind(R.id.tv)
    TextView textView;
    @Bind(R.id.view_first)
    View viewFirst;
    @Bind(R.id.frame_layout)
    FrameLayout frameLayout;

    private String mobile;
    private String actlink;
    private String title;
    private int id;

    @Override
    protected void setListener() {
        getCheckCode.setOnClickListener(this);
        confirm.setOnClickListener(this);
        seekBar.setOnSeekBarChangeListener(this);
        registerName.addTextChangedListener(this);
        viewFirst.setOnClickListener(this);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_sms;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.getCheckCode:
                mobile = registerName.getText().toString().trim();
                if (checkMobileInput(mobile)) {
                    if (seekBar.getProgress() == seekBar.getMax()) {
                        RxCountDown.countdown(60).doOnSubscribe(() -> {
                            getCheckCode.setClickable(false);
                            getCheckCode.setBackgroundColor(Color.parseColor("#f3f3f4"));
                            addSubscription(UserModel.getInstance()
                                    .getCodeBean(mobile, "sms_login")
                                    .subscribe(new ServiceResponse<CodeBean>() {
                                        @Override
                                        public void onNext(CodeBean codeBean) {
                                            JUtils.Toast(codeBean.getMsg());
                                        }
                                    }));
                        }).unsafeSubscribe(new Subscriber<Integer>() {
                            @Override
                            public void onCompleted() {
                                if (getCheckCode != null) {
                                    getCheckCode.setText("获取验证码");
                                    getCheckCode.setClickable(true);
                                    getCheckCode.setBackgroundResource(R.drawable.btn_common_white);
                                }
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(Integer integer) {
                                if (getCheckCode != null) {
                                    getCheckCode.setText(integer + "s后重新获取");
                                }
                            }
                        });
                    } else {
                        JUtils.Toast("请先拖动滑块验证");
                    }
                }
                break;
            case R.id.confirm:
                mobile = registerName.getText().toString().trim();
                String invalid_code = checkcode.getText().toString().trim();
                if (checkInput(mobile, invalid_code)) {
                    addSubscription(UserModel.getInstance()
                            .verify_code(mobile, "sms_login", invalid_code)
                            .subscribe(new ServiceResponse<CodeBean>() {
                                @Override
                                public void onNext(CodeBean codeBean) {
                                    int code = codeBean.getRcode();
                                    JUtils.Toast(codeBean.getMsg());
                                    if (code == 0) {
                                        EventBus.getDefault().post(new SetMiPushEvent());
                                        addSubscription(UserModel.getInstance()
                                                .need_set_info()
                                                .subscribe(new ServiceResponse<NeedSetInfoBean>() {
                                                    @Override
                                                    public void onNext(NeedSetInfoBean needSetInfoBean) {
                                                        super.onNext(needSetInfoBean);
                                                        int codeInfo = needSetInfoBean.getCode();
                                                        if (0 == codeInfo) {
                                                            LoginUtils.saveLoginSuccess(true, getApplicationContext());

                                                            String login = null;
                                                            if (null != getIntent()
                                                                    && getIntent().getExtras() != null) {
                                                                login = getIntent().getExtras().getString("login");
                                                                actlink = getIntent().getExtras().getString("actlink");
                                                                title = getIntent().getExtras().getString("title");
                                                                id = getIntent().getExtras().getInt("id");
                                                            }

                                                            if (null != login) {
                                                                if (login.equals("push_jump")) {
                                                                    JumpUtils.push_jump_proc(SmsLoginActivity.this, actlink);
                                                                    finish();
                                                                } else if (login.equals("productdetail")) {
                                                                    Intent intent = new Intent(mContext, ProductDetailActivity.class);
                                                                    Bundle bundle = new Bundle();
                                                                    bundle.putInt("model_id", id);
                                                                    intent.putExtras(bundle);
                                                                    startActivity(intent);
                                                                    finish();
                                                                } else if (login.equals("h5")) {
                                                                    Intent intent = new Intent(mContext, CommonWebViewActivity.class);
                                                                    SharedPreferences sharedPreferences =
                                                                            getSharedPreferences("xlmmCookiesAxiba",
                                                                                    Context.MODE_PRIVATE);
                                                                    String cookies = sharedPreferences.getString("cookiesString", "");
                                                                    String domain = sharedPreferences.getString("cookiesDomain", "");
                                                                    Bundle bundle = new Bundle();
                                                                    bundle.putString("cookies", cookies);
                                                                    bundle.putString("domain", domain);
                                                                    bundle.putString("actlink", actlink);
                                                                    intent.putExtras(bundle);
                                                                    startActivity(intent);
                                                                    finish();
                                                                } else if (login.equals("goactivity")) {
                                                                    JumpUtils.jumpToWebViewWithCookies(mContext, actlink, id,
                                                                            ActivityWebViewActivity.class, title);
                                                                    finish();
                                                                } else if (login.equals("getCoupon")) {
                                                                    addSubscription(UserModel.getInstance()
                                                                            .getCouPon()
                                                                            .subscribe(new ServiceResponse<Response<GetCouponbean>>() {
                                                                                @Override
                                                                                public void onNext(
                                                                                        Response<GetCouponbean> getCouponbeanResponse) {
                                                                                    if (getCouponbeanResponse != null) {
                                                                                        if (getCouponbeanResponse.isSuccessful()) {
                                                                                            JUtils.Toast(getCouponbeanResponse.body().getInfo());
                                                                                            finish();
                                                                                        }
                                                                                    }
                                                                                }

                                                                                @Override
                                                                                public void onError(Throwable e) {
                                                                                    super.onError(e);
                                                                                    if (e instanceof HttpException) {
                                                                                        JUtils.Toast("优惠券领取失败");
                                                                                    }
                                                                                }
                                                                            }));
                                                                } else {
                                                                    finish();
                                                                }
                                                            }
                                                        } else {
                                                            JUtils.Toast(needSetInfoBean.getInfo());
                                                        }
                                                    }
                                                }));
                                    }
                                }
                            }));
                }

                break;
        }
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
        mobile = registerName.getText().toString().trim();
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
