package com.jimei.xiaolumeimei.model;

import com.jimei.xiaolumeimei.entities.AddCartsBean;
import com.jimei.xiaolumeimei.entities.CartsPayinfoBean;
import com.jimei.xiaolumeimei.entities.CartsinfoBean;
import com.jimei.xiaolumeimei.rx.DefaultTransform;
import com.jimei.xiaolumeimei.xlmmService.XlmmRetrofitClient;
import java.util.List;
import rx.Observable;

/**
 * Created by 优尼世界 on 2016/01/15.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class CartsModel {

  //添加购物车
  public Observable<AddCartsBean> addCarts(String itemId, String skuId) {

    return XlmmRetrofitClient.getService()
        .addCarts(itemId, skuId)
        .compose(new DefaultTransform<>());
  }

  //获取购物车列表信息
  public Observable<List<CartsinfoBean>> getCartsList() {
    return XlmmRetrofitClient.getService()
        .getCartsList()
        .compose(new DefaultTransform<>());
  }

  //获取购物车列表信息
  public Observable<CartsPayinfoBean> getCartsInfoList(String cart_ids) {
    return XlmmRetrofitClient.getService()
        .getCartsPayInfoList(cart_ids)
        .compose(new DefaultTransform<>());
  }
}
