package com.jimei.xiaolumeimei.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 优尼世界 on 2016/01/14.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class AddCartsBean {

  /**
   * result : 1
   */

  @SerializedName("result") private String mResult;

  public String getResult() {
    return mResult;
  }

  public void setResult(String result) {
    this.mResult = result;
  }
}
