package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import butterknife.Bind;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseFragment;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.ui.fragment.v2.CarryLogAllFragment;
import com.jimei.xiaolumeimei.ui.fragment.v2.CarryLogBounsFragment;
import com.jimei.xiaolumeimei.ui.fragment.v2.CarryLogCashbackFragment;
import com.jimei.xiaolumeimei.ui.fragment.v2.CarryLogCommissionFragment;
import com.jimei.xiaolumeimei.widget.scrolllayout.ScrollableLayout;
import com.umeng.analytics.MobclickAgent;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by itxuye(www.itxuye.com) on 2016/03/11.
 * <p>
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class MMcarryLogActivity extends BaseSwipeBackCompatActivity
    implements ViewPager.OnPageChangeListener {
  @Bind(R.id.tab_layout) TabLayout tabLayout;
  @Bind(R.id.view_pager) ViewPager viewPager;
  @Bind(R.id.tv_leiji) TextView tvLeiji;
  @Bind(R.id.tv_num) TextView tvNum;
  @Bind(R.id.tv_his) TextView tvHis;
  @Bind(R.id.scrollable_layout) ScrollableLayout scrollableLayout;
  List<BaseFragment> fragments = new ArrayList<>();
  private String carrylogMoney;
  private TabLayout.Tab[] tabs;
  private String hisConfirmedCashOut;

  @Override protected void setListener() {
  }

  @Override protected void initData() {
    viewPager.setOnPageChangeListener(this);
  }

  @Override protected void getBundleExtras(Bundle extras) {
    carrylogMoney = extras.getString("carrylogMoney");
    hisConfirmedCashOut = extras.getString("hisConfirmedCashOut");
  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.activity_mmcarrylog;
  }

  @Override protected void onResume() {
    super.onResume();
    MobclickAgent.onPageStart(this.getClass().getSimpleName());
    MobclickAgent.onResume(this);
  }

  @Override protected void onPause() {
    super.onPause();
    MobclickAgent.onPageEnd(this.getClass().getSimpleName());
    MobclickAgent.onPause(this);
  }

  @Override protected void initViews() {

    tvLeiji.setText("累计收益");
    tvNum.setText(carrylogMoney);
    tvHis.setText("2016.3.24号系统升级之前的收益" + hisConfirmedCashOut);
    fragments.add(CarryLogAllFragment.newInstance("全部"));
    fragments.add(CarryLogCommissionFragment.newInstance("佣金"));
    fragments.add(CarryLogCashbackFragment.newInstance("返现"));
    fragments.add(CarryLogBounsFragment.newInstance("奖金"));

    List<String> titles = new ArrayList<>();
    titles.add("全部");
    titles.add("佣金");
    titles.add("返现");
    titles.add("奖金");

    tabs = new TabLayout.Tab[4];
    tabs[0] = tabLayout.newTab().setText(titles.get(0));
    tabs[1] = tabLayout.newTab().setText(titles.get(1));
    tabs[2] = tabLayout.newTab().setText(titles.get(2));
    tabs[3] = tabLayout.newTab().setText(titles.get(3));

    tabLayout.addTab(tabs[0]);
    tabLayout.addTab(tabs[1]);
    tabLayout.addTab(tabs[2]);
    tabLayout.addTab(tabs[3]);

    MainTabAdapter mAdapter = new MainTabAdapter(getSupportFragmentManager(), titles);
    viewPager.setAdapter(mAdapter);
    viewPager.setOffscreenPageLimit(3);
    tabLayout.setupWithViewPager(viewPager);
    tabLayout.setTabMode(TabLayout.MODE_FIXED);
    scrollableLayout.getHelper().setCurrentScrollableContainer(fragments.get(0));
  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }

  @Override
  public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
  }

  @Override public void onPageSelected(int position) {
    scrollableLayout.getHelper().setCurrentScrollableContainer(fragments.get(position));
  }

  @Override public void onPageScrollStateChanged(int state) {
  }

  class MainTabAdapter extends FragmentPagerAdapter {
    private List<String> listTitle;

    public MainTabAdapter(FragmentManager fm, List<String> listTitle) {
      super(fm);
      this.listTitle = listTitle;
    }

    @Override public Fragment getItem(int position) {
      return fragments.get(position);
    }

    @Override public int getCount() {
      return fragments.size();
    }

    @Override public CharSequence getPageTitle(int position) {
      return listTitle.get(position);
    }
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_personal:
        readyGo(PersonalCarryRankActivity.class);
        break;
      default:
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_personal, menu);
    return super.onCreateOptionsMenu(menu);
  }
}
