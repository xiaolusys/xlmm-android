package com.jimei.xiaolumeimei.xlmmService;

import com.jimei.xiaolumeimei.entities.ActivityBean;
import com.jimei.xiaolumeimei.entities.AddressBean;
import com.jimei.xiaolumeimei.entities.AddressDownloadResultBean;
import com.jimei.xiaolumeimei.entities.AddressResultBean;
import com.jimei.xiaolumeimei.entities.AllOrdersBean;
import com.jimei.xiaolumeimei.entities.AllRefundsBean;
import com.jimei.xiaolumeimei.entities.AllowanceBean;
import com.jimei.xiaolumeimei.entities.AwardCarryBean;
import com.jimei.xiaolumeimei.entities.BindInfoBean;
import com.jimei.xiaolumeimei.entities.BrandListBean;
import com.jimei.xiaolumeimei.entities.BudgetdetailBean;
import com.jimei.xiaolumeimei.entities.CarryLogListBean;
import com.jimei.xiaolumeimei.entities.CartsHisBean;
import com.jimei.xiaolumeimei.entities.CartsInfoBean;
import com.jimei.xiaolumeimei.entities.CartsNumResultBean;
import com.jimei.xiaolumeimei.entities.CartsPayinfoBean;
import com.jimei.xiaolumeimei.entities.CashoutPolicy;
import com.jimei.xiaolumeimei.entities.CategoryBean;
import com.jimei.xiaolumeimei.entities.CategoryDownBean;
import com.jimei.xiaolumeimei.entities.CategoryProductListBean;
import com.jimei.xiaolumeimei.entities.ChooseListBean;
import com.jimei.xiaolumeimei.entities.ClickcarryBean;
import com.jimei.xiaolumeimei.entities.CodeBean;
import com.jimei.xiaolumeimei.entities.CollectionAllBean;
import com.jimei.xiaolumeimei.entities.CollectionDeleteBody;
import com.jimei.xiaolumeimei.entities.CollectionResultBean;
import com.jimei.xiaolumeimei.entities.CouponEntity;
import com.jimei.xiaolumeimei.entities.CouponSelectEntity;
import com.jimei.xiaolumeimei.entities.DrawCouponBean;
import com.jimei.xiaolumeimei.entities.GetCouponbean;
import com.jimei.xiaolumeimei.entities.IsGetcoupon;
import com.jimei.xiaolumeimei.entities.LogOutBean;
import com.jimei.xiaolumeimei.entities.LogisticCompany;
import com.jimei.xiaolumeimei.entities.LogisticsBean;
import com.jimei.xiaolumeimei.entities.MMChooselistBean;
import com.jimei.xiaolumeimei.entities.MMShoppingBean;
import com.jimei.xiaolumeimei.entities.MMVisitorsBean;
import com.jimei.xiaolumeimei.entities.MaMaReNewBean;
import com.jimei.xiaolumeimei.entities.MamaFansBean;
import com.jimei.xiaolumeimei.entities.MamaFortune;
import com.jimei.xiaolumeimei.entities.MamaLivenessBean;
import com.jimei.xiaolumeimei.entities.MamaSelfListBean;
import com.jimei.xiaolumeimei.entities.MamaUrl;
import com.jimei.xiaolumeimei.entities.MembershipPointBean;
import com.jimei.xiaolumeimei.entities.MiPushOrderCarryBean;
import com.jimei.xiaolumeimei.entities.NeedSetInfoBean;
import com.jimei.xiaolumeimei.entities.NicknameBean;
import com.jimei.xiaolumeimei.entities.NinePicBean;
import com.jimei.xiaolumeimei.entities.OderCarryBean;
import com.jimei.xiaolumeimei.entities.OrderDetailBean;
import com.jimei.xiaolumeimei.entities.PayInfoBean;
import com.jimei.xiaolumeimei.entities.PersonalCarryRankBean;
import com.jimei.xiaolumeimei.entities.PointLogBean;
import com.jimei.xiaolumeimei.entities.PortalBean;
import com.jimei.xiaolumeimei.entities.PostActivityBean;
import com.jimei.xiaolumeimei.entities.PotentialFans;
import com.jimei.xiaolumeimei.entities.ProductDetailBean;
import com.jimei.xiaolumeimei.entities.ProductListBean;
import com.jimei.xiaolumeimei.entities.QiniuTokenBean;
import com.jimei.xiaolumeimei.entities.RecentCarryBean;
import com.jimei.xiaolumeimei.entities.RedBagBean;
import com.jimei.xiaolumeimei.entities.RefundMsgBean;
import com.jimei.xiaolumeimei.entities.ResponseResultBean;
import com.jimei.xiaolumeimei.entities.ResultBean;
import com.jimei.xiaolumeimei.entities.ResultEntity;
import com.jimei.xiaolumeimei.entities.SaveTimeBean;
import com.jimei.xiaolumeimei.entities.ShareModelBean;
import com.jimei.xiaolumeimei.entities.TeamBuyBean;
import com.jimei.xiaolumeimei.entities.UserAccountBean;
import com.jimei.xiaolumeimei.entities.UserBean;
import com.jimei.xiaolumeimei.entities.UserInfoBean;
import com.jimei.xiaolumeimei.entities.UserTopic;
import com.jimei.xiaolumeimei.entities.UserWithdrawResult;
import com.jimei.xiaolumeimei.entities.VersionBean;
import com.jimei.xiaolumeimei.entities.WeekTaskRewardBean;
import com.jimei.xiaolumeimei.entities.WithdrawCashHisBean;
import com.jimei.xiaolumeimei.entities.WxPubAuthInfo;
import com.jimei.xiaolumeimei.entities.WxQrcode;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by 优尼世界 on 15/12/29.
 * <p>
 * Copyright 2015年 上海己美. All rights reserved.
 */
