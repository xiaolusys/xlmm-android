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
import com.jimei.xiaolumeimei.ui.fragment.AllOrdersFragment;
import com.jimei.xiaolumeimei.ui.fragment.WaitPayOrdersFragment;
import com.jimei.xiaolumeimei.ui.fragment.WaitSendOrdersFragment;
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
    List<Fragment> fragments;
    List<String> titles;

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

        initTitles();

        initTabLayout();

        swith_fragment();

    }

    private void initTabLayout() {
        MainTabAdapter mAdapter =
                new MainTabAdapter(getSupportFragmentManager(), fragments, titles);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(3);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

    private void initTitles() {
        titles = new ArrayList<>();
        titles.add("所有订单");
        titles.add("待付款");
        titles.add("待收货");
    }

    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(AllOrdersFragment.newInstance("所有订单"));
        fragments.add(WaitPayOrdersFragment.newInstance("待付款"));
        fragments.add(WaitSendOrdersFragment.newInstance("待收货"));

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
        int tabid = 0;
        if (getIntent().getExtras() != null) {
            tabid = getIntent().getExtras().getInt("fragment");
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
        private List<Fragment> listFragment;
        private List<String> listTitle;

        public MainTabAdapter(FragmentManager fm, List<Fragment> listFragment,
                              List<String> listTitle) {
            super(fm);
            this.listFragment = listFragment;
            this.listTitle = listTitle;
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
            return listTitle.get(position);
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
