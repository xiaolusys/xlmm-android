package com.jimei.xiaolumeimei.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by wulei on 3/15/16.
 */
public class RecentCarryBean {

    /**
     * order_num : 0
     * carry : 0.0
     * date_field : 2016-03-11
     * visitor_num : 0
     */

    @SerializedName("order_num")
    private int orderNum;
    @SerializedName("carry")
    private double carry;
    @SerializedName("date_field")
    private String dateField;
    @SerializedName("visitor_num")
    private int visitorNum;

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public void setCarry(double carry) {
        this.carry = carry;
    }

    public void setDateField(String dateField) {
        this.dateField = dateField;
    }

    public void setVisitorNum(int visitorNum) {
        this.visitorNum = visitorNum;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public double getCarry() {
        return carry;
    }

    public String getDateField() {
        return dateField;
    }

    public int getVisitorNum() {
        return visitorNum;
    }

    @Override
    public String toString() {
        return "RecentCarryBean{" +
                "orderNum=" + orderNum +
                ", carry=" + carry +
                ", dateField='" + dateField + '\'' +
                ", visitorNum=" + visitorNum +
                '}';
    }
}
