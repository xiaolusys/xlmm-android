package com.jimei.xiaolumeimei.ui.activity.trade;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.AllRefundsListAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.AllRefundsBean;
import com.jimei.xiaolumeimei.model.TradeModel;
import com.jimei.xiaolumeimei.ui.activity.main.MainActivity;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import java.util.List;
import rx.Subscription;
import rx.schedulers.Schedulers;

public class AllRefundsActivity extends BaseSwipeBackCompatActivity
    implements View.OnClickListener {
  String TAG = "AllRefundsActivity";

  @Bind(R.id.btn_jump) Button btn_jump;
  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.rlayout_order_empty) RelativeLayout rl_empty;

  TradeModel model = new TradeModel();
  private AllRefundsListAdapter mAllRefundsAdapter;

  @Override protected void setListener() {
    btn_jump.setOnClickListener(this);
    toolbar.setOnClickListener(this);
  }

  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.activity_allrefunds;
  }

  @Override protected void initViews() {
    toolbar.setTitle("");
    setSupportActionBar(toolbar);
    finishBack(toolbar);
    //config allorders list adaptor
    ListView all_refunds_listview = (ListView) findViewById(R.id.all_refunds_listview);
    mAllRefundsAdapter = new AllRefundsListAdapter(this);

    all_refunds_listview.setAdapter(mAllRefundsAdapter);
    all_refunds_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

      @Override
      public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub
        Log.d(TAG, "onItemClick " + arg2 + " " + arg3);
        int goods_id = mAllRefundsAdapter.getGoodsId(arg2);
        int refund_state = mAllRefundsAdapter.getRefundStatus(arg2);
        Intent intent = new Intent(AllRefundsActivity.this, RefundDetailActivity.class);

        intent.putExtra("goods_id", goods_id);
        intent.putExtra("refund_state", refund_state);
        Log.d(TAG, "transfer goods_id  " + goods_id + " to RefundDetailActivity");
        startActivity(intent);
      }
    });

    TextView tx_info = (TextView) findViewById(R.id.tx_info);
    tx_info.setText("亲，您暂时还没有退货（款）订单哦~快去看看吧！");
  }

  //从server端获得所有订单数据，可能要查询几次
  @Override protected void initData() {
    Subscription subscription = model.getRefundsBean()
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<AllRefundsBean>() {
          @Override public void onNext(AllRefundsBean allRefundsBean) {
            List<AllRefundsBean.ResultsEntity> results = allRefundsBean.getResults();
            if (0 == results.size()) {
              Log.d(TAG, " NO redunds data");
              rl_empty.setVisibility(View.VISIBLE);
            } else {
              Log.d(TAG, " redunds data num " + results.size());

              mAllRefundsAdapter.update(results);
            }

            Log.i(TAG, allRefundsBean.toString());
          }

          @Override public void onCompleted() {
            super.onCompleted();
          }

          @Override public void onError(Throwable e) {

            Log.e(TAG, " error:, " + e.toString());
            super.onError(e);
          }
        });
    addSubscription(subscription);
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
        Intent intent = new Intent(AllRefundsActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        break;
    }
  }
}
