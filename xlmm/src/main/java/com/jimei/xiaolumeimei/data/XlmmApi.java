package com.jimei.xiaolumeimei.data;

/**
 * Created by 优尼世界 on 15/12/29.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class XlmmApi {
  public static final String URL_BASE = "http://m.xiaolumeimei.com/rest/v1/";
  //public static final String URL_BASE = "http://dev.xiaolumeimei.com/rest/v1/";
  //public static final String URL_BASE = "http://192.168.1.57:8000/rest/v1/";
  //public static final String URL_BASE = "http://192.168.1.31:9000/rest/v1/";

  public static final String TWO_DIMEN_URL_BASE = "http://m.xiaolumeimei.com";

  //注册链接
  public static final String LOGIN_URL = URL_BASE + "register/customer_login";
  //首页网址(商品、海报)
  public static final String TODAY_URL = URL_BASE + "products/promote_today_paging";
  public static final String TODAY_POSTER_URL = URL_BASE + "posters/today";

  //昨日特卖(商品、海报)
  public static final String YESTERDAY_URL = URL_BASE + "products/promote_previous.json";
  public static final String YESTERDAY_POSTER_URL = URL_BASE + "posters/previous";

  // 女装链接
  public static final String WOMAN_URL = URL_BASE + "products/ladylist";
  //童装链接
  public static final String CHILD_URL = URL_BASE + "products/childlist";

  //同款
  public static final String TONGKUAN_URL = URL_BASE + "products/modellist/";

  //商品详情页
  public static final String PRODUCT_URL = URL_BASE + "products/";

  //所有订单
  public static final String ALL_ORDERS_URL = URL_BASE + "trades";

  //所有退货订单
  public static final String ALL_REFUNDS_URL = URL_BASE + "refunds";
  //购物车
  public static final String CARTS_URL = URL_BASE + "carts";
  //用户信息
  public static final String USERINFO_URL = URL_BASE + "users";

  //用户修改密码
  public static final String CHANGE_USER_PASSWORD_URL =
      URL_BASE + "register/change_user_pwd";

  //用户积分
  public static final String MEMBERSHIPPOINT_URL = URL_BASE + "integral";

  //用户积分记录
  public static final String MEMBERSHIPPOINTLOG_URL = URL_BASE + "integrallog";

  //用户优惠券
  public static final String COUPON_URL = URL_BASE + "usercoupons";

  //获取待支付订单
  public static final String WAITPAY_URL = URL_BASE + "trades/waitpay";

  //获取待发货订单
  public static final String WAITSEND_URL = URL_BASE + "trades/waitsend";

  public static final String QINIU_UPLOAD_URL_BASE =
      "http://7xkyoy.com2.z0.glb.qiniucdn.com/";

  public static final String SETTINGMYINFO_URL =
      URL_BASE + "/xueZhang/servlet/AndroidInfoSev";

  public static final String SETTINGREGISTER_URL =
      URL_BASE + "/xueZhang/servlet/AndroidRegisterSev";

  public static class WRAPPER {
    public static final String STATUS = "status";
    public static final String INFO = "info";
    public static final String DATA = "data";
  }

  public static class CODE {
    public static final int SUCCEED = 0;

    public static final int ANALYSIS_ERROR = -1;
    public static final int NET_INVALID = -2;

    public static final int SMS_ERROR = 1;
    public static final int RONG_ERROR = 2;
    public static final int QINIU_ERROR = 3;
    public static final int PARAMS_ERROR = 9;
    public static final int PARAMS_INVALID = 10;

    public static final int SERVER_ERROR = 100;

    public static final int LOGIN_INVALID = 400;
    public static final int PERMISSION_DENIED = 401;

    public static final int USER_INVALID = 1001;
  }
}
