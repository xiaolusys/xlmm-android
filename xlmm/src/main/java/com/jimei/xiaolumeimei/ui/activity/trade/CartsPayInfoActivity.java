package com.jimei.xiaolumeimei.ui.activity.trade;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jimei.library.utils.IdCardChecker;
import com.jimei.library.utils.JUtils;
import com.jimei.library.widget.NestedListView;
import com.jimei.library.widget.SmoothCheckBox;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.XlmmApp;
import com.jimei.xiaolumeimei.adapter.CartsPayInfoAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.base.CommonWebViewActivity;
import com.jimei.xiaolumeimei.entities.AddressBean;
import com.jimei.xiaolumeimei.entities.CartsPayinfoBean;
import com.jimei.xiaolumeimei.entities.PayInfoBean;
import com.jimei.xiaolumeimei.entities.TeamBuyBean;
import com.jimei.xiaolumeimei.entities.event.UserChangeEvent;
import com.jimei.xiaolumeimei.model.AddressModel;
import com.jimei.xiaolumeimei.model.CartsModel;
import com.jimei.xiaolumeimei.model.TradeModel;
import com.jimei.xiaolumeimei.ui.activity.user.AddNoAddressActivity;
import com.jimei.xiaolumeimei.ui.activity.user.AddressSelectActivity;
import com.jimei.xiaolumeimei.ui.activity.user.SelectCouponActivity;
import com.jimei.xiaolumeimei.utils.JumpUtils;
import com.jimei.xiaolumeimei.utils.pay.PayUtils;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.Subscription;

