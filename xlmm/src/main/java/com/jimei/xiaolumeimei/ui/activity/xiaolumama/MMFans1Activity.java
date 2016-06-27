package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import butterknife.Bind;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseFragment;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.base.BaseWebViewFragment;
import com.jimei.xiaolumeimei.ui.fragment.v2.MMFansFragment;
import com.jimei.xiaolumeimei.ui.fragment.v2.MMPotentialFansFragment;
import com.umeng.analytics.MobclickAgent;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by itxuye on 2016/6/23.
 */
public class MMFans1Activity extends BaseSwipeBackCompatActivity
    implements ViewPager.OnPageChangeListener {

  @Bind(R.id.tab_layout) TabLayout tabLayout;
  @Bind(R.id.view_pager) ViewPager viewPager;
  List<BaseFragment> fragments = new ArrayList<>();
  private TabLayout.Tab[] tabs;
  @Override protected void setListener() {
    viewPager.setOnPageChangeListener(this);
  }

  @Override protected void initData() {

  }

  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.activity_fans;
  }

  @Override protected void initViews() {
    fragments.add(MMFansFragment.newInstance("我的粉丝"));
    fragments.add(MMPotentialFansFragment.newInstance("潜在粉丝"));
    fragments.add(BaseWebViewFragment.newInstance("关于粉丝"));


    List<String> titles = new ArrayList<>();
    titles.add("我的粉丝");
    titles.add("潜在粉丝");
    titles.add("关于粉丝");


    tabs = new TabLayout.Tab[3];
    tabs[0] = tabLayout.newTab().setText(titles.get(0));
    tabs[1] = tabLayout.newTab().setText(titles.get(1));
    tabs[2] = tabLayout.newTab().setText(titles.get(2));

    tabLayout.addTab(tabs[0]);
    tabLayout.addTab(tabs[1]);
    tabLayout.addTab(tabs[2]);

    MainTabAdapter mAdapter = new MainTabAdapter(getSupportFragmentManager(), titles);
    viewPager.setAdapter(mAdapter);
    viewPager.setOffscreenPageLimit(3);
    tabLayout.setupWithViewPager(viewPager);
    tabLayout.setTabMode(TabLayout.MODE_FIXED);
  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
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

  @Override
  public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

  }

  @Override public void onPageSelected(int position) {

  }

  @Override public void onPageScrollStateChanged(int state) {

  }

  class MainTabAdapter extends FragmentPagerAdapter {
    private List<String> listTitle;

    public MainTabAdapter(FragmentManager fm, List<String> listTitle) {
      super(fm);
      this.listTitle = listTitle;
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
      return listTitle.get(position);
    }
  }
}
