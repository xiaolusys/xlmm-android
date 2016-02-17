package com.jimei.xiaolumeimei.ui.activity.user;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Bind;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.widget.CircleImageView;
import com.jimei.xiaolumeimei.widget.ClearEditText;

/**
 * Created by itxuye(www.itxuye.com) on 2016/02/05.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class WxLoginBindPhoneActivity extends BaseSwipeBackCompatActivity {

  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.headimage) CircleImageView headimage;
  @Bind(R.id.tv_nickname) TextView tvNickname;
  @Bind(R.id.register_name) ClearEditText registerName;
  @Bind(R.id.checkcode) EditText checkcode;
  @Bind(R.id.getCheckCode) Button getCheckCode;
  @Bind(R.id.next) Button next;
  @Bind(R.id.pass) Button pass;
  private String headimgurl;
  private String nickname;

  @Override protected void setListener() {

  }

  @Override protected void initData() {

  }

  @Override protected void getBundleExtras(Bundle extras) {

    headimgurl = extras.getString("headimgurl");
    nickname = extras.getString("nickname");
  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.activity_wxlogin;
  }

  @Override protected void initViews() {
    tvNickname.setText("微信账号： "+nickname);

  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }

}
