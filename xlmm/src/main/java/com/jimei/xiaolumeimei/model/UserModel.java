package com.jimei.xiaolumeimei.model;

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

  //设置昵称
  public Observable<ResponseBody> setNickname(int userid, UserInfoBean userinfo) {
    return XlmmRetrofitClient.getService()
            .setNickname(userid, userinfo)
            .compose(new DefaultTransform<>());
  }
}
