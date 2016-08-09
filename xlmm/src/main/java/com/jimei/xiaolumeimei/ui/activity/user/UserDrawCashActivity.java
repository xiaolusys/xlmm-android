package com.jimei.xiaolumeimei.ui.activity.user;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.UserInfoBean;
import com.jimei.xiaolumeimei.entities.UserWithdrawResult;
import com.jimei.xiaolumeimei.event.UserChangeEvent;
import com.jimei.xiaolumeimei.model.UserModel;
import com.jimei.xiaolumeimei.model.UserNewModel;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.DrawCashResultActivity;
import com.jimei.xiaolumeimei.widget.SmoothCheckBox;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import rx.Subscription;
import rx.schedulers.Schedulers;

public class UserDrawCashActivity extends BaseSwipeBackCompatActivity
        implements SmoothCheckBox.OnCheckedChangeListener, TextWatcher, View.OnClickListener {
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
    boolean bindFlag = false;
    private double money;
    private double drawMoney = 0;

    @Override
    protected void setListener() {
        smoothCheckBox.setOnCheckedChangeListener(this);
        moneyEt.addTextChangedListener(this);
        drawCashBtn.setOnClickListener(this);
        bindBtn.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        Subscription subscribe = UserNewModel.getInstance()
                .getProfile()
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<UserInfoBean>() {
                    @Override
                    public void onNext(UserInfoBean userNewBean) {
                        if (userNewBean != null) {
                            if (null != userNewBean.getUserBudget()) {
                                money = userNewBean.getUserBudget().getBudgetCash();
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
                });
        addSubscription(subscribe);
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
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
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
                return;
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
        if (!bindFlag) {
            msgTv.setText("请先绑定微信后提现哦~~");
            msgTv.setTextColor(getResources().getColor(R.color.red));
            drawCashBtn.setClickable(false);
            drawCashBtn.setBackgroundResource(R.drawable.shape_common_unclickable);
        } else if (money < 8.88) {
            msgTv.setText("您的余额未满8.88元，不满足提现条件");
            msgTv.setTextColor(getResources().getColor(R.color.red));
            drawCashBtn.setClickable(false);
            drawCashBtn.setBackgroundResource(R.drawable.shape_common_unclickable);
        } else {
            if (drawMoney < 8.88) {
                msgTv.setText("提现最低金额需要8.88元哦");
                msgTv.setTextColor(getResources().getColor(R.color.red));
                drawCashBtn.setClickable(false);
                drawCashBtn.setBackgroundResource(R.drawable.shape_common_unclickable);
            } else if (drawMoney > money) {
                msgTv.setText("提现金额超过账户可提额度");
                msgTv.setTextColor(getResources().getColor(R.color.red));
                drawCashBtn.setClickable(false);
                drawCashBtn.setBackgroundResource(R.drawable.shape_common_unclickable);
            } else if (drawMoney > 200) {
                msgTv.setText("提现金额超过微信红包限额");
                msgTv.setTextColor(getResources().getColor(R.color.red));
                drawCashBtn.setClickable(false);
                drawCashBtn.setBackgroundResource(R.drawable.shape_common_unclickable);
            } else {
                msgTv.setText("提现金额将以微信红包形式，24小时内发至你绑定的微信账户");
                msgTv.setTextColor(Color.parseColor("#BBBBBB"));
                drawCashBtn.setClickable(true);
                drawCashBtn.setBackgroundResource(R.drawable.shape_common);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_draw_cash:
                if (drawMoney == 0) {
                    JUtils.Toast("提现金额不能为空,并且要大于0哦~~");
                } else {
                    drawCash(drawMoney);
                }
                break;
            case R.id.btn_bind:
                startActivity(new Intent(this, WxPubTwoDimenCodeActivity.class));
                finish();
                break;
        }
    }

    private void drawCash(double fund) {
        Subscription subscribe = UserModel.getInstance()
                .user_withdraw_cash(Double.toString(fund))
                .subscribeOn(Schedulers.io())
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
                    }
                });
        addSubscription(subscribe);
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

    @Override protected void onStop() {
        super.onStop();
        EventBus.getDefault().postSticky(new UserChangeEvent());
    }
}
