package com.jimei.xiaolumeimei.entities;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by wulei on 3/11/16.
 */
public class MamaLivenessBean {

  /**
   * count : 16
   * next : http://api.xiaolumeimei.com/rest/v2/mama/activevalue?page=2
   * previous : null
   * results : [{"mama_id":44,"value_num":248,"value_type":1,"value_type_name":"点击","uni_key":"active-44-1-2016-03-22","value_description":"点击活跃值
   * +1","date_field":"2016-03-22","status":1,"status_display":"待确定","today_carry":248,"modified":"2016-03-22T00:00:39","created":"2016-03-22T00:00:39"},{"mama_id":44,"value_num":2050,"value_type":1,"value_type_name":"点击","uni_key":"active-44-1-2016-03-21","value_description":"点击活跃值
   * +1","date_field":"2016-03-21","status":1,"status_display":"待确定","today_carry":2050,"modified":"2016-03-21T00:07:52","created":"2016-03-21T00:07:52"},{"mama_id":44,"value_num":299,"value_type":1,"value_type_name":"点击","uni_key":"active-44-1-2016-03-20","value_description":"点击活跃值
   * +1","date_field":"2016-03-20","status":1,"status_display":"待确定","today_carry":299,"modified":"2016-03-22T00:00:39","created":"2016-03-20T00:00:51"},{"mama_id":44,"value_num":279,"value_type":1,"value_type_name":"点击","uni_key":"active-44-1-2016-03-19","value_description":"点击活跃值
   * +1","date_field":"2016-03-19","status":1,"status_display":"待确定","today_carry":279,"modified":"2016-03-19T00:02:28","created":"2016-03-19T00:02:28"},{"mama_id":44,"value_num":292,"value_type":1,"value_type_name":"点击","uni_key":"active-44-1-2016-03-18","value_description":"点击活跃值
   * +1","date_field":"2016-03-18","status":1,"status_display":"待确定","today_carry":292,"modified":"2016-03-18T00:15:03","created":"2016-03-18T00:15:03"},{"mama_id":44,"value_num":262,"value_type":1,"value_type_name":"点击","uni_key":"active-44-1-2016-03-17","value_description":"点击活跃值
   * +1","date_field":"2016-03-17","status":1,"status_display":"待确定","today_carry":262,"modified":"2016-03-17T00:02:47","created":"2016-03-17T00:02:47"},{"mama_id":44,"value_num":264,"value_type":1,"value_type_name":"点击","uni_key":"active-44-1-2016-03-16","value_description":"点击活跃值
   * +1","date_field":"2016-03-16","status":1,"status_display":"待确定","today_carry":264,"modified":"2016-03-16T00:30:50","created":"2016-03-16T00:30:50"},{"mama_id":44,"value_num":10,"value_type":2,"value_type_name":"订单","uni_key":"active-44-2-2016-02-29-xo16022956d3f6c52bd3a","value_description":"订单活跃值
   * +10","date_field":"2016-02-29","status":2,"status_display":"已确定","today_carry":90,"modified":"2016-03-17T06:02:43","created":"2016-03-17T06:02:43"},{"mama_id":44,"value_num":10,"value_type":2,"value_type_name":"订单","uni_key":"active-44-2-2016-02-29-xo16022956d3d554322dd","value_description":"订单活跃值
   * +10","date_field":"2016-02-29","status":2,"status_display":"已确定","today_carry":90,"modified":"2016-03-17T06:02:41","created":"2016-03-17T06:02:41"},{"mama_id":44,"value_num":10,"value_type":2,"value_type_name":"订单","uni_key":"active-44-2-2016-02-29-xo16022956d3c88def131","value_description":"订单活跃值
   * +10","date_field":"2016-02-29","status":2,"status_display":"已确定","today_carry":90,"modified":"2016-03-17T06:02:39","created":"2016-03-17T06:02:39"}]
   */

  @SerializedName("count") private int mCount;
  @SerializedName("next") private String mNext;
  @SerializedName("previous") private Object mPrevious;
  /**
   * mama_id : 44
   * value_num : 248
   * value_type : 1
   * value_type_name : 点击
   * uni_key : active-44-1-2016-03-22
   * value_description : 点击活跃值 +1
   * date_field : 2016-03-22
   * status : 1
   * status_display : 待确定
   * today_carry : 248
   * modified : 2016-03-22T00:00:39
   * created : 2016-03-22T00:00:39
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

  public Object getPrevious() {
    return mPrevious;
  }

  public void setPrevious(Object previous) {
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
    @SerializedName("value_num") private int mValueNum;
    @SerializedName("value_type") private int mValueType;
    @SerializedName("value_type_name") private String mValueTypeName;
    @SerializedName("uni_key") private String mUniKey;
    @SerializedName("value_description") private String mValueDescription;
    @SerializedName("date_field") private String mDateField;
    @SerializedName("status") private int mStatus;
    @SerializedName("status_display") private String mStatusDisplay;
    @SerializedName("today_carry") private int mTodayCarry;
    @SerializedName("modified") private String mModified;
    @SerializedName("created") private String mCreated;

    public int getMamaId() {
      return mMamaId;
    }

    public void setMamaId(int mamaId) {
      mMamaId = mamaId;
    }

    public int getValueNum() {
      return mValueNum;
    }

    public void setValueNum(int valueNum) {
      mValueNum = valueNum;
    }

    public int getValueType() {
      return mValueType;
    }

    public void setValueType(int valueType) {
      mValueType = valueType;
    }

    public String getValueTypeName() {
      return mValueTypeName;
    }

    public void setValueTypeName(String valueTypeName) {
      mValueTypeName = valueTypeName;
    }

    public String getUniKey() {
      return mUniKey;
    }

    public void setUniKey(String uniKey) {
      mUniKey = uniKey;
    }

    public String getValueDescription() {
      return mValueDescription;
    }

    public void setValueDescription(String valueDescription) {
      mValueDescription = valueDescription;
    }

    public String getDateField() {
      return mDateField;
    }

    public void setDateField(String dateField) {
      mDateField = dateField;
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

    public int getTodayCarry() {
      return mTodayCarry;
    }

    public void setTodayCarry(int todayCarry) {
      mTodayCarry = todayCarry;
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
