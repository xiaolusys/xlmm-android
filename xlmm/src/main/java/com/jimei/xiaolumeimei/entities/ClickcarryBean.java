package com.jimei.xiaolumeimei.entities;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by itxuye(www.itxuye.com) on 2016/03/14.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class ClickcarryBean {

  /**
   * count : 2
   * next :
   * previous :
   * results : [{"mama_id":5,"click_num":1,"init_order_num":0,"init_click_price":0.1,"init_click_limit":10,"confirmed_order_num":0,"confirmed_click_price":0,"confirmed_click_limit":0,"total_value":0.1,"carry_description":"","carry_plan_name":"","date_field":"2016-03-14","uni_key":"click-5-2016-03-14","status":1,"status_display":"待确定","today_carry":0.1,"modified":"2016-03-14T13:02:39","created":"2016-03-14T13:02:39"},{"mama_id":5,"click_num":2,"init_order_num":1,"init_click_price":0.2,"init_click_limit":60,"confirmed_order_num":0,"confirmed_click_price":0,"confirmed_click_limit":0,"total_value":0.4,"carry_description":"","carry_plan_name":"","date_field":"2016-03-13","uni_key":"click-5-2016-03-13","status":1,"status_display":"待确定","today_carry":0.4,"modified":"2016-03-13T22:02:33","created":"2016-03-13T22:02:33"}]
   */

  @SerializedName("count") private int mCount;
  @SerializedName("next") private String mNext;
  @SerializedName("previous") private String mPrevious;
  /**
   * mama_id : 5
   * click_num : 1
   * init_order_num : 0
   * init_click_price : 0.1
   * init_click_limit : 10
   * confirmed_order_num : 0
   * confirmed_click_price : 0.0
   * confirmed_click_limit : 0
   * total_value : 0.1
   * carry_description :
   * carry_plan_name :
   * date_field : 2016-03-14
   * uni_key : click-5-2016-03-14
   * status : 1
   * status_display : 待确定
   * today_carry : 0.1
   * modified : 2016-03-14T13:02:39
   * created : 2016-03-14T13:02:39
   */

  @SerializedName("results") private List<ResultsEntity> mResults;

  public int getCount() {
    return mCount;
  }

  public void setCount(int count) {
    this.mCount = count;
  }

  public String getNext() {
    return mNext;
  }

  public void setNext(String next) {
    this.mNext = next;
  }

  public String getPrevious() {
    return mPrevious;
  }

  public void setPrevious(String previous) {
    this.mPrevious = previous;
  }

  public List<ResultsEntity> getResults() {
    return mResults;
  }

  public void setResults(List<ResultsEntity> results) {
    this.mResults = results;
  }

  public static class ResultsEntity {
    @SerializedName("mama_id") private int mMamaId;
    @SerializedName("click_num") private int mClickNum;
    @SerializedName("init_order_num") private int mInitOrderNum;
    @SerializedName("init_click_price") private double mInitClickPrice;
    @SerializedName("init_click_limit") private int mInitClickLimit;
    @SerializedName("confirmed_order_num") private int mConfirmedOrderNum;
    @SerializedName("confirmed_click_price") private double mConfirmedClickPrice;
    @SerializedName("confirmed_click_limit") private int mConfirmedClickLimit;
    @SerializedName("total_value") private double mTotalValue;
    @SerializedName("carry_description") private String mCarryDescription;
    @SerializedName("carry_plan_name") private String mCarryPlanName;
    @SerializedName("date_field") private String mDateField;
    @SerializedName("uni_key") private String mUniKey;
    @SerializedName("status") private int mStatus;
    @SerializedName("status_display") private String mStatusDisplay;
    @SerializedName("today_carry") private double mTodayCarry;
    @SerializedName("modified") private String mModified;
    @SerializedName("created") private String mCreated;

    public int getMamaId() {
      return mMamaId;
    }

    public void setMamaId(int mamaId) {
      this.mMamaId = mamaId;
    }

    public int getClickNum() {
      return mClickNum;
    }

    public void setClickNum(int clickNum) {
      this.mClickNum = clickNum;
    }

    public int getInitOrderNum() {
      return mInitOrderNum;
    }

    public void setInitOrderNum(int initOrderNum) {
      this.mInitOrderNum = initOrderNum;
    }

    public double getInitClickPrice() {
      return mInitClickPrice;
    }

    public void setInitClickPrice(double initClickPrice) {
      this.mInitClickPrice = initClickPrice;
    }

    public int getInitClickLimit() {
      return mInitClickLimit;
    }

    public void setInitClickLimit(int initClickLimit) {
      this.mInitClickLimit = initClickLimit;
    }

    public int getConfirmedOrderNum() {
      return mConfirmedOrderNum;
    }

    public void setConfirmedOrderNum(int confirmedOrderNum) {
      this.mConfirmedOrderNum = confirmedOrderNum;
    }

    public double getConfirmedClickPrice() {
      return mConfirmedClickPrice;
    }

    public void setConfirmedClickPrice(double confirmedClickPrice) {
      this.mConfirmedClickPrice = confirmedClickPrice;
    }

    public int getConfirmedClickLimit() {
      return mConfirmedClickLimit;
    }

    public void setConfirmedClickLimit(int confirmedClickLimit) {
      this.mConfirmedClickLimit = confirmedClickLimit;
    }

    public double getTotalValue() {
      return mTotalValue;
    }

    public void setTotalValue(double totalValue) {
      this.mTotalValue = totalValue;
    }

    public String getCarryDescription() {
      return mCarryDescription;
    }

    public void setCarryDescription(String carryDescription) {
      this.mCarryDescription = carryDescription;
    }

    public String getCarryPlanName() {
      return mCarryPlanName;
    }

    public void setCarryPlanName(String carryPlanName) {
      this.mCarryPlanName = carryPlanName;
    }

    public String getDateField() {
      return mDateField;
    }

    public void setDateField(String dateField) {
      this.mDateField = dateField;
    }

    public String getUniKey() {
      return mUniKey;
    }

    public void setUniKey(String uniKey) {
      this.mUniKey = uniKey;
    }

    public int getStatus() {
      return mStatus;
    }

    public void setStatus(int status) {
      this.mStatus = status;
    }

    public String getStatusDisplay() {
      return mStatusDisplay;
    }

    public void setStatusDisplay(String statusDisplay) {
      this.mStatusDisplay = statusDisplay;
    }

    public double getTodayCarry() {
      return mTodayCarry;
    }

    public void setTodayCarry(double todayCarry) {
      this.mTodayCarry = todayCarry;
    }

    public String getModified() {
      return mModified;
    }

    public void setModified(String modified) {
      this.mModified = modified;
    }

    public String getCreated() {
      return mCreated;
    }

    public void setCreated(String created) {
      this.mCreated = created;
    }
  }
}