public interface XlmmService {

    //@formatter:off

    @FormUrlEncoded
    @POST("/rest/v1/register/customer_login")
    Observable<UserBean> login(
            @Field("username") String username,
            @Field("password") String password);

    //获取所有订单
    @GET("/rest/v2/trades")
    Observable<AllOrdersBean> getAllOdersList(
            @Query("page") String page);

    //获取购物车信息
    @GET("/rest/v2/carts")
    Observable<List<CartsInfoBean>> getCartsList();

    //获取购物车信息
    @GET("/rest/v2/carts")
    Observable<List<CartsInfoBean>> getCartsList(
            @Query("type") int type
    );

    //获取历史购物车信息
    @GET("/rest/v2/carts/show_carts_history")
    Observable<List<CartsInfoBean>> getCartsHisList();

    //获取购物信息列表
    @GET("/rest/v1/carts/carts_payinfo")
    Observable<CartsPayinfoBean> getCartsPayInfoList(
            @Query("cart_ids") String cart_ids
    );

    //获取购物信息列表
    @GET("/rest/v2/carts/carts_payinfo")
    Observable<CartsPayinfoBean> getCartsPayInfoListV2(
            @Query("cart_ids") String cart_ids,
            @Query("device") String app
    );

    //获取购物信息列表
    @GET("/rest/v1/carts/carts_payinfo")
    Observable<CartsPayinfoBean> getCartsPayInfoList(
            @Query("cart_ids") String cart_ids,
            @Query("coupon_id") String coupon_id
    );

    //重新购买商品
    @FormUrlEncoded
    @POST("/rest/v1/carts")
    Observable<CartsHisBean> rebuy(
            @Field("item_id") String item_id,
            @Field("sku_id") String sku_id,
            @Field("cart_id") String cart_id
    );

    @FormUrlEncoded
    @POST("/rest/v2/trades/shoppingcart_create")
    Observable<PayInfoBean> shoppingcart_create_v2(
            @Field("cart_ids") String cart_ids,
            @Field("addr_id") String addr_id,
            @Field("channel") String channel,
            @Field("payment") String payment,
            @Field("post_fee") String post_fee,
            @Field("discount_fee") String discount_fee,
            @Field("total_fee") String total_fee,
            @Field("uuid") String uuid,
            @Field("pay_extras") String pay_extras,
            @Field("logistics_company_id") String code
    );


