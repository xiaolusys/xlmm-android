package com.jimei.xiaolumeimei.ui.fragment.main;


import android.support.design.widget.TabLayout;
import android.view.View;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.BaseTabAdapter;
import com.jimei.xiaolumeimei.base.BaseBindingFragment;
import com.jimei.xiaolumeimei.base.BaseFragment;
import com.jimei.xiaolumeimei.databinding.FragmentCollectTabBinding;
import com.jimei.xiaolumeimei.ui.fragment.product.CollectionFragment;

import java.util.ArrayList;
import java.util.List;

public class CollectTabFragment extends BaseBindingFragment<FragmentCollectTabBinding> {


    public static CollectTabFragment newInstance() {
        return new CollectTabFragment();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_collect_tab;
    }

    @Override
    public View getLoadingView() {
        return b.viewPager;
    }

    @Override
    public void initData() {
        hideIndeterminateProgressDialog();
    }

    @Override
    protected void initViews() {
        List<BaseFragment> fragments = new ArrayList<>();
        fragments.add(CollectionFragment.newInstance("热销商品", "on"));
        fragments.add(CollectionFragment.newInstance("未上架商品", "off"));
        BaseTabAdapter mAdapter = new BaseTabAdapter(getChildFragmentManager(), fragments);
        b.viewPager.setAdapter(mAdapter);
        b.viewPager.setOffscreenPageLimit(2);
        b.tabLayout.setupWithViewPager(b.viewPager);
        b.tabLayout.setTabMode(TabLayout.MODE_FIXED);
        hideIndeterminateProgressDialog();
    }
}
