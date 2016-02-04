package com.jimei.xiaolumeimei.model;

import com.jimei.xiaolumeimei.entities.AddressResultBean;
import com.jimei.xiaolumeimei.entities.CouponBean;
import com.jimei.xiaolumeimei.entities.LogOutBean;
import com.jimei.xiaolumeimei.entities.MembershipPointBean;
import com.jimei.xiaolumeimei.entities.NicknameBean;
import com.jimei.xiaolumeimei.entities.PointLogBean;
import com.jimei.xiaolumeimei.entities.RegisterBean;
import com.jimei.xiaolumeimei.entities.SmsLoginBean;
import com.jimei.xiaolumeimei.entities.SmsLoginUserBean;
import com.jimei.xiaolumeimei.entities.UserBean;
import com.jimei.xiaolumeimei.entities.UserInfoBean;
import com.jimei.xiaolumeimei.rx.DefaultTransform;
import com.jimei.xiaolumeimei.xlmmService.XlmmRetrofitClient;
import com.squareup.okhttp.ResponseBody;
import rx.Observable;

/**
 * Created by 优尼世界 on 15/12/29.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class UserModel {

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
  public Observable<ResponseBody> wxapp_login(String headimgurl, String nickname,
      String openid, String unionid) {
    return XlmmRetrofitClient.getService()
        .wxapp_login(headimgurl, nickname, openid, unionid)
        .compose(new DefaultTransform<>());
  }
}
