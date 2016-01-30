package com.jimei.xiaolumeimei.model;

import com.jimei.xiaolumeimei.entities.AllOrdersBean;
import com.jimei.xiaolumeimei.entities.AllRefundsBean;
import com.jimei.xiaolumeimei.entities.OrderDetailBean;
import com.jimei.xiaolumeimei.entities.QiniuTokenBean;
import com.jimei.xiaolumeimei.entities.UserBean;
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

  //创建订单,使用优惠券
  public Observable<ResponseBody> shoppingcart_create_with_coupon(String cart_ids,
      String addr_id, String channel, String payment, String post_fee,
      String discount_fee, String total_fee, String uuid, String coupon_id) {
    return XlmmRetrofitClient.getService()
        .shoppingcart_create_with_coupon(cart_ids, addr_id, channel, payment, post_fee,
            discount_fee, total_fee, uuid, coupon_id)
        .compose(new DefaultTransform<>());
  }

  //立即支付订单
  public Observable<ResponseBody> shoppingcart_paynow(int order_id) {
    return XlmmRetrofitClient.getService()
        .shoppingcart_paynow(order_id)
        .compose(new DefaultTransform<>());
  }

  //得到某个订单详细数据列表
  public Observable<OrderDetailBean> getOrderDetailBean(int order_id) {
    return XlmmRetrofitClient.getService()
        .getOrderDetail(order_id)
        .compose(new DefaultTransform<>());
  }

  //得到全部退货单数据列表
  public Observable<AllRefundsBean> getRefundsBean() {
    return XlmmRetrofitClient.getService()
        .getAllRedundsList()
        .compose(new DefaultTransform<>());
  }

  //得到全部待支付订单数据列表
  public Observable<AllOrdersBean> getWaitPayOrdersBean() {
    return XlmmRetrofitClient.getService()
        .getWaitPayOrdersBean()
        .compose(new DefaultTransform<>());
  }

  //得到全部待发货订单数据列表
  public Observable<AllOrdersBean> getWaitSendOrdersBean() {
    return XlmmRetrofitClient.getService()
        .getWaitSendOrdersBean()
        .compose(new DefaultTransform<>());
  }

  //确认收货
  public Observable<UserBean> receiveGoods(int id) {
    return XlmmRetrofitClient.getService()
        .receiveGoods(id)
        .compose(new DefaultTransform<>());
  }

  //得到退货单详细数据
  public Observable<AllRefundsBean.ResultsEntity> getRefundDetailBean(int order_id) {
    return XlmmRetrofitClient.getService()
        .getRefundDetailBean(order_id)
        .compose(new DefaultTransform<>());
  }

  //删除ding单详细数据
  public Observable<ResponseBody> delRefund(int order_id) {
    return XlmmRetrofitClient.getService()
            .delRefund(order_id)
            .compose(new DefaultTransform<>());
  }

  //创建退货单数据
  public Observable<ResponseBody> refund_create(int goods_id, int reason, int num,
      double sum_price, String description, String proof_pic) {
    return XlmmRetrofitClient.getService()
        .refund_create(goods_id, reason, num, sum_price, description, proof_pic)
        .compose(new DefaultTransform<>());
  }

  //得到退货单详细数据
  public Observable<QiniuTokenBean> getQiniuToken() {
    return XlmmRetrofitClient.getService()
        .getQiniuToken()
        .compose(new DefaultTransform<>());
  }

  //修改退货单数据
  public Observable<ResponseBody> update_refund_info(int goods_id, int reason, int num,
      double sum_price, String description, String proof_pic) {
    return XlmmRetrofitClient.getService()
        .update_refund_info(goods_id, 1,reason, num, sum_price, description)
        .compose(new DefaultTransform<>());
  }

  //填写退货物流信息
  public Observable<ResponseBody> commit_logistics_info(int goods_id, String company,
       String logistics_number) {
    return XlmmRetrofitClient.getService()
        .commit_logistics_info(goods_id,2, company, logistics_number)
        .compose(new DefaultTransform<>());
  }
}
