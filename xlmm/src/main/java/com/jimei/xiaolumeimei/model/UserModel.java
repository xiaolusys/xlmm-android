package com.jimei.xiaolumeimei.model;

import com.jimei.xiaolumeimei.entities.AddressResultBean;
import com.jimei.xiaolumeimei.entities.BindInfoBean;
import com.jimei.xiaolumeimei.entities.CodeBean;
import com.jimei.xiaolumeimei.entities.CouponBean;
import com.jimei.xiaolumeimei.entities.LogOutBean;
import com.jimei.xiaolumeimei.entities.MembershipPointBean;
import com.jimei.xiaolumeimei.entities.NeedSetInfoBean;
import com.jimei.xiaolumeimei.entities.NicknameBean;
import com.jimei.xiaolumeimei.entities.PointLogBean;
import com.jimei.xiaolumeimei.entities.RegisterBean;
import com.jimei.xiaolumeimei.entities.SmsLoginBean;
import com.jimei.xiaolumeimei.entities.SmsLoginUserBean;
import com.jimei.xiaolumeimei.entities.UserAccountBean;
import com.jimei.xiaolumeimei.entities.UserBean;
import com.jimei.xiaolumeimei.entities.UserInfoBean;
import com.jimei.xiaolumeimei.entities.UserWithdrawResult;
import com.jimei.xiaolumeimei.entities.WxLogininfoBean;
import com.jimei.xiaolumeimei.entities.WxPubAuthInfo;
import com.jimei.library.rx.DefaultTransform;
import com.jimei.xiaolumeimei.xlmmService.XlmmRetrofitClient;

import rx.Observable;

