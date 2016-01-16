package com.jimei.xiaolumeimei.ui.activity.trade;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import butterknife.Bind;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.CartsPayinfoBean;
import com.jimei.xiaolumeimei.model.CartsModel;
import com.jimei.xiaolumeimei.model.TradeModel;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.pingplusplus.android.PaymentActivity;
import com.squareup.okhttp.ResponseBody;
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
  @Bind(R.id.alipay) Button alipy;
  @Bind(R.id.wx) Button wx;
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
    alipy.setOnClickListener(this);
    wx.setOnClickListener(this);
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

        //new OkHttpRequest.Builder().url(XlmmApi.URL_BASE + "trades/shoppingcart_create")
        //    .addParams("cart_ids", cart_ids)
        //    .addParams("addr_id", addr_id)
        //    .addParams("channel", "alipay")
        //    .addParams("payment", payment)
        //    .addParams("post_fee", post_fee)
        //    .addParams("discount_fee", discount_fee)
        //    .addParams("total_fee", total_fee)
        //    .addParams("uuid", uuid)
        //    .post(new OkHttpCallback() {
        //      @Override public void onError(Request request, Exception e) {
        //
        //      }
        //
        //      @Override public void onResponse(Response response, Object data) {
        //        try {
        //          String string = response.body().string();
        //          Log.i("charge", string);
        //          Intent intent = new Intent();
        //          String packageName = getPackageName();
        //          ComponentName componentName = new ComponentName(packageName,
        //              packageName + ".wxapi.WXPayEntryActivity");
        //          intent.setComponent(componentName);
        //          intent.putExtra(PaymentActivity.EXTRA_CHARGE, string);
        //
        //          startActivityForResult(intent, REQUEST_CODE_PAYMENT);
        //        } catch (IOException e) {
        //          e.printStackTrace();
        //        }
        //      }
        //    });

        tradeModel.shoppingcart_create(cart_ids, addr_id, "alipay", payment, post_fee,
            discount_fee, total_fee, uuid)
            .subscribeOn(Schedulers.io())
            .subscribe(new ServiceResponse<ResponseBody>() {
              @Override public void onNext(ResponseBody responseBody) {
                super.onNext(responseBody);
                try {
                  String string = responseBody.string();
                  Log.i("charge", string);
                  Intent intent = new Intent();
                  String packageName = getPackageName();
                  ComponentName componentName = new ComponentName(packageName,
                      packageName + ".wxapi.WXPayEntryActivity");
                  intent.setComponent(componentName);
                  intent.putExtra(PaymentActivity.EXTRA_CHARGE, string);
                  startActivityForResult(intent, REQUEST_CODE_PAYMENT);
                } catch (IOException e) {
                  e.printStackTrace();
                }
              }
            });

        break;

      case R.id.wx:

        //new OkHttpRequest.Builder().url(XlmmApi.URL_BASE + "trades/shoppingcart_create")
        //    .addParams("cart_ids", cart_ids)
        //    .addParams("addr_id", addr_id)
        //    .addParams("channel", "wx")
        //    .addParams("payment", payment)
        //    .addParams("post_fee", post_fee)
        //    .addParams("discount_fee", discount_fee)
        //    .addParams("total_fee", total_fee)
        //    .addParams("uuid", uuid)
        //    .post(new OkHttpCallback() {
        //      @Override public void onError(Request request, Exception e) {
        //
        //      }
        //
        //      @Override public void onResponse(Response response, Object data) {
        //        try {
        //          String string = response.body().string();
        //          Log.i("charge", string);
        //          Intent intent = new Intent();
        //          String packageName = getPackageName();
        //          ComponentName componentName = new ComponentName(packageName,
        //              packageName + ".wxapi.WXPayEntryActivity");
        //          intent.setComponent(componentName);
        //          intent.putExtra(PaymentActivity.EXTRA_CHARGE, string);
        //
        //          startActivityForResult(intent, REQUEST_CODE_PAYMENT);
        //        } catch (IOException e) {
        //          e.printStackTrace();
        //        }
        //      }
        //    });

        tradeModel.shoppingcart_create(cart_ids, addr_id, "wx", payment, post_fee,
            discount_fee, total_fee, uuid)
            .subscribeOn(Schedulers.io())
            .subscribe(new ServiceResponse<ResponseBody>() {
              @Override public void onNext(ResponseBody responseBody) {
                super.onNext(responseBody);
                try {
                  String string = responseBody.string();
                  Log.i("charge", string);
                  Intent intent = new Intent();
                  String packageName = getPackageName();
                  ComponentName componentName = new ComponentName(packageName,
                      packageName + ".wxapi.WXPayEntryActivity");
                  intent.setComponent(componentName);
                  intent.putExtra(PaymentActivity.EXTRA_CHARGE, string);
                  startActivityForResult(intent, REQUEST_CODE_PAYMENT);
                } catch (IOException e) {
                  e.printStackTrace();
                }
              }
            });

        break;
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    //支付页面返回处理
    if (requestCode == REQUEST_CODE_PAYMENT) {
      if (resultCode == Activity.RESULT_OK) {
        String result = data.getExtras().getString("pay_result");
            /* 处理返回值
             * "success" - payment succeed
             * "fail"    - payment failed
             * "cancel"  - user canceld
             * "invalid" - payment plugin not installed
             *
             */
        String errorMsg = data.getExtras().getString("error_msg"); // 错误信息
        String extraMsg = data.getExtras().getString("extra_msg"); // 错误信息
        showMsg(result, errorMsg, extraMsg);
        //JUtils.Toast(result + "" + errorMsg + "" + extraMsg);
      }
    }
  }

  public void showMsg(String title, String msg1, String msg2) {
    String str = title;
    if (null != msg1 && msg1.length() != 0) {
      str += "\n" + msg1;
    }
    if (null != msg2 && msg2.length() != 0) {
      str += "\n" + msg2;
    }
    AlertDialog.Builder builder = new AlertDialog.Builder(CartsPayInfoActivity.this);
    builder.setMessage(str);
    builder.setTitle("提示");
    builder.setPositiveButton("OK", null);
    builder.create().show();
  }
}
