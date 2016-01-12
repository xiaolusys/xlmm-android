package com.jimei.xiaolumeimei.model;

import com.jimei.xiaolumeimei.entities.AllOdersBean;
import com.jimei.xiaolumeimei.rx.DefaultTransform;
import com.jimei.xiaolumeimei.xlmmService.XlmmRetrofitClient;
import rx.Observable;

/**
 * Created by itxuye(www.itxuye.com) on 15/12/29.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class TradeModel {

  //得到全部订单数据列表
  public Observable<AllOdersBean> getAlloderBean() {
    return XlmmRetrofitClient.getService()
        .getAllOdersList()
        .compose(new DefaultTransform<>());
  }
}
