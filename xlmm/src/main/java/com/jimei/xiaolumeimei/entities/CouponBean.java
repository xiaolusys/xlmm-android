package com.jimei.xiaolumeimei.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CouponBean {

    /**
     * count : 6
     * next : null
     * previous : null
     * results : [{"id":115729,"coupon_type":9,"title":"母亲节满188减30元","customer":"1","coupon_no":"YH160509572fe874919fa","coupon_value":30,"valid":true,"poll_status":1,"deadline":"2016-05-10 10:00:00","sale_trade":"0","status":0,"created":"2016-05-09T09:31:32","modified":"2016-05-09T09:31:32","use_fee":188,"coupon_type_display":"普通","use_fee_des":"满188.0可用","pros_desc":"全场通用","start_time":"2016-05-08 10:00:00"},{"id":115728,"coupon_type":9,"title":"母亲节满88减10元","customer":"1","coupon_no":"YH160509572fe87484d92","coupon_value":10,"valid":true,"poll_status":1,"deadline":"2016-05-10 10:00:00","sale_trade":"0","status":0,"created":"2016-05-09T09:31:32","modified":"2016-05-09T09:31:32","use_fee":88,"coupon_type_display":"普通","use_fee_des":"满88.0可用","pros_desc":"全场通用","start_time":"2016-05-08 10:00:00"},{"id":115727,"coupon_type":9,"title":"母亲节5元无限制","customer":"1","coupon_no":"YH160509572fe874778e0","coupon_value":5,"valid":true,"poll_status":1,"deadline":"2016-05-10 10:00:00","sale_trade":"0","status":0,"created":"2016-05-09T09:31:32","modified":"2016-05-09T09:31:32","use_fee":0,"coupon_type_display":"普通","use_fee_des":"满0.0可用","pros_desc":"全场通用","start_time":"2016-05-08 10:00:00"},{"id":110049,"coupon_type":9,"title":"答题活动优惠券","customer":"1","coupon_no":"YH16032956fa33ef6dc16","coupon_value":5,"valid":true,"poll_status":1,"deadline":"2016-05-31 12:00:00","sale_trade":"336862","status":0,"created":"2016-03-29T15:51:11","modified":"2016-05-04T11:15:43","use_fee":50,"coupon_type_display":"普通","use_fee_des":"满50.0可用","pros_desc":"全场通用","start_time":"2016-03-22 00:00:00"},{"id":63266,"coupon_type":9,"title":"test-app","customer":"1","coupon_no":"YH151225567c9a8b693f8","coupon_value":100,"valid":true,"poll_status":1,"deadline":"2017-03-29 00:00:00","sale_trade":"317663","status":2,"created":"2015-12-25T09:23:23","modified":"2016-03-30T20:14:45","use_fee":0,"coupon_type_display":"普通","use_fee_des":"满0.0可用","pros_desc":"全场通用","start_time":"2016-03-29 00:00:00"},{"id":590,"coupon_type":0,"title":"代理专享满30减30","customer":"1","coupon_no":"YH15090755edab8edc64e","coupon_value":30,"valid":true,"poll_status":1,"deadline":"2017-08-01 12:00:00","sale_trade":"329351","status":2,"created":"2015-09-07T23:21:50","modified":"2016-04-20T15:37:44","use_fee":0,"coupon_type_display":"代理优惠券","use_fee_des":"满0.0可用","pros_desc":"全场通用","start_time":"2015-08-01 12:00:00"}]
     */

    @SerializedName("count")
    private int count;
    @SerializedName("next")
    private Object next;
    @SerializedName("previous")
    private Object previous;
    /**
     * id : 115729
     * coupon_type : 9
     * title : 母亲节满188减30元
     * customer : 1
     * coupon_no : YH160509572fe874919fa
     * coupon_value : 30.0
     * valid : true
     * poll_status : 1
     * deadline : 2016-05-10 10:00:00
     * sale_trade : 0
     * status : 0
     * created : 2016-05-09T09:31:32
     * modified : 2016-05-09T09:31:32
     * use_fee : 188.0
     * coupon_type_display : 普通
     * use_fee_des : 满188.0可用
     * pros_desc : 全场通用
     * start_time : 2016-05-08 10:00:00
     */
    @SerializedName("results")
    private List<ResultsBean> results;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Object getNext() {
        return next;
    }

    public void setNext(Object next) {
        this.next = next;
    }

    public Object getPrevious() {
        return previous;
    }

    public void setPrevious(Object previous) {
        this.previous = previous;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        @SerializedName("id")
        private int id;
        @SerializedName("coupon_type")
        private int coupon_type;
        @SerializedName("title")
        private String title;
        @SerializedName("customer")
        private String customer;
        @SerializedName("coupon_no")
        private String coupon_no;
        @SerializedName("coupon_value")
        private double coupon_value;
        @SerializedName("valid")
        private boolean valid;
        @SerializedName("poll_status")
        private int poll_status;
        @SerializedName("deadline")
        private String deadline;
        @SerializedName("sale_trade")
        private String sale_trade;
        @SerializedName("status")
        private int status;
        @SerializedName("created")
        private String created;
        @SerializedName("modified")
        private String modified;
        @SerializedName("use_fee")
        private double use_fee;
        @SerializedName("coupon_type_display")
        private String coupon_type_display;
        @SerializedName("use_fee_des")
        private String use_fee_des;
        @SerializedName("pros_desc")
        private String pros_desc;
        @SerializedName("start_time")
        private String start_time;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCoupon_type() {
            return coupon_type;
        }

        public void setCoupon_type(int coupon_type) {
            this.coupon_type = coupon_type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCustomer() {
            return customer;
        }

        public void setCustomer(String customer) {
            this.customer = customer;
        }

        public String getCoupon_no() {
            return coupon_no;
        }

        public void setCoupon_no(String coupon_no) {
            this.coupon_no = coupon_no;
        }

        public double getCoupon_value() {
            return coupon_value;
        }

        public void setCoupon_value(double coupon_value) {
            this.coupon_value = coupon_value;
        }

        public boolean isValid() {
            return valid;
        }

        public void setValid(boolean valid) {
            this.valid = valid;
        }

        public int getPoll_status() {
            return poll_status;
        }

        public void setPoll_status(int poll_status) {
            this.poll_status = poll_status;
        }

        public String getDeadline() {
            return deadline;
        }

        public void setDeadline(String deadline) {
            this.deadline = deadline;
        }

        public String getSale_trade() {
            return sale_trade;
        }

        public void setSale_trade(String sale_trade) {
            this.sale_trade = sale_trade;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getModified() {
            return modified;
        }

        public void setModified(String modified) {
            this.modified = modified;
        }

        public double getUse_fee() {
            return use_fee;
        }

        public void setUse_fee(double use_fee) {
            this.use_fee = use_fee;
        }

        public String getCoupon_type_display() {
            return coupon_type_display;
        }

        public void setCoupon_type_display(String coupon_type_display) {
            this.coupon_type_display = coupon_type_display;
        }

        public String getUse_fee_des() {
            return use_fee_des;
        }

        public void setUse_fee_des(String use_fee_des) {
            this.use_fee_des = use_fee_des;
        }

        public String getPros_desc() {
            return pros_desc;
        }

        public void setPros_desc(String pros_desc) {
            this.pros_desc = pros_desc;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }
    }
}
