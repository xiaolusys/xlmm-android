package com.jimei.xiaolumeimei.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import butterknife.ButterKnife;
import com.jimei.xiaolumeimei.utils.LoginUtils;
import com.umeng.analytics.MobclickAgent;
import com.zhy.autolayout.AutoLayoutActivity;

/**
 * Created by 优尼世界 on 15/12/29.
 * <p/>
 * Copyright 2015年 上海己美. All rights reserved.
 */
public abstract class BaseActivity extends AutoLayoutActivity {

  abstract protected int provideContentViewId();

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(provideContentViewId());
    ButterKnife.bind(this);

    String[] loginInfo = LoginUtils.getLoginInfo(getApplicationContext());
    boolean b = Boolean.parseBoolean(loginInfo[2]);
    if (b) {
      LoginUtils.doLogin(loginInfo[0], loginInfo[1]);
    }

    if (savedInstanceState == null) {
      initView();

      initData();

      setListener();
    }
  }

  protected abstract void setListener();//设置监听

  protected abstract void initData();//初始化数据

  protected abstract void initView();//初始化view

  @Override protected void onDestroy() {
    super.onDestroy();
    ButterKnife.unbind(this);
  }

  @Override
  protected void onResume() {
    super.onResume();
    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    MobclickAgent.onResume(this);
  }

  @Override protected void onPause() {
    super.onPause();
    MobclickAgent.onPause(this);
  }
}
