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
   * count : 12
   * next : http://dev.xiaolumeimei.com/rest/v2/mama/carry?page=2
   * previous : null
   * results : [{"mama_id":5,"carry_value":36,"carry_type":2,"carry_type_name":"佣金","status":3,"status_display":"取消","today_carry":null,"modified":"2016-03-10T18:39:56","created":"2016-03-10T18:26:19"},{"mama_id":5,"carry_value":31.2,"carry_type":2,"carry_type_name":"佣金","status":3,"status_display":"取消","today_carry":null,"modified":"2016-03-09T18:00:27","created":"2016-03-09T17:28:32"},{"mama_id":5,"carry_value":17.9,"carry_type":1,"carry_type_name":"返现","status":1,"status_display":"待确定","today_carry":null,"modified":"2016-03-08T12:37:53","created":"2016-03-08T12:37:53"},{"mama_id":5,"carry_value":2.19,"carry_type":3,"carry_type_name":"奖金","status":2,"status_display":"已确定","today_carry":null,"modified":"2016-03-08T12:03:32","created":"2016-03-08T12:01:11"},{"mama_id":5,"carry_value":2.39,"carry_type":2,"carry_type_name":"佣金","status":1,"status_display":"待确定","today_carry":null,"modified":"2016-03-08T11:20:33","created":"2016-03-08T11:20:33"},{"mama_id":5,"carry_value":2.19,"carry_type":2,"carry_type_name":"佣金","status":1,"status_display":"待确定","today_carry":null,"modified":"2016-03-08T10:25:21","created":"2016-03-08T10:25:21"},{"mama_id":5,"carry_value":9.19,"carry_type":2,"carry_type_name":"佣金","status":3,"status_display":"取消","today_carry":null,"modified":"2016-03-08T10:48:44","created":"2016-03-08T10:20:05"},{"mama_id":5,"carry_value":6.29,"carry_type":2,"carry_type_name":"佣金","status":1,"status_display":"待确定","today_carry":null,"modified":"2016-03-08T10:07:10","created":"2016-03-08T10:07:10"},{"mama_id":5,"carry_value":5.79,"carry_type":2,"carry_type_name":"佣金","status":1,"status_display":"待确定","today_carry":null,"modified":"2016-03-08T10:06:36","created":"2016-03-08T10:06:36"},{"mama_id":5,"carry_value":1.39,"carry_type":2,"carry_type_name":"佣金","status":1,"status_display":"待确定","today_carry":null,"modified":"2016-03-08T09:57:58","created":"2016-03-08T09:57:58"}]
   */

  @SerializedName("count") private int mCount;
  @SerializedName("next") private String mNext;
  /**
   * mama_id : 5
   * carry_value : 36.0
   * carry_type : 2
   * carry_type_name : 佣金
   * status : 3
   * status_display : 取消
   * today_carry : null
   * modified : 2016-03-10T18:39:56
   * created : 2016-03-10T18:26:19
   */

  @SerializedName("results") private List<ResultsEntity> mResults;

  public void setCount(int count) {
    this.mCount = count;
  }

  public void setNext(String next) {
    this.mNext = next;
  }


  public void setResults(List<ResultsEntity> results) {
    this.mResults = results;
  }

  public int getCount() {
    return mCount;
  }

  public String getNext() {
    return mNext;
  }


  public List<ResultsEntity> getResults() {
    return mResults;
  }

  public static class ResultsEntity {
    @SerializedName("mama_id") private int mMamaId;
    @SerializedName("carry_value") private double mCarryValue;
    @SerializedName("carry_type") private int mCarryType;
    @SerializedName("carry_type_name") private String mCarryTypeName;
    @SerializedName("status") private int mStatus;
    @SerializedName("status_display") private String mStatusDisplay;
    @SerializedName("today_carry") private Object mTodayCarry;
    @SerializedName("modified") private String mModified;
    @SerializedName("created") private String mCreated;

    public void setMamaId(int mamaId) {
      this.mMamaId = mamaId;
    }

    public void setCarryValue(double carryValue) {
      this.mCarryValue = carryValue;
    }

    public void setCarryType(int carryType) {
      this.mCarryType = carryType;
    }

    public void setCarryTypeName(String carryTypeName) {
      this.mCarryTypeName = carryTypeName;
    }

    public void setStatus(int status) {
      this.mStatus = status;
    }

    public void setStatusDisplay(String statusDisplay) {
      this.mStatusDisplay = statusDisplay;
    }

    public void setTodayCarry(Object todayCarry) {
      this.mTodayCarry = todayCarry;
    }

    public void setModified(String modified) {
      this.mModified = modified;
    }

    public void setCreated(String created) {
      this.mCreated = created;
    }

    public int getMamaId() {
      return mMamaId;
    }

    public double getCarryValue() {
      return mCarryValue;
    }

    public int getCarryType() {
      return mCarryType;
    }

    public String getCarryTypeName() {
      return mCarryTypeName;
    }

    public int getStatus() {
      return mStatus;
    }

    public String getStatusDisplay() {
      return mStatusDisplay;
    }

    public Object getTodayCarry() {
      return mTodayCarry;
    }

    public String getModified() {
      return mModified;
    }

    public String getCreated() {
      return mCreated;
    }
  }
}
