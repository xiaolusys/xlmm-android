package com.jimei.xiaolumeimei.ui.activity.trade;

import android.os.Bundle;
import android.util.Log;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.AllOdersBean;
import com.jimei.xiaolumeimei.model.TradeModel;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye(www.itxuye.com) on 15/12/29.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class AlloderActivity extends BaseSwipeBackCompatActivity {

  TradeModel model = new TradeModel();

  @Override protected void setListener() {

  }

  @Override protected void initData() {
    model.getAlloderBean()
        .subscribeOn(Schedulers.newThread())
        .subscribe(new ServiceResponse<AllOdersBean>() {
          @Override public void onNext(AllOdersBean allOdersBean) {
            super.onNext(allOdersBean);
            Log.i("itxuye", allOdersBean.toString());
          }
        });
  }

  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.alloders_activity;
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
