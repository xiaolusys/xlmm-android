package com.jimei.xiaolumeimei.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by itxuye on 16/4/28.
 */
public class PortalBean {

    /**
     * id : 295
     * posters : [{"item_link":"http://m.xiaolumeimei.com/nvzhuang.html","pic_link":"http://image.xiaolu.so/post1461546650935未标题1.jpg","app_link":"com.jimei.xlmm://app/v1/products/ladylist","subject":["2折起","小鹿美美  女装专场"]},{"item_link":"http://m.xiaolumeimei.com/chaotong.html","pic_link":"http://image.xiaolu.so/post146154667965472.png","app_link":"com.jimei.xlmm://app/v1/products/childlist","subject":["2折起","小鹿美美  童装专场"]}]
     * categorys : [{"cat_link":"http://m.xiaolumeimei.com/chaotong.html?","cat_img":"http://7xogkj.com2.z0.glb.qiniucdn.com/category/child.png","id":5,"name":"童装专区"},{"cat_link":"http://m.xiaolumeimei.com/nvzhuang.html?","cat_img":"http://7xogkj.com2.z0.glb.qiniucdn.com/category/lady.png","id":8,"name":"女装专区"}]
     * activitys : [{"id":5,"title":"【精选Top10】用心推荐","login_required":true,"act_desc":"小鹿美美本周top10热销商品，满80减10元，优惠多多","act_img":"http://image.xiaolu.so/TT1461405903371AppTop10entrance.png","mask_link":"","act_link":"http://m.xiaolumeimei.com/sale/promotion/join/5/?ufrom=app","act_type":"webview","act_applink":"","start_time":"2016-04-24T20:00:00","end_time":"2016-05-31T20:00:00","order_val":0,"extras":{"html":{"apply":"/activity/post/index.html"},"data":{}},"total_member_num":2000,"friend_member_num":16,"is_active":true}]
     * promotion_brands : [{"id":1,"brand_name":"妖精的口袋  专场","brand_desc":"","brand_pic":"http://7xogkj.com2.z0.glb.qiniucdn.com/brand/yinman.png","brand_post":"","brand_applink":"","start_time":"2016-04-28T09:40:20","end_time":"2016-05-31T09:40:25"}]
     * active_time : 2016-04-27T09:30:00
     */

    private int id;
    private String active_time;
    /**
     * item_link : http://m.xiaolumeimei.com/nvzhuang.html
     * pic_link : http://image.xiaolu.so/post1461546650935未标题1.jpg
     * app_link : com.jimei.xlmm://app/v1/products/ladylist
     * subject : ["2折起","小鹿美美  女装专场"]
     */

    private List<PostersBean> posters;
    /**
     * cat_link : http://m.xiaolumeimei.com/chaotong.html?
     * cat_img : http://7xogkj.com2.z0.glb.qiniucdn.com/category/child.png
     * id : 5
     * name : 童装专区
     */

    private List<CategorysBean> categorys;
    /**
     * id : 5
     * title : 【精选Top10】用心推荐
     * login_required : true
     * act_desc : 小鹿美美本周top10热销商品，满80减10元，优惠多多
     * act_img : http://image.xiaolu.so/TT1461405903371AppTop10entrance.png
     * mask_link :
     * act_link : http://m.xiaolumeimei.com/sale/promotion/join/5/?ufrom=app
     * act_type : webview
     * act_applink :
     * start_time : 2016-04-24T20:00:00
     * end_time : 2016-05-31T20:00:00
     * order_val : 0
     * extras : {"html":{"apply":"/activity/post/index.html"},"data":{}}
     * total_member_num : 2000
     * friend_member_num : 16
     * is_active : true
     */

    private List<ActivitysBean> activitys;
    /**
     * id : 1
     * brand_name : 妖精的口袋  专场
     * brand_desc :
     * brand_pic : http://7xogkj.com2.z0.glb.qiniucdn.com/brand/yinman.png
     * brand_post :
     * brand_applink :
     * start_time : 2016-04-28T09:40:20
     * end_time : 2016-05-31T09:40:25
     */

    private List<PromotionBrandsBean> promotion_brands;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getActive_time() {
        return active_time;
    }

    public void setActive_time(String active_time) {
        this.active_time = active_time;
    }

    public List<PostersBean> getPosters() {
        return posters;
    }

    public void setPosters(List<PostersBean> posters) {
        this.posters = posters;
    }

    public List<CategorysBean> getCategorys() {
        return categorys;
    }

    public void setCategorys(List<CategorysBean> categorys) {
        this.categorys = categorys;
    }

    public List<ActivitysBean> getActivitys() {
        return activitys;
    }

    public void setActivitys(List<ActivitysBean> activitys) {
        this.activitys = activitys;
    }

    public List<PromotionBrandsBean> getPromotion_brands() {
        return promotion_brands;
    }

    public void setPromotion_brands(List<PromotionBrandsBean> promotion_brands) {
        this.promotion_brands = promotion_brands;
    }

