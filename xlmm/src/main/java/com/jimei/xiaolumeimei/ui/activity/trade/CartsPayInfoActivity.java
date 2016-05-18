package com.jimei.xiaolumeimei.ui.activity.trade;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;

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

import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Created by 优尼世界 on 2016/01/15.
 * <p>
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class CartsPayInfoActivity extends BaseSwipeBackCompatActivity
        implements View.OnClickListener, SmoothCheckBox.OnCheckedChangeListener, CompoundButton.OnCheckedChangeListener {
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
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.phone)
    TextView phone;
    @Bind(R.id.address_details)
    TextView addressDetails;
    @Bind(R.id.choose_address)
    TextView chooseAddress;
    @Bind(R.id.adress)
    RelativeLayout adress;
    @Bind(R.id.payinfo_listview)
    NestedListView payinfoListview;
    @Bind(R.id.total_price)
    TextView totalPrice;
    @Bind(R.id.total_price_all)
    TextView totalPrice_all;
    @Bind(R.id.jiehsneg)
    TextView jiesheng;
    @Bind(R.id.confirm)
    Button confirm;
    @Bind(R.id.post_fee)
    TextView tv_postfee;
    @Bind(R.id.coupon_layout)
    RelativeLayout coupon_layout;
    @Bind(R.id.tv_coupon)
    TextView tv_coupon;
    List<CartsPayinfoBean.CartListEntity> list;
    @Bind(R.id.go_main)
    Button goMain;
    @Bind(R.id.empty_content)
    RelativeLayout emptyContent;
    @Bind(R.id.scb)
    SmoothCheckBox scb;
    @Bind(R.id.tv_app_discount)
    TextView tv_app_discount;
    @Bind(R.id.extra_budget)
    TextView extraBudget;
    @Bind(R.id.edit_query)
    EditText editText;
    @Bind(R.id.iv_wx)
    ImageView wxImg;
    @Bind(R.id.iv_alipay)
    ImageView alipayImg;
    @Bind(R.id.wx_layout)
    LinearLayout wxLayout;
    @Bind(R.id.alipay_layout)
    LinearLayout alipayLayout;
    @Bind(R.id.cb_rule)
    CheckBox ruleCb;
    @Bind(R.id.tv_rule)
    TextView ruleTv;
    private boolean isAlipay, isWx, isBudget;
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
    private String pay_extras;
    private double jieshengjine;
    private String budgetCash;
    private double real_use_yue;
    private double yue;
    private double appcut;
    private AlertDialog dialog;

    public static void closeInputMethod(Activity context) {
        try {
            InputMethodManager inputMethodManager =
                    (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(
                    context.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            JUtils.Log(TAG_LOG, "输入法关闭异常");
        }
    }

    @Override
    protected void setListener() {
        adress.setOnClickListener(this);
        confirm.setOnClickListener(this);
        wxLayout.setOnClickListener(this);
        alipayLayout.setOnClickListener(this);
        scb.setOnCheckedChangeListener(this);
        ruleCb.setOnCheckedChangeListener(this);
        ruleTv.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        isAlipay = true;

        list = new ArrayList<>();

        downLoadCartsInfo();

        //        Subscription subscribe = UserModel.getInstance()
        //                .getUnusedCouponBean()
        //                .subscribeOn(Schedulers.io())
        //                .subscribe(new ServiceResponse<CouponBean>() {
        //                    @Override
        //                    public void onNext(CouponBean couponBean) {
        //                        results = couponBean.getResults();
        //                        for (int i = 0; i < results.size(); i++) {
        //                            CouponBean.ResultsEntity entity = results.get(i);
        //                            JUtils.Log(TAG,entity.getTitle()+"-->"+entity.getUse_fee());
        //                            if (entity.getPoll_status() == 1 && entity.getStatus() == 0 && entity.getUse_fee() <= (paymentInfo + jieshengjine) && entity.isValid()) {
        //                                if (entity.getCoupon_value() >= coupon_price) {
        //                                    coupon_price = entity.getCoupon_value();
        //                                    coupon_id = entity.getId() + "";
        //                                    tv_coupon.setText(coupon_price + "元优惠券");
        //                                    isCoupon = true;
        //                                }
        //                            }
        //                        }
        //                    }
        //                });
        //        addSubscription(subscribe);

    }

    private void downLoadCartsInfo() {
        Subscription subscription = CartsModel.getInstance()
                .getCartsInfoList(ids)
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<CartsPayinfoBean>() {
                    @Override
                    public void onNext(CartsPayinfoBean cartsPayinfoBean) {
                        if (cartsPayinfoBean != null) {
                            JUtils.Log(TAG, cartsPayinfoBean.toString());
                            mAdapter.updateWithClear(cartsPayinfoBean.getCartList());
                            cart_ids = cartsPayinfoBean.getCartIds();
                            //              channel = "alipay";

                            budgetCash =
                                    (double) (Math.round(cartsPayinfoBean.getBudget_cash() * 100)) / 100
                                            + "";
                            payment = (double) (Math.round(
                                    (cartsPayinfoBean.getTotalFee() + cartsPayinfoBean.getPostFee()
                                            - cartsPayinfoBean.getDiscountFee()) * 100)) / 100;

                            paymentInfo = (double) (Math.round(
                                    (cartsPayinfoBean.getTotalFee() + cartsPayinfoBean.getPostFee()
                                            - cartsPayinfoBean.getDiscountFee()) * 100)) / 100;
                            post_fee =
                                    (double) (Math.round(cartsPayinfoBean.getPostFee() * 100)) / 100 + "";
                            discount_fee =
                                    (float) (Math.round((cartsPayinfoBean.getDiscountFee()) * 100)) / 100
                                            + "";
                            JUtils.Log(TAG, "discount_fee" + discount_fee);
                            discount_feeInfo = cartsPayinfoBean.getDiscountFee();
                            total_fee = cartsPayinfoBean.getTotalFee() + "";
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
                                                pay_extras = "pid:"
                                                        + payExtrasEntityApps.get(i).getValue()
                                                        + ":couponid:"
                                                        + coupon_id
                                                        + ":value:"
                                                        + coupon_price
                                                        + ";";

                                                continue;
                                            } else {
                                                coupon_price = 0;
                                            }
                                        } else {
                                            tv_coupon.setText("无可用优惠券");
                                            coupon_layout.setOnClickListener(null);
                                            pay_extras = "";
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
                            JUtils.Log(TAG,
                                    "downLoadCartsInfo:" + cart_ids + "    " + addr_id + "    " + "    " +
                                            payment + "    " + post_fee + "    " +
                                            discount_fee + "    " + total_fee + "    " + uuid + "    " +
                                            pay_extras);
                        } else {
                            emptyContent.setVisibility(View.VISIBLE);
                            goMain.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
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
        jieshengjine =
                (double) (Math.round((discount_feeInfo + coupon_price + appcut) * 100)) / 100;
        paymentInfo = payment;
        JUtils.Log("CartsPayinfo",
                "jishengjine ===" + jieshengjine + "   payment==" + payment);
        if (Double.compare(coupon_price + appcut - paymentInfo, 0) >= 0) {
            yue = 0;
            real_use_yue = 0;
            paymentInfo = 0;
            jieshengjine = payment;
        } else {
            //算余额
            if (isBudget) {
                if (Double.compare(yue, paymentInfo - coupon_price - appcut) >= 0) {
                    yue = (double) (Math.round((paymentInfo - coupon_price - appcut) * 100)) / 100;

                    paymentInfo = 0;
                } else {
                    paymentInfo =
                            (double) (Math.round((paymentInfo - coupon_price - appcut - yue) * 100))
                                    / 100;
                }
                real_use_yue = yue;
            } else {
                if (Double.compare(yue, paymentInfo - coupon_price - appcut) >= 0) {
                    yue = (double) (Math.round((paymentInfo - coupon_price - appcut) * 100)) / 100;
                }
                paymentInfo =
                        (double) (Math.round((paymentInfo - coupon_price - appcut) * 100)) / 100;
                real_use_yue = 0;
            }
        }

        JUtils.Log("CartsPayinfo", "yue:"
                + yue
                + " real use yue:"
                + real_use_yue
                + " paymentInfo:"
                + paymentInfo
                + " jieshengjine:"
                + jieshengjine);

        tv_app_discount.setText("-" + (double) (Math.round(appcut * 100)) / 100 + "元");
        extraBudget.setText(
                "余额抵扣:   剩余" + budgetCash + " 本次可使用 " + (double) (Math.round(yue * 100)) / 100);
        totalPrice.setText("¥" + (double) (Math.round(paymentInfo * 100)) / 100);
        totalPrice_all.setText("合计: ¥" + (double) (Math.round(paymentInfo * 100)) / 100 + "");
        jiesheng.setText("已节省" + (double) (Math.round(jieshengjine * 100)) / 100 + "");
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        ids = extras.getString("ids");
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.cartspayinfo_activity;
    }

    @Override
    protected void initViews() {


        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        finishBack(toolbar);
        mAdapter = new CartsPayInfoAdapter(this, list);
        payinfoListview.setAdapter(mAdapter);
        View view = getLayoutInflater().inflate(R.layout.dialog_rule, null);
        view.setLayoutParams(new LinearLayout.LayoutParams(200, ViewGroup.LayoutParams.WRAP_CONTENT));
        dialog = new AlertDialog.Builder(this)
                .setView(view)
                .create();
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    @Override
    public void onClick(View v) {
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
                                        + APP_PAY
                                        + appcut
                                        + ";";
                                payV2(BUDGET, paymentInfo + real_use_yue + "", pay_extras,
                                        (jieshengjine) + "");
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
                                        + APP_PAY
                                        + appcut
                                        + ";"
                                        + BUDGET_PAY
                                        + yue
                                        + ";";

                                JUtils.Log(TAG, pay_extras);
                                payV2(BUDGET, paymentInfo + real_use_yue + "", pay_extras,
                                        (jieshengjine) + "");
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
                                    + APP_PAY
                                    + appcut
                                    + ";";
                            if (isBudget) {
                                pay_extras = "pid:"
                                        + 2
                                        + ":couponid:"
                                        + coupon_id
                                        + ":value:"
                                        + coupon_price
                                        + ";"
                                        + APP_PAY
                                        + appcut
                                        + ";"
                                        + BUDGET_PAY
                                        + yue
                                        + ";";
                            }
                            if (paymentInfo == 0) {
                                channel = BUDGET;
                            } else {
                                if (isAlipay) {
                                    channel = ALIPAY;
                                } else {
                                    channel = WX;
                                }
                            }

                            payV2(channel, (paymentInfo + real_use_yue) + "", pay_extras,
                                    (jieshengjine) + "");
                        }
                    } else {

                        if (isAlipay && !isBudget && !isWx) {
                            pay_extras = APP_PAY + appcut + ";";
                            payV2(ALIPAY, (paymentInfo + real_use_yue) + "", pay_extras,
                                    jieshengjine + "");
                        }
                        if (isWx && !isAlipay && !isBudget) {
                            pay_extras = APP_PAY + appcut + ";";
                            payV2(WX, (paymentInfo + real_use_yue) + "", pay_extras, jieshengjine + "");
                        }

                        if (isBudget) {
                            if (((paymentInfo > 0)) && !isAlipay && !isWx) {
                                JUtils.Toast("余额不足,请选择下面一种方式一起支付");
                            } else {
                                if (paymentInfo == 0) {
                                    channel = BUDGET;
                                } else {

                                    if (isAlipay) {
                                        channel = ALIPAY;
                                    } else if (isWx) {
                                        channel = WX;
                                    }
                                }
                                pay_extras = APP_PAY + appcut + ";" + BUDGET_PAY + yue + ";";
                                payV2(channel, (paymentInfo + real_use_yue) + "", pay_extras,
                                        jieshengjine + "");
                            }
                        }
                    }
                } else {
                    JUtils.Toast("你还未设置地址");
                }

                JUtils.Log(TAG, "pay_extras ======" + pay_extras);
                break;

            case R.id.coupon_layout:
                Intent intent = new Intent(CartsPayInfoActivity.this, CouponSelectActivity.class);
                Bundle bundle = new Bundle();
                if ((coupon_id != null) && (!coupon_id.isEmpty())) {
                    bundle.putString("coupon_id", coupon_id);
                    //intent.putExtra("coupon_id", coupon_id);
                    intent.putExtras(bundle);
                }
                startActivityForResult(intent, REQUEST_CODE_COUPONT);
                break;
            case R.id.wx_layout:
                isWx = true;
                isAlipay = false;
                wxImg.setImageResource(R.drawable.radio_bg_checked);
                alipayImg.setImageResource(R.drawable.radio_bg);
                break;
            case R.id.alipay_layout:
                isAlipay = true;
                isWx = false;
                alipayImg.setImageResource(R.drawable.radio_bg_checked);
                wxImg.setImageResource(R.drawable.radio_bg);
                break;
            case R.id.tv_rule:
                dialog.show();
                break;
        }
    }

    private void payV2(String pay_method, String paymentprice_v2, String pay_extrasaa,
                       String discount_fee_price) {
        JUtils.Log("CartsPayinfo",
                cart_ids
                        + "    "
                        + addr_id
                        + "    "
                        + pay_method
                        + "    "
                        +
                        paymentprice_v2
                        + "    "
                        + post_fee
                        + "    "
                        +
                        discount_fee_price
                        + "    "
                        + total_fee
                        + "    "
                        + uuid
                        + "  "
                        + pay_extrasaa);

        showIndeterminateProgressDialog(false);
        String buyer_message = editText.getText() + "";
        Subscription subscription = TradeModel.getInstance()
                .shoppingcart_create_v2(ids, addr_id, pay_method, paymentprice_v2, post_fee,
                        discount_fee_price, total_fee, uuid, pay_extrasaa, buyer_message)
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<PayInfoBean>() {

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        hideIndeterminateProgressDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(PayInfoBean payInfoBean) {

                        if (null != payInfoBean) {
                            JUtils.Log(TAG, payInfoBean.toString());
                            Gson gson = new Gson();
                            JUtils.Log(TAG, gson.toJson(payInfoBean.getCharge()));
                            if ((payInfoBean.getChannel() != null) && (!payInfoBean.getChannel()
                                    .equals("budget"))) {

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
                                    Intent intent =
                                            new Intent(CartsPayInfoActivity.this, AllOrdersActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("fragment", 3);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    JUtils.Toast(payInfoBean.getInfo());
                                }
                            }
                        }
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
                if (result == null) {
                    return;
                }

                if (result.equals("cancel")) {
                    //wexin alipay already showmsg
                    JUtils.Toast("你已取消支付!");
                    startActivity(new Intent(CartsPayInfoActivity.this, CartActivity.class));
                    finish();
                } else if (result.equals("success")) {
                    JUtils.Toast("支付成功！");
                    //startActivity(new Intent(CartsPayInfoActivity.this, AllOrdersActivity.class));
                    //finish();
                    Intent intent = new Intent(CartsPayInfoActivity.this, AllOrdersActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("fragment", 3);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                } else {
                    showMsg(result, errorMsg, extraMsg);
                    startActivity(new Intent(CartsPayInfoActivity.this, CartActivity.class));
                    finish();
                    //JUtils.Toast(result + "" + errorMsg + "" + extraMsg);
                }
            }
        }
        if (requestCode == REQUEST_CODE_COUPONT) {
            if (resultCode == Activity.RESULT_OK) {
                coupon_id = data.getStringExtra("coupon_id");
                coupon_price = data.getDoubleExtra("coupon_price", 0);
                JUtils.Log("CartsPayinfo", "优惠券返回  " + coupon_price);
                tv_coupon.setText(coupon_price + "元优惠券");
                if (coupon_price == 0) {
                    JUtils.Log("CartsPayinfo", "优惠券返回 0++++");
                    downLoadCartsInfo();
                    calcAllPrice();
                    isCoupon = false;
                    return;
                }
                JUtils.Log(TAG, "coupon_id:" + coupon_id);
                Subscription subscription = CartsModel.getInstance()
                        .getCartsInfoList(ids, coupon_id)
                        .subscribeOn(Schedulers.io())
                        .subscribe(new ServiceResponse<CartsPayinfoBean>() {
                            @Override
                            public void onNext(CartsPayinfoBean cartsPayinfoBean) {
                                if (cartsPayinfoBean != null) {
                                    if (TextUtils.isEmpty(cartsPayinfoBean.getmCoupon_message())) {
                                        if ((coupon_id == null) || coupon_id.isEmpty() || (0
                                                == Double.compare(coupon_price, 0))) {
                                            isCoupon = false;
                                            tv_coupon.setText("");
                                        } else {
                                            isCoupon = true;
                                            calcAllPrice();
                                        }
                                    } else {
                                        isCoupon = false;
                                        tv_coupon.setText("");
                                        new MaterialDialog.Builder(CartsPayInfoActivity.this).
                                                content(cartsPayinfoBean.getmCoupon_message()).
                                                positiveText("OK").
                                                callback(new MaterialDialog.ButtonCallback() {
                                                    @Override
                                                    public void onPositive(MaterialDialog dialog) {
                                                        downLoadCartsInfo();

                                                        dialog.dismiss();
                                                    }

                                                    @Override
                                                    public void onNegative(MaterialDialog dialog) {
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

    @Override
    protected void onResume() {

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
                        @Override
                        public void onNext(List<AddressBean> list) {
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

                        @Override
                        public void onError(Throwable e) {
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
        closeInputMethod(this);
    }

    @Override
    public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
        if (isChecked) {
            isBudget = true;
        } else {
            isBudget = false;
        }
        calcAllPrice();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            confirm.setClickable(true);
            confirm.setEnabled(true);
        } else {
            confirm.setClickable(false);
            confirm.setEnabled(false);
        }
    }
}
