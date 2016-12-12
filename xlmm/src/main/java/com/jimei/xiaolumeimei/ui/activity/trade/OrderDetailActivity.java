package com.jimei.xiaolumeimei.ui.activity.trade;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
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

import com.google.gson.Gson;
import com.jimei.library.utils.JUtils;
import com.jimei.library.widget.CountDownView;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.XlmmApp;
import com.jimei.xiaolumeimei.adapter.CompanyAdapter;
import com.jimei.xiaolumeimei.adapter.OrderGoodsListAdapter;
import com.jimei.xiaolumeimei.adapter.PayAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.base.CommonWebViewActivity;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.entities.AllOrdersBean;
import com.jimei.xiaolumeimei.entities.OrderDetailBean;
import com.jimei.xiaolumeimei.entities.PayInfoBean;
import com.jimei.xiaolumeimei.entities.RedBagBean;
import com.jimei.xiaolumeimei.entities.event.RefreshOrderListEvent;
import com.jimei.xiaolumeimei.model.TradeModel;
import com.jimei.xiaolumeimei.ui.activity.user.WaitSendAddressActivity;
import com.jimei.xiaolumeimei.utils.JumpUtils;
import com.jimei.xiaolumeimei.utils.pay.PayUtils;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.Bind;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import cn.sharesdk.wechat.moments.WechatMoments;
import okhttp3.ResponseBody;
import rx.Subscription;

