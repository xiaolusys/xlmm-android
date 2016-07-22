package com.jimei.xiaolumeimei.widget;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.io.Serializable;

/**
 * Created by wisdom on 16/7/18.
 */
public class SerialFragmentAdapter extends FragmentPagerAdapter  implements Serializable{
    public SerialFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }
}
