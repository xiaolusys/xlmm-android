package com.jimei.xiaolumeimei.ui.activity.trade;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import butterknife.Bind;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.model.TradeModel;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.squareup.okhttp.ResponseBody;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye(www.itxuye.com) on 2016/01/22.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class WriteLogisticsInfoActivty extends BaseSwipeBackCompatActivity
    implements View.OnClickListener {

  String TAG = "WriteLogisticsInfoActivty";

  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.et_logistics_company) EditText et_logistics_company;
  @Bind(R.id.et_logistics_number) EditText et_logistics_number;
  @Bind(R.id.btn_commit) Button btn_commit;

  String company;
  String logistics_number;
  int goods_id;

  @Override protected void setListener() {
    toolbar.setOnClickListener(this);
    btn_commit.setOnClickListener(this);
  }

  @Override protected void initData() {
  }

  @Override protected void getBundleExtras(Bundle extras) {
    goods_id = extras.getInt("goods_id");
  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.activity_write_logistics_info;
  }

  @Override protected void initViews() {
    toolbar.setTitle("");
    setSupportActionBar(toolbar);
    finishBack(toolbar);
    et_logistics_company.setOnTouchListener(new View.OnTouchListener() {
      public boolean onTouch(View v, MotionEvent event) {
        //et_refund_reason.setInputType(InputType.TYPE_NULL); //关闭软键盘
        if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
          Log.d(TAG, "choose logistics commpay");
          Intent intent = new Intent(WriteLogisticsInfoActivty.this,
              ChooseLogisticsCompanyActivity.class);

          Log.d(TAG, " to ChooseLogisticsCompanyActivity");
          startActivityForResult(intent, 1);
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

      case R.id.btn_commit:
        commit_logistics_info();
        break;
    }
  }

  protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == 1) {

      // data contains result
      company = data.getExtras().getString("company");
      Log.d(TAG, "onActivityResult company" + company);
      et_logistics_company.setText(company);
    }
  }

  private void commit_logistics_info() {
    Log.i(TAG, "commit_logistics_info goods_id  "
        + goods_id
        + " "
        + company
        + " "
        + ""
        + et_logistics_number.getText().toString().trim());
    Subscription subscription = TradeModel.getInstance()
        .commit_logistics_info(goods_id, company,
            et_logistics_number.getText().toString().trim())
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<ResponseBody>() {
          @Override public void onNext(ResponseBody resp) {
            JUtils.Toast("提交物流信息成功，收货后我们会尽快为您处理退款！");
            Log.i(TAG, "commit_logistics_info " + resp.toString());
            finish();
          }

          @Override public void onCompleted() {
            super.onCompleted();
          }

          @Override public void onError(Throwable e) {
            e.printStackTrace();
            Log.e(TAG, " error:commit_logistics_info " + e.toString());
            JUtils.Toast("网络异常，提交信息失败，请重试！");
            super.onError(e);
          }
        });
    addSubscription(subscription);
  }
}
