package com.jimei.xiaolumeimei.ui.activity.trade;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.AllRefundsBean;
import com.jimei.xiaolumeimei.model.TradeModel;
import com.jimei.xiaolumeimei.utils.ViewUtils;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;

import com.jimei.xiaolumeimei.R;

import butterknife.Bind;
import rx.schedulers.Schedulers;

public class ApplyReturnGoodsActivity extends BaseSwipeBackCompatActivity implements View.OnClickListener{
    String TAG = "ApplyReturnGoodsActivity";

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

    int order_id = 0;
    TradeModel model = new TradeModel();

    @Override protected void setListener() {
        toolbar.setOnClickListener(this);

    }
    @Override protected void getBundleExtras(Bundle extras) {

    }

    @Override protected int getContentViewLayoutID() {
        return R.layout.activity_apply_return_goods;
    }

    @Override protected void initViews() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);


    }
    //从server端获得所有订单数据，可能要查询几次
    @Override protected void initData() {
        order_id = getIntent().getExtras().getInt("orderinfo");
        model.getRefundDetailBean(order_id)
                .subscribeOn(Schedulers.newThread())
                .subscribe(new ServiceResponse<AllRefundsBean.ResultsEntity>() {
                    @Override public void onNext(AllRefundsBean.ResultsEntity refundDetailBean) {
                        fillDataToView(refundDetailBean);
                        Log.i(TAG, refundDetailBean.toString());
                    }
                });
    }

    @Override protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }





    private void fillDataToView(AllRefundsBean.ResultsEntity refundDetailBean){
        //TextView tx_order_id = (TextView) findViewById(R.id.tx_refund_no);
        tx_order_id.setText("订单编号" + refundDetailBean.getRefund_no());

        //TextView tx_refund_state = (TextView) findViewById(R.id.tx_refund_state);
        tx_refund_state.setText(refundDetailBean.getStatus_display());

        //ImageView img_good = (ImageView) findViewById(R.id.img_good);
        ViewUtils.loadImgToImgView(this,img_good, refundDetailBean.getPic_path());

        //TextView tx_good_name = (TextView) findViewById(R.id.tx_good_name);
        tx_good_name.setText(refundDetailBean.getTitle());
        //TextView tx_good_price = (TextView) findViewById(R.id.tx_good_price);
        tx_good_price.setText("￥"+ refundDetailBean.getTotal_fee());

        //TextView tx_good_size = (TextView) findViewById(R.id.tx_good_size);
        tx_good_size.setText("尺码："+refundDetailBean.getSku_name());
        //TextView tx_good_num = (TextView) findViewById(R.id.tx_good_num);
        tx_good_num.setText("×"+ refundDetailBean.getRefund_num());

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

        }
    }
}
