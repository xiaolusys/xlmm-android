package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.support.v4.view.ViewPager;
import android.text.TextUtils;

import com.jimei.library.utils.ViewUtils;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.RankFragmentPagerAdapter;
import com.jimei.xiaolumeimei.base.BaseMVVMActivity;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.databinding.ActivityTeamBinding;
import com.jimei.xiaolumeimei.entities.PersonalCarryRankBean;
import com.jimei.xiaolumeimei.model.MamaInfoModel;
import com.jimei.xiaolumeimei.ui.fragment.xiaolumama.RankFragment;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;

import retrofit2.Response;

/**
 * Created by itxuye on 2016/7/28.
 */
public class MMTeamCarryRankActivity extends BaseMVVMActivity<ActivityTeamBinding> {

    private ArrayList<RankFragment> fragments;

    @Override
    protected void initViews() {
        fragments = new ArrayList<>();
        fragments.add(RankFragment.newInstance(XlmmConst.TYPE_TEAM_RANK, "总排行"));
        fragments.add(RankFragment.newInstance(XlmmConst.TYPE_TEAM_WEEK_RANK, "周排行"));

        RankFragmentPagerAdapter adapter = new RankFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        b.viewPager.setAdapter(adapter);
        b.viewPager.setOffscreenPageLimit(2);
        b.tabLayout.setupWithViewPager(b.viewPager);
        b.scrollableLayout.getHelper().setCurrentScrollableContainer(fragments.get(0));
    }

    @Override
    protected void setListener() {
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
        MamaInfoModel.getInstance()
                .getTeamSelfRank()
                .subscribe(new ServiceResponse<Response<PersonalCarryRankBean>>() {
                    @Override
                    public void onNext(Response<PersonalCarryRankBean> personalCarryRankBeanResponse) {
                        if (null != personalCarryRankBeanResponse) {
                            if (personalCarryRankBeanResponse.isSuccessful()) {
                                PersonalCarryRankBean personalCarryRankBean = personalCarryRankBeanResponse.body();
                                b.setPersonalInfo(personalCarryRankBean);
                                if (personalCarryRankBean.getRank() == 0) {
                                    b.tvRank.setText("");
                                } else {
                                    b.tvRank.setText("第" + personalCarryRankBean.getRank() + "名");
                                }
                                b.tvCarry.setText("团队收益" + personalCarryRankBean.getTotal() / 100.00 + "元");
                                try {
                                    if (!TextUtils.isEmpty(personalCarryRankBean.getThumbnail())) {

                                        ViewUtils.loadImgToImgViewWithTransformCircle(MMTeamCarryRankActivity.this,
                                                b.imgUser, personalCarryRankBean.getThumbnail());
                                    } else {
                                        b.imgUser.setImageResource(R.drawable.img_diamond);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                });
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_team;
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
