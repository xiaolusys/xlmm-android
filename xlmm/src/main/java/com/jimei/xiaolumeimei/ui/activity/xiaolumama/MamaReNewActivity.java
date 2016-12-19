package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jimei.library.utils.JUtils;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.base.CommonWebViewActivity;
import com.jimei.xiaolumeimei.entities.MaMaReNewBean;
import com.jimei.xiaolumeimei.entities.ResultBean;
import com.jimei.xiaolumeimei.model.MamaInfoModel;
import com.jimei.xiaolumeimei.utils.JumpUtils;
import com.jimei.xiaolumeimei.utils.pay.PayUtils;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;

import butterknife.Bind;
import retrofit2.Response;

/**
 * Created by itxuye on 16/7/19.
 */
public class MamaReNewActivity extends BaseSwipeBackCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private static final String HALF = "half";
    private static final String FULL = "full";
    private static final String ALIPAY = "alipay";
    private static final String WX = "wx";
    @Bind(R.id.image_99)
    ImageView image99;
    @Bind(R.id.image_991)
    ImageView image991;
    @Bind(R.id.image_188)
    ImageView image188;
    @Bind(R.id.image_1881)
    ImageView image1881;
    @Bind(R.id.tv_moren)
    TextView tvMoren;
    @Bind(R.id.cb_rule)
    CheckBox ruleCb;
    @Bind(R.id.tv_rule)
    TextView ruleTv;
    @Bind(R.id.commit)
    TextView commitTv;
    @Bind(R.id.layout)
    LinearLayout layout;

    private int productId;
    private int skuId188;
    private int skuId99;
    private boolean radio_188Choose;
    private boolean radio_99Choose;
    private double payment188;
    private double payment99;
    private double postfee188;
    private double postfee99;
    private double discountfee188;
    private double discountfee99;
    private double totalfee188;
    private double totalfee99;
    private String uuid;
    private double mamaCarryValue;

    @Override
    protected void setListener() {
        ruleCb.setOnCheckedChangeListener(this);
        image99.setOnClickListener(this);
        image991.setOnClickListener(this);
        image188.setOnClickListener(this);
        image1881.setOnClickListener(this);
        ruleTv.setOnClickListener(this);
        commitTv.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        showIndeterminateProgressDialog(true);
        MamaInfoModel.getInstance()
                .getRegisterProInfo()
                .subscribe(new ServiceResponse<MaMaReNewBean>() {
                    @Override
                    public void onNext(MaMaReNewBean maMaReNewBean) {
                        if (null != maMaReNewBean) {
                            productId = maMaReNewBean.getProduct().getId();
                            for (int i = 0; i < maMaReNewBean.getProduct().getNormalSkus().size(); i++) {
                                if ("1".equals(maMaReNewBean.getProduct().getNormalSkus().get(i).getOuterId())) {
                                    skuId188 = maMaReNewBean.getProduct().getNormalSkus().get(i).getId();
                                }

                                if ("2".equals(maMaReNewBean.getProduct().getNormalSkus().get(i).getOuterId())) {
                                    skuId99 = maMaReNewBean.getProduct().getNormalSkus().get(i).getId();
                                }
                            }
                            uuid = maMaReNewBean.getUuid();
                            payment188 = maMaReNewBean.getPayinfos().get(0).getTotalPayment();
                            payment99 = maMaReNewBean.getPayinfos().get(1).getTotalPayment();
                            postfee188 = maMaReNewBean.getPayinfos().get(0).getPostFee();
                            postfee99 = maMaReNewBean.getPayinfos().get(1).getPostFee();
                            discountfee188 = maMaReNewBean.getPayinfos().get(0).getDiscountFee();
                            discountfee99 = maMaReNewBean.getPayinfos().get(1).getDiscountFee();
                            totalfee188 = maMaReNewBean.getPayinfos().get(0).getTotalFee();
                            totalfee99 = maMaReNewBean.getPayinfos().get(1).getTotalFee();
                        } else {
                            JUtils.Toast("数据加载失败，请重新进入加载数据!");
                        }
                        hideIndeterminateProgressDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        JUtils.Toast("数据加载失败，请重新进入加载数据!");
                        hideIndeterminateProgressDialog();
                    }

                });
    }

    @Override
    public View getLoadingView() {
        return layout;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        mamaCarryValue = extras.getDouble("mamaCarryValue", 0);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_mamarenew;
    }

    @Override
    protected void initViews() {
        radio_99Choose = false;
        radio_188Choose = true;
        image991.setVisibility(View.VISIBLE);
        image99.setVisibility(View.GONE);
        image188.setVisibility(View.VISIBLE);
        image1881.setVisibility(View.GONE);
        tvMoren.setText("默认使用小鹿妈妈钱包金额抵扣,总余额:" + mamaCarryValue);
    }

    public void mamaPay(String product_id, String sku_id, String payment, String channel, String num,
                        String post_fee, String discount_fee, String uuid, String total_fee,
                        String wallet_renew_deposit) {
        showIndeterminateProgressDialog(false);
        MamaInfoModel.getInstance()
                .mamaRegisterPay(product_id, sku_id, payment, channel, num, post_fee, discount_fee, uuid,
                        total_fee, wallet_renew_deposit)
                .subscribe(new ServiceResponse<Object>() {
                    @Override
                    public void onNext(Object charge) {
                        PayUtils.createPayment(MamaReNewActivity.this, new Gson().toJson(charge));
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideIndeterminateProgressDialog();
                    }
                });
    }

    public void mamaCarryValuePay(String exchange_type) {
        showIndeterminateProgressDialog(false);
        MamaInfoModel.getInstance()
                .exchangeDeposit(exchange_type)
                .subscribe(new ServiceResponse<Response<ResultBean>>() {
                    @Override
                    public void onNext(Response<ResultBean> responseBodyResponse) {
                        if (responseBodyResponse.isSuccessful()) {
                            try {
                                ResultBean body = responseBodyResponse.body();
                                if (body.getCode() == 0) {
                                    JUtils.Toast("支付成功");
                                    finish();
                                } else {
                                    JUtils.Toast("支付失败");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        hideIndeterminateProgressDialog();
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_rule:
                JumpUtils.jumpToWebViewWithCookies(this, "https://m.xiaolumeimei.com/static/tiaokuan.html", -1,
                        CommonWebViewActivity.class, "小鹿妈妈服务条款", false);
                break;
            case R.id.commit:
                if (radio_99Choose) {
                    if (mamaCarryValue >= 99.00) {
                        showInfo(HALF);
                    } else {
                        new MyDialog(this).show();
                    }
                } else if (radio_188Choose) {
                    if (mamaCarryValue >= 188.00) {
                        showInfo(FULL);
                    } else {
                        new MyDialog(this).show();
                    }
                }
                break;
            case R.id.image_991:
                radio_99Choose = true;
                radio_188Choose = false;
                image991.setVisibility(View.GONE);
                image99.setVisibility(View.VISIBLE);
                image188.setVisibility(View.GONE);
                image1881.setVisibility(View.VISIBLE);
                break;
            case R.id.image_1881:
                radio_99Choose = false;
                radio_188Choose = true;
                image991.setVisibility(View.VISIBLE);
                image99.setVisibility(View.GONE);
                image188.setVisibility(View.VISIBLE);
                image1881.setVisibility(View.GONE);
                break;
        }
    }

    public void showInfo(String type) {
        new AlertDialog.Builder(this)
                .setTitle("支付提示")
                .setMessage("是否使用妈妈账户余额抵扣支付?")
                .setPositiveButton("确认", (dialog, which) -> {
                    dialog.dismiss();
                    mamaCarryValuePay(type);
                })
                .setNegativeButton("取消", (dialog, which) -> dialog.dismiss())
                .show();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.cb_rule:
                if (isChecked) {
                    commitTv.setClickable(true);
                    commitTv.setEnabled(true);
                } else {
                    commitTv.setClickable(false);
                    commitTv.setEnabled(false);
                }
                break;
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
            TextView tvPay = (TextView) mView.findViewById(R.id.tvPay);
            tvPay.setVisibility(View.INVISIBLE);
            mView.findViewById(R.id.finish_iv).setOnClickListener(v -> MyDialog.this.dismiss());

            alipay_layout.setOnClickListener(v -> {
                if (radio_188Choose) {
                    mamaPay(productId + "", skuId188 + "", (payment188 - mamaCarryValue) + "", ALIPAY, "1",
                            postfee188 + "", discountfee188 + "", uuid, totalfee188 + "", mamaCarryValue + "");
                } else if (radio_99Choose) {
                    mamaPay(productId + "", skuId99 + "", (payment99 - mamaCarryValue) + "", ALIPAY, "1",
                            postfee99 + "", discountfee99 + "", uuid, totalfee99 + "", mamaCarryValue + "");
                }
                dismiss();
            });

            wx_layout.setOnClickListener(v -> {
                if (radio_188Choose) {
                    mamaPay(productId + "", skuId188 + "", (payment188 - mamaCarryValue) + "", WX, "1",
                            postfee188 + "", discountfee188 + "", uuid, totalfee188 + "", mamaCarryValue + "");
                } else if (radio_99Choose) {
                    mamaPay(productId + "", skuId99 + "", (payment99 - mamaCarryValue) + "", WX, "1",
                            postfee99 + "", discountfee99 + "", uuid, totalfee99 + "", mamaCarryValue + "");
                }
                dismiss();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        hideIndeterminateProgressDialog();
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PayUtils.REQUEST_CODE_PAYMENT) {
            if (resultCode == RESULT_OK) {
                finish();
            }
        }
    }
}
