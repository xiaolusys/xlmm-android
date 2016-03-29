package com.jimei.xiaolumeimei.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by itxuye(www.itxuye.com) on 2016/03/28.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class PayInfoBean {

  /**
   * code : 0
   * info : ok
   * charge : aa
   */
  @SerializedName("channel") private String channel;
  @SerializedName("code") private int mCode;
  @SerializedName("info") private String mInfo;
  @SerializedName("charge") private Object mCharge;

  @Override public String toString() {
    return "PayInfoBean{" +
        "channel='" + channel + '\'' +
        ", mCode=" + mCode +
        ", mInfo='" + mInfo + '\'' +
        ", mCharge=" + mCharge +
        '}';
  }

  public String getChannel() {
    return channel;
  }

  public void setChannel(String channel) {
    this.channel = channel;
  }

  public int getCode() {
    return mCode;
  }

  public void setCode(int code) {
    mCode = code;
  }

  public String getInfo() {
    return mInfo;
  }

  public void setInfo(String info) {
    mInfo = info;
  }

  public Object getCharge() {
    return mCharge;
  }

  public void setCharge(Object charge) {
    mCharge = charge;
  }
}
