package com.jimei.xiaolumeimei.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by itxuye(www.itxuye.com) on 2016/01/18.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class AddressBean {

  /**
   * id : 96491
   * url : http://api.xiaolumeimei.com/rest/v1/address/96491
   * cus_uid : 684126
   * receiver_name : jsjshs
   * receiver_state : 天津
   * receiver_city : 天津市
   * receiver_district : 塘沽区
   * receiver_address : nxnsksk
   * receiver_zip :
   * receiver_mobile : 18021397782
   * receiver_phone :
   * default : true
   * status : normal
   * created : 2016-01-05T15:48:41
   */

  @SerializedName("id") private String mId;
  @SerializedName("url") private String mUrl;
  @SerializedName("cus_uid") private int mCusUid;
  @SerializedName("receiver_name") private String mReceiverName;
  @SerializedName("receiver_state") private String mReceiverState;
  @SerializedName("receiver_city") private String mReceiverCity;
  @SerializedName("receiver_district") private String mReceiverDistrict;
  @SerializedName("receiver_address") private String mReceiverAddress;
  @SerializedName("receiver_zip") private String mReceiverZip;
  @SerializedName("receiver_mobile") private String mReceiverMobile;
  @SerializedName("receiver_phone") private String mReceiverPhone;
  @SerializedName("default") private boolean mDefaultX;
  @SerializedName("status") private String mStatus;
  @SerializedName("created") private String mCreated;

  public void setId(String id) {
    this.mId = id;
  }

  public void setUrl(String url) {
    this.mUrl = url;
  }

  public void setCusUid(int cusUid) {
    this.mCusUid = cusUid;
  }

  public void setReceiverName(String receiverName) {
    this.mReceiverName = receiverName;
  }

  public void setReceiverState(String receiverState) {
    this.mReceiverState = receiverState;
  }

  public void setReceiverCity(String receiverCity) {
    this.mReceiverCity = receiverCity;
  }

  public void setReceiverDistrict(String receiverDistrict) {
    this.mReceiverDistrict = receiverDistrict;
  }

  public void setReceiverAddress(String receiverAddress) {
    this.mReceiverAddress = receiverAddress;
  }

  public void setReceiverZip(String receiverZip) {
    this.mReceiverZip = receiverZip;
  }

  public void setReceiverMobile(String receiverMobile) {
    this.mReceiverMobile = receiverMobile;
  }

  public void setReceiverPhone(String receiverPhone) {
    this.mReceiverPhone = receiverPhone;
  }

  public void setDefaultX(boolean defaultX) {
    this.mDefaultX = defaultX;
  }

  public void setStatus(String status) {
    this.mStatus = status;
  }

  public void setCreated(String created) {
    this.mCreated = created;
  }

  public String getId() {
    return mId;
  }

  public String getUrl() {
    return mUrl;
  }

  public int getCusUid() {
    return mCusUid;
  }

  public String getReceiverName() {
    return mReceiverName;
  }

  public String getReceiverState() {
    return mReceiverState;
  }

  public String getReceiverCity() {
    return mReceiverCity;
  }

  public String getReceiverDistrict() {
    return mReceiverDistrict;
  }

  public String getReceiverAddress() {
    return mReceiverAddress;
  }

  public String getReceiverZip() {
    return mReceiverZip;
  }

  public String getReceiverMobile() {
    return mReceiverMobile;
  }

  public String getReceiverPhone() {
    return mReceiverPhone;
  }

  public boolean isDefaultX() {
    return mDefaultX;
  }

  public String getStatus() {
    return mStatus;
  }

  public String getCreated() {
    return mCreated;
  }
}
