package com.jimei.xiaolumeimei.ui.activity.trade;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.AllOrdersBean;
import com.jimei.xiaolumeimei.model.TradeModel;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.AllOrdersListAdapter;
import com.jimei.xiaolumeimei.data.XlmmApi;

import com.jimei.xiaolumeimei.entities.OrderDetailBean;

import butterknife.Bind;
import rx.schedulers.Schedulers;

public class OrderDetailActivity extends BaseSwipeBackCompatActivity {
    String TAG = "OrderDetailActivity";
    @Bind(R.id.toolbar) Toolbar toolbar;
    int order_id = 0;
    TradeModel model = new TradeModel();

    @Override protected void setListener() {

    }
    @Override protected void getBundleExtras(Bundle extras) {

    }

    @Override protected int getContentViewLayoutID() {
        return R.layout.activity_order_detail;
    }

    @Override protected void initViews() {
        toolbar.setTitle("订单详情");
        setSupportActionBar(toolbar);
    }
    //从server端获得所有订单数据，可能要查询几次
    @Override protected void initData() {
        order_id = getIntent().getExtras().getInt("orderinfo");
        model.getOrderDetailBean(order_id)
                .subscribeOn(Schedulers.newThread())
                .subscribe(new ServiceResponse<OrderDetailBean>() {
                    @Override public void onNext(OrderDetailBean orderDetailBean) {

                        fillDataToView(orderDetailBean);
                        Log.i(TAG, orderDetailBean.toString());
                    }
                });
    }

    @Override protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }





    private void fillDataToView(OrderDetailBean orderDetailBean){
        TextView tx_order_id = (TextView) findViewById(R.id.tx_order_id);
        tx_order_id.setText("订单编号" + orderDetailBean.getTid());

        TextView tx_order_state = (TextView) findViewById(R.id.tx_order_state);
        tx_order_state.setText("状态" + orderDetailBean.getStatus());

        TextView tx_custom_name = (TextView) findViewById(R.id.tx_custom_name);
        tx_custom_name.setText("姓名：" + orderDetailBean.getReceiver_name());

        TextView tx_custom_address = (TextView) findViewById(R.id.tx_custom_address);
        tx_custom_address.setText("地址：" + orderDetailBean.getReceiver_address());

        //没有订单创建时间?
        TextView tx_order_crttime = (TextView) findViewById(R.id.tx_order_crttime);
        tx_order_crttime.setText("时间：" + orderDetailBean.getCreated());
        TextView tx_order_crtstate = (TextView) findViewById(R.id.tx_order_crtstate);
        tx_order_crtstate.setText("订单创建成功" );

        if(1 == orderDetailBean.getOrders().size()) {
            ImageView img_good = (ImageView) findViewById(R.id.img_good);
            String headImg = orderDetailBean.getOrders().get(0).getPic_path();
            String[] temp = headImg.split("http://image.xiaolu.so/");
            String head_img = "";
            if (temp.length > 1) {
                try {
                    head_img = "http://image.xiaolu.so/"
                            + URLEncoder.encode(temp[1], "utf-8")
                            + "?imageMogr2/format/jpg/size-limit/30k/thumbnail/289/quality/90";
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            Glide.with(this)
                    .load(head_img)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.parceholder)
                    .centerCrop()
                    .into(img_good);

            TextView tx_good_name = (TextView) findViewById(R.id.tx_good_name);
            tx_good_name.setText(orderDetailBean.getOrders().get(0).getTitle());
            TextView tx_good_price = (TextView) findViewById(R.id.tx_good_price);
            tx_good_price.setText("￥" + orderDetailBean.getOrders().get(0).getTotal_fee());

            TextView tx_good_size = (TextView) findViewById(R.id.tx_good_size);
            tx_good_size.setText(orderDetailBean.getOrders().get(0).getSku_name());

            TextView tx_good_num = (TextView) findViewById(R.id.tx_good_num);
            tx_good_num.setText("×" + orderDetailBean.getOrders().get(0).getNum());
        }
        TextView tx_order_totalfee = (TextView) findViewById(R.id.tx_order_totalfee);
        tx_order_totalfee.setText("￥" + orderDetailBean.getTotal_fee());

        TextView tx_order_discountfee = (TextView) findViewById(R.id.tx_order_discountfee);
        tx_order_discountfee.setText("￥" + orderDetailBean.getDiscount_fee());

        TextView tx_order_postfee = (TextView) findViewById(R.id.tx_order_postfee);
        tx_order_postfee.setText("￥" + orderDetailBean.getPost_fee());

        TextView tx_order_payment = (TextView) findViewById(R.id.tx_order_payment);
        tx_order_payment.setText("总金额 ￥" + orderDetailBean.getPayment());
    }
}
