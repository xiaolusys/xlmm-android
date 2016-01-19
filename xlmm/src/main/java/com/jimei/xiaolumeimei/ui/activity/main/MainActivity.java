package com.jimei.xiaolumeimei.ui.activity.main;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import butterknife.Bind;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseActivity;
import com.jimei.xiaolumeimei.ui.activity.trade.AllOrdersActivity;
import com.jimei.xiaolumeimei.ui.activity.trade.AllRefundsActivity;
import com.jimei.xiaolumeimei.ui.activity.trade.CartActivity;
import com.jimei.xiaolumeimei.ui.activity.trade.WaitPayOrdersActivity;
import com.jimei.xiaolumeimei.ui.activity.trade.WaitSendOrdersActivity;
import com.jimei.xiaolumeimei.ui.activity.user.LoginActivity;
import com.jimei.xiaolumeimei.ui.activity.user.SettingActivity;
import com.jimei.xiaolumeimei.ui.fragment.ChildListFragment;
import com.jimei.xiaolumeimei.ui.fragment.LadyListFragment;
import com.jimei.xiaolumeimei.ui.fragment.PreviousFragment;
import com.jimei.xiaolumeimei.ui.fragment.TodayFragment;
import com.jimei.xiaolumeimei.utils.LoginUtils;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity
    implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

  @Bind(R.id.tab_layout) TabLayout mTabLayout;
  @Bind(R.id.view_pager) ViewPager mViewPager;
  @Bind(R.id.tool_bar) Toolbar toolbar;
  @Bind(R.id.fab) FloatingActionButton carts;
  List<Fragment> fragments;
  List<String> titles;

  @Override protected int provideContentViewId() {
    return R.layout.activity_main;
  }

  @Override protected void setListener() {
    carts.setOnClickListener(this);
  }

  @Override protected void initData() {
    initFragment();

    initTitles();

    initTabLayout();
  }

  private void initTabLayout() {
    mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(0)));
    mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(1)));
    mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(2)));
    mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(3)));

    MainTabAdapter mAdapter =
        new MainTabAdapter(getSupportFragmentManager(), fragments, titles);
    mViewPager.setAdapter(mAdapter);
    mTabLayout.setupWithViewPager(mViewPager);
    //mTabLayout.setTabsFromPagerAdapter(mAdapter);
    mTabLayout.setTabMode(TabLayout.MODE_FIXED);
  }

  private void initTitles() {
    titles = new ArrayList<>();
    titles.add("今日上新");
    titles.add("昨日特卖");
    titles.add("萌娃专区");
    titles.add("时尚女装");
  }

  private void initFragment() {
    fragments = new ArrayList<>();
    fragments.add(new TodayFragment());
    fragments.add(new PreviousFragment());
    fragments.add(new ChildListFragment());
    fragments.add(new LadyListFragment());
  }

  @Override protected void initView() {
    toolbar.setTitle("小鹿美美--外贸原单 天天特价");
    setSupportActionBar(toolbar);

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle =
        new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
            R.string.navigation_drawer_close);
    drawer.setDrawerListener(toggle);
    toggle.syncState();

    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);
  }

  @Override public boolean onNavigationItemSelected(MenuItem item) {

    int id = item.getItemId();

    String[] loginInfo = LoginUtils.getLoginInfo(getApplicationContext());
    boolean b = Boolean.parseBoolean(loginInfo[2]);

    if (!b) {
            /*未登录进入登录界面*/
      startActivity(new Intent(MainActivity.this, LoginActivity.class));
    } else {
      if (id == R.id.nav_tobepaid) {
        startActivity(new Intent(MainActivity.this, WaitPayOrdersActivity.class));
      } else if (id == R.id.nav_tobereceived) {
        startActivity(new Intent(MainActivity.this, WaitSendOrdersActivity.class));
      } else if (id == R.id.nav_returned) {
        startActivity(new Intent(MainActivity.this, AllRefundsActivity.class));
      } else if (id == R.id.nav_orders) {
        startActivity(new Intent(MainActivity.this, AllOrdersActivity.class));
      } else if (id == R.id.nav_setting) {
        startActivity(new Intent(MainActivity.this, SettingActivity.class));
      } else if (id == R.id.nav_complain) {

        //Log.d(TAG, "start complain activity ");
      } else if (id == R.id.nav_login) {

        //Log.d(TAG, "start login activity ");
      }
    }

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;
  }

  @Override public void onBackPressed() {
    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }

  @Override public void onClick(View v) {
    startActivity(new Intent(MainActivity.this, CartActivity.class));
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
