package com.jimei.xiaolumeimei.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.widget.SwipeBackLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by itxuye(www.itxuye.com) on 15/12/29.
 * <p/>
 * Copyright 2015年 上海己美. All rights reserved.
 */
public abstract class BaseSwipeBackActivity extends AppCompatActivity{

    @Bind(R.id.swipBackLayout)SwipeBackLayout mSwipeBackLayout;

    abstract protected int provideContendtViewId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(provideContendtViewId());

        ButterKnife.bind(this);
        if (savedInstanceState == null) {
            initSwipeLayOut();

            initView();

            initData();

            setListener();
        }
    }

    protected abstract void setListener();//设置监听

    protected abstract void initData();//初始化数据

    protected abstract void initView();//初始化view

    protected void initSwipeLayOut(){
        mSwipeBackLayout.setCallBack(new SwipeBackLayout.CallBack() {
            @Override
            public void onFinish() {
                finish();
            }
        });
}}
