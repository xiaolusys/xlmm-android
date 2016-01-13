package com.jimei.xiaolumeimei.model;

import com.jimei.xiaolumeimei.entities.ChildListBean;
import com.jimei.xiaolumeimei.entities.IndexBean;
import com.jimei.xiaolumeimei.entities.LadyListBean;
import com.jimei.xiaolumeimei.entities.PostBean;
import com.jimei.xiaolumeimei.entities.ProductBean;
import com.jimei.xiaolumeimei.entities.ProductDetailBean;
import com.jimei.xiaolumeimei.entities.ProductListBean;
import com.jimei.xiaolumeimei.rx.DefaultTransform;
import com.jimei.xiaolumeimei.xlmmService.XlmmRetrofitClient;
import java.util.List;
import rx.Observable;

/**
 * Created by itxuye(www.itxuye.com) on 15/12/29.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class ProductModel {

  //得到儿童数据列表
  public Observable<ChildListBean> getChildList() {

    return XlmmRetrofitClient.getService()
        .getChildList()
        .compose(new DefaultTransform<>());
  }

  //得到今日数据
  public Observable<ProductListBean> getTodayList(int page, int page_size) {

    return XlmmRetrofitClient.getService()
        .getTodayList(page, page_size)
        .compose(new DefaultTransform<>());
  }

  //得到昨日数据
  public Observable<IndexBean> getPreviousList() {

    return XlmmRetrofitClient.getService()
        .getYestDayList()
        .compose(new DefaultTransform<>());
  }

  //得到女装数据列表
  public Observable<LadyListBean> getLadyList() {

    return XlmmRetrofitClient.getService()
        .getLadyList()
        .compose(new DefaultTransform<>());
  }

  //得到同款数据列表
  public Observable<List<ProductBean>> getTongkuanList(int model_id) {

    return XlmmRetrofitClient.getService()
        .getTongKuanList(model_id)
        .compose(new DefaultTransform<>());
  }

  //得到今日海报数据列表
  public Observable<PostBean> getTodayPost() {

    return XlmmRetrofitClient.getService()
        .getTodayPost()
        .compose(new DefaultTransform<>());
  }

  //得到昨日海报数据列表
  public Observable<PostBean> getYestdayPost() {

    return XlmmRetrofitClient.getService()
        .getYestDayPost()
        .compose(new DefaultTransform<>());
  }

  //得到商品详情
  public Observable<ProductDetailBean> getProductDetails(String id) {

    return XlmmRetrofitClient.getService()
        .getProductDetails(id)
        .compose(new DefaultTransform<>());
  }
}
