package com.jimei.xiaolumeimei.event;

import com.jimei.xiaolumeimei.entities.UserInfoBean;

/**
 * Created by itxuye on 2016/7/5.
 */
public class UserEvent {
  public UserInfoBean getUserInfoBean() {
    return userInfoBean;
  }

  public void setUserInfoBean(UserInfoBean userInfoBean) {
    this.userInfoBean = userInfoBean;
  }

  public  UserInfoBean userInfoBean;

  public UserEvent(UserInfoBean userInfoBean) {
    this.userInfoBean = userInfoBean;
  }
}