    public static class PostersBean {
        private String item_link;
        private String pic_link;
        private String app_link;
        private List<String> subject;

        public String getItem_link() {
            return item_link;
        }

        public void setItem_link(String item_link) {
            this.item_link = item_link;
        }

        public String getPic_link() {
            return pic_link;
        }

        public void setPic_link(String pic_link) {
            this.pic_link = pic_link;
        }

        public String getApp_link() {
            return app_link;
        }

        public void setApp_link(String app_link) {
            this.app_link = app_link;
        }

        public List<String> getSubject() {
            return subject;
        }

        public void setSubject(List<String> subject) {
            this.subject = subject;
        }
    }

    public static class CategorysBean {
        private String cat_link;
        private String cat_img;
        private int id;
        private String name;

        public String getCat_link() {
            return cat_link;
        }

        public void setCat_link(String cat_link) {
            this.cat_link = cat_link;
        }

        public String getCat_img() {
            return cat_img;
        }

        public void setCat_img(String cat_img) {
            this.cat_img = cat_img;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class ActivitysBean {
        private int id;
        private String title;
        private boolean login_required;
        private String act_desc;
        private String act_img;
        private String mask_link;
        private String act_link;
        private String act_type;
        private String act_applink;
        private String start_time;
        private String end_time;
        private int order_val;
        /**
         * html : {"apply":"/activity/post/index.html"}
         * data : {}
         */

        private ExtrasBean extras;
        private int total_member_num;
        private int friend_member_num;
        private boolean is_active;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public boolean isLogin_required() {
            return login_required;
        }

        public void setLogin_required(boolean login_required) {
            this.login_required = login_required;
        }

        public String getAct_desc() {
            return act_desc;
        }

        public void setAct_desc(String act_desc) {
            this.act_desc = act_desc;
        }

        public String getAct_img() {
            return act_img;
        }

        public void setAct_img(String act_img) {
            this.act_img = act_img;
        }

        public String getMask_link() {
            return mask_link;
        }

        public void setMask_link(String mask_link) {
            this.mask_link = mask_link;
        }

        public String getAct_link() {
            return act_link;
        }

        public void setAct_link(String act_link) {
            this.act_link = act_link;
        }

        public String getAct_type() {
            return act_type;
        }

        public void setAct_type(String act_type) {
            this.act_type = act_type;
        }

        public String getAct_applink() {
            return act_applink;
        }

        public void setAct_applink(String act_applink) {
            this.act_applink = act_applink;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public int getOrder_val() {
            return order_val;
        }

        public void setOrder_val(int order_val) {
            this.order_val = order_val;
        }

        public ExtrasBean getExtras() {
            return extras;
        }

        public void setExtras(ExtrasBean extras) {
            this.extras = extras;
        }

        public int getTotal_member_num() {
            return total_member_num;
        }

        public void setTotal_member_num(int total_member_num) {
            this.total_member_num = total_member_num;
        }

        public int getFriend_member_num() {
            return friend_member_num;
        }

        public void setFriend_member_num(int friend_member_num) {
            this.friend_member_num = friend_member_num;
        }

        public boolean isIs_active() {
            return is_active;
        }

        public void setIs_active(boolean is_active) {
            this.is_active = is_active;
        }

        public static class ExtrasBean {
            /**
             * apply : /activity/post/index.html
             */

            private HtmlBean html;
            private DataBean data;

            public HtmlBean getHtml() {
                return html;
            }

            public void setHtml(HtmlBean html) {
                this.html = html;
            }

            public DataBean getData() {
                return data;
            }

            public void setData(DataBean data) {
                this.data = data;
            }

            public static class HtmlBean {
            }

            public static class DataBean {
            }

            @SerializedName("template_id") private String mTemplateId;

            public String getTemplateId() {
                return mTemplateId;
            }

            public void setTemplateId(String templateId) {
                mTemplateId = templateId;
            }
        }
    }

    public static class PromotionBrandsBean {
        private int id;
        private String brand_name;
        private String brand_desc;
        private String brand_pic;
        private String brand_post;
        private String brand_applink;
        private String start_time;
        private String end_time;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getBrand_name() {
            return brand_name;
        }

        public void setBrand_name(String brand_name) {
            this.brand_name = brand_name;
        }

        public String getBrand_desc() {
            return brand_desc;
        }

        public void setBrand_desc(String brand_desc) {
            this.brand_desc = brand_desc;
        }

        public String getBrand_pic() {
            return brand_pic;
        }

        public void setBrand_pic(String brand_pic) {
            this.brand_pic = brand_pic;
        }

        public String getBrand_post() {
            return brand_post;
        }

        public void setBrand_post(String brand_post) {
            this.brand_post = brand_post;
        }

        public String getBrand_applink() {
            return brand_applink;
        }

        public void setBrand_applink(String brand_applink) {
            this.brand_applink = brand_applink;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }
    }
}
