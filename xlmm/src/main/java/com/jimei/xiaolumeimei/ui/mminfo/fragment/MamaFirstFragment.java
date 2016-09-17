package com.jimei.xiaolumeimei.ui.mminfo.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseLazyFragment;
import com.jimei.xiaolumeimei.databinding.FragmentMamaFirstBinding;
import com.jimei.xiaolumeimei.entities.PersonalCarryRankBean;
import com.jimei.xiaolumeimei.model.MamaInfoModel;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.GoodWeekActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MMNinePicActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MMShareCodeWebViewActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MMStoreWebViewActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MamaChooseActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.PersonalCarryRankActivity;
import com.jimei.xiaolumeimei.ui.mminfo.MMInfoModel;
import com.jimei.xiaolumeimei.utils.JumpUtils;
import com.jimei.xiaolumeimei.utils.RxUtils;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;

import retrofit2.Response;
import rx.schedulers.Schedulers;

public class MamaFirstFragment extends BaseLazyFragment<FragmentMamaFirstBinding> implements View.OnClickListener {
    private static final String TITLE = "title";
    private static final String ID = "id";

    private String id;
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
            id = getArguments().getString(ID);
        }
    }

    @Override
    protected void initData() {
        addSubscription(MMInfoModel.getInstance()
                .getMamaUrl()
                .retryWhen(new RxUtils.RetryWhenNoInternet(100, 2000))
                .subscribe(mamaUrl -> {
                    shareLink = mamaUrl.getResults().get(0).getExtra().getInvite();
                }, Throwable::printStackTrace));
        addSubscription(MMInfoModel.getInstance()
                .getShareShopping()
                .retryWhen(new RxUtils.RetryWhenNoInternet(100, 2000))
                .subscribe(mmShoppingBean -> {
                    shopLink = mmShoppingBean.getShopInfo().getPreviewShopLink();
                }, Throwable::printStackTrace));
        addSubscription(MamaInfoModel.getInstance()
                .getPersonalSelfCarryRankBean()
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<Response<PersonalCarryRankBean>>() {
                    @Override
                    public void onNext(Response<PersonalCarryRankBean> personalCarryRankBeanResponse) {
                        if (null != personalCarryRankBeanResponse) {
                            if (personalCarryRankBeanResponse.isSuccessful()) {
                                PersonalCarryRankBean bean = personalCarryRankBeanResponse.body();
                                b.tvIncome.setText(bean.getTotal() / 100.00 + "");
                                b.tvRank.setText(bean.getRank() + "");
                            }
                        }
                    }
                }));
        setListener();
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
