package com.jimei.xiaolumeimei.ui.activity.trade;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jimei.library.utils.JUtils;
import com.jimei.library.widget.LogImageView;
import com.jimei.library.widget.LogMsgView;
import com.jimei.library.widget.XlmmTitleView;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.LogisticsBean;
import com.jimei.xiaolumeimei.model.TradeModel;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.umeng.analytics.MobclickAgent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import okhttp3.ResponseBody;
import rx.Subscription;

/**
 * Created by itxuye(www.itxuye.com) on 2016/01/22.
 * <p>
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class WriteLogisticsInfoActivty extends BaseSwipeBackCompatActivity
        implements View.OnClickListener {

    String TAG = "WriteLogisticsInfoActivty";

    @Bind(R.id.title_view)
    XlmmTitleView titleView;
    @Bind(R.id.et_logistics_company)
    EditText et_logistics_company;
    @Bind(R.id.et_logistics_number)
    EditText et_logistics_number;
    @Bind(R.id.btn_commit)
    Button btn_commit;
    @Bind(R.id.tv_name)
    TextView nameTv;
    @Bind(R.id.tv_phone)
    TextView phoneTv;
    @Bind(R.id.tv_address)
    TextView addressTv;
    @Bind(R.id.tv_reason)
    TextView reasonTv;
    @Bind(R.id.logistic_layout)
    LinearLayout logisticLayout;
    @Bind(R.id.msg_layout)
    LinearLayout msgLayout;
    @Bind(R.id.write_layout)
    LinearLayout writeLayout;
    @Bind(R.id.logistic_name)
    TextView logistic_name;
    @Bind(R.id.logistic_num)
    TextView logistic_num;
    @Bind(R.id.log_image_layout)
    LinearLayout log_image_layout;
    @Bind(R.id.log_msg_layout)
    LinearLayout log_msg_layout;
    @Bind(R.id.tv_order_last_time)
    TextView tv_order_last_time;
    @Bind(R.id.tv_order_last_state)
    TextView tv_order_last_state;
    @Bind(R.id.rl_scan)
    RelativeLayout scanLayout;
    private String address;
    String company;
    int goods_id;
    private boolean flag;
    private String company_name;
    private String packetid;
    private int rid;

    @Override
    protected void setListener() {
        btn_commit.setOnClickListener(this);
        scanLayout.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        if (address.contains("，")) {
            String[] split = address.split("，");
            for (String s : split) {
                if (s.contains("市")) {
                    addressTv.setText("收货地址:" + s);
                } else if (s.contains("小鹿")) {
                    nameTv.setText("收件人:小鹿售后");
                } else {
                    phoneTv.setText("联系电话:" + s);
                }
            }
        } else if (address.contains(",")) {
            String[] split = address.split(",");
            for (String s : split) {
                if (s.contains("市")) {
                    addressTv.setText("收货地址:" + s);
                } else if (s.contains("小鹿")) {
                    nameTv.setText("收件人:小鹿售后");
                } else {
                    phoneTv.setText("联系电话:" + s);
                }
            }
        }
        if (flag) {
            Subscription subscribe = TradeModel.getInstance()
                    .getRefundLogistic(rid, packetid, company_name)
                    .subscribe(new ServiceResponse<LogisticsBean>() {
                        @Override
                        public void onNext(LogisticsBean logisticsBean) {
                            if ("".equals(logisticsBean.getName())) {
                                logistic_name.setText("暂时无法查询物流信息,请稍后再试");
                            } else {
                                logistic_name.setText(logisticsBean.getName());
                            }
                            if ("".equals(logisticsBean.getOrder())) {
                                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                logistic_num.setText(df.format(new Date()));
                            } else {
                                logistic_num.setText("快递单号:  " + logisticsBean.getOrder());
                            }
                            if (logisticsBean.getData().size() > 0) {
                                for (int i = 0; i < logisticsBean.getData().size(); i++) {
                                    String content = logisticsBean.getData().get(i).getContent();
                                    String time = logisticsBean.getData().get(i).getTime().replace("T", " ");
                                    if (i == 0) {
                                        tv_order_last_time.setText(time);
                                        tv_order_last_state.setText(content);
                                    } else {
                                        log_image_layout.addView(new LogImageView(WriteLogisticsInfoActivty.this));
                                        LogMsgView logMsgView = new LogMsgView(WriteLogisticsInfoActivty.this);
                                        logMsgView.setMsg(content);
                                        logMsgView.setTime(time);
                                        log_msg_layout.addView(logMsgView);
                                    }
                                }
                            } else {
                                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                tv_order_last_time.setText(df.format(new Date()));
                                tv_order_last_state.setText("暂时无法查询物流信息,请稍后再试");
                            }
                            logisticLayout.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onError(Throwable e) {
                            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            logistic_name.setText(df.format(new Date()));
                            logistic_num.setText("暂时无法查询物流信息,请稍后再试");
                            msgLayout.setVisibility(View.GONE);
                            logisticLayout.setVisibility(View.VISIBLE);
                        }
                    });
            addSubscription(subscribe);
        }
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        goods_id = extras.getInt("goods_id");
        address = extras.getString("address");
        flag = extras.getBoolean("flag");
        company_name = extras.getString("company_name", "");
        packetid = extras.getString("packetid", "");
        rid = extras.getInt("rid");
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_write_logistics_info;
    }

    @Override
    protected void initViews() {
        if (!flag) {
            writeLayout.setVisibility(View.VISIBLE);
        } else {
            titleView.setName("查询物流信息");
        }
        et_logistics_company.setOnTouchListener((v, event) -> {
            if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                Log.d(TAG, "choose logistics commpay");
                Intent intent = new Intent(WriteLogisticsInfoActivty.this,
                        ChooseLogisticsCompanyActivity.class);

                Log.d(TAG, " to ChooseLogisticsCompanyActivity");
                startActivityForResult(intent, 1);
            }
            return false;
        });
        SpannableStringBuilder builder = new SpannableStringBuilder(reasonTv.getText().toString());
        //ForegroundColorSpan 为文字前景色，BackgroundColorSpan为文字背景色
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(getResources().getColor(R.color.colorAccent));
        builder.setSpan(colorSpan, 20,35, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        reasonTv.setText(builder);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_scan:
                startActivityForResult(new Intent(this, CommonScanActivity.class), 2);
                break;
            case R.id.btn_commit:
                commit_logistics_info();
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == 1) && (data != null)) {
            // data contains result
            company = data.getExtras().getString("company");
            Log.d(TAG, "onActivityResult company" + company);
            et_logistics_company.setText(company);
        }
        if (requestCode == 2 && data != null) {
            String number = data.getExtras().getString("number");
            et_logistics_number.setText(number);
        }
    }

    private void commit_logistics_info() {
        Log.i(TAG, "commit_logistics_info goods_id  "
                + goods_id
                + " "
                + company
                + " "
                + et_logistics_number.getText().toString().trim());

        if ((company == null) || (company.isEmpty())
                || (et_logistics_number.getText().toString().trim() == null)
                || (et_logistics_number.getText().toString().trim().isEmpty())) {
            JUtils.Toast("提交物流信息为空，请重试！");
            return;
        }

        Subscription subscription = TradeModel.getInstance()
                .commit_logistics_info(goods_id, company,
                        et_logistics_number.getText().toString().trim())
                .subscribe(new ServiceResponse<ResponseBody>() {
                    @Override
                    public void onNext(ResponseBody resp) {
                        JUtils.Toast("提交物流信息成功，收货后我们会尽快为您处理退款！");
                        Log.i(TAG, "commit_logistics_info " + resp.toString());
                        Intent intent = new Intent(WriteLogisticsInfoActivty.this,
                                RefundDetailActivity.class);
                        intent.putExtra("flag", true);
                        setResult(0, intent);
                        finish();
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.e(TAG, " error:commit_logistics_info " + e.toString());
                        JUtils.Toast("提交信息失败，请重试！");
                        super.onError(e);
                    }
                });
        addSubscription(subscription);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(this.getClass().getSimpleName());
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
        MobclickAgent.onPause(this);
    }
}