    @FormUrlEncoded
    @POST("/rest/v2/trades/shoppingcart_create")
    Observable<PayInfoBean> shoppingcart_create_v2(
            @Field("cart_ids") String cart_ids,
            @Field("addr_id") String addr_id,
            @Field("channel") String channel,
            @Field("payment") String payment,
            @Field("post_fee") String post_fee,
            @Field("discount_fee") String discount_fee,
            @Field("total_fee") String total_fee,
            @Field("uuid") String uuid,
            @Field("pay_extras") String pay_extras,
            @Field("logistics_company_id") String code,
            @Field("order_type") String type
    );

    //立即支付订单接口
    @FormUrlEncoded
    @POST("/rest/v2/trades/{pk}/charge")
    Observable<PayInfoBean> orderPayWithChannel(
            @Path("pk") int order_id,
            @Field("channel") String channel
    );

    //获得订单数据
    @GET("/rest/v2/trades/{pk}")
    Observable<OrderDetailBean> getOrderDetail(
            @Path("pk") int order_id,
            @Query("device") String device
    );

    //获取所有待支付订单
    @GET("/rest/v2/trades/waitpay")
    Observable<AllOrdersBean> getWaitPayOrdersBean(
            @Query("page") String page);

    //获取所有待发货订单
    @GET("/rest/v2/trades/waitsend")
    Observable<AllOrdersBean> getWaitSendOrdersBean(
            @Query("page") String page);

    //获取所有退货订单
    @GET("/rest/v1/refunds")
    Observable<AllRefundsBean> getAllRedundsList(
            @Query("page") String page);

    //创建新的地址
    @FormUrlEncoded
    @POST("/rest/v1/address/create_address")
    Observable<AddressResultBean> create_address(
            @Field("receiver_state") String receiver_state,
            @Field("receiver_city") String receiver_city,
            @Field("receiver_district") String receiver_district,
            @Field("receiver_address") String receiver_address,
            @Field("receiver_name") String receiver_name,
            @Field("receiver_mobile") String receiver_mobile,
            @Field("default") String defaulta
    );

    //修改地址
    @FormUrlEncoded
    @POST("/rest/v1/address/{id}/update")
    Observable<AddressResultBean> update_address(
            @Path("id") String id,
            @Field("receiver_state") String receiver_state,
            @Field("receiver_city") String receiver_city,
            @Field("receiver_district") String receiver_district,
            @Field("receiver_address") String receiver_address,
            @Field("receiver_name") String receiver_name,
            @Field("receiver_mobile") String receiver_mobile,
            @Field("default") String defaulta
    );

    //修改地址
    @FormUrlEncoded
    @POST("/rest/v1/address/{id}/update")
    Observable<AddressResultBean> update_address(
            @Path("id") String id,
            @Field("receiver_state") String receiver_state,
            @Field("receiver_city") String receiver_city,
            @Field("receiver_district") String receiver_district,
            @Field("receiver_address") String receiver_address,
            @Field("receiver_name") String receiver_name,
            @Field("receiver_mobile") String receiver_mobile,
            @Field("logistic_company_code") String logistic_company_code,
            @Field("referal_trade_id") String referal_trade_id
    );


    //获取地址列表
    @GET("/rest/v1/address")
    Observable<List<AddressBean>> getAddressList();

    //删除某一个地址
    @POST("/rest/v1/address/{id}/delete_address")
    Observable<AddressResultBean> delete_address(
            @Path("id") String id
    );

    //获取用户信息
    @GET("/rest/v1/users/profile")
    Observable<UserInfoBean> getUserInfo();

    //投诉建议
    @FormUrlEncoded
    @POST("/rest/v1/complain")
    Observable<AddressResultBean> complain(
            @Field("com_type") String com_type,
            @Field("com_content") String com_content,
            @Field("com_title") String com_title);

    //设置用户昵称
    @PATCH("/rest/v1/users" + "/{id}")
    Observable<UserBean> setNickname(
            @Path("id") int id,
            @Body NicknameBean nickname);

