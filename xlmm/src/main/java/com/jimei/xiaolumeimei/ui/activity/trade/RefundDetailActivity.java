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
import android.widget.TextView;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.HorizontalScrollViewAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.entities.AllRefundsBean;
import com.jimei.xiaolumeimei.model.TradeModel;
import com.jimei.xiaolumeimei.utils.ViewUtils;
import com.jimei.xiaolumeimei.widget.MyHorizontalScrollView;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.Subscription;
import rx.schedulers.Schedulers;

public class RefundDetailActivity extends BaseSwipeBackCompatActivity implements View.OnClickListener {
    private static final String TAG = RefundDetailActivity.class.getSimpleName();
    @Bind(R.id.tv_order_id)
    TextView orderIdTv;
    @Bind(R.id.tv_status)
    TextView statusTv;
    @Bind(R.id.ll_return)
    LinearLayout returnLayout;
    @Bind(R.id.btn_return_addr)
    Button returnAddrBtn;
    @Bind(R.id.et_logistics_info)
    EditText logInfoEt;
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
    private int refund_state;
    private int goods_id;

    AllRefundsBean.ResultsEntity refundDetail;
    MyHorizontalScrollView mHorizontalScrollView;

    @Override
    protected void setListener() {
        logInfoEt.setOnClickListener(this);
        returnAddrBtn.setOnClickListener(this);
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
                            fillPicPath(mDatas, (String) (refundDetailBean.getProof_pic().toString()));
                            Log.d(TAG, "proofpic " + refundDetailBean.getProof_pic());

                            mHorizontalScrollView =
                                    (MyHorizontalScrollView) findViewById(R.id.id_horizontalScrollView);
                            HorizontalScrollViewAdapter mAdapter =
                                    new HorizontalScrollViewAdapter(RefundDetailActivity.this, mDatas);
                            mHorizontalScrollView.initDatas(mAdapter);
                        }

                    }

                    @Override
                    public void onCompleted() {
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
        ViewUtils.loadImgToImgView(getApplicationContext(), goodImageView, refundDetailBean.getPic_path());
        if (refundDetailBean.getTitle().length() >= 15) {
            goodNameTv.setText(refundDetailBean.getTitle().substring(0, 8) + "...");
        } else {
            goodNameTv.setText(refundDetailBean.getTitle());
        }
        goodPriceTv.setText("¥" + refundDetailBean.getPayment());
        goodSizeTv.setText("尺码：" + refundDetailBean.getSku_name());
        goodNumTv.setText("");
        numTv.setText(Integer.toString(refundDetailBean.getRefund_num()));
        priceTv.setText("¥" + refundDetailBean.getRefund_fee());
        reasonTv.setText(refundDetailBean.getReason());
        createTimeTv.setText(refundDetailBean.getCreated().replace("T", " "));
        lastTimeTv.setText(refundDetailBean.getModified().replace("T", " "));
        lastStateTv.setText(refundDetailBean.getStatus_display());
        JUtils.Log(TAG, "crt time " + refundDetailBean.getCreated());
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
    protected void onResume() {
        super.onResume();
        logInfoEt.setClickable(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_return_addr:
                if ((refundDetail.getReturn_address() != null) && (!refundDetail.getReturn_address().isEmpty())) {
                    new AlertDialog.Builder(this).setTitle("")
                            .setMessage(refundDetail.getReturn_address() + getResources().getString(
                                    R.string.return_addr))
                            .show();
                }
                break;
            case R.id.et_logistics_info:
                Log.d(TAG, "write logistics");
                Intent intent = new Intent(this, WriteLogisticsInfoActivty.class);
                intent.putExtra("goods_id", refundDetail.getOrder_id());
                Log.d(TAG, " to WriteLogisticsInfoActivty");
                startActivity(intent);
                logInfoEt.setClickable(false);
                break;
        }
    }

}
