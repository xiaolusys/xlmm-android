package com.jimei.xiaolumeimei.ui.activity.user;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import butterknife.Bind;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;

public class RegisterActivity extends BaseSwipeBackCompatActivity {

  @Bind(R.id.register_name) EditText register_name;
  @Bind(R.id.register_password) EditText register_pass;
  @Bind(R.id.register_button) Button register_button;

  @Override protected void setListener() {

  }

  @Override protected void initData() {

  }

  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.register_activity;
  }

  @Override protected void initViews() {

  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }
}
