package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jimei.library.utils.JUtils;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.CashoutPolicy;
import com.jimei.xiaolumeimei.model.MamaInfoModel;
import com.umeng.analytics.MobclickAgent;

import butterknife.Bind;

public class MamaWalletActivity extends BaseSwipeBackCompatActivity implements View.OnClickListener {
    @Bind(R.id.ll_small)
    LinearLayout smallLayout;
    @Bind(R.id.ll_hundred)
    LinearLayout hundredLayout;
    @Bind(R.id.ll_coupon)
    LinearLayout couponLayout;
    @Bind(R.id.money)
    TextView moneyTv;
    @Bind(R.id.tv_small)
    TextView smallTv;
    @Bind(R.id.tv_rule)
    TextView ruleTv;
    private double mCash;
    private double min;
    private double max;
    private String mMessage;

    @Override
    protected void setListener() {
        smallLayout.setOnClickListener(this);
        hundredLayout.setOnClickListener(this);
        couponLayout.setOnClickListener(this);
        ruleTv.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        addSubscription(MamaInfoModel.getInstance()
                .getCashoutPolicy()
                .subscribe(this::initCashoutPolicy, Throwable::printStackTrace));
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(this.getClass().getSimpleName());
        MobclickAgent.onResume(this);
        addSubscription(MamaInfoModel.getInstance()
                .getMamaFortune()
                .subscribe(mamaFortune -> {
                    mCash = mamaFortune.getMamaFortune().getCashValue();
                    moneyTv.setText(mCash + "");
                }, Throwable::printStackTrace));
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
        MobclickAgent.onPause(this);
    }
    private void initCashoutPolicy(CashoutPolicy cashoutPolicy) {
        min = cashoutPolicy.getMin_cashout_amount();
        max = cashoutPolicy.getAudit_cashout_amount();
        mMessage = cashoutPolicy.getMessage();
        smallTv.setText("提现金额" + min + "元至" + max + "元");
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        mCash = extras.getDouble("cash");
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_mama_wallet;
    }

    @Override
    protected void initViews() {
        moneyTv.setText(mCash + "");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_small:
                Intent intent = new Intent(this, MamaDrawSmallCashActivity.class);
                intent.putExtra("cash", mCash);
                intent.putExtra("max", max);
                intent.putExtra("min", min);
                startActivity(intent);
                break;
            case R.id.ll_hundred:
                if (mCash >= 100) {
                    Intent hundredIntent = new Intent(this, MamaDrawCashActivity.class);
                    hundredIntent.putExtra("cash", mCash);
                    startActivity(hundredIntent);
                } else {
                    JUtils.Toast("整额提现最低需要100元哦!");
                }
                break;
            case R.id.ll_coupon:
                if (mCash >= 20) {
                    Intent couponIntent = new Intent(this, MamaDrawCouponActivity.class);
                    couponIntent.putExtra("cash", mCash);
                    startActivity(couponIntent);
                } else {
                    JUtils.Toast("兑换现金消费券至少需要20元!");
                }
                break;
            case R.id.tv_rule:
                new AlertDialog.Builder(this)
                        .setTitle("提示")
                        .setMessage(mMessage)
                        .setPositiveButton("确认", (dialog, which) -> dialog.dismiss())
                        .show();
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
