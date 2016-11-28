package com.jimei.xiaolumeimei.ui.activity.product;

import android.support.design.widget.TabLayout;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.CollectionTabAdapter;
import com.jimei.xiaolumeimei.base.BaseMVVMActivity;
import com.jimei.xiaolumeimei.databinding.ActivityCollectionBinding;
import com.jimei.xiaolumeimei.ui.fragment.product.CollectionFragment;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

public class CollectionActivity extends BaseMVVMActivity<ActivityCollectionBinding> {
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
    protected int getContentViewLayoutID() {
        return R.layout.activity_collection;
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

}
