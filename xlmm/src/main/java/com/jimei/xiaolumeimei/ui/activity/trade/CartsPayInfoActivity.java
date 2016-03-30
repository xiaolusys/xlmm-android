package com.jimei.xiaolumeimei.ui.activity.trade;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.CartsPayInfoAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.AddressBean;
import com.jimei.xiaolumeimei.entities.CartsPayinfoBean;
import com.jimei.xiaolumeimei.entities.PayInfoBean;
import com.jimei.xiaolumeimei.model.AddressModel;
import com.jimei.xiaolumeimei.model.CartsModel;
import com.jimei.xiaolumeimei.model.TradeModel;
import com.jimei.xiaolumeimei.ui.activity.main.MainActivity;
import com.jimei.xiaolumeimei.ui.activity.user.AddNoAddressActivity;
import com.jimei.xiaolumeimei.ui.activity.user.AddressSelectActivity;
import com.jimei.xiaolumeimei.ui.activity.user.CouponSelectActivity;
import com.jimei.xiaolumeimei.widget.NestedListView;
import com.jimei.xiaolumeimei.widget.SmoothCheckBox;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.pingplusplus.android.PaymentActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Created by 优尼世界 on 2016/01/15.
 * <p>
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class CartsPayInfoActivity extends BaseSwipeBackCompatActivity
    implements View.OnClickListener, RadioGroup.OnCheckedChangeListener,
    SmoothCheckBox.OnCheckedChangeListener {
  private static final int REQUEST_CODE_PAYMENT = 1;
  private static final int REQUEST_CODE_COUPONT = 2;
  private static final int REQUEST_CODE_ADDRESS = 3;
  private static final String APP_PAY = "pid:1:value:";
  private static final String BUDGET_PAY = "pid:3:budget:";
  private static final String ALIPAY = "alipay";
  private static final String WX = "wx";
  private static final String BUDGET = "budget";
  String TAG = "CartsPayInfoActivity";
  CartsPayInfoAdapter mAdapter;
  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.name) TextView name;
  @Bind(R.id.phone) TextView phone;
  @Bind(R.id.address_details) TextView addressDetails;
  @Bind(R.id.choose_address) TextView chooseAddress;
  @Bind(R.id.adress) RelativeLayout adress;
  @Bind(R.id.payinfo_listview) NestedListView payinfoListview;
  @Bind(R.id.total_price) TextView totalPrice;
  @Bind(R.id.total_price_all) TextView totalPrice_all;
  @Bind(R.id.jiehsneg) TextView jiesheng;
  @Bind(R.id.confirm) Button confirm;
  @Bind(R.id.pay_rg) RadioGroup rg_pay;
  @Bind(R.id.post_fee) TextView tv_postfee;
  @Bind(R.id.coupon_layout) RelativeLayout coupon_layout;
  @Bind(R.id.tv_coupon) TextView tv_coupon;
  List<CartsPayinfoBean.CartListEntity> list;
  @Bind(R.id.go_main) Button goMain;
  @Bind(R.id.empty_content) RelativeLayout emptyContent;
  @Bind(R.id.scb) SmoothCheckBox scb;
  @Bind(R.id.tv_app_discount) TextView tv_app_discount;

  @Bind(R.id.extra_budget) TextView extraBudget;
  private boolean isAlipay, isWx, isBudget;
  //@Bind(R.id.payinfo_recyclerview) RecyclerView payinfoRecyclerview;
  private String ids;
  private String cart_ids;
  private String addr_id;
  private String channel;
  private double payment;
  private String post_fee;
  private String discount_fee;
  private String total_fee;
  private String uuid;
  private String coupon_id;
  private boolean isCoupon;
  private double coupon_price;
  private boolean isHaveAddress;
  private boolean isSelectAddress;
  private String nameSelect;
  private String phoneSelect;
  private String addressDetailsSelect;
  private String addr_idSelect;
  private double paymentInfo;
  private double discount_feeInfo;
  private double total_feeInfo;
  private boolean budget_payable;
  private String pay_extras, pay_extras_budget;
  private double jieshengjine;
  private boolean isEnough;
  private String budgetCash;
  private double real_use_yue;
  private double yue;
  private double appcut;
  private boolean useCouponAllowed = false;

  @Override protected void setListener() {
    adress.setOnClickListener(this);
    confirm.setOnClickListener(this);
    rg_pay.setOnCheckedChangeListener(this);
    scb.setOnCheckedChangeListener(this);
  }

  @Override protected void initData() {

    list = new ArrayList<>();

    downLoadCartsInfo();
  }

  private void downLoadCartsInfo() {
    Subscription subscription = CartsModel.getInstance()
        .getCartsInfoList(ids)
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<CartsPayinfoBean>() {
          @Override public void onNext(CartsPayinfoBean cartsPayinfoBean) {
            if (cartsPayinfoBean != null) {
              JUtils.Log(TAG, cartsPayinfoBean.toString());
              mAdapter.updateWithClear(cartsPayinfoBean.getCartList());
              cart_ids = cartsPayinfoBean.getCartIds();
              //              channel = "alipay";

              budgetCash = cartsPayinfoBean.getBudget_cash() + "";
              payment = cartsPayinfoBean.getTotalFee() + cartsPayinfoBean.getPostFee()
                  - cartsPayinfoBean.getDiscountFee();
              paymentInfo = cartsPayinfoBean.getTotalFee() + cartsPayinfoBean.getPostFee()
                  - cartsPayinfoBean.getDiscountFee();
              post_fee = (double)(Math.round(cartsPayinfoBean.getPostFee()*100)) / 100 + "";
              discount_fee = (float) (Math.round((cartsPayinfoBean.getDiscountFee()) * 100)) / 100 + "";
              JUtils.Log(TAG, "discount_fee" + discount_fee);
              discount_feeInfo = cartsPayinfoBean.getDiscountFee();
              total_fee = cartsPayinfoBean.getTotalFee() + "";
              total_feeInfo = cartsPayinfoBean.getTotalFee();
              uuid = cartsPayinfoBean.getUuid();

              JUtils.Log(TAG, "post-fee" + post_fee);
              JUtils.Log(TAG, "payment" + paymentInfo);
              JUtils.Log(TAG, "合计" + (cartsPayinfoBean.getTotalFee()));
              JUtils.Log(TAG, "已节省" + discount_fee);

              tv_postfee.setText("¥" + post_fee + "元");

              if (null != cartsPayinfoBean.getmPayExtras()) {
                List<CartsPayinfoBean.payExtrasEntityApp> payExtrasEntityApps =
                    cartsPayinfoBean.getmPayExtras();


                for (int i = 0; i < payExtrasEntityApps.size(); i++) {
                  //优惠券
                  if (payExtrasEntityApps.get(i).getPid() == 2) {
                    if (payExtrasEntityApps.get(i).getUseCouponAllowed() == 1) {

                      coupon_layout.setOnClickListener(CartsPayInfoActivity.this);

                      if (isCoupon) {
                        useCouponAllowed = true;
                        pay_extras = "pid:"
                            + payExtrasEntityApps.get(i).getValue()
                            + ":couponid:"
                            + coupon_id
                            + ":value:"
                            + coupon_price
                            + ";";

                        continue;
                      }
                      else {
                        coupon_price = 0;
                      }
                    } else {
                      tv_coupon.setText("无可用优惠券");
                      coupon_layout.setOnClickListener(null);
                      pay_extras = "";
                      useCouponAllowed = false;
                      coupon_price = 0;
                    }
                  }

                  //app支付立减两元
                  if (payExtrasEntityApps.get(i).getPid() == 1) {

                    appcut = payExtrasEntityApps.get(i).getValue();

                    pay_extras += "pid:"
                        + payExtrasEntityApps.get(i).getPid()
                        + ":value:"
                        + payExtrasEntityApps.get(i).getValue()
                        + ";";


                    continue;
                  }

                  //使用钱包
                  if (payExtrasEntityApps.get(i).getPid() == 3) {
                      try {
                        yue = payExtrasEntityApps.get(i).getValue();
                        JUtils.Log(TAG, yue + ":yue");

                      } catch (NullPointerException e) {
                        e.printStackTrace();
                      }

                    }
                  }
                }

            calcAllPrice();

            JUtils.Log(TAG, "discount_fee" + discount_fee);
            JUtils.Log(TAG, "downLoadCartsInfo:"+cart_ids + "    " + addr_id + "    " + "    " +
                payment + "    " + post_fee + "    " +
                discount_fee + "    " + total_fee + "    " + uuid + "    " +
                pay_extras);

              //List<CartsPayinfoBean.payExtrasEntityApp> payExtrasEntities =
              //    cartsPayinfoBean.getmPayExtras();
              //
              ////if (payExtrasEntities.size() > 1) {
              //payExtrasEntityApp = payExtrasEntities.get(0);
              //pay_extras = "pid:"
              //    + payExtrasEntityApp.getPid()
              //    + ":value:"
              //    + payExtrasEntityApp.getValue();
              //
              //if (payExtrasEntities.size() > 1) {
              //  payExtrasEntityAppBudget = payExtrasEntities.get(2);
              //  pay_extras_budget = "pid:"
              //      + payExtrasEntityApp.getPid()
              //      + ":value:"
              //      + payExtrasEntityApp.getValue()
              //      + ";"
              //      + "pid:"
              //      + payExtrasEntityAppBudget.getPid()
              //      + ":budget:"
              //      + payExtrasEntityAppBudget.getValue();
              //}
              //

              ////} else {
              //
              ////}
              //
              //payment = cartsPayinfoBean.getTotalFee() + cartsPayinfoBean.getPostFee()
              //    - cartsPayinfoBean.getDiscountFee()
              //    - payExtrasEntityApp.getValue() + "";
              //
              //paymentInfo = cartsPayinfoBean.getTotalFee() + cartsPayinfoBean.getPostFee()
              //    - cartsPayinfoBean.getDiscountFee()
              //    - payExtrasEntityApp.getValue();
              //
              //post_fee = cartsPayinfoBean.getPostFee() + "";
              //
              //discount_fee = cartsPayinfoBean.getDiscountFee() + "";
              //JUtils.Log(TAG, discount_fee);
              //discount_feeInfo = cartsPayinfoBean.getDiscountFee();
              //total_fee = cartsPayinfoBean.getTotalFee() + "";
              //
              //total_feeInfo =
              //    cartsPayinfoBean.getTotalFee() - payExtrasEntityApp.getValue();
              //
              //uuid = cartsPayinfoBean.getUuid();
              //
              //tv_postfee.setText("¥" + post_fee);
              //
              //pay_extra.setText(payExtrasEntityApp.getName());
              //totalPrice.setText("¥" + payment);
              //totalPrice_all.setText("合计: ¥" + (cartsPayinfoBean.getTotalFee()
              //    - payExtrasEntityApp.getValue()) +
              //    "");
              //jieshengjine =
              //    cartsPayinfoBean.getDiscountFee() + payExtrasEntityApp.getValue();
              //jiehsneg.setText("已节省"
              //    + (float) (Math.round(
              //    (cartsPayinfoBean.getDiscountFee() + payExtrasEntityApp.getValue())
              //        * 100)) / 100
              //    + "");
              ////}
              //
              //budget_payable = cartsPayinfoBean.isBudget_payable();
              //
              ////if (Double.parseDouble(total_fee) < 150) {
              ////  coupon_layout.setOnClickListener(null);
              ////  tv_coupon.setText("无可用优惠券");
              ////} else {
              //coupon_layout.setOnClickListener(CartsPayInfoActivity.this);
              //
              //JUtils.Log(TAG, cart_ids + "    " + addr_id + "    " + "    " +
              //    payment + "    " + post_fee + "    " +
              //    discount_fee + "    " + total_fee + "    " + uuid);
              //}
            } else {
              emptyContent.setVisibility(View.VISIBLE);
              goMain.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                  startActivity(
                      new Intent(CartsPayInfoActivity.this, MainActivity.class));
                  finish();
                }
              });
            }
          }
        });
    addSubscription(subscription);
  }

  private void calcAllPrice() {
    jieshengjine = coupon_price + appcut;
    paymentInfo = payment;

    if(Double.compare(coupon_price + appcut - paymentInfo, 0) >= 0 )
    {
      yue = 0;
      real_use_yue = 0;
      paymentInfo = 0;
      jieshengjine = payment;
    }
    else{
      //算余额
      if(isBudget) {
        if (Double.compare(yue, paymentInfo - coupon_price - appcut) >= 0) {
          yue = paymentInfo - coupon_price - appcut;
          paymentInfo = 0;
        } else {
          paymentInfo = paymentInfo - coupon_price - appcut - yue;
        }
        real_use_yue = yue;
      }
      else{
        if (Double.compare(yue, paymentInfo - coupon_price - appcut) >= 0) {
          yue = paymentInfo - coupon_price - appcut;
        }
        paymentInfo = paymentInfo - coupon_price - appcut;
        real_use_yue = 0;
      }
    }

    JUtils.Log(TAG, "yue:"+yue+" real use yue:"+real_use_yue + " paymentInfo:"+paymentInfo + " jieshengjine:"+jieshengjine);

    tv_app_discount.setText("-"+(double)(Math.round(appcut * 100))/100+"元");
    extraBudget.setText("余额抵扣:   余额剩余" + budgetCash + " 本次可使用 " + yue);
    totalPrice.setText("¥" + (double)(Math.round(paymentInfo * 100))/100);
    totalPrice_all.setText("合计: ¥" + (double)(Math.round(paymentInfo * 100))/100 + "");
    jiesheng.setText("已节省" + (double)(Math.round(jieshengjine * 100))/100 + "");
  }

  @Override protected void getBundleExtras(Bundle extras) {
    ids = extras.getString("ids");
  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.cartspayinfo_activity;
  }

  @Override protected void initViews() {
    toolbar.setTitle("");
    setSupportActionBar(toolbar);
    finishBack(toolbar);
    mAdapter = new CartsPayInfoAdapter(this, list);
    payinfoListview.setAdapter(mAdapter);
  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }

  @Override public void onClick(View v) {

    switch (v.getId()) {
      case R.id.adress:

        if (isHaveAddress) {

          Intent intent =
              new Intent(CartsPayInfoActivity.this, AddressSelectActivity.class);
          startActivityForResult(intent, REQUEST_CODE_ADDRESS);
        } else {
          startActivity(
              new Intent(CartsPayInfoActivity.this, AddNoAddressActivity.class));
        }

        break;

      case R.id.confirm:
        if (isHaveAddress) {

          if (!isCoupon && !isBudget && !isWx && !isAlipay) {
            JUtils.Toast("请选择支付方式");
            return;
          }

          if (isCoupon) {

            if (!isAlipay && !isWx && !isBudget) {
              if (paymentInfo == 0) {
                pay_extras = "pid:"
                    + 2
                    + ":couponid:"
                    + coupon_id
                    + ":value:"
                    + coupon_price
                    + ";"
                    + APP_PAY+appcut+";";
                payV2(BUDGET, paymentInfo+real_use_yue+"", pay_extras, (jieshengjine) + "");
              } else {
                JUtils.Toast("优惠券金额不足,可以选择其它混合支付");
              }
            } else if (isBudget && !isAlipay && !isWx) {
              if (paymentInfo == 0) {
                pay_extras = "pid:"
                    + 2
                    + ":couponid:"
                    + coupon_id
                    + ":value:"
                    + coupon_price
                    + ";"
                    + APP_PAY+appcut+";"
                    + BUDGET_PAY
                    + yue
                    + ";";

                JUtils.Log(TAG, pay_extras);
                payV2(BUDGET, paymentInfo+real_use_yue + "", pay_extras, (jieshengjine) + "");
              } else {
                JUtils.Toast("金额不足,可以选择下面一种支付方式混合支付");
              }
            } else {

              pay_extras = "pid:"
                      + 2
                      + ":couponid:"
                      + coupon_id
                      + ":value:"
                      + coupon_price
                      + ";"
                      + APP_PAY+appcut+";"
                      ;
              if (isBudget) {
                pay_extras = "pid:"
                    + 2
                    + ":couponid:"
                    + coupon_id
                    + ":value:"
                    + coupon_price
                    + ";"
                    + APP_PAY+appcut+";"
                    + BUDGET_PAY
                    + yue
                    + ";";
              }
              if (paymentInfo == 0) {
                channel = BUDGET;
              }
              else {
                if (isAlipay) {
                  channel = ALIPAY;
                } else {
                  channel = WX;
                }
              }

              payV2(channel, (paymentInfo+real_use_yue) + "", pay_extras,
                  (jieshengjine) + "");
            }
          } else {

            if (isAlipay && !isBudget && !isWx) {
              pay_extras = APP_PAY + appcut+";";
              payV2(ALIPAY, (paymentInfo+real_use_yue) + "", pay_extras, jieshengjine + "");
            }
            if (isWx && !isAlipay && !isBudget) {
              pay_extras = APP_PAY+ appcut+";";
              payV2(WX, (paymentInfo+real_use_yue) + "", pay_extras, jieshengjine + "");
            }

            if (isBudget) {
              if (((paymentInfo > 0)) && !isAlipay && !isWx) {
                JUtils.Toast("余额不足,请选择下面一种方式一起支付");
              }
              else{
                if (paymentInfo == 0) {
                  channel = BUDGET;
                }
                else {

                  if (isAlipay) {
                    channel = ALIPAY;
                  } else if (isWx) {
                    channel = WX;
                  }
                }
                pay_extras = APP_PAY+ appcut+";" + BUDGET_PAY + yue + ";";
                payV2(channel, (paymentInfo+real_use_yue) + "", pay_extras, jieshengjine + "");
              }

            }
          }
        } else {
          JUtils.Toast("你还未设置地址");
        }

        break;

      case R.id.coupon_layout:
        Intent intent = new Intent(CartsPayInfoActivity.this, CouponSelectActivity.class);
        if ((coupon_id != null) && (!coupon_id.isEmpty())) {
          intent.putExtra("coupon_id", coupon_id);
        }
        startActivityForResult(intent, REQUEST_CODE_COUPONT);
        break;
    }
  }

  //private void payWithBudget(String pay_method, String pay_extrasaa) {
  //  JUtils.Log(TAG, cart_ids + "    " + addr_id + "    " + pay_method + "    " +
  //      payment + "    " + post_fee + "    " +
  //      discount_fee + "    " + total_fee + "    " + uuid + "  " + pay_extrasaa);
  //
  //  showIndeterminateProgressDialog(false);
  //
  //  Subscription subscription = TradeModel.getInstance()
  //      .shoppingcart_create_v2(ids, addr_id, pay_method, payment, post_fee, discount_fee,
  //          total_fee, uuid, pay_extrasaa)
  //      .subscribeOn(Schedulers.io())
  //      .subscribe(new ServiceResponse<PayInfoBean>() {
  //
  //        @Override public void onCompleted() {
  //          super.onCompleted();
  //          hideIndeterminateProgressDialog();
  //        }
  //
  //        @Override public void onError(Throwable e) {
  //          super.onError(e);
  //          e.printStackTrace();
  //        }
  //
  //        @Override public void onNext(PayInfoBean payInfoBean) {
  //
  //          if (null != payInfoBean) {
  //            JUtils.Log(TAG, payInfoBean.toString());
  //
  //            if (isBudget
  //                && TextUtils.isEmpty(payInfoBean.getCharge().toString())
  //                && payInfoBean.getCode() == 0) {
  //              JUtils.Toast("支付成功");
  //            }
  //
  //            if (isBudget && !TextUtils.isEmpty(payInfoBean.getCharge().toString())) {
  //              Intent intent = new Intent();
  //              String packageName = getPackageName();
  //              ComponentName componentName = new ComponentName(packageName,
  //                  packageName + ".wxapi.WXPayEntryActivity");
  //              intent.setComponent(componentName);
  //              intent.putExtra(PaymentActivity.EXTRA_CHARGE,
  //                  payInfoBean.getCharge().toString());
  //              startActivityForResult(intent, REQUEST_CODE_PAYMENT);
  //            }
  //
  //            if (!isBudget && !TextUtils.isEmpty(payInfoBean.getCharge().toString())) {
  //              Intent intent = new Intent();
  //              String packageName = getPackageName();
  //              ComponentName componentName = new ComponentName(packageName,
  //                  packageName + ".wxapi.WXPayEntryActivity");
  //              intent.setComponent(componentName);
  //              intent.putExtra(PaymentActivity.EXTRA_CHARGE,
  //                  payInfoBean.getCharge().toString());
  //              startActivityForResult(intent, REQUEST_CODE_PAYMENT);
  //            }
  //          }
  //        }
  //      });
  //
  //  addSubscription(subscription);
  //}

  private void payV2(String pay_method, String paymentprice_v2, String pay_extrasaa,
      String discount_fee_price) {
    JUtils.Log("CartsPayinfo",
        cart_ids + "    " + addr_id + "    " + pay_method + "    " +
                paymentprice_v2 + "    " + post_fee + "    " +
                discount_fee_price + "    " + total_fee + "    " + uuid + "  " + pay_extrasaa);

    showIndeterminateProgressDialog(false);
    Subscription subscription = TradeModel.getInstance()
        .shoppingcart_create_v2(ids, addr_id, pay_method, paymentprice_v2, post_fee,
            discount_fee_price, total_fee, uuid, pay_extrasaa)
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<PayInfoBean>() {

          @Override public void onCompleted() {
            super.onCompleted();
            hideIndeterminateProgressDialog();
          }

          @Override public void onError(Throwable e) {
            super.onError(e);
            e.printStackTrace();
          }

          @Override public void onNext(PayInfoBean payInfoBean) {

            if (null != payInfoBean) {
              JUtils.Log(TAG, payInfoBean.toString());
              Gson gson = new Gson();
              JUtils.Log(TAG, gson.toJson(payInfoBean.getCharge()));
              if ((payInfoBean.getChannel() != null) && (!payInfoBean.getChannel().equals("budget"))) {

                if (payInfoBean.getCode() > 0) {
                  JUtils.Toast(payInfoBean.getInfo());
                } else {
                  Intent intent = new Intent();
                  String packageName = getPackageName();
                  ComponentName componentName = new ComponentName(packageName,
                      packageName + ".wxapi.WXPayEntryActivity");
                  intent.setComponent(componentName);

                  intent.putExtra(PaymentActivity.EXTRA_CHARGE,
                      gson.toJson(payInfoBean.getCharge()));
                  startActivityForResult(intent, REQUEST_CODE_PAYMENT);
                }
              } else {
                if (payInfoBean.getCode() == 0) {
                  JUtils.Toast("支付成功");
                } else {
                  JUtils.Toast(payInfoBean.getInfo());
                }
              }
            }
          }
        });

    addSubscription(subscription);
  }

  //private void payWithNoCoupon(String pay_method) {
  //  JUtils.Log("CartsPayinfo",
  //      cart_ids + "    " + addr_id + "    " + pay_method + "    " +
  //          payment + "    " + post_fee + "    " +
  //          discount_fee + "    " + total_fee + "    " + uuid);
  //  showIndeterminateProgressDialog(false);
  //  Subscription subscription = TradeModel.getInstance()
  //      .shoppingcart_create(cart_ids, addr_id, pay_method, payment, post_fee,
  //          discount_fee, total_fee, pay_extras, uuid)
  //      .subscribeOn(Schedulers.io())
  //      .subscribe(new ServiceResponse<ResponseBody>() {
  //
  //        @Override public void onCompleted() {
  //          super.onCompleted();
  //          hideIndeterminateProgressDialog();
  //        }
  //
  //        @Override public void onError(Throwable e) {
  //          super.onError(e);
  //          e.printStackTrace();
  //        }
  //
  //        @Override public void onNext(ResponseBody responseBody) {
  //          super.onNext(responseBody);
  //          try {
  //            String string = responseBody.string();
  //            Log.i("charge", string);
  //            Intent intent = new Intent();
  //            String packageName = getPackageName();
  //            ComponentName componentName = new ComponentName(packageName,
  //                packageName + ".wxapi.WXPayEntryActivity");
  //            intent.setComponent(componentName);
  //            intent.putExtra(PaymentActivity.EXTRA_CHARGE, string);
  //            startActivityForResult(intent, REQUEST_CODE_PAYMENT);
  //          } catch (IOException e) {
  //            e.printStackTrace();
  //          }
  //        }
  //      });
  //  addSubscription(subscription);
  //}

  //private void payWithBudget(String pay_method) {
  //  JUtils.Log("CartsPayinfo",
  //      cart_ids + "    " + addr_id + "    " + pay_method + "    " +
  //          payment + "    " + post_fee + "    " +
  //          discount_fee + "    " + total_fee + "    " + uuid);
  //  showIndeterminateProgressDialog(false);
  //  Subscription subscription = TradeModel.getInstance()
  //      .shoppingcart_createBudget(cart_ids, addr_id, pay_method, payment, post_fee,
  //          discount_fee, total_fee, pay_extras, uuid)
  //      .subscribeOn(Schedulers.io())
  //      .subscribe(new ServiceResponse<BudgetPayBean>() {
  //        @Override public void onCompleted() {
  //          super.onCompleted();
  //          hideIndeterminateProgressDialog();
  //        }
  //
  //        @Override public void onError(Throwable e) {
  //          super.onError(e);
  //          e.printStackTrace();
  //        }
  //
  //        @Override public void onNext(BudgetPayBean responseBody) {
  //          if (responseBody != null) {
  //            boolean success = responseBody.isSuccess();
  //            if (success) {
  //              JUtils.Toast("支付成功");
  //            } else {
  //              JUtils.Toast(responseBody.getInfo());
  //            }
  //          }
  //        }
  //      });
  //  addSubscription(subscription);
  //}
  //
  //private void payWithBudgetCoupon(String pay_method) {
  //  BigDecimal bd = new BigDecimal((paymentInfo - coupon_price));
  //  double bigDecimal = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
  //  showIndeterminateProgressDialog(false);
  //  Subscription subscription = TradeModel.getInstance()
  //      .shoppingcart_createBudget_with_coupon(cart_ids, addr_id, pay_method,
  //          bigDecimal + "", post_fee, discount_fee, total_fee, pay_extras, uuid,
  //          coupon_id)
  //      .subscribeOn(Schedulers.io())
  //      .subscribe(new ServiceResponse<BudgetPayBean>() {
  //
  //        @Override public void onNext(BudgetPayBean responseBody) {
  //          if (responseBody != null) {
  //            boolean success = responseBody.isSuccess();
  //            if (success) {
  //              JUtils.Toast("支付成功");
  //            } else {
  //              JUtils.Toast(responseBody.getInfo());
  //            }
  //          }
  //        }
  //
  //        @Override public void onCompleted() {
  //          super.onCompleted();
  //          hideIndeterminateProgressDialog();
  //        }
  //
  //        @Override public void onError(Throwable e) {
  //          super.onError(e);
  //          e.printStackTrace();
  //        }
  //      });
  //  addSubscription(subscription);
  //}

  /*private void payWithCoupon(String pay_method) {

    BigDecimal bd = new BigDecimal((paymentInfo - coupon_price));
    double bigDecimal = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    showIndeterminateProgressDialog(false);
    Subscription subscription = TradeModel.getInstance()
        .shoppingcart_create_with_coupon(cart_ids, addr_id, pay_method, bigDecimal + "",
            post_fee, discount_fee, total_fee, pay_extras, uuid, coupon_id)
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<ResponseBody>() {
          @Override public void onNext(ResponseBody responseBody) {
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

          @Override public void onCompleted() {
            super.onCompleted();
            hideIndeterminateProgressDialog();
          }

          @Override public void onError(Throwable e) {
            super.onError(e);
            e.printStackTrace();
          }
        });
    addSubscription(subscription);
  }*/

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
        assert result != null;
        if (result.equals("cancel")) {
          //wexin alipay already showmsg
          JUtils.Toast("你已取消支付!");
        } else if (result.equals("success")) {
          JUtils.Toast("支付成功！");
          startActivity(new Intent(CartsPayInfoActivity.this, MainActivity.class));
          finish();
        } else {
          showMsg(result, errorMsg, extraMsg);
          //JUtils.Toast(result + "" + errorMsg + "" + extraMsg);
        }
      }
    }
    if (requestCode == REQUEST_CODE_COUPONT) {
      if (resultCode == Activity.RESULT_OK) {

        coupon_id = data.getStringExtra("coupon_id");
        coupon_price = data.getDoubleExtra("coupon_price", 0);

        tv_coupon.setText(coupon_price + "元优惠券");

        calcAllPrice();

        JUtils.Log(TAG, "coupon_id:" + coupon_id);
        Subscription subscription =
            CartsModel.getInstance()
                .getCartsInfoList(ids, coupon_id)
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<CartsPayinfoBean>() {
                  @Override public void onNext(CartsPayinfoBean cartsPayinfoBean) {
                    if (cartsPayinfoBean != null) {
                      payment = cartsPayinfoBean.getTotalFee() + cartsPayinfoBean.getPostFee()
                              - cartsPayinfoBean.getDiscountFee();

                      if (TextUtils.isEmpty(cartsPayinfoBean.getmCoupon_message())) {
                        if ((coupon_id == null) || coupon_id.isEmpty() || (0
                            == Double.compare(coupon_price, 0))) {
                          isCoupon = false;
                          tv_coupon.setText("");
                        } else {
                          isCoupon = true;
                        }
                      } else {
                        isCoupon = false;
                        tv_coupon.setText("");
                        new MaterialDialog.Builder(CartsPayInfoActivity.this).
                            content(cartsPayinfoBean.getmCoupon_message()).
                            positiveText("OK").
                            callback(new MaterialDialog.ButtonCallback() {
                              @Override public void onPositive(MaterialDialog dialog) {
                                downLoadCartsInfo();

                                dialog.dismiss();
                              }

                              @Override public void onNegative(MaterialDialog dialog) {
                                dialog.dismiss();
                              }
                            }).show();
                      }
                    }
                  }
                });
        addSubscription(subscription);
      }
    }

    if (requestCode == REQUEST_CODE_ADDRESS) {
      if (resultCode == Activity.RESULT_OK) {
        isSelectAddress = true;
        nameSelect = data.getStringExtra("name");
        phoneSelect = data.getStringExtra("phone");
        addressDetailsSelect = data.getStringExtra("addressDetails");
        addr_idSelect = data.getStringExtra("addr_id");
      } else {
        isSelectAddress = false;
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
    JUtils.Log(TAG, "charge result" + str);
    if (title.equals("fail")) {
      str = "支付失败，请重试！";
    } else if (title.equals("invalid")) {
      str = "支付失败，支付软件未安装完整！";
    }

    AlertDialog.Builder builder = new AlertDialog.Builder(CartsPayInfoActivity.this);
    builder.setMessage(str);
    builder.setTitle("提示");
    builder.setPositiveButton("OK", null);
    builder.create().show();
  }

  @Override public void onCheckedChanged(RadioGroup group, int checkedId) {
    if (checkedId == R.id.alipay) {
      isAlipay = true;
      isWx = false;
    } else if (checkedId == R.id.wx) {
      isWx = true;
      isAlipay = false;
    }
    //
    //    else if (checkedId == R.id.budget) {
    //      isWx = false;
    //      isAlipay = false;
    //      if (budget_payable) {
    //        isBudget = true;
    //      } else {
    //        JUtils.Toast("钱包不可用,请选择其他支付方式");
    //        isBudget = false;
    //      }
    //    }
  }

  @Override protected void onResume() {

    super.onResume();

    if (isSelectAddress && chooseAddress != null) {

      chooseAddress.setVisibility(View.INVISIBLE);

      name.setVisibility(View.VISIBLE);
      phone.setVisibility(View.VISIBLE);
      addressDetails.setVisibility(View.VISIBLE);

      name.setText(nameSelect);
      phone.setText(phoneSelect);
      addressDetails.setText(addressDetailsSelect);

      addr_id = addr_idSelect;
    } else {
      Subscription subscription = AddressModel.getInstance()
          .getAddressList()
          .subscribeOn(Schedulers.io())
          .subscribe(new ServiceResponse<List<AddressBean>>() {
            @Override public void onNext(List<AddressBean> list) {
              super.onNext(list);
              if (list != null && chooseAddress != null) {
                chooseAddress.setVisibility(View.INVISIBLE);

                name.setVisibility(View.VISIBLE);
                phone.setVisibility(View.VISIBLE);
                addressDetails.setVisibility(View.VISIBLE);
                AddressBean addressBean = list.get(0);

                addr_id = addressBean.getId();
                JUtils.Log(TAG, addr_id + "addr_id");
                name.setText(addressBean.getReceiverName());
                phone.setText(addressBean.getReceiverMobile());

                addressDetails.setText(addressBean.getReceiverState()
                    + addressBean.getReceiverCity()
                    + addressBean.getReceiverDistrict()
                    + addressBean.getReceiverAddress());
                isHaveAddress = true;
              } else {
                if (chooseAddress != null) {
                  chooseAddress.setVisibility(View.VISIBLE);
                }
                name.setVisibility(View.INVISIBLE);
                phone.setVisibility(View.INVISIBLE);
                addressDetails.setVisibility(View.INVISIBLE);
                isHaveAddress = false;
              }
            }

            @Override public void onError(Throwable e) {
              super.onError(e);
              if (chooseAddress != null) {
                chooseAddress.setVisibility(View.VISIBLE);
              }
              name.setVisibility(View.INVISIBLE);
              phone.setVisibility(View.INVISIBLE);
              addressDetails.setVisibility(View.INVISIBLE);
              isHaveAddress = false;
            }
          });
      addSubscription(subscription);
    }
  }

  @Override public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
    if (isChecked) {
      isBudget = true;
    } else {
      isBudget = false;
    }
    calcAllPrice();
  }
}
