package com.jimei.xiaolumeimei.ui.activity.user;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.LinearLayout;

import com.jimei.library.utils.JUtils;
import com.jimei.library.utils.ViewUtils;
import com.jimei.library.widget.CircleImageView;
import com.jimei.library.widget.MyPreferenceView;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.XlmmApp;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.LogoutBean;
import com.jimei.xiaolumeimei.entities.UserInfoBean;
import com.jimei.xiaolumeimei.entities.event.InformationEvent;
import com.jimei.xiaolumeimei.entities.event.LogoutEvent;
import com.jimei.xiaolumeimei.service.ServiceResponse;
import com.jimei.xiaolumeimei.ui.activity.main.TabActivity;
import com.jimei.xiaolumeimei.util.LoginUtils;
import com.xiaomi.mipush.sdk.MiPushClient;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;

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
    @Bind(R.id.layout)
    LinearLayout layout;

    @Override
    protected void setListener() {
        login_out.setOnClickListener(this);
        debug.setOnClickListener(this);
    }

    @Override
    public View getLoadingView() {
        return layout;
    }

    @Override
    protected void initViews() {
        EventBus.getDefault().register(this);
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
    protected void initData() {
        addSubscription(XlmmApp.getUserInteractor(this)
            .getUserInfo(new ServiceResponse<UserInfoBean>() {
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
            }));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshInfomation(InformationEvent event) {
        initData();
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.information_activity;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_out:
                new AlertDialog.Builder(this)
                    .setTitle("注销登录")
                    .setMessage("您确定要退出登录吗？")
                    .setPositiveButton("注销", (dialog, which) -> {
                        final String finalAccount =
                            LoginUtils.getUserAccount(getApplicationContext());
                        addSubscription(XlmmApp.getUserInteractor(InformationActivity.this)
                            .customerLogout(new ServiceResponse<LogoutBean>() {
                                @Override
                                public void onNext(LogoutBean responseBody) {
                                    if (responseBody.getCode() == 0) {
                                        JUtils.Toast("退出成功");
                                        if ((finalAccount != null) && ((!finalAccount.isEmpty()))) {
                                            MiPushClient.unsetUserAccount(getApplicationContext(),
                                                finalAccount, null);
                                        }
                                        LoginUtils.delLoginInfo(getApplicationContext());
                                        EventBus.getDefault().post(new LogoutEvent());
                                        readyGoThenKill(TabActivity.class);
                                    }
                                }
                            }));
                        dialog.dismiss();
                    })
                    .setNegativeButton("取消", (dialog, which) -> dialog.dismiss())
                    .show();
                break;

        }
    }
}
