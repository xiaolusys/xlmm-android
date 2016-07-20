package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.DrawCouponBean;
import com.jimei.xiaolumeimei.model.MamaInfoModel;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;

import butterknife.Bind;
import rx.schedulers.Schedulers;

public class MamaDrawCouponActivity extends BaseSwipeBackCompatActivity implements View.OnClickListener {
    @Bind(R.id.tv_money)
    TextView moneyTv;
    @Bind(R.id.tv_msg)
    TextView msgTv;
    @Bind(R.id.rl_20)
    RelativeLayout relativeLayout20;
    @Bind(R.id.iv_flag20)
    ImageView flag20;
    @Bind(R.id.rl_50)
    RelativeLayout relativeLayout50;
    @Bind(R.id.iv_flag50)
    ImageView flag50;
    @Bind(R.id.btn_draw_coupon)
    Button button;

    private String id;
    private Double num;
    private String msg;
    private Double money;
    private AlertDialog alertDialog;

    @Override
    protected void setListener() {
        relativeLayout20.setOnClickListener(this);
        relativeLayout50.setOnClickListener(this);
        button.setOnClickListener(this);
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        msg = extras.getString("msg");
        money = extras.getDouble("cash");
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_mama_draw_coupon;
    }

    @Override
    protected void initViews() {
        moneyTv.setText(money + "");
        msgTv.setText(msg);
        alertDialog = new AlertDialog.Builder(this)
                .setMessage("兑换优惠券金额将直接从妈妈账户余额扣除!")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                        MamaInfoModel.getInstance()
                                .drawCoupon(id)
                                .subscribeOn(Schedulers.io())
                                .subscribe(new ServiceResponse<DrawCouponBean>() {
                                    @Override
                                    public void onNext(DrawCouponBean drawCouponBean) {
                                        JUtils.Toast(drawCouponBean.getInfo());
                                        if (drawCouponBean.getCode() == 0) {
                                            Intent intent = new Intent(MamaDrawCouponActivity.this, DrawCouponResultActivity.class);
                                            Bundle bundle = new Bundle();
                                            bundle.putString("num", num + "");
                                            intent.putExtras(bundle);
                                            startActivity(intent);
                                            money = money - num;
                                            moneyTv.setText(money + "");
                                            flag20.setVisibility(View.INVISIBLE);
                                            flag50.setVisibility(View.INVISIBLE);
                                            id = "";
                                            if (money < 20) {
                                                finish();
                                            }
                                        }
                                    }
                                });
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                })
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
            case R.id.rl_20:
                flag20.setVisibility(View.VISIBLE);
                flag50.setVisibility(View.INVISIBLE);
                num = 20d;
                id = "72";
                break;
            case R.id.rl_50:
                flag20.setVisibility(View.INVISIBLE);
                flag50.setVisibility(View.VISIBLE);
                num = 50d;
                id = "73";
                break;
            case R.id.btn_draw_coupon:
                if ("".equals(id)) {
                    JUtils.Toast("请选择需要兑换的优惠券");
                } else if (money < 20) {
                    JUtils.Toast("余额不足,无法兑换");
                } else {
                    alertDialog.show();
                }
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_history:
                startActivity(new Intent(this, MamaWithdrawCashHistoryActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_withdrawcash, menu);
        return super.onCreateOptionsMenu(menu);
    }


}