    //修改用户密码
    @FormUrlEncoded
    @POST("/rest/v1/register/change_user_pwd")
    Observable<UserBean> changePassword(
            @Field("username") String username,
            @Field("valid_code") String valid_code,
            @Field("password1") String password1,
            @Field("password2") String password2
    );

    @POST("/rest/v1/users/customer_logout")
    Observable<LogOutBean> customer_logout();

    //获取用户积分信息
    @GET("/rest/v1/integral")
    Observable<MembershipPointBean> getMembershipPointBean();


    //获取用户信息
    @GET("/rest/v1/users/profile")
    Observable<UserInfoBean> getProfile();

    //获取用户信息
    @GET("/rest/v1/users/profile")
    Observable<Response<UserInfoBean>> getUserLoginInfo();

    //获取用户积分记录信息
    @GET("/rest/v1/integrallog")
    Observable<PointLogBean> getPointLogBean(
            @Query("page") String page
    );

    //获取优惠券
    @GET("/rest/v1/usercoupons/get_user_coupons")
    Observable<ArrayList<CouponEntity>> getCouponList(
            @Query("status") int status
    );

    //购物车选择优惠券
    @GET("/rest/v1/usercoupons/coupon_able")
    Observable<CouponSelectEntity> getCouponSelectEntity(
            @Query("cart_ids") String cart_ids
    );

    //购物车增加一件
    @POST("/rest/v2/carts/{id}/plus_product_carts")
    Observable<Response<CodeBean>> plus_product_carts(
            @Path("id") String id
    );

    //购物车删除一件
    @POST("/rest/v2/carts/{id}/minus_product_carts")
    Observable<Response<CodeBean>> minus_product_carts(
            @Path("id") String id
    );

    //删除一列
    @POST("/rest/v2/carts/{id}/delete_carts")
    Observable<Response<CodeBean>> delete_carts(
            @Path("id") String id
    );

    //确认签收
    @POST("/rest/v1/order/{id}/confirm_sign")
    Observable<UserBean> receiveGoods(
            @Path("id") int id);


    //获得详细退款单数据
    @GET("/rest/v1/refunds/{pk}")
    Observable<AllRefundsBean.ResultsEntity> getRefundDetailBean(
            @Path("pk") int order_id);

    //删除订单数据
    @DELETE("/rest/v1/trades/{pk}")
    Observable<ResponseBody> delRefund(
            @Path("pk") int order_id);

    //创建退款单接口
    @FormUrlEncoded
    @POST("/rest/v1/refunds")
    Observable<RefundMsgBean> refund_create(
            @Field("id") int goods_id,
            @Field("reason") int reason,
            @Field("num") int num,
            @Field("sum_price") double sum_price,
            @Field("description") String description,
            @Field("proof_pic") String proof_pic,
            @Field("refund_channel") String refund_channel
    );

    //创建退款单接口
    @FormUrlEncoded
    @POST("/rest/v1/refunds")
    Observable<RefundMsgBean> refund_create(
            @Field("id") int goods_id,
            @Field("reason") int reason,
            @Field("num") int num,
            @Field("sum_price") double sum_price,
            @Field("description") String description,
            @Field("proof_pic") String proof_pic
    );

    //添加退款物流信息
    @FormUrlEncoded
    @POST("/rest/v1/refunds")
    Observable<ResponseBody> commit_logistics_info(
            @Field("id") int goods_id,
            @Field("modify") int type,
            @Field("company") String company,
            @Field("sid") String sid
    );

    @GET("/rest/v2/carts/show_carts_num")
    Observable<CartsNumResultBean> show_carts_num(
    );

    @GET("/rest/v1/refunds/qiniu_token")
    Observable<QiniuTokenBean> getQiniuToken(
    );

    @FormUrlEncoded
    @POST("/rest/v2/weixinapplogin")
    Observable<CodeBean> wxapp_login(
            @Query("noncestr") String noncestr,
            @Query("timestamp") String timestamp,
            @Query("sign") String sign,
            @Field("headimgurl") String headimgurl,
            @Field("nickname") String nickname,
            @Field("openid") String openid,
            @Field("unionid") String unionid,
            @Field("devtype") String devtype);

