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
   * count : 119
   * next : http://api.xiaolumeimei.com/rest/v1/pmt/carrylog?page=2
   * previous : null
   * results : [{"id":1186694,"carry_type":"in","xlmm":44,"value_money":2217.75,"carry_type_name":"收入","log_type":"thousand","carry_date":"2016-03-01","created":"2016-03-01T04:00:01","dayly_in_amount":2217.75,"dayly_clk_amount":0,"desc":"待确认","get_log_type_display":"千元提成"},{"id":1186325,"carry_type":"in","xlmm":44,"value_money":510.67,"carry_type_name":"收入","log_type":"rebeta","carry_date":"2016-02-29","created":"2016-03-01T00:44:00","dayly_in_amount":510.67,"dayly_clk_amount":0,"desc":"哇！好厉害，今天又有205个订单,收益待确认","get_log_type_display":"订单返利"},{"id":1183383,"carry_type":"in","xlmm":44,"value_money":297.4,"carry_type_name":"收入","log_type":"rebeta","carry_date":"2016-02-28","created":"2016-02-29T00:41:36","dayly_in_amount":297.4,"dayly_clk_amount":0,"desc":"哇！好厉害，今天又有29个订单,收益待确认","get_log_type_display":"订单返利"},{"id":1180161,"carry_type":"in","xlmm":44,"value_money":299.52,"carry_type_name":"收入","log_type":"rebeta","carry_date":"2016-02-27","created":"2016-02-28T00:44:11","dayly_in_amount":299.52,"dayly_clk_amount":0,"desc":"哇！好厉害，今天又有363个订单,收益待确认","get_log_type_display":"订单返利"},{"id":1176604,"carry_type":"in","xlmm":44,"value_money":302.42,"carry_type_name":"收入","log_type":"rebeta","carry_date":"2016-02-26","created":"2016-02-27T01:14:28","dayly_in_amount":302.42,"dayly_clk_amount":0,"desc":"哇！好厉害，今天又有211个订单,收益待确认","get_log_type_display":"订单返利"},{"id":1172011,"carry_type":"in","xlmm":44,"value_money":334.74,"carry_type_name":"收入","log_type":"rebeta","carry_date":"2016-02-25","created":"2016-02-26T00:16:25","dayly_in_amount":334.74,"dayly_clk_amount":0,"desc":"大王，今天有来了537订单,收益待确认","get_log_type_display":"订单返利"},{"id":1172248,"carry_type":"in","xlmm":44,"value_money":212.64,"carry_type_name":"收入","log_type":"rebeta","carry_date":"2016-02-24","created":"2016-02-26T00:28:17","dayly_in_amount":212.64,"dayly_clk_amount":0,"desc":"哇！好厉害，今天又有228个订单,收益待确认","get_log_type_display":"订单返利"},{"id":1165980,"carry_type":"in","xlmm":44,"value_money":472.22,"carry_type_name":"收入","log_type":"rebeta","carry_date":"2016-02-23","created":"2016-02-24T00:43:43","dayly_in_amount":472.22,"dayly_clk_amount":0,"desc":"哇！好厉害，今天又有105个订单,收益待确认","get_log_type_display":"订单返利"},{"id":1162791,"carry_type":"in","xlmm":44,"value_money":776.19,"carry_type_name":"收入","log_type":"rebeta","carry_date":"2016-02-22","created":"2016-02-23T00:45:23","dayly_in_amount":776.19,"dayly_clk_amount":0,"desc":"哇！好厉害，今天又有121个订单,收益待确认","get_log_type_display":"订单返利"},{"id":1160189,"carry_type":"in","xlmm":44,"value_money":20.35,"carry_type_name":"收入","log_type":"rebeta","carry_date":"2016-02-21","created":"2016-02-22T00:41:12","dayly_in_amount":20.35,"dayly_clk_amount":0,"desc":"哇！好厉害，今天又有5个订单,收益待确认","get_log_type_display":"订单返利"}]
   */

  @SerializedName("count") private int mCount;
  @SerializedName("next") private String mNext;
  @SerializedName("previous") private Object mPrevious;
  /**
   * id : 1186694
   * carry_type : in
   * xlmm : 44
   * value_money : 2217.75
   * carry_type_name : 收入
   * log_type : thousand
   * carry_date : 2016-03-01
   * created : 2016-03-01T04:00:01
   * dayly_in_amount : 2217.75
   * dayly_clk_amount : 0.0
   * desc : 待确认
   * get_log_type_display : 千元提成
   */

  @SerializedName("results") private List<ResultsEntity> mResults;

  public void setCount(int count) {
    this.mCount = count;
  }

  public void setNext(String next) {
    this.mNext = next;
  }

  public void setPrevious(Object previous) {
    this.mPrevious = previous;
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

  public Object getPrevious() {
    return mPrevious;
  }

  public List<ResultsEntity> getResults() {
    return mResults;
  }

  public static class ResultsEntity {
    @SerializedName("id") private int mId;
    @SerializedName("carry_type") private String mCarryType;
    @SerializedName("xlmm") private int mXlmm;
    @SerializedName("value_money") private double mValueMoney;
    @SerializedName("carry_type_name") private String mCarryTypeName;
    @SerializedName("log_type") private String mLogType;
    @SerializedName("carry_date") private String mCarryDate;
    @SerializedName("created") private String mCreated;
    @SerializedName("dayly_in_amount") private double mDaylyInAmount;
    @SerializedName("dayly_clk_amount") private double mDaylyClkAmount;
    @SerializedName("desc") private String mDesc;
    @SerializedName("get_log_type_display") private String mGetLogTypeDisplay;

    public void setId(int id) {
      this.mId = id;
    }

    public void setCarryType(String carryType) {
      this.mCarryType = carryType;
    }

    public void setXlmm(int xlmm) {
      this.mXlmm = xlmm;
    }

    public void setValueMoney(double valueMoney) {
      this.mValueMoney = valueMoney;
    }

    public void setCarryTypeName(String carryTypeName) {
      this.mCarryTypeName = carryTypeName;
    }

    public void setLogType(String logType) {
      this.mLogType = logType;
    }

    public void setCarryDate(String carryDate) {
      this.mCarryDate = carryDate;
    }

    public void setCreated(String created) {
      this.mCreated = created;
    }

    public void setDaylyInAmount(double daylyInAmount) {
      this.mDaylyInAmount = daylyInAmount;
    }

    public void setDaylyClkAmount(double daylyClkAmount) {
      this.mDaylyClkAmount = daylyClkAmount;
    }

    public void setDesc(String desc) {
      this.mDesc = desc;
    }

    public void setGetLogTypeDisplay(String getLogTypeDisplay) {
      this.mGetLogTypeDisplay = getLogTypeDisplay;
    }

    public int getId() {
      return mId;
    }

    public String getCarryType() {
      return mCarryType;
    }

    public int getXlmm() {
      return mXlmm;
    }

    public double getValueMoney() {
      return mValueMoney;
    }

    public String getCarryTypeName() {
      return mCarryTypeName;
    }

    public String getLogType() {
      return mLogType;
    }

    public String getCarryDate() {
      return mCarryDate;
    }

    public String getCreated() {
      return mCreated;
    }

    public double getDaylyInAmount() {
      return mDaylyInAmount;
    }

    public double getDaylyClkAmount() {
      return mDaylyClkAmount;
    }

    public String getDesc() {
      return mDesc;
    }

    public String getGetLogTypeDisplay() {
      return mGetLogTypeDisplay;
    }
  }
}
