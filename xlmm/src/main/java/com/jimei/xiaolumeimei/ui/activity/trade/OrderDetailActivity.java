package com.jimei.xiaolumeimei.ui.activity.trade;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import cn.iwgang.countdownview.CountdownView;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.OrderGoodsListAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.entities.OrderDetailBean;
import com.jimei.xiaolumeimei.entities.PackageBean;
import com.jimei.xiaolumeimei.model.TradeModel;
import com.jimei.xiaolumeimei.ui.activity.user.WaitSendAddressActivity;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.pingplusplus.android.PaymentActivity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import rx.Subscription;
import rx.schedulers.Schedulers;

public class OrderDetailActivity extends BaseSwipeBackCompatActivity
        implements View.OnClickListener {
    private static final int REQUEST_CODE_PAYMENT = 1;
    String TAG = "OrderDetailActivity";
    @Bind(R.id.btn_order_proc)
    Button btn_proc;
    @Bind(R.id.btn_order_cancel)
    Button btn_order_cancel;
    @Bind(R.id.rlayout_order_lefttime)
    RelativeLayout rlayout_order_lefttime;
    @Bind(R.id.tx_order_id)
    TextView tx_order_id;
    @Bind(R.id.tx_order_state)
    TextView tx_order_state;
    @Bind(R.id.tx_custom_name)
    TextView tx_custom_name;
    @Bind(R.id.tx_custom_address)
    TextView tx_custom_address;
    @Bind(R.id.tx_order_totalfee)
    TextView tx_order_totalfee;
    @Bind(R.id.tx_order_discountfee)
    TextView tx_order_discountfee;
    @Bind(R.id.tx_order_postfee)
    TextView tx_order_postfee;
    @Bind(R.id.tx_order_payment)
    TextView tx_order_payment;
    @Bind(R.id.address)
    RelativeLayout addressLayout;
    @Bind(R.id.right_flag)
    ImageView rightImage;
    private ArrayList<PackageBean> packageBeanList;
    int order_id = 0;
    OrderDetailBean orderDetail;
    String source;
    String tid;
    private OrderGoodsListAdapter mGoodsAdapter;

    @Override
    protected void setListener() {
        btn_proc.setOnClickListener(this);
        btn_order_cancel.setOnClickListener(this);
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected void initViews() {

    }

    //从server端获得所有订单数据，可能要查询几次
    @Override
    protected void initData() {
        if ((getIntent() != null) && (getIntent().getExtras() != null)) {
            order_id = getIntent().getExtras().getInt("orderinfo");
            source = getIntent().getExtras().getString("source");

            Subscription subscription = TradeModel.getInstance()
                    .getOrderDetailBean(order_id)
                    .subscribeOn(Schedulers.io())
                    .subscribe(new ServiceResponse<OrderDetailBean>() {

                        @Override
                        public void onNext(OrderDetailBean orderDetailBean) {
                            tid = orderDetailBean.getTid();
                            orderDetail = orderDetailBean;
                            showProcBtn(orderDetailBean);
                            fillDataToView(orderDetailBean);
                            // TODO: 16/5/27 后台修改地址功能暂时未完成,先屏蔽修改地址功能
//                            if ("已付款".equals(orderDetailBean.getStatus_display())) {
//                                addressLayout.setOnClickListener(OrderDetailActivity.this);
//                            } else {
                            rightImage.setVisibility(View.GONE);
//                            }
                            Log.i(TAG, "order_id " + order_id + " " + orderDetailBean.toString());
                        }

                        @Override
                        public void onError(Throwable e) {

                            Log.e(TAG, " error:, " + e.toString());
                            super.onError(e);
                        }
                    });
            addSubscription(subscription);
        }
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    private void fillDataToView(OrderDetailBean orderDetailBean) {
        tx_order_id.setText("订单编号: " + orderDetailBean.getTid());
        tx_order_state.setText(orderDetailBean.getStatus_display());
        tx_custom_name.setText("姓名：" + orderDetailBean.getUser_adress().getReceiver_name());
        tx_custom_address.setText("地址：" + orderDetailBean.getUser_adress().getReceiver_state() + orderDetailBean.getUser_adress().getReceiver_city() + orderDetailBean.getUser_adress().getReceiver_address());
        tx_order_totalfee.setText("¥" + orderDetailBean.getTotal_fee());
        tx_order_discountfee.setText("¥" + orderDetailBean.getDiscount_fee());
        tx_order_postfee.setText("¥" + orderDetailBean.getPost_fee());
        tx_order_payment.setText("¥" + orderDetailBean.getPayment());

        JUtils.Log(TAG, "crt time " + orderDetailBean.getCreated());
        ListView lv_goods = (ListView) findViewById(R.id.lv_goods);
        mGoodsAdapter = new OrderGoodsListAdapter(this);

        packageBeanList = new ArrayList<>();
        Subscription subscribe = TradeModel.getInstance()
                .getPackageList(tid)
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<ArrayList<PackageBean>>() {
                    @Override
                    public void onNext(ArrayList<PackageBean> packageBeen) {
                        packageBeanList.addAll(packageBeen);
                        mGoodsAdapter.setPackageBeanList(packageBeanList);
                        mGoodsAdapter.setData(orderDetailBean);
                        lv_goods.setAdapter(mGoodsAdapter);
                        setListViewHeightBasedOnChildren(lv_goods);
                    }
                });
        addSubscription(subscribe);


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
                                    @Override
                                    public void onEnd(CountdownView cv) {
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

    @Override
    public void onClick(View v) {
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
            case R.id.address:
                Intent intent = new Intent(this, WaitSendAddressActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("receiver_name", orderDetail.getUser_adress().getReceiver_name());
                bundle.putString("mobile", orderDetail.getUser_adress().getReceiver_mobile());
                bundle.putString("address1", orderDetail.getUser_adress().getReceiver_state() + orderDetail.getUser_adress().getReceiver_city() + orderDetail.getUser_adress().getReceiver_district());
                bundle.putString("address2", orderDetail.getUser_adress().getReceiver_address());
                bundle.putString("receiver_state", orderDetail.getUser_adress().getReceiver_state());
                bundle.putString("receiver_city", orderDetail.getUser_adress().getReceiver_city());
                bundle.putString("receiver_district", orderDetail.getUser_adress().getReceiver_district());
                bundle.putString("address_id", orderDetail.getUser_adress().getId() + "");
                bundle.putString("referal_trade_id", orderDetail.getId() + "");
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Subscription subscription = TradeModel.getInstance()
                .getOrderDetailBean(order_id)
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<OrderDetailBean>() {
                    @Override
                    public void onNext(OrderDetailBean orderDetailBean) {
                        orderDetail = orderDetailBean;
                        tx_custom_name.setText("姓名：" + orderDetailBean.getUser_adress().getReceiver_name());
                        tx_custom_address.setText("地址：" + orderDetailBean.getUser_adress().getReceiver_state() + orderDetailBean.getUser_adress().getReceiver_city() + orderDetailBean.getUser_adress().getReceiver_address());
                    }
                });
        addSubscription(subscription);
    }

    private void payNow() {
        Subscription subscription = TradeModel.getInstance()
                .shoppingcart_paynow(order_id)
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<ResponseBody>() {
                    @Override
                    public void onNext(ResponseBody responseBody) {
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

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {

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
                    @Override
                    public void onNext(ResponseBody responseBody) {
                        super.onNext(responseBody);
                        try {

                            finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {

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

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
