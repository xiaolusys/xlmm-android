package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.jimei.library.utils.JUtils;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseMVVMActivity;
import com.jimei.xiaolumeimei.databinding.ActivityMamaWalletBinding;
import com.jimei.xiaolumeimei.entities.CashoutPolicy;
import com.jimei.xiaolumeimei.entities.event.WalletEvent;
import com.jimei.xiaolumeimei.model.MamaInfoModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MamaWalletActivity extends BaseMVVMActivity<ActivityMamaWalletBinding> implements View.OnClickListener {
    private double mCash;
    private double min;
    private double max;
    private String mMessage;

    @Override
    protected void setListener() {
        b.llSmall.setOnClickListener(this);
        b.llHundred.setOnClickListener(this);
        b.llCoupon.setOnClickListener(this);
        b.tvRule.setOnClickListener(this);
    }

    @Override
    public View getLoadingView() {
        return b.layout;
    }

    @Override
    protected void initData() {
        addSubscription(MamaInfoModel.getInstance()
                .getCashoutPolicy()
                .subscribe(this::initCashoutPolicy, Throwable::printStackTrace));
        addSubscription(MamaInfoModel.getInstance()
                .getMamaFortune()
                .subscribe(mamaFortune -> {
                    mCash = mamaFortune.getMamaFortune().getCashValue();
                    b.money.setText(mCash + "");
                }, Throwable::printStackTrace));
    }


    private void initCashoutPolicy(CashoutPolicy cashoutPolicy) {
        min = cashoutPolicy.getMin_cashout_amount();
        max = cashoutPolicy.getAudit_cashout_amount();
        mMessage = cashoutPolicy.getMessage();
        b.tvSmall.setText("提现金额" + min + "元至" + max + "元");
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
        EventBus.getDefault().register(this);
        b.money.setText(mCash + "");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshData(WalletEvent event) {
        initData();
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
                Intent hundredIntent = new Intent(this, MamaDrawCashActivity.class);
                hundredIntent.putExtra("cash", mCash);
                startActivity(hundredIntent);
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
