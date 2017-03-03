package com.jimei.xiaolumeimei.module;

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

import java.util.List;

import okhttp3.ResponseBody;
import rx.Subscription;

/**
 * Created by wisdom on 17/3/1.
 */

public interface TradeInteractor {

    Subscription getOrderList(int type, int page, ServiceResponse<AllOrdersBean> response);

    Subscription getAllOrderBean(int page, ServiceResponse<AllOrdersBean> response);

    Subscription shoppingCartCreateV2(String cart_ids, String addr_id, String channel,
                                      String payment, String post_fee, String discount_fee,
                                      String total_fee, String uuid, String pay_extras, String code,
                                      boolean isteam, ServiceResponse<PayInfoBean> response);

    Subscription orderPayWithChannel(int order_id, String channel, ServiceResponse<PayInfoBean> response);

    Subscription getOrderDetail(int order_id, ServiceResponse<OrderDetailBean> response);

    Subscription getRefunds(int page, ServiceResponse<AllRefundsBean> response);

    Subscription getWaitPayOrdersBean(int page, ServiceResponse<AllOrdersBean> response);

    Subscription getWaitSendOrdersBean(int page, ServiceResponse<AllOrdersBean> response);

    Subscription receiveGoods(int id, ServiceResponse<UserBean> response);

    Subscription getRefundDetailBean(int order_id, ServiceResponse<AllRefundsBean.ResultsEntity> response);

    Subscription delRefund(int order_id, ServiceResponse<ResponseBody> response);

    Subscription refundCreate(int goods_id, int reason, int num, double sum_price, String description,
                              String proof_pic, String refund_channel, ServiceResponse<RefundMsgBean> response);

    Subscription refundCreate(int goods_id, int reason, int num, double sum_price, String description,
                              String proof_pic, ServiceResponse<RefundMsgBean> response);

    Subscription getQiniuToken(ServiceResponse<QiniuTokenBean> response);

    Subscription commitLogisticsInfo(int goods_id, String company, String logistics_number,
                                     ServiceResponse<ResponseBody> response);

    Subscription getLogisticsByPacketId(String packetid, String company_code,
                                        ServiceResponse<LogisticsBean> response);

    Subscription getRefundLogistic(int rid, String packetid, String company_name,
                                   ServiceResponse<LogisticsBean> response);

    Subscription getLogisticCompany(int referal_trade_id,ServiceResponse<List<LogisticCompany>> response);

    Subscription changeLogisticCompany(int address_id, String referal_trade_id, String logistic_company_code,
                                       ServiceResponse<ResultBean> response);

    Subscription getTeamBuyBean(String tid,ServiceResponse<TeamBuyBean> response);
}
