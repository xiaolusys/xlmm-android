package com.jimei.xiaolumeimei.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by itxuye(www.itxuye.com) on 2016/01/30.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class SmsLoginUserBean {

  /**
   * info : {"id":684126,"url":"http://api.xiaolumeimei.com/rest/v1/users/684126","user_id":"684279","username":"18021397781","nick":"77678","mobile":"18021397781","email":"","phone":"","status":1,"created":"2015-12-23T12:14:11","modified":"2016-01-20T14:56:30","xiaolumm":null,"has_usable_password":true,"score":0}
   * code : 0
   */

  @SerializedName("code") private int mCode;



  public void setCode(int code) {
    this.mCode = code;
  }



  public int getCode() {
    return mCode;
  }


}
