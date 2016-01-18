package com.jimei.xiaolumeimei.model;

import com.jimei.xiaolumeimei.entities.AllOrdersBean;
import com.jimei.xiaolumeimei.entities.OrderDetailBean;
import com.jimei.xiaolumeimei.rx.DefaultTransform;
import com.jimei.xiaolumeimei.xlmmService.XlmmRetrofitClient;
import com.squareup.okhttp.ResponseBody;
import rx.Observable;

/**
 * Created by 优尼世界 on 15/12/29.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class TradeModel {

  //得到全部订单数据列表
  public Observable<AllOrdersBean> getAlloderBean() {
    return XlmmRetrofitClient.getService()
        .getAllOdersList()
        .compose(new DefaultTransform<>());
  }

  //创建订单
  public Observable<ResponseBody> shoppingcart_create(String cart_ids, String addr_id,
      String channel, String payment, String post_fee, String discount_fee,
      String total_fee, String uuid) {
    return XlmmRetrofitClient.getService()
        .shoppingcart_create(cart_ids, addr_id, channel, payment, post_fee, discount_fee,
            total_fee, uuid)
        .compose(new DefaultTransform<>());
  }

  public Observable<OrderDetailBean> getOrderDetailBean(int order_id){
    return XlmmRetrofitClient.getService()
            .getOrderDetail(order_id)
            .compose(new DefaultTransform<>());
  }
}
