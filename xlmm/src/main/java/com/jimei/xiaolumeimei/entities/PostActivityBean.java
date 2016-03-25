package com.jimei.xiaolumeimei.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by itxuye(www.itxuye.com) on 2016/03/23.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class PostActivityBean {

  /**
   * id : 2
   * title : 3.21春夏美衣大赏优惠
   * login_required : true
   * act_desc :
   * act_img : http://image.xiaolu.so/post14541168439012%E5%8F%B7%E7%AB%A5.png?&imageMogr2/thumbnail/618x253/format/jpg/quality/90/
   * mask_link :
   * act_link : http://dev.xiaolumeimei.com/rest/v1/usercoupons
   * act_type : webview
   * act_applink :
   * start_time : 2016-03-22T17:36:50
   * end_time : 2016-03-24T17:36:53
   * order_val : 0
   * extras : {"template_id":"35,37"}
   */

  @SerializedName("id") private int mId;
  @SerializedName("title") private String mTitle;
  @SerializedName("login_required") private boolean mLoginRequired;
  @SerializedName("act_desc") private String mActDesc;
  @SerializedName("act_img") private String mActImg;
  @SerializedName("mask_link") private String mMaskLink;
  @SerializedName("act_link") private String mActLink;
  @SerializedName("act_type") private String mActType;
  @SerializedName("act_applink") private String mActApplink;
  @SerializedName("start_time") private String mStartTime;
  @SerializedName("end_time") private String mEndTime;
  @SerializedName("order_val") private int mOrderVal;
  /**
   * template_id : 35,37
   */

  @SerializedName("extras") private ExtrasEntity mExtras;

  public int getId() {
    return mId;
  }

  public void setId(int id) {
    mId = id;
  }

  public String getTitle() {
    return mTitle;
  }

  public void setTitle(String title) {
    mTitle = title;
  }

  public boolean isLoginRequired() {
    return mLoginRequired;
  }

  public void setLoginRequired(boolean loginRequired) {
    mLoginRequired = loginRequired;
  }

  public String getActDesc() {
    return mActDesc;
  }

  public void setActDesc(String actDesc) {
    mActDesc = actDesc;
  }

  public String getActImg() {
    return mActImg;
  }

  public void setActImg(String actImg) {
    mActImg = actImg;
  }

  public String getMaskLink() {
    return mMaskLink;
  }

  public void setMaskLink(String maskLink) {
    mMaskLink = maskLink;
  }

  public String getActLink() {
    return mActLink;
  }

  public void setActLink(String actLink) {
    mActLink = actLink;
  }

  public String getActType() {
    return mActType;
  }

  public void setActType(String actType) {
    mActType = actType;
  }

  public String getActApplink() {
    return mActApplink;
  }

  public void setActApplink(String actApplink) {
    mActApplink = actApplink;
  }

  public String getStartTime() {
    return mStartTime;
  }

  public void setStartTime(String startTime) {
    mStartTime = startTime;
  }

  public String getEndTime() {
    return mEndTime;
  }

  public void setEndTime(String endTime) {
    mEndTime = endTime;
  }

  public int getOrderVal() {
    return mOrderVal;
  }

  public void setOrderVal(int orderVal) {
    mOrderVal = orderVal;
  }

  public ExtrasEntity getExtras() {
    return mExtras;
  }

  public void setExtras(ExtrasEntity extras) {
    mExtras = extras;
  }

  public static class ExtrasEntity {
    @SerializedName("template_id") private String mTemplateId;

    public String getTemplateId() {
      return mTemplateId;
    }

    public void setTemplateId(String templateId) {
      mTemplateId = templateId;
    }
  }
}