    @GET("/rest/v1/pmt/cashout")
    Observable<WithdrawCashHisBean> getWithdrawCashHis(
            @Query("page") String page
    );

    //获取粉丝列表
    @GET("/rest/v2/mama/fans")
    Observable<MamaFansBean> getMamaFans(
            @Query("page") String page
    );

    //妈妈余额兑换现金消费券
    @GET("/rest/v1/pmt/cashout/exchange_coupon")
    Observable<DrawCouponBean> drawCoupon(
            @Query("template_id") String template_id,
            @Query("exchange_num") String exchange_num
    );

    //获取访客列表
    @GET("/rest/v2/mama/visitor")
    Observable<MMVisitorsBean> getMamaVisitor(
            @Query("recent") int recent,
            @Query("page") int page
    );

    //创建提款单信息
    @FormUrlEncoded
    @POST("/rest/v1/pmt/cashout")
    Observable<ResponseResultBean> withdraw_cash(
            @Field("choice") String fund_type);

    //妈妈钱包转账到小鹿钱包
    @FormUrlEncoded
    @POST("/rest/v1/pmt/cashout/cashout_to_budget")
    Observable<ResponseResultBean> toWallet(
            @Field("cashout_amount") String cashout_amount);

    //cancel提款单信息
    @FormUrlEncoded
    @POST("/rest/v1/pmt/cashout/cancal_cashout")
    Observable<ResponseResultBean> cancel_withdraw_cash(
            @Field("id") String id);

    //选品默认列表
    @GET("/rest/v2/products/my_choice_pro")
    Observable<MMChooselistBean> getMMChooseList(
            @Query("page") String page,
            @Query("page_size") String pagesize
    );

    //选品默认列表排序
    @GET("/rest/v2/products/my_choice_pro")
    Observable<MMChooselistBean> getMMChooseSortList(
            @Query("sort_field") String sort_field,
            @Query("page") String page,
            @Query("page_size") String pagesize
    );

    //选品女装或者童装列表
    @GET("/rest/v2/products/my_choice_pro")
    Observable<MMChooselistBean> getMMChooseLadyOrChildList(

            @Query("category") String category,
            @Query("page") String page,
            @Query("page_size") String pagesize
    );

    //选品排序列表
    @GET("/rest/v2/products/my_choice_pro")
    Observable<MMChooselistBean> getMMChooseLadyOrChildSortListSort(
            @Query("sort_field") String sort_field,
            @Query("category") String category,
            @Query("page") String page,
            @Query("page_size") String pagesize
    );


    @GET("/rest/v1/users/need_set_info")
    Observable<NeedSetInfoBean> need_set_info(
    );

    @FormUrlEncoded
    @POST("/rest/v1/users/bang_mobile")
    Observable<BindInfoBean> bang_mobile(
            @Field("username") String username,
            @Field("password1") String password1,
            @Field("password2") String password2,
            @Field("valid_code") String valid_code
    );

    @GET("/rest/v1/pmt/carrylog/get_clk_list")
    Observable<AllowanceBean> getAllowance(
            @Query("page") String page
    );

    //活动内容分享
    @GET("/rest/v1/activitys/{id}/get_share_params")
    Observable<ActivityBean> get_party_share_content(@Path("id") String id);

    //品牌
    @GET("/rest/v1/activitys/{id}")
    Observable<BrandListBean> getBrandList(@Path("id") String id);

    @GET("/rest/v1/share/model")
    Observable<ShareModelBean> getShareModel(
            @Query("model_id") int model_id
    );

    //get push useraccount
    @FormUrlEncoded
    @POST("/rest/v1/push/set_device")
    Observable<UserAccountBean> getUserAccount(
            @Field("platform") String platform,
            @Field("regid") String regid,
            @Field("device_id") String device_id
    );

    @GET("/rest/v1/users/get_budget_detail")
    Observable<BudgetdetailBean> budGetdetailBean(
            @Query("page") String page
    );

