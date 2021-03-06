package com.jimei.xiaolumeimei.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by itxuye(www.itxuye.com) on 2016/01/20.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class AddressResultBean {

  /**
   * ret : true
   */

  @SerializedName("ret") private boolean mRet;

  public int getCode() {
    return mCode;
  }

  @SerializedName("code") private int mCode;

  private String info;

  public String getInfo() {
    return info;
  }

  public void setInfo(String info) {
    this.info = info;
  }

  public void setRet(boolean ret) {
    this.mRet = ret;
  }

  public boolean isRet() {
    return mRet;
  }
}
