package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.MaMaReNewBean;
import com.jimei.xiaolumeimei.event.MaMaInfoEmptyEvent;
import com.jimei.xiaolumeimei.event.UserChangeEvent;
import com.jimei.xiaolumeimei.model.MamaInfoModel;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.pingplusplus.android.PaymentActivity;
import com.umeng.analytics.MobclickAgent;
import java.io.IOException;
import okhttp3.ResponseBody;
import org.greenrobot.eventbus.EventBus;
import retrofit2.Response;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye on 16/7/19.
 */
public class MamaReNewActivity extends BaseSwipeBackCompatActivity implements View.OnClickListener {

  private static final int REQUEST_CODE_PAYMENT = 1;
  @Bind(R.id.image_99) ImageView image99;
  @Bind(R.id.image_991) ImageView image991;
  @Bind(R.id.image_188) ImageView image188;
  @Bind(R.id.image_1881) ImageView image1881;

  @Bind(R.id.confirm) RelativeLayout confirm;
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

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    return true;
  }

  @Override protected void onResume() {
    super.onResume();
    MobclickAgent.onPageStart(this.getClass().getSimpleName());
    MobclickAgent.onResume(this);
  }

  @Override protected void onPause() {
    super.onPause();
    MobclickAgent.onPageEnd(this.getClass().getSimpleName());
    MobclickAgent.onPause(this);
  }

  @Override protected void setListener() {
    image99.setOnClickListener(this);
    image991.setOnClickListener(this);
    image188.setOnClickListener(this);
    image1881.setOnClickListener(this);
    confirm.setOnClickListener(this);
  }

  @Override protected void initData() {
    MamaInfoModel.getInstance()
        .getRegisterProInfo()
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<MaMaReNewBean>() {
          @Override public void onNext(MaMaReNewBean maMaReNewBean) {
            if (null != maMaReNewBean) {
              productId = maMaReNewBean.getProduct().getId();
              for (int i = 0; i < maMaReNewBean.getProduct().getNormalSkus().size(); i++) {
                if (maMaReNewBean.getProduct().getNormalSkus().get(i).getOuterId().equals("1")) {
                  skuId188 = maMaReNewBean.getProduct().getNormalSkus().get(i).getId();
                }

                if (maMaReNewBean.getProduct().getNormalSkus().get(i).getOuterId().equals("2")) {
                  skuId99 = maMaReNewBean.getProduct().getNormalSkus().get(i).getId();
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
              }
            }
          }

          @Override public void onError(Throwable e) {
            super.onError(e);
          }

          @Override public void onCompleted() {
            super.onCompleted();
          }
        });
  }

  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.activity_mamarenew;
  }

  @Override protected void initViews() {
    radio_99Choose = true;
    radio_188Choose = false;
    image991.setVisibility(View.GONE);
    image99.setVisibility(View.VISIBLE);
    image188.setVisibility(View.GONE);
    image1881.setVisibility(View.VISIBLE);
  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }

  public void mamaPay(String product_id, String sku_id, String payment, String channel, String num,
      String post_fee, String discount_fee, String uuid, String total_fee) {
    showIndeterminateProgressDialog(false);
    MamaInfoModel.getInstance()
        .mamaRegisterPay(product_id, sku_id, payment, channel, num, post_fee, discount_fee, uuid,
            total_fee)
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<Response<ResponseBody>>() {
          @Override public void onNext(Response<ResponseBody> responseBodyResponse) {
            if (responseBodyResponse.isSuccessful()) {
              try {
                Intent intent = new Intent();
                String packageName = getPackageName();
                ComponentName componentName =
                    new ComponentName(packageName, packageName + ".wxapi.WXPayEntryActivity");
                intent.setComponent(componentName);
                intent.putExtra(PaymentActivity.EXTRA_CHARGE, responseBodyResponse.body().string());
                startActivityForResult(intent, REQUEST_CODE_PAYMENT);
              } catch (IOException e) {
                e.printStackTrace();
              }
            }
          }

          @Override public void onError(Throwable e) {
            super.onError(e);
          }

          @Override public void onCompleted() {
            super.onCompleted();
            hideIndeterminateProgressDialog();
          }
        });
  }

  @Override public void onClick(View v) {
    switch (v.getId()) {
      case R.id.confirm:
        new MyDialog(this).show();
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
      mView.findViewById(R.id.finish_iv).setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          MyDialog.this.dismiss();
        }
      });

      alipay_layout.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          if (radio_188Choose) {
            mamaPay(productId + "", skuId188 + "", payment188 + "", "alipay", "1", postfee188 + "",
                discountfee188 + "", uuid, totalfee188 + "");
          } else if (radio_99Choose) {
            mamaPay(productId + "", skuId99 + "", payment99 + "", "alipay", "1", postfee99 + "",
                discountfee99 + "", uuid, totalfee99 + "");
          }
          dismiss();
        }
      });

      wx_layout.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          if (radio_188Choose) {
            mamaPay(productId + "", skuId188 + "", payment188 + "", "wx", "1", postfee188 + "",
                discountfee188 + "", uuid, totalfee188 + "");
          } else if (radio_99Choose) {
            mamaPay(productId + "", skuId99 + "", payment99 + "", "wx", "1", postfee99 + "",
                discountfee99 + "", uuid, totalfee99 + "");
          }
          dismiss();
        }
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

  public void showMsg(String title, String msg1, String msg2) {
    String str = title;
    if (null != msg1 && msg1.length() != 0) {
      str += "\n" + msg1;
    }
    if (null != msg2 && msg2.length() != 0) {
      str += "\n" + msg2;
    }
    if (title.equals("fail")) {
      str = "支付失败，请重试！";
    } else if (title.equals("invalid")) {
      str = "支付失败，支付软件未安装完整！";
    }

    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setMessage(str);
    builder.setTitle("提示");
    builder.setPositiveButton("OK", null);
    builder.create().show();
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    //支付页面返回处理
    EventBus.getDefault().postSticky(new UserChangeEvent());
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
          finish();
        } else if (result.equals("success")) {
          EventBus.getDefault().post(new MaMaInfoEmptyEvent());
          finish();
        } else {
          showMsg(result, errorMsg, extraMsg);
        }
      }
    }
  }
}
