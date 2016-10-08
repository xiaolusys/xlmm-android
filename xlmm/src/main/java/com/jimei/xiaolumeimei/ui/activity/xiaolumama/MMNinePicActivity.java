package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.NinePicAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.WxQrcode;
import com.jimei.xiaolumeimei.model.MMProductModel;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.umeng.analytics.MobclickAgent;

import butterknife.Bind;
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
        showIndeterminateProgressDialog(false);
        addSubscription(MMProductModel.getInstance()
                .getWxCode()
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<WxQrcode>() {
                    @Override
                    public void onNext(WxQrcode wxQrcode) {
                        mAdapter.setCodeLink(wxQrcode.getQrcode_link());
                    }
                }));
        addSubscription(MMProductModel.getInstance()
                .getNinePic()
                .subscribeOn(Schedulers.io())
                .subscribe(ninePicBean -> {
                    if (ninePicBean != null) {
                        mAdapter.update(ninePicBean);
                        mAdapter.notifyDataSetChanged();
                    }
                    if (mRefreshLayout.isRefreshing()) {
                        mRefreshLayout.setRefreshing(false);
                    }
                    hideIndeterminateProgressDialog();
                }, Throwable::printStackTrace));
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
        mAdapter = new NinePicAdapter(this);
        circleLv.setAdapter(mAdapter);
        mRefreshLayout.setColorSchemeResources(R.color.colorAccent);
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
        mAdapter.clear();
        loadData();
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
