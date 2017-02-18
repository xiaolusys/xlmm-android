package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jimei.library.utils.JUtils;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.ResultEntity;
import com.jimei.xiaolumeimei.entities.UserInfoBean;
import com.jimei.xiaolumeimei.entities.event.WalletEvent;
import com.jimei.xiaolumeimei.model.MamaInfoModel;
import com.jimei.xiaolumeimei.service.ServiceResponse;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;

public class MamaDrawCashActivity extends BaseSwipeBackCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, TextWatcher {
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
    @Bind(R.id.edit_cash)
    EditText cashText;
    @Bind(R.id.layout_wx)
    LinearLayout layoutWx;
    private double cash;
    private double drawMoney = 100;
    private double walletMoney = 0;
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
        cashText.addTextChangedListener(this);
    }

    @Override
    protected void initData() {
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
                    .subscribe(new ServiceResponse<ResultEntity>() {
                        String msg = "";
                        int code = -1;

                        @Override
                        public void onNext(ResultEntity resp) {
                            msg = resp.getInfo();
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
                    .subscribe(new ServiceResponse<ResultEntity>() {
                        @Override
                        public void onNext(ResultEntity resp) {
                            EventBus.getDefault().post(new WalletEvent());
                            Intent intent = new Intent(MamaDrawCashActivity.this,
                                    MamaWithdrawCashResultActivity.class);
                            intent.putExtra("cash", fund);
                            intent.putExtra("code", resp.getCode());
                            intent.putExtra("msg", resp.getInfo());
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
                    toWallet(walletMoney);
                }
                break;
            case R.id.wx_layout:
                try {
                    View focus = getCurrentFocus();
                    if (focus != null) {
                        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                                hideSoftInputFromWindow(focus.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                flag = false;
                wxIv.setImageResource(R.drawable.radio_bg_checked);
                xlIv.setImageResource(R.drawable.radio_bg);
                layoutWx.setVisibility(View.VISIBLE);
                cashText.setVisibility(View.GONE);
                break;
            case R.id.xl_layout:
                flag = true;
                xlIv.setImageResource(R.drawable.radio_bg_checked);
                wxIv.setImageResource(R.drawable.radio_bg);
                layoutWx.setVisibility(View.GONE);
                cashText.setVisibility(View.VISIBLE);
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
                                + "妈妈可以提现到小鹿钱包或者兑换现金券进行消费.")
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

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.toString().contains(".")) {
            if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                s = s.toString().subSequence(0, s.toString().indexOf(".") + 3);
                cashText.setText(s);
                cashText.setSelection(s.length());
            }
        }
        if (s.toString().equals(".")) {
            s = "0" + s;
            cashText.setText(s);
            cashText.setSelection(2);
        }

        if (s.toString().startsWith("0") && s.toString().length() > 1) {
            if (!s.toString().substring(1, 2).equals(".")) {
                cashText.setText(s.subSequence(0, 1));
                cashText.setSelection(1);
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() == 0) {
            cashText.setTextSize(14);
            walletMoney = 0;
        } else {
            cashText.setTextSize(32);
            if (!s.toString().startsWith(".")) {
                walletMoney = Double.parseDouble(s.toString());
            }
        }
        if (walletMoney > cash) {
            walletMoney = cash;
            cashText.setText(walletMoney + "");
            cashText.setSelection((walletMoney + "").length());
        }
    }
}
