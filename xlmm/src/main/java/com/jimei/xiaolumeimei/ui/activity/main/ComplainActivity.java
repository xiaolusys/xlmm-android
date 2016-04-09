package com.jimei.xiaolumeimei.ui.activity.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.Bind;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.AddressResultBean;
import com.jimei.xiaolumeimei.model.UserModel;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye(www.itxuye.com) on 2016/01/19.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class ComplainActivity extends BaseSwipeBackCompatActivity
    implements View.OnClickListener, TextWatcher {
  @Bind(R.id.tijiao) Button confirm;
  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.complain_text) EditText complainText;
  @Bind(R.id.count_text) TextView countText;

  @Override protected void setListener() {
    confirm.setOnClickListener(this);
    complainText.addTextChangedListener(this);
  }

  @Override protected void initData() {

  }

  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.complain_activity;
  }

  @Override protected void initViews() {
    toolbar.setTitle("");
    setSupportActionBar(toolbar);
    finishBack(toolbar);
  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }

  @Override public void onClick(View v) {

    if (v.getId() == R.id.tijiao) {
      String text = complainText.getText().toString().trim();

    Subscription  subscribe = UserModel.getInstance()
          .complain(text)
          .subscribeOn(Schedulers.io())
          .subscribe(new ServiceResponse<AddressResultBean>() {
            @Override public void onNext(AddressResultBean responseBody) {
              super.onNext(responseBody);

              if (responseBody.isRet()) {
                JUtils.Toast("您的建议已提交，我们会尽快处理,谢谢");
              }
            }
          });
      addSubscription(subscribe);
      Intent intent = new Intent(this, MainActivity.class);
      startActivity(intent);
    }
  }

  @Override protected void onStop() {
    super.onStop();

  }

  @Override
  public void beforeTextChanged(CharSequence s, int start, int count, int after) {

  }

  @Override
  public void onTextChanged(CharSequence s, int start, int before, int count) {
  }

  @Override
  public void afterTextChanged(Editable s) {
    String countStr = s.length()+"/200";
    countText.setText(countStr);
  }

  public void getSoftInput(View v){
    InputMethodManager m=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    m.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
  }
}
