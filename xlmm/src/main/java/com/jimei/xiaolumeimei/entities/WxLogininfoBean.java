package com.jimei.xiaolumeimei.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by itxuye(www.itxuye.com) on 2016/02/17.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class WxLogininfoBean {

  /**
   * id : 684121
   * url : http://m.xiaolumeimei.com/rest/v1/users/684121
   * user_id : 684274
   * username : o29cQsz78N7PbVwESUT8jbuYxN-c
   * nick :
   * mobile :
   * email :
   * phone :
   * status : 1
   * created : 2015-12-23T12:11:48
   * modified : 2015-12-23T12:11:48
   * xiaolumm : null
   * has_usable_password : true
   * score : 0
   */

  @SerializedName("info") private InfoEntity mInfo;
  /**
   * info : {"id":684121,"url":"http://m.xiaolumeimei.com/rest/v1/users/684121","user_id":"684274","username":"o29cQsz78N7PbVwESUT8jbuYxN-c","nick":"","mobile":"","email":"","phone":"","status":1,"created":"2015-12-23T12:11:48","modified":"2015-12-23T12:11:48","xiaolumm":null,"has_usable_password":true,"score":0}
   * code : 0
   * ｉs_login : true
   */

  @SerializedName("code") private int mCode;
  @SerializedName("ｉs_login") private boolean mＩsLogin;

  public void setInfo(InfoEntity info) {
    this.mInfo = info;
  }

  public void setCode(int code) {
    this.mCode = code;
  }

  public void setＩsLogin(boolean ｉsLogin) {
    this.mＩsLogin = ｉsLogin;
  }

  public InfoEntity getInfo() {
    return mInfo;
  }

  public int getCode() {
    return mCode;
  }

  public boolean isＩsLogin() {
    return mＩsLogin;
  }

  public static class InfoEntity {
    @SerializedName("id") private int mId;
    @SerializedName("url") private String mUrl;
    @SerializedName("user_id") private String mUserId;
    @SerializedName("username") private String mUsername;
    @SerializedName("nick") private String mNick;
    @SerializedName("mobile") private String mMobile;
    @SerializedName("email") private String mEmail;
    @SerializedName("phone") private String mPhone;
    @SerializedName("status") private int mStatus;
    @SerializedName("created") private String mCreated;
    @SerializedName("modified") private String mModified;
    @SerializedName("xiaolumm") private Object mXiaolumm;
    @SerializedName("has_usable_password") private boolean mHasUsablePassword;
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

    public void setStatus(int status) {
      this.mStatus = status;
    }

    public void setCreated(String created) {
      this.mCreated = created;
    }

    public void setModified(String modified) {
      this.mModified = modified;
    }

    public void setXiaolumm(Object xiaolumm) {
      this.mXiaolumm = xiaolumm;
    }

    public void setHasUsablePassword(boolean hasUsablePassword) {
      this.mHasUsablePassword = hasUsablePassword;
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

    public int getStatus() {
      return mStatus;
    }

    public String getCreated() {
      return mCreated;
    }

    public String getModified() {
      return mModified;
    }

    public Object getXiaolumm() {
      return mXiaolumm;
    }

    public boolean isHasUsablePassword() {
      return mHasUsablePassword;
    }

    public int getScore() {
      return mScore;
    }
  }
}
