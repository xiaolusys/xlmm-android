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
   * count : 141
   * next : http://api.xiaolumeimei.com/rest/v1/pmt/carrylog?page=2
   * previous : null
   * results : [{"id":1,"carry_type":"out","xlmm":44,"value_money":0.01,"carry_type_name":"支出","log_type":"buy","carry_date":"2015-04-30","created":"2015-04-30T15:47:30"},{"id":2,"carry_type":"out","xlmm":44,"value_money":0.01,"carry_type_name":"支出","log_type":"buy","carry_date":"2015-04-30","created":"2015-04-30T15:50:05"},{"id":3,"carry_type":"out","xlmm":44,"value_money":0.01,"carry_type_name":"支出","log_type":"buy","carry_date":"2015-04-30","created":"2015-04-30T17:05:27"},{"id":5,"carry_type":"out","xlmm":44,"value_money":0.01,"carry_type_name":"支出","log_type":"buy","carry_date":"2015-05-04","created":"2015-05-04T15:52:23"},{"id":12519,"carry_type":"in","xlmm":44,"value_money":0,"carry_type_name":"收入","log_type":"click","carry_date":"2015-05-15","created":"2015-05-16T09:38:32"},{"id":31582,"carry_type":"in","xlmm":44,"value_money":0,"carry_type_name":"收入","log_type":"click","carry_date":"2015-05-21","created":"2015-05-22T03:31:58"},{"id":33109,"carry_type":"in","xlmm":44,"value_money":0,"carry_type_name":"收入","log_type":"click","carry_date":"2015-05-22","created":"2015-05-23T03:32:07"},{"id":34721,"carry_type":"in","xlmm":44,"value_money":0,"carry_type_name":"收入","log_type":"click","carry_date":"2015-05-23","created":"2015-05-24T03:32:16"},{"id":37683,"carry_type":"in","xlmm":44,"value_money":0,"carry_type_name":"收入","log_type":"click","carry_date":"2015-05-25","created":"2015-05-26T03:32:24"},{"id":39524,"carry_type":"in","xlmm":44,"value_money":0,"carry_type_name":"收入","log_type":"click","carry_date":"2015-05-26","created":"2015-05-27T03:32:39"}]
   */

  @SerializedName("count") private int mCount;
  @SerializedName("next") private String mNext;
  @SerializedName("previous") private Object mPrevious;
  /**
   * id : 1
   * carry_type : out
   * xlmm : 44
   * value_money : 0.01
   * carry_type_name : 支出
   * log_type : buy
   * carry_date : 2015-04-30
   * created : 2015-04-30T15:47:30
   * "dayly_in_amount": 0.0,
   "dayly_clk_amount": 0.0
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

    public void setDaylyClkAmount(double daylyClkAmount ) {
      this.mDaylyClkAmount = daylyClkAmount;
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

    public double getDaylyInAmount() {return mDaylyInAmount;}

    public double getDaylyClkAmount() {return mDaylyClkAmount;}
  }
}
