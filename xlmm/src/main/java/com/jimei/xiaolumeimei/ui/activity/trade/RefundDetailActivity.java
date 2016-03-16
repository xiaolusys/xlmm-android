package com.jimei.xiaolumeimei.ui.activity.trade;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import java.util.ArrayList;
import java.util.List;
import rx.Subscription;
import rx.schedulers.Schedulers;

public class RefundDetailActivity extends BaseSwipeBackCompatActivity
    implements View.OnClickListener {
  String TAG = "RefundDetailActivity";

  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.tx_order_id) TextView tx_order_id;
  @Bind(R.id.tx_refund_state) TextView tx_refund_state;
  @Bind(R.id.img_good) ImageView img_good;
  @Bind(R.id.tx_good_name) TextView tx_good_name;
  @Bind(R.id.tx_good_price) TextView tx_good_price;
  @Bind(R.id.tx_good_size) TextView tx_good_size;
  @Bind(R.id.tx_good_num) TextView tx_good_num;
  @Bind(R.id.tx_refund_num) TextView tx_refund_num;
  @Bind(R.id.tx_refundfee) TextView tx_refundfee;
  @Bind(R.id.tx_refund_reason) TextView tx_refund_reason;
  @Bind(R.id.tx_order_crttime) TextView tx_order_crttime;

  Button btn_return_addr;
  EditText et_logistics_info;
  MyHorizontalScrollView mHorizontalScrollView;
  int goods_id = 0;
  int refund_state = 0;
  AllRefundsBean.ResultsEntity refundDetail;

  @Override protected void setListener() {
    toolbar.setOnClickListener(this);
  }

  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    if ((getIntent() != null) && (getIntent().getExtras() != null)) {
      refund_state = getIntent().getExtras().getInt("refund_state");
    }
    Log.d(TAG, "refund_state " + refund_state);
    if (refund_state == XlmmConst.REFUND_STATE_SELLER_AGREED) {
      return R.layout.activity_refund_detail3;
    } else if (refund_state == XlmmConst.REFUND_STATE_BUYER_APPLY) {
      return R.layout.activity_refund_detail2;
    } else {
      return R.layout.activity_refund_detail;
    }
  }

  @Override protected void initViews() {
    toolbar.setTitle("");
    setSupportActionBar(toolbar);
    finishBack(toolbar);

    goods_id = getIntent().getExtras().getInt("goods_id");
    Log.d(TAG, "goods_id " + goods_id);

    if (refund_state == XlmmConst.REFUND_STATE_SELLER_AGREED) {
      et_logistics_info = (EditText) findViewById(R.id.et_logistics_info);
      et_logistics_info.setOnTouchListener(new View.OnTouchListener() {
        public boolean onTouch(View v, MotionEvent event) {
          //et_refund_reason.setInputType(InputType.TYPE_NULL); //关闭软键盘
          if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
            Log.d(TAG, "write logistics");
            Intent intent =
                new Intent(RefundDetailActivity.this, WriteLogisticsInfoActivty.class);
            intent.putExtra("goods_id", refundDetail.getOrder_id());
            Log.d(TAG, " to WriteLogisticsInfoActivty");
            startActivity(intent);
          }
          return false;
        }
      });
      btn_return_addr = (Button) findViewById(R.id.btn_return_addr);
      btn_return_addr.setOnClickListener(this);
    }
  }

  //从server端获得所有订单数据，可能要查询几次
  @Override protected void initData() {
    JUtils.Log(TAG, "initData goods_id " + goods_id);
    Subscription subscription = TradeModel.getInstance()
        .getRefundDetailBean(goods_id)
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<AllRefundsBean.ResultsEntity>() {
          @Override public void onNext(AllRefundsBean.ResultsEntity refundDetailBean) {
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
              //设置适配器
              mHorizontalScrollView.initDatas(mAdapter);
            }
          }

          @Override public void onCompleted() {
            super.onCompleted();
          }

          @Override public void onError(Throwable e) {

            Log.e(TAG, "getRefundDetailBean error:, " + e.toString());
            super.onError(e);
          }
        });
    addSubscription(subscription);
  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }

  private void fillDataToView(AllRefundsBean.ResultsEntity refundDetailBean) {
    JUtils.Log(TAG, "fillDataToView ");

    tx_order_id.setText("订单编号" + refundDetailBean.getRefund_no());

    tx_refund_state.setText(refundDetailBean.getStatus_display());

    ViewUtils.loadImgToImgView(this, img_good, refundDetailBean.getPic_path());

    //TextView tx_good_name = (TextView) findViewById(R.id.tx_good_name);
    if (refundDetailBean.getTitle().length() >= 9) {
      tx_good_name.setText(refundDetailBean.getTitle().substring(0, 8) + "...");
    } else {
      tx_good_name.setText(refundDetailBean.getTitle());
    }
    //TextView tx_good_price = (TextView) findViewById(R.id.tx_good_price);
    tx_good_price.setText("¥" + refundDetailBean.getPayment());

    //TextView tx_good_size = (TextView) findViewById(R.id.tx_good_size);
    tx_good_size.setText("尺码：" + refundDetailBean.getSku_name());
    //TextView tx_good_num = (TextView) findViewById(R.id.tx_good_num);
    //tx_good_num.setText("x" + refundDetailBean.getRefund_num());
    tx_good_num.setText("");

    //TextView tx_refund_num = (TextView) findViewById(R.id.tx_refund_num);
    tx_refund_num.setText(Integer.toString(refundDetailBean.getRefund_num()));

    //TextView tx_refundfee = (TextView) findViewById(R.id.tx_refundfee);
    tx_refundfee.setText("¥" + refundDetailBean.getRefund_fee());

    //TextView tx_refund_reason = (TextView) findViewById(R.id.tx_refund_reason);
    tx_refund_reason.setText(refundDetailBean.getReason());

    tx_order_crttime.setText(refundDetailBean.getCreated().replace("T", " "));
    JUtils.Log(TAG, "crt time " + refundDetailBean.getCreated());
  }

  @Override public void onClick(View v) {
    switch (v.getId()) {
      case R.id.btn_return_addr:
        if ((refundDetail.getReturn_address() != null)
            && (!refundDetail.getReturn_address().isEmpty())) {
          new AlertDialog.Builder(this).setTitle("")
              .setMessage(refundDetail.getReturn_address() + getResources().getString(
                  R.string.return_addr))
              .show();
        }
        break;
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
}
