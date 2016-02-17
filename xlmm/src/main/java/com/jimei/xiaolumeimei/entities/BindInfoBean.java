package com.jimei.xiaolumeimei.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by itxuye(www.itxuye.com) on 2016/02/17.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class BindInfoBean {

  /**
   * code : 1
   * result : 1
   * info : 手机已经绑定
   */

  @SerializedName("code") private int mCode;
  @SerializedName("result") private String mResult;
  @SerializedName("info") private String mInfo;

  public void setCode(int code) {
    this.mCode = code;
  }

  public void setResult(String result) {
    this.mResult = result;
  }

  public void setInfo(String info) {
    this.mInfo = info;
  }

  public int getCode() {
    return mCode;
  }

  public String getResult() {
    return mResult;
  }

  public String getInfo() {
    return mInfo;
  }
}
