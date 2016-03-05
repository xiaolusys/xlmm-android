package com.jimei.xiaolumeimei.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by itxuye(www.itxuye.com) on 2016/01/28.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class CartsNumResultBean {

  /**
   * result : 5
   * last_created : 1.453970346E9
   */

  @SerializedName("result") private int mResult;
  @SerializedName("last_created") private double mLastCreated;

  public void setResult(int result) {
    this.mResult = result;
  }

  public void setLastCreated(double lastCreated) {
    this.mLastCreated = lastCreated;
  }

  public int getResult() {
    return mResult;
  }

  public double getLastCreated() {
    return mLastCreated;
  }
}
