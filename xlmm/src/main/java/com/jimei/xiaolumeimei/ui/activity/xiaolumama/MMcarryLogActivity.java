package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import butterknife.Bind;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.utils.Utils;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by itxuye(www.itxuye.com) on 2016/03/11.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class MMcarryLogActivity extends BaseSwipeBackCompatActivity
    implements AppBarLayout.OnOffsetChangedListener {
  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.tab_layout) TabLayout tabLayout;
  @Bind(R.id.collapse_toolbar) CollapsingToolbarLayout collapseToolbar;
  @Bind(R.id.appBarLayout) AppBarLayout appBarLayout;
  @Bind(R.id.view_pager) ViewPager viewPager;

  @Override protected void setListener() {

  }

  @Override protected void initData() {

  }

  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.activity_mmcarrylog;
  }

  @Override protected void initViews() {

    collapseToolbar.setTitleEnabled(false);
    appBarLayout.addOnOffsetChangedListener(this);

    int toolbar_hight = Utils.getToolbarHeight(this) * 2;
    CollapsingToolbarLayout.LayoutParams params =
        (CollapsingToolbarLayout.LayoutParams) toolbar.getLayoutParams();
    params.height = toolbar_hight;
    toolbar.setLayoutParams(params);

    setUpCommonBackTooblBar(toolbar, "收益记录");

    List<Fragment> fragments = new ArrayList<>();
    //fragments.add()

    List<String> titles = new ArrayList<>();
    titles.add("全部");
    titles.add("佣金");
    titles.add("返现");
    titles.add("奖金");

    tabLayout.addTab(tabLayout.newTab().setText(titles.get(0)));
    tabLayout.addTab(tabLayout.newTab().setText(titles.get(1)));
    tabLayout.addTab(tabLayout.newTab().setText(titles.get(2)));

    MainTabAdapter mAdapter =
        new MainTabAdapter(getSupportFragmentManager(), fragments, titles);
    viewPager.setAdapter(mAdapter);
    tabLayout.setupWithViewPager(viewPager);
    //mTabLayout.setTabsFromPagerAdapter(mAdapter);
    tabLayout.setTabMode(TabLayout.MODE_FIXED);

  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }

  @Override public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
    float alpha =
        (float) Math.abs(verticalOffset) / (float) appBarLayout.getTotalScrollRange()
            * 1.0f;
    toolbar.setAlpha(alpha);
  }

  protected void setUpCommonBackTooblBar(Toolbar toolbar, String title) {
    toolbar.setTitle(title);
    setSupportActionBar(toolbar);
    toobarAsBackButton(toolbar);
  }

  public void toobarAsBackButton(Toolbar toolbar) {
    toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowHomeEnabled(true);
    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        finish();
      }
    });
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

    @Override public Fragment getItem(int position) {
      return listFragment.get(position);
    }

    @Override public int getCount() {
      return listFragment.size();
    }

    @Override public CharSequence getPageTitle(int position) {
      return listTitle.get(position);
    }
  }
}
