package com.jimei.xiaolumeimei.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by itxuye on 2016/7/4.
 */
public class IsGetcoupon {

  /**
   * info : 没有领取
   * is_picked : 0
   * code : 0
   */

  @SerializedName("info") private String info;
  @SerializedName("is_picked") private int isPicked;
  @SerializedName("code") private int code;

  public String getInfo() {
    return info;
  }

  public void setInfo(String info) {
    this.info = info;
  }

  public int getIsPicked() {
    return isPicked;
  }

  public void setIsPicked(int isPicked) {
    this.isPicked = isPicked;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }
}
