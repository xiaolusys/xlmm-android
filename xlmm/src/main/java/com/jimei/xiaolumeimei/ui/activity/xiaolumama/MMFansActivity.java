package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.BaseTabAdapter;
import com.jimei.xiaolumeimei.base.BaseFragment;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.ui.fragment.xiaolumama.MMFansFragment;
import com.jimei.xiaolumeimei.ui.fragment.xiaolumama.MMFansWebFragment;
import com.jimei.xiaolumeimei.ui.fragment.xiaolumama.MMPotentialFansFragment;

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
        fragments.add(MMFansWebFragment.newInstance("关于粉丝"));
        BaseTabAdapter mAdapter = new BaseTabAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(mAdapter);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

    @Override
    public boolean isNeedShow() {
        return false;
    }
}
