package com.jimei.xiaolumeimei.ui.activity.main;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import butterknife.Bind;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jude.utils.JUtils;

/**
 * Created by itxuye(www.itxuye.com) on 2016/01/19.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class CompanyInfoActivity extends BaseSwipeBackCompatActivity {

  @Bind(R.id.tv_version) TextView tv_version;

  @Override protected void setListener() {
    
  }

  @Override protected void initData() {

  }

  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.companyinfo_activity;
  }

  @Override protected void initViews() {
    tv_version.setText(XlmmConst.VERSION);
  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
   
  }

  
  
}
