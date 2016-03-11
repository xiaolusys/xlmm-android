package com.jimei.xiaolumeimei.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by wulei on 3/11/16.
 */
public class MamaLivenessBean {

    /**
     * count : 6
     * next : null
     * previous : null
     * results : [{"mama_id":5,"value_num":10,"value_type":2,"value_type_name":"订单","uni_key":"active-5-2-2016-02-23-xo16022356cc287c4767f","date_field":"2016-02-23","status":3,"status_display":"已取消","today_carry":null,"modified":"2016-03-10T18:39:57","created":"2016-03-10T18:31:36"},{"mama_id":5,"value_num":5,"value_type":4,"value_type_name":"粉丝","uni_key":"active-5-4-32","date_field":"2016-03-10","status":3,"status_display":"已取消","today_carry":null,"modified":"2016-03-10T16:57:32","created":"2016-03-10T16:57:32"},{"mama_id":5,"value_num":1,"value_type":1,"value_type_name":"点击","uni_key":"active-5-1-2016-03-09","date_field":"2016-03-09","status":1,"status_display":"待确定","today_carry":null,"modified":"2016-03-09T12:23:22","created":"2016-03-09T12:23:22"},{"mama_id":5,"value_num":50,"value_type":3,"value_type_name":"推荐","uni_key":"active-5-3-55390","date_field":"2016-03-09","status":1,"status_display":"待确定","today_carry":null,"modified":"2016-03-09T10:18:11","created":"2016-03-09T10:18:11"},{"mama_id":5,"value_num":5,"value_type":4,"value_type_name":"粉丝","uni_key":"active-5-4-575510","date_field":"2016-03-09","status":1,"status_display":"待确定","today_carry":null,"modified":"2016-03-09T10:18:10","created":"2016-03-09T10:18:10"},{"mama_id":5,"value_num":5,"value_type":4,"value_type_name":"粉丝","uni_key":"active-5-4-727721","date_field":"2016-03-09","status":1,"status_display":"待确定","today_carry":null,"modified":"2016-03-09T10:17:52","created":"2016-03-09T10:17:52"}]
     */

    @SerializedName("count")
    private int count;
    @SerializedName("next")
    private Object next;
    @SerializedName("previous")
    private Object previous;
    /**
     * mama_id : 5
     * value_num : 10
     * value_type : 2
     * value_type_name : 订单
     * uni_key : active-5-2-2016-02-23-xo16022356cc287c4767f
     * date_field : 2016-02-23
     * status : 3
     * status_display : 已取消
     * today_carry : 0.0
     * modified : 2016-03-10T18:39:57
     * created : 2016-03-10T18:31:36
     */

    @SerializedName("results")
    private List<LivenessEntity> MamaLiveness;

    public void setCount(int count) {
        this.count = count;
    }

    public void setNext(Object next) {
        this.next = next;
    }

    public void setPrevious(Object previous) {
        this.previous = previous;
    }

    public void setMamaLiveness(List<LivenessEntity> MamaLiveness) {
        this.MamaLiveness = MamaLiveness;
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

    public List<LivenessEntity> getMamaLiveness() {
        return MamaLiveness;
    }

    public static class LivenessEntity {
        @SerializedName("mama_id")
        private int mamaId;
        @SerializedName("value_num")
        private int valueNum;
        @SerializedName("value_type")
        private int valueType;
        @SerializedName("value_type_name")
        private String valueTypeName;
        @SerializedName("uni_key")
        private String uniKey;
        @SerializedName("date_field")
        private String dateField;
        @SerializedName("status")
        private int status;
        @SerializedName("status_display")
        private String statusDisplay;
        @SerializedName("today_carry")
        private double todayCarry;
        @SerializedName("modified")
        private String modified;
        @SerializedName("created")
        private String created;

        public void setMamaId(int mamaId) {
            this.mamaId = mamaId;
        }

        public void setValueNum(int valueNum) {
            this.valueNum = valueNum;
        }

        public void setValueType(int valueType) {
            this.valueType = valueType;
        }

        public void setValueTypeName(String valueTypeName) {
            this.valueTypeName = valueTypeName;
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

        public void setStatusDisplay(String statusDisplay) {
            this.statusDisplay = statusDisplay;
        }

        public void setTodayCarry(double todayCarry) {
            this.todayCarry = todayCarry;
        }

        public void setModified(String modified) {
            this.modified = modified;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public int getMamaId() {
            return mamaId;
        }

        public int getValueNum() {
            return valueNum;
        }

        public int getValueType() {
            return valueType;
        }

        public String getValueTypeName() {
            return valueTypeName;
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

        public String getStatusDisplay() {
            return statusDisplay;
        }

        public double getTodayCarry() {
            return todayCarry;
        }

        public String getModified() {
            return modified;
        }

        public String getCreated() {
            return created;
        }
    }
}