    @GET("/rest/v1/pmt/ninepic")
    Observable<List<NinePicBean>> getNinepic();

    @GET("/rest/v1/pmt/ninepic")
    Observable<List<NinePicBean>> getNinepic(
            @Query("sale_category") int sale_category
    );

    @GET("/rest/v1/users/get_wxpub_authinfo")
    Observable<WxPubAuthInfo> getWxPubAuthInfo();

    //创建提款信息
    @FormUrlEncoded
    @POST("/rest/v1/users/budget_cash_out")
    Observable<UserWithdrawResult> user_withdraw_cash(
            @Field("cashout_amount") String amount,
            @Field("verify_code") String verify_code
    );

    @POST("/rest/v2/request_cashout_verify_code")
    Observable<ResultEntity> getVerifyCode();

    @FormUrlEncoded
    @POST("/rest/v1/pmt/cashout/noaudit_cashout")
    Observable<ResultEntity> getNoauditCashout(
            @Field("amount") double amount,
            @Field("verify_code") String verify_code
    );

    @GET("/rest/v1/pmt/cushop/customer_shop")
    Observable<MMShoppingBean> getShareShopping();

    @GET("/rest/v2/mama/fortune")
    Observable<MamaFortune> getMamaFortune();

    @GET("/rest/v2/mama/activevalue")
    Observable<MamaLivenessBean> getMamaLiveness(
            @Query("page") String page);

    @GET("/rest/v2/mama/carry")
    Observable<CarryLogListBean> getMamaAllCarryLogs(
            @Query("page") String page
    );

    @GET("/rest/v2/mama/ordercarry")
    Observable<OderCarryBean> getMamaAllOderCarryLogs(
            @Query("page") String page
    );

    @GET("/rest/v2/mama/ordercarry")
    Observable<OderCarryBean> getMamaAllOderCarryLogs(
            @Query("carry_type") String carry_type,
            @Query("page") String page
    );

    @GET("/rest/v2/mama/awardcarry")
    Observable<AwardCarryBean> getMamaAllAwardCarryLogs(
            @Query("page") String page
    );

    @GET("/rest/v2/mama/clickcarry")
    Observable<ClickcarryBean> getMamaAllClickCarryLogs(
            @Query("page") String page
    );

    @GET("/rest/v1/activitys")
    Observable<List<PostActivityBean>> getPostActivity(
    );

    @GET("/rest/v1/address/get_logistic_companys")
    Observable<List<LogisticCompany>> getLogisticCompany(
            @Query("referal_trade_id") int referal_trade_id
    );

    @FormUrlEncoded
    @POST("/rest/v1/address/{address_id}/change_company_code")
    Observable<ResultBean> changeLogisticCompany(
            @Path("address_id") int address_id,
            @Field("referal_trade_id") String referal_trade_id,
            @Field("logistic_company_code") String logistic_company_code
    );

    @FormUrlEncoded
    @POST("/rest/v1/usercoupons")
    Observable<ResponseBody> getUsercoupons(
            @Field("template_id") String template_id
    );

    //发送验证码
    @FormUrlEncoded
    @POST("/rest/v2/send_code")
    Observable<CodeBean> send_code(
            @Field("mobile") String mobile,
            @Field("action") String action
    );

    //验证码验证
    @FormUrlEncoded
    @POST("/rest/v2/verify_code")
    Observable<CodeBean> verify_code(
            @Field("mobile") String mobile,
            @Field("action") String action,
            @Field("verify_code") String code,
            @Field("devtype") String devtype);

    //设置账号密码
    @FormUrlEncoded
    @POST("/rest/v2/reset_password")
    Observable<CodeBean> reset_password(
            @Field("mobile") String mobile,
            @Field("password1") String password1,
            @Field("password2") String password2,
            @Field("verify_code") String code
    );

    //用户账号密码
    @FormUrlEncoded
    @POST("/rest/v2/passwordlogin")
    Observable<CodeBean> passwordlogin(
            @Field("username") String username,
            @Field("password") String password,
            @Field("next") String next,
            @Field("devtype") String devtype);

