package com.jimei.xiaolumeimei.model;

import com.jimei.library.rx.DefaultTransform;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.entities.AllOrdersBean;
import com.jimei.xiaolumeimei.entities.AllRefundsBean;
import com.jimei.xiaolumeimei.entities.BudgetPayBean;
import com.jimei.xiaolumeimei.entities.LogisticsBean;
import com.jimei.xiaolumeimei.entities.OrderDetailBean;
import com.jimei.xiaolumeimei.entities.PackageBean;
import com.jimei.xiaolumeimei.entities.PayInfoBean;
import com.jimei.xiaolumeimei.entities.QiniuTokenBean;
import com.jimei.xiaolumeimei.entities.RedBagBean;
import com.jimei.xiaolumeimei.entities.RefundMsgBean;
import com.jimei.xiaolumeimei.entities.UserBean;
import com.jimei.xiaolumeimei.xlmmService.XlmmRetrofitClient;
import java.util.ArrayList;
import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by 优尼世界 on 15/12/29.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class TradeModel {

  private static TradeModel ourInstance = new TradeModel();

  private TradeModel() {
  }

  public static TradeModel getInstance() {
    return ourInstance;
  }

  public Observable<AllOrdersBean> getOrderList(int type,String page){
    if (type == XlmmConst.WAIT_SEND) {
      return getWaitSendOrdersBean(page);
    }else if (type == XlmmConst.WAIT_PAY ){
      return getWaitPayOrdersBean(page);
    }else {
      return getAlloderBean(page);
    }
  }

  //得到全部订单数据列表
  public Observable<AllOrdersBean> getAlloderBean(String page) {
    return XlmmRetrofitClient.getService()
        .getAllOdersList(page)
        .compose(new DefaultTransform<>());
  }

  //创建订单
  public Observable<PayInfoBean> shoppingcart_create_v2(String cart_ids, String addr_id,
      String channel, String payment, String post_fee, String discount_fee,
      String total_fee, String uuid, String pay_extras,String code) {
    return XlmmRetrofitClient.getService()
        .shoppingcart_create_v2(cart_ids, addr_id, channel, payment, post_fee,
            discount_fee, total_fee, uuid, pay_extras,code)
        .compose(new DefaultTransform<>());
  }

  //立即支付订单
  public Observable<PayInfoBean> orderPayWithChannel(int order_id,String channel) {
    return XlmmRetrofitClient.getService()
            .orderPayWithChannel(order_id,channel)
            .compose(new DefaultTransform<>());
  }

  //得到某个订单详细数据列表
  public Observable<OrderDetailBean> getOrderDetailBean(int order_id) {
    return XlmmRetrofitClient.getService()
        .getOrderDetail(order_id,"app")
        .compose(new DefaultTransform<>());
  }

  //得到全部退货单数据列表
  public Observable<AllRefundsBean> getRefundsBean(String page) {
    return XlmmRetrofitClient.getService()
        .getAllRedundsList(page)
        .compose(new DefaultTransform<>());
  }

  //得到全部待支付订单数据列表
  public Observable<AllOrdersBean> getWaitPayOrdersBean(String page) {
    return XlmmRetrofitClient.getService()
        .getWaitPayOrdersBean(page)
        .compose(new DefaultTransform<>());
  }

  //得到全部待发货订单数据列表
  public Observable<AllOrdersBean> getWaitSendOrdersBean(String page) {
    return XlmmRetrofitClient.getService()
        .getWaitSendOrdersBean(page)
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
  public Observable<RefundMsgBean> refund_create(int goods_id, int reason, int num,
                                                 double sum_price, String description, String proof_pic, String refund_channel) {
    return XlmmRetrofitClient.getService()
        .refund_create(goods_id, reason, num, sum_price, description, proof_pic,refund_channel)
        .compose(new DefaultTransform<>());
  }

  //创建退货单数据
  public Observable<RefundMsgBean> refund_create(int goods_id, int reason, int num,
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

  //填写退货物流信息
  public Observable<ResponseBody> commit_logistics_info(int goods_id, String company,
      String logistics_number) {
    return XlmmRetrofitClient.getService()
        .commit_logistics_info(goods_id, 2, company, logistics_number)
        .compose(new DefaultTransform<>());
  }

  //获取物流信息
  public Observable<LogisticsBean> get_logistics_by_packagetid(String packetid,String company_code) {
    return XlmmRetrofitClient.getService()
            .get_logistics_by_packagetid(packetid,company_code)
            .compose(new DefaultTransform<>());
  }

  public Observable<RedBagBean> getRedBag(String uniq_id){
    return  XlmmRetrofitClient.getService()
            .getRedBag(uniq_id)
            .compose(new DefaultTransform<>());
  }
}
