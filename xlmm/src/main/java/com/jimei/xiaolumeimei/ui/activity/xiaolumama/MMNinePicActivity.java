package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.Bind;
import cn.iwgang.countdownview.CountdownView;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.NinePicAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.NinePicBean;
import com.jimei.xiaolumeimei.model.MMProductModel;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.umeng.analytics.MobclickAgent;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import rx.schedulers.Schedulers;

/**
 * Created by itxuye(www.itxuye.com) on 2016/02/29.
 * <p>
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class MMNinePicActivity extends BaseSwipeBackCompatActivity
        implements SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.circleLv)
    ListView circleLv;
    @Bind(R.id.mRefreshLayout)
    SwipeRefreshLayout mRefreshLayout;
    @Bind(R.id.cv_lefttime)
    CountdownView cvLefttime;
    @Bind(R.id.left)
    TextView left;
    @Bind(R.id.count_left)
    FrameLayout countLeft;

    private NinePicAdapter mAdapter;

    @Override
    protected void setListener() {
        mRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void initData() {
        loadData();
    }

    private void loadData() {

        if (calcLeftTime() > 0) {
            //myTickCircleProgress.setVisibility(View.VISIBLE);
            countLeft.setVisibility(View.VISIBLE);
            cvLefttime.start(calcLeftTime());
            left.setText("热销分享即将开始...");
        } else {
            countLeft.setVisibility(View.GONE);
            showIndeterminateProgressDialog(false);
            MMProductModel.getInstance()
                    .getNinePic()
                    .subscribeOn(Schedulers.io())
                    .subscribe(new ServiceResponse<List<NinePicBean>>() {

                        @Override
                        public void onCompleted() {
                            super.onCompleted();
                            hideIndeterminateProgressDialog();
                        }

                        @Override
                        public void onError(Throwable e) {
                            super.onError(e);
                            e.printStackTrace();
                        }

                        @Override
                        public void onNext(List<NinePicBean> ninePicBean) {
                            if (ninePicBean != null) {
                                mAdapter.setDatas(ninePicBean);
                                mAdapter.notifyDataSetChanged();
                            } else {
                                countLeft.setVisibility(View.VISIBLE);
                            }
                        }
                    });
        }
    }

    private void loadDataRefresh() {

        //showIndeterminateProgressDialog(false);
        MMProductModel.getInstance()
                .getNinePic()
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<List<NinePicBean>>() {

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        //hideIndeterminateProgressDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(List<NinePicBean> ninePicBean) {
                        if (ninePicBean != null) {
                            mAdapter.setDatas(ninePicBean);
                            mAdapter.notifyDataSetChanged();
                            countLeft.setVisibility(View.GONE);
                        } else {
                            countLeft.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_ninepic;
    }

    @Override
    protected void initViews() {

        mRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        mAdapter = new NinePicAdapter(this);
        circleLv.setAdapter(mAdapter);
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadDataRefresh();
                try {
                    mRefreshLayout.setRefreshing(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 2000);
    }

    private long calcLeftTime() {

        Date now = new Date();
        Date nextDay10PM = new Date();
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(nextDay10PM);
        calendar.set(Calendar.HOUR_OF_DAY, 10);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        nextDay10PM = calendar.getTime();

        return nextDay10PM.getTime() - now.getTime();
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