    //获取物流信息
    @GET("/rest/v1/wuliu/get_wuliu_by_packetid")
    Observable<LogisticsBean> get_logistics_by_packagetid(
            @Query("packetid") String packetid,
            @Query("company_code") String company_code
    );

    //获取退货物流信息
    @GET("/rest/v1/rtnwuliu/get_wuliu_by_packetid")
    Observable<LogisticsBean> getRefundLogistic(
            @Query("rid") int rid,
            @Query("packetid") String packetid,
            @Query("company_name") String company_name
    );

    @GET("/rest/v1/portal")
    Observable<PortalBean> getPortalBean();

    @FormUrlEncoded
    @POST("/rest/v1/users/open_debug_for_app")
    Observable<CodeBean> openDebug(
            @Field("debug_secret") String debug_secret
    );


    //获得未来粉丝列表
    @GET("/rest/v2/potential_fans")
    Observable<PotentialFans> getPotentialFans(
            @Query("page") String page
    );

    @FormUrlEncoded
    @POST("/rest/v2/sharecoupon/create_order_share")
    Observable<RedBagBean> getRedBag(
            @Field("uniq_id") String uniq_id
    );

    @GET("rest/v1/usercoupons/get_register_gift_coupon")
    Observable<Response<GetCouponbean>> getCouPon();

    @GET("/rest/v1/usercoupons/is_picked_register_gift_coupon")
    Observable<Response<IsGetcoupon>> isCouPon();

    @GET("/rest/v1/mmwebviewconfig")
    Observable<MamaUrl> getMamaUrl(
            @Query("version") String version
    );

    @GET("/rest/v1/districts/latest_version")
    Observable<AddressDownloadResultBean> getAddressVersionAndUrl(

    );

    @GET("/rest/v1/pmt/xlmm/get_register_pro_info")
    Observable<MaMaReNewBean> getRegisterProInfo(

    );

    @GET("/sale/apprelease/newversion")
    Observable<VersionBean> getVersion();

    @FormUrlEncoded
    @POST("/rest/v1/pmt/xlmm/mama_register_pay")
    Observable<Response<ResponseBody>> mamaRegisterPay(
            @Field("product_id") String product_id,
            @Field("sku_id") String sku_id,
            @Field("payment") String payment,
            @Field("channel") String channel,
            @Field("num") String num,
            @Field("post_fee") String post_fee,
            @Field("discount_fee") String discount_fee,
            @Field("uuid") String uuid,
            @Field("total_fee") String total_fee,
            @Field("wallet_renew_deposit") String wallet_renew_deposit
    );

    @FormUrlEncoded
    @POST("/rest/v1/pmt/cashout/exchange_deposit")
    Observable<Response<ResultBean>> exchangeDeposit(
            @Field("exchange_type") String exchange_type
    );

    @GET("/rest/v1/favorites")
    Observable<CollectionAllBean> getCollection(
            @Query("page") int page,
            @Query("shelf_status") String shelf_status
    );

    @FormUrlEncoded
    @POST("/rest/v1/favorites")
    Observable<CollectionResultBean> addCollection(
            @Field("model_id") int model_id
    );

    @HTTP(method = "DELETE", path = "/rest/v1/favorites", hasBody = true)
    Observable<CollectionResultBean> deleteCollection(
            @Body CollectionDeleteBody deleteBody
    );

    @GET("/rest/v2/mama/rank/carry_total_rank")
    Observable<List<PersonalCarryRankBean>> getPersonalCarryRankBean();

    @GET("/rest/v2/mama/teamrank/carry_total_rank")
    Observable<List<PersonalCarryRankBean>> getTeamCarryRankBean();


    @GET("/rest/v2/rank/carry_duration_rank")
    Observable<List<PersonalCarryRankBean>> getWeekPersonalCarryRankBean();

    @GET("/rest/v2/mama/teamrank/carry_duration_rank")
    Observable<List<PersonalCarryRankBean>> getWeekTeamCarryRankBean();


