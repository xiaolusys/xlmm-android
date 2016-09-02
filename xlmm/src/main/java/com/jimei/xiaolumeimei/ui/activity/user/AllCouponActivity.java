package com.jimei.xiaolumeimei.ui.activity.user;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.entities.CouponEntity;
import com.jimei.xiaolumeimei.model.UserModel;
import com.jimei.xiaolumeimei.ui.fragment.coupon.CouponFragment;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.schedulers.Schedulers;

public class AllCouponActivity extends BaseSwipeBackCompatActivity {
    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    ArrayList<CouponFragment> fragments = new ArrayList<>();
    List<String> titles = new ArrayList<>();

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_all_coupon;
    }

    @Override
    protected void initViews() {
        showIndeterminateProgressDialog(false);
        UserModel.getInstance()
                .getCouponList(0)
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<ArrayList<CouponEntity>>() {
                    @Override
                    public void onNext(ArrayList<CouponEntity> couponEntities) {
                        titles.add("未使用(" + couponEntities.size() + ")");
                        fragments.add(CouponFragment.newInstance(XlmmConst.UNUSED_COUPON,couponEntities));
                        UserModel.getInstance()
                                .getCouponList(3)
                                .subscribeOn(Schedulers.io())
                                .subscribe(new ServiceResponse<ArrayList<CouponEntity>>() {
                                    @Override
                                    public void onNext(ArrayList<CouponEntity> couponEntities) {
                                        titles.add("已过期(" + couponEntities.size() + ")");
                                        fragments.add(CouponFragment.newInstance(XlmmConst.PAST_COUPON,couponEntities));
                                        UserModel.getInstance()
                                                .getCouponList(1)
                                                .subscribeOn(Schedulers.io())
                                                .subscribe(new ServiceResponse<ArrayList<CouponEntity>>() {
                                                    @Override
                                                    public void onNext(ArrayList<CouponEntity> couponEntities) {
                                                        titles.add("已使用(" + couponEntities.size() + ")");
                                                        fragments.add(CouponFragment.newInstance(XlmmConst.USED_COUPON,couponEntities));
                                                        MainTabAdapter mAdapter = new MainTabAdapter(getSupportFragmentManager());
                                                        viewPager.setAdapter(mAdapter);
                                                        viewPager.setOffscreenPageLimit(3);
                                                        tabLayout.setupWithViewPager(viewPager);
                                                        tabLayout.setTabMode(TabLayout.MODE_FIXED);
                                                        hideIndeterminateProgressDialog();
                                                    }
                                                });
                                    }
                                });
                    }
                });

    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    public class MainTabAdapter extends FragmentPagerAdapter {

        public MainTabAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
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
