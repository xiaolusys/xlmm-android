package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.RankFragmentPagerAdapter;
import com.jimei.xiaolumeimei.base.BaseMVVMActivity;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.databinding.ActivityPersonalcarryrankBinding;
import com.jimei.xiaolumeimei.entities.PersonalCarryRankBean;
import com.jimei.xiaolumeimei.model.MamaInfoModel;
import com.jimei.xiaolumeimei.ui.fragment.v2.RankFragment;
import com.jimei.xiaolumeimei.utils.ViewUtils;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;

import retrofit2.Response;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye on 2016/7/27.
 */
public class PersonalCarryRankActivity extends BaseMVVMActivity<ActivityPersonalcarryrankBinding> {

    private ArrayList<RankFragment> fragments;

    @Override
    protected void initView() {
        fragments = new ArrayList<>();
        fragments.add(RankFragment.newInstance(XlmmConst.TYPE_PERSON_RANK, "总排行"));
        fragments.add(RankFragment.newInstance(XlmmConst.TYPE_PERSON_WEEK_RANK, "周排行"));

        RankFragmentPagerAdapter adapter = new RankFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        b.viewPager.setAdapter(adapter);
        b.viewPager.setOffscreenPageLimit(2);
        b.tabLayout.setupWithViewPager(b.viewPager);
        b.scrollableLayout.getHelper().setCurrentScrollableContainer(fragments.get(0));
    }

    @Override
    protected void initListener() {
        b.viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                b.scrollableLayout.getHelper().setCurrentScrollableContainer(fragments.get(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void initData() {
        showIndeterminateProgressDialog(false);
        MamaInfoModel.getInstance()
                .getPersonalSelfCarryRankBean()
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<Response<PersonalCarryRankBean>>() {
                    @Override
                    public void onNext(Response<PersonalCarryRankBean> personalCarryRankBeanResponse) {
                        if (null != personalCarryRankBeanResponse) {
                            if (personalCarryRankBeanResponse.isSuccessful()) {
                                PersonalCarryRankBean personalCarryRankBean = personalCarryRankBeanResponse.body();
                                fillDataToView(personalCarryRankBean);
                            }
                        }
                        hideIndeterminateProgressDialog();
                    }
                });
    }

    private void fillDataToView(PersonalCarryRankBean personalCarryRankBean) {
        b.setPersonalInfo(personalCarryRankBean);
        if (personalCarryRankBean.getRank() == 0) {
            b.tvRank.setText("");
        } else {
            b.tvRank.setText("第" + personalCarryRankBean.getRank() + "名");
        }
        b.tvCarry.setText("收益" + personalCarryRankBean.getTotal() / 100.00 + "元");
        try {
            if (personalCarryRankBean.getRankAdd() > 0) {
                b.tvRankChange.setText("比上周上升" + personalCarryRankBean.getRankAdd() + "名");
            } else {
                b.tvRankChange.setText(
                        "本周下降" + Math.abs(personalCarryRankBean.getRankAdd()) + "名");
            }

            if (!TextUtils.isEmpty(personalCarryRankBean.getThumbnail())) {

                ViewUtils.loadImgToImgViewWithTransformCircle(PersonalCarryRankActivity.this,
                        b.imgUser, personalCarryRankBean.getThumbnail());
            } else {
                b.imgUser.setImageResource(R.drawable.img_diamond);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_personalcarryrank;
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
