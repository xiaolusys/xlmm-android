package com.jimei.xiaolumeimei.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jimei.xiaolumeimei.ui.fragment.v2.RankFragment;

import java.util.List;

/**
 * Created by wisdom on 16/8/17.
 */
public class RankFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<RankFragment> data;

    public RankFragmentPagerAdapter(FragmentManager fm, List<RankFragment> data) {
        super(fm);
        this.data = data;
    }

    @Override
    public Fragment getItem(int position) {
        return data.get(position);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return data.get(position).getTitle();
    }
}
