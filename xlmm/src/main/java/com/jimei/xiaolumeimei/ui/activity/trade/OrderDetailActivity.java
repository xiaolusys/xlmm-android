package com.jimei.xiaolumeimei.ui.activity.trade;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
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
import com.jimei.xiaolumeimei.entities.LogisticCompany;
import com.jimei.xiaolumeimei.entities.OrderDetailBean;
import com.jimei.xiaolumeimei.entities.PackageBean;
import com.jimei.xiaolumeimei.entities.ResultBean;
import com.jimei.xiaolumeimei.model.ActivityModel;
import com.jimei.xiaolumeimei.model.TradeModel;
import com.jimei.xiaolumeimei.ui.activity.user.WaitSendAddressActivity;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.pingplusplus.android.PaymentActivity;
import com.umeng.analytics.MobclickAgent;

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
    ListView listView;
    @Bind(R.id.rl_pay)
    RelativeLayout relativeLayout;
    @Bind(R.id.iv_pay)
    ImageView imageView;
    @Bind(R.id.line_1)
    ImageView line1;
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

    private ArrayList<PackageBean> packageBeanList;
    int order_id = 0;
    OrderDetailBean orderDetail;
    String source;
    String tid;
    private Dialog dialog;
    private OrderGoodsListAdapter mGoodsAdapter;

    @Override
    protected void setListener() {
        btn_proc.setOnClickListener(this);
        btn_order_cancel.setOnClickListener(this);
        logisticsLayout.setOnClickListener(this);
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
        dialog = new Dialog(this, R.style.dialog_style);
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
                            if ("已付款".equals(orderDetailBean.getStatus_display())) {
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
                                                                    changeDialogWindowState();
                                                                }

                                                                @Override
                                                                public void onError(Throwable e) {
                                                                    JUtils.Toast(e.getMessage());
                                                                    changeDialogWindowState();
                                                                }
                                                            });
                                                }
                                            });
                                        }
                                    });
                            addSubscription(subscribe);
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
        if (!"退款中".equals(orderDetailBean.getStatus_display())) {
            setStatusView(orderDetailBean.getStatus());
        }
        tx_order_id.setText("订单编号: " + orderDetailBean.getTid());
        tx_order_state.setText(orderDetailBean.getStatus_display());
        tx_custom_name.setText("姓名：" + orderDetailBean.getUser_adress().getReceiver_name());
        tx_custom_address.setText("地址：" + orderDetailBean.getUser_adress().getReceiver_state()
                + orderDetailBean.getUser_adress().getReceiver_city()
                + orderDetailBean.getUser_adress().getReceiver_district()
                + orderDetailBean.getUser_adress().getReceiver_address());
        tx_order_totalfee.setText("¥" + orderDetailBean.getTotal_fee());
        tx_order_discountfee.setText("¥" + orderDetailBean.getDiscount_fee());
        tx_order_postfee.setText("¥" + orderDetailBean.getPost_fee());
        tx_order_payment.setText("¥" + orderDetailBean.getPayment());
        timeText.setText("下单时间:" + orderDetailBean.getCreated().replace("T", "-"));
        if (orderDetailBean.getLogistics_company() != null) {
            logisticsTv.setText(orderDetailBean.getLogistics_company().getName());
        }
        JUtils.Log(TAG, "crt time " + orderDetailBean.getCreated());
        mGoodsAdapter = new OrderGoodsListAdapter(this);
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
        tv1.setTextColor(getResources().getColor(R.color.text_color_62));
        tv2.setTextColor(getResources().getColor(R.color.colorAccent));
        imageView1.setImageResource(R.drawable.state_oval);
        imageView2.setImageResource(R.drawable.state_last);
        line2.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        hsv.setVisibility(View.VISIBLE);
    }

    private void setView3() {
        setView2();
        tv2.setTextColor(getResources().getColor(R.color.text_color_62));
        tv3.setTextColor(getResources().getColor(R.color.colorAccent));
        imageView2.setImageResource(R.drawable.state_oval);
        imageView3.setImageResource(R.drawable.state_last);
        line3.setBackgroundColor(getResources().getColor(R.color.colorAccent));
    }

    private void setView4() {
        setView3();
        tv3.setTextColor(getResources().getColor(R.color.text_color_62));
        tv4.setTextColor(getResources().getColor(R.color.colorAccent));
        imageView3.setImageResource(R.drawable.state_oval);
        imageView4.setImageResource(R.drawable.state_last);
        line4.setBackgroundColor(getResources().getColor(R.color.colorAccent));
    }

    private void setView5() {
        setView4();
        tv4.setTextColor(getResources().getColor(R.color.text_color_62));
        tv5.setTextColor(getResources().getColor(R.color.colorAccent));
        imageView4.setImageResource(R.drawable.state_oval);
        imageView5.setImageResource(R.drawable.state_last);
        line5.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        line6.setBackgroundColor(getResources().getColor(R.color.colorAccent));
    }

    private void changeDialogWindowState() {
        if (dialog.isShowing()) {
            dialog.dismiss();
        } else {
            dialog.show();
        }
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
                    changeDialogWindowState();
                }
                break;
            case R.id.close_iv:
                changeDialogWindowState();
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
                        tx_custom_name.setText("姓名：" + orderDetailBean.getUser_adress().getReceiver_name());
                        tx_custom_address.setText("地址：" + orderDetailBean.getUser_adress().getReceiver_state()
                                + orderDetailBean.getUser_adress().getReceiver_city()
                                + orderDetailBean.getUser_adress().getReceiver_district()
                                + orderDetailBean.getUser_adress().getReceiver_address());
                        if (orderDetailBean.getLogistics_company() != null) {
                            String name = orderDetailBean.getLogistics_company().getName();
                            logisticsTv.setText(name);
                        }
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

    public class CompanyAdapter extends BaseAdapter {
        private List<LogisticCompany> logisticCompanies;
        private Context context;

        public CompanyAdapter(List<LogisticCompany> logisticCompanies, Context context) {
            this.logisticCompanies = logisticCompanies;
            this.context = context;
        }

        @Override
        public int getCount() {
            return logisticCompanies.size();
        }

        @Override
        public Object getItem(int position) {
            return logisticCompanies.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_logistics, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            String name = logisticCompanies.get(position).getName();
            if (name.contains("申通")) {
                holder.iconImg.setImageResource(R.drawable.icon_sto);
            } else if (name.contains("邮政")) {
                holder.iconImg.setImageResource(R.drawable.icon_ems);
            } else if (name.contains("韵达")) {
                holder.iconImg.setImageResource(R.drawable.icon_yunda);
            } else if (name.contains("小鹿")) {
                holder.iconImg.setImageResource(R.drawable.icon_xiaolu);
            }
            holder.nameTv.setText(name);
            return convertView;
        }

        private class ViewHolder {
            TextView nameTv;
            ImageView iconImg;

            public ViewHolder(View itemView) {
                nameTv = ((TextView) itemView.findViewById(R.id.name));
                iconImg = ((ImageView) itemView.findViewById(R.id.icon));
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
        MobclickAgent.onPause(this);
    }
}
