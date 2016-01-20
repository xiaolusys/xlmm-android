package com.jimei.xiaolumeimei.ui.activity.user;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.model.TradeModel;

import butterknife.Bind;

public class SettingPasswordActivity extends BaseSwipeBackCompatActivity {
  String TAG = "SettingPasswordActivity";
  @Bind(R.id.toolbar)  Toolbar toolbar;
  TradeModel model = new TradeModel();
  @Override protected void setListener() {

  }

  @Override protected void initData() {

  }

  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.setting_password_activity;
  }

  @Override protected void initViews() {
    toolbar.setTitle("修改密码");
    setSupportActionBar(toolbar);
  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }
}
