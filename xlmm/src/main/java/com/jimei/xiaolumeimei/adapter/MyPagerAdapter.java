package com.jimei.xiaolumeimei.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by wisdom on 16/8/9.
 */
public class MyPagerAdapter extends PagerAdapter {


    private ArrayList<ImageView> mListViews;
    private ViewPager mViewPager;

    public MyPagerAdapter(ArrayList<ImageView> mListViews, ViewPager viewPager) {
        this.mListViews = mListViews;
        mViewPager = viewPager;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mListViews.get(position));
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mListViews.get(position), 0);
        return mListViews.get(position);
    }

//    @Override
//    public void finishUpdate(ViewGroup container) {
//        if (mListViews.size() == 1) {
//            return;
//        }
//        int position = mViewPager.getCurrentItem();
//        if (position == 0) {
//            mViewPager.setCurrentItem((mListViews.size() - 2), false);
//        } else if (position == mListViews.size() - 1) {
//            mViewPager.setCurrentItem(1, false);
//        }
//    }

    @Override
    public int getCount() {
        return mListViews.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }
}