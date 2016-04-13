package com.jimei.xiaolumeimei.ui.activity.trade;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
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
import cn.iwgang.countdownview.CountdownView;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.OrderGoodsListAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.data.PayRightNowInfo;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.entities.LogisticsBean;
import com.jimei.xiaolumeimei.entities.OrderDetailBean;
import com.jimei.xiaolumeimei.model.TradeModel;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.pingplusplus.android.PaymentActivity;
import com.squareup.okhttp.ResponseBody;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import rx.Subscription;
import rx.schedulers.Schedulers;

public class OrderDetailActivity extends BaseSwipeBackCompatActivity
    implements View.OnClickListener {
  private static final int REQUEST_CODE_PAYMENT = 1;
  String TAG = "OrderDetailActivity";
  @Bind(R.id.btn_order_proc) Button btn_proc;
  @Bind(R.id.btn_order_cancel) Button btn_order_cancel;
  @Bind(R.id.rlayout_order_lefttime) RelativeLayout rlayout_order_lefttime;
  @Bind(R.id.logistics_layout) RelativeLayout logisticsLayout;

  int order_id = 0;
  OrderDetailBean orderDetail;
  PayRightNowInfo pay_info = new PayRightNowInfo();
  String source;
  private OrderGoodsListAdapter mGoodsAdapter;
  String tid;
  private String time;

  @Override protected void setListener() {
    btn_proc.setOnClickListener(this);
    btn_order_cancel.setOnClickListener(this);
    logisticsLayout.setOnClickListener(this);
  }

  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.activity_order_detail;
  }

  @Override protected void initViews() {
    ListView lv_goods = (ListView) findViewById(R.id.lv_goods);
    mGoodsAdapter = new OrderGoodsListAdapter(this);
    lv_goods.setAdapter(mGoodsAdapter);
  }

  //从server端获得所有订单数据，可能要查询几次
  @Override protected void initData() {
    if ((getIntent() != null) && (getIntent().getExtras() != null)) {
      order_id = getIntent().getExtras().getInt("orderinfo");
      source = getIntent().getExtras().getString("source");

      Subscription subscription = TradeModel.getInstance()
          .getOrderDetailBean(order_id)
          .subscribeOn(Schedulers.io())
          .subscribe(new ServiceResponse<OrderDetailBean>() {

            @Override public void onNext(OrderDetailBean orderDetailBean) {
              tid = orderDetailBean.getTid();
              orderDetail = orderDetailBean;
              fillDataToView(orderDetailBean);
              showProcBtn(orderDetailBean);

              Log.i(TAG, "order_id " + order_id + " " + orderDetailBean.toString());
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
  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }

  private void fillDataToView(OrderDetailBean orderDetailBean) {
    time = orderDetailBean.getCreated().replace("T", " ");
    TextView tx_order_id = (TextView) findViewById(R.id.tx_order_id);
    tx_order_id.setText("订单编号 " + orderDetailBean.getTid());

    TextView tx_order_state = (TextView) findViewById(R.id.tx_order_state);
    tx_order_state.setText(orderDetailBean.getStatus_display());

    TextView tx_custom_name = (TextView) findViewById(R.id.tx_custom_name);
    tx_custom_name.setText("姓名：" + orderDetailBean.getReceiver_name());

    TextView tx_custom_address = (TextView) findViewById(R.id.tx_custom_address);
    tx_custom_address.setText("地址：" + orderDetailBean.getReceiver_address());

    TextView tx_order_crttime = (TextView) findViewById(R.id.tx_order_crttime);
    TextView tx_order_crtstate = (TextView) findViewById(R.id.tx_order_crtstate);

    if (tid!="") {
      Subscription subscribe = TradeModel.getInstance()
              .get_logistics(tid)
              .subscribeOn(Schedulers.io())
              .subscribe(new ServiceResponse<LogisticsBean>() {

                @Override
                public void onNext(LogisticsBean logisticsBean) {
                  List<LogisticsBean.Msg> data = logisticsBean.getData();
                  if (data.size()>=1){
                    LogisticsBean.Msg msg = data.get(data.size() - 1);
                    tx_order_crttime.setText(msg.getTime().replace("T", " "));
                    tx_order_crtstate.setText(msg.getContent());
                  }else{
                    tx_order_crttime.setText("时间：" + orderDetailBean.getCreated().replace("T", " "));
                    tx_order_crtstate.setText("订单创建成功");
                  }
                }

                @Override
                public void onCompleted() {
                }

                @Override
                public void onError(Throwable e) {
                }
              });
      addSubscription(subscribe);
    }else{
      tx_order_crttime.setText("时间：" + orderDetailBean.getCreated().replace("T", " "));
      tx_order_crtstate.setText("订单创建成功");
    }

    JUtils.Log(TAG, "crt time " + orderDetailBean.getCreated());

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
          Log.i(TAG, "wait pay lefttime show");

          rlayout_order_lefttime.setVisibility(View.VISIBLE);
          LinearLayout llayout_order_lefttime =
              (LinearLayout) findViewById(R.id.llayout_order_lefttime);
          llayout_order_lefttime.setVisibility(View.VISIBLE);
          cn.iwgang.countdownview.CountdownView cv_lefttime =
              (cn.iwgang.countdownview.CountdownView) findViewById(R.id.cv_lefttime);

          if (calcLeftTime(orderDetailBean.getCreated()) > 0) {
            cv_lefttime.start(calcLeftTime(orderDetailBean.getCreated()));
            cv_lefttime.setOnCountdownEndListener(
                new CountdownView.OnCountdownEndListener() {
                  @Override public void onEnd(CountdownView cv) {
                    JUtils.Log(TAG, "timeout");
                    btn_proc.setClickable(false);
                    btn_proc.setBackgroundColor(Color.parseColor("#f3f3f4"));
                  }
                });
          } else {
            JUtils.Log(TAG, "left time 0");
            btn_proc.setClickable(false);
            btn_proc.setBackgroundColor(Color.parseColor("#f3f3f4"));
          }

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

  private long calcLeftTime(String crtTime) {
    long left = 0;
    Date now = new Date();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    try {
      crtTime = crtTime.replace("T", " ");
      Date crtdate = format.parse(crtTime);
      Log.d(TAG, " crtdate  " + crtdate.getTime() + "now " + now.getTime());
      if (crtdate.getTime() + 20 * 60 * 1000 - now.getTime() > 0) {
        left = crtdate.getTime() + 20 * 60 * 1000 - now.getTime();
      }
    } catch (Exception e) {
      Log.e(TAG, "left time get failed ");
      e.printStackTrace();
    }

    Log.d(TAG, "left time  " + left);

    return left;
  }

  @Override public void onClick(View v) {
    switch (v.getId()) {
      case R.id.btn_order_proc:
        if (orderDetail.getStatus() == XlmmConst.ORDER_STATE_WAITPAY) {
          JUtils.Log(TAG, "onClick paynow");
          payNow();
        }
        break;
      case R.id.btn_order_cancel:
        JUtils.Log(TAG, "onClick cancel");
        cancel_order();
        break;
      case R.id.logistics_layout:
        Intent intent = new Intent(this, LogisticsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("tid",tid);
        bundle.putString("time",time);
        intent.putExtras(bundle);
        startActivity(intent);
        break;
    }
  }

  private void payNow() {
    Subscription subscription = TradeModel.getInstance()
        .shoppingcart_paynow(order_id)
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<ResponseBody>() {
          @Override public void onNext(ResponseBody responseBody) {
            super.onNext(responseBody);
            try {
              String charge = responseBody.string();
              Log.i("charge", charge);
              Intent intent = new Intent();
              String packageName = getPackageName();
              ComponentName componentName = new ComponentName(packageName,
                  packageName + ".wxapi.WXPayEntryActivity");
              intent.setComponent(componentName);
              intent.putExtra(PaymentActivity.EXTRA_CHARGE, charge);
              startActivityForResult(intent, REQUEST_CODE_PAYMENT);
              finish();
            } catch (IOException e) {
              e.printStackTrace();
            }
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

  private void cancel_order() {
    JUtils.Log(TAG, "cancel_order " + order_id);
    Subscription subscription = TradeModel.getInstance()
        .delRefund(order_id)
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<ResponseBody>() {
          @Override public void onNext(ResponseBody responseBody) {
            super.onNext(responseBody);
            try {

              finish();
            } catch (Exception e) {
              e.printStackTrace();
            }
          }

          @Override public void onCompleted() {
            super.onCompleted();
          }

          @Override public void onError(Throwable e) {

            Log.e(TAG, "delRefund error:, " + e.toString());
            super.onError(e);
          }
        });
    addSubscription(subscription);
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
        if (result.equals("cancel")) {
          //wexin alipay already showmsg
        } else if (result.equals("success")) {
          JUtils.Toast("支付成功！");
          finish();
        } else {
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
    if (title.equals("fail")) {
      str = "支付失败，请重试！";
    } else if (title.equals("invalid")) {
      str = "支付失败，支付软件未安装完整！";
    }

    AlertDialog.Builder builder = new AlertDialog.Builder(OrderDetailActivity.this);
    builder.setMessage(str);
    builder.setTitle("提示");
    builder.setPositiveButton("OK", null);
    builder.create().show();
  }
}
