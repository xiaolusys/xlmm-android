package com.jimei.xiaolumeimei.ui.activity.trade;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import com.afollestad.materialdialogs.MaterialDialog;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.CartsPayInfoAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.AddressBean;
import com.jimei.xiaolumeimei.entities.BudgetPayBean;
import com.jimei.xiaolumeimei.entities.CartsPayinfoBean;
import com.jimei.xiaolumeimei.model.AddressModel;
import com.jimei.xiaolumeimei.model.CartsModel;
import com.jimei.xiaolumeimei.model.TradeModel;
import com.jimei.xiaolumeimei.ui.activity.main.MainActivity;
import com.jimei.xiaolumeimei.ui.activity.user.AddNoAddressActivity;
import com.jimei.xiaolumeimei.ui.activity.user.AddressSelectActivity;
import com.jimei.xiaolumeimei.ui.activity.user.CouponSelectActivity;
import com.jimei.xiaolumeimei.widget.NestedListView;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.pingplusplus.android.PaymentActivity;
import com.squareup.okhttp.ResponseBody;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Created by 优尼世界 on 2016/01/15.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class CartsPayInfoActivity extends BaseSwipeBackCompatActivity
    implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
  private static final int REQUEST_CODE_PAYMENT = 1;
  private static final int REQUEST_CODE_COUPONT = 2;
  private static final int REQUEST_CODE_ADDRESS = 3;
  String TAG = "CartsPayInfoActivity";
  CartsModel model = new CartsModel();
  TradeModel tradeModel = new TradeModel();
  AddressModel addressModel = new AddressModel();
  CartsPayInfoAdapter mAdapter;
  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.app_bar_layout) AppBarLayout appBarLayout;
  @Bind(R.id.name) TextView name;
  @Bind(R.id.phone) TextView phone;
  @Bind(R.id.address_details) TextView addressDetails;
  @Bind(R.id.choose_address) TextView chooseAddress;
  @Bind(R.id.adress) RelativeLayout adress;
  @Bind(R.id.payinfo_listview) NestedListView payinfoListview;
  @Bind(R.id.total_price) TextView totalPrice;
  @Bind(R.id.total_price_all) TextView totalPrice_all;
  @Bind(R.id.jiehsneg) TextView jiehsneg;
  @Bind(R.id.confirm) Button confirm;
  @Bind(R.id.pay_rg) RadioGroup rg_pay;
  @Bind(R.id.post_fee) TextView tv_postfee;
  @Bind(R.id.coupon_layout) RelativeLayout coupon_layout;
  @Bind(R.id.tv_coupon) TextView tv_coupon;
  List<CartsPayinfoBean.CartListEntity> list;
  @Bind(R.id.go_main) Button goMain;
  @Bind(R.id.empty_content) RelativeLayout emptyContent;
  private boolean isAlipay, isWx, isBudget;
  //@Bind(R.id.payinfo_recyclerview) RecyclerView payinfoRecyclerview;
  private String ids;
  private String cart_ids;
  private String addr_id;
  private String channel;
  private String payment;
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

  @Override protected void setListener() {
    adress.setOnClickListener(this);
    confirm.setOnClickListener(this);
    rg_pay.setOnCheckedChangeListener(this);
  }

  @Override protected void initData() {

    list = new ArrayList<>();

    downLoadCartsInfo();
  }

  private void downLoadCartsInfo() {
    Subscription subscription = model.getCartsInfoList(ids)
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<CartsPayinfoBean>() {
          @Override public void onNext(CartsPayinfoBean cartsPayinfoBean) {
            if (cartsPayinfoBean != null) {
              mAdapter.updateWithClear(cartsPayinfoBean.getCartList());
              cart_ids = cartsPayinfoBean.getCartIds();
              channel = "alipay";

              payment = cartsPayinfoBean.getTotalFee() + cartsPayinfoBean.getPostFee()
                  - cartsPayinfoBean.getDiscountFee() + "";

              paymentInfo = cartsPayinfoBean.getTotalFee() + cartsPayinfoBean.getPostFee()
                  - cartsPayinfoBean.getDiscountFee();

              post_fee = cartsPayinfoBean.getPostFee() + "";

              discount_fee = cartsPayinfoBean.getDiscountFee() + "";
              discount_feeInfo = cartsPayinfoBean.getDiscountFee();
              total_fee = cartsPayinfoBean.getTotalFee() + "";

              total_feeInfo = cartsPayinfoBean.getTotalFee();

              uuid = cartsPayinfoBean.getUuid();

              tv_postfee.setText("¥" + post_fee);
              //
              //if (isCoupon) {
              //  totalPrice_all.setText(
              //      "合计: ¥" + (cartsPayinfoBean.getTotalFee() - coupon_price) + "");
              //  jiehsneg.setText(
              //      "已节省" + (cartsPayinfoBean.getDiscountFee() + coupon_price) + "");
              //} else {
              totalPrice.setText("¥" + payment);
              totalPrice_all.setText("合计: ¥" + cartsPayinfoBean.getTotalFee() + "");
              jiehsneg.setText("已节省" + cartsPayinfoBean.getDiscountFee() + "");
              //}

              budget_payable = cartsPayinfoBean.isBudget_payable();

              //if (Double.parseDouble(total_fee) < 150) {
              //  coupon_layout.setOnClickListener(null);
              //  tv_coupon.setText("无可用优惠券");
              //} else {
              coupon_layout.setOnClickListener(CartsPayInfoActivity.this);
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

        if (isCoupon) {
          if (isWx) {
            payWithCoupon("wx");
          } else if (isAlipay) {
            payWithCoupon("alipay");
          } else if (isBudget) {
            payWithBudgetCoupon("budget");
          } else {
            JUtils.Toast("请选择支付方式");
          }
        } else {
          if (isAlipay) {
            payWithNoCoupon("alipay");
          } else if (isWx) {
            payWithNoCoupon("wx");
          } else if (isBudget) {
            payWithBudget("budget");
          } else {
            JUtils.Toast("请选择支付方式");
          }
        }

        break;

      case R.id.coupon_layout:
        Intent intent = new Intent(CartsPayInfoActivity.this, CouponSelectActivity.class);
        startActivityForResult(intent, REQUEST_CODE_COUPONT);
        break;
    }
  }

  private void payWithNoCoupon(String pay_method) {
    JUtils.Log("CartsPayinfo",
        cart_ids + "    " + addr_id + "    " + pay_method + "    " +
            payment + "    " + post_fee + "    " +
            discount_fee + "    " + total_fee + "    " + uuid);
    showIndeterminateProgressDialog(false);
    Subscription subscription =
        tradeModel.shoppingcart_create(cart_ids, addr_id, pay_method, payment, post_fee,
            discount_fee, total_fee, uuid)
            .subscribeOn(Schedulers.io())
            .subscribe(new ServiceResponse<ResponseBody>() {

              @Override public void onCompleted() {
                super.onCompleted();
                hideIndeterminateProgressDialog();
              }

              @Override public void onError(Throwable e) {
                super.onError(e);
                e.printStackTrace();
              }

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
    addSubscription(subscription);
  }

  private void payWithBudget(String pay_method) {
    JUtils.Log("CartsPayinfo",
        cart_ids + "    " + addr_id + "    " + pay_method + "    " +
            payment + "    " + post_fee + "    " +
            discount_fee + "    " + total_fee + "    " + uuid);
    showIndeterminateProgressDialog(false);
    Subscription subscription =
        tradeModel.shoppingcart_createBudget(cart_ids, addr_id, pay_method, payment,
            post_fee, discount_fee, total_fee, uuid)
            .subscribeOn(Schedulers.io())
            .subscribe(new ServiceResponse<BudgetPayBean>() {
              @Override public void onCompleted() {
                super.onCompleted();
                hideIndeterminateProgressDialog();
              }

              @Override public void onError(Throwable e) {
                super.onError(e);
                e.printStackTrace();
              }

              @Override public void onNext(BudgetPayBean responseBody) {
                if (responseBody != null) {
                  boolean success = responseBody.isSuccess();
                  if (success) {
                    JUtils.Toast("支付成功");
                  } else {
                    JUtils.Toast(responseBody.getInfo());
                  }
                }
              }
            });
    addSubscription(subscription);
  }

  private void payWithBudgetCoupon(String pay_method) {
    BigDecimal bd = new BigDecimal((paymentInfo - coupon_price));
    double bigDecimal = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    showIndeterminateProgressDialog(false);
    Subscription subscription =
        tradeModel.shoppingcart_createBudget_with_coupon(cart_ids, addr_id, pay_method,
            bigDecimal + "", post_fee, discount_fee, total_fee, uuid, coupon_id)
            .subscribeOn(Schedulers.io())
            .subscribe(new ServiceResponse<BudgetPayBean>() {

              @Override public void onNext(BudgetPayBean responseBody) {
                if (responseBody != null) {
                  boolean success = responseBody.isSuccess();
                  if (success) {
                    JUtils.Toast("支付成功");
                  } else {
                    JUtils.Toast(responseBody.getInfo());
                  }
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
  }

  private void payWithCoupon(String pay_method) {

    BigDecimal bd = new BigDecimal((paymentInfo - coupon_price));
    double bigDecimal = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    showIndeterminateProgressDialog(false);
    Subscription subscription =
        tradeModel.shoppingcart_create_with_coupon(cart_ids, addr_id, pay_method,
            bigDecimal + "", post_fee, discount_fee, total_fee, uuid, coupon_id)
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

        BigDecimal bd = new BigDecimal((paymentInfo - coupon_price));
        double bigDecimal = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        BigDecimal bd1 = new BigDecimal((total_feeInfo - coupon_price));
        double bigDecimal1 = bd1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        totalPrice.setText("¥" + bigDecimal);
        totalPrice_all.setText("合计: ¥" + bigDecimal1);
        jiehsneg.setText("已节省" + coupon_price);
        tv_coupon.setText(coupon_price + "元优惠券");

        JUtils.Log(TAG, "coupon_id:" + coupon_id);
        Subscription subscription = model.getCartsInfoList(ids, coupon_id)

            .subscribeOn(Schedulers.io())
            .subscribe(new ServiceResponse<CartsPayinfoBean>() {
              @Override public void onNext(CartsPayinfoBean cartsPayinfoBean) {
                if (cartsPayinfoBean != null) {
                  if (TextUtils.isEmpty(cartsPayinfoBean.getmCoupon_message())) {
                    if((coupon_id == null) || coupon_id.isEmpty()
                        || (0 == Double.compare(coupon_price, 0)))
                    {
                      isCoupon = false;
                      tv_coupon.setText("");
                    }
                    else {
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
      isBudget = false;
    } else if (checkedId == R.id.wx) {
      isWx = true;
      isAlipay = false;
      isBudget = false;
    } else if (checkedId == R.id.budget) {
      isWx = false;
      isAlipay = false;
      if (budget_payable) {
        isBudget = true;
      } else {
        JUtils.Toast("钱包不可用,请选择其他支付方式");
        isBudget = false;
      }
    }
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
      Subscription subscription = addressModel.getAddressList()
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
}
