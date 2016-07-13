package com.jimei.xiaolumeimei.ui.activity.user;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseFragment;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.ui.fragment.coupon.SelectCouponFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class SelectCouponActivity extends BaseSwipeBackCompatActivity {
    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    String selected_couponid;
    List<BaseFragment> fragments = new ArrayList<>();
    private double money;
    private double coupon_price;
    private String cart_ids;

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        selected_couponid = extras.getString("coupon_id", "");
        money = extras.getDouble("money");
        coupon_price = extras.getDouble("coupon_price", 0);
        cart_ids = extras.getString("cart_ids");
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_select;
    }

    @Override
    protected void initViews() {
        SelectCouponAdapter mAdapter = new SelectCouponAdapter(getSupportFragmentManager());
        fragments.add(SelectCouponFragment.newInstance(0, "可用优惠券", mAdapter, selected_couponid, money, coupon_price));
        fragments.add(SelectCouponFragment.newInstance(1, "不可用优惠券", mAdapter, "", money, coupon_price));
        viewPager.setAdapter(mAdapter);
        viewPager.setOffscreenPageLimit(2);
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

    public class SelectCouponAdapter extends FragmentPagerAdapter implements Serializable {
        FragmentManager fm;
        public SelectCouponAdapter(FragmentManager fm) {
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
