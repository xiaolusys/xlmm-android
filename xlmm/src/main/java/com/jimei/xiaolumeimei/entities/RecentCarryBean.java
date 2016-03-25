package com.jimei.xiaolumeimei.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by wulei on 3/15/16.
 */
public class RecentCarryBean {


    /**
     * count : 2
     * next : null
     * previous : null
     * results : [{"id":80,"order_num":3,"visitor_num":32,"carry":164.1,"today_carry_num":164.1,"created":"2016-03-25T17:28:30","modified":"2016-03-25T20:23:18","mama_id":44,"today_visitor_num":32,"today_order_num":3,"today_active_value":0,"uni_key":"44-2016-03-25","date_field":"2016-03-25","status":1},{"id":919,"order_num":0,"visitor_num":0,"carry":1,"today_carry_num":1,"created":"2016-03-25T19:47:12","modified":"2016-03-25T19:47:12","mama_id":44,"today_visitor_num":0,"today_order_num":0,"today_active_value":0,"uni_key":"44-2016-03-17","date_field":"2016-03-17","status":1}]
     */

    @SerializedName("count")
    private int count;
    @SerializedName("next")
    private Object next;
    @SerializedName("previous")
    private Object previous;
    /**
     * id : 80
     * order_num : 3
     * visitor_num : 32
     * carry : 164.1
     * today_carry_num : 164.1
     * created : 2016-03-25T17:28:30
     * modified : 2016-03-25T20:23:18
     * mama_id : 44
     * today_visitor_num : 32
     * today_order_num : 3
     * today_active_value : 0
     * uni_key : 44-2016-03-25
     * date_field : 2016-03-25
     * status : 1
     */

    @SerializedName("results")
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
        @SerializedName("id")
        private int id;
        @SerializedName("order_num")
        private int orderNum;
        @SerializedName("visitor_num")
        private int visitorNum;
        @SerializedName("carry")
        private double carry;
        @SerializedName("today_carry_num")
        private double todayCarryNum;
        @SerializedName("created")
        private String created;
        @SerializedName("modified")
        private String modified;
        @SerializedName("mama_id")
        private int mamaId;
        @SerializedName("today_visitor_num")
        private int todayVisitorNum;
        @SerializedName("today_order_num")
        private int todayOrderNum;
        @SerializedName("today_active_value")
        private int todayActiveValue;
        @SerializedName("uni_key")
        private String uniKey;
        @SerializedName("date_field")
        private String dateField;
        @SerializedName("status")
        private int status;

        public void setId(int id) {
            this.id = id;
        }

        public void setOrderNum(int orderNum) {
            this.orderNum = orderNum;
        }

        public void setVisitorNum(int visitorNum) {
            this.visitorNum = visitorNum;
        }

        public void setCarry(double carry) {
            this.carry = carry;
        }

        public void setTodayCarryNum(double todayCarryNum) {
            this.todayCarryNum = todayCarryNum;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public void setModified(String modified) {
            this.modified = modified;
        }

        public void setMamaId(int mamaId) {
            this.mamaId = mamaId;
        }

        public void setTodayVisitorNum(int todayVisitorNum) {
            this.todayVisitorNum = todayVisitorNum;
        }

        public void setTodayOrderNum(int todayOrderNum) {
            this.todayOrderNum = todayOrderNum;
        }

        public void setTodayActiveValue(int todayActiveValue) {
            this.todayActiveValue = todayActiveValue;
        }

        public void setUniKey(String uniKey) {
            this.uniKey = uniKey;
        }

        public void setDateField(String dateField) {
            this.dateField = dateField;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getId() {
            return id;
        }

        public int getOrderNum() {
            return orderNum;
        }

        public int getVisitorNum() {
            return visitorNum;
        }

        public double getCarry() {
            return carry;
        }

        public double getTodayCarryNum() {
            return todayCarryNum;
        }

        public String getCreated() {
            return created;
        }

        public String getModified() {
            return modified;
        }

        public int getMamaId() {
            return mamaId;
        }

        public int getTodayVisitorNum() {
            return todayVisitorNum;
        }

        public int getTodayOrderNum() {
            return todayOrderNum;
        }

        public int getTodayActiveValue() {
            return todayActiveValue;
        }

        public String getUniKey() {
            return uniKey;
        }

        public String getDateField() {
            return dateField;
        }

        public int getStatus() {
            return status;
        }
    }

    @Override
    public String toString() {
        return "RecentCarryBean{" +
                "count=" + count +
                ", next=" + next +
                ", previous=" + previous +
                ", results=" + results +
                '}';
    }
}
