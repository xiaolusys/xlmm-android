package com.jimei.xiaolumeimei.callback;

import com.jimei.xiaolumeimei.model.User;

/**
 * Created by itxuye(www.itxuye.com) on 15/12/29.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public interface OnLoginListener
{
  void loginSuccess(User user);

  void loginFailed();
}
