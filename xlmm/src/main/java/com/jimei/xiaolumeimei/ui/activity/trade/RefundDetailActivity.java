package com.jimei.xiaolumeimei.ui.activity.trade;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.entities.AllRefundsBean.ResultsEntity.StatusShaftBean;
import com.jimei.xiaolumeimei.entities.AllRefundsBean;
import com.jimei.xiaolumeimei.model.TradeModel;
import com.jimei.xiaolumeimei.utils.ViewUtils;
import com.jimei.xiaolumeimei.widget.RoundCornerImageView;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
import rx.schedulers.Schedulers;

public class RefundDetailActivity extends BaseSwipeBackCompatActivity
        implements View.OnClickListener {
    private static final String TAG = RefundDetailActivity.class.getSimpleName();
    @Bind(R.id.tv_order_id)
    TextView orderIdTv;
    @Bind(R.id.tv_status)
    TextView statusTv;
    @Bind(R.id.ll_return)
    LinearLayout returnLayout;
    @Bind(R.id.sdv)
    ImageView goodImageView;
    @Bind(R.id.tv_good_name)
    TextView goodNameTv;
    @Bind(R.id.tv_good_price)
    TextView goodPriceTv;
    @Bind(R.id.tv_good_size)
    TextView goodSizeTv;
    @Bind(R.id.tv_good_num)
    TextView goodNumTv;
    @Bind(R.id.num)
    TextView numTv;
    @Bind(R.id.price)
    TextView priceTv;
    @Bind(R.id.reason)
    TextView reasonTv;
    @Bind(R.id.tv_order_create_time)
    TextView createTimeTv;
    @Bind(R.id.tv_order_last_time)
    TextView lastTimeTv;
    @Bind(R.id.tv_order_last_state)
    TextView lastStateTv;
    @Bind(R.id.ll_last)
    LinearLayout lastLayout;
    @Bind(R.id.round_image)
    RoundCornerImageView imageView;
    @Bind(R.id.refund_type)
    TextView refundTypeTv;
    @Bind(R.id.refund_layout)
    LinearLayout refundLayout;
    @Bind(R.id.time)
    TextView timeTv;
    @Bind(R.id.name)
    TextView nameTv;
    @Bind(R.id.phone)
    TextView phoneTv;
    @Bind(R.id.address)
    TextView addressTv;
    @Bind(R.id.btn_write)
    Button writeBtn;
    @Bind(R.id.image_layout)
    RelativeLayout imageLayout;
    AllRefundsBean.ResultsEntity refundDetail;
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
    @Bind(R.id.line_2)
    ImageView lineImage2;
    @Bind(R.id.line_3)
    ImageView lineImage3;
    @Bind(R.id.line_4)
    ImageView lineImage4;
    @Bind(R.id.line_5)
    ImageView lineImage5;
    @Bind(R.id.line_6)
    ImageView lineImage6;
    @Bind(R.id.tv_1)
    TextView textView1;
    @Bind(R.id.tv_2)
    TextView textView2;
    @Bind(R.id.tv_3)
    TextView textView3;
    @Bind(R.id.tv_4)
    TextView textView4;
    @Bind(R.id.tv_5)
    TextView textView5;
    @Bind(R.id.status_layout)
    LinearLayout statusLayout;

    private int refund_state;
    private int goods_id;

    @Override
    protected void setListener() {
        writeBtn.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        if ((getIntent() != null) && (getIntent().getExtras() != null)) {
            refund_state = getIntent().getExtras().getInt("refund_state");
        }
        Log.d(TAG, "refund_state " + refund_state);
        if (refund_state == XlmmConst.REFUND_STATE_SELLER_AGREED) {
            returnLayout.setVisibility(View.VISIBLE);
        } else if (refund_state != XlmmConst.REFUND_STATE_BUYER_APPLY) {
            lastLayout.setVisibility(View.VISIBLE);
        }
        JUtils.Log(TAG, "initData goods_id " + goods_id);
        showIndeterminateProgressDialog(false);
        Subscription subscription = TradeModel.getInstance()
                .getRefundDetailBean(goods_id)
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<AllRefundsBean.ResultsEntity>() {
                    @Override
                    public void onNext(AllRefundsBean.ResultsEntity refundDetailBean) {
                        JUtils.Log(TAG, "getRefundDetailBean success ");
                        refundDetail = refundDetailBean;
                        fillDataToView(refundDetailBean);
                        Log.i(TAG, refundDetailBean.toString());
                        Log.i(TAG, "status " + refundDetailBean.getStatus());
                        if ((refund_state == XlmmConst.REFUND_STATE_SELLER_AGREED) || (refund_state
                                == XlmmConst.REFUND_STATE_BUYER_APPLY)) {
                            List<String> mDatas = new ArrayList<String>();
                            fillPicPath(mDatas, refundDetailBean.getProof_pic().toString());
                            Log.d(TAG, "proofpic " + refundDetailBean.getProof_pic());
                        }
                    }

                    @Override
                    public void onCompleted() {
                        hideIndeterminateProgressDialog();
                        super.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.e(TAG, "getRefundDetailBean error:, " + e.toString());
                        super.onError(e);
                    }
                });
        addSubscription(subscription);
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        goods_id = extras.getInt("goods_id");
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_refund_detail;
    }

    @Override
    protected void initViews() {
        Log.d(TAG, "goods_id " + goods_id);
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    private void fillDataToView(AllRefundsBean.ResultsEntity refundDetailBean) {
        JUtils.Log(TAG, "fillDataToView ");
        orderIdTv.setText(refundDetailBean.getRefund_no());
        statusTv.setText(refundDetailBean.getStatus_display());
        String address = refundDetailBean.getReturn_address();
        String[] split = address.split("，");
        for (String s : split) {
            if (s.contains("市")) {
                addressTv.setText(s);
            } else if (s.contains("小鹿")) {
                nameTv.setText("小鹿售后");
            } else {
                phoneTv.setText(s);
            }
        }
        ViewUtils.loadImgToImgView(getApplicationContext(), goodImageView,
                refundDetailBean.getPic_path());
        if (refundDetailBean.getTitle().length() >= 15) {
            goodNameTv.setText(refundDetailBean.getTitle().substring(0, 8) + "...");
        } else {
            goodNameTv.setText(refundDetailBean.getTitle());
        }
        goodPriceTv.setText("¥" + refundDetailBean.getPayment());
        goodSizeTv.setText("尺码：" + refundDetailBean.getSku_name());
        goodNumTv.setText("");
        timeTv.setText("下单时间:" + refundDetailBean.getCreated().replace("T", " "));
        numTv.setText(Integer.toString(refundDetailBean.getRefund_num()));
        priceTv.setText("¥" + refundDetailBean.getRefund_fee());
        reasonTv.setText(refundDetailBean.getReason());
        createTimeTv.setText(refundDetailBean.getCreated().replace("T", " "));
        lastTimeTv.setText(refundDetailBean.getModified().replace("T", " "));
        lastStateTv.setText(refundDetailBean.getStatus_display());
        if (refundDetailBean.getProof_pic().size() > 0) {
            ViewUtils.loadImgToImgView(getApplicationContext(), imageView,
                    refundDetailBean.getProof_pic().get(0));
        } else {
            imageLayout.setVisibility(View.GONE);
        }
        JUtils.Log(TAG, "crt time " + refundDetailBean.getCreated());
        String desc = refundDetailBean.getAmount_flow().getDesc();
        refundTypeTv.setText(desc);
        if (refundDetailBean.getStatus_display().equals("退款成功")) {
            refundLayout.setVisibility(View.VISIBLE);
        } else {
            refundLayout.setVisibility(View.GONE);
        }
        if ("拒绝退款".equals(refundDetailBean.getStatus_display()) || "没有退款".equals(refundDetailBean.getStatus_display()) ||
                "退款关闭".equals(refundDetailBean.getStatus_display())) {
            statusLayout.setVisibility(View.GONE);
        } else {
            showRefundStatus(refundDetailBean.getStatus_shaft());
        }
    }

    private void showRefundStatus(List<StatusShaftBean> status_shaft) {
        if (status_shaft.size() > 1) {
            String display = status_shaft.get(status_shaft.size() - 1).getStatus_display();
            if ("同意申请".equals(display)) {
                setView1();
            } else if ("退货待收".equals(display)) {
                setView2();
            } else if ("等待返款".equals(display)) {
                setView3();
            } else if ("退款成功".equals(display)) {
                setView4();
            }
        }
    }

    private void setView4() {
        setView3();
        textView4.setTextColor(getResources().getColor(R.color.text_color_62));
        textView5.setTextColor(getResources().getColor(R.color.colorAccent));
        imageView4.setImageResource(R.drawable.state_oval);
        imageView5.setImageResource(R.drawable.state_last);
        lineImage5.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        lineImage6.setBackgroundColor(getResources().getColor(R.color.colorAccent));
    }

    private void setView3() {
        setView2();
        textView3.setTextColor(getResources().getColor(R.color.text_color_62));
        textView4.setTextColor(getResources().getColor(R.color.colorAccent));
        imageView3.setImageResource(R.drawable.state_oval);
        imageView4.setImageResource(R.drawable.state_last);
        lineImage4.setBackgroundColor(getResources().getColor(R.color.colorAccent));
    }

    private void setView2() {
        setView1();
        textView2.setTextColor(getResources().getColor(R.color.text_color_62));
        textView3.setTextColor(getResources().getColor(R.color.colorAccent));
        imageView2.setImageResource(R.drawable.state_oval);
        imageView3.setImageResource(R.drawable.state_last);
        lineImage3.setBackgroundColor(getResources().getColor(R.color.colorAccent));
    }

    private void setView1() {
        textView1.setTextColor(getResources().getColor(R.color.text_color_62));
        textView2.setTextColor(getResources().getColor(R.color.colorAccent));
        imageView1.setImageResource(R.drawable.state_oval);
        imageView2.setImageResource(R.drawable.state_last);
        lineImage2.setBackgroundColor(getResources().getColor(R.color.colorAccent));
    }


    private void fillPicPath(List<String> mDatas, String pics) {
        if ((null == pics) || (pics.equals("")) || (pics.equals("[]"))) return;
        String[] strArray = null;
        strArray = pics.split(",");
        for (int i = 0; i < strArray.length; i++) {
            mDatas.add(strArray[i]);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_write:
                Log.d(TAG, "write logistics");
                Intent intent = new Intent(this, WriteLogisticsInfoActivty.class);
                intent.putExtra("goods_id", refundDetail.getOrder_id());
                if (refundDetail != null) {
                    if ((refundDetail.getReturn_address() != null)
                            && (!refundDetail.getReturn_address().isEmpty())) {
                        intent.putExtra("address", refundDetail.getReturn_address());
                    }
                }
                Log.d(TAG, " to WriteLogisticsInfoActivty");
                startActivityForResult(intent, 0);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == 0) {
            boolean flag = false;
            if (data != null) {
                flag = data.getBooleanExtra("flag", false);
            }
            if (flag) {
                finish();
            }
        }
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
