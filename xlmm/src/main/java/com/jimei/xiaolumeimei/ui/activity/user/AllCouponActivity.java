package com.jimei.xiaolumeimei.ui.activity.user;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.ui.fragment.coupon.CouponFragment;
import com.jimei.xiaolumeimei.widget.SerialFragmentManager;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.Bind;

public class AllCouponActivity extends BaseSwipeBackCompatActivity {
    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    ArrayList<CouponFragment> fragments = new ArrayList<>();

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
        MainTabAdapter mAdapter = new MainTabAdapter(getSupportFragmentManager());
        fragments.add(CouponFragment.newInstance(XlmmConst.UNUSED_COUPON, "未使用",mAdapter));
        fragments.add(CouponFragment.newInstance(XlmmConst.PAST_COUPON, "已过期",mAdapter));
        fragments.add(CouponFragment.newInstance(XlmmConst.USED_COUPON, "已使用",mAdapter));
        viewPager.setAdapter(mAdapter);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    public class MainTabAdapter extends SerialFragmentManager implements Serializable {
        FragmentManager fm;

        public MainTabAdapter(FragmentManager fm) {
            super(fm);
            this.fm = fm;
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
            return fragments.get(position).getTitle();
        }
    }
}
