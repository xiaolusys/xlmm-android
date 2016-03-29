package com.jimei.xiaolumeimei.xlmmService;

import com.jimei.xiaolumeimei.data.XlmmApi;
import com.jimei.xiaolumeimei.entities.ActivityBean;
import com.jimei.xiaolumeimei.entities.AddCartsBean;
import com.jimei.xiaolumeimei.entities.AddressBean;
import com.jimei.xiaolumeimei.entities.AddressResultBean;
import com.jimei.xiaolumeimei.entities.AgentInfoBean;
import com.jimei.xiaolumeimei.entities.AllOrdersBean;
import com.jimei.xiaolumeimei.entities.AllRefundsBean;
import com.jimei.xiaolumeimei.entities.AllowanceBean;
import com.jimei.xiaolumeimei.entities.AwardCarryBean;
import com.jimei.xiaolumeimei.entities.BindInfoBean;
import com.jimei.xiaolumeimei.entities.BudgetPayBean;
import com.jimei.xiaolumeimei.entities.BudgetdetailBean;
import com.jimei.xiaolumeimei.entities.CarryLogListBean;
import com.jimei.xiaolumeimei.entities.CartsNumResultBean;
import com.jimei.xiaolumeimei.entities.CartsPayinfoBean;
import com.jimei.xiaolumeimei.entities.CartsinfoBean;
import com.jimei.xiaolumeimei.entities.ChildListBean;
import com.jimei.xiaolumeimei.entities.ClickcarryBean;
import com.jimei.xiaolumeimei.entities.CouponBean;
import com.jimei.xiaolumeimei.entities.IndexBean;
import com.jimei.xiaolumeimei.entities.LadyListBean;
import com.jimei.xiaolumeimei.entities.LogOutBean;
import com.jimei.xiaolumeimei.entities.MMChooselistBean;
import com.jimei.xiaolumeimei.entities.MMShoppingBean;
import com.jimei.xiaolumeimei.entities.MMStoreBean;
import com.jimei.xiaolumeimei.entities.MMVisitorsBean;
import com.jimei.xiaolumeimei.entities.MamaFansBean;
import com.jimei.xiaolumeimei.entities.MamaFortune;
import com.jimei.xiaolumeimei.entities.MamaLivenessBean;
import com.jimei.xiaolumeimei.entities.MembershipPointBean;
import com.jimei.xiaolumeimei.entities.NeedSetInfoBean;
import com.jimei.xiaolumeimei.entities.NicknameBean;
import com.jimei.xiaolumeimei.entities.NinePicBean;
import com.jimei.xiaolumeimei.entities.OderCarryBean;
import com.jimei.xiaolumeimei.entities.OneDayAgentOrdersBean;
import com.jimei.xiaolumeimei.entities.OrderDetailBean;
import com.jimei.xiaolumeimei.entities.PayInfoBean;
import com.jimei.xiaolumeimei.entities.PointLogBean;
import com.jimei.xiaolumeimei.entities.PostActivityBean;
import com.jimei.xiaolumeimei.entities.PostBean;
import com.jimei.xiaolumeimei.entities.ProductBean;
import com.jimei.xiaolumeimei.entities.ProductDetailBean;
import com.jimei.xiaolumeimei.entities.ProductListBean;
import com.jimei.xiaolumeimei.entities.QiniuTokenBean;
import com.jimei.xiaolumeimei.entities.RecentCarryBean;
import com.jimei.xiaolumeimei.entities.RegisterBean;
import com.jimei.xiaolumeimei.entities.ResponseResultBean;
import com.jimei.xiaolumeimei.entities.ShareProductBean;
import com.jimei.xiaolumeimei.entities.ShoppingListBean;
import com.jimei.xiaolumeimei.entities.SmsLoginBean;
import com.jimei.xiaolumeimei.entities.SmsLoginUserBean;
import com.jimei.xiaolumeimei.entities.UserAccountBean;
import com.jimei.xiaolumeimei.entities.UserBean;
import com.jimei.xiaolumeimei.entities.UserInfoBean;
import com.jimei.xiaolumeimei.entities.UserWithdrawResult;
import com.jimei.xiaolumeimei.entities.WithdrawCashHisBean;
import com.jimei.xiaolumeimei.entities.WxLogininfoBean;
import com.jimei.xiaolumeimei.entities.WxPubAuthInfo;
import com.squareup.okhttp.ResponseBody;
import java.util.List;
import retrofit.http.Body;
import retrofit.http.DELETE;
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
 * <p>
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

  //童装分页列表
    @GET(XlmmApi.CHILD_URL)
    Observable<ChildListBean> getChildList(
            @Query("page") int page,
            @Query("page_size") int page_size);


    //女装列表
    @GET(XlmmApi.WOMAN_URL)
    Observable<LadyListBean> getLadyList();

  //昨日分页列表
    @GET(XlmmApi.WOMAN_URL)
    Observable<LadyListBean> getLadyList(
            @Query("page") int page,
            @Query("page_size") int page_size);


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

  //昨日分页列表
    @GET("products/promote_previous_paging")
    Observable<ProductListBean> getPreviousList(
            @Query("page") int page,
            @Query("page_size") int page_size);

    //同款列表
    @GET(XlmmApi.TONGKUAN_URL + "{model_id}")
    Observable<List<ProductBean>> getTongKuanList(
            @Path("model_id") int model_id);

    //获取所有订单
    @GET(XlmmApi.ALL_ORDERS_URL)
    Observable<AllOrdersBean> getAllOdersList(
            @Query("page") String page);


    //获得商品详情页面数据
    @GET(XlmmApi.PRODUCT_URL + "{id}/details")
    Observable<ProductDetailBean> getProductDetails(
            @Path("id") String id);


    //添加购物车
    @FormUrlEncoded
    @POST("carts")
    Observable<AddCartsBean> addCarts(

            @Field("item_id") String itemId,
            @Field("sku_id") String skuId
    );


    //获取购物车信息

    @GET("carts")
    Observable<List<CartsinfoBean>> getCartsList();

    //获取购物信息列表
    @GET("carts/carts_payinfo")
    Observable<CartsPayinfoBean> getCartsPayInfoList(
            @Query("cart_ids") String cart_ids);


    //获取购物信息列表
    @GET("carts/carts_payinfo")
    Observable<CartsPayinfoBean> getCartsPayInfoList(
            @Query("cart_ids") String cart_ids,
            @Query("coupon_id") String coupon_id
    );


    //创建订单接口

    @FormUrlEncoded
    @POST("trades/shoppingcart_create")
    Observable<ResponseBody> shoppingcart_create(
            @Field("cart_ids") String cart_ids,
            @Field("addr_id") String addr_id,
            @Field("channel") String channel,
            @Field("payment") String payment,
            @Field("post_fee") String post_fee,
            @Field("discount_fee") String discount_fee,
            @Field("total_fee") String total_fee,
            @Field("pay_extras")String pay_extras,
            @Field("uuid") String uuid
    );


  @FormUrlEncoded
  @POST(XlmmApi.APP_BASE_URL+"/rest/v2/trades/shoppingcart_create")
     Observable<PayInfoBean> shoppingcart_create_v2(
            @Field("cart_ids") String cart_ids,
            @Field("addr_id") String addr_id,
            @Field("channel") String channel,
            @Field("payment") String payment,
            @Field("post_fee") String post_fee,
            @Field("discount_fee") String discount_fee,
            @Field("total_fee") String total_fee,
            @Field("uuid") String uuid,
            @Field("pay_extras")String pay_extras
    );



    @FormUrlEncoded
    @POST("trades/shoppingcart_create")
    Observable<BudgetPayBean> shoppingcart_createBudget(
            @Field("cart_ids") String cart_ids,
            @Field("addr_id") String addr_id,
            @Field("channel") String channel,
            @Field("payment") String payment,
            @Field("post_fee") String post_fee,
            @Field("discount_fee") String discount_fee,
            @Field("total_fee") String total_fee,
            @Field("pay_extras")String pay_extras,
            @Field("uuid") String uuid
    );

  //使用优惠券
    @FormUrlEncoded
    @POST("trades/shoppingcart_create")
    Observable<ResponseBody> shoppingcart_create_with_coupon(
            @Field("cart_ids") String cart_ids,
            @Field("addr_id") String addr_id,
            @Field("channel") String channel,
            @Field("payment") String payment,
            @Field("post_fee") String post_fee,
            @Field("discount_fee") String discount_fee,
            @Field("total_fee") String total_fee,
            @Field("pay_extras")String pay_extras,
            @Field("uuid") String uuid,
            @Field("coupon_id") String coupon_id
    );

  //使用优惠券
    @FormUrlEncoded
    @POST("trades/shoppingcart_create")
    Observable<BudgetPayBean> shoppingcart_createBudget_with_coupon(
            @Field("cart_ids") String cart_ids,
            @Field("addr_id") String addr_id,
            @Field("channel") String channel,
            @Field("payment") String payment,
            @Field("post_fee") String post_fee,
            @Field("discount_fee") String discount_fee,
            @Field("total_fee") String total_fee,
            @Field("pay_extras")String pay_extras,
            @Field("uuid") String uuid,
            @Field("coupon_id") String coupon_id
    );

    //立即支付订单接口

    @POST("trades/{pk}/charge")
    Observable<ResponseBody> shoppingcart_paynow(
        @Path("pk") int order_id

    );

    //获得订单数据
    @GET(XlmmApi.ALL_ORDERS_URL + "/{pk}/details")
    Observable<OrderDetailBean> getOrderDetail(
            @Path("pk") int order_id);

    //获取所有待支付订单
    @GET(XlmmApi.WAITPAY_URL)
    Observable<AllOrdersBean> getWaitPayOrdersBean(
            @Query("page")String page);

    //获取所有待发货订单
    @GET(XlmmApi.WAITSEND_URL)
    Observable<AllOrdersBean> getWaitSendOrdersBean(
            @Query("page")String page);

    //获取所有退货订单
    @GET(XlmmApi.ALL_REFUNDS_URL)
    Observable<AllRefundsBean> getAllRedundsList(
            @Query("page")String page);


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
            @Field("receiver_state") String receiver_state,
            @Field("receiver_city") String receiver_city,
            @Field("receiver_district") String receiver_district,
            @Field("receiver_address") String receiver_address,
            @Field("receiver_name") String receiver_name,
            @Field("receiver_mobile") String receiver_mobile
    );

    //修改地址
    @FormUrlEncoded
    @POST("address/{id}/update")
    Observable<AddressResultBean> update_address(
            @Path("id")String id,
            @Field("receiver_state") String receiver_state,
            @Field("receiver_city") String receiver_city,
            @Field("receiver_district") String receiver_district,
            @Field("receiver_address") String receiver_address,
            @Field("receiver_name") String receiver_name,
            @Field("receiver_mobile") String receiver_mobile
    );


    //获取地址列表
    @GET("address")
    Observable<List<AddressBean>> getAddressList();

    //获取某一个地址
    @GET("address")
    Observable<AddressBean> getOneAddressList();

    //删除某一个地址
    @POST("address/{id}/delete_address")
    Observable<AddressResultBean> delete_address(
        @Path("id")String id
    );

    //设置默认地址
    @POST("address/{id}/change_default")
    Observable<AddressResultBean> change_default(
        @Path("id")String id
    );

    //获取用户信息
    @GET(XlmmApi.USERINFO_URL)
    Observable<UserInfoBean> getUserInfo();


    //投诉建议
    @FormUrlEncoded
    @POST("complain")
    Observable<AddressResultBean> complain(
            @Field("com_content") String com_content);

    //设置用户昵称
    @PATCH("users" + "/{id}")
    Observable<UserBean> setNickname(
            @Path("id") int id,
            @Body NicknameBean nickname);

    //修改用户密码
    @FormUrlEncoded
    @POST(XlmmApi.CHANGE_USER_PASSWORD_URL)
    Observable<UserBean> changePassword(

            @Field("username") String username,
            @Field("valid_code") String valid_code,
            @Field("password1") String password1,
            @Field("password2") String password2
    );

    //获取修改密码时验证码
    @FormUrlEncoded
    @POST("register/change_pwd_code")
    Observable<RegisterBean> getChgPasswordCheckCode(
            @Field("vmobile") String vmobile
    );


    @POST("users/customer_logout")
    Observable<LogOutBean> customer_logout();

    //获取用户积分信息
    @GET(XlmmApi.MEMBERSHIPPOINT_URL)
    Observable<MembershipPointBean> getMembershipPointBean();


    //获取用户信息
    @GET("users/profile")
    Observable<UserInfoBean> getProfile();

    //获取用户积分记录信息
    @GET(XlmmApi.MEMBERSHIPPOINTLOG_URL)
    Observable<PointLogBean> getPointLogBean();

    //获取用户未使用优惠券信息
    @GET(XlmmApi.COUPON_URL)
    Observable<CouponBean> getUnusedCouponBean();

    //获取用户过期优惠券信息
    @GET(XlmmApi.COUPON_URL+"/list_past_coupon")
    Observable<CouponBean> getPastCouponBean();

    //获取短信登录验证码
    @FormUrlEncoded
    @POST("register/send_code")
    Observable<SmsLoginBean> getSmsCheckCode(
            @Field("mobile") String mobile
    );


  //短信登录
    @FormUrlEncoded
    @POST("register/sms_login")
    Observable<SmsLoginUserBean> smsLogin(
            @Field("mobile") String username,
            @Field("sms_code") String valid_code
    );


  //购物车增加一件
    @POST("carts/{id}/plus_product_carts")
    Observable<ResponseBody> plus_product_carts(
          @Path("id")String id
    );

  //购物车删除一件
    @POST("carts/{id}/minus_product_carts")
    Observable<ResponseBody> minus_product_carts(
          @Path("id")String id
    );

  //删除一列
    @POST("carts/{id}/delete_carts")
    Observable<ResponseBody> delete_carts(
          @Path("id")String id
    );

    //设置用户昵称
    @POST("order/{id}/confirm_sign")
    Observable<UserBean> receiveGoods(
            @Path("id") int id);


    //获得详细退款单数据
    @GET( "refunds/{pk}")
    Observable<AllRefundsBean.ResultsEntity> getRefundDetailBean(
            @Path("pk") int order_id);

    //删除订单数据
    @DELETE( "trades/{pk}")
    Observable<ResponseBody> delRefund(
            @Path("pk") int order_id);

    //创建退款单接口

    @FormUrlEncoded
    @POST("refunds")
    Observable<ResponseBody> refund_create(
        @Field("id") int goods_id,
        @Field("reason") int reason,
        @Field("num") int num,
        @Field("sum_price") double sum_price,
        @Field("description") String description,
        @Field("proof_pic") String proof_pic

    );

  //修改退款单
  @FormUrlEncoded
  @POST("refunds")
  Observable<ResponseBody> update_refund_info(
      @Field("id") int goods_id,
      @Field("modify") int type,
      @Field("reason") int reason,
      @Field("num") int num,
      @Field("sum_price") double sum_price,
      @Field("description") String description
  );

  //添加退款物流信息
  @FormUrlEncoded
  @POST("refunds")
  Observable<ResponseBody> commit_logistics_info(
      @Field("id") int goods_id,
      @Field("modify") int type,
      @Field("company") String company,
      @Field("sid") String sid
  );

  @GET("carts/show_carts_num")
  Observable<CartsNumResultBean> show_carts_num(
  );

  @GET("refunds/qiniu_token")
  Observable<QiniuTokenBean> getQiniuToken(
  );

  @FormUrlEncoded
  @POST("register/wxapp_login")
  Observable<WxLogininfoBean> wxapp_login(
      @Query("noncestr")   String noncestr,
      @Query("timestamp")  String timestamp,
      @Query("sign")       String sign,
      @Field("headimgurl")String headimgurl,
      @Field("nickname")String nickname,
      @Field("openid")String openid,
      @Field("unionid")String unionid
  );

  @GET("pmt/xlmm/agency_info")
  Observable<AgentInfoBean> getAgentInfoBean(
  );

  @GET("pmt/cashout")
  Observable<WithdrawCashHisBean> getWithdrawCashHis();

  //获取粉丝列表
  @GET(XlmmApi.APP_BASE_URL+ "/rest/v2/mama/fans")
  Observable<MamaFansBean> getMamaFans(
      @Query("page")String page
  );
  //获取访客列表
  @GET(XlmmApi.APP_BASE_URL+ "/rest/v2/mama/visitor")
  Observable<MMVisitorsBean> getMamavisitor(
      @Query("from")String from,
      @Query("page")String page
  );

  //创建提款单信息
  @FormUrlEncoded
  @POST("pmt/cashout")
  Observable<ResponseResultBean> withdraw_cash(
      @Field("choice") String fund_type );

  //cancel提款单信息
  @FormUrlEncoded
  @POST("pmt/cashout")
  Observable<ResponseResultBean> cancel_withdraw_cash(
      @Field("id") String id );

  //选品默认列表
    @GET("products/my_choice_pro")
    Observable<List<MMChooselistBean>> getMMChooseList();

  //我的店铺列表
    @GET("pmt/cushoppros")
    Observable<List<MMStoreBean>> getMMStoreList();

  //选品默认列表排序
    @GET("products/my_choice_pro")
    Observable<List<MMChooselistBean>> getMMChooseSortList(
          @Query("sort_field")String sort_field
    );

  //选品女装或者童装列表
    @GET("products/my_choice_pro")
    Observable<List<MMChooselistBean>> getMMChooseLadyOrChildList(

        @Query("category")String category
    );

  //选品排序列表
    @GET("products/my_choice_pro")
    Observable<List<MMChooselistBean>> getMMChooseLadyOrChildSortListSort(
        @Query("sort_field")String sort_field,
        @Query("category")String category
    );

  //MM上架商品到商铺
  @FormUrlEncoded
  @POST("pmt/cushoppros/add_pro_to_shop")
  Observable<ResponseResultBean> add_pro_to_shop(
       @Field("product")String product
  );

  //MM下架商品
  @FormUrlEncoded
  @POST("pmt/cushoppros/remove_pro_from_shop")
  Observable<ResponseResultBean> remove_pro_from_shop (
        @Field("product")String product
  );

  @GET("users/need_set_info")
  Observable<NeedSetInfoBean> need_set_info(
  );

  //绑定手机获取验证码
  @FormUrlEncoded
  @POST("users/bang_mobile_code")
  Observable<BindInfoBean> bang_mobile_code(
      @Field("vmobile")String vmobile
  );

  @FormUrlEncoded
  @POST("users/bang_mobile")
  Observable<BindInfoBean> bang_mobile(
      @Field("username")   String username,
      @Field("password1")  String password1,
      @Field("password2")  String password2,
      @Field("valid_code") String valid_code
  );

  @FormUrlEncoded
  @POST("users/bang_mobile_unpassword")
  Observable<BindInfoBean> bang_mobile_unpassword(
      @Field("username")   String username,
      @Field("valid_code") String valid_code
  );


   //MM订单历史
    @GET("pmt/shopping")
    Observable<ShoppingListBean> getShoppingList(

        @Query("page")String page
    );

   //MM历史收益
    @GET("pmt/carrylog")
    Observable<CarryLogListBean> getCarrylogList(

        @Query("page")String page
    );


  @GET("pmt/carrylog/get_clk_list")
  Observable<AllowanceBean> getAllowance(
      @Query("page")String page
  );


  //活动内容分享
  @FormUrlEncoded
  @POST("pmt/free_order/get_share_content")
  Observable<ActivityBean> get_share_content(
    @Field("ufrom")String ufrom
  );

  //活动内容分享
  @GET("activitys/{id}/get_share_params")
  Observable<ActivityBean> get_party_share_content( @Path("id")String id );

  //获得one day小鹿妈妈订单记录
  @GET( "pmt/shopping/shops_by_day")
  Observable<OneDayAgentOrdersBean> getOneDayAgentOrders(
      @Query("days") String day);

  //获得one day小鹿妈妈订单记录
  @GET( "pmt/shopping/days_num")
  Observable<List<Integer>> getLatestAgentOrders(
      @Query("days") String day);

  //获得商品详情共享页面信息
  @GET("share/product")
  Observable<ShareProductBean> getProductShareInfo(
      @Query("product_id") String product_id);

  //get push useraccount
  @FormUrlEncoded
  @POST("push/set_device")
  Observable<UserAccountBean> getUserAccount(
      @Field("platform")String platform,
      @Field("regid")String regid,
      @Field("device_id")String device_id
  );

   @GET("users/get_budget_detail")
  Observable<BudgetdetailBean> budGetdetailBean(
           @Query("page")String page
   );

  @GET("pmt/ninepic")
  Observable<List<NinePicBean>> getNinepic();

  @GET("users/get_wxpub_authinfo")
  Observable<WxPubAuthInfo> getWxPubAuthInfo();

  //创建提款信息
  @FormUrlEncoded
  @POST("users/budget_cash_out")
  Observable<UserWithdrawResult> user_withdraw_cash(
      @Field("cashout_amount") String amount );

   @GET("pmt/cushop/customer_shop")
  Observable<MMShoppingBean> getShareShopping();

  @GET(XlmmApi.APP_BASE_URL+"/rest/v2/mama/fortune")
  Observable<MamaFortune> getMamaFortune();

  @GET(XlmmApi.APP_BASE_URL+"/rest/v2/mama/activevalue")
  Observable<MamaLivenessBean> getMamaLiveness(
          @Query("page")String page);

  @GET(XlmmApi.APP_BASE_URL+"/rest/v2/mama/carry")
  Observable<CarryLogListBean> getMamaAllCarryLogs(
       @Query("page")String page
  );
  @GET(XlmmApi.APP_BASE_URL+"/rest/v2/mama/ordercarry")
  Observable<OderCarryBean> getMamaAllOderCarryLogs(
       @Query("page")String page
  );

  @GET(XlmmApi.APP_BASE_URL+"/rest/v2/mama/ordercarry")
  Observable<OderCarryBean> getMamaAllOderCarryLogs(
       @Query("carry_type")String carry_type,
       @Query("page")String page
  );
  @GET(XlmmApi.APP_BASE_URL+"/rest/v2/mama/awardcarry")
  Observable<AwardCarryBean> getMamaAllAwardCarryLogs(
       @Query("page")String page
  );
  @GET(XlmmApi.APP_BASE_URL+"/rest/v2/mama/clickcarry")
  Observable<ClickcarryBean> getMamaAllClickCarryLogs(
       @Query("page")String page
  );

  //获得recent days小鹿妈妈订单和收益记录
  @GET( XlmmApi.APP_BASE_URL+"/rest/v2/mama/dailystats")
  Observable<RecentCarryBean> getRecentCarry(
          @Query("from") String from,
          @Query("days") String day);

  @GET("activitys")
  Observable<List<PostActivityBean>> getPostActivity(
  );

  @FormUrlEncoded
  @POST("usercoupons")
  Observable<ResponseBody> getUsercoupons(
      @Field("template_id")String template_id
  );



}
