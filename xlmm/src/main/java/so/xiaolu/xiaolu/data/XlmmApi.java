package so.xiaolu.xiaolu.data;

/**
 * Created by itxuye(www.itxuye.com) on 15/12/29.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class XlmmApi {

  public static final String URL_BASE = "http://api.xiaolumeimei.com/rest/v1/";
  //注册链接
  public static final String LOGIN_URL = URL_BASE + "register/customer_login";
  //首页网址(商品、海报)
  public static final String TODAY_URL = URL_BASE + "products/promote_today_paging";
  public static final String TODAY_POSTER_URL = URL_BASE + "posters/today.json";

  //昨日特卖(商品、海报)
  public static final String YESTERDAY_URL = URL_BASE + "products/promote_previous.json";
  public static final String YESTERDAY_POSTER_URL = URL_BASE + "posters/previous.json";

  // 女装链接
  public static final String WOMAN_URL = URL_BASE + "products/ladylist";
  //童装链接
  public static final String CHILD_URL = URL_BASE + "products/childlist";

  //同款
  public static final String TONGKUAN_URL = URL_BASE + "products/modellist/";

  //商品详情页
  public static final String PRODUCT_URL = URL_BASE + "products/";
  //所有订单
  public static final String ALL_ORDERS_URL = URL_BASE + "trades/";

  public static final String SETTINGMYINFO_URL =
      URL_BASE + "/xueZhang/servlet/AndroidInfoSev";

  public static final String SETTINGREGISTER_URL =
      URL_BASE + "/xueZhang/servlet/AndroidRegisterSev";
}