/**
 * Created by 优尼世界 on 15/12/29.
 * <p>
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class UserModel {

    private static UserModel ourInstance = new UserModel();

    private UserModel() {
    }

    public static UserModel getInstance() {
        return ourInstance;
    }

    public Observable<UserBean> login(String name, String password) {
        return XlmmRetrofitClient.getService()
                .login(name, password)
                .compose(new DefaultTransform<>());
    }

    public Observable<UserBean> register(String name, String password) {
        return XlmmRetrofitClient.getService()
                .login(name, password)
                .compose(new DefaultTransform<>());
    }

    //得到用户信息
    public Observable<UserInfoBean> getUserInfo() {
        return XlmmRetrofitClient.getService()
                .getUserInfo()
                .compose(new DefaultTransform<>());
    }

    //投诉建议
    public Observable<AddressResultBean> complain(String com_content) {
        return XlmmRetrofitClient.getService()
                .complain(com_content)
                .compose(new DefaultTransform<>());
    }

    //获取注册验证码
    public Observable<RegisterBean> getRegisterCheckCode(String vmobile) {
        return XlmmRetrofitClient.getService()
                .getRegisterCheckCode(vmobile)
                .compose(new DefaultTransform<>());
    }

    //获取注册验证码
    public Observable<RegisterBean> check_code_user(String username, String valid_code) {
        return XlmmRetrofitClient.getService()
                .check_code_user(username, valid_code)
                .compose(new DefaultTransform<>());
    }

    //设置昵称
    public Observable<UserBean> setNickname(int userid, NicknameBean nickname) {
        return XlmmRetrofitClient.getService()
                .setNickname(userid, nickname)
                .compose(new DefaultTransform<>());
    }

    //设置密码
    public Observable<UserBean> changePassword(String username, String valid_code,
                                               String password1, String password2) {
        return XlmmRetrofitClient.getService()
                .changePassword(username, valid_code, password1, password2)
                .compose(new DefaultTransform<>());
    }

    //获取修改密码时验证码
    public Observable<RegisterBean> getChgPasswordCheckCode(String vmobile) {
        return XlmmRetrofitClient.getService()
                .getChgPasswordCheckCode(vmobile)
                .compose(new DefaultTransform<>());
    }

    public Observable<LogOutBean> customer_logout() {

        return XlmmRetrofitClient.getService()
                .customer_logout()
                .compose(new DefaultTransform<>());
    }

    //得到用户积分信息
    public Observable<MembershipPointBean> getMembershipPointBean() {
        return XlmmRetrofitClient.getService()
                .getMembershipPointBean()
                .compose(new DefaultTransform<>());
    }

    //得到用户积分记录信息
    public Observable<PointLogBean> getPointLogBean() {
        return XlmmRetrofitClient.getService()
                .getPointLogBean()
                .compose(new DefaultTransform<>());
    }

    //得到用户优惠券信息
    public Observable<CouponBean> getUnusedCouponBean() {
        return XlmmRetrofitClient.getService()
                .getUnusedCouponBean()
                .compose(new DefaultTransform<>());
    }

    //得到用户过期优惠券信息
    public Observable<CouponBean> getPastCouponBean() {
        return XlmmRetrofitClient.getService()
                .getPastCouponBean()
                .compose(new DefaultTransform<>());
    }

    //获取短信登录验证码
    public Observable<SmsLoginBean> getSmsCheckCode(String mobile) {
        return XlmmRetrofitClient.getService()
                .getSmsCheckCode(mobile)
                .compose(new DefaultTransform<>());
    }

    //短信登录
    public Observable<SmsLoginUserBean> smsLogin(String mobile, String code) {
        return XlmmRetrofitClient.getService()
                .smsLogin(mobile, code)
                .compose(new DefaultTransform<>());
    }

    //微信登录
    public Observable<WxLogininfoBean> wxapp_login(String noncestr, String timestamp,
                                                   String sign, String headimgurl, String nickname, String openid, String unionid) {
        return XlmmRetrofitClient.getService()
                .wxapp_login(noncestr, timestamp, sign, headimgurl, nickname, openid, unionid)
                .compose(new DefaultTransform<>());
    }

    //判断用户是否需要绑定
    public Observable<NeedSetInfoBean> need_set_info() {
        return XlmmRetrofitClient.getService()
                .need_set_info()
                .compose(new DefaultTransform<>());
    }

    //绑定手机获取验证码
    public Observable<BindInfoBean> bang_mobile_code(String vmobile) {

        return XlmmRetrofitClient.getService()
                .bang_mobile_code(vmobile)
                .compose(new DefaultTransform<>());
    }

    //绑定手机
    public Observable<BindInfoBean> bang_mobile(String username, String password1,
                                                String password2, String valid_code) {
        return XlmmRetrofitClient.getService()
                .bang_mobile(username, password1, password2, valid_code)
                .compose(new DefaultTransform<>());
    }

    public Observable<BindInfoBean> bang_mobile_unpassword(String username,
                                                           String valid_code) {
        return XlmmRetrofitClient.getService()
                .bang_mobile_unpassword(username, valid_code)
                .compose(new DefaultTransform<>());
    }

    //get push useraccount
    public Observable<UserAccountBean> getUserAccount(String platform, String regid,
                                                      String device_id) {
        return XlmmRetrofitClient.getService()
                .getUserAccount(platform, regid, device_id)
                .compose(new DefaultTransform<>());
    }

    //get 微信公众号验证信息
    public Observable<WxPubAuthInfo> getWxPubAuthInfo() {
        return XlmmRetrofitClient.getService()
                .getWxPubAuthInfo()
                .compose(new DefaultTransform<>());
    }

    //普通用户提现
    public Observable<UserWithdrawResult> user_withdraw_cash(String amount) {
        return XlmmRetrofitClient.getService()
                .user_withdraw_cash(amount)
                .compose(new DefaultTransform<>());
    }

    public Observable<CodeBean> getCodeBean(String mobile, String action) {
        return XlmmRetrofitClient.getService()
                .send_code(mobile, action)
                .compose(new DefaultTransform<>());
    }

    public Observable<CodeBean> verify_code(String mobile, String action, String code) {
        return XlmmRetrofitClient.getService()
                .verify_code(mobile, action, code)
                .compose(new DefaultTransform<>());
    }

    public Observable<CodeBean> reset_password(String mobile, String password1, String password2, String code) {
        return XlmmRetrofitClient.getService()
                .reset_password(mobile, password1, password2, code)
                .compose(new DefaultTransform<>());
    }

    public Observable<CodeBean> passwordlogin(String username, String password, String next) {
        return XlmmRetrofitClient.getService()
                .passwordlogin(username, password, next)
                .compose(new DefaultTransform<>());
    }

    public Observable<CodeBean> wxlogin(String noncestr, String timestamp, String sign) {
        return XlmmRetrofitClient.getService()
                .wxlogin(noncestr, timestamp, sign)
                .compose(new DefaultTransform<>());
    }
}
