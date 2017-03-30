package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.jimei.library.widget.PageSelectedListener;
import com.jimei.library.widget.scrolllayout.ScrollableLayout;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.BaseTabAdapter;
import com.jimei.xiaolumeimei.base.BaseFragment;
import com.jimei.xiaolumeimei.base.BaseLazyFragment;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.ui.fragment.xiaolumama.CarryLogAllFragment;
import com.jimei.xiaolumeimei.ui.fragment.xiaolumama.CarryLogBounsFragment;
import com.jimei.xiaolumeimei.ui.fragment.xiaolumama.CarryLogCashbackFragment;
import com.jimei.xiaolumeimei.ui.fragment.xiaolumama.CarryLogCommissionFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by itxuye(www.itxuye.com) on 2016/03/11.
 * <p>
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class MMCarryLogActivity extends BaseSwipeBackCompatActivity {
    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    @Bind(R.id.tv_leiji)
    TextView tvLeiji;
    @Bind(R.id.tv_num)
    TextView tvNum;
    @Bind(R.id.tv_his)
    TextView tvHis;
    @Bind(R.id.scrollable_layout)
    ScrollableLayout scrollableLayout;
    List<BaseFragment> fragments = new ArrayList<>();
    private String carrylogMoney;
    private String hisConfirmedCashOut;

    @Override
    protected void initData() {
        viewPager.addOnPageChangeListener(new PageSelectedListener() {
            @Override
            public void onPageSelected(int position) {
                scrollableLayout.getHelper().setCurrentScrollableContainer((BaseLazyFragment) fragments.get(position));
            }
        });
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        carrylogMoney = extras.getString("carrylogMoney");
        hisConfirmedCashOut = extras.getString("hisConfirmedCashOut");
    }

    @Override
    public View getLoadingView() {
        return scrollableLayout;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_mmcarrylog;
    }

    @Override
    protected void initViews() {

        tvLeiji.setText("累计收益");
        tvNum.setText(carrylogMoney);
        tvHis.setText("2016.3.24号系统升级之前的收益" + hisConfirmedCashOut);
        fragments.add(CarryLogAllFragment.newInstance("全部"));
        fragments.add(CarryLogBounsFragment.newInstance("奖金"));
        fragments.add(CarryLogCommissionFragment.newInstance("佣金"));
        fragments.add(CarryLogCashbackFragment.newInstance("返现"));

        BaseTabAdapter mAdapter = new BaseTabAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(mAdapter);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        scrollableLayout.getHelper().setCurrentScrollableContainer((BaseLazyFragment) fragments.get(0));
    }
}
