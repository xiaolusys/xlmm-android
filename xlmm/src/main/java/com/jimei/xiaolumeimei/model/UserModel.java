package com.jimei.xiaolumeimei.model;

import com.jimei.xiaolumeimei.entities.UserBean;
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



}
