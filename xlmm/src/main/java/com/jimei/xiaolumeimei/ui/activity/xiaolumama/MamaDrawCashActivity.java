package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jimei.library.utils.JUtils;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.ResponseResultBean;
import com.jimei.xiaolumeimei.entities.UserInfoBean;
import com.jimei.xiaolumeimei.entities.event.WalletEvent;
import com.jimei.xiaolumeimei.model.MamaInfoModel;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;

public class MamaDrawCashActivity extends BaseSwipeBackCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
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
    @Bind(R.id.nick_name)
    TextView nickNameTv;
    @Bind(R.id.tv_rule)
    TextView tvRule;
    @Bind(R.id.cb_rule)
    CheckBox cbRule;
    @Bind(R.id.layout)
    LinearLayout layout;
    private double cash;
    private double drawMoney = 0;
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
        tvRule.setOnClickListener(this);
        cbRule.setOnCheckedChangeListener(this);
    }

    @Override
    protected void initData() {
        drawMoney = 100;
        addSubscription(MamaInfoModel.getInstance()
                .getUserInfo()
                .subscribe(new ServiceResponse<UserInfoBean>() {
                    @Override
                    public void onNext(UserInfoBean userNewBean) {
                        if (userNewBean != null) {
                            nickNameTv.setText(userNewBean.getNick());
                        }
                    }
                }));
    }

    @Override
    public View getLoadingView() {
        return layout;
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

    private void toWallet(double fund) {
        if (fund == 0) {
            JUtils.Toast("请选择转账金额。");
        } else {
            addSubscription(MamaInfoModel.getInstance()
                    .toWallet(fund + "")
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
                            EventBus.getDefault().post(new WalletEvent());
                            if (code == 0) {
                                Intent intent = new Intent(MamaDrawCashActivity.this, DrawCashResultActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("account", "小鹿钱包");
                                intent.putExtras(bundle);
                                startActivity(intent);
                            } else {
                                JUtils.ToastLong(msg);
                            }
                            finish();
                        }
                    }));
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
            addSubscription(MamaInfoModel.getInstance()
                    .withdraw_cash(fund_type)
                    .subscribe(new ServiceResponse<ResponseResultBean>() {
                        @Override
                        public void onNext(ResponseResultBean resp) {
                            EventBus.getDefault().post(new WalletEvent());
                            Intent intent = new Intent(MamaDrawCashActivity.this,
                                    MamaWithdrawCashResultActivity.class);
                            intent.putExtra("cash", fund);
                            intent.putExtra("code", resp.getCode());
                            intent.putExtra("msg", resp.getMsg());
                            startActivity(intent);
                            MamaDrawCashActivity.this.finish();
                        }
                    }));
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
            case R.id.tv_rule:
                new AlertDialog.Builder(this)
                        .setTitle("提现提示")
                        .setMessage("由于微信存在提现次数限制,为了在方便妈妈提现和多次小额提现等待审核时间长之间做一个"
                                + "平衡,我们设定提现金额为100元和200元.金额不足100元或者活跃值不足的情况下,"
                                + "妈妈可以兑换现金券进行消费.")
                        .setPositiveButton("确认", (dialog, which) -> {
                            dialog.dismiss();
                        })
                        .show();
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            drawCashBtn.setClickable(true);
            drawCashBtn.setEnabled(true);
        } else {
            drawCashBtn.setClickable(false);
            drawCashBtn.setEnabled(false);
        }
    }
}
