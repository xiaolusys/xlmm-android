package com.jimei.xiaolumeimei.ui.activity.trade;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import butterknife.Bind;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.ui.fragment.v2.OrderListFragment;
import com.jude.utils.JUtils;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

public class AllOrdersActivity extends BaseSwipeBackCompatActivity
        implements View.OnClickListener {
    String TAG = "AllOrdersActivity";
    @Bind(R.id.tab_layout)
    TabLayout mTabLayout;
    @Bind(R.id.view_pager)
    ViewPager mViewPager;
    List<OrderListFragment> fragments;

    @Override
    protected void setListener() {
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_allorders;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData() {
        initFragment();
        initTabLayout();
        swith_fragment();
    }

    private void initTabLayout() {
        MainTabAdapter mAdapter = new MainTabAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(3);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
    }


    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(OrderListFragment.newInstance(XlmmConst.ALL_ORDER, "所有订单"));
        fragments.add(OrderListFragment.newInstance(XlmmConst.WAIT_PAY, "待付款"));
        fragments.add(OrderListFragment.newInstance(XlmmConst.WAIT_SEND, "待收货"));
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
    public void onClick(View v) {

    }

    public void swith_fragment() {
        int tabid;
        if (getIntent().getExtras() != null) {
            tabid = getIntent().getExtras().getInt("fragment",0);
            JUtils.Log(TAG, "jump to fragment:" + tabid);
            if ((tabid >= 1) && (tabid <= 3)) {
                try {
                    mTabLayout.setScrollPosition(tabid - 1, 0, true);
                    mViewPager.setCurrentItem(tabid - 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class MainTabAdapter extends FragmentPagerAdapter {
        private List<OrderListFragment> listFragment;

        public MainTabAdapter(FragmentManager fm, List<OrderListFragment> listFragment) {
            super(fm);
            this.listFragment = listFragment;
        }

        @Override
        public Fragment getItem(int position) {
            return listFragment.get(position);
        }

        @Override
        public int getCount() {
            return listFragment.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return listFragment.get(position).getTitle();
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
