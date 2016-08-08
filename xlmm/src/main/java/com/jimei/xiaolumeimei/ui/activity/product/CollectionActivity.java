package com.jimei.xiaolumeimei.ui.activity.product;

import android.os.Bundle;
import android.support.design.widget.TabLayout;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.CollectionTabAdapter;
import com.jimei.xiaolumeimei.base.BaseMVVMActivity;
import com.jimei.xiaolumeimei.databinding.ActivityCollectionBinding;
import com.jimei.xiaolumeimei.ui.fragment.v1.CollectionFragment;

import java.util.ArrayList;
import java.util.List;

public class CollectionActivity extends BaseMVVMActivity<ActivityCollectionBinding> {
    @Override
    protected void initView() {
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void initData() {
        showIndeterminateProgressDialog(false);
        List<CollectionFragment> fragments = new ArrayList<>();
        fragments.add(CollectionFragment.newInstance("热销商品", "on"));
        fragments.add(CollectionFragment.newInstance("未上架商品", "off"));
        CollectionTabAdapter mAdapter = new CollectionTabAdapter(getSupportFragmentManager(), fragments);
        b.viewPager.setAdapter(mAdapter);
        b.viewPager.setOffscreenPageLimit(2);
        b.tabLayout.setupWithViewPager(b.viewPager);
        b.tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_collection;
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
