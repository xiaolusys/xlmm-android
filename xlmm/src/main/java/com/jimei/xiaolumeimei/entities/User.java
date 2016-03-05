package com.jimei.xiaolumeimei.entities;

/**
 * Created by 优尼世界 on 15/12/29.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class User {
  private String name;
  private String password;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override public String toString() {
    return "User{" +
        "name='" + name + '\'' +
        ", password='" + password + '\'' +
        '}';
  }
}
