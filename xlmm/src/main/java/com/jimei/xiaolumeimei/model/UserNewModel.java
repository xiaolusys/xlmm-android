package com.jimei.xiaolumeimei.model;

import com.jimei.xiaolumeimei.entities.BudgetdetailBean;
import com.jimei.xiaolumeimei.entities.UserInfoBean;
import com.jimei.library.rx.DefaultTransform;
import com.jimei.xiaolumeimei.xlmmService.XlmmRetrofitClient;
import rx.Observable;

/**
 * Created by itxuye(www.itxuye.com) on 2016/02/26.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class UserNewModel {
  private static UserNewModel ourInstance = new UserNewModel();

  private UserNewModel() {
  }

  public static UserNewModel getInstance() {
    return ourInstance;
  }

  //得到用户信息
  public Observable<UserInfoBean> getProfile() {
    return XlmmRetrofitClient.getService().getProfile().compose(new DefaultTransform<>());
  }

  //得到用户信息
  public Observable<BudgetdetailBean> budGetdetailBean(String page) {
    return XlmmRetrofitClient.getService()
        .budGetdetailBean(page)
        .compose(new DefaultTransform<>());
  }
}