    @GET("/rest/v2/mama/rank/self_rank")
    Observable<Response<PersonalCarryRankBean>> getPersonalSelfCarryRankBean();

    @GET("/rest/v2/mama/rank/{id}/get_team_members")
    Observable<Response<List<PersonalCarryRankBean>>> getTeamMembers(
            @Path("id") String id
    );

    @GET("/rest/v2/mama/teamrank/self_rank")
    Observable<Response<PersonalCarryRankBean>> getTeamSelfRank(
    );

    @GET("/rest/v2/mama/message/self_list")
    Observable<Response<MamaSelfListBean>> getMaMaselfList(
    );

    @GET("/rest/v2/categorys/latest_version")
    Observable<CategoryDownBean> getCategoryDown();


    @GET("/rest/v2/categorys")
    Observable<List<CategoryBean>> getCategory();

    @GET("/rest/v2/modelproducts")
    Observable<CategoryProductListBean> getCategoryProductList(
            @Query("cid") String cid,
            @Query("page") int page,
            @Query("order_by") String order_by
    );

    @GET("/rest/v2/modelproducts/{id}")
    Observable<ProductDetailBean> getProductDetail(
            @Path("id") int id
    );

    @FormUrlEncoded
    @POST("/rest/v2/carts")
    Observable<ResultEntity> addToCart(
            @Field("item_id") int item_id,
            @Field("sku_id") int sku_id,
            @Field("num") int num
    );

    @FormUrlEncoded
    @POST("/rest/v2/carts")
    Observable<ResultEntity> addToCart(
            @Field("item_id") int item_id,
            @Field("sku_id") int sku_id,
            @Field("num") int num,
            @Field("type") int type
    );

    @GET("/rest/v2/modelproducts/today")
    Observable<ProductListBean> getTodayProducts(
            @Query("page") int page
    );

    @GET("/rest/v2/modelproducts/yesterday")
    Observable<ProductListBean> getYesterdayProducts(
            @Query("page") int page
    );

    @GET("/rest/v2/modelproducts/tomorrow")
    Observable<ProductListBean> getTomorrowProducts(
            @Query("page") int page
    );

    @GET("/rest/v1/push/topic")
    Observable<UserTopic> getTopic();

    @GET("/rest/v2/mama/mission/weeklist")
    Observable<WeekTaskRewardBean> getTaskReward();

    @GET("/rest/v2/ordercarry/get_latest_order_carry")
    Observable<List<MiPushOrderCarryBean>> getLatestOrderCarry();

    @GET("/rest/v2/teambuy/{tid}/team_info")
    Observable<TeamBuyBean> getTeamBuyBean(
            @Path("tid") String tid
    );

    @GET("/rest/v2/modelproducts/product_choice")
    Observable<ChooseListBean> getChooseList(
            @Query("page") int page
    );

    @GET("/rest/v2/modelproducts/product_choice")
    Observable<ChooseListBean> getChooseListBySort(
            @Query("page") int page,
            @Query("sort_field") String sort_field,
            @Query("reverse") int reverse
    );

    @GET("/rest/v2/modelproducts/product_choice")
    Observable<ChooseListBean> getChooseListByCid(
            @Query("page") int page,
            @Query("cid") String cid
    );

    @GET("/rest/v2/modelproducts/product_choice")
    Observable<ChooseListBean> getChooseList(
            @Query("page") int page,
            @Query("sort_field") String sort_field,
            @Query("cid") String cid,
            @Query("reverse") int reverse
    );

    @GET("/rest/v2/cashout_policy")
    Observable<CashoutPolicy> getCashoutPolicy();

    @GET("/rest/v2/qrcode/get_wxpub_qrcode")
    Observable<WxQrcode> getWxCode();

    @GET("/rest/v2/mama/dailystats")
    Observable<RecentCarryBean> getRecentCarry(
            @Query("from") String from,
            @Query("days") String days);

    @PATCH("/rest/v1/pmt/ninepic/{id}")
    Observable<SaveTimeBean> saveTime(
            @Path("id") int id,
            @Query("save_times") int save_times
    );
}
