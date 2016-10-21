package com.jimei.xiaolumeimei.ui.activity.user;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.jimei.library.widget.SmoothCheckBox;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.ResultEntity;
import com.jimei.xiaolumeimei.entities.UserInfoBean;
import com.jimei.xiaolumeimei.entities.UserWithdrawResult;
import com.jimei.xiaolumeimei.entities.event.UserChangeEvent;
import com.jimei.xiaolumeimei.model.UserModel;
import com.jimei.xiaolumeimei.model.UserNewModel;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.DrawCashResultActivity;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;

public class UserDrawCashActivity extends BaseSwipeBackCompatActivity
        implements SmoothCheckBox.OnCheckedChangeListener, TextWatcher, View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private static final String TAG = UserDrawCashActivity.class.getSimpleName();
    @Bind(R.id.tv_money)
    TextView moneyTv;
    @Bind(R.id.scb)
    SmoothCheckBox smoothCheckBox;
    @Bind(R.id.et_money)
    EditText moneyEt;
    @Bind(R.id.tv_msg)
    TextView msgTv;
    @Bind(R.id.btn_draw_cash)
    Button drawCashBtn;
    @Bind(R.id.btn_bind)
    Button bindBtn;
    @Bind(R.id.nick_name)
    TextView nickNameTv;
    @Bind(R.id.tv_rule)
    TextView tvRule;
    @Bind(R.id.cb_rule)
    CheckBox cbRule;
    @Bind(R.id.et_code)
    EditText codeEt;
    @Bind(R.id.btn_code)
    Button codeBtn;
    private double minMoney = 8.88;
    private boolean bindFlag = false;
    private boolean ruleFlag = true;
    private double money;
    private double drawMoney = 0;
    private CountDownTimer mCountDownTimer;

    @Override
    protected void setListener() {
        smoothCheckBox.setOnCheckedChangeListener(this);
        moneyEt.addTextChangedListener(this);
        drawCashBtn.setOnClickListener(this);
        bindBtn.setOnClickListener(this);
        cbRule.setOnCheckedChangeListener(this);
        tvRule.setOnClickListener(this);
        codeBtn.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        addSubscription(UserNewModel.getInstance()
                .getProfile()
                .subscribe(new ServiceResponse<UserInfoBean>() {
                    @Override
                    public void onNext(UserInfoBean userNewBean) {
                        if (userNewBean != null) {
                            if (null != userNewBean.getUserBudget()) {
                                money = userNewBean.getUserBudget().getBudgetCash();
                                minMoney = userNewBean.getUserBudget().getCashOutLimit();
                            }
                            moneyTv.setText((float) (Math.round(money * 100)) / 100 + "元");
                            if (userNewBean.getIsAttentionPublic() == 1) {
                                bindFlag = true;
                            } else {
                                bindBtn.setVisibility(View.VISIBLE);
                            }
                            nickNameTv.setText(userNewBean.getNick());
                        }
                    }
                }));
        mCountDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                codeBtn.setText(millisUntilFinished / 1000 + "秒");
            }

            @Override
            public void onFinish() {
                codeBtn.setClickable(true);
                codeBtn.setEnabled(true);
                codeBtn.setText("获取验证码");
            }
        };
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        money = extras.getDouble("money");
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_user_draw_cash;
    }

    @Override
    protected void initViews() {
        moneyTv.setText(money + "元");
        smoothCheckBox.setCanClickable(true);
    }

    @Override
    public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
        if (isChecked) {
            if (money >= 200) {
                moneyEt.setText("200");
                moneyEt.setSelection(3);
                drawMoney = 200;
            } else {
                moneyEt.setText(money + "");
            }
        } else {
            moneyEt.setText("");
            drawMoney = 0;
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
                moneyEt.setText(s);
                moneyEt.setSelection(s.length());
            }
        }
        if (s.toString().equals(".")) {
            s = "0" + s;
            moneyEt.setText(s);
            moneyEt.setSelection(2);
        }

        if (s.toString().startsWith("0") && s.toString().length() > 1) {
            if (!s.toString().substring(1, 2).equals(".")) {
                moneyEt.setText(s.subSequence(0, 1));
                moneyEt.setSelection(1);
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() == 0) {
            moneyEt.setTextSize(14);
            drawMoney = 0;
        } else {
            moneyEt.setTextSize(32);
            if (!s.toString().startsWith(".")) {
                drawMoney = Double.parseDouble(s.toString());
            }
        }
        setBtn();
    }

    private void setBtn() {
        if (!ruleFlag) {
            msgTv.setText("请阅读个人提现条款并同意后可以提现哦");
            msgTv.setTextColor(getResources().getColor(R.color.red));
            setBtnUnClick();
        } else if (!bindFlag) {
            msgTv.setText("请先绑定微信后提现哦~~");
            msgTv.setTextColor(getResources().getColor(R.color.red));
            setBtnUnClick();
        } else if (money < minMoney) {
            msgTv.setText("您的零钱未满" + minMoney + "元，不满足提现条件");
            msgTv.setTextColor(getResources().getColor(R.color.red));
            setBtnUnClick();
        } else {
            if (drawMoney < minMoney) {
                msgTv.setText("提现最低金额需要" + minMoney + "元哦");
                msgTv.setTextColor(getResources().getColor(R.color.red));
                setBtnUnClick();
            } else if (drawMoney > money) {
                msgTv.setText("提现零钱超过账户可提额度");
                msgTv.setTextColor(getResources().getColor(R.color.red));
                setBtnUnClick();
            } else if (drawMoney > 200) {
                msgTv.setText("提现零钱超过微信红包限额");
                msgTv.setTextColor(getResources().getColor(R.color.red));
                setBtnUnClick();
            } else {
                msgTv.setText("提现零钱将以微信红包形式，24小时内发至你绑定的微信账户");
                msgTv.setTextColor(Color.parseColor("#BBBBBB"));
                setBtnClick();
            }
        }
    }

    private void setBtnClick() {
        drawCashBtn.setClickable(true);
        drawCashBtn.setEnabled(true);
    }

    private void setBtnUnClick() {
        drawCashBtn.setClickable(false);
        drawCashBtn.setEnabled(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_draw_cash:
                if (drawMoney == 0) {
                    JUtils.Toast("提现金额不能为空,并且要大于0哦~~");
                } else if (codeEt.getText().length() < 6) {
                    JUtils.Toast("验证码有误");
                } else {
                    drawCash(drawMoney);
                }
                break;
            case R.id.btn_bind:
                startActivity(new Intent(this, WxPubTwoDimenCodeActivity.class));
                finish();
                break;
            case R.id.tv_rule:
                new AlertDialog.Builder(this)
                        .setTitle("提现提示")
                        .setMessage("默认最低提现金额为8.88元，根据推广活动会调整此最低提现金额，目前最低提现金额为"
                                + minMoney + "元。不提现时零钱可以购物消费。")
                        .setPositiveButton("确认", (dialog, which) -> dialog.dismiss())
                        .show();
                break;
            case R.id.btn_code:
                codeBtn.setClickable(false);
                codeBtn.setEnabled(false);
                mCountDownTimer.start();
                addSubscription(UserModel.getInstance()
                        .getVerifyCode()
                        .subscribe(new ServiceResponse<ResultEntity>() {
                            @Override
                            public void onNext(ResultEntity resultEntity) {
                                JUtils.Toast(resultEntity.getInfo());
                            }

                            @Override
                            public void onError(Throwable e) {
                                JUtils.Toast("获取验证码失败");
                            }
                        }));
                break;
        }
    }

    private void drawCash(double fund) {
        drawCashBtn.setClickable(false);
        addSubscription(UserModel.getInstance()
                .user_withdraw_cash(Double.toString(fund), codeEt.getText().toString())
                .subscribe(new ServiceResponse<UserWithdrawResult>() {
                    @Override
                    public void onNext(UserWithdrawResult resp) {
                        switch (resp.getCode()) {
                            case 0:
                                JUtils.Log(TAG, "SUCCESS");
                                JUtils.Toast(resp.getMessage());
                                Intent intent = new Intent(UserDrawCashActivity.this, DrawCashResultActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("account", "微信账户");
                                intent.putExtras(bundle);
                                startActivity(intent);
                                finish();
                                break;
                            default:
                                JUtils.Log(TAG, "failed:" + resp.getCode());
                                JUtils.Toast(resp.getMessage());
                                break;
                        }
                        drawCashBtn.setClickable(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        drawCashBtn.setClickable(true);
                    }
                }));
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(this.getClass().getSimpleName());
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mCountDownTimer.cancel();
        EventBus.getDefault().postSticky(new UserChangeEvent());
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            ruleFlag = true;
            setBtn();
        } else {
            ruleFlag = false;
            setBtn();
        }
    }
}
