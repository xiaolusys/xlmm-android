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

  @SerializedName("code") private int mCode;
  @SerializedName("info") private String mInfo;
  @SerializedName("charge") private String mCharge;

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

  public String getCharge() {
    return mCharge;
  }

  public void setCharge(String charge) {
    mCharge = charge;
  }

  @Override public String toString() {
    return "PayInfoBean{" +
        "mCode=" + mCode +
        ", mInfo='" + mInfo + '\'' +
        ", mCharge='" + mCharge + '\'' +
        '}';
  }
}
