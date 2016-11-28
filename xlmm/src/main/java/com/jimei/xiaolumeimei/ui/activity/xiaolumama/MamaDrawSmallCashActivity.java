package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jimei.library.utils.JUtils;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.ResultEntity;
import com.jimei.xiaolumeimei.entities.UserInfoBean;
import com.jimei.xiaolumeimei.model.MamaInfoModel;
import com.jimei.xiaolumeimei.model.UserModel;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.umeng.analytics.MobclickAgent;

import butterknife.Bind;


public class MamaDrawSmallCashActivity extends BaseSwipeBackCompatActivity implements View.OnClickListener, TextWatcher {
    @Bind(R.id.tv_money)
    TextView moneyTv;
    @Bind(R.id.nick_name)
    TextView nickName;
    @Bind(R.id.et_money)
    EditText moneyEt;
    @Bind(R.id.et_code)
    EditText codeEt;
    @Bind(R.id.btn_code)
    Button codeBtn;
    @Bind(R.id.btn_draw_cash)
    Button drawCashBtn;
    @Bind(R.id.tv_msg)
    TextView msgTv;

    private double mCash, mMax, mMin;
    private CountDownTimer mCountDownTimer;
    private double drawMoney = 0;

    @Override
    protected void setListener() {
        codeBtn.setOnClickListener(this);
        drawCashBtn.setOnClickListener(this);
        moneyEt.addTextChangedListener(this);
    }

    @Override
    protected void initData() {
        addSubscription(MamaInfoModel.getInstance()
                .getUserInfo()
                .subscribe(new ServiceResponse<UserInfoBean>() {
                    @Override
                    public void onNext(UserInfoBean userNewBean) {
                        if (userNewBean != null) {
                            nickName.setText(userNewBean.getNick());
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
        mCash = extras.getDouble("cash");
        mMax = extras.getDouble("max");
        mMin = extras.getDouble("min");
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_mama_draw_small_cash;
    }

    @Override
    protected void initViews() {
        moneyTv.setText(mCash + "元");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
            case R.id.btn_draw_cash:
                if (drawMoney == 0) {
                    JUtils.Toast("提现金额不能为空,并且要大于0哦~~");
                } else if (codeEt.getText().length() < 6) {
                    JUtils.Toast("验证码有误");
                } else {
                    drawCashBtn.setClickable(false);
                    addSubscription(MamaInfoModel.getInstance()
                            .getNoauditCashout(drawMoney, codeEt.getText().toString())
                            .subscribe(resultEntity -> {
                                JUtils.Toast(resultEntity.getInfo());
                                if (resultEntity.getCode() == 0) {
                                    Intent intent = new Intent(MamaDrawSmallCashActivity.this, DrawCashResultActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("account", "微信账户");
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                    finish();
                                }
                            }, e -> {
                                e.printStackTrace();
                                drawCashBtn.setClickable(true);
                            }));
                }
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mCountDownTimer.cancel();
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
        if (mCash < mMin) {
            msgTv.setText("妈妈余额未满" + mMin + "元，不满足提现条件");
            setBtnUnClick();
        } else {
            if (drawMoney < mMin) {
                msgTv.setText("提现最低需要" + mMin + "元哦");
                setBtnUnClick();
            } else if (drawMoney > mCash) {
                msgTv.setText("提现金额超过账户可提额度");
                setBtnUnClick();
            } else if (drawMoney > mMax) {
                msgTv.setText("提现金额超过快速提现限额");
                setBtnUnClick();
            } else {
                msgTv.setText("提现妈妈余额将以微信红包形式，24小时内发至你绑定的微信账户");
                setBtnClick();
            }
        }
    }

    private void setBtnClick() {
        msgTv.setTextColor(Color.parseColor("#BBBBBB"));
        drawCashBtn.setClickable(true);
        drawCashBtn.setEnabled(true);
    }

    private void setBtnUnClick() {
        msgTv.setTextColor(getResources().getColor(R.color.red));
        drawCashBtn.setClickable(false);
        drawCashBtn.setEnabled(false);
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
}
