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
    private String next;
    @SerializedName("previous")
    private String previous;
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
    private List<CouponEntity> results;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public List<CouponEntity> getResults() {
        return results;
    }

    public void setResults(List<CouponEntity> results) {
        this.results = results;
    }

}
