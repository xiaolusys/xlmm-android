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
import android.widget.TextView;

import butterknife.Bind;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.model.TradeModel;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.umeng.analytics.MobclickAgent;

import okhttp3.ResponseBody;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye(www.itxuye.com) on 2016/01/22.
 * <p>
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class WriteLogisticsInfoActivty extends BaseSwipeBackCompatActivity
        implements View.OnClickListener {

    String TAG = "WriteLogisticsInfoActivty";

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
    private String address;
    String company;
    int goods_id;

    @Override
    protected void setListener() {
        btn_commit.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        String[] split = address.split("，");
        for (String s : split) {
            if (s.contains("市")) {
                addressTv.setText(s);
            } else if (s.contains("小鹿")) {
                nameTv.setText("收件人:小鹿售后");
            } else {
                phoneTv.setText("联系电话:" + s);
            }
        }
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        goods_id = extras.getInt("goods_id");
        address = extras.getString("address");
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_write_logistics_info;
    }

    @Override
    protected void initViews() {
        et_logistics_company.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    Log.d(TAG, "choose logistics commpay");
                    Intent intent = new Intent(WriteLogisticsInfoActivty.this,
                            ChooseLogisticsCompanyActivity.class);

                    Log.d(TAG, " to ChooseLogisticsCompanyActivity");
                    startActivityForResult(intent, 1);
                }
                return false;
            }
        });
        SpannableStringBuilder builder = new SpannableStringBuilder(reasonTv.getText().toString());
        //ForegroundColorSpan 为文字前景色，BackgroundColorSpan为文字背景色
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(getResources().getColor(R.color.colorAccent));
        builder.setSpan(colorSpan, 20, reasonTv.getText().toString().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        reasonTv.setText(builder);

    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

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
                .subscribeOn(Schedulers.io())
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
