package com.jimei.xiaolumeimei.model;

import com.jimei.xiaolumeimei.entities.ChooseResponseBean;
import com.jimei.xiaolumeimei.entities.MMChooselistBean;
import com.jimei.xiaolumeimei.rx.DefaultTransform;
import com.jimei.xiaolumeimei.xlmmService.XlmmRetrofitClient;
import java.util.List;
import rx.Observable;

/**
 * Created by itxuye(www.itxuye.com) on 2016/02/14.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class MMProductModel {
  private static MMProductModel ourInstance = new MMProductModel();

  private MMProductModel() {
  }

  public static MMProductModel getInstance() {
    return ourInstance;
  }

  //得到默认选品列表
  public Observable<List<MMChooselistBean>> getMMChooseList() {
    return XlmmRetrofitClient.getService()
        .getMMChooseList()
        .compose(new DefaultTransform<>());
  }

  //得到默认选品列表排序
  public Observable<List<MMChooselistBean>> getMMChooseSortList(String sort_field) {
    return XlmmRetrofitClient.getService()
        .getMMChooseSortList(sort_field)
        .compose(new DefaultTransform<>());
  }

  //选品女装或者童装列表
  public Observable<List<MMChooselistBean>> getMMChooseLadyOrChildList(String category) {
    return XlmmRetrofitClient.getService()
        .getMMChooseLadyOrChildList(category)
        .compose(new DefaultTransform<>());
  }

  //选品女装或者童装列表排序
  public Observable<List<MMChooselistBean>> getMMChooseLadyOrChildSortListSort(
      String sort_field, String category) {
    return XlmmRetrofitClient.getService()
        .getMMChooseLadyOrChildSortListSort(sort_field, category)
        .compose(new DefaultTransform<>());
  }

  //MM上架商品到商铺
  public Observable<ChooseResponseBean> add_pro_to_shop(String product) {
    return XlmmRetrofitClient.getService()
        .add_pro_to_shop(product)
        .compose(new DefaultTransform<>());
  }

  //MM下架商品
  public Observable<ChooseResponseBean> remove_pro_from_shop(String product) {
    return XlmmRetrofitClient.getService()
        .remove_pro_from_shop(product)
        .compose(new DefaultTransform<>());
  }
}
