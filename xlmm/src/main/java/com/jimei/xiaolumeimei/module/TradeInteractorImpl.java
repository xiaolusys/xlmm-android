package com.jimei.xiaolumeimei.module;

import com.jimei.library.rx.DefaultTransform;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.entities.AllOrdersBean;
import com.jimei.xiaolumeimei.entities.AllRefundsBean;
import com.jimei.xiaolumeimei.entities.LogisticCompany;
import com.jimei.xiaolumeimei.entities.LogisticsBean;
import com.jimei.xiaolumeimei.entities.OrderDetailBean;
import com.jimei.xiaolumeimei.entities.PayInfoBean;
import com.jimei.xiaolumeimei.entities.QiniuTokenBean;
import com.jimei.xiaolumeimei.entities.RefundMsgBean;
import com.jimei.xiaolumeimei.entities.ResultBean;
import com.jimei.xiaolumeimei.entities.TeamBuyBean;
import com.jimei.xiaolumeimei.entities.UserBean;
import com.jimei.xiaolumeimei.service.ServiceResponse;
import com.jimei.xiaolumeimei.service.api.TradeService;

import java.util.List;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import rx.Subscription;

/**
 * Created by wisdom on 17/3/1.
 */

public class TradeInteractorImpl implements TradeInteractor {

    private final TradeService service;

    @Inject
    public TradeInteractorImpl(TradeService service) {
        this.service = service;
    }

    @Override
    public Subscription getOrderList(int type, int page, ServiceResponse<AllOrdersBean> response) {
        if (type == XlmmConst.WAIT_SEND) {
            return getWaitSendOrdersBean(page, response);
        } else if (type == XlmmConst.WAIT_PAY) {
            return getWaitPayOrdersBean(page, response);
        } else {
            return getAllOrderBean(page, response);
        }
    }

    @Override
    public Subscription getAllOrderBean(int page, ServiceResponse<AllOrdersBean> response) {
        return service.getAllOrdersList(page)
            .compose(new DefaultTransform<>())
            .subscribe(response);
    }

    @Override
    public Subscription shoppingCartCreateV2(String cart_ids, String addr_id, String channel,
                                             String payment, String post_fee, String discount_fee,
                                             String total_fee, String uuid, String pay_extras,
                                             String code, boolean isteam, ServiceResponse<PayInfoBean> response) {
        if (isteam) {
            return service.shoppingCartCreateV2(cart_ids, addr_id, channel, payment, post_fee,
                discount_fee, total_fee, uuid, pay_extras, code, "3")
                .compose(new DefaultTransform<>())
                .subscribe(response);
        } else {
            return service.shoppingCartCreateV2(cart_ids, addr_id, channel, payment, post_fee,
                discount_fee, total_fee, uuid, pay_extras, code)
                .compose(new DefaultTransform<>())
                .subscribe(response);
        }

    }

    @Override
    public Subscription orderPayWithChannel(int order_id, String channel, ServiceResponse<PayInfoBean> response) {
        return service.orderPayWithChannel(order_id, channel)
            .compose(new DefaultTransform<>())
            .subscribe(response);
    }

    @Override
    public Subscription getOrderDetail(int order_id, ServiceResponse<OrderDetailBean> response) {
        return service.getOrderDetail(order_id, "app")
            .compose(new DefaultTransform<>())
            .subscribe(response);
    }

    @Override
    public Subscription getRefunds(int page, ServiceResponse<AllRefundsBean> response) {
        return service.getAllRefundsList(page)
            .compose(new DefaultTransform<>())
            .subscribe(response);
    }

    @Override
    public Subscription getWaitPayOrdersBean(int page, ServiceResponse<AllOrdersBean> response) {
        return service.getWaitPayOrdersBean(page)
            .compose(new DefaultTransform<>())
            .subscribe(response);
    }

    @Override
    public Subscription getWaitSendOrdersBean(int page, ServiceResponse<AllOrdersBean> response) {
        return service.getWaitSendOrdersBean(page)
            .compose(new DefaultTransform<>())
            .subscribe(response);
    }

    @Override
    public Subscription receiveGoods(int id, ServiceResponse<UserBean> response) {
        return service.receiveGoods(id)
            .compose(new DefaultTransform<>())
            .subscribe(response);
    }

    @Override
    public Subscription getRefundDetailBean(int order_id, ServiceResponse<AllRefundsBean.ResultsEntity> response) {
        return service.getRefundDetailBean(order_id)
            .compose(new DefaultTransform<>())
            .subscribe(response);
    }

    @Override
    public Subscription delRefund(int order_id, ServiceResponse<ResponseBody> response) {
        return service.delRefund(order_id)
            .compose(new DefaultTransform<>())
            .subscribe(response);
    }

    @Override
    public Subscription refundCreate(int goods_id, int reason, int num, double sum_price, String description,
                                     String proof_pic, String refund_channel, ServiceResponse<RefundMsgBean> response) {
        return service.refundCreate(goods_id, reason, num, sum_price, description, proof_pic, refund_channel)
            .compose(new DefaultTransform<>())
            .subscribe(response);
    }

    @Override
    public Subscription refundCreate(int goods_id, int reason, int num, double sum_price,
                                     String description, String proof_pic, ServiceResponse<RefundMsgBean> response) {
        return service.refundCreate(goods_id, reason, num, sum_price, description, proof_pic)
            .compose(new DefaultTransform<>())
            .subscribe(response);
    }

    @Override
    public Subscription getQiniuToken(ServiceResponse<QiniuTokenBean> response) {
        return service.getQiniuToken()
            .compose(new DefaultTransform<>())
            .subscribe(response);
    }

    @Override
    public Subscription commitLogisticsInfo(int goods_id, String company, String logistics_number,
                                            ServiceResponse<ResponseBody> response) {
        return service.commitLogisticsInfo(goods_id, 2, company, logistics_number)
            .compose(new DefaultTransform<>())
            .subscribe(response);
    }

    @Override
    public Subscription getLogisticsByPacketId(String packetid, String company_code,
                                               ServiceResponse<LogisticsBean> response) {
        return service.getLogisticsByPacketId(packetid, company_code)
            .compose(new DefaultTransform<>())
            .subscribe(response);
    }

    @Override
    public Subscription getRefundLogistic(int rid, String packetid, String company_name,
                                          ServiceResponse<LogisticsBean> response) {
        return service.getRefundLogistic(rid, packetid, company_name)
            .compose(new DefaultTransform<>())
            .subscribe(response);
    }

    @Override
    public Subscription getLogisticCompany(int referal_trade_id, ServiceResponse<List<LogisticCompany>> response) {
        return service.getLogisticCompany(referal_trade_id)
            .compose(new DefaultTransform<>())
            .subscribe(response);
    }

    @Override
    public Subscription changeLogisticCompany(int address_id, String referal_trade_id, String logistic_company_code,
                                              ServiceResponse<ResultBean> response) {
        return service.changeLogisticCompany(address_id, referal_trade_id, logistic_company_code)
            .compose(new DefaultTransform<>())
            .subscribe(response);
    }

    @Override
    public Subscription getTeamBuyBean(String tid, ServiceResponse<TeamBuyBean> response) {
        return service.getTeamBuyBean(tid)
            .compose(new DefaultTransform<>())
            .subscribe(response);
    }
}
