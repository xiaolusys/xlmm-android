package com.jimei.xiaolumeimei.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by itxuye(www.itxuye.com) on 2016/02/17.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class WxLogininfoBean {


  /**
   * info : {"id":684121,"url":"http://m.xiaolumeimei.com/rest/v1/users/684121","user_id":"684274","username":"o29cQsz78N7PbVwESUT8jbuYxN-c","nick":"","mobile":"","email":"","phone":"","status":1,"created":"2015-12-23T12:11:48","modified":"2015-12-23T12:11:48","xiaolumm":null,"has_usable_password":true,"score":0}
   * code : 0
   * ｉs_login : true
   */

  @SerializedName("code") private int mCode;
  @SerializedName("is_login") private boolean mIsLogin;

  public void setCode(int code) {
    this.mCode = code;
  }

  public void setIsLogin(boolean ｉsLogin) {
    this.mIsLogin = ｉsLogin;
  }

  public int getCode() {
    return mCode;
  }

  public boolean isIsLogin() {
    return mIsLogin;
  }


}
