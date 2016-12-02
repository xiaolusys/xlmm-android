package com.jimei.xiaolumeimei.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jimei.xiaolumeimei.ui.fragment.product.ProductListFragment;

import java.util.List;

/**
 * Created by wisdom on 16/12/1.
 */

public class MainFragmentAdapter extends FragmentPagerAdapter {

    private List<ProductListFragment> list;

    public MainFragmentAdapter(FragmentManager fm, List<ProductListFragment> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return getCount() > position ? list.get(position) : null;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position).getTitle();
    }
}
