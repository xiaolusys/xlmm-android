package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.ResponseResultBean;
import com.jimei.xiaolumeimei.model.MamaInfoModel;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;

import butterknife.Bind;
import rx.Subscription;
import rx.schedulers.Schedulers;

public class MamaDrawCashActivity extends BaseSwipeBackCompatActivity implements View.OnClickListener {
    @Bind(R.id.tv_money)
    TextView moneyTv;
    @Bind(R.id.iv_wx)
    ImageView wxIv;
    @Bind(R.id.iv_xl)
    ImageView xlIv;
    @Bind(R.id.btn_draw_cash)
    Button drawCashBtn;
    @Bind(R.id.tv_100)
    TextView textView100;
    @Bind(R.id.tv_200)
    TextView textView200;
    @Bind(R.id.xl_layout)
    LinearLayout xlLayout;
    @Bind(R.id.wx_layout)
    LinearLayout wxLayout;
    @Bind(R.id.iv_100)
    ImageView iv100;
    @Bind(R.id.iv_200)
    ImageView iv200;
    private double cash;
    private double drawMoney = 0;
    Subscription subscribe;
    boolean flag = true;

    @Override
    protected void setListener() {
        drawCashBtn.setOnClickListener(this);
        xlLayout.setOnClickListener(this);
        wxLayout.setOnClickListener(this);
        iv100.setOnClickListener(this);
        iv200.setOnClickListener(this);
        textView100.setOnClickListener(this);
        textView200.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        drawMoney = 100;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        cash = extras.getDouble("cash");
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_mama_draw_cash;
    }

    @Override
    protected void initViews() {
        moneyTv.setText(cash + "元");
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    private void toWallet(double fund) {
        String cashout_amount = "";
        if ((Double.compare(fund, 100) != 0) && (Double.compare(fund, 200) != 0)) {
            JUtils.Toast("请选择转账金额。");
        } else {
            if (Double.compare(fund, 100) == 0) {
                cashout_amount = "100.0";
            } else if (Double.compare(fund, 200) == 0) {
                cashout_amount = "200.0";
            }
            subscribe = MamaInfoModel.getInstance()
                    .toWallet(cashout_amount)
                    .subscribeOn(Schedulers.io())
                    .subscribe(new ServiceResponse<ResponseResultBean>() {
                        String msg = "";
                        int code = -1;

                        @Override
                        public void onNext(ResponseResultBean resp) {
                            msg = resp.getMsg();
                            code = resp.getCode();
                        }

                        @Override
                        public void onCompleted() {
                            if (code == 0) {
                                Intent intent = new Intent(MamaDrawCashActivity.this, DrawCashResultActivity.class);
                                startActivity(intent);
                            } else {
                                JUtils.ToastLong(msg);
                            }
                            finish();
                        }
                    });
        }
    }

    private void drawCash(double fund) {
        String fund_type = "";
        if ((Double.compare(fund, 100) != 0) && (Double.compare(fund, 200) != 0)) {
            JUtils.Toast("请选择提现金额。");
        } else {
            if (Double.compare(fund, 100) == 0) {
                fund_type = "c1";
            } else if (Double.compare(fund, 200) == 0) {
                fund_type = "c2";
            }
            subscribe = MamaInfoModel.getInstance()
                    .withdraw_cash(fund_type)
                    .subscribeOn(Schedulers.io())
                    .subscribe(new ServiceResponse<ResponseResultBean>() {
                        @Override
                        public void onNext(ResponseResultBean resp) {
                            Intent intent = new Intent(MamaDrawCashActivity.this,
                                    MamaWithdrawCashResultActivity.class);
                            intent.putExtra("cash", fund);
                            intent.putExtra("code", resp.getCode());
                            startActivity(intent);
                            MamaDrawCashActivity.this.finish();
                        }
                    });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_draw_cash:
                if (!flag) {
                    drawCash(drawMoney);
                } else {
                    toWallet(drawMoney);
                }
                break;
            case R.id.wx_layout:
                flag = false;
                wxIv.setImageResource(R.drawable.radio_bg_checked);
                xlIv.setImageResource(R.drawable.radio_bg);
                break;
            case R.id.xl_layout:
                flag = true;
                xlIv.setImageResource(R.drawable.radio_bg_checked);
                wxIv.setImageResource(R.drawable.radio_bg);
                break;
            case R.id.tv_100:
            case R.id.iv_100:
                drawMoney = 100;
                iv100.setImageResource(R.drawable.radio_bg_checked);
                iv200.setImageResource(R.drawable.radio_bg);
                textView100.setTextColor(getResources().getColor(R.color.colorAccent));
                textView100.setBackgroundResource(R.drawable.text_checked_bg);
                textView200.setTextColor(Color.parseColor("#c5c5c5"));
                textView200.setBackgroundResource(R.drawable.text_unchecked_bg);
                break;
            case R.id.tv_200:
            case R.id.iv_200:
                drawMoney = 200;
                iv200.setImageResource(R.drawable.radio_bg_checked);
                iv100.setImageResource(R.drawable.radio_bg);
                textView200.setTextColor(getResources().getColor(R.color.colorAccent));
                textView200.setBackgroundResource(R.drawable.text_checked_bg);
                textView100.setTextColor(Color.parseColor("#c5c5c5"));
                textView100.setBackgroundResource(R.drawable.text_unchecked_bg);
                break;
        }
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_history:
//                startActivity(new Intent(this, MamaWithdrawCashHistoryActivity.class));
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_withdrawcash, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
}
