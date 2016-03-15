package com.jimei.xiaolumeimei.entities;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by itxuye(www.itxuye.com) on 2016/03/14.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class OderCarryBean {

  /**
   * count : 2
   * next :
   * previous :
   * results : [{"mama_id":5,"order_id":"xo16031356e575b2aa9a2","order_value":100,"carry_num":0,"carry_type":2,"carry_type_name":"App粉丝订单","sku_name":"测试商品","sku_img":"","contributor_nick":"笑嘻嘻","contributor_img":"
   * ","contributor_id":29,"agency_level":1,"carry_plan_name":"代理佣金初始计划","date_field":"2016-03-13","status":1,"status_display":"待确定","modified":"2016-03-14T12:41:15","created":"2016-03-13T22:23:44","today_carry":0},{"mama_id":5,"order_id":"xo16022956d3ed73aa987","order_value":156,"carry_num":0,"carry_type":2,"carry_type_name":"App粉丝订单","sku_name":"秒杀
   * 绅士蝴蝶结拼接衬衫/黑色","sku_img":"https://mmbiz.qlogo.cn/mmbiz/yMhOQPTKhLsyAicOriaIXNVf9EOd1QDQQV7AgIjWQq7E6wib5gBF1E7cIMZbHjiaecFjXVCs5sWIvSGIPu6CyDc7Yw/0?wx_fmt=png","contributor_nick":"笑嘻嘻","contributor_img":"
   * ","contributor_id":29,"agency_level":1,"carry_plan_name":"代理佣金初始计划","date_field":"2016-02-29","status":1,"status_display":"待确定","modified":"2016-03-13T22:03:36","created":"2016-03-12T13:54:12","today_carry":0}]
   */

  @SerializedName("count") private int mCount;
  @SerializedName("next") private String mNext;
  @SerializedName("previous") private String mPrevious;
  /**
   * mama_id : 5
   * order_id : xo16031356e575b2aa9a2
   * order_value : 100.0
   * carry_num : 0.0
   * carry_type : 2
   * carry_type_name : App粉丝订单
   * sku_name : 测试商品
   * sku_img :
   * contributor_nick : 笑嘻嘻
   * contributor_img :
   * contributor_id : 29
   * agency_level : 1
   * carry_plan_name : 代理佣金初始计划
   * date_field : 2016-03-13
   * status : 1
   * status_display : 待确定
   * modified : 2016-03-14T12:41:15
   * created : 2016-03-13T22:23:44
   * today_carry : 0.0
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
    @SerializedName("order_id") private String mOrderId;
    @SerializedName("order_value") private double mOrderValue;
    @SerializedName("carry_num") private double mCarryNum;
    @SerializedName("carry_type") private int mCarryType;
    @SerializedName("carry_type_name") private String mCarryTypeName;
    @SerializedName("sku_name") private String mSkuName;
    @SerializedName("sku_img") private String mSkuImg;
    @SerializedName("contributor_nick") private String mContributorNick;
    @SerializedName("contributor_img") private String mContributorImg;
    @SerializedName("carry_description") private String mCarryDescription;
    @SerializedName("contributor_id") private int mContributorId;
    @SerializedName("agency_level") private int mAgencyLevel;
    @SerializedName("carry_plan_name") private String mCarryPlanName;
    @SerializedName("date_field") private String mDateField;
    @SerializedName("status") private int mStatus;
    @SerializedName("status_display") private String mStatusDisplay;
    @SerializedName("modified") private String mModified;
    @SerializedName("created") private String mCreated;
    @SerializedName("today_carry") private double mTodayCarry;

    public String getmCarryDescription() {
      return mCarryDescription;
    }

    public void setmCarryDescription(String mCarryDescription) {
      this.mCarryDescription = mCarryDescription;
    }

    public int getMamaId() {
      return mMamaId;
    }

    public void setMamaId(int mamaId) {
      this.mMamaId = mamaId;
    }

    public String getOrderId() {
      return mOrderId;
    }

    public void setOrderId(String orderId) {
      this.mOrderId = orderId;
    }

    public double getOrderValue() {
      return mOrderValue;
    }

    public void setOrderValue(double orderValue) {
      this.mOrderValue = orderValue;
    }

    public double getCarryNum() {
      return mCarryNum;
    }

    public void setCarryNum(double carryNum) {
      this.mCarryNum = carryNum;
    }

    public int getCarryType() {
      return mCarryType;
    }

    public void setCarryType(int carryType) {
      this.mCarryType = carryType;
    }

    public String getCarryTypeName() {
      return mCarryTypeName;
    }

    public void setCarryTypeName(String carryTypeName) {
      this.mCarryTypeName = carryTypeName;
    }

    public String getSkuName() {
      return mSkuName;
    }

    public void setSkuName(String skuName) {
      this.mSkuName = skuName;
    }

    public String getSkuImg() {
      return mSkuImg;
    }

    public void setSkuImg(String skuImg) {
      this.mSkuImg = skuImg;
    }

    public String getContributorNick() {
      return mContributorNick;
    }

    public void setContributorNick(String contributorNick) {
      this.mContributorNick = contributorNick;
    }

    public String getContributorImg() {
      return mContributorImg;
    }

    public void setContributorImg(String contributorImg) {
      this.mContributorImg = contributorImg;
    }

    public int getContributorId() {
      return mContributorId;
    }

    public void setContributorId(int contributorId) {
      this.mContributorId = contributorId;
    }

    public int getAgencyLevel() {
      return mAgencyLevel;
    }

    public void setAgencyLevel(int agencyLevel) {
      this.mAgencyLevel = agencyLevel;
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

    public double getTodayCarry() {
      return mTodayCarry;
    }

    public void setTodayCarry(double todayCarry) {
      this.mTodayCarry = todayCarry;
    }
  }
}
