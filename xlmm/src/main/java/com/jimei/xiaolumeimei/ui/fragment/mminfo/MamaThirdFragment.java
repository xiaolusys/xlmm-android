package com.jimei.xiaolumeimei.ui.fragment.mminfo;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jimei.library.utils.ViewUtils;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseBindingFragment;
import com.jimei.xiaolumeimei.databinding.FragmentMamaThirdBinding;
import com.jimei.xiaolumeimei.entities.MamaFortune;
import com.jimei.xiaolumeimei.entities.MamaUrl;
import com.jimei.xiaolumeimei.entities.event.WebViewEvent;
import com.jimei.xiaolumeimei.model.MamaInfoModel;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MMFansActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MMShoppingListActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MMTeamActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MMcarryLogActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MamaLivenessActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MamaVisitorActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MamaWalletActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.PersonalCarryRankActivity;
import com.jimei.xiaolumeimei.widget.NoDoubleClickListener;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;

public class MamaThirdFragment extends BaseBindingFragment<FragmentMamaThirdBinding> implements View.OnClickListener {
    private static final String TITLE = "title";

    private int mamaId = -1;
    private String teamUrl;
    private String fansUrl;
    private double mCashValue;
    private String carryLogMoney;
    private String hisConfirmedCashOut;
    private int mOrderNum;
    private int mActiveValueNum;
    private long lastClickTime = 0;

    public static MamaThirdFragment newInstance(String title) {
        MamaThirdFragment fragment = new MamaThirdFragment();
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initData() {
        refreshFortune();
        addSubscription(MamaInfoModel.getInstance()
                .getMamaUrl()
                .subscribe(this::setUrl, Throwable::printStackTrace));
    }

    public void refreshFortune() {
        addSubscription(MamaInfoModel.getInstance()
                .getMamaFortune()
                .subscribe(this::initFortune, throwable -> {
                    throwable.printStackTrace();
                    hideIndeterminateProgressDialog();
                }));
    }

    private void initFortune(MamaFortune mamaFortune) {
        MamaFortune.MamaFortuneBean fortune = mamaFortune.getMamaFortune();
        carryLogMoney = fortune.getCarryValue() + "";
        mCashValue = fortune.getCashValue();
        mOrderNum = fortune.getOrderNum();
        mActiveValueNum = fortune.getActiveValueNum();
        if (fortune.getExtraInfo() != null) {
            hisConfirmedCashOut = fortune.getExtraInfo().getHisConfirmedCashOut();
            if (fortune.getExtraInfo().getThumbnail() != null) {
                ViewUtils.loadImgToImgView(mActivity, b.ivHead,
                        fortune.getExtraInfo().getThumbnail());
            }
            b.tvLevel.setText(fortune.getExtraInfo().getAgencylevelDisplay());
        }
        b.tvId.setText("ID:" + fortune.getMamaId());
        mamaId = fortune.getMamaId();
        b.tvMamaName.setText(fortune.getMamaLevelDisplay());
        b.tvCashValue.setText(fortune.getCashValue() + "元");
        b.tvCarryValue.setText(fortune.getCarryValue() + "元");
        b.tvOrder.setText(fortune.getOrderNum() + "个");
        b.tvActive.setText(fortune.getActiveValueNum() + "点");
        b.tvFans.setText(fortune.getFansNum() + "个");
        b.tvPersonal.setText(fortune.getExtra_figures().getPersonal_total_rank() + "名");
        b.tvTeam.setText(fortune.getExtra_figures().getTeam_total_rank() + "名");
        hideIndeterminateProgressDialog();
    }

    private void setUrl(MamaUrl mamaUrl) {
        MamaUrl.ResultsBean.ExtraBean bean = mamaUrl.getResults().get(0).getExtra();
        teamUrl = bean.getTeam_explain();
        fansUrl = bean.getFans_explain();
    }

    public void setListener() {
        b.llWallet.setOnClickListener(this);
        b.llIncome.setOnClickListener(this);
        b.llVisit.setOnClickListener(this);
        b.llOrder.setOnClickListener(this);
        b.llActive.setOnClickListener(this);
        b.llFans.setOnClickListener(this);
        b.llPersonal.setOnClickListener(this);
        b.llTeam.setOnClickListener(this);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_mama_third;
    }

    @Override
    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > NoDoubleClickListener.MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            switch (v.getId()) {
                case R.id.ll_wallet:
                    Intent walletIntent = new Intent(mActivity, MamaWalletActivity.class);
                    walletIntent.putExtra("cash", mCashValue);
                    startActivity(walletIntent);
                    break;
                case R.id.ll_income:
                    Bundle incomeBundle = new Bundle();
                    incomeBundle.putString("carrylogMoney", carryLogMoney);
                    incomeBundle.putString("hisConfirmedCashOut", hisConfirmedCashOut);
                    Intent incomeIntent = new Intent(mActivity, MMcarryLogActivity.class);
                    incomeIntent.putExtras(incomeBundle);
                    startActivity(incomeIntent);
                    break;
                case R.id.ll_visit:
                    Intent visitIntent = new Intent(mActivity, MamaVisitorActivity.class);
                    startActivity(visitIntent);
                    break;
                case R.id.ll_order:
                    Intent orderIntent = new Intent(mActivity, MMShoppingListActivity.class);
                    orderIntent.putExtra("orderNum", mOrderNum);
                    startActivity(orderIntent);
                    break;
                case R.id.ll_active:
                    Intent activeIntent = new Intent(mActivity, MamaLivenessActivity.class);
                    activeIntent.putExtra("liveness", mActiveValueNum);
                    startActivity(activeIntent);
                    break;
                case R.id.ll_fans:
                    if (fansUrl != null && !"".equals(fansUrl)) {
                        EventBus.getDefault().postSticky(new WebViewEvent(fansUrl));
                        startActivity(new Intent(mActivity, MMFansActivity.class));
                    }
                    break;
                case R.id.ll_personal:
                    startActivity(new Intent(mActivity, PersonalCarryRankActivity.class));
                    break;
                case R.id.ll_team:
                    if (teamUrl != null && !"".equals(teamUrl) && mamaId != -1) {
                        Bundle teamBundle = new Bundle();
                        teamBundle.putString("id", mamaId + "");
                        teamBundle.putString("url", teamUrl);
                        Intent teamIntent = new Intent(mActivity, MMTeamActivity.class);
                        teamIntent.putExtras(teamBundle);
                        startActivity(teamIntent);
                    }
                    break;
            }
        }
    }

    @Override
    public View getLoadingView() {
        return b.scrollView;
    }
}
