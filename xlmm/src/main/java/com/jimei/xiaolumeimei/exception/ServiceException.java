package com.jimei.xiaolumeimei.exception;

/**
 * Created by itxuye(www.itxuye.com) on 15/12/29.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class ServiceException extends ConversionException {
  private int status;
  private String info;

  public int getStatus() {
    return status;
  }

  public String getInfo() {
    return info;
  }

  public ServiceException(int status, String info) {
    super("ServiceException status:"+status+"  info"+info);
    this.status = status;
    this.info = info;
  }

}

