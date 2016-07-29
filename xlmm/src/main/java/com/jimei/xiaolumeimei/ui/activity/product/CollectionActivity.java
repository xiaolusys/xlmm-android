package com.jimei.xiaolumeimei.ui.activity.product;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.CollectionTabAdapter;
import com.jimei.xiaolumeimei.base.BaseAppCompatActivity;
import com.jimei.xiaolumeimei.ui.fragment.v1.CollectionFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class CollectionActivity extends BaseAppCompatActivity {
    @Bind(R.id.tab_layout)
    TabLayout mTabLayout;
    @Bind(R.id.view_pager)
    ViewPager mViewPager;

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
        List<CollectionFragment> fragments = new ArrayList<>();
        fragments.add(CollectionFragment.newInstance("热销商品",1));
        fragments.add(CollectionFragment.newInstance("下架商品",0));
        CollectionTabAdapter mAdapter = new CollectionTabAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(2);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_collection;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }
}
