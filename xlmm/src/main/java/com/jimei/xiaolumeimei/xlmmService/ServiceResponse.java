package com.jimei.xiaolumeimei.xlmmService;

import android.content.Context;
import android.content.Intent;
import com.jimei.xiaolumeimei.data.XlmmApi;
import com.jimei.xiaolumeimei.exception.ServiceException;
import com.jimei.xiaolumeimei.ui.user.LoginActivity;
import com.jude.utils.JActivityManager;
import com.jude.utils.JUtils;
import rx.Observer;

/**
 * Created by itxuye(www.itxuye.com) on 15/12/29.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class ServiceResponse<T> implements Observer<T> {

  @Override public void onNext(T t) {

  }

  @Override public void onCompleted() {

  }

  @Override public void onError(Throwable e) {
    if (e.getCause() instanceof ServiceException) {
      //JUtils.Log("Server Error:" + e.getLocalizedMessage());
      onServiceError(((ServiceException) e.getCause()).getStatus(),
          ((ServiceException) e.getCause()).getInfo());
    } else {
      //JUtils.Log("UnKnow Error:" + e.getLocalizedMessage());
      onServiceError(XlmmApi.CODE.NET_INVALID, "网络错误");
    }
  }

  public void onServiceError(int status, String info) {
    if (status == XlmmApi.CODE.LOGIN_INVALID) {
      Context ctx = JActivityManager.getInstance().currentActivity();
      ctx.startActivity(new Intent(ctx, LoginActivity.class));
      return;
    }
    JUtils.Toast(info);
  }
}
