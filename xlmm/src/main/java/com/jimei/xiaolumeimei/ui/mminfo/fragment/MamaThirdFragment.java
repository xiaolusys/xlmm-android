package com.jimei.xiaolumeimei.ui.mminfo.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseLazyFragment;
import com.jimei.xiaolumeimei.databinding.FragmentMamaThirdBinding;
import com.jimei.xiaolumeimei.entities.MamaFortune;
import com.jimei.xiaolumeimei.entities.MamaUrl;
import com.jimei.xiaolumeimei.event.WebViewEvent;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MMFans1Activity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MMShoppingListActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MMTeamActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MMcarryLogActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MamaLivenessActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MamaVisitorActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MamaWalletActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.PersonalCarryRankActivity;
import com.jimei.xiaolumeimei.ui.mminfo.MMInfoModel;
import com.jimei.xiaolumeimei.ui.mminfo.MamaActivity;
import com.jimei.xiaolumeimei.utils.RxUtils;
import com.jimei.xiaolumeimei.utils.ViewUtils;

import org.greenrobot.eventbus.EventBus;

public class MamaThirdFragment extends BaseLazyFragment<FragmentMamaThirdBinding> implements View.OnClickListener {
    private static final String TITLE = "title";
    private static final String ID = "id";

    private int id;
    private String teamUrl;
    private String fansUrl;
    private double mCashValue;
    private String carryLogMoney;
    private String hisConfirmedCashOut;
    private int mOrderNum;
    private int mActiveValueNum;

    public static MamaThirdFragment newInstance(String title, int id) {
        MamaThirdFragment fragment = new MamaThirdFragment();
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        args.putInt(ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getInt(ID);
        }
    }

    @Override
    protected void initData() {
        refreshFortune();
        addSubscription(MMInfoModel.getInstance()
                .getMamaUrl()
                .retryWhen(new RxUtils.RetryWhenNoInternet(100, 2000))
                .subscribe(this::setUrl, Throwable::printStackTrace));
        setListener();
    }

    public void refreshFortune() {
        if (mActivity != null) {
            ((MamaActivity) mActivity).showIndeterminateProgressDialog(true);
        }
        addSubscription(MMInfoModel.getInstance()
                .getMamaFortune()
                .retryWhen(new RxUtils.RetryWhenNoInternet(100, 2000))
                .subscribe(this::initFortune, Throwable::printStackTrace));
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
        b.tvMamaName.setText(fortune.getMamaLevelDisplay());
        b.tvCashValue.setText(fortune.getCashValue() + "元");
        b.tvCarryValue.setText(fortune.getCarryValue() + "元");
        b.tvOrder.setText(fortune.getOrderNum() + "个");
        b.tvActive.setText(fortune.getActiveValueNum() + "点");
        b.tvFans.setText(fortune.getFansNum() + "个");
        b.tvPersonal.setText(fortune.getExtra_figures().getPersonal_total_rank() + "名");
        b.tvTeam.setText(fortune.getExtra_figures().getTeam_total_rank() + "名");
        ((MamaActivity) mActivity).hideIndeterminateProgressDialog();
    }

    private void setUrl(MamaUrl mamaUrl) {
        MamaUrl.ResultsBean.ExtraBean bean = mamaUrl.getResults().get(0).getExtra();
        teamUrl = bean.getTeam_explain();
        fansUrl = bean.getFans_explain();
    }

    private void setListener() {
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
    public String getTitle() {
        String title;
        if (getArguments() != null) {
            title = getArguments().getString(TITLE);
        } else {
            title = "";
        }
        return title;
    }

    @Override
    public void onClick(View v) {
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
                    SharedPreferences sharedPreferences = mActivity.getSharedPreferences("xlmmCookiesAxiba", Context.MODE_PRIVATE);
                    String cookies = sharedPreferences.getString("cookiesString", "");
                    String actLink = fansUrl;
                    String domain = sharedPreferences.getString("cookiesDomain", "");
                    String sessionId = sharedPreferences.getString("Cookie", "");
                    EventBus.getDefault().postSticky(new WebViewEvent(cookies, domain, actLink, -1, sessionId));
                    startActivity(new Intent(mActivity, MMFans1Activity.class));
                }
                break;
            case R.id.ll_personal:
                startActivity(new Intent(mActivity, PersonalCarryRankActivity.class));
                break;
            case R.id.ll_team:
                if (teamUrl != null && !"".equals(teamUrl)) {
                    Bundle teamBundle = new Bundle();
                    teamBundle.putString("id", id + "");
                    teamBundle.putString("url", teamUrl);
                    Intent teamIntent = new Intent(mActivity, MMTeamActivity.class);
                    teamIntent.putExtras(teamBundle);
                    startActivity(teamIntent);
                }
                break;
        }
    }
}
