package com.jimei.xiaolumeimei.data;

/**
 * Created by wulei on 2016/1/22.
 */
public class XlmmConst {

    public static  final int ORDER_STATE_CREATE = 0;
    public static  final int ORDER_STATE_WAITPAY = 1;
    public static  final int ORDER_STATE_PAYED = 2;
    public static  final int ORDER_STATE_SENDED = 3;
    public static  final int ORDER_STATE_CONFIRM_RECEIVE = 4;
    public static  final int ORDER_STATE_TRADE_SUCCESS = 5;
    public static  final int ORDER_STATE_REFUND_CLOSE = 6;
    public static  final int ORDER_STATE_TRADE_CLOSE = 7;

    public static  final int REFUND_STATE_NO_REFUND = 0;
    public static  final int REFUND_STATE_BUYER_APPLY = 1;
    public static  final int REFUND_STATE_SELLER_AGREED = 2;
    public static  final int REFUND_STATE_BUYER_RETURNED_GOODS = 3;
    public static  final int REFUND_STATE_SELLER_REJECTED = 4;
    public static  final int REFUND_STATE_WAIT_RETURN_FEE = 5;
    public static  final int REFUND_STATE_REFUND_CLOSE = 6;
    public static  final int REFUND_STATE_REFUND_SUCCESS = 7;
}