/**
 * Created by 优尼世界 on 2016/01/15.
 * <p>
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class CartsPayInfoActivity extends BaseSwipeBackCompatActivity
        implements View.OnClickListener, SmoothCheckBox.OnCheckedChangeListener,
        CompoundButton.OnCheckedChangeListener {
    private static final int REQUEST_CODE_COUPONT = 2;
    private static final int REQUEST_CODE_ADDRESS = 3;
    private static final String APP_PAY = "pid:1:value:";
    private static final String BUDGET_PAY = "pid:3:budget:";
    private static final String ALIPAY = "alipay";
    private static final String WX = "wx";
    private static final String BUDGET = "budget";
    String TAG = "CartsPayInfoActivity";
    CartsPayInfoAdapter mAdapter;
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
    @Bind(R.id.confirm)
    Button confirm;
    @Bind(R.id.post_fee)
    TextView tv_postfee;
    @Bind(R.id.coupon_layout)
    RelativeLayout coupon_layout;
    @Bind(R.id.tv_coupon)
    TextView tv_coupon;
    List<CartsPayinfoBean.CartListEntity> list;
    @Bind(R.id.scb)
    SmoothCheckBox scb;
    @Bind(R.id.tv_app_discount)
    TextView tv_app_discount;
    @Bind(R.id.extra_budget)
    TextView extraBudget;
    @Bind(R.id.cb_rule)
    CheckBox ruleCb;
    @Bind(R.id.tv_rule)
    TextView ruleTv;
    @Bind(R.id.tv_wuliu)
    TextView tvWuliu;
    @Bind(R.id.wuliu_layout)
    RelativeLayout wuliuLayout;
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
    private int goodNum;
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
    private List<CartsPayinfoBean.LogisticsCompanys> logisticsCompanyses = new ArrayList<>();
    private List<String> logisticsCompanysesString = new ArrayList<>();
    private String code;
    private String order_no = "";
    private int order_id = -1;
    @Bind(R.id.jiesheng_price)
    TextView jiesheng_price;
    private int position;
    private boolean mFlag, couponFlag;
    private String idNo;
    private boolean idFlag = false;

    @Override
    protected void setListener() {
        adress.setOnClickListener(this);
        confirm.setOnClickListener(this);
        scb.setOnCheckedChangeListener(this);
        ruleCb.setOnCheckedChangeListener(this);
        ruleTv.setOnClickListener(this);
        wuliuLayout.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        list = new ArrayList<>();
        downLoadCartsInfoWithout();
    }

    private void downLoadCartsInfo() {
        Subscription subscription = CartsModel.getInstance()
                .getCartsPayInfoListV2(ids)
                .subscribe(new ServiceResponse<CartsPayinfoBean>() {
                    @Override
                    public void onNext(CartsPayinfoBean cartsPayinfoBean) {
                        if (cartsPayinfoBean != null) {
                            JUtils.Log(TAG, cartsPayinfoBean.toString());
                            mAdapter.updateWithClear(cartsPayinfoBean.getCartList());
                            goodNum = cartsPayinfoBean.getCartList().get(0).getNum();
                            cart_ids = cartsPayinfoBean.getCartIds();
                            logisticsCompanyses.addAll(cartsPayinfoBean.getLogisticsCompanyses());
                            for (CartsPayinfoBean.LogisticsCompanys list : logisticsCompanyses) {
                                logisticsCompanysesString.add(list.getName());
                            }

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

                            tv_postfee.setText("¥" + post_fee);

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
                                    "downLoadCartsInfo:" + cart_ids + "  " + addr_id + "  " + "  " +
                                            payment + "  " + post_fee + "  " +
                                            discount_fee + "  " + total_fee + "  " + uuid + "  " +
                                            pay_extras);
                        } else {
                            JUtils.Toast("商品已过期,请重新选购");
                            readyGo(CartActivity.class);
                        }
                    }
                });
        addSubscription(subscription);
    }

    private void downLoadCartsInfoWithout() {
        Subscription subscription = CartsModel.getInstance()
                .getCartsPayInfoListV2(ids)
                .subscribe(new ServiceResponse<CartsPayinfoBean>() {
                    @Override
                    public void onNext(CartsPayinfoBean cartsPayinfoBean) {
                        if (cartsPayinfoBean != null) {
                            List<CartsPayinfoBean.CartListEntity> cartList = cartsPayinfoBean.getCartList();
                            for (int i = 0; i < cartList.size(); i++) {
                                if (cartList.get(position).is_bonded_goods()) {
                                    idFlag = true;
                                    checkIdNo();
                                }
                            }
                            JUtils.Log(TAG, cartsPayinfoBean.toString());
                            mAdapter.updateWithClear(cartList);
                            goodNum = cartList.get(0).getNum();
                            cart_ids = cartsPayinfoBean.getCartIds();
                            logisticsCompanyses.addAll(cartsPayinfoBean.getLogisticsCompanyses());
                            for (CartsPayinfoBean.LogisticsCompanys list : logisticsCompanyses) {
                                logisticsCompanysesString.add(list.getName());
                            }

                            tvWuliu.setText(logisticsCompanyses.get(0).getName());
                            code = logisticsCompanyses.get(0).getId();

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

                            tv_postfee.setText("¥" + post_fee);

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
                            JUtils.Toast("商品已过期,请重新选购");
                            readyGo(CartActivity.class);
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

        tv_app_discount.setText("-" + (double) (Math.round(appcut * 100)) / 100);
        extraBudget.setText("¥" + budgetCash);
        totalPrice.setText("¥" + (double) (Math.round(paymentInfo * 100)) / 100);
        totalPrice_all.setText("¥" + (double) (Math.round(paymentInfo * 100)) / 100);
        jiesheng_price.setText("¥" + (double) (Math.round(jieshengjine * 100)) / 100);
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        ids = extras.getString("ids");
        mFlag = extras.getBoolean("flag", false);
        couponFlag = extras.getBoolean("couponFlag", false);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.cartspayinfo_activity;
    }

    @Override
    protected void initViews() {
        mAdapter = new CartsPayInfoAdapter(this, list);
        payinfoListview.setAdapter(mAdapter);
        dialog = new AlertDialog.Builder(this)
                .setTitle("购买条款")
                .setMessage(getResources().getString(R.string.buy_rule))
                .setPositiveButton("同意", (dialog1, which) -> dialog1.dismiss())
                .create();
        scb.setCanClickable(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.adress:
                if (isHaveAddress) {
                    Intent intent =
                            new Intent(CartsPayInfoActivity.this, AddressSelectActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("position", position);
                    bundle.putBoolean("idFlag", idFlag);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, REQUEST_CODE_ADDRESS);
                } else {
                    startActivity(
                            new Intent(CartsPayInfoActivity.this, AddNoAddressActivity.class));
                }
                break;
            case R.id.confirm:
                MobclickAgent.onEvent(this, "PayId");
                if (idFlag && !IdCardChecker.isValidatedAllIdcard(idNo)) {
                    new AlertDialog.Builder(this)
                            .setTitle("提示")
                            .setMessage("订单中包含进口保税区发货商品，根据海关监管要求，需要提供收货人身份证号码。此信息加密保存，只用于此订单海关通关。")
                            .setPositiveButton("确认", (dialog1, which) -> dialog1.dismiss())
                            .setCancelable(false)
                            .show();
                } else {
                    xlmmPayWithDialog();
                }
                break;
            case R.id.coupon_layout:
                Intent intent = new Intent(CartsPayInfoActivity.this, SelectCouponActivity.class);
                Bundle bundle = new Bundle();
                if ((coupon_id != null) && (!coupon_id.isEmpty())) {
                    bundle.putString("coupon_id", coupon_id);
                }
                if (goodNum > 1) {
                    bundle.putInt("goodNum", goodNum);
                }
                bundle.putBoolean("couponFlag", couponFlag);
                bundle.putString("cart_ids", cart_ids);
                intent.putExtras(bundle);
                startActivityForResult(intent, REQUEST_CODE_COUPONT);
                break;
            case R.id.tv_rule:
                dialog.show();
                break;
            case R.id.wuliu_layout:
                new MyDialog1(this).show();
                break;
        }
    }

    private void xlmmPay() {
        if (isHaveAddress) {
            if (!isCoupon && !isBudget && !isWx && !isAlipay) {
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
                    payV2(ALIPAY, (paymentInfo + real_use_yue) + "", pay_extras, jieshengjine + "");
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
    }

    private void xlmmPayWithDialog() {
        if (isHaveAddress) {
            if (!isCoupon && !isBudget && !isWx && !isAlipay) {
                new MyDialog(this).show();
                return;
            }

            if (isCoupon) {
                if (!isAlipay && !isWx && !isBudget) {
                    if (paymentInfo == 0) {
                        setConfirmClickAble();
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
                        //JUtils.Toast("优惠券金额不足,可以选择其它混合支付");
                        new MyDialog(this).show();
                    }
                } else if (isBudget && !isAlipay && !isWx) {
                    if (paymentInfo == 0) {
                        setConfirmClickAble();
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
                        //JUtils.Toast("金额不足,可以选择下面一种支付方式混合支付");
                        new MyDialog(this).show();
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
                        setConfirmClickAble();
                        channel = BUDGET;
                        payV2(channel, (paymentInfo + real_use_yue) + "", pay_extras,
                                (jieshengjine) + "");
                    } else {
                        if (isAlipay) {
                            channel = ALIPAY;
                            new MyDialog(this).show();
                        } else {
                            channel = WX;
                            new MyDialog(this).show();
                        }
                    }
                }
            } else {

                if (isAlipay && !isBudget && !isWx) {
                    pay_extras = APP_PAY + appcut + ";";
                    //payV2(ALIPAY, (paymentInfo + real_use_yue) + "", pay_extras, jieshengjine + "");
                    new MyDialog(this).show();
                }
                if (isWx && !isAlipay && !isBudget) {
                    pay_extras = APP_PAY + appcut + ";";
                    //payV2(WX, (paymentInfo + real_use_yue) + "", pay_extras, jieshengjine + "");
                    new MyDialog(this).show();
                }

                if (isBudget) {
                    if (((paymentInfo > 0)) && !isAlipay && !isWx) {
                        //JUtils.Toast("余额不足,请选择下面一种方式一起支付");
                        new MyDialog(this).show();
                    } else {
                        if (paymentInfo == 0) {
                            setConfirmClickAble();
                            channel = BUDGET;
                            pay_extras = APP_PAY + appcut + ";" + BUDGET_PAY + yue + ";";
                            payV2(channel, (paymentInfo + real_use_yue) + "", pay_extras,
                                    jieshengjine + "");
                        } else {
                            if (isAlipay) {
                                channel = ALIPAY;
                                new MyDialog(this).show();
                            } else if (isWx) {
                                channel = WX;
                                new MyDialog(this).show();
                            }
                        }
                    }
                }
            }
        } else {
            JUtils.Toast("你还未设置地址");
        }
        JUtils.Log(TAG, "pay_extras ======" + pay_extras);
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
        //int position = spinner.getSelectedItemPosition();
        //String code = logisticCompanyList.get(position).getCode();
        Subscription subscription = TradeModel.getInstance()
                .shoppingcart_create_v2(ids, addr_id, pay_method, paymentprice_v2, post_fee,
                        discount_fee_price, total_fee, uuid, pay_extrasaa, code, mFlag)
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
                        hideIndeterminateProgressDialog();
                    }

                    @Override
                    public void onNext(PayInfoBean payInfoBean) {
                        if (null != payInfoBean && payInfoBean.getTrade() != null) {
                            order_id = payInfoBean.getTrade().getId();
                            order_no = payInfoBean.getTrade().getTid();
                            JUtils.Log(TAG, payInfoBean.toString());
                            Gson gson = new Gson();
                            JUtils.Log(TAG, gson.toJson(payInfoBean.getCharge()));
                            if ((payInfoBean.getChannel() != null) && (!payInfoBean.getChannel()
                                    .equals("budget"))) {
                                if (payInfoBean.getCode() > 0) {
                                    JUtils.Toast(payInfoBean.getInfo());
                                } else {
                                    PayUtils.createPayment(CartsPayInfoActivity.this, gson.toJson(payInfoBean.getCharge()));
                                }
                            } else {
                                if (payInfoBean.getCode() == 0) {
                                    JUtils.Toast("支付成功");
                                    hideIndeterminateProgressDialog();
                                    successJump();
                                } else {
                                    JUtils.Toast(payInfoBean.getInfo());
                                }
                            }
                        } else if (null != payInfoBean) {
                            JUtils.Toast(payInfoBean.getInfo());
                        }
                    }
                });

        addSubscription(subscription);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //支付页面返回处理
        if (requestCode == PayUtils.REQUEST_CODE_PAYMENT) {
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
                    EventBus.getDefault().postSticky(new UserChangeEvent());
                    //wexin alipay already showmsg
                    MobclickAgent.onEvent(CartsPayInfoActivity.this, "PayCancelID");
                    JUtils.Toast("你已取消支付!");

                    if (order_id != -1) {
                        Intent intent = new Intent(this, OrderDetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("orderinfo", order_id);
                        bundle.putString("source", CartsPayInfoActivity.class.getSimpleName());
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                    finish();
                } else if (result.equals("success")) {
                    EventBus.getDefault().postSticky(new UserChangeEvent());
                    JUtils.Toast("支付成功！");
                    successJump();
                } else {
                    EventBus.getDefault().postSticky(new UserChangeEvent());
                    MobclickAgent.onEvent(CartsPayInfoActivity.this, "PayFailID");
                    showMsgAndFinish(result, errorMsg, extraMsg, true);
                }
            }
        }
        if (requestCode == REQUEST_CODE_COUPONT) {
            if (resultCode == Activity.RESULT_OK) {
                coupon_id = data.getStringExtra("coupon_id");
                coupon_price = data.getDoubleExtra("coupon_price", 0);
                int coupon_num = data.getIntExtra("coupon_num", 1);
                JUtils.Log("CartsPayinfo", "优惠券返回  " + coupon_price);
                if (coupon_num > 1) {
                    tv_coupon.setText(coupon_price / coupon_num + "元优惠券x" + coupon_num);
                } else {
                    tv_coupon.setText(coupon_price + "元优惠券");
                }
                if (coupon_id == null || coupon_id.isEmpty() || coupon_price == 0) {
                    downLoadCartsInfo();
                    isCoupon = false;
                    tv_coupon.setText("");
                    coupon_price = 0;
                } else {
                    isCoupon = true;
                }
                calcAllPrice();
            }
        }
        if (requestCode == REQUEST_CODE_ADDRESS) {
            if (resultCode == Activity.RESULT_OK) {
                isSelectAddress = true;
                nameSelect = data.getStringExtra("name");
                phoneSelect = data.getStringExtra("phone");
                position = data.getIntExtra("position", 0);
                idNo = data.getStringExtra("idNo");
                checkIdNo();
                addressDetailsSelect = data.getStringExtra("addressDetails");
                addr_idSelect = data.getStringExtra("addr_id");
            } else {
                isSelectAddress = false;
            }
        }
    }

    private void successJump() {
        if (mFlag) {
            addSubscription(TradeModel.getInstance()
                    .getTeamBuyBean(order_no)
                    .subscribe(new ServiceResponse<TeamBuyBean>() {
                        @Override
                        public void onNext(TeamBuyBean teamBuyBean) {
                            int id = teamBuyBean.getId();
                            SharedPreferences preferences = XlmmApp.getmContext().getSharedPreferences("APICLIENT", Context.MODE_PRIVATE);
                            String baseUrl = "https://m.xiaolumeimei.com/mall/order/spell/group/" + id + "?from_page=order_commit";
                            if (!TextUtils.isEmpty(preferences.getString("BASE_URL", ""))) {
                                baseUrl = "https://" + preferences.getString("BASE_URL", "") + "/mall/order/spell/group/" + id + "?from_page=order_commit";
                            }
                            JumpUtils.jumpToWebViewWithCookies(mContext,
                                    baseUrl, -1, CommonWebViewActivity.class, "查看拼团详情", false);
                            finish();
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (order_id != -1) {
                                Intent intent = new Intent(CartsPayInfoActivity.this, OrderDetailActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putInt("orderinfo", order_id);
                                bundle.putString("source", CartsPayInfoActivity.class.getSimpleName());
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                            finish();
                        }
                    }));
        } else {
            Intent intent = new Intent(CartsPayInfoActivity.this, RedBagActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("tid", order_no);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onResume() {

        super.onResume();
        MobclickAgent.onPageStart(this.getClass().getSimpleName());
        MobclickAgent.onResume(this);

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
            addSubscription(AddressModel.getInstance()
                    .getAddressList()
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
                                position = 0;

                                addr_id = addressBean.getId();
                                JUtils.Log(TAG, addr_id + "addr_id");
                                name.setText(addressBean.getReceiverName());
                                phone.setText(addressBean.getReceiverMobile());
                                idNo = addressBean.getmIdentificationNo();
                                checkIdNo();
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
                    }));
        }
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

    private void checkIdNo() {
        if (idFlag && !IdCardChecker.isValidatedAllIdcard(idNo)) {
            new AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage("订单中包含进口保税区发货商品，根据海关监管要求，需要提供收货人身份证号码。此信息加密保存，只用于此订单海关通关。")
                    .setPositiveButton("确认", (dialog1, which) -> dialog1.dismiss())
                    .setCancelable(false)
                    .show();
        }
    }

    class MyDialog extends Dialog {
        public MyDialog(Context context) {
            super(context, R.style.MyDialog);
            setDialog();
        }

        private void setDialog() {
            View mView = LayoutInflater.from(getContext()).inflate(R.layout.pop_wxoralipay, null);
            LinearLayout wx_layout = (LinearLayout) mView.findViewById(R.id.wx_layout);
            LinearLayout alipay_layout = (LinearLayout) mView.findViewById(R.id.alipay_layout);
            TextView textView = (TextView) mView.findViewById(R.id.total_price);
            mView.findViewById(R.id.finish_iv).setOnClickListener(v -> MyDialog.this.dismiss());

            textView.setText("¥" + (double) (Math.round(paymentInfo * 100)) / 100);

            alipay_layout.setOnClickListener(v -> {
                alipay_layout.setClickable(false);
                isAlipay = true;
                isWx = false;
                xlmmPay();
                MyDialog.this.dismiss();
            });

            wx_layout.setOnClickListener(v -> {
                wx_layout.setClickable(false);
                isAlipay = false;
                isWx = true;
                xlmmPay();
                MyDialog.this.dismiss();
            });

            MyDialog.this.setCanceledOnTouchOutside(true);
            Window win = this.getWindow();
            win.setGravity(Gravity.BOTTOM);
            win.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams lp = win.getAttributes();
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            win.setAttributes(lp);
            super.setContentView(mView);
        }
    }

    class MyDialog1 extends Dialog {
        public MyDialog1(Context context) {
            super(context, R.style.MyDialog);
            setDialog();
        }

        private void setDialog() {
            View mView = LayoutInflater.from(getContext()).inflate(R.layout.pop_logic, null);

            ListView listView = (ListView) mView.findViewById(R.id.listview_wuliu);

            for (String str : logisticsCompanysesString) {
                JUtils.Log(TAG, "wuliu====" + str);
            }

            listView.setAdapter(new ArrayAdapter<>(CartsPayInfoActivity.this,
                    android.R.layout.simple_list_item_1, logisticsCompanysesString));

            listView.setOnItemClickListener((parent, view, position1, id) -> {
                code = logisticsCompanyses.get(position1).getId();
                tvWuliu.setText(logisticsCompanyses.get(position1).getName());
                MyDialog1.this.dismiss();
            });

            MyDialog1.this.setCanceledOnTouchOutside(true);
            Window win = this.getWindow();
            win.setGravity(Gravity.BOTTOM);
            win.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams lp = win.getAttributes();
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            win.setAttributes(lp);
            super.setContentView(mView);
        }

        @Override
        public void dismiss() {
            super.dismiss();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
        MobclickAgent.onPause(this);
    }

    private void setConfirmClickAble() {
        confirm.setClickable(false);
    }
}
