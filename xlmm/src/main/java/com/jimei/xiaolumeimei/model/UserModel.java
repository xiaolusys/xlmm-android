package com.jimei.xiaolumeimei.model;

import com.jimei.xiaolumeimei.entities.User;
import com.jimei.xiaolumeimei.rx.DefaultTransform;
import com.jimei.xiaolumeimei.xlmmService.XlmmRetrofitClient;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by itxuye(www.itxuye.com) on 15/12/29.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class UserModel {
  public Observable<User> login(String name, String password) {
    return XlmmRetrofitClient.getService()
        .login(name, password)
        .compose(new DefaultTransform<>())
        .doOnNext(new Action1<User>() {
          @Override public void call(User user) {

          }
        });
  }
}
