package com.jimei.xiaolumeimei.ui.activity.trade;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.Bind;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.OrderGoodsListAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.data.PayRightNowInfo;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.entities.AddressBean;
import com.jimei.xiaolumeimei.entities.CartsPayinfoBean;
import com.jimei.xiaolumeimei.entities.OrderDetailBean;
import com.jimei.xiaolumeimei.model.AddressModel;
import com.jimei.xiaolumeimei.model.CartsModel;
import com.jimei.xiaolumeimei.model.TradeModel;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.pingplusplus.android.PaymentActivity;
import com.squareup.okhttp.ResponseBody;
import java.io.IOException;
import java.util.List;
import rx.schedulers.Schedulers;

public class OrderDetailActivity extends BaseSwipeBackCompatActivity
    implements View.OnClickListener {
  String TAG = "OrderDetailActivity";
  private static final int REQUEST_CODE_PAYMENT = 1;

  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.btn_order_proc) Button btn_proc;
  @Bind(R.id.rlayout_order_lefttime) RelativeLayout rlayout_order_lefttime;

  int order_id = 0;
  OrderDetailBean orderDetail;
  TradeModel tradeModel = new TradeModel();
  private OrderGoodsListAdapter mGoodsAdapter;
  PayRightNowInfo pay_info = new PayRightNowInfo();

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
    tradeModel.getOrderDetailBean(order_id)
        .subscribeOn(Schedulers.newThread())
        .subscribe(new ServiceResponse<OrderDetailBean>() {
          @Override public void onNext(OrderDetailBean orderDetailBean) {

            orderDetail = orderDetailBean;
            fillDataToView(orderDetailBean);
            showProcBtn(orderDetailBean);

            Log.i(TAG,"order_id "+order_id + " " + orderDetailBean.toString());
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

    TextView tx_order_crttime = (TextView) findViewById(R.id.tx_order_crttime);
    tx_order_crttime.setText(" " + orderDetailBean.getCreated());
    TextView tx_order_crtstate = (TextView) findViewById(R.id.tx_order_crtstate);
    tx_order_crtstate.setText("订单创建成功");

    mGoodsAdapter.update(orderDetailBean.getOrders());

    TextView tx_order_totalfee = (TextView) findViewById(R.id.tx_order_totalfee);
    tx_order_totalfee.setText("¥" + orderDetailBean.getTotal_fee());

    TextView tx_order_discountfee = (TextView) findViewById(R.id.tx_order_discountfee);
    tx_order_discountfee.setText("¥" + orderDetailBean.getDiscount_fee());

    TextView tx_order_postfee = (TextView) findViewById(R.id.tx_order_postfee);
    tx_order_postfee.setText("¥" + orderDetailBean.getPost_fee());

    TextView tx_order_payment = (TextView) findViewById(R.id.tx_order_payment);
    tx_order_payment.setText("¥" + orderDetailBean.getPayment());
  }

  private void showProcBtn(OrderDetailBean orderDetailBean) {
    Log.d(TAG, "state " + orderDetailBean.getStatus());
    try {
      int state = orderDetailBean.getStatus();
      switch (state) {
        case XlmmConst.ORDER_STATE_WAITPAY: {
          Log.d(TAG, "wait pay lefttime show");

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
        if(orderDetail.getStatus() == XlmmConst.ORDER_STATE_WAITPAY){
          Log.i(TAG, "onClick paynow");
          payNow();

        }

        break;
      case R.id.toolbar:
        finish();
        break;
    }
  }



  private void payNow(){
    tradeModel.shoppingcart_paynow(order_id)
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<ResponseBody>() {
          @Override public void onNext(ResponseBody responseBody) {
            super.onNext(responseBody);
            try {
              String string = responseBody.string();
              Log.i("charge", string);
              Intent intent = new Intent();
              String packageName = getPackageName();
              ComponentName componentName = new ComponentName(packageName,
                  packageName + ".wxapi.WXPayEntryActivity");
              intent.setComponent(componentName);
              intent.putExtra(PaymentActivity.EXTRA_CHARGE, string);
              startActivityForResult(intent, REQUEST_CODE_PAYMENT);
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
        });

  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    //支付页面返回处理
    if (requestCode == REQUEST_CODE_PAYMENT) {
      if (resultCode == Activity.RESULT_OK) {
        String result = data.getExtras().getString("pay_result");
            /* 处理返回值
             * "success" - payment succeed
             * "fail"    - payment failed
             * "cancel"  - user canceld
             * "invalid" - payment plugin not installed
             *
             */
        String errorMsg = data.getExtras().getString("error_msg"); // 错误信息
        String extraMsg = data.getExtras().getString("extra_msg"); // 错误信息
        if(result.equals( "cancel")) {
          //wexin alipay already showmsg
        }
        else if(result.equals( "success")){
          JUtils.Toast("支付成功！");
          finish();
        }
        else{
          showMsg(result, errorMsg, extraMsg);
        }

      }
    }
  }

  public void showMsg(String title, String msg1, String msg2) {
    String str = title;
    if (null != msg1 && msg1.length() != 0) {
      str += "\n" + msg1;
    }
    if (null != msg2 && msg2.length() != 0) {
      str += "\n" + msg2;
    }
    Log.i(TAG, "charge result" + str);
    if(title.equals( "fail")){
      str ="支付失败，请重试！";
    }
    else if(title.equals( "invalid")){
      str ="支付失败，支付软件未安装完整！";
    }

    AlertDialog.Builder builder = new AlertDialog.Builder(OrderDetailActivity.this);
    builder.setMessage(str);
    builder.setTitle("提示");
    builder.setPositiveButton("OK", null);
    builder.create().show();
  }
}
