package com.jimei.xiaolumeimei.entities;

import java.util.List;

/**
 * Created by wulei on 2016/1/21.
 */
public class CouponBean {

    /**
     * count : 1
     * next : null
     * previous : null
     * results : [{"id":178,"coupon_type":9,"title":"开单优惠券","customer":"33","coupon_no":"YH151226567e8cc226e22","coupon_value":5,"valid":true,"poll_status":1,"deadline":"2016-01-03 12:00:00","sale_trade":"0","status":0,"created":"2015-12-26T20:49:06","modified":"2016-01-21T09:05:19","use_fee":0}]
     */

    private int count;
    private Object next;
    private Object previous;
    /**
     * id : 178
     * coupon_type : 9
     * title : 开单优惠券
     * customer : 33
     * coupon_no : YH151226567e8cc226e22
     * coupon_value : 5.0
     * valid : true
     * poll_status : 1
     * deadline : 2016-01-03 12:00:00
     * sale_trade : 0
     * status : 0
     * created : 2015-12-26T20:49:06
     * modified : 2016-01-21T09:05:19
     * use_fee : 0.0
     */

    private List<ResultsEntity> results;

    public void setCount(int count) {
        this.count = count;
    }

    public void setNext(Object next) {
        this.next = next;
    }

    public void setPrevious(Object previous) {
        this.previous = previous;
    }

    public void setResults(List<ResultsEntity> results) {
        this.results = results;
    }

    public int getCount() {
        return count;
    }

    public Object getNext() {
        return next;
    }

    public Object getPrevious() {
        return previous;
    }

    public List<ResultsEntity> getResults() {
        return results;
    }

    public static class ResultsEntity {
        private int id;
        private int coupon_type;
        private String title;
        private String customer;
        private String coupon_no;
        private double coupon_value;
        private boolean valid;
        private int poll_status;
        private String deadline;
        private String sale_trade;
        private int status;
        private String created;
        private String modified;
        private double use_fee;
        private String coupon_type_display;

        public void setId(int id) {
            this.id = id;
        }

        public void setCoupon_type(int coupon_type) {
            this.coupon_type = coupon_type;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setCustomer(String customer) {
            this.customer = customer;
        }

        public void setCoupon_no(String coupon_no) {
            this.coupon_no = coupon_no;
        }

        public void setCoupon_value(double coupon_value) {
            this.coupon_value = coupon_value;
        }

        public void setValid(boolean valid) {
            this.valid = valid;
        }

        public void setPoll_status(int poll_status) {
            this.poll_status = poll_status;
        }

        public void setDeadline(String deadline) {
            this.deadline = deadline;
        }

        public void setSale_trade(String sale_trade) {
            this.sale_trade = sale_trade;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public void setModified(String modified) {
            this.modified = modified;
        }

        public void setUse_fee(double use_fee) {
            this.use_fee = use_fee;
        }

        public void setCoupon_type_display(String coupon_type_display) {
            this.coupon_type_display = coupon_type_display;
        }

        public int getId() {
            return id;
        }

        public int getCoupon_type() {
            return coupon_type;
        }

        public String getTitle() {
            return title;
        }

        public String getCustomer() {
            return customer;
        }

        public String getCoupon_no() {
            return coupon_no;
        }

        public double getCoupon_value() {
            return coupon_value;
        }

        public boolean isValid() {
            return valid;
        }

        public int getPoll_status() {
            return poll_status;
        }

        public String getDeadline() {
            return deadline;
        }

        public String getSale_trade() {
            return sale_trade;
        }

        public int getStatus() {
            return status;
        }

        public String getCreated() {
            return created;
        }

        public String getModified() {
            return modified;
        }

        public double getUse_fee() {
            return use_fee;
        }

        public String getCoupon_type_display() {
            return coupon_type_display;
        }

        @Override public String toString() {
            return "ResultsEntity{" +
                "id=" + id +
                ", coupon_type=" + coupon_type +
                ", title='" + title + '\'' +
                ", customer='" + customer + '\'' +
                ", coupon_no='" + coupon_no + '\'' +
                ", coupon_value=" + coupon_value +
                ", valid=" + valid +
                ", poll_status=" + poll_status +
                ", deadline='" + deadline + '\'' +
                ", sale_trade='" + sale_trade + '\'' +
                ", status=" + status +
                ", created='" + created + '\'' +
                ", modified='" + modified + '\'' +
                ", use_fee=" + use_fee +
                ", coupon_type_display='" + coupon_type_display + '\'' +
                '}';
        }
    }

    @Override
    public String toString() {
        return "CouponBean{" +
                "count=" + count +
                ", next=" + next +
                ", previous=" + previous +
                ", results=" + results +
                '}';
    }
}
