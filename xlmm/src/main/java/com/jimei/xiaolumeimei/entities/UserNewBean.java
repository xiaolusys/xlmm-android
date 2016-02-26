package com.jimei.xiaolumeimei.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by itxuye(www.itxuye.com) on 2016/02/26.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class UserNewBean {

  /**
   * id : 29
   * url : http://dev.xiaolumeimei.com/rest/v1/users/29
   * user_id : 115
   * username : chongyang.chen
   * nick : 笑嘻嘻
   * mobile : 13816404857
   * email :
   * phone : 1
   * thumbnail :
   * status : 1
   * created : 2015-08-20T15:31:45
   * modified : 2016-01-31T12:53:06
   * xiaolumm : {"id":2,"cash":10000,"agencylevel":2,"created":"2015-05-12T17:03:25","status":"effect"}
   * has_usable_password : true
   * user_budget : {"budget_cash":10}
   * score : 150
   */

  @SerializedName("id") private int mId;
  @SerializedName("url") private String mUrl;
  @SerializedName("user_id") private String mUserId;
  @SerializedName("username") private String mUsername;
  @SerializedName("nick") private String mNick;
  @SerializedName("mobile") private String mMobile;
  @SerializedName("email") private String mEmail;
  @SerializedName("phone") private String mPhone;
  @SerializedName("thumbnail") private String mThumbnail;
  @SerializedName("status") private int mStatus;
  @SerializedName("created") private String mCreated;
  @SerializedName("modified") private String mModified;
  /**
   * id : 2
   * cash : 10000
   * agencylevel : 2
   * created : 2015-05-12T17:03:25
   * status : effect
   */

  @SerializedName("xiaolumm") private XiaolummEntity mXiaolumm;
  @SerializedName("has_usable_password") private boolean mHasUsablePassword;
  /**
   * budget_cash : 10.0
   */

  @SerializedName("user_budget") private UserBudgetEntity mUserBudget;
  @SerializedName("score") private int mScore;

  public void setId(int id) {
    this.mId = id;
  }

  public void setUrl(String url) {
    this.mUrl = url;
  }

  public void setUserId(String userId) {
    this.mUserId = userId;
  }

  public void setUsername(String username) {
    this.mUsername = username;
  }

  public void setNick(String nick) {
    this.mNick = nick;
  }

  public void setMobile(String mobile) {
    this.mMobile = mobile;
  }

  public void setEmail(String email) {
    this.mEmail = email;
  }

  public void setPhone(String phone) {
    this.mPhone = phone;
  }

  public void setThumbnail(String thumbnail) {
    this.mThumbnail = thumbnail;
  }

  public void setStatus(int status) {
    this.mStatus = status;
  }

  public void setCreated(String created) {
    this.mCreated = created;
  }

  public void setModified(String modified) {
    this.mModified = modified;
  }

  public void setXiaolumm(XiaolummEntity xiaolumm) {
    this.mXiaolumm = xiaolumm;
  }

  public void setHasUsablePassword(boolean hasUsablePassword) {
    this.mHasUsablePassword = hasUsablePassword;
  }

  public void setUserBudget(UserBudgetEntity userBudget) {
    this.mUserBudget = userBudget;
  }

  public void setScore(int score) {
    this.mScore = score;
  }

  public int getId() {
    return mId;
  }

  public String getUrl() {
    return mUrl;
  }

  public String getUserId() {
    return mUserId;
  }

  public String getUsername() {
    return mUsername;
  }

  public String getNick() {
    return mNick;
  }

  public String getMobile() {
    return mMobile;
  }

  public String getEmail() {
    return mEmail;
  }

  public String getPhone() {
    return mPhone;
  }

  public String getThumbnail() {
    return mThumbnail;
  }

  public int getStatus() {
    return mStatus;
  }

  public String getCreated() {
    return mCreated;
  }

  public String getModified() {
    return mModified;
  }

  public XiaolummEntity getXiaolumm() {
    return mXiaolumm;
  }

  public boolean isHasUsablePassword() {
    return mHasUsablePassword;
  }

  public UserBudgetEntity getUserBudget() {
    return mUserBudget;
  }

  public int getScore() {
    return mScore;
  }

  public static class XiaolummEntity {
    @SerializedName("id") private int mId;
    @SerializedName("cash") private int mCash;
    @SerializedName("agencylevel") private int mAgencylevel;
    @SerializedName("created") private String mCreated;
    @SerializedName("status") private String mStatus;

    public void setId(int id) {
      this.mId = id;
    }

    public void setCash(int cash) {
      this.mCash = cash;
    }

    public void setAgencylevel(int agencylevel) {
      this.mAgencylevel = agencylevel;
    }

    public void setCreated(String created) {
      this.mCreated = created;
    }

    public void setStatus(String status) {
      this.mStatus = status;
    }

    public int getId() {
      return mId;
    }

    public int getCash() {
      return mCash;
    }

    public int getAgencylevel() {
      return mAgencylevel;
    }

    public String getCreated() {
      return mCreated;
    }

    public String getStatus() {
      return mStatus;
    }
  }

  public static class UserBudgetEntity {
    @SerializedName("budget_cash") private double mBudgetCash;

    public void setBudgetCash(double budgetCash) {
      this.mBudgetCash = budgetCash;
    }

    public double getBudgetCash() {
      return mBudgetCash;
    }
  }
}
