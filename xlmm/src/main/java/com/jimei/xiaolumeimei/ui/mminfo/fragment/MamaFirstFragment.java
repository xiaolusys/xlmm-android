package com.jimei.xiaolumeimei.ui.mminfo.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.ActivityListAdapter;
import com.jimei.xiaolumeimei.base.BaseLazyFragment;
import com.jimei.xiaolumeimei.databinding.FragmentMamaFirstBinding;
import com.jimei.xiaolumeimei.entities.MamaFortune;
import com.jimei.xiaolumeimei.entities.MamaUrl;
import com.jimei.xiaolumeimei.entities.PortalBean;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.GoodWeekActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MMNinePicActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MMShareCodeWebViewActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MMStoreWebViewActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MamaChooseActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.PersonalCarryRankActivity;
import com.jimei.xiaolumeimei.ui.mminfo.MMInfoModel;
import com.jimei.xiaolumeimei.utils.JumpUtils;
import com.jimei.xiaolumeimei.utils.RxUtils;

import java.util.List;

public class MamaFirstFragment extends BaseLazyFragment<FragmentMamaFirstBinding> implements View.OnClickListener {
    private static final String TITLE = "title";
    private static final String ID = "id";

    private int id;
    private String shareLink;
    private String shopLink;

    public static MamaFirstFragment newInstance(String title, int id) {
        MamaFirstFragment fragment = new MamaFirstFragment();
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
        addSubscription(MMInfoModel.getInstance()
                .getMamaUrl()
                .retryWhen(new RxUtils.RetryWhenNoInternet(100, 2000))
                .subscribe(this::initUrl, Throwable::printStackTrace));
        addSubscription(MMInfoModel.getInstance()
                .getShareShopping()
                .retryWhen(new RxUtils.RetryWhenNoInternet(100, 2000))
                .subscribe(mmShoppingBean -> {
                    shopLink = mmShoppingBean.getShopInfo().getPreviewShopLink();
                }, Throwable::printStackTrace));
        addSubscription(MMInfoModel.getInstance()
                .getMamaFortune()
                .retryWhen(new RxUtils.RetryWhenNoInternet(100, 2000))
                .subscribe(this::initFortune, Throwable::printStackTrace));
        setListener();
    }

    private void initUrl(MamaUrl mamaUrl) {
        MamaUrl.ResultsBean resultsBean = mamaUrl.getResults().get(0);
        shareLink = resultsBean.getExtra().getInvite();
        List<PortalBean.ActivitysBean> activities = resultsBean.getMama_activities();
        if (activities != null && activities.size() > 0) {
            ActivityListAdapter adapter = new ActivityListAdapter(mActivity);
            adapter.updateWithClear(activities);
            b.lv.setAdapter(adapter);
        }
    }

    private void initFortune(MamaFortune mamaFortune) {
        MamaFortune.MamaFortuneBean.ExtraFiguresBean figures = mamaFortune.getMamaFortune().getExtra_figures();
        b.tvIncome.setText(figures.getWeek_duration_total() + "");
        b.tvRank.setText(figures.getWeek_duration_rank() + "");
        b.tvWeekTask.setText("本周完成任务" + (figures.getTask_percentage() * 100) + "%");
    }

    private void setListener() {
        b.llRank.setOnClickListener(this);
        b.llShare.setOnClickListener(this);
        b.llPush.setOnClickListener(this);
        b.llChoose.setOnClickListener(this);
        b.llShop.setOnClickListener(this);
        b.tvGoodWeek.setOnClickListener(this);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_mama_first;
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
    public void onResume() {
        super.onResume();
        b.scrollView.scrollTo(0, 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_rank:
                startActivity(new Intent(mActivity, PersonalCarryRankActivity.class));
                break;
            case R.id.ll_share:
                JumpUtils.jumpToWebViewWithCookies(mActivity, shopLink, -1,
                        MMStoreWebViewActivity.class);
                break;
            case R.id.ll_push:
                startActivity(new Intent(mActivity, MMNinePicActivity.class));
                break;
            case R.id.ll_choose:
                startActivity(new Intent(mActivity, MamaChooseActivity.class));
                break;
            case R.id.ll_shop:
                JumpUtils.jumpToWebViewWithCookies(mActivity, shareLink, 26,
                        MMShareCodeWebViewActivity.class, "");
                break;
            case R.id.tv_good_week:
                startActivity(new Intent(mActivity, GoodWeekActivity.class));
                break;
        }
    }
}
