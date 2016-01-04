package com.jimei.xiaolumeimei.biz;

import com.jimei.xiaolumeimei.callback.OnLoginListener;

/**
 * Created by itxuye(www.itxuye.com) on 15/12/29.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public abstract interface IUserBiz {

  void login(String username, String password, OnLoginListener loginListener);
}

