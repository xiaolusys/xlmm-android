package com.jimei.xiaolumeimei.model;

import com.jimei.xiaolumeimei.entities.AddressResultBean;
import com.jimei.xiaolumeimei.entities.RegisterBean;
import com.jimei.xiaolumeimei.entities.UserBean;
import com.jimei.xiaolumeimei.entities.UserInfoBean;
import com.jimei.xiaolumeimei.rx.DefaultTransform;
import com.jimei.xiaolumeimei.xlmmService.XlmmRetrofitClient;
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

  //设置昵称
  public Observable<UserBean> setNickname(String nickname) {
    return XlmmRetrofitClient.getService()
            .setNickname(nickname)
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
  public Observable<RegisterBean> check_code_user(String username,String valid_code) {
    return XlmmRetrofitClient.getService()
        .check_code_user(username,valid_code)
        .compose(new DefaultTransform<>());
  }



}
