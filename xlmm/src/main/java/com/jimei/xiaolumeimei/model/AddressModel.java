package com.jimei.xiaolumeimei.model;

import com.jimei.xiaolumeimei.entities.AddressBean;
import com.jimei.xiaolumeimei.entities.AddressResultBean;
import com.jimei.library.rx.DefaultTransform;
import com.jimei.xiaolumeimei.xlmmService.XlmmRetrofitClient;
import java.util.List;
import rx.Observable;

/**
 * Created by itxuye(www.itxuye.com) on 2016/01/19.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class AddressModel {


  private static AddressModel ourInstance = new AddressModel();

  private AddressModel() {
  }

  public static AddressModel getInstance() {
    return ourInstance;
  }

  //获取地址列表
  public Observable<List<AddressBean>> getAddressList() {
    return XlmmRetrofitClient.getService()
        .getAddressList()
        .compose(new DefaultTransform<>());
  }

  //删除某个地址
  public Observable<AddressResultBean> delete_address(String id) {
    return XlmmRetrofitClient.getService()
        .delete_address(id)
        .compose(new DefaultTransform<>());
  }

  //创建新的地址
  public Observable<AddressResultBean> create_address(String receiver_state,
      String receiver_city, String receiver_district, String receiver_address,
      String receiver_name, String receiver_mobile,String defaulta) {
    return XlmmRetrofitClient.getService()
        .create_address(receiver_state, receiver_city, receiver_district,
            receiver_address, receiver_name, receiver_mobile,defaulta)
        .compose(new DefaultTransform<>());
  }

  //更新地址
  public Observable<AddressResultBean> update_address(String id,String receiver_state,
      String receiver_city, String receiver_district, String receiver_address,
      String receiver_name, String receiver_mobile,String defalut) {
    return XlmmRetrofitClient.getService()
        .update_address(id, receiver_state, receiver_city, receiver_district,
            receiver_address, receiver_name, receiver_mobile,defalut)
        .compose(new DefaultTransform<>());
  }

  //更新地址
  public Observable<AddressResultBean> update_address(String id,String receiver_state,
      String receiver_city, String receiver_district, String receiver_address,
      String receiver_name, String receiver_mobile,String logistic_company_code, String referal_trade_id) {
    return XlmmRetrofitClient.getService()
        .update_address(id, receiver_state, receiver_city, receiver_district,
            receiver_address, receiver_name, receiver_mobile,logistic_company_code,referal_trade_id)
        .compose(new DefaultTransform<>());
  }
}
