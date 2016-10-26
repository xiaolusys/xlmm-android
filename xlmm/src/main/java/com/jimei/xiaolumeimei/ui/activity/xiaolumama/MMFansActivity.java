package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseFragment;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.base.BaseWebViewFragment;
import com.jimei.xiaolumeimei.ui.fragment.v2.MMFansFragment;
import com.jimei.xiaolumeimei.ui.fragment.v2.MMPotentialFansFragment;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by itxuye on 2016/6/23.
 */
public class MMFansActivity extends BaseSwipeBackCompatActivity{

    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    List<BaseFragment> fragments = new ArrayList<>();

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_fans;
    }

    @Override
    protected void initViews() {
        fragments.add(MMFansFragment.newInstance("我的粉丝"));
        fragments.add(MMPotentialFansFragment.newInstance("潜在粉丝"));
        fragments.add(BaseWebViewFragment.newInstance("关于粉丝"));


        List<String> titles = new ArrayList<>();
        titles.add("我的粉丝");
        titles.add("潜在粉丝");
        titles.add("关于粉丝");

        MainTabAdapter mAdapter = new MainTabAdapter(getSupportFragmentManager(), titles);
        viewPager.setAdapter(mAdapter);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
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
