package com.jimei.xiaolumeimei.ui.activity.trade;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import butterknife.Bind;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.data.XlmmApi;
import com.jimei.xiaolumeimei.entities.CartsPayinfoBean;
import com.jimei.xiaolumeimei.model.CartsModel;
import com.jimei.xiaolumeimei.model.TradeModel;
import com.jimei.xiaolumeimei.okhttp.callback.OkHttpCallback;
import com.jimei.xiaolumeimei.okhttp.request.OkHttpRequest;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import java.io.IOException;
import rx.schedulers.Schedulers;

/**
 * Created by 优尼世界 on 2016/01/15.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class CartsPayInfoActivity extends BaseSwipeBackCompatActivity
    implements View.OnClickListener {

  private static final int REQUEST_CODE_PAYMENT = 1;
  CartsModel model = new CartsModel();
  TradeModel tradeModel = new TradeModel();
  @Bind(R.id.alipay) Button button;
  private String ids;
  private String cart_ids;
  private String addr_id;
  private String channel;
  private String payment;
  private String post_fee;
  private String discount_fee;
  private String total_fee;
  private String uuid;

  @Override protected void setListener() {

  }

  @Override protected void initData() {
    model.getCartsInfoList(ids)
        .subscribeOn(Schedulers.newThread())
        .subscribe(new ServiceResponse<CartsPayinfoBean>() {
          @Override public void onNext(CartsPayinfoBean cartsPayinfoBean) {
            super.onNext(cartsPayinfoBean);
            JUtils.Log("ITXUYE", cartsPayinfoBean.toString());
            cart_ids = cartsPayinfoBean.getCartIds();
            addr_id = "96491";
            channel = "alipay";
            payment = cartsPayinfoBean.getTotalFee() + cartsPayinfoBean.getPostFee()
                - cartsPayinfoBean.getDiscountFee() + "";
            post_fee = cartsPayinfoBean.getPostFee() + "";
            discount_fee = cartsPayinfoBean.getDiscountFee() + "";
            total_fee = cartsPayinfoBean.getTotalFee() + "";
            uuid = cartsPayinfoBean.getUuid();
          }
        });
  }

  @Override protected void getBundleExtras(Bundle extras) {
    ids = extras.getString("ids");
    JUtils.Log("hhahha", ids);
  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.cartspayinfo_activity;
  }

  @Override protected void initViews() {
    button.setOnClickListener(this);
  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }

  @Override public void onClick(View v) {

    switch (v.getId()) {
      case R.id.alipay:

        //Intent intent = new Intent();
        //String packageName = getPackageName();
        //ComponentName componentName =
        //    new ComponentName(packageName, packageName + ".wxapi.WXPayEntryActivity");
        //intent.setComponent(componentName);
        //intent.putExtra(PaymentActivity.EXTRA_CHARGE, charge);
        //startActivityForResult(intent, REQUEST_CODE_PAYMENT);

        new OkHttpRequest.Builder().url(XlmmApi.URL_BASE + "trades/shoppingcart_create")
            .addParams("cart_ids", cart_ids)
            .addParams("addr_id", addr_id)
            .addParams("channel", channel)
            .addParams("payment", payment)
            .addParams("post_fee", post_fee)
            .addParams("discount_fee", discount_fee)
            .addParams("total_fee", total_fee)
            .addParams("uuid", uuid)
            .post(new OkHttpCallback() {
              @Override public void onError(Request request, Exception e) {
                JUtils.Toast("请检查你的网络");
              }

              @Override public void onResponse(Response response, Object data) {
                try {
                  String string = response.body().string();
                  Log.i("支付宝", string);
                } catch (IOException e) {
                  e.printStackTrace();
                }
              }
            });

        //tradeModel.shoppingcart_create(cart_ids, addr_id, channel, payment, post_fee,
        //    discount_fee, total_fee, uuid)
        //    .subscribeOn(Schedulers.newThread())
        //    .subscribe(new ServiceResponse<PayReturnBean>() {
        //      @Override public void onNext(PayReturnBean s) {
        //        super.onNext(s);
        //        Log.i("支付宝", s.getData());
        //      }
        //    });
        break;
    }
  }

  public String getCharge() {

    //new OkHttpRequest.Builder().url()

    return null;
  }
}
