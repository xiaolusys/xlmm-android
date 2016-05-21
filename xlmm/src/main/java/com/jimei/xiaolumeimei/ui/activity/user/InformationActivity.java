package com.jimei.xiaolumeimei.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.LogOutBean;
import com.jimei.xiaolumeimei.entities.UserInfoBean;
import com.jimei.xiaolumeimei.model.UserModel;
import com.jimei.xiaolumeimei.ui.activity.main.MainActivity;
import com.jimei.xiaolumeimei.utils.LoginUtils;
import com.jimei.xiaolumeimei.utils.ViewUtils;
import com.jimei.xiaolumeimei.widget.CircleImageView;
import com.jimei.xiaolumeimei.widget.MyPreferenceView;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.xiaomi.mipush.sdk.MiPushClient;

import butterknife.Bind;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye(www.itxuye.com) on 2016/01/18.
 * <p>
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class InformationActivity extends BaseSwipeBackCompatActivity
        implements View.OnClickListener {
    static String nickName;
    static String mobile;
    String TAG = "InformationActivity";
    @Bind(R.id.user_img)
    CircleImageView imgUser;
    @Bind(R.id.nick_name)
    MyPreferenceView nickNameView;
    @Bind(R.id.bind_phone)
    MyPreferenceView bindPhoneView;
    @Bind(R.id.edit_pwd)
    MyPreferenceView editPwdView;
    @Bind(R.id.edit_address)
    MyPreferenceView editAddressView;
    @Bind(R.id.setting)
    MyPreferenceView settingView;
    @Bind(R.id.login_out)
    MyPreferenceView login_out;

    UserInfoBean userinfo;
    @Bind(R.id.debug)
    LinearLayout debug;
    private int num;

    @Override
    protected void setListener() {
        login_out.setOnClickListener(this);
        debug.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        nickNameView.setTitleText("昵称");
        bindPhoneView.setTitleText("绑定手机");
        editPwdView.setTitleText("修改密码");
        editAddressView.setTitleText("地址管理");
        settingView.setTitleText("设置");
        login_out.setTitleText("退出账户");
        nickNameView.bindActivity(this, SettingNicknameActivity.class);
        bindPhoneView.bindActivity(this, WxLoginBindPhoneActivity.class);
        editPwdView.bindActivity(this, VerifyPhoneActivity.class);
        editAddressView.bindActivity(this, AddressActivity.class);
        settingView.bindActivity(this, SettingActivity.class);
        login_out.bindActivity(this, null);
        login_out.hideImg();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Subscription subscribe = UserModel.getInstance()
                .getUserInfo()
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<UserInfoBean>() {
                    @Override
                    public void onNext(UserInfoBean user) {
                        userinfo = user;
                        nickName = userinfo.getNick();
                        mobile = userinfo.getMobile();
                        nickNameView.setSummary(nickName);
                        Bundle bundle = new Bundle();
                        bundle.putString("nickname", nickName);
                        bundle.putString("headimgurl", user.getThumbnail());
                        bindPhoneView.addBundle(bundle);
                        bindPhoneView.setSummary(
                                mobile.substring(0, 3) + "****" + mobile.substring(7));
                        ViewUtils.loadImgToImgView(getApplicationContext(), imgUser,
                                user.getThumbnail());
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "error:, " + e.toString());
                        super.onError(e);
                    }
                });
        addSubscription(subscribe);
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.information_activity;
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_out:
                new MaterialDialog.Builder(this)
                        .title("注销登录")
                        .content("您确定要退出登录吗？")
                        .positiveText("注销")
                        .positiveColorRes(R.color.colorAccent)
                        .negativeText("取消")
                        .negativeColorRes(R.color.colorAccent)
                        .callback(new MaterialDialog.ButtonCallback() {
                            @Override
                            public void onPositive(MaterialDialog dialog) {
                                final String finalAccount =
                                        LoginUtils.getUserAccount(getApplicationContext());
                                UserModel.getInstance()
                                        .customer_logout()
                                        .subscribeOn(Schedulers.io())
                                        .subscribe(new ServiceResponse<LogOutBean>() {
                                            @Override
                                            public void onNext(LogOutBean responseBody) {
                                                super.onNext(responseBody);
                                                if (responseBody.getCode() == 0) {
                                                    JUtils.Toast("退出成功");
                                                    if ((finalAccount != null) && ((!finalAccount.isEmpty()))) {
                                                        MiPushClient.unsetUserAccount(getApplicationContext(),
                                                                finalAccount, null);
                                                    }
                                                    Intent intent =
                                                            new Intent(getApplicationContext(), MainActivity.class);
                                                    LoginUtils.delLoginInfo(getApplicationContext());
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            }
                                        });

                                dialog.dismiss();
                            }

                            @Override
                            public void onNegative(MaterialDialog dialog) {
                                dialog.dismiss();
                            }
                        }).show();
                break;

        }
    }
}
