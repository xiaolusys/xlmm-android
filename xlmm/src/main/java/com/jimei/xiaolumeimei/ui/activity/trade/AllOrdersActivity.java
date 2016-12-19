package com.jimei.xiaolumeimei.ui.activity.trade;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.jimei.library.utils.JUtils;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.BaseTabAdapter;
import com.jimei.xiaolumeimei.base.BaseFragment;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.ui.fragment.trade.OrderListFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class AllOrdersActivity extends BaseSwipeBackCompatActivity {
    String TAG = "AllOrdersActivity";
    @Bind(R.id.tab_layout)
    TabLayout mTabLayout;
    @Bind(R.id.view_pager)
    ViewPager mViewPager;
    List<BaseFragment> fragments;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_allorders;
    }

    @Override
    public boolean isNeedShow() {
        return false;
    }

    @Override
    protected void initData() {
        initFragment();
        initTabLayout();
        swith_fragment();
    }

    private void initTabLayout() {
        BaseTabAdapter mAdapter = new BaseTabAdapter(getSupportFragmentManager(), fragments);
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

    public void swith_fragment() {
        int tabid;
        if (getIntent().getExtras() != null) {
            tabid = getIntent().getExtras().getInt("fragment", 0);
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
}
