package com.jimei.xiaolumeimei.model;

import com.jimei.library.rx.DefaultTransform;
import com.jimei.xiaolumeimei.data.XlmmConst;
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
import com.jimei.xiaolumeimei.xlmmService.RetrofitClient;
import com.jimei.xiaolumeimei.xlmmService.api.TradeService;

import java.util.List;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by 优尼世界 on 15/12/29.
 * <p>
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class TradeModel {

    private static TradeModel ourInstance = new TradeModel();
    private static TradeService tradeService;

    private static TradeService getService() {
        if (tradeService == null) {
            tradeService = RetrofitClient.createAdapter().create(TradeService.class);
        }
        return tradeService;
    }

    private TradeModel() {
    }

    public static TradeModel getInstance() {
        return ourInstance;
    }

    //根据type获得订单列表
    public Observable<AllOrdersBean> getOrderList(int type, int page) {
        if (type == XlmmConst.WAIT_SEND) {
            return getWaitSendOrdersBean(page);
        } else if (type == XlmmConst.WAIT_PAY) {
            return getWaitPayOrdersBean(page);
        } else {
            return getAlloderBean(page);
        }
    }

    //得到全部订单数据列表
    private Observable<AllOrdersBean> getAlloderBean(int page) {
        return getService()
                .getAllOdersList(page)
                .compose(new DefaultTransform<>());
    }

    //创建订单
    public Observable<PayInfoBean> shoppingcart_create_v2(String cart_ids, String addr_id, String channel,
                                                          String payment, String post_fee, String discount_fee,
                                                          String total_fee, String uuid, String pay_extras,
                                                          String code, boolean isteam) {
        if (isteam) {
            return getService()
                    .shoppingcart_create_v2(cart_ids, addr_id, channel, payment, post_fee,
                            discount_fee, total_fee, uuid, pay_extras, code, "3")
                    .compose(new DefaultTransform<>());
        } else {
            return getService()
                    .shoppingcart_create_v2(cart_ids, addr_id, channel, payment, post_fee,
                            discount_fee, total_fee, uuid, pay_extras, code)
                    .compose(new DefaultTransform<>());
        }
    }

    //立即支付订单
    public Observable<PayInfoBean> orderPayWithChannel(int order_id, String channel) {
        return getService()
                .orderPayWithChannel(order_id, channel)
                .compose(new DefaultTransform<>());
    }

    //得到某个订单详细数据列表
    public Observable<OrderDetailBean> getOrderDetailBean(int order_id) {
        return getService()
                .getOrderDetail(order_id, "app")
                .compose(new DefaultTransform<>());
    }

    //得到全部退货单数据列表
    public Observable<AllRefundsBean> getRefundsBean(int page) {
        return getService()
                .getAllRedundsList(page)
                .compose(new DefaultTransform<>());
    }

    //得到全部待支付订单数据列表
    private Observable<AllOrdersBean> getWaitPayOrdersBean(int page) {
        return getService()
                .getWaitPayOrdersBean(page)
                .compose(new DefaultTransform<>());
    }

    //得到全部待发货订单数据列表
    private Observable<AllOrdersBean> getWaitSendOrdersBean(int page) {
        return getService()
                .getWaitSendOrdersBean(page)
                .compose(new DefaultTransform<>());
    }

    //确认收货
    public Observable<UserBean> receiveGoods(int id) {
        return getService()
                .receiveGoods(id)
                .compose(new DefaultTransform<>());
    }

    //得到退货单详细数据
    public Observable<AllRefundsBean.ResultsEntity> getRefundDetailBean(int order_id) {
        return getService()
                .getRefundDetailBean(order_id)
                .compose(new DefaultTransform<>());
    }

    //删除ding单详细数据
    public Observable<ResponseBody> delRefund(int order_id) {
        return getService()
                .delRefund(order_id)
                .compose(new DefaultTransform<>());
    }

    //创建退货单数据
    public Observable<RefundMsgBean> refund_create(int goods_id, int reason, int num, double sum_price,
                                                   String description, String proof_pic, String refund_channel) {
        return getService()
                .refund_create(goods_id, reason, num, sum_price, description, proof_pic, refund_channel)
                .compose(new DefaultTransform<>());
    }

    //创建退货单数据
    public Observable<RefundMsgBean> refund_create(int goods_id, int reason, int num, double sum_price,
                                                   String description, String proof_pic) {
        return getService()
                .refund_create(goods_id, reason, num, sum_price, description, proof_pic)
                .compose(new DefaultTransform<>());
    }

    //得到退货单详细数据
    public Observable<QiniuTokenBean> getQiniuToken() {
        return getService()
                .getQiniuToken()
                .compose(new DefaultTransform<>());
    }

    //填写退货物流信息
    public Observable<ResponseBody> commit_logistics_info(int goods_id, String company, String logistics_number) {
        return getService()
                .commit_logistics_info(goods_id, 2, company, logistics_number)
                .compose(new DefaultTransform<>());
    }

    //获取物流信息
    public Observable<LogisticsBean> get_logistics_by_packagetid(String packetid, String company_code) {
        return getService()
                .get_logistics_by_packagetid(packetid, company_code)
                .compose(new DefaultTransform<>());
    }


    //获取物流信息
    public Observable<LogisticsBean> getRefundLogistic(int rid, String packetid, String company_name) {
        return getService()
                .getRefundLogistic(rid, packetid, company_name)
                .compose(new DefaultTransform<>());
    }

    //获取红包
    public Observable<RedBagBean> getRedBag(String uniq_id) {
        return getService()
                .getRedBag(uniq_id)
                .compose(new DefaultTransform<>());
    }

    //物流列表
    public Observable<List<LogisticCompany>> getLogisticCompany(int referal_trade_id) {
        return getService()
                .getLogisticCompany(referal_trade_id)
                .compose(new DefaultTransform<>());
    }

    //修改物流
    public Observable<ResultBean> changeLogisticCompany(int address_id, String referal_trade_id,
                                                        String logistic_company_code) {
        return getService()
                .changeLogisticCompany(address_id, referal_trade_id, logistic_company_code)
                .compose(new DefaultTransform<>());
    }

    //团购信息
    public Observable<TeamBuyBean> getTeamBuyBean(String tid) {
        return getService()
                .getTeamBuyBean(tid)
                .compose(new DefaultTransform<>());
    }
}
