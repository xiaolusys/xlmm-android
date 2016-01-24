package com.jimei.xiaolumeimei.ui.activity.trade;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.AllOrdersBean;
import com.jimei.xiaolumeimei.model.TradeModel;
import com.jimei.xiaolumeimei.utils.ViewUtils;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;

import com.jimei.xiaolumeimei.R;

import butterknife.Bind;
import com.squareup.okhttp.ResponseBody;
import rx.schedulers.Schedulers;

public class ApplyRefundActivity extends BaseSwipeBackCompatActivity
    implements View.OnClickListener {
  String TAG = "ApplyRefundActivity";

  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.img_good) ImageView img_good;
  @Bind(R.id.tx_good_name) TextView tx_good_name;
  @Bind(R.id.tx_good_price) TextView tx_good_price;
  @Bind(R.id.tx_good_size) TextView tx_good_size;
  @Bind(R.id.tx_good_num) TextView tx_good_num;
  @Bind(R.id.delete) TextView delete;
  @Bind(R.id.tx_refund_num) TextView tx_refund_num;
  @Bind(R.id.add) TextView add;
  @Bind(R.id.tx_refundfee) TextView tx_refundfee;
  @Bind(R.id.et_refund_info) EditText et_refund_info;
  @Bind(R.id.sp_refund_reason) Spinner sp_refund_reason;
  @Bind(R.id.btn_commit) Button btn_commit;

  AllOrdersBean.ResultsEntity.OrdersEntity goods_info;
  TradeModel model = new TradeModel();
  String reason = "";
  int num = 0;
  double apply_fee = 0;
  String desc = "";
  String proof_pic="";

  @Override protected void setListener() {
    toolbar.setOnClickListener(this);
    btn_commit.setOnClickListener(this);
    add.setOnClickListener(this);
    delete.setOnClickListener(this);
    sp_refund_reason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view,
          int position, long id) {
        String str=parent.getItemAtPosition(position).toString();
        Log.d(TAG, "write reason");
      }
      @Override
      public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
      }
    });
  }

  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.activity_apply_refund;
  }

  @Override protected void initViews() {
    toolbar.setTitle("");
    setSupportActionBar(toolbar);
    toolbar.setNavigationIcon(R.drawable.back);

    add.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        num++;
        tx_refund_num.setText(Integer.toString(num));
      }
    });

    delete.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        num--;
        tx_refund_num.setText(Integer.toString(num));
      }
    });
  }

  //从server端获得所有订单数据，可能要查询几次
  @Override protected void initData() {
    goods_info = getIntent().getExtras().getParcelable("goods_info");
    fillDataToView(goods_info);
  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }

  private void fillDataToView(AllOrdersBean.ResultsEntity.OrdersEntity goods) {
    ViewUtils.loadImgToImgView(this, img_good, goods.getPicPath());

    tx_good_name.setText(goods.getTitle());
    tx_good_price.setText("￥" + goods.getTotalFee());

    tx_good_size.setText("尺码：" + goods.getSkuName());
    tx_good_num.setText("×" + goods.getPayment());

    num = goods.getNum();
    tx_refund_num.setText(Integer.toString(num));
    tx_refundfee.setText("￥" + apply_fee);

  }

  @Override public void onClick(View v) {
    switch (v.getId()) {
      case R.id.toolbar:
        finish();
        break;
      case R.id.btn_commit:
        desc = et_refund_info.getText().toString().trim();
        commit_apply();
        finish();
        break;
    }
  }

  private void commit_apply(){
    model.refund_create(goods_info.getId(), reason, num, apply_fee, desc, proof_pic)
        .subscribeOn(Schedulers.newThread())
        .subscribe(new ServiceResponse<ResponseBody>() {
          @Override public void onNext(ResponseBody resp) {

            Log.i(TAG,"commit_apply "+ resp.toString());
          }
        });
  }
}
