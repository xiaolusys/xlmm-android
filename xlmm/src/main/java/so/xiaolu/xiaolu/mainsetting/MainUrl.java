package so.xiaolu.xiaolu.mainsetting;

public class MainUrl {

    public String URL_BASE = "http://m.xiaolu.so/rest/v1/";
    public String URL_0 = "http://192.168.1.39:8000/rest/v1/";
    //注册链接
    public String LOGIN_URL = URL_BASE + "register/customer_login";
    //首页网址(商品、海报)
    public String TODAY_URL = URL_BASE + "products/promote_today.json";
    public String TODAY_POSTER_URL = URL_BASE + "posters/today.json";


    //昨日特卖(商品、海报)
    public String YESTERDAY_URL = URL_BASE + "products/promote_previous.json";
    public String YESTERDAY_POSTER_URL = URL_BASE + "posters/previous.json";


    // 女装链接
    public String WOMAN_URL = URL_BASE + "products/ladylist";
    //童装链接
    public String CHILD_URL = URL_BASE + "products/childlist";

    //同款
    public String TONGKUAN_URL = URL_BASE + "products/modellist/";

    //商品详情页
    public String PRODUCT_URL = URL_BASE + "products/";


    //下面没有用到

    public String getTODAY_URL() {
        return TODAY_URL;
    }

    public String getTODAYPOSTER_URL() {
        return TODAY_POSTER_URL;
    }

    public String getYESTERDAY_POSTER_URL() {
        return YESTERDAY_POSTER_URL;
    }

    public String getYESTERDAY_URL() {
        return YESTERDAY_URL;
    }

    public String getTONGKUAN_URL() {
        return TONGKUAN_URL;
    }

    public String getWOMAN_URL() {
        return WOMAN_URL;
    }

    public String getCHILD_URL() {
        return CHILD_URL;
    }

    public String getPRODUCT_URL() {
        return PRODUCT_URL;
    }




    public String SETTINGMYINFO_URL = URL_BASE + "/xueZhang/servlet/AndroidInfoSev";

    public String SETTINGREGISTER_URL = URL_BASE + "/xueZhang/servlet/AndroidRegisterSev";



    public String getSETTINGRESGISTER_URL() {
        return SETTINGREGISTER_URL;
    }

    public String getSETTINGMYINFO_URL() {
        return SETTINGMYINFO_URL;
    }


    public String getLOGIN_URL() {
        return LOGIN_URL;
    }


}
