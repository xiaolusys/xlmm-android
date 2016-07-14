package com.jimei.xiaolumeimei.ui.activity.trade;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import butterknife.Bind;
import cn.iwgang.countdownview.CountdownView;

import com.google.gson.Gson;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.CompanyAdapter;
import com.jimei.xiaolumeimei.adapter.OrderGoodsListAdapter;
import com.jimei.xiaolumeimei.adapter.PayAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.entities.LogisticCompany;
import com.jimei.xiaolumeimei.entities.OrderDetailBean;
import com.jimei.xiaolumeimei.entities.PayInfoBean;
import com.jimei.xiaolumeimei.entities.RedBagBean;
import com.jimei.xiaolumeimei.entities.ResultBean;
import com.jimei.xiaolumeimei.model.ActivityModel;
import com.jimei.xiaolumeimei.model.TradeModel;
import com.jimei.xiaolumeimei.ui.activity.user.WaitSendAddressActivity;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.pingplusplus.android.PaymentActivity;
import com.umeng.analytics.MobclickAgent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import cn.sharesdk.wechat.moments.WechatMoments;
import okhttp3.ResponseBody;
import rx.Subscription;
import rx.schedulers.Schedulers;

public class OrderDetailActivity extends BaseSwipeBackCompatActivity
        implements View.OnClickListener, View.OnTouchListener, AdapterView.OnItemClickListener {
    private static final int REQUEST_CODE_PAYMENT = 1;
    String TAG = "OrderDetailActivity";
    @Bind(R.id.btn_order_proc)
    ImageView btn_proc;
    @Bind(R.id.btn_order_cancel)
    ImageView btn_order_cancel;
    @Bind(R.id.rlayout_order_lefttime)
    RelativeLayout rlayout_order_lefttime;
    @Bind(R.id.tx_order_id)
    TextView tx_order_id;
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
    @Bind(R.id.time)
    TextView timeText;
    @Bind(R.id.lv_goods)
    ListView lv_goods;
    @Bind(R.id.logistics)
    TextView logisticsTv;
    @Bind(R.id.logistics_layout)
    LinearLayout logisticsLayout;
    @Bind(R.id.logistics_right)
    ImageView logisticsRightImage;
    @Bind(R.id.rl_pay)
    RelativeLayout relativeLayout;
    @Bind(R.id.iv_pay)
    ImageView imageView;
    @Bind(R.id.line_2)
    ImageView line2;
    @Bind(R.id.line_3)
    ImageView line3;
    @Bind(R.id.line_4)
    ImageView line4;
    @Bind(R.id.line_5)
    ImageView line5;
    @Bind(R.id.line_6)
    ImageView line6;
    @Bind(R.id.iv_1)
    ImageView imageView1;
    @Bind(R.id.iv_2)
    ImageView imageView2;
    @Bind(R.id.iv_3)
    ImageView imageView3;
    @Bind(R.id.iv_4)
    ImageView imageView4;
    @Bind(R.id.iv_5)
    ImageView imageView5;
    @Bind(R.id.tv_1)
    TextView tv1;
    @Bind(R.id.tv_2)
    TextView tv2;
    @Bind(R.id.tv_3)
    TextView tv3;
    @Bind(R.id.tv_4)
    TextView tv4;
    @Bind(R.id.tv_5)
    TextView tv5;
    @Bind(R.id.hsv)
    HorizontalScrollView hsv;
    @Bind(R.id.red_bag)
    LinearLayout redBagLayout;
    @Bind(R.id.scroll_view)
    ScrollView scrollView;

    ListView listView;
    ListView listView2;

    int order_id = 0;
    OrderDetailBean orderDetail;
    String source;
    String tid;
    private Dialog dialog;
    private Dialog dialog2;
    private RedBagBean redBagEntity;
    private boolean alive = false;
    private boolean flag = true;

    @Override
    protected void setListener() {
        btn_proc.setOnClickListener(this);
        btn_order_cancel.setOnClickListener(this);
        logisticsLayout.setOnClickListener(this);
        redBagLayout.setOnClickListener(this);
        scrollView.setOnTouchListener(this);
        listView2.setOnItemClickListener(this);
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
        View view = getLayoutInflater().inflate(R.layout.pop_layout, null);
        dialog = new Dialog(this, R.style.CustomDialog);
        dialog.setContentView(view);
        dialog.setCancelable(true);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(wlp);
        window.setWindowAnimations(R.style.dialog_anim);
        View closeIv = view.findViewById(R.id.close_iv);
        listView = (ListView) view.findViewById(R.id.lv_logistics_company);
        closeIv.setOnClickListener(this);

        View view2 = getLayoutInflater().inflate(R.layout.pop_pay, null);
        dialog2 = new Dialog(this, R.style.CustomDialog);
        dialog2.setContentView(view2);
        dialog2.setCancelable(true);
        Window window2 = dialog2.getWindow();
        WindowManager.LayoutParams wlp2 = window2.getAttributes();
        wlp2.gravity = Gravity.BOTTOM;
        wlp2.width = WindowManager.LayoutParams.MATCH_PARENT;
        window2.setAttributes(wlp);
        window2.setWindowAnimations(R.style.dialog_anim);
        View closeIv2 = view2.findViewById(R.id.close_iv);
        listView2 = (ListView) view2.findViewById(R.id.lv_logistics_company);
        closeIv2.setOnClickListener(this);
    }

    //从server端获得所有订单数据，可能要查询几次
    @Override
    protected void initData() {
        if ((getIntent() != null) && (getIntent().getExtras() != null)) {
            order_id = getIntent().getExtras().getInt("orderinfo");
            source = getIntent().getExtras().getString("source");
            showIndeterminateProgressDialog(false);
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
                            String display = orderDetailBean.getStatus_display();
                            if ("已付款".equals(display)) {
                                addressLayout.setOnClickListener(OrderDetailActivity.this);
                            } else {
                                rightImage.setVisibility(View.GONE);
                                logisticsRightImage.setVisibility(View.GONE);
                            }
                            Log.i(TAG, "order_id " + order_id + " " + orderDetailBean.toString());

                            Subscription subscribe = ActivityModel.getInstance()
                                    .getLogisticCompany(order_id)
                                    .subscribeOn(Schedulers.io())
                                    .subscribe(new ServiceResponse<List<LogisticCompany>>() {
                                        @Override
                                        public void onNext(List<LogisticCompany> logisticCompanies) {
                                            CompanyAdapter adapter = new CompanyAdapter(logisticCompanies, getApplicationContext());
                                            listView.setAdapter(adapter);
                                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                @Override
                                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                    String code = logisticCompanies.get(position).getCode();
                                                    ActivityModel.getInstance()
                                                            .changeLogisticCompany(orderDetail.getUser_adress().getId(), order_id + "", code)
                                                            .subscribeOn(Schedulers.io())
                                                            .subscribe(new ServiceResponse<ResultBean>() {
                                                                @Override
                                                                public void onNext(ResultBean resultBean) {
                                                                    switch (resultBean.getCode()) {
                                                                        case 0:
                                                                            logisticsTv.setText(logisticCompanies.get(position).getName());
                                                                            break;
                                                                    }
                                                                    JUtils.Toast(resultBean.getInfo());
                                                                    dialog.dismiss();
                                                                }

                                                                @Override
                                                                public void onError(Throwable e) {
                                                                    JUtils.Toast(e.getMessage());
                                                                    dialog.dismiss();
                                                                }
                                                            });
                                                }
                                            });
                                        }
                                    });
                            addSubscription(subscribe);
                            hideIndeterminateProgressDialog();
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
        PayAdapter payAdapter = new PayAdapter(orderDetailBean.getExtras().getChannels(), this);
        listView2.setAdapter(payAdapter);
        int status = orderDetailBean.getStatus();
        if (!"退款中".equals(orderDetailBean.getStatus_display()) && !"退货中".equals(orderDetailBean.getStatus_display())) {
            setStatusView(status);
            if (status == 2 || status == 3 || status == 4 || status == 5) {
                Subscription subscribe = TradeModel.getInstance()
                        .getRedBag(tid)
                        .subscribeOn(Schedulers.io())
                        .subscribe(new ServiceResponse<RedBagBean>() {
                            @Override
                            public void onNext(RedBagBean redBagBean) {
                                if (redBagBean.getCode() == 0) {
                                    if (redBagBean.getShare_times_limit() > 0) {
                                        redBagLayout.setVisibility(View.VISIBLE);
                                        redBagEntity = redBagBean;
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                while (flag) {
                                                    SystemClock.sleep(1500);
                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            if (flag && !alive) {
                                                                redBagLayout.setVisibility(View.VISIBLE);
                                                            }

                                                        }
                                                    });
                                                }
                                            }
                                        }).start();

                                    } else {
                                        redBagLayout.setVisibility(View.GONE);
                                    }
                                }
                            }
                        });
                addSubscription(subscribe);
            }
        }
        tx_order_id.setText(orderDetailBean.getTid());
        tx_custom_name.setText(orderDetailBean.getUser_adress().getReceiver_name());
        tx_custom_address.setText(orderDetailBean.getUser_adress().getReceiver_state()
                + orderDetailBean.getUser_adress().getReceiver_city()
                + orderDetailBean.getUser_adress().getReceiver_district()
                + orderDetailBean.getUser_adress().getReceiver_address());
        tx_order_totalfee.setText("¥" + orderDetailBean.getTotal_fee());
        tx_order_discountfee.setText("-¥" + orderDetailBean.getDiscount_fee());
        tx_order_postfee.setText("¥" + orderDetailBean.getPost_fee());
        tx_order_payment.setText("¥" + orderDetailBean.getPayment());
        timeText.setText(orderDetailBean.getCreated().replace("T", " "));
        if (orderDetailBean.getLogistics_company() != null) {
            logisticsTv.setText(orderDetailBean.getLogistics_company().getName());
        }
        JUtils.Log(TAG, "crt time " + orderDetailBean.getCreated());

        String channel = orderDetailBean.getChannel();
        if ("".equals(channel)) {
            relativeLayout.setVisibility(View.GONE);
        } else if (channel.contains("budget")) {
            imageView.setImageResource(R.drawable.icon_xiaolu);
        } else if (channel.contains("alipay")) {
            imageView.setImageResource(R.drawable.alipay);
        } else if (channel.contains("wx")) {
            imageView.setImageResource(R.drawable.wx);
        } else {
            relativeLayout.setVisibility(View.GONE);
        }
        OrderGoodsListAdapter mGoodsAdapter = new OrderGoodsListAdapter(this, orderDetailBean);
        lv_goods.setAdapter(mGoodsAdapter);
        setListViewHeightBasedOnChildren(lv_goods);
        scrollView.scrollTo(0, 0);
    }

    private void setStatusView(int status) {
        switch (status) {
            case 5:
                setView5();
                break;
            case 4:
                setView4();
                break;
            case 3:
                setView3();
                break;
            case 2:
                setView2();
                break;
            case 0:
            case 1:
                hsv.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void setView2() {
        tv1.setTextColor(getResources().getColor(R.color.text_color_32));
        tv2.setTextColor(getResources().getColor(R.color.colorAccent));
        imageView1.setImageResource(R.drawable.status_black);
        imageView2.setImageResource(R.drawable.state_in);
        line2.setBackgroundColor(getResources().getColor(R.color.text_color_32));
        hsv.setVisibility(View.VISIBLE);
    }

    private void setView3() {
        setView2();
        tv2.setTextColor(getResources().getColor(R.color.text_color_32));
        tv3.setTextColor(getResources().getColor(R.color.colorAccent));
        imageView2.setImageResource(R.drawable.status_black);
        imageView3.setImageResource(R.drawable.state_in);
        line3.setBackgroundColor(getResources().getColor(R.color.text_color_32));
    }

    private void setView4() {
        setView3();
        tv3.setTextColor(getResources().getColor(R.color.text_color_32));
        tv4.setTextColor(getResources().getColor(R.color.colorAccent));
        imageView3.setImageResource(R.drawable.status_black);
        imageView4.setImageResource(R.drawable.state_in);
        line4.setBackgroundColor(getResources().getColor(R.color.text_color_32));
    }

    private void setView5() {
        setView4();
        tv4.setTextColor(getResources().getColor(R.color.text_color_32));
        tv5.setTextColor(getResources().getColor(R.color.colorAccent));
        imageView4.setImageResource(R.drawable.status_black);
        imageView5.setImageResource(R.drawable.state_in);
        line5.setBackgroundColor(getResources().getColor(R.color.text_color_32));
        line6.setBackgroundColor(getResources().getColor(R.color.text_color_32));
    }

    private void showProcBtn(OrderDetailBean orderDetailBean) {
        Log.d(TAG, "state " + orderDetailBean.getStatus());
        try {
            int state = orderDetailBean.getStatus();
            switch (state) {
                case XlmmConst.ORDER_STATE_WAITPAY: {
                    Log.i(TAG, "wait pay lefttime show");

                    rlayout_order_lefttime.setVisibility(View.VISIBLE);
                    relativeLayout.setVisibility(View.GONE);
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
                                        btn_proc.setImageResource(R.drawable.pay_order_not);
                                    }
                                });
                    } else {
                        JUtils.Log(TAG, "left time 0");
                        btn_proc.setClickable(false);
                        btn_proc.setImageResource(R.drawable.pay_order_not);
                    }

                    ImageView btn_order_cancel = (ImageView) findViewById(R.id.btn_order_cancel);
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
                    dialog2.show();
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
                bundle.putString("address1", orderDetail.getUser_adress().getReceiver_state()
                        + orderDetail.getUser_adress().getReceiver_city()
                        + orderDetail.getUser_adress().getReceiver_district());
                bundle.putString("address2", orderDetail.getUser_adress().getReceiver_address());
                bundle.putString("receiver_state", orderDetail.getUser_adress().getReceiver_state());
                bundle.putString("receiver_city", orderDetail.getUser_adress().getReceiver_city());
                bundle.putString("receiver_district", orderDetail.getUser_adress().getReceiver_district());
                bundle.putString("address_id", orderDetail.getUser_adress().getId() + "");
                bundle.putString("referal_trade_id", orderDetail.getId() + "");
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.logistics_layout:
                if ("已付款".equals(orderDetail.getStatus_display())) {
                    dialog.show();
                }
                break;
            case R.id.close_iv:
                if (dialog2.isShowing()) {
                    dialog2.dismiss();
                }
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                break;
            case R.id.red_bag:
                OnekeyShare oks = new OnekeyShare();
                oks.disableSSOWhenAuthorize();
                oks.setTitle(redBagEntity.getTitle());
                oks.setTitleUrl(redBagEntity.getShare_link());
                oks.setText(redBagEntity.getDescription());
                oks.setImageUrl(redBagEntity.getPost_img());
                oks.setUrl(redBagEntity.getShare_link());
                oks.setShareContentCustomizeCallback(new ShareContentCustom(redBagEntity.getDescription()));
                oks.show(this);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(this.getClass().getSimpleName());
        MobclickAgent.onResume(this);
        Subscription subscription = TradeModel.getInstance()
                .getOrderDetailBean(order_id)
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<OrderDetailBean>() {
                    @Override
                    public void onNext(OrderDetailBean orderDetailBean) {
                        orderDetail = orderDetailBean;
                        fillDataToView(orderDetailBean);
                    }
                });
        addSubscription(subscription);
    }

    private void payNow(String channel) {
        Subscription subscription = TradeModel.getInstance()
                .orderPayWithChannel(order_id, channel)
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<PayInfoBean>() {
                    @Override
                    public void onNext(PayInfoBean payInfoBean) {
                        super.onNext(payInfoBean);
                        Intent intent = new Intent();
                        String packageName = getPackageName();
                        ComponentName componentName = new ComponentName(packageName,
                                packageName + ".wxapi.WXPayEntryActivity");
                        intent.setComponent(componentName);
                        intent.putExtra(PaymentActivity.EXTRA_CHARGE,
                                new Gson().toJson(payInfoBean.getCharge()));
                        startActivityForResult(intent, REQUEST_CODE_PAYMENT);
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        JUtils.Toast("支付请求失败!");
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
                    JUtils.Toast("已取消支付!");
                } else if (result.equals("success")) {
                    JUtils.Toast("支付成功！");
                    Intent intent = new Intent(this, RedBagActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("tid", orderDetail.getTid());
                    intent.putExtras(bundle);
                    startActivity(intent);
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

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_SCROLL:
            case MotionEvent.ACTION_MOVE:
                redBagLayout.setVisibility(View.GONE);
                alive = true;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(1500);
                        alive = false;
                    }
                }).start();
                break;
        }
        return false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onStop() {
        flag = false;
        super.onStop();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String channel = orderDetail.getExtras().getChannels().get(position).getId();
        dialog2.dismiss();
        payNow(channel);
    }

    class ShareContentCustom implements ShareContentCustomizeCallback {

        private String text;

        public ShareContentCustom(String text) {
            this.text = text;
        }

        @Override
        public void onShare(Platform platform, Platform.ShareParams paramsToShare) {
            if (WechatMoments.NAME.equals(platform.getName())) {
                paramsToShare.setTitle(text);
            }
        }
    }

}
