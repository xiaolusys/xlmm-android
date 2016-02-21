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
   * share_type : link
   * title : 开年活动－红包不停发
   * share_img : http://7xogkj.com2.z0.glb.qiniucdn.com/222-share-pyq2.png
   * qrcode_link : http://m.xiaolumeimei.com/sale/promotion/ercode/
   * link_qrcode : /media/pmt/custm-1-None.jpg
   * share_link : http://m.xiaolumeimei.com/sale/promotion/xlsampleapply/?from_customer=1&ufrom=None
   */

  @SerializedName("active_dec") private String activeDec;
  @SerializedName("share_type") private String shareType;
  @SerializedName("title") private String title;
  @SerializedName("share_img") private String shareImg;
  @SerializedName("qrcode_link") private String qrcodeLink;
  @SerializedName("link_qrcode") private String linkQrcode;
  @SerializedName("share_link") private String shareLink;

  public void setActiveDec(String activeDec) {
    this.activeDec = activeDec;
  }

  public void setShareType(String shareType) {
    this.shareType = shareType;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setShareImg(String shareImg) {
    this.shareImg = shareImg;
  }

  public void setQrcodeLink(String qrcodeLink) {
    this.qrcodeLink = qrcodeLink;
  }

  public void setLinkQrcode(String linkQrcode) {
    this.linkQrcode = linkQrcode;
  }

  public void setShareLink(String shareLink) {
    this.shareLink = shareLink;
  }

  public String getActiveDec() {
    return activeDec;
  }

  public String getShareType() {
    return shareType;
  }

  public String getTitle() {
    return title;
  }

  public String getShareImg() {
    return shareImg;
  }

  public String getQrcodeLink() {
    return qrcodeLink;
  }

  public String getLinkQrcode() {
    return linkQrcode;
  }

  public String getShareLink() {
    return shareLink;
  }
}
