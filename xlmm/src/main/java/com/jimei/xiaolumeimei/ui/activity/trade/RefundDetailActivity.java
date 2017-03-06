package com.jimei.xiaolumeimei.ui.activity.trade;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.jimei.library.utils.JUtils;
import com.jimei.library.utils.ViewUtils;
import com.jimei.library.widget.RoundCornerImageView;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.XlmmApp;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.entities.AllRefundsBean;
import com.jimei.xiaolumeimei.entities.AllRefundsBean.ResultsEntity.StatusShaftBean;
import com.jimei.xiaolumeimei.service.ServiceResponse;

import java.util.List;

import butterknife.Bind;

public class RefundDetailActivity extends BaseSwipeBackCompatActivity
    implements View.OnClickListener {
    private static final String TAG = RefundDetailActivity.class.getSimpleName();
    @Bind(R.id.tv_order_id)
    TextView orderIdTv;
    @Bind(R.id.tv_status)
    TextView statusTv;
    @Bind(R.id.ll_return)
    LinearLayout returnLayout;
    @Bind(R.id.sdv)
    ImageView goodImageView;
    @Bind(R.id.tv_good_name)
    TextView goodNameTv;
    @Bind(R.id.tv_good_price)
    TextView goodPriceTv;
    @Bind(R.id.tv_good_size)
    TextView goodSizeTv;
    @Bind(R.id.num)
    TextView numTv;
    @Bind(R.id.price)
    TextView priceTv;
    @Bind(R.id.reason)
    TextView reasonTv;
    @Bind(R.id.round_image)
    RoundCornerImageView round_image;
    @Bind(R.id.round_image1)
    RoundCornerImageView round_image1;
    @Bind(R.id.round_image2)
    RoundCornerImageView round_image2;
    @Bind(R.id.round_image3)
    RoundCornerImageView round_image3;
    @Bind(R.id.rl_img)
    RelativeLayout rl_img;
    @Bind(R.id.rl_img1)
    RelativeLayout rl_img1;
    @Bind(R.id.rl_img2)
    RelativeLayout rl_img2;
    @Bind(R.id.rl_img3)
    RelativeLayout rl_img3;
    @Bind(R.id.refund_type)
    TextView refundTypeTv;
    @Bind(R.id.refund_layout)
    LinearLayout refundLayout;
    @Bind(R.id.time)
    TextView timeTv;
    @Bind(R.id.name)
    TextView nameTv;
    @Bind(R.id.phone)
    TextView phoneTv;
    @Bind(R.id.address)
    TextView addressTv;
    @Bind(R.id.btn_write)
    Button writeBtn;
    @Bind(R.id.image_layout)
    LinearLayout imageLayout;
    AllRefundsBean.ResultsEntity refundDetail;
    @Bind(R.id.iv_1)
    ImageView imageView1;
    @Bind(R.id.iv_2)
    ImageView imageView2;
    @Bind(R.id.iv_3)
    ImageView imageView3;
    @Bind(R.id.iv_4)
    ImageView imageView4;
    @Bind(R.id.iv_5)
    ImageView imageView5;
    @Bind(R.id.iv)
    ImageView iView;
    @Bind(R.id.line_2)
    ImageView lineImage2;
    @Bind(R.id.line_3)
    ImageView lineImage3;
    @Bind(R.id.line_4)
    ImageView lineImage4;
    @Bind(R.id.line_5)
    ImageView lineImage5;
    @Bind(R.id.line_6)
    ImageView lineImage6;
    @Bind(R.id.line)
    ImageView lineImage;
    @Bind(R.id.tv_1)
    TextView textView1;
    @Bind(R.id.tv_2)
    TextView textView2;
    @Bind(R.id.tv_3)
    TextView textView3;
    @Bind(R.id.tv_4)
    TextView textView4;
    @Bind(R.id.tv_5)
    TextView textView5;
    @Bind(R.id.tv)
    TextView textView;
    @Bind(R.id.status_layout)
    LinearLayout statusLayout;
    @Bind(R.id.layout)
    ScrollView layout;
    @Bind(R.id.driver)
    View driver;

    private int goods_id;
    private boolean isWrited;

    @Override
    protected void setListener() {
        writeBtn.setOnClickListener(this);
    }

    @Override
    public View getLoadingView() {
        return layout;
    }

    @Override
    protected void initData() {
        JUtils.Log(TAG, "initData goods_id " + goods_id);
        showIndeterminateProgressDialog(false);
        addSubscription(XlmmApp.getTradeInteractor(this)
            .getRefundDetailBean(goods_id, new ServiceResponse<AllRefundsBean.ResultsEntity>() {
                @Override
                public void onNext(AllRefundsBean.ResultsEntity refundDetailBean) {
                    JUtils.Log(TAG, "getRefundDetailBean success ");
                    refundDetail = refundDetailBean;
                    fillDataToView(refundDetailBean);
                    Log.i(TAG, refundDetailBean.toString());
                    Log.i(TAG, "status " + refundDetailBean.getStatus());
                    hideIndeterminateProgressDialog();
                }
            }));
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        goods_id = extras.getInt("goods_id");
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_refund_detail;
    }

    private void fillDataToView(AllRefundsBean.ResultsEntity refundDetailBean) {
        if (refundDetailBean.isHas_good_return()) {
            switch (refundDetailBean.getStatus()) {
                case XlmmConst.REFUND_STATE_SELLER_AGREED:
                    returnLayout.setVisibility(View.VISIBLE);
                    writeBtn.setText("填写快递单");
                    isWrited = false;
                    break;
                case XlmmConst.REFUND_STATE_WAIT_RETURN_FEE:
                case XlmmConst.REFUND_STATE_REFUND_SUCCESS:
                    returnLayout.setVisibility(View.VISIBLE);
                    writeBtn.setText("已验收");
                    isWrited = true;
                    break;
                case XlmmConst.REFUND_STATE_BUYER_RETURNED_GOODS:
                    returnLayout.setVisibility(View.VISIBLE);
                    writeBtn.setText("查看进度");
                    isWrited = true;
                    break;
                default:
                    returnLayout.setVisibility(View.GONE);
                    isWrited = true;
                    break;
            }
        } else {
            returnLayout.setVisibility(View.GONE);
        }
        orderIdTv.setText(refundDetailBean.getRefund_no());
        statusTv.setText(refundDetailBean.getStatus_display());
        String address = refundDetailBean.getReturn_address();
        String[] split;
        if (address.contains("，")) {
            split = address.split("，");
        } else if (address.contains(",")) {
            split = address.split(",");
        } else {
            split = address.split(";");
        }
        for (String s : split) {
            if (s.contains("市")) {
                addressTv.setText(s);
            } else if (s.contains("小鹿")) {
                nameTv.setText("小鹿售后");
            } else {
                phoneTv.setText(s);
            }
        }
        ViewUtils.loadImgToImgView(getApplicationContext(), goodImageView,
            refundDetailBean.getPic_path());
        goodNameTv.setText(refundDetailBean.getTitle());
        goodPriceTv.setText("¥" + refundDetailBean.getTotal_fee() + "x" + refundDetailBean.getRefund_num());
        goodSizeTv.setText("尺码：" + refundDetailBean.getSku_name());
        timeTv.setText("申请时间:" + refundDetailBean.getCreated().replace("T", " "));
        numTv.setText(Integer.toString(refundDetailBean.getRefund_num()));
        priceTv.setText("¥" + refundDetailBean.getRefund_fee());
        reasonTv.setText(refundDetailBean.getReason());
        RoundCornerImageView[] images = {round_image, round_image1, round_image2, round_image3};
        RelativeLayout[] rls = {rl_img, rl_img1, rl_img2, rl_img3};
        if (refundDetailBean.getProof_pic() != null && refundDetailBean.getProof_pic().size() > 0) {
            for (int i = 0; i < refundDetailBean.getProof_pic().size(); i++) {
                ViewUtils.loadImgToImgView(getApplicationContext(), images[i],
                    refundDetailBean.getProof_pic().get(i));
                rls[i].setVisibility(View.VISIBLE);
            }
            imageLayout.setVisibility(View.VISIBLE);
        }
        JUtils.Log(TAG, "crt time " + refundDetailBean.getCreated());
        String desc = refundDetailBean.getAmount_flow().getDesc();
        refundTypeTv.setText(desc);
        if (!"".equals(desc)) {
            refundLayout.setVisibility(View.VISIBLE);
        } else {
            refundLayout.setVisibility(View.GONE);
        }
        if ("拒绝退款".equals(refundDetailBean.getStatus_display()) || "没有退款".equals(refundDetailBean.getStatus_display()) ||
            "退款关闭".equals(refundDetailBean.getStatus_display())) {
            statusLayout.setVisibility(View.GONE);
        } else {
            showRefundStatus(refundDetailBean);
        }
    }

    private void showRefundStatus(AllRefundsBean.ResultsEntity refundDetailBean) {
        List<StatusShaftBean> status_shaft = refundDetailBean.getStatus_shaft();
        if (refundDetailBean.isHas_good_return()) {
            textView3.setVisibility(View.VISIBLE);
            lineImage3.setVisibility(View.VISIBLE);
            imageView3.setVisibility(View.VISIBLE);
            iView.setVisibility(View.VISIBLE);
            lineImage.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
        } else {
            LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            lp.gravity = Gravity.CENTER;
            statusLayout.setLayoutParams(lp);
            driver.setVisibility(View.GONE);
        }
        if (status_shaft.size() > 1) {
            String display = status_shaft.get(status_shaft.size() - 1).getStatus_display();
            if ("同意申请".equals(display)) {
                setView1();
            } else if ("退货待收".equals(display)) {
                setView2();
            } else if ("等待返款".equals(display)) {
                setView3();
            } else if ("退款成功".equals(display)) {
                setView4();
            }
        }
    }

    private void setView2() {
        setView1();
        textView2.setTextColor(getResources().getColor(R.color.text_color_32));
        textView2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
        textView.setTextColor(getResources().getColor(R.color.colorAccent));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        imageView2.setImageResource(R.drawable.status_black);
        iView.setImageResource(R.drawable.state_in);
        lineImage.setBackgroundColor(getResources().getColor(R.color.text_color_32));
    }

    private void setView4() {
        setView3();
        textView4.setTextColor(getResources().getColor(R.color.text_color_32));
        textView4.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
        textView5.setTextColor(getResources().getColor(R.color.colorAccent));
        textView5.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        imageView4.setImageResource(R.drawable.status_black);
        imageView5.setImageResource(R.drawable.state_in);
        lineImage5.setBackgroundColor(getResources().getColor(R.color.text_color_32));
        lineImage6.setBackgroundColor(getResources().getColor(R.color.text_color_32));
    }

    private void setView3() {
        setView2();
        textView.setTextColor(getResources().getColor(R.color.text_color_32));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
        textView3.setTextColor(getResources().getColor(R.color.text_color_32));
        textView3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
        textView4.setTextColor(getResources().getColor(R.color.colorAccent));
        textView4.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        iView.setImageResource(R.drawable.status_black);
        imageView3.setImageResource(R.drawable.status_black);
        imageView4.setImageResource(R.drawable.state_in);
        lineImage3.setBackgroundColor(getResources().getColor(R.color.text_color_32));
        lineImage4.setBackgroundColor(getResources().getColor(R.color.text_color_32));
    }

    private void setView1() {
        textView1.setTextColor(getResources().getColor(R.color.text_color_32));
        textView1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
        textView2.setTextColor(getResources().getColor(R.color.colorAccent));
        textView2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        imageView1.setImageResource(R.drawable.status_black);
        imageView2.setImageResource(R.drawable.state_in);
        lineImage2.setBackgroundColor(getResources().getColor(R.color.text_color_32));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_write:
                Log.d(TAG, "write logistics");
                Intent intent = new Intent(this, WriteLogisticsInfoActivty.class);
                Bundle bundle = new Bundle();
                bundle.putInt("goods_id", refundDetail.getOrder_id());
                if (refundDetail != null) {
                    if ((refundDetail.getReturn_address() != null)
                        && (!refundDetail.getReturn_address().isEmpty())) {
                        bundle.putString("address", refundDetail.getReturn_address());
                    }
                }
                bundle.putBoolean("flag", isWrited);
                if (isWrited) {
                    bundle.putInt("rid", refundDetail.getId());
                    bundle.putString("company_name", refundDetail.getCompany_name());
                    bundle.putString("packetid", refundDetail.getSid());
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    intent.putExtras(bundle);
                    startActivityForResult(intent, 0);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == 0) {
            boolean flag = false;
            if (data != null) {
                flag = data.getBooleanExtra("flag", false);
            }
            if (flag) {
                initData();
            }
        }
    }
}
