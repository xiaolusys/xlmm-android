package com.jimei.xiaolumeimei.ui.activity.user;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jimei.library.rx.RxCountDown;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.base.CommonWebViewActivity;
import com.jimei.xiaolumeimei.entities.CodeBean;
import com.jimei.xiaolumeimei.entities.GetCouponbean;
import com.jimei.xiaolumeimei.entities.NeedSetInfoBean;
import com.jimei.xiaolumeimei.entities.event.SetMiPushEvent;
import com.jimei.xiaolumeimei.entities.event.UserInfoEmptyEvent;
import com.jimei.xiaolumeimei.model.UserModel;
import com.jimei.xiaolumeimei.ui.activity.main.ActivityWebViewActivity;
import com.jimei.xiaolumeimei.ui.activity.product.CollectionActivity;
import com.jimei.xiaolumeimei.ui.activity.product.ProductDetailActivity;
import com.jimei.xiaolumeimei.ui.activity.trade.CartActivity;
import com.jimei.xiaolumeimei.utils.JumpUtils;
import com.jimei.xiaolumeimei.utils.LoginUtils;
import com.jimei.xiaolumeimei.widget.ClearEditText;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye(www.itxuye.com) on 2016/01/22.
 * <p>
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class SmsLoginActivity extends BaseSwipeBackCompatActivity
        implements View.OnClickListener {
    private static final String TAG = SmsLoginActivity.class.getSimpleName();

    @Bind(R.id.register_name)
    ClearEditText registerName;
    @Bind(R.id.checkcode)
    EditText checkcode;
    @Bind(R.id.getCheckCode)
    Button getCheckCode;
    @Bind(R.id.confirm)
    Button confirm;

    private String mobile, invalid_code;
    private Subscription subscribe;
    private String actlink;
    private String title;
    private int id;

    @Override
    protected void setListener() {
        getCheckCode.setOnClickListener(this);
        confirm.setOnClickListener(this);
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
                    RxCountDown.countdown(60).doOnSubscribe(() -> {
                        getCheckCode.setClickable(false);
                        getCheckCode.setBackgroundColor(Color.parseColor("#f3f3f4"));

                        subscribe = UserModel.getInstance()
                                .getCodeBean(mobile, "sms_login")
                                .subscribeOn(Schedulers.io())
                                .subscribe(new ServiceResponse<CodeBean>() {
                                    @Override
                                    public void onNext(CodeBean codeBean) {
                                        JUtils.Toast(codeBean.getMsg());
                                    }
                                });
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
                }

                break;
            case R.id.confirm:
                mobile = registerName.getText().toString().trim();
                invalid_code = checkcode.getText().toString().trim();
                if (checkInput(mobile, invalid_code)) {
                    subscribe = UserModel.getInstance()
                            .verify_code(mobile, "sms_login", invalid_code)
                            .subscribeOn(Schedulers.io())
                            .subscribe(new ServiceResponse<CodeBean>() {
                                @Override
                                public void onNext(CodeBean codeBean) {
                                    int code = codeBean.getRcode();
                                    JUtils.Toast(codeBean.getMsg());
                                    if (code == 0) {
                                        EventBus.getDefault().post(new UserInfoEmptyEvent());
                                        EventBus.getDefault().post(new SetMiPushEvent());
                                        subscribe = UserModel.getInstance()
                                                .need_set_info()
                                                .subscribeOn(Schedulers.io())
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
                                                                if (login.equals("cart")) {
                                                                    Intent intent =
                                                                            new Intent(mContext, CartActivity.class);
                                                                    startActivity(intent);
                                                                    finish();
                                                                } else if (login.equals("push_jump")) {
                                                                    JumpUtils.push_jump_proc(SmsLoginActivity.this, actlink);
                                                                    finish();
                                                                } else if (login.equals("product")) {
                                                                    finish();
                                                                } else if (login.equals("main")) {
                                                                    finish();
                                                                } else if (login.equals("collect")) {
                                                                    Intent intent = new Intent(mContext, CollectionActivity.class);
                                                                    startActivity(intent);
                                                                    finish();
                                                                } else if (login.equals("point")) {
                                                                    Intent intent =
                                                                            new Intent(mContext, MembershipPointActivity.class);
                                                                    startActivity(intent);
                                                                    finish();
                                                                } else if (login.equals("money")) {
                                                                    Intent intent =
                                                                            new Intent(mContext, WalletActivity.class);
                                                                    startActivity(intent);
                                                                    finish();
                                                                } else if (login.equals("axiba")) {
                                                                    finish();
                                                                } else if (login.equals("coupon")) {
                                                                    Intent intent =
                                                                            new Intent(mContext, AllCouponActivity.class);
                                                                    startActivity(intent);
                                                                    finish();
                                                                } else if (login.equals("productdetail")) {
                                                                    Intent intent = new Intent(mContext, ProductDetailActivity.class);
                                                                    Bundle bundle = new Bundle();
                                                                    bundle.putInt("model_id", id);
                                                                    intent.putExtras(bundle);
                                                                    startActivity(intent);
                                                                    finish();
                                                                } else if (login.equals("h5")) {
                                                                    JumpUtils.jumpToWebViewWithCookies(mContext, actlink,
                                                                            -1, CommonWebViewActivity.class);
                                                                    finish();
                                                                } else if (login.equals("goactivity")) {
                                                                    JumpUtils.jumpToWebViewWithCookies(mContext, actlink,
                                                                            id, ActivityWebViewActivity.class, title);
                                                                    finish();
                                                                } else if (login.equals("getCoupon")) {
                                                                    UserModel.getInstance()
                                                                            .getCouPon()
                                                                            .subscribeOn(Schedulers.io())
                                                                            .subscribe(new ServiceResponse<Response<GetCouponbean>>() {
                                                                                @Override
                                                                                public void onNext(
                                                                                        Response<GetCouponbean> getCouponbeanResponse) {

                                                                                    if (getCouponbeanResponse != null) {
                                                                                        if (getCouponbeanResponse.isSuccessful()
                                                                                                && getCouponbeanResponse.code() == 200) {
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
                                                                            });
                                                                }
                                                            }
                                                        } else {
                                                            JUtils.Toast(needSetInfoBean.getInfo());
                                                        }
                                                    }
                                                });
                                    }
                                }

                                @Override
                                public void onError(Throwable e) {
                                    super.onError(e);
                                    e.printStackTrace();
                                }
                            });
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
