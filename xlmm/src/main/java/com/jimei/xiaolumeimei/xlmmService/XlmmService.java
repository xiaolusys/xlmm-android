package com.jimei.xiaolumeimei.xlmmService;

import com.jimei.xiaolumeimei.data.XlmmApi;
import com.jimei.xiaolumeimei.entities.AddCartsBean;
import com.jimei.xiaolumeimei.entities.AllOrdersBean;
import com.jimei.xiaolumeimei.entities.AllRefundsBean;
import com.jimei.xiaolumeimei.entities.CartsPayinfoBean;
import com.jimei.xiaolumeimei.entities.CartsinfoBean;
import com.jimei.xiaolumeimei.entities.ChildListBean;
import com.jimei.xiaolumeimei.entities.IndexBean;
import com.jimei.xiaolumeimei.entities.LadyListBean;
import com.jimei.xiaolumeimei.entities.OrderDetailBean;
import com.jimei.xiaolumeimei.entities.PostBean;
import com.jimei.xiaolumeimei.entities.ProductBean;
import com.jimei.xiaolumeimei.entities.ProductDetailBean;
import com.jimei.xiaolumeimei.entities.ProductListBean;
import com.jimei.xiaolumeimei.entities.UserBean;
import com.squareup.okhttp.ResponseBody;
import java.util.List;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by 优尼世界 on 15/12/29.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public interface XlmmService {

  //@formatter:off

  @FormUrlEncoded
  @POST("register/customer_login")
  Observable<UserBean> login(
      @Field("username") String username,
      @Field("password") String password);

  //童装列表
  @GET(XlmmApi.CHILD_URL)
  Observable<ChildListBean> getChildList();

  //女装列表
  @GET(XlmmApi.WOMAN_URL)
  Observable<LadyListBean> getLadyList();

  //今日列表
  @GET(XlmmApi.TODAY_URL)
  Observable<ProductListBean> getTodayList(
      @Query("page") int page,
      @Query("page_size") int page_size);

  //今日海报列表
  @GET(XlmmApi.TODAY_POSTER_URL)
  Observable<PostBean> getTodayPost();

  //昨日海报列表
  @GET(XlmmApi.YESTERDAY_POSTER_URL)
  Observable<PostBean> getYestDayPost();

  //昨日列表
  @GET(XlmmApi.YESTERDAY_URL)
  Observable<IndexBean> getYestDayList();

  //同款列表
  @GET(XlmmApi.TONGKUAN_URL+"{model_id}")
  Observable<List<ProductBean>> getTongKuanList(
      @Path("model_id")int model_id);

  //获取所有订单
  @GET(XlmmApi.ALL_ORDERS_URL)
  Observable<AllOrdersBean> getAllOdersList();


  //获得商品详情页面数据
  @GET(XlmmApi.PRODUCT_URL+"{id}/details")
  Observable<ProductDetailBean> getProductDetails(
      @Path("id")String id);


  //添加购物车
  @FormUrlEncoded
  @POST("carts")
  Observable<AddCartsBean> addCarts(

      @Field("item_id") String itemId,
      @Field("sku_id")  String skuId
  );


  //获取购物车信息

  @GET("carts")
  Observable<List<CartsinfoBean>> getCartsList();

    //获取购物信息列表
  @GET("carts/carts_payinfo")
  Observable<CartsPayinfoBean> getCartsPayInfoList(
      @Query("cart_ids") String cart_ids);


  //创建订单接口

  @FormUrlEncoded
  @POST("trades/shoppingcart_create")
  Observable<ResponseBody> shoppingcart_create(
      @Field("cart_ids")     String cart_ids,
      @Field("addr_id")      String addr_id,
      @Field("channel")      String channel,
      @Field("payment")      String payment,
      @Field("post_fee")     String post_fee,
      @Field("discount_fee") String discount_fee,
      @Field("total_fee")    String total_fee,
      @Field("uuid")         String uuid
  );

  //获得订单数据
  @GET(XlmmApi.ALL_ORDERS_URL+"/{pk}/details")
  Observable<OrderDetailBean> getOrderDetail(
          @Path("pk")int order_id);

  //获取所有退货订单
  @GET(XlmmApi.ALL_REFUNDS_URL)
  Observable<AllRefundsBean> getAllRedundsList();


}