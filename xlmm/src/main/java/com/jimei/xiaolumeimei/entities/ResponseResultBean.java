package com.jimei.xiaolumeimei.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by itxuye(www.itxuye.com) on 2016/02/14.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class ResponseResultBean {

  /**
   * code : 0
   */

  @SerializedName("code") private int mCode;

  @SerializedName("msg") private String msg;

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public int getCode() {
    return mCode;
  }

  public void setCode(int code) {
    this.mCode = code;
  }
}
