package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.ResponseResultBean;
import com.jimei.xiaolumeimei.model.MamaInfoModel;
import com.jimei.xiaolumeimei.widget.SmoothCheckBox;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;

import butterknife.Bind;
import rx.Subscription;
import rx.schedulers.Schedulers;

public class MamaDrawCashActivity extends BaseSwipeBackCompatActivity implements SmoothCheckBox.OnCheckedChangeListener, TextWatcher, View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    @Bind(R.id.tv_money)
    TextView moneyTv;
    @Bind(R.id.scb)
    SmoothCheckBox smoothCheckBox;
    @Bind(R.id.et_money)
    EditText moneyEt;
    @Bind(R.id.tv_msg)
    TextView msgTv;
    @Bind(R.id.rb_wx)
    RadioButton wxRbtn;
    @Bind(R.id.rb_xl)
    RadioButton xlRbtn;
    @Bind(R.id.btn_draw_cash)
    Button drawCashBtn;
    @Bind(R.id.rg_money)
    RadioGroup moneyRg;
    private double cash;
    private double drawMoney = 0;
    Subscription subscribe;

    @Override
    protected void setListener() {
        smoothCheckBox.setOnCheckedChangeListener(this);
        moneyEt.addTextChangedListener(this);
        drawCashBtn.setOnClickListener(this);
        wxRbtn.setOnClickListener(this);
        xlRbtn.setOnClickListener(this);
        moneyRg.setOnCheckedChangeListener(this);
    }

    @Override
    protected void initData() {
        moneyEt.setText("100");
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
        String fund_type = "";
        if ((Double.compare(fund, 100) != 0) && (Double.compare(fund, 200) != 0)) {
            JUtils.Toast("请选择转账金额。");
        } else {
            if (Double.compare(fund, 100) == 0) {
                fund_type = "c1";
            } else if (Double.compare(fund, 200) == 0) {
                fund_type = "c2";
            }
            subscribe = MamaInfoModel.getInstance()
                    .toWallet(fund_type)
                    .subscribeOn(Schedulers.io())
                    .subscribe(new ServiceResponse<ResponseResultBean>() {
                        String msg = "";

                        @Override
                        public void onNext(ResponseResultBean resp) {
                            switch (resp.getCode()) {
                                case 0:
                                    msg = "转账成功,请查询~~";
                                    break;
                                case 1:
                                    msg = "暂未开放!";
                                    break;
                                case 2:
                                    msg = "不足转账金额";
                                    break;
                                case 3:
                                    msg = "有待审核记录不予操作";
                                    break;
                                default:
                                    msg = "系统状态异常";
                                    break;
                            }
                        }

                        @Override
                        public void onCompleted() {
                            JUtils.ToastLong(msg);
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
    public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
        if (isChecked) {
            if (cash >= 200) {
                moneyEt.setText("200");
            } else {
                moneyEt.setText(cash + "");
            }
        } else {
            moneyEt.setText("");
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.toString().contains(".")) {
            if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                s = s.toString().subSequence(0,
                        s.toString().indexOf(".") + 3);
                moneyEt.setText(s);
                moneyEt.setSelection(s.length());
            }
        }
        if (s.toString().trim().substring(0).equals(".")) {
            s = "0" + s;
            moneyEt.setText(s);
            moneyEt.setSelection(2);
        }

        if (s.toString().startsWith("0")
                && s.toString().trim().length() > 1) {
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
        } else {
            moneyEt.setTextSize(32);
            drawMoney = Double.parseDouble(s.toString());
        }
        if (cash < 8.88) {
            msgTv.setText("您的余额未满8.88元，不满足提现条件");
            drawCashBtn.setClickable(false);
            drawCashBtn.setBackgroundResource(R.drawable.shape_common_unclickable);
        } else {
            if (drawMoney < 8.88) {
                msgTv.setText("提现最低金额需要大于8.88元哦");
                drawCashBtn.setClickable(false);
                drawCashBtn.setBackgroundResource(R.drawable.shape_common_unclickable);
            } else if (drawMoney > 200) {
                msgTv.setText("提现金额超过微信红包限额");
                drawCashBtn.setClickable(false);
                drawCashBtn.setBackgroundResource(R.drawable.shape_common_unclickable);
            } else if (drawMoney > cash) {
                msgTv.setText("提现金额超过账户可提额度");
                drawCashBtn.setClickable(false);
                drawCashBtn.setBackgroundResource(R.drawable.shape_common_unclickable);
            } else {
                msgTv.setText("");
                drawCashBtn.setClickable(true);
                drawCashBtn.setBackgroundResource(R.drawable.shape_common);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_draw_cash:
                if (wxRbtn.isChecked()) {
                    drawCash(drawMoney);
                } else if (xlRbtn.isChecked()) {
                    toWallet(drawMoney);
                    finish();
                } else {
                    JUtils.Toast("请选择一种提现方式");
                }
                break;
            case R.id.rb_wx:
                wxRbtn.setChecked(true);
                xlRbtn.setChecked(false);
                break;
            case R.id.rb_xl:
                wxRbtn.setChecked(false);
                xlRbtn.setChecked(true);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_history:
                startActivity(new Intent(this, MamaWithdrawCashHistoryActivity.class));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_withdrawcash, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_100:
                drawMoney = 100;
                moneyEt.setText("100");
                break;
            case R.id.rb_200:
                drawMoney = 200;
                moneyEt.setText("200");
                break;
        }
    }
}
