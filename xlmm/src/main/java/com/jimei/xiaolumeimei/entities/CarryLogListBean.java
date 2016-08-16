package com.jimei.xiaolumeimei.entities;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by itxuye(www.itxuye.com) on 2016/02/18.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class CarryLogListBean {

  /**
   * count : 10108
   * next : http://api.xiaolumeimei.com/rest/v2/mama/carry?page=2
   * previous : null
   * results : [{"mama_id":44,"carry_value":0.1,"carry_num":0.1,"carry_type":1,"carry_type_name":"返现","carry_description":"","status":1,"status_display":"待确定","today_carry":0.1,"date_field":"2016-03-18","modified":"2016-03-18T00:15:03","created":"2016-03-18T00:15:03"},{"mama_id":44,"carry_value":0.1,"carry_num":0.1,"carry_type":1,"carry_type_name":"返现","carry_description":"","status":1,"status_display":"待确定","today_carry":0.1,"date_field":"2016-03-17","modified":"2016-03-17T00:02:47","created":"2016-03-17T00:02:47"},{"mama_id":44,"carry_value":0.1,"carry_num":0.1,"carry_type":1,"carry_type_name":"返现","carry_description":"","status":1,"status_display":"待确定","today_carry":0.1,"date_field":"2016-03-16","modified":"2016-03-16T00:30:51","created":"2016-03-16T00:30:51"},{"mama_id":44,"carry_value":96,"carry_num":96,"carry_type":2,"carry_type_name":"佣金","carry_description":"升级导入","status":1,"status_display":"待确定","today_carry":1091.49,"date_field":"2016-03-15","modified":"2016-03-17T14:47:33","created":"2016-03-15T23:06:39"},{"mama_id":44,"carry_value":69.9,"carry_num":69.9,"carry_type":2,"carry_type_name":"佣金","carry_description":"升级导入","status":1,"status_display":"待确定","today_carry":1091.49,"date_field":"2016-03-15","modified":"2016-03-17T14:47:33","created":"2016-03-15T22:16:26"},{"mama_id":44,"carry_value":69.9,"carry_num":69.9,"carry_type":2,"carry_type_name":"佣金","carry_description":"升级导入","status":1,"status_display":"待确定","today_carry":1091.49,"date_field":"2016-03-15","modified":"2016-03-17T14:47:33","created":"2016-03-15T21:30:52"},{"mama_id":44,"carry_value":69.9,"carry_num":69.9,"carry_type":2,"carry_type_name":"佣金","carry_description":"升级导入","status":1,"status_display":"待确定","today_carry":1091.49,"date_field":"2016-03-15","modified":"2016-03-17T14:47:33","created":"2016-03-15T20:15:43"},{"mama_id":44,"carry_value":69.9,"carry_num":69.9,"carry_type":2,"carry_type_name":"佣金","carry_description":"升级导入","status":1,"status_display":"待确定","today_carry":1091.49,"date_field":"2016-03-15","modified":"2016-03-17T14:47:33","created":"2016-03-15T20:14:52"},{"mama_id":44,"carry_value":29.9,"carry_num":29.9,"carry_type":2,"carry_type_name":"佣金","carry_description":"升级导入","status":1,"status_display":"待确定","today_carry":1091.49,"date_field":"2016-03-15","modified":"2016-03-17T14:47:33","created":"2016-03-15T20:13:31"},{"mama_id":44,"carry_value":139.8,"carry_num":139.8,"carry_type":2,"carry_type_name":"佣金","carry_description":"升级导入","status":1,"status_display":"待确定","today_carry":1091.49,"date_field":"2016-03-15","modified":"2016-03-17T14:47:33","created":"2016-03-15T20:01:48"}]
   */

  @SerializedName("count") private int mCount;
  @SerializedName("next") private String mNext;
  @SerializedName("previous") private String mPrevious;
  /**
   * mama_id : 44
   * carry_value : 0.1
   * carry_num : 0.1
   * carry_type : 1
   * carry_type_name : 返现
   * carry_description :
   * status : 1
   * status_display : 待确定
   * today_carry : 0.1
   * date_field : 2016-03-18
   * modified : 2016-03-18T00:15:03
   * created : 2016-03-18T00:15:03
   */

  @SerializedName("results") private List<ResultsEntity> mResults;

  public int getCount() {
    return mCount;
  }

  public void setCount(int count) {
    mCount = count;
  }

  public String getNext() {
    return mNext;
  }

  public void setNext(String next) {
    mNext = next;
  }

  public String getPrevious() {
    return mPrevious;
  }

  public void setPrevious(String previous) {
    mPrevious = previous;
  }

  public List<ResultsEntity> getResults() {
    return mResults;
  }

  public void setResults(List<ResultsEntity> results) {
    mResults = results;
  }

  public static class ResultsEntity {
    @SerializedName("mama_id") private int mMamaId;
    @SerializedName("carry_value") private double mCarryValue;
    @SerializedName("carry_num") private double mCarryNum;
    @SerializedName("carry_type") private int mCarryType;
    @SerializedName("carry_type_name") private String mCarryTypeName;
    @SerializedName("carry_description") private String mCarryDescription;
    @SerializedName("status") private int mStatus;
    @SerializedName("status_display") private String mStatusDisplay;
    @SerializedName("today_carry") private double mTodayCarry;
    @SerializedName("date_field") private String mDateField;
    @SerializedName("modified") private String mModified;
    @SerializedName("created") private String mCreated;

    public int getMamaId() {
      return mMamaId;
    }

    public void setMamaId(int mamaId) {
      mMamaId = mamaId;
    }

    public double getCarryValue() {
      return mCarryValue;
    }

    public void setCarryValue(double carryValue) {
      mCarryValue = carryValue;
    }

    public double getCarryNum() {
      return mCarryNum;
    }

    public void setCarryNum(double carryNum) {
      mCarryNum = carryNum;
    }

    public int getCarryType() {
      return mCarryType;
    }

    public void setCarryType(int carryType) {
      mCarryType = carryType;
    }

    public String getCarryTypeName() {
      return mCarryTypeName;
    }

    public void setCarryTypeName(String carryTypeName) {
      mCarryTypeName = carryTypeName;
    }

    public String getCarryDescription() {
      return mCarryDescription;
    }

    public void setCarryDescription(String carryDescription) {
      mCarryDescription = carryDescription;
    }

    public int getStatus() {
      return mStatus;
    }

    public void setStatus(int status) {
      mStatus = status;
    }

    public String getStatusDisplay() {
      return mStatusDisplay;
    }

    public void setStatusDisplay(String statusDisplay) {
      mStatusDisplay = statusDisplay;
    }

    public double getTodayCarry() {
      return mTodayCarry;
    }

    public void setTodayCarry(double todayCarry) {
      mTodayCarry = todayCarry;
    }

    public String getDateField() {
      return mDateField;
    }

    public void setDateField(String dateField) {
      mDateField = dateField;
    }

    public String getModified() {
      return mModified;
    }

    public void setModified(String modified) {
      mModified = modified;
    }

    public String getCreated() {
      return mCreated;
    }

    public void setCreated(String created) {
      mCreated = created;
    }
  }
}
