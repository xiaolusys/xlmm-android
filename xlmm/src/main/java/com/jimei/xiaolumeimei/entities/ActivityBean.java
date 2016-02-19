package com.jimei.xiaolumeimei.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by itxuye(www.itxuye.com) on 2016/02/19.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class ActivityBean {

  /**
   * active_dec : 开年活动－开年有好礼，红包不停发，免费等你拿！
   * link_qrcode : /media/pmt/custm-684126-qq.jpg
   * title : 开年活动－红包不停发
   */

  @SerializedName("active_dec") private String mActiveDec;
  @SerializedName("link_qrcode") private String mLinkQrcode;
  @SerializedName("title") private String mTitle;

  public void setActiveDec(String activeDec) {
    this.mActiveDec = activeDec;
  }

  public void setLinkQrcode(String linkQrcode) {
    this.mLinkQrcode = linkQrcode;
  }

  public void setTitle(String title) {
    this.mTitle = title;
  }

  public String getActiveDec() {
    return mActiveDec;
  }

  public String getLinkQrcode() {
    return mLinkQrcode;
  }

  public String getTitle() {
    return mTitle;
  }
}
