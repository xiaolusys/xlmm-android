package com.jimei.xiaolumeimei.ui.activity.main;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
public class ComplainActvity extends BaseSwipeBackCompatActivity
    implements View.OnClickListener {
  @Bind(R.id.tijiao) Button confirm;
  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.complain_text) EditText complainText;
  private Subscription subscribe;

  @Override protected void setListener() {
    confirm.setOnClickListener(this);
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

      subscribe = UserModel.getInstance()
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
    }
  }

  @Override protected void onStop() {
    super.onStop();
    if (subscribe != null && subscribe.isUnsubscribed()) {
      subscribe.unsubscribe();
    }
  }
}
