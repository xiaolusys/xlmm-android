package com.jimei.xiaolumeimei.model;

import com.jimei.library.rx.DefaultTransform;
import com.jimei.xiaolumeimei.entities.ChildListBean;
import com.jimei.xiaolumeimei.entities.CollectionAllBean;
import com.jimei.xiaolumeimei.entities.CollectionDeleteBody;
import com.jimei.xiaolumeimei.entities.CollectionResultBean;
import com.jimei.xiaolumeimei.entities.LadyListBean;
import com.jimei.xiaolumeimei.entities.PortalBean;
import com.jimei.xiaolumeimei.entities.ProductBean;
import com.jimei.xiaolumeimei.entities.ProductDetailBean;
import com.jimei.xiaolumeimei.entities.ProductListBean;
import com.jimei.xiaolumeimei.entities.ShareProductBean;
import com.jimei.xiaolumeimei.xlmmService.XlmmRetrofitClient;

import java.util.List;
import rx.Observable;

/**
 * Created by 优尼世界 on 15/12/29.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class ProductModel {

  private static ProductModel ourInstance = new ProductModel();

  private ProductModel() {
  }

  public static ProductModel getInstance() {
    return ourInstance;
  }


  //得到今日数据
  public Observable<ProductListBean> getTodayList(int page, int page_size) {

    return XlmmRetrofitClient.getService()
        .getTodayList(page, page_size)
        .compose(new DefaultTransform<>());
  }

  //得到昨日分页数据
  public Observable<ProductListBean> getPreviousList(int page, int page_size) {

    return XlmmRetrofitClient.getService()
        .getPreviousList(page, page_size)
        .compose(new DefaultTransform<>());
  }


  //得到明日分页数据
  public Observable<ProductListBean> getAdvanceList(int page, int page_size) {

    return XlmmRetrofitClient.getService()
        .getAdvanceList(page, page_size)
        .compose(new DefaultTransform<>());
  }

  //得到儿童分页数据
  public Observable<ChildListBean> getChildList(int page, int page_size) {

    return XlmmRetrofitClient.getService()
        .getChildList(page, page_size)
        .compose(new DefaultTransform<>());
  }
  //得到儿童分页数据
  public Observable<ChildListBean> getChildList(int page, int page_size,String orderBy) {

    return XlmmRetrofitClient.getService()
        .getChildList(page, page_size,orderBy)
        .compose(new DefaultTransform<>());
  }

  //得到女装分页数据
  public Observable<LadyListBean> getLadyList(int page, int page_size) {

    return XlmmRetrofitClient.getService()
        .getLadyList(page, page_size)
        .compose(new DefaultTransform<>());
  }

  //得到女装分页数据,升价排序
  public Observable<LadyListBean> getLadyList(int page, int page_size,String order_by) {
    return XlmmRetrofitClient.getService()
            .getLadyList(page, page_size,order_by)
            .compose(new DefaultTransform<>());
  }

  //得到同款数据列表
  public Observable<List<ProductBean>> getTongkuanList(int model_id) {

    return XlmmRetrofitClient.getService()
        .getTongKuanList(model_id)
        .compose(new DefaultTransform<>());
  }

  //得到商品详情
  public Observable<ProductDetailBean> getProductDetails(String id) {

    return XlmmRetrofitClient.getService()
        .getProductDetails(id)
        .compose(new DefaultTransform<>());
  }

  //得到商品详情share link
  public Observable<ShareProductBean> getProductShareInfo(String product_id) {

    return XlmmRetrofitClient.getService()
        .getProductShareInfo(product_id)
        .compose(new DefaultTransform<>());
  }

  //得到主页Portal数据
  public Observable<PortalBean> getPortalBean(){

    return XlmmRetrofitClient.getService()
            .getPortalBean()
            .compose(new DefaultTransform<>());
  }

  //获得收藏列表
  public Observable<CollectionAllBean> getCollection(int page){
    return XlmmRetrofitClient.getService()
            .getCollection(page)
            .compose(new DefaultTransform<>());
  }

  //添加单品收藏
  public Observable<CollectionResultBean> addCollection(int model_id){
    return XlmmRetrofitClient.getService()
            .addCollection(new CollectionDeleteBody(model_id))
            .compose(new DefaultTransform<>());
  }

  //删除单品收藏
  public Observable<CollectionResultBean> deleteCollection(int model_id){
    return XlmmRetrofitClient.getService()
            .deleteCollection(new CollectionDeleteBody(model_id))
            .compose(new DefaultTransform<>());
  }
}
