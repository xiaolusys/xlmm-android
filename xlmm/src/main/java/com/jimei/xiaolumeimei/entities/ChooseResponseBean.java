package com.jimei.xiaolumeimei.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by itxuye(www.itxuye.com) on 2016/02/14.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class ChooseResponseBean {

  /**
   * code : 0
   */

  @SerializedName("code") private int mCode;

  public int getCode() {
    return mCode;
  }

  public void setCode(int code) {
    this.mCode = code;
  }
}
