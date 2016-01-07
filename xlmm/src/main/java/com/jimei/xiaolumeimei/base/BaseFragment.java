package com.jimei.xiaolumeimei.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by itxuye(www.itxuye.com) on 15/12/29.
 * <p/>
 * Copyright 2015年 上海己美. All rights reserved.
 */
public abstract class BaseFragment extends Fragment {

    public Activity activity;
    public View view;

    abstract protected int provideContentViewId();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (null == view) {
            view = inflater.inflate(provideContentViewId(), container, false);

            initViews();

            initData();
        }


        ButterKnife.bind(this, view);

        return view;
    }

    protected abstract void initData();//初始化data


    protected abstract void initViews();//初始化view

}
