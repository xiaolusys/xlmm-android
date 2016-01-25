package com.jimei.xiaolumeimei.ui.activity.trade;

import android.content.Intent;
import android.graphics.RadialGradient;
import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jimei.xiaolumeimei.adapter.HorizontalScrollViewAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.data.LogisticsCompanyInfo;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.entities.AllRefundsBean;
import com.jimei.xiaolumeimei.model.TradeModel;
import com.jimei.xiaolumeimei.utils.ViewUtils;
import com.jimei.xiaolumeimei.widget.MyHorizontalScrollView;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;

import com.jimei.xiaolumeimei.R;

import butterknife.Bind;
import java.util.ArrayList;
import java.util.List;
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

  Button btn_return_addr;
  EditText et_logistics_info;
  MyHorizontalScrollView mHorizontalScrollView;
  int goods_id = 0;
  int refund_state = 0;
  TradeModel model = new TradeModel();

  @Override protected void setListener() {
    toolbar.setOnClickListener(this);
  }

  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    refund_state = getIntent().getExtras().getInt("refund_state");
    if (refund_state == XlmmConst.REFUND_STATE_SELLER_AGREED) {
      return R.layout.activity_refund_detail3;
    }
    else if (refund_state == XlmmConst.REFUND_STATE_BUYER_APPLY){
      return R.layout.activity_refund_detail2;
    }
    else {
      return R.layout.activity_refund_detail;
    }
  }

  @Override protected void initViews() {
    toolbar.setTitle("");
    setSupportActionBar(toolbar);
    toolbar.setNavigationIcon(R.drawable.back);

    if (refund_state == XlmmConst.REFUND_STATE_SELLER_AGREED) {
      et_logistics_info = (EditText)findViewById(R.id.et_logistics_info);
      et_logistics_info.setOnTouchListener(new View.OnTouchListener(){
        public boolean onTouch(View v, MotionEvent event){
          //et_refund_reason.setInputType(InputType.TYPE_NULL); //关闭软键盘
          if(MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
            Log.d(TAG, "write logistics");
            Intent intent = new Intent(RefundDetailActivity.this, WriteLogisticsInfoActivty.class);

            Log.d(TAG," to WriteLogisticsInfoActivty");
            startActivity(intent);
          }
          return false;
        }
      });
      btn_return_addr = (Button)findViewById(R.id.btn_return_addr);
      btn_return_addr.setOnClickListener(this);
    }
  }

  //从server端获得所有订单数据，可能要查询几次
  @Override protected void initData() {


    goods_id = getIntent().getExtras().getInt("goods_id");
    model.getRefundDetailBean(goods_id)
        .subscribeOn(Schedulers.newThread())
        .subscribe(new ServiceResponse<AllRefundsBean.ResultsEntity>() {
          @Override public void onNext(AllRefundsBean.ResultsEntity refundDetailBean) {
            fillDataToView(refundDetailBean);
            Log.i(TAG, refundDetailBean.toString());
            if ((refund_state == XlmmConst.REFUND_STATE_SELLER_AGREED)
            || (refund_state == XlmmConst.REFUND_STATE_BUYER_APPLY)){
              List<String> mDatas = new ArrayList<String>();
              fillPicPath(mDatas, (String) (refundDetailBean.getProof_pic()));
              Log.d(TAG, "proofpic " + refundDetailBean.getProof_pic());

              mHorizontalScrollView = (MyHorizontalScrollView) findViewById(R.id.id_horizontalScrollView);
              HorizontalScrollViewAdapter mAdapter =
                  new HorizontalScrollViewAdapter(RefundDetailActivity.this, mDatas);
              //设置适配器
              mHorizontalScrollView.initDatas(mAdapter);
            }
          }
        });
  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }

  private void fillDataToView(AllRefundsBean.ResultsEntity refundDetailBean) {
    //TextView tx_order_id = (TextView) findViewById(R.id.tx_refund_no);
    tx_order_id.setText("订单编号" + refundDetailBean.getRefund_no());

    //TextView tx_refund_state = (TextView) findViewById(R.id.tx_refund_state);
    tx_refund_state.setText(refundDetailBean.getStatus_display());

    //ImageView img_good = (ImageView) findViewById(R.id.img_good);
    ViewUtils.loadImgToImgView(this, img_good, refundDetailBean.getPic_path());

    //TextView tx_good_name = (TextView) findViewById(R.id.tx_good_name);
    tx_good_name.setText(refundDetailBean.getTitle());
    //TextView tx_good_price = (TextView) findViewById(R.id.tx_good_price);
    tx_good_price.setText("￥" + refundDetailBean.getTotal_fee());

    //TextView tx_good_size = (TextView) findViewById(R.id.tx_good_size);
    tx_good_size.setText("尺码：" + refundDetailBean.getSku_name());
    //TextView tx_good_num = (TextView) findViewById(R.id.tx_good_num);
    tx_good_num.setText("×" + refundDetailBean.getRefund_num());

    //TextView tx_refund_num = (TextView) findViewById(R.id.tx_refund_num);
    tx_refund_num.setText(refundDetailBean.getRefund_num());

    //TextView tx_refundfee = (TextView) findViewById(R.id.tx_refundfee);
    tx_refundfee.setText("￥" + refundDetailBean.getRefund_fee());

    //TextView tx_refund_reason = (TextView) findViewById(R.id.tx_refund_reason);
    tx_refund_reason.setText(refundDetailBean.getReason());
  }

  @Override public void onClick(View v) {
    switch (v.getId()) {
      case R.id.toolbar:
        finish();
        break;
      case R.id.btn_return_addr:
        new AlertDialog.Builder(this).setTitle("")
            .setMessage(R.string.return_addr).show();
        break;
    }
  }

  private void fillPicPath(List<String> mDatas, String pics){
    if((null == pics) || (pics.equals(""))) return;
    String[] strArray = null;
    strArray = pics.split(",");
    for(int i = 0; i< strArray.length;i++) {
      mDatas.add(strArray[i]);
    }
  }
}
