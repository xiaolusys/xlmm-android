package com.jimei.xiaolumeimei.ui.activity.trade;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.HorizontalScrollViewAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.entities.AllRefundsBean;
import com.jimei.xiaolumeimei.model.TradeModel;
import com.jimei.xiaolumeimei.utils.ViewUtils;
import com.jimei.xiaolumeimei.widget.MyHorizontalScrollView;
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
