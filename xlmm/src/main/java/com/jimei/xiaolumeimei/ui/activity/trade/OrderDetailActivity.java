package com.jimei.xiaolumeimei.ui.activity.trade;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.OrderGoodsListAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.entities.OrderDetailBean;
import com.jimei.xiaolumeimei.model.TradeModel;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import rx.schedulers.Schedulers;

public class OrderDetailActivity extends BaseSwipeBackCompatActivity
    implements View.OnClickListener {
  String TAG = "OrderDetailActivity";
  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.btn_order_proc) Button btn_proc;
  int order_id = 0;
  TradeModel model = new TradeModel();
  private OrderGoodsListAdapter mGoodsAdapter;

  @Override protected void setListener() {
    btn_proc.setOnClickListener(this);
    toolbar.setOnClickListener(this);
  }

  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.activity_order_detail;
  }

  @Override protected void initViews() {
    toolbar.setTitle("");
    setSupportActionBar(toolbar);
    toolbar.setNavigationIcon(R.drawable.back);

    ListView lv_goods = (ListView) findViewById(R.id.lv_goods);
    mGoodsAdapter = new OrderGoodsListAdapter(this);
    lv_goods.setAdapter(mGoodsAdapter);
  }

  //从server端获得所有订单数据，可能要查询几次
  @Override protected void initData() {
    order_id = getIntent().getExtras().getInt("orderinfo");
    model.getOrderDetailBean(order_id)
        .subscribeOn(Schedulers.newThread())
        .subscribe(new ServiceResponse<OrderDetailBean>() {
          @Override public void onNext(OrderDetailBean orderDetailBean) {

            fillDataToView(orderDetailBean);
            showProcBtn(orderDetailBean);
            Log.i(TAG, orderDetailBean.toString());
          }
        });
  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }

  private void fillDataToView(OrderDetailBean orderDetailBean) {
    TextView tx_order_id = (TextView) findViewById(R.id.tx_order_id);
    tx_order_id.setText("订单编号 " + orderDetailBean.getTid());

    TextView tx_order_state = (TextView) findViewById(R.id.tx_order_state);
    tx_order_state.setText(orderDetailBean.getStatus_display());

    TextView tx_custom_name = (TextView) findViewById(R.id.tx_custom_name);
    tx_custom_name.setText("姓名：" + orderDetailBean.getReceiver_name());

    TextView tx_custom_address = (TextView) findViewById(R.id.tx_custom_address);
    tx_custom_address.setText("地址：" + orderDetailBean.getReceiver_address());

    //没有订单创建时间?
    TextView tx_order_crttime = (TextView) findViewById(R.id.tx_order_crttime);
    tx_order_crttime.setText(" " + orderDetailBean.getCreated());
    TextView tx_order_crtstate = (TextView) findViewById(R.id.tx_order_crtstate);
    tx_order_crtstate.setText("订单创建成功");

    mGoodsAdapter.update(orderDetailBean.getOrders());

    TextView tx_order_totalfee = (TextView) findViewById(R.id.tx_order_totalfee);
    tx_order_totalfee.setText("￥" + orderDetailBean.getTotal_fee());

    TextView tx_order_discountfee = (TextView) findViewById(R.id.tx_order_discountfee);
    tx_order_discountfee.setText("￥" + orderDetailBean.getDiscount_fee());

    TextView tx_order_postfee = (TextView) findViewById(R.id.tx_order_postfee);
    tx_order_postfee.setText("￥" + orderDetailBean.getPost_fee());

    TextView tx_order_payment = (TextView) findViewById(R.id.tx_order_payment);
    tx_order_payment.setText("￥" + orderDetailBean.getPayment());
  }

  private void showProcBtn(OrderDetailBean orderDetailBean) {
    try {
      int state = orderDetailBean.getOrders().get(0).getStatus();
      switch (state) {
        case XlmmConst.ORDER_STATE_WAITPAY: {
          Log.d(TAG, "wait pay lefttime show");
          RelativeLayout rlayout_order_lefttime =
              (RelativeLayout) findViewById(R.id.rlayout_order_lefttime);
          rlayout_order_lefttime.setVisibility(View.VISIBLE);
          LinearLayout llayout_order_lefttime =
              (LinearLayout) findViewById(R.id.llayout_order_lefttime);
          llayout_order_lefttime.setVisibility(View.VISIBLE);
          Button btn_order_cancel = (Button) findViewById(R.id.btn_order_cancel);
          btn_order_cancel.setVisibility(View.VISIBLE);
          break;
        }
        case XlmmConst.ORDER_STATE_PAYED: {
          break;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override public void onClick(View v) {
    switch (v.getId()) {
      case R.id.btn_order_proc:
        //Intent intent = new Intent(OrderDetailActivity.this, MainActivity.class);
        //startActivity(intent);
        finish();
        break;
      case R.id.toolbar:
        finish();
        break;
    }
  }
}
