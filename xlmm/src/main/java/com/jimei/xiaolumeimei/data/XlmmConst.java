package com.jimei.xiaolumeimei.data;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by wulei on 2016/1/22.
 */
public class XlmmConst {

    public static final int ORDER_STATE_CREATE = 0;
    public static final int ORDER_STATE_WAITPAY = 1;
    public static final int ORDER_STATE_PAYED = 2;
    public static final int ORDER_STATE_SENDED = 3;
    public static final int ORDER_STATE_CONFIRM_RECEIVE = 4;
    public static final int ORDER_STATE_TRADE_SUCCESS = 5;
    public static final int ORDER_STATE_REFUND_CLOSE = 6;
    public static final int ORDER_STATE_TRADE_CLOSE = 7;

    public static final int REFUND_STATE_NO_REFUND = 0;
    public static final int REFUND_STATE_BUYER_APPLY = 3;
    public static final int REFUND_STATE_SELLER_AGREED = 4;
    public static final int REFUND_STATE_BUYER_RETURNED_GOODS = 5;
    public static final int REFUND_STATE_SELLER_REJECTED = 2;
    public static final int REFUND_STATE_WAIT_RETURN_FEE = 6;
    public static final int REFUND_STATE_REFUND_CLOSE = 1;
    public static final int REFUND_STATE_REFUND_SUCCESS = 7;

    public static final int UNUSED_COUPON = 0;
    public static final int PAST_COUPON = 1;
    public static final int USED_COUPON = 2;

    public static final int ALL_ORDER = 0;
    public static final int WAIT_PAY = 1;
    public static final int WAIT_SEND = 2;

    public static final int JUMP_PROMOTE_TODAY = 1;
    public static final int JUMP_PROMOTE_PREVIOUS = 2;
    public static final int JUMP_PRODUCT_CHILDLIST = 3;
    public static final int JUMP_PRODUCT_LADYLIST = 4;
    public static final int JUMP_PRODUCT_MODELLIST = 5;
    public static final int JUMP_PRODUCT_DETAIL = 6;
    public static final int JUMP_TRADE_DETAIL = 7;
    public static final int JUMP_USER_COUPON = 8;
    public static final int JUMP_WEBVIEW = 9;
    public static final int JUMP_XIAOLUMAMA = 10;
    public static final int JUMP_XIAOLUMAMA_DAILYPOST = 11;
    public static final int JUMP_REFUNDS = 12;
    public static final int JUMP_CARTS = 13;
    public static final int JUMP_TOPIC = 14;
    public static final int JUMP_PRODUCT_CATEGORY = 15;
    public static final int JUMP_VIP_FORUM = 16;

    public static final int TYPE_YESTERDAY = 0;
    public static final int TYPE_TODAY = 1;
    public static final int TYPE_TOMORROW = 2;

    public static final String TYPE_LADY = "2";
    public static final String TYPE_CHILD = "1";

    public static final String JUMP_PREFIX = "com.jimei.xlmm://app/v1/";

    public static final String XIAOMI_APP_ID = "2882303761517434918";
    public static final String XIAOMI_APP_KEY = "5551743422918";

    public static final String UDESK_URL = "xiaolumeimei.udesk.cn";
    public static final String UDESK_KEY = "e7bfd4447bf206d17fb536240a9f4fbb";


    public static final int TYPE_PERSON_RANK = 0;
    public static final int TYPE_PERSON_WEEK_RANK = 1;
    public static final int TYPE_TEAM_RANK = 2;
    public static final int TYPE_TEAM_WEEK_RANK = 3;

    public static final int MI_MAMA_ORDER_CARRY_BROADCAST = 999;
    public static final int MI_PRODUCT_DETAIL = 998;

    public static final int TYPE_REWARD_PERSONAL = 0;
    public static final int TYPE_REWARD_TEAM = 1;




    public static int get_reason_num(String reason) {
        int reason_num = 0;

        if (reason.equals("其他")) {
            reason_num = 0;
        } else if (reason.equals("错拍")) {
            reason_num = 1;
        } else if (reason.equals("缺货")) {
            reason_num = 2;
        } else if (reason.equals("开线/脱色/脱毛/有色差/有虫洞")) {
            reason_num = 3;
        } else if (reason.equals("发错货/漏发")) {
            reason_num = 4;
        } else if (reason.equals("没有发货")) {
            reason_num = 5;
        } else if (reason.equals("未收到货")) {
            reason_num = 6;
        } else if (reason.equals("与描述不符")) {
            reason_num = 7;
        } else if (reason.equals("发票问题")) {
            reason_num = 9;
        } else if (reason.equals("七天无理由退换货")) {
            reason_num = 10;
        }
        return reason_num;
    }

    public static int getVersionCode(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo;
        int versionCode = 0;
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    public static String getVersionName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo;
        String versionName = "";
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }
}
