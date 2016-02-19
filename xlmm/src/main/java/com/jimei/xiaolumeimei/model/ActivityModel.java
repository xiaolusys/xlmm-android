package com.jimei.xiaolumeimei.model;

import com.jimei.xiaolumeimei.entities.ActivityBean;
import com.jimei.xiaolumeimei.rx.DefaultTransform;
import com.jimei.xiaolumeimei.xlmmService.XlmmRetrofitClient;
import rx.Observable;

/**
 * Created by itxuye(www.itxuye.com) on 2016/02/19.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class ActivityModel {
  private static ActivityModel ourInstance = new ActivityModel();

  private ActivityModel() {
  }

  public static ActivityModel getInstance() {
    return ourInstance;
  }

  //活动内容分享
  public Observable<ActivityBean> get_share_content(String ufrom) {
    return XlmmRetrofitClient.getService()
        .get_share_content(ufrom)
        .compose(new DefaultTransform<>());
  }
}
