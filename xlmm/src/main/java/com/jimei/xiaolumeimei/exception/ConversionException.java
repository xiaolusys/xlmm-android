package com.jimei.xiaolumeimei.exception;

/**
 * Created by itxuye(www.itxuye.com) on 15/12/29.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class ConversionException extends Exception {
  public ConversionException(String message) {
    super(message);
  }

  public ConversionException(String message, Throwable throwable) {
    super(message, throwable);
  }

  public ConversionException(Throwable throwable) {
    super(throwable);
  }
}
