package com.jimei.xiaolumeimei.ui.activity.trade;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.WaitPayOrdersListAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.AllOrdersBean;
import com.jimei.xiaolumeimei.model.TradeModel;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;

import java.util.List;
import rx.Subscription;
import rx.schedulers.Schedulers;

public class WaitPayOrdersActivity extends BaseSwipeBackCompatActivity
    implements View.OnClickListener {
  String TAG = "WaitPayOrdersActivity";

  @Bind(R.id.btn_jump) Button btn_jump;
  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.rlayout_order_empty) RelativeLayout rl_empty;

  TradeModel model = new TradeModel();
  private WaitPayOrdersListAdapter mAllOrderAdapter;

  @Override protected void setListener() {
    btn_jump.setOnClickListener(this);
    toolbar.setOnClickListener(this);
  }

  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.activity_waitpayorders;
  }

  @Override protected void initViews() {
    toolbar.setTitle("");
    setSupportActionBar(toolbar);
    finishBack(toolbar);
    ListView all_orders_listview = (ListView) findViewById(R.id.all_orders_listview);

    mAllOrderAdapter = new WaitPayOrdersListAdapter(this);
    all_orders_listview.setAdapter(mAllOrderAdapter);

    TextView tx_info = (TextView) findViewById(R.id.tx_info);
    tx_info.setText("亲，您暂时还没有待支付订单哦~快去看看吧！");
  }

  @Override protected void initData() {


  }
  //从server端获得所有订单数据，可能要查询几次
  private void initOrderData() {
   Subscription subscription= model.getWaitPayOrdersBean()
        .subscribeOn(Schedulers.newThread())
        .subscribe(new ServiceResponse<AllOrdersBean>() {
          @Override public void onNext(AllOrdersBean allOrdersBean) {
            List<AllOrdersBean.ResultsEntity> results = allOrdersBean.getResults();
            if (0 == results.size()) {
              rl_empty.setVisibility(View.VISIBLE);
            } else {
              mAllOrderAdapter.update(results);
            }

            Log.i(TAG, allOrdersBean.toString());
          }

          @Override
          public void onCompleted() {
            super.onCompleted();
          }

          @Override
          public void onError(Throwable e) {

            Log.e(TAG, " error:, "   + e.toString());
            super.onError(e);
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
      case R.id.btn_jump:
        //Intent intent = new Intent(WaitPayOrdersActivity.this, MainActivity.class);
        //startActivity(intent);
        finish();
        break;

    }
  }

  @Override
  protected void onResume() {
    JUtils.Log(TAG, "onresume init orderdata");
    super.onResume();
    initOrderData();
  }
}
