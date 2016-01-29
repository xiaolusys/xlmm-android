package com.jimei.xiaolumeimei.model;

import com.jimei.xiaolumeimei.entities.AddressBean;
import com.jimei.xiaolumeimei.entities.AddressResultBean;
import com.jimei.xiaolumeimei.rx.DefaultTransform;
import com.jimei.xiaolumeimei.xlmmService.XlmmRetrofitClient;
import java.util.List;
import rx.Observable;

/**
 * Created by itxuye(www.itxuye.com) on 2016/01/19.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class AddressModel {

  //获取地址列表
  public Observable<List<AddressBean>> getAddressList() {
    return XlmmRetrofitClient.getService()
        .getAddressList()
        .compose(new DefaultTransform<>());
  }

  //获取某个地址
  public Observable<AddressBean> getOneAddressList() {
    return XlmmRetrofitClient.getService()
        .getOneAddressList()
        .compose(new DefaultTransform<>());
  }

  //创建新的地址
  public Observable<AddressResultBean> create_address(String receiver_state,String
      receiver_city,
      String receiver_district, String receiver_address, String receiver_name,
      String receiver_mobile) {

    return XlmmRetrofitClient.getService()
        .create_address(receiver_state,receiver_city, receiver_district, receiver_address,
            receiver_name, receiver_mobile)
        .compose(new DefaultTransform<>());
  }
}
