package com.jimei.xiaolumeimei.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jimei.xiaolumeimei.base.BaseBindingFragment;

import java.util.List;

/**
 * Created by wisdom on 16/9/12.
 */
public class MamaTabAdapter extends FragmentPagerAdapter {
    private List<BaseBindingFragment> listFragment;

    public MamaTabAdapter(FragmentManager fm, List<BaseBindingFragment> listFragment) {
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