public class OrderDetailActivity extends BaseSwipeBackCompatActivity
        implements View.OnClickListener, View.OnTouchListener, AdapterView.OnItemClickListener {
    public static final int HAND_MSG = 6;
    String TAG = "OrderDetailActivity";
    @Bind(R.id.btn_order_pay)
    ImageView btn_order_pay;
    @Bind(R.id.btn_order_cancel)
    ImageView btn_order_cancel;
    @Bind(R.id.rlayout_order_lefttime)
    LinearLayout rlayout_order_lefttime;
    @Bind(R.id.tx_order_id)
    TextView tx_order_id;
    @Bind(R.id.tx_custom_name)
    TextView tx_custom_name;
    @Bind(R.id.tx_custom_phone)
    TextView tx_custom_phone;
    @Bind(R.id.tx_custom_mobile)
    TextView tx_custom_mobile;
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
    @Bind(R.id.tx_order_payment2)
    TextView tx_order_payment2;
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
    @Bind(R.id.team_buy)
    LinearLayout teamLayout;
    @Bind(R.id.count_view)
    CountDownView mCountDownView;

    Handler mHandler;
    ListView listView;
    ListView listView2;

    int order_id = 0;
    OrderDetailBean orderDetail;
    String tid;
    private Dialog dialog;
    private Dialog dialog2;
    private RedBagBean redBagEntity;
    private boolean hasRedBag = false;
    private boolean bonded_goods = false;

    @Override
    protected void setListener() {
        btn_order_pay.setOnClickListener(this);
        btn_order_cancel.setOnClickListener(this);
        logisticsLayout.setOnClickListener(this);
        redBagLayout.setOnClickListener(this);
        scrollView.setOnTouchListener(this);
        listView2.setOnItemClickListener(this);
        teamLayout.setOnClickListener(this);
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        order_id = extras.getInt("orderinfo");
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_order_detail;
    }


    @Override
    public void getIntentUrl(Uri uri) {
        order_id = Integer.valueOf(uri.getQueryParameter("trade_id"));
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

    public void resendMsg() {
        mHandler.removeMessages(HAND_MSG);
        Message msg = mHandler.obtainMessage(HAND_MSG);
        mHandler.sendMessageDelayed(msg, 1500);
    }

    //从server端获得所有订单数据，可能要查询几次
    @Override
    protected void initData() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case HAND_MSG:
                        redBagLayout.setVisibility(View.VISIBLE);
                        break;
                }
            }
        };
        if (order_id != -1) {
            showIndeterminateProgressDialog(false);
            addSubscription(TradeModel.getInstance()
                    .getOrderDetailBean(order_id)
                    .subscribe(orderDetailBean -> {
                        tid = orderDetailBean.getTid();
                        showProcBtn(orderDetailBean);
                        scrollView.scrollTo(0, 0);
                        if (orderDetailBean.isCan_change_address()) {
                            addressLayout.setOnClickListener(OrderDetailActivity.this);
                        } else {
                            rightImage.setVisibility(View.GONE);
                            logisticsRightImage.setVisibility(View.GONE);
                        }
                        Log.i(TAG, "order_id " + order_id + " " + orderDetailBean.toString());
                        addSubscription(TradeModel.getInstance()
                                .getLogisticCompany(order_id)
                                .subscribe(logisticCompanies -> {
                                    CompanyAdapter adapter = new CompanyAdapter(logisticCompanies, getApplicationContext());
                                    listView.setAdapter(adapter);
                                    listView.setOnItemClickListener((parent, view, position, id) -> {
                                        String code = logisticCompanies.get(position).getCode();
                                        addSubscription(TradeModel.getInstance()
                                                .changeLogisticCompany(orderDetail.getUser_adress().getId(), order_id + "", code)
                                                .subscribe(resultBean -> {
                                                    switch (resultBean.getCode()) {
                                                        case 0:
                                                            logisticsTv.setText(logisticCompanies.get(position).getName());
                                                            break;
                                                    }
                                                    JUtils.Toast(resultBean.getInfo());
                                                    dialog.dismiss();
                                                }, e -> {
                                                    JUtils.Toast(e.getMessage());
                                                    dialog.dismiss();
                                                }));
                                    });
                                }, e -> JUtils.Log(e.getMessage())));
                        hideIndeterminateProgressDialog();
                    }, e -> {
                        JUtils.Log(e.getMessage());
                        JUtils.Toast("订单详情获取失败!");
                        hideIndeterminateProgressDialog();
                    }));

        }
    }

    private void fillDataToView(OrderDetailBean orderDetailBean) {
        PayAdapter payAdapter = new PayAdapter(orderDetailBean.getExtras().getChannels(), this);
        listView2.setAdapter(payAdapter);
        OrderDetailBean.UserAdressBean user_adress = orderDetailBean.getUser_adress();
        int status = orderDetailBean.getStatus();
        if (!"退款中".equals(orderDetailBean.getStatus_display())
                && !"退货中".equals(orderDetailBean.getStatus_display())) {
            if (status == 2 || status == 3 || status == 4 || status == 5) {
                if (orderDetailBean.getOrder_type() == 3) {
                    addSubscription(TradeModel.getInstance()
                            .getTeamBuyBean(orderDetailBean.getTid())
                            .subscribe(teamBuyBean -> {
                                if (teamBuyBean.getStatus() != 2) {
                                    setStatusView(status);
                                }
                                teamLayout.setVisibility(View.VISIBLE);
                            }, e -> JUtils.Log(e.getMessage())));
                } else {
                    addSubscription(TradeModel.getInstance()
                            .getRedBag(tid)
                            .subscribe(redBagBean -> {
                                if (redBagBean.getCode() == 0) {
                                    if (redBagBean.getShare_times_limit() > 0) {
                                        hasRedBag = true;
                                        redBagLayout.setVisibility(View.VISIBLE);
                                        redBagEntity = redBagBean;
                                    } else {
                                        hasRedBag = false;
                                        redBagLayout.setVisibility(View.GONE);
                                    }
                                }
                            }, e -> JUtils.Log(e.getMessage())));
                }
            } else {
                setStatusView(status);
            }
        }
        tx_order_id.setText(orderDetailBean.getTid());
        tx_custom_name.setText(user_adress.getReceiver_name());
        tx_custom_mobile.setText(user_adress.getReceiver_mobile());
        tx_custom_phone.setText(user_adress.getReceiver_phone());
        tx_custom_address.setText(user_adress.getReceiver_state()
                + user_adress.getReceiver_city()
                + user_adress.getReceiver_district()
                + user_adress.getReceiver_address());
        tx_order_totalfee.setText("¥" + orderDetailBean.getTotal_fee());
        tx_order_discountfee.setText("-¥" + orderDetailBean.getDiscount_fee());
        tx_order_postfee.setText("¥" + orderDetailBean.getPost_fee());
        tx_order_payment.setText("¥" + orderDetailBean.getPay_cash());
        String format = new DecimalFormat("#.00").format(orderDetailBean.getPayment() - orderDetailBean.getPay_cash());
        if (format.startsWith(".")) {
            tx_order_payment2.setText("¥0" + format);
        } else {
            tx_order_payment2.setText("¥" + format);
        }

        timeText.setText(orderDetailBean.getCreated().replace("T", " "));
        if (orderDetailBean.getLogistics_company() != null) {
            logisticsTv.setText(orderDetailBean.getLogistics_company().getName());
        }
        JUtils.Log(TAG, "crt time " + orderDetailBean.getCreated());

        String channel = orderDetailBean.getChannel();
        if (channel.contains("budget")) {
            imageView.setImageResource(R.drawable.icon_xiaolu);
        } else if (channel.contains("alipay")) {
            imageView.setImageResource(R.drawable.alipay);
        } else if (channel.contains("wx")) {
            imageView.setImageResource(R.drawable.wx);
        } else {
            relativeLayout.setVisibility(View.GONE);
        }
        OrderGoodsListAdapter mGoodsAdapter = new OrderGoodsListAdapter(this, orderDetailBean, orderDetailBean.isCan_refund());
        lv_goods.setAdapter(mGoodsAdapter);
        ArrayList<AllOrdersBean.ResultsEntity.OrdersEntity> orders = orderDetailBean.getOrders();
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).is_bonded_goods()) {
                bonded_goods = true;
            }
        }
        String no = user_adress.getIdentification_no();
        if (no == null || no.length() < 18) {
            if (bonded_goods) {
                new AlertDialog.Builder(this)
                        .setTitle("提示")
                        .setMessage("订单中包含进口保税区发货商品，根据海关监管要求，需要提供收货人身份证号码。此信息加密保存，只用于此订单海关通关。")
                        .setPositiveButton("确认", (dialog1, which) -> dialog1.dismiss())
                        .setCancelable(false)
                        .show();
            }
        }
        setListViewHeightBasedOnChildren(lv_goods);
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
                case XlmmConst.ORDER_STATE_WAITPAY:
                    Log.i(TAG, "wait pay lefttime show");
                    rlayout_order_lefttime.setVisibility(View.VISIBLE);
                    relativeLayout.setVisibility(View.GONE);
                    mCountDownView.start(calcLeftTime(orderDetailBean.getCreated()), CountDownView.TYPE_MINUTE);
                    mCountDownView.setOnEndListener(view -> {
                        if (btn_order_pay == null) {
                            btn_order_pay = (ImageView) findViewById(R.id.btn_order_pay);
                        }
                        assert btn_order_pay != null;
                        btn_order_pay.setClickable(false);
                        btn_order_pay.setImageResource(R.drawable.pay_order_not);

                    });
                    if (calcLeftTime(orderDetailBean.getCreated()) <= 0) {
                        JUtils.Log(TAG, "left time 0");
                        btn_order_pay.setClickable(false);
                        btn_order_pay.setImageResource(R.drawable.pay_order_not);
                    }
                    break;
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
            case R.id.btn_order_pay:
                if (orderDetail != null && orderDetail.getStatus() == XlmmConst.ORDER_STATE_WAITPAY) {
                    JUtils.Log(TAG, "onClick paynow");
                    dialog2.show();
                }
                break;
            case R.id.team_buy:
                SharedPreferences preferences = XlmmApp.getmContext().getSharedPreferences("APICLIENT", Context.MODE_PRIVATE);
                String baseUrl = "https://m.xiaolumeimei.com/mall/order/spell/group/" + tid + "?from_page=order_detail";
                if (!TextUtils.isEmpty(preferences.getString("BASE_URL", ""))) {
                    baseUrl = "https://" + preferences.getString("BASE_URL", "") + "/mall/order/spell/group/" + tid + "?from_page=order_detail";
                }
                JumpUtils.jumpToWebViewWithCookies(mContext,
                        baseUrl, -1, CommonWebViewActivity.class, "查看拼团详情", false);
                break;
            case R.id.btn_order_cancel:
                JUtils.Log(TAG, "onClick cancel");
                cancel_order();
                break;
            case R.id.address:
                if (orderDetail != null) {
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
                    bundle.putBoolean("is_bonded_goods", bonded_goods);
                    bundle.putString("idNo", orderDetail.getUser_adress().getIdentification_no());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                break;
            case R.id.logistics_layout:
                if (orderDetail != null && "已付款".equals(orderDetail.getStatus_display())) {
                    new AlertDialog.Builder(this)
                            .setTitle("提示")
                            .setMessage("非服装类商品是由供应商直接发货，只能尽量满足您选择的快递公司，" +
                                    "如需确认能否满足您的快递需求，请联系客服。")
                            .setPositiveButton("确认", (dialog1, which) -> {
                                dialog1.dismiss();
                                dialog.show();
                            })
                            .show();
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
                if (redBagEntity != null) {
                    OnekeyShare oks = new OnekeyShare();
                    oks.disableSSOWhenAuthorize();
                    oks.setTitle(redBagEntity.getTitle());
                    oks.setTitleUrl(redBagEntity.getShare_link());
                    oks.setText(redBagEntity.getDescription());
                    oks.setImageUrl(redBagEntity.getPost_img());
                    oks.setUrl(redBagEntity.getShare_link());
                    oks.setShareContentCustomizeCallback(new ShareContentCustom(redBagEntity.getDescription()));
                    oks.show(this);
                }
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
                .subscribe(new ServiceResponse<OrderDetailBean>() {
                    @Override
                    public void onNext(OrderDetailBean orderDetailBean) {
                        orderDetail = orderDetailBean;
                        tid = orderDetailBean.getTid();
                        fillDataToView(orderDetailBean);
                    }
                });
        addSubscription(subscription);
    }

    private void payNow(String channel) {
        showIndeterminateProgressDialog(false);
        Subscription subscription = TradeModel.getInstance()
                .orderPayWithChannel(order_id, channel)
                .subscribe(new ServiceResponse<PayInfoBean>() {
                    @Override
                    public void onNext(PayInfoBean payInfoBean) {
                        PayUtils.createPayment(OrderDetailActivity.this, new Gson().toJson(payInfoBean.getCharge()));
                    }

                    @Override
                    public void onError(Throwable e) {
                        JUtils.Toast("支付请求失败!");
                        hideIndeterminateProgressDialog();
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
                .subscribe(new ServiceResponse<ResponseBody>() {
                    @Override
                    public void onNext(ResponseBody responseBody) {
                        super.onNext(responseBody);
                        try {
                            finish();
                            EventBus.getDefault().post(new RefreshOrderListEvent());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
        addSubscription(subscription);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //支付页面返回处理
        if (requestCode == PayUtils.REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                EventBus.getDefault().post(new RefreshOrderListEvent());
                hideIndeterminateProgressDialog();
                String result = data.getExtras().getString("pay_result");
                String errorMsg = data.getExtras().getString("error_msg"); // 错误信息
                String extraMsg = data.getExtras().getString("extra_msg"); // 错误信息
                if (result != null) {
                    switch (result) {
                        case "cancel":
                            JUtils.Toast("已取消支付!");
                            break;
                        case "success":
                            JUtils.Toast("支付成功！");
                            Intent intent = new Intent(this, RedBagActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("tid", orderDetail.getTid());
                            intent.putExtras(bundle);
                            startActivity(intent);
                            finish();
                            break;
                        default:
                            showMsg(result, errorMsg, extraMsg);
                            break;
                    }
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
                if (hasRedBag) {
                    resendMsg();
                }
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
    protected void onDestroy() {
        mHandler.removeMessages(HAND_MSG);
        super.onDestroy();
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
