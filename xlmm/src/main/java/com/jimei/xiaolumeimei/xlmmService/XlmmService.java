package com.jimei.xiaolumeimei.xlmmService;

import com.jimei.xiaolumeimei.data.XlmmApi;
import com.jimei.xiaolumeimei.entities.AddCartsBean;
import com.jimei.xiaolumeimei.entities.AddressBean;
import com.jimei.xiaolumeimei.entities.AddressResultBean;
import com.jimei.xiaolumeimei.entities.AllOrdersBean;
import com.jimei.xiaolumeimei.entities.AllRefundsBean;
import com.jimei.xiaolumeimei.entities.CartsPayinfoBean;
import com.jimei.xiaolumeimei.entities.CartsinfoBean;
import com.jimei.xiaolumeimei.entities.ChildListBean;
import com.jimei.xiaolumeimei.entities.IndexBean;
import com.jimei.xiaolumeimei.entities.LadyListBean;
import com.jimei.xiaolumeimei.entities.LogOutBean;
import com.jimei.xiaolumeimei.entities.NicknameBean;
import com.jimei.xiaolumeimei.entities.OrderDetailBean;
import com.jimei.xiaolumeimei.entities.PostBean;
import com.jimei.xiaolumeimei.entities.ProductBean;
import com.jimei.xiaolumeimei.entities.ProductDetailBean;
import com.jimei.xiaolumeimei.entities.ProductListBean;
import com.jimei.xiaolumeimei.entities.RegisterBean;
import com.jimei.xiaolumeimei.entities.UserBean;
import com.jimei.xiaolumeimei.entities.UserInfoBean;
import com.squareup.okhttp.ResponseBody;
import java.util.List;

import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.PATCH;
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


    //获取注册验证码
  @FormUrlEncoded
  @POST("register")
  Observable<RegisterBean> getRegisterCheckCode(
     @Field("vmobile") String vmobile
  );

    //注册
  @FormUrlEncoded
  @POST("register/check_vcode")
  Observable<RegisterBean> check_code_user(
     @Field("mobile") String username,
     @Field("vcode") String valid_code
  );


    //创建新的地址
  @FormUrlEncoded
  @POST("address/create_address")
  Observable<AddressResultBean> create_address(
     @Field("receiver_state")       String receiver_state,
     @Field("receiver_city")        String receiver_city,
     @Field("receiver_district")    String receiver_district,
     @Field("receiver_address")     String receiver_address,
     @Field("receiver_name")        String receiver_name,
     @Field("receiver_mobile")      String receiver_mobile
  );


  //获取地址列表
  @GET("address")
  Observable<List<AddressBean>> getAddressList();

  //获取用户信息
  @GET(XlmmApi.USERINFO_URL)
  Observable<UserInfoBean> getUserInfo();




  //投诉建议
  @FormUrlEncoded
  @POST("complain")
  Observable<AddressResultBean> complain(
          @Field("com_content")String com_content );

    //设置用户昵称
    @PATCH(XlmmApi.USERINFO_URL+"/{id}")
  Observable<UserBean> setNickname(
          @Path("id")int id,
          @Body NicknameBean nickname);

    //修改用户密码
    @FormUrlEncoded
    @POST(XlmmApi.CHANGE_USER_PASSWORD_URL)
    Observable<UserBean> changePassword(

            @Field("username") String username,
            @Field("valid_code")  String valid_code,
            @Field("password1")  String password1,
            @Field("password2")  String password2
    );

    //获取修改密码时验证码
    @FormUrlEncoded
    @POST("register/change_pwd_code")
    Observable<RegisterBean> getChgPasswordCheckCode(
            @Field("vmobile") String vmobile
    );



  @POST("users/customer_logout")
  Observable<LogOutBean> customer_logout();

}
