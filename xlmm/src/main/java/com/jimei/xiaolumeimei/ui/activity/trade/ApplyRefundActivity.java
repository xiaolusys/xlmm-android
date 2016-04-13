package com.jimei.xiaolumeimei.ui.activity.trade;

import android.content.DialogInterface;
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
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.entities.AllOrdersBean;
import com.jimei.xiaolumeimei.model.TradeModel;
import com.jimei.xiaolumeimei.utils.ViewUtils;
import com.jimei.xiaolumeimei.widget.XlmmTitleView;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.squareup.okhttp.ResponseBody;
import rx.Subscription;
import rx.schedulers.Schedulers;

public class ApplyRefundActivity extends BaseSwipeBackCompatActivity
    implements View.OnClickListener {
  String TAG = "ApplyRefundActivity";
  String slect_reason[] = new String[] { "七天无理由退换", "缺货", "错拍", "没有发货", "与描述不符", "其他" };

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
  @Bind(R.id.et_refund_reason) EditText et_refund_reason;
  @Bind(R.id.btn_commit) Button btn_commit;

  AllOrdersBean.ResultsEntity.OrdersEntity goods_info;
  String reason = "";
  int num = 0;
  double apply_fee = 0;
  String desc = "";
  String proof_pic = "";

  @Override protected void setListener() {
    btn_commit.setOnClickListener(this);
    add.setOnClickListener(this);
    delete.setOnClickListener(this);
    et_refund_info.setOnClickListener(this);
    et_refund_reason.setOnTouchListener(new View.OnTouchListener() {
      public boolean onTouch(View v, MotionEvent event) {
        //et_refund_reason.setInputType(InputType.TYPE_NULL); //关闭软键盘
        if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
          Log.d(TAG, "choose reason");
          chooseReason();
        }
        return false;
      }
    });
  }

  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.activity_apply_refund;
  }

  @Override protected void initViews() {

  }

  //从server端获得所有订单数据，可能要查询几次
  @Override protected void initData() {
    if (null != getIntent() && null != getIntent().getExtras()) {
      goods_info = getIntent().getExtras().getParcelable("goods_info");
    }
    if(goods_info != null) {
      fillDataToView(goods_info);
    }
    else{
      JUtils.Log(TAG, "initData, no good info,return");
      Intent intent = new Intent(ApplyRefundActivity.this, AllOrdersActivity.class);
      Bundle bundle = new Bundle();
      bundle.putInt("fragment", 1);
      intent.putExtras(bundle);
      startActivity(intent);
    }
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
    tx_good_num.setText("×" + goods.getNum());

    num = goods.getNum();
    tx_refund_num.setText(Integer.toString(num));
    tx_refundfee.setText("￥" + goods.getPayment());
  }

  @Override public void onClick(View v) {
    switch (v.getId()) {
      case R.id.toolbar:
        finish();
        break;
      case R.id.btn_commit:
        desc = et_refund_info.getText().toString().trim();
        if (reason.equals("")) {
          JUtils.Toast("请选择退货原因！");
        } else {
          commit_apply();
        }

        break;
      case R.id.et_refund_info:
        Log.i(TAG, "click et_refund_info ");
        et_refund_info.setCursorVisible(true);
        et_refund_info.setFocusable(true);
        et_refund_info.setFocusableInTouchMode(true);
        et_refund_info.requestFocus();
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        break;
      case R.id.add:
        Log.i(TAG, "add ");
        if (goods_info.getNum() > num) {
          num++;
        }
        tx_refund_num.setText(Integer.toString(num));
        break;
      case R.id.delete:
        Log.i(TAG, "delete ");
        if (num > 1) {
          num--;
        }
        tx_refund_num.setText(Integer.toString(num));
        break;
    }
  }

  private void commit_apply() {
    Subscription subscription = TradeModel.getInstance()
        .refund_create(goods_info.getId(), XlmmConst.get_reason_num(reason), num,
            apply_fee, desc, proof_pic)
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<ResponseBody>() {
          @Override public void onNext(ResponseBody resp) {

            Log.i(TAG, "commit_apply success " + resp.toString());
            finish();
          }

          @Override public void onCompleted() {
            super.onCompleted();
          }

          @Override public void onError(Throwable e) {

            Log.e(TAG, " error:, " + e.toString());
            super.onError(e);
          }
        });
    addSubscription(subscription);
  }

  private void chooseReason() {
    new AlertDialog.Builder(this).setTitle("")
        .setItems(slect_reason, new DialogInterface.OnClickListener() {

          @Override public void onClick(DialogInterface dialog, int which) {
            /*
            * ad变量用final关键字定义，因为在隐式实现的Runnable接口 的run()方法中 需要访问final变量。
             */
            Log.d(TAG, "你选择的是：" + which + ": " + slect_reason[which]);
            reason = slect_reason[which];
            et_refund_reason.setText(reason);
            dialog.dismiss();
          }
        })
        .setNegativeButton("取消", null)
        .show();
  }
}
