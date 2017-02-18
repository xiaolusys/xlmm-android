package com.jimei.xiaolumeimei.service.api;

import com.jimei.xiaolumeimei.entities.AllOrdersBean;
import com.jimei.xiaolumeimei.entities.AllRefundsBean;
import com.jimei.xiaolumeimei.entities.LogisticCompany;
import com.jimei.xiaolumeimei.entities.LogisticsBean;
import com.jimei.xiaolumeimei.entities.OrderDetailBean;
import com.jimei.xiaolumeimei.entities.PayInfoBean;
import com.jimei.xiaolumeimei.entities.QiniuTokenBean;
import com.jimei.xiaolumeimei.entities.RedBagBean;
import com.jimei.xiaolumeimei.entities.RefundMsgBean;
import com.jimei.xiaolumeimei.entities.ResultBean;
import com.jimei.xiaolumeimei.entities.TeamBuyBean;
import com.jimei.xiaolumeimei.entities.UserBean;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by wisdom on 16/11/23.
 */

public interface TradeService {

    //获取所有订单
    @GET("/rest/v2/trades")
    Observable<AllOrdersBean> getAllOdersList(
        @Query("page") int page);

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
        @Field("order_type") String type);

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
        @Field("logistics_company_id") String code);

    //立即支付订单接口
    @FormUrlEncoded
    @POST("/rest/v2/trades/{pk}/charge")
    Observable<PayInfoBean> orderPayWithChannel(
        @Path("pk") int order_id,
        @Field("channel") String channel);

    //获得订单数据
    @GET("/rest/v2/trades/{pk}")
    Observable<OrderDetailBean> getOrderDetail(
        @Path("pk") int order_id,
        @Query("device") String device);

    //获取所有退货订单
    @GET("/rest/v1/refunds")
    Observable<AllRefundsBean> getAllRedundsList(
        @Query("page") int page);

    //获取所有待支付订单
    @GET("/rest/v2/trades/waitpay")
    Observable<AllOrdersBean> getWaitPayOrdersBean(
        @Query("page") int page);

    //获取所有待发货订单
    @GET("/rest/v2/trades/waitsend")
    Observable<AllOrdersBean> getWaitSendOrdersBean(
        @Query("page") int page);

    //确认签收
    @POST("/rest/v1/order/{id}/confirm_sign")
    Observable<UserBean> receiveGoods(
        @Path("id") int id);

    //获得详细退款单数据
    @GET("/rest/v1/refunds/{pk}")
    Observable<AllRefundsBean.ResultsEntity> getRefundDetailBean(
        @Path("pk") int order_id);

    //删除订单数据
    @DELETE("/rest/v2/trades/{pk}")
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
        @Field("refund_channel") String refund_channel);

    //创建退款单接口
    @FormUrlEncoded
    @POST("/rest/v1/refunds")
    Observable<RefundMsgBean> refund_create(
        @Field("id") int goods_id,
        @Field("reason") int reason,
        @Field("num") int num,
        @Field("sum_price") double sum_price,
        @Field("description") String description,
        @Field("proof_pic") String proof_pic);

    @GET("/rest/v1/refunds/qiniu_token")
    Observable<QiniuTokenBean> getQiniuToken();

    //添加退款物流信息
    @FormUrlEncoded
    @POST("/rest/v1/refunds")
    Observable<ResponseBody> commit_logistics_info(
        @Field("id") int goods_id,
        @Field("modify") int type,
        @Field("company") String company,
        @Field("sid") String sid);

    //获取物流信息
    @GET("/rest/v1/wuliu/get_wuliu_by_packetid")
    Observable<LogisticsBean> get_logistics_by_packagetid(
        @Query("packetid") String packetid,
        @Query("company_code") String company_code);

    //获取退货物流信息
    @GET("/rest/v1/rtnwuliu/get_wuliu_by_packetid")
    Observable<LogisticsBean> getRefundLogistic(
        @Query("rid") int rid,
        @Query("packetid") String packetid,
        @Query("company_name") String company_name);

    @FormUrlEncoded
    @POST("/rest/v2/sharecoupon/create_order_share")
    Observable<RedBagBean> getRedBag(
        @Field("uniq_id") String uniq_id);

    @GET("/rest/v1/address/get_logistic_companys")
    Observable<List<LogisticCompany>> getLogisticCompany(
        @Query("referal_trade_id") int referal_trade_id);

    @FormUrlEncoded
    @POST("/rest/v1/address/{address_id}/change_company_code")
    Observable<ResultBean> changeLogisticCompany(
        @Path("address_id") int address_id,
        @Field("referal_trade_id") String referal_trade_id,
        @Field("logistic_company_code") String logistic_company_code);

    @GET("/rest/v2/teambuy/{tid}/team_info")
    Observable<TeamBuyBean> getTeamBuyBean(
        @Path("tid") String tid);

}
