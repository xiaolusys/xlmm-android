package com.jimei.xiaolumeimei.ui.activity.trade;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import butterknife.Bind;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;

/**
 * Created by itxuye(www.itxuye.com) on 2016/01/22.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class WriteLogisticsInfoActivty extends BaseSwipeBackCompatActivity implements View.OnClickListener{

  String TAG = "WriteLogisticsInfoActivty";

  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.et_logistics_company) EditText et_logistics_company;
  @Bind(R.id.et_logistics_number) EditText et_logistics_number;


  @Override protected void setListener() {
    toolbar.setOnClickListener(this);
  }

  @Override protected void initData() {

  }

  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.activity_write_logistics_info;
  }

  @Override protected void initViews() {
    toolbar.setTitle("");
    setSupportActionBar(toolbar);
    toolbar.setNavigationIcon(R.drawable.back);

    et_logistics_company.setOnTouchListener(new View.OnTouchListener(){
      public boolean onTouch(View v, MotionEvent event){
        //et_refund_reason.setInputType(InputType.TYPE_NULL); //关闭软键盘
        if(MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
          Log.d(TAG, "choose logistics commpay");
          Intent intent = new Intent(WriteLogisticsInfoActivty.this, ChooseLogisticsCompanyActivity.class);

          Log.d(TAG," to ChooseLogisticsCompanyActivity");
          startActivity(intent);
        }
        return false;
      }
    });
  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }

  @Override public void onClick(View v) {
    switch (v.getId()) {
      case R.id.toolbar:
        finish();
        break;

    }
  }
}
