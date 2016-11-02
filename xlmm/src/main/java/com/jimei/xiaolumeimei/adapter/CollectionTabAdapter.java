package com.jimei.xiaolumeimei.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jimei.xiaolumeimei.ui.fragment.v1.CollectionFragment;

import java.util.List;

/**
 * Created by wisdom on 16/7/28.
 */
public class CollectionTabAdapter extends FragmentPagerAdapter {
    private List<CollectionFragment> listFragment;

    public CollectionTabAdapter(FragmentManager fm, List<CollectionFragment> listFragment) {
        super(fm);
        this.listFragment = listFragment;
    }

    @Override
    public Fragment getItem(int position) {
        return listFragment.get(position);
    }

    @Override
    public int getCount() {
        return listFragment.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return listFragment.get(position).getTitle();
    }
}
