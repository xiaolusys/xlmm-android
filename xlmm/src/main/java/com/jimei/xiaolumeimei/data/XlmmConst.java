package com.jimei.xiaolumeimei.data;

import android.os.Environment;

/**
 * Created by wulei on 2016/1/22.
 */
public class XlmmConst {

    //订单状态
    public static final int ORDER_STATE_CREATE = 0;
    public static final int ORDER_STATE_WAITPAY = 1;
    public static final int ORDER_STATE_PAYED = 2;
    public static final int ORDER_STATE_SENDED = 3;
    public static final int ORDER_STATE_CONFIRM_RECEIVE = 4;
    public static final int ORDER_STATE_TRADE_SUCCESS = 5;
    public static final int ORDER_STATE_REFUND_CLOSE = 6;
    public static final int ORDER_STATE_TRADE_CLOSE = 7;

    //退货退款状态
    public static final int REFUND_STATE_NO_REFUND = 0;
    public static final int REFUND_STATE_BUYER_APPLY = 3;
    public static final int REFUND_STATE_SELLER_AGREED = 4;
    public static final int REFUND_STATE_BUYER_RETURNED_GOODS = 5;
    public static final int REFUND_STATE_SELLER_REJECTED = 2;
    public static final int REFUND_STATE_WAIT_RETURN_FEE = 6;
    public static final int REFUND_STATE_REFUND_CLOSE = 1;
    public static final int REFUND_STATE_REFUND_SUCCESS = 7;

    //优惠券状态
    public static final int UNUSED_COUPON = 0;
    public static final int PAST_COUPON = 1;
    public static final int USED_COUPON = 2;
    public static final int GOOD_COUPON = 3;

    //订单类型
    public static final int ALL_ORDER = 0;
    public static final int WAIT_PAY = 1;
    public static final int WAIT_SEND = 2;

    //特卖类型
    public static final int TYPE_YESTERDAY = 0;
    public static final int TYPE_TODAY = 1;
    public static final int TYPE_TOMORROW = 2;

    //分类cid
    public static final String TYPE_CHILD = "1";
    public static final String TYPE_LADY = "2";

    //小米推送
    public static final String XIAOMI_APP_ID = "2882303761517434918";
    public static final String XIAOMI_APP_KEY = "5551743422918";

    //Udesk客服
    public static final String UDESK_URL = "xiaolumeimei.udesk.cn";
    public static final String UDESK_KEY = "e7bfd4447bf206d17fb536240a9f4fbb";

    //微信
    public static final String WX_APP_ID = "wx25fcb32689872499";

    //排名类别
    public static final int TYPE_PERSON_RANK = 0;
    public static final int TYPE_PERSON_WEEK_RANK = 1;
    public static final int TYPE_TEAM_RANK = 2;
    public static final int TYPE_TEAM_WEEK_RANK = 3;

    public static final int TYPE_REWARD_PERSONAL = 0;
    public static final int TYPE_REWARD_TEAM = 1;

    public static final String XLMM_DIR = Environment.getExternalStorageDirectory().getAbsolutePath()
            + "/xlmm/";

    public static final String CATEGORY_JSON = XLMM_DIR + "category.json";

    private static final String[] NUMBER = {"零", "一", "二", "三", "四", "五"
            , "六", "七", "八", "九", "十"};

    public static String numberToWord(int num) {
        if (num >= 0 && num <= 10) {
            return NUMBER[num];
        } else if (num <= 99) {
            if (num % 10 == 0) {
                return NUMBER[num / 10] + "十";
            } else {
                return NUMBER[num / 10] + "十" + NUMBER[num % 10];
            }
        } else {
            return "";
        }
    }

    public static int get_reason_num(String reason) {
        int reason_num = 0;
        switch (reason) {
            case "其他":
                reason_num = 0;
                break;
            case "错拍":
                reason_num = 1;
                break;
            case "缺货":
                reason_num = 2;
                break;
            case "开线/脱色/脱毛/有色差/有虫洞":
                reason_num = 3;
                break;
            case "发错货/漏发":
                reason_num = 4;
                break;
            case "没有发货":
                reason_num = 5;
                break;
            case "未收到货":
                reason_num = 6;
                break;
            case "与描述不符":
                reason_num = 7;
                break;
            case "发票问题":
                reason_num = 9;
                break;
            case "七天无理由退换货":
                reason_num = 10;
                break;
            default:
                break;
        }
        return reason_num;
    }
}
