package com.jimei.xiaolumeimei.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by itxuye(www.itxuye.com) on 2016/01/20.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class LogOutBean {

  /**
   * code : 0
   * result : logout
   */

  @SerializedName("code") private int mCode;
  @SerializedName("result") private String mResult;

  public void setCode(int code) {
    this.mCode = code;
  }

  public void setResult(String result) {
    this.mResult = result;
  }

  public int getCode() {
    return mCode;
  }

  public String getResult() {
    return mResult;
  }
}
