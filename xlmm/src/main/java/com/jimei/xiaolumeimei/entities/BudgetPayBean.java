package com.jimei.xiaolumeimei.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by itxuye(www.itxuye.com) on 2016/03/05.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class BudgetPayBean {

  /**
   * channel : budget
   * success : true
   * id : xxxxxxx
   * info : u'小鹿钱包余额不足'
   */

  @SerializedName("channel") private String mChannel;
  @SerializedName("success") private boolean mSuccess;
  @SerializedName("id") private String mId;
  @SerializedName("info") private String mInfo;

  public void setChannel(String channel) {
    this.mChannel = channel;
  }

  public void setSuccess(boolean success) {
    this.mSuccess = success;
  }

  public void setId(String id) {
    this.mId = id;
  }

  public void setInfo(String info) {
    this.mInfo = info;
  }

  public String getChannel() {
    return mChannel;
  }

  public boolean isSuccess() {
    return mSuccess;
  }

  public String getId() {
    return mId;
  }

  public String getInfo() {
    return mInfo;
  }
}
