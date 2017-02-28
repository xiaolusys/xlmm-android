package com.jimei.xiaolumeimei.model;

import com.jimei.library.rx.DefaultTransform;
import com.jimei.xiaolumeimei.entities.BudgetDetailBean;
import com.jimei.xiaolumeimei.entities.CodeBean;
import com.jimei.xiaolumeimei.entities.CoinHistoryListBean;
import com.jimei.xiaolumeimei.entities.CouponEntity;
import com.jimei.xiaolumeimei.entities.CouponSelectEntity;
import com.jimei.xiaolumeimei.entities.LogOutBean;
import com.jimei.xiaolumeimei.entities.NeedSetInfoBean;
import com.jimei.xiaolumeimei.entities.NicknameBean;
import com.jimei.xiaolumeimei.entities.ResultEntity;
import com.jimei.xiaolumeimei.entities.UserAccountBean;
import com.jimei.xiaolumeimei.entities.UserBean;
import com.jimei.xiaolumeimei.entities.UserInfoBean;
import com.jimei.xiaolumeimei.entities.UserWithdrawResult;
import com.jimei.xiaolumeimei.entities.VersionBean;
import com.jimei.xiaolumeimei.entities.WxPubAuthInfo;
import com.jimei.xiaolumeimei.service.RetrofitClient;
import com.jimei.xiaolumeimei.service.api.UserService;

import java.util.ArrayList;

import rx.Observable;

/**
 * Created by 优尼世界 on 15/12/29.
 * <p>
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class UserModel {

    private static UserModel ourInstance = new UserModel();
    private static UserService userService;

    private static UserService getService() {
        if (userService == null) {
            userService = RetrofitClient.createAdapter().create(UserService.class);
        }
        return userService;
    }

    private UserModel() {
    }

    public static UserModel getInstance() {
        return ourInstance;
    }

    //得到用户信息
    public Observable<UserInfoBean> getUserInfo() {
        return getService()
            .getUserInfo()
            .compose(new DefaultTransform<>());
    }

    //设置昵称
    public Observable<UserBean> setNickname(int userid, NicknameBean nickname) {
        return getService()
            .setNickname(userid, nickname)
            .compose(new DefaultTransform<>());
    }

    public Observable<LogOutBean> customer_logout() {
        return getService()
            .customer_logout()
            .compose(new DefaultTransform<>());
    }

    //得到用户积分记录信息
    public Observable<CoinHistoryListBean> getCoinHisList(String page) {
        return getService()
            .getCoinHisList(page)
            .compose(new DefaultTransform<>());
    }

    //获取优惠券
    public Observable<ArrayList<CouponEntity>> getCouponList(int status) {
        return getService()
            .getCouponList(status)
            .compose(new DefaultTransform<>());
    }

    //获取优惠券
    public Observable<ArrayList<CouponEntity>> getCouponList(int status, int coupon_type) {
        return getService()
            .getCouponList(status, coupon_type)
            .compose(new DefaultTransform<>());
    }

    //购物车选择优惠券
    public Observable<CouponSelectEntity> getCouponSelectEntity(String cart_ids) {
        return getService()
            .getCouponSelectEntity(cart_ids)
            .compose(new DefaultTransform<>());
    }

    //微信登录
    public Observable<CodeBean> wxapp_login(String noncestr, String timestamp, String sign,
                                            String headimgurl, String nickname, String openid, String unionid) {
        return getService()
            .wxapp_login(noncestr, timestamp, sign, headimgurl, nickname, openid, unionid, "android")
            .compose(new DefaultTransform<>());
    }

    //判断用户是否需要绑定
    public Observable<NeedSetInfoBean> need_set_info() {
        return getService()
            .need_set_info()
            .compose(new DefaultTransform<>());
    }

    //get push useraccount
    public Observable<UserAccountBean> getUserAccount(String platform, String regid, String device_id) {
        return getService()
            .getUserAccount(platform, regid, device_id)
            .compose(new DefaultTransform<>());
    }

    //get 微信公众号验证信息
    public Observable<WxPubAuthInfo> getWxPubAuthInfo() {
        return getService()
            .getWxPubAuthInfo()
            .compose(new DefaultTransform<>());
    }

    //普通用户提现
    public Observable<UserWithdrawResult> user_withdraw_cash(String amount, String verify_code) {
        return getService()
            .user_withdraw_cash(amount, verify_code)
            .compose(new DefaultTransform<>());
    }


    public Observable<ResultEntity> getVerifyCode() {
        return getService()
            .getVerifyCode()
            .compose(new DefaultTransform<>());
    }

    //发送验证码
    public Observable<CodeBean> getCodeBean(String mobile, String action) {
        return getService()
            .send_code(mobile, action)
            .compose(new DefaultTransform<>());
    }

    //验证码验证
    public Observable<CodeBean> verify_code(String mobile, String action, String code) {
        return getService()
            .verify_code(mobile, action, code, "android")
            .compose(new DefaultTransform<>());
    }

    //设置账号密码
    public Observable<CodeBean> reset_password(String mobile, String password1, String password2, String code) {
        return getService()
            .reset_password(mobile, password1, password2, code)
            .compose(new DefaultTransform<>());
    }

    //用户账号密码登录
    public Observable<CodeBean> passwordlogin(String username, String password, String next) {
        return getService()
            .passwordlogin(username, password, next, "android")
            .compose(new DefaultTransform<>());
    }

    public Observable<CodeBean> openDebug(String debug_secret) {
        return getService()
            .openDebug(debug_secret)
            .compose(new DefaultTransform<>());
    }

    //得到用户信息
    public Observable<BudgetDetailBean> budGetDetailBean(String page) {
        return getService()
            .budGetDetailBean(page)
            .compose(new DefaultTransform<>());
    }

    public Observable<VersionBean> getVersion() {
        return getService()
            .getVersion()
            .compose(new DefaultTransform<>());
    }

}
