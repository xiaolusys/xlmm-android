package com.jimei.xiaolumeimei.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by itxuye(www.itxuye.com) on 2016/01/30.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class SmsLoginBean {

  /**
   * info : 验证码已发送
   * code : 0
   */

  @SerializedName("info") private String mInfo;
  @SerializedName("code") private int mCode;

  public void setInfo(String info) {
    this.mInfo = info;
  }

  public void setCode(int code) {
    this.mCode = code;
  }

  public String getInfo() {
    return mInfo;
  }

  public int getCode() {
    return mCode;
  }
}
