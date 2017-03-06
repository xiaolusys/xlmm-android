package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.jimei.library.utils.JUtils;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.XlmmApp;
import com.jimei.xiaolumeimei.adapter.NinePicAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.NinePicBean;
import com.jimei.xiaolumeimei.entities.WxQrcode;
import com.jimei.xiaolumeimei.service.ServiceResponse;

import java.util.List;

import butterknife.Bind;

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
    @Bind(R.id.empty_layout)
    LinearLayout emptyLayout;
    @Bind(R.id.layout)
    FrameLayout layout;

    private NinePicAdapter mAdapter;
    private int mSale_category = -1;
    private int mModel_id = -1;
    private String mCodeLink;

    @Override
    protected void setListener() {
        mRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void initData() {
        if (mCodeLink == null || "".equals(mCodeLink)) {
            addSubscription(XlmmApp.getVipInteractor(this)
                .getWxCode(new ServiceResponse<WxQrcode>() {
                    @Override
                    public void onNext(WxQrcode wxQrcode) {
                        mCodeLink = wxQrcode.getQrcode_link();
                        loadData();
                    }
                }));
        } else {
            loadData();
        }
    }

    @Override
    public View getLoadingView() {
        return layout;
    }

    private void loadData() {
        showIndeterminateProgressDialog(false);
        if (mModel_id == -1) {
            addSubscription(XlmmApp.getVipInteractor(this)
                .getNinePic(mSale_category, new ServiceResponse<List<NinePicBean>>() {
                    @Override
                    public void onNext(List<NinePicBean> list) {
                        doWhileSuccess(list);
                    }

                    @Override
                    public void onError(Throwable e) {
                        doWhileFail(e);
                    }
                }));
        } else {
            addSubscription(XlmmApp.getVipInteractor(this)
                .getNinePicByModelId(mModel_id, new ServiceResponse<List<NinePicBean>>() {
                    @Override
                    public void onNext(List<NinePicBean> list) {
                        doWhileSuccess(list);
                    }

                    @Override
                    public void onError(Throwable e) {
                        doWhileFail(e);
                    }
                }));
        }
    }

    private void doWhileSuccess(List<NinePicBean> ninePicBean) {
        if (ninePicBean != null && ninePicBean.size() > 0) {
            mAdapter.update(ninePicBean);
        } else {
            emptyLayout.setVisibility(View.VISIBLE);
        }
        if (mRefreshLayout.isRefreshing()) {
            mRefreshLayout.setRefreshing(false);
        }
        hideIndeterminateProgressDialog();
    }

    private void doWhileFail(Throwable e) {
        e.printStackTrace();
        JUtils.Toast("数据加载失败,请下拉刷新重试!");
        hideIndeterminateProgressDialog();
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        mSale_category = extras.getInt("sale_category", -1);
        mModel_id = extras.getInt("model_id", -1);
        mCodeLink = extras.getString("codeLink", "");
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_ninepic;
    }

    @Override
    protected void initViews() {
        mAdapter = new NinePicAdapter(this);
        mAdapter.setCodeLink(mCodeLink);
        circleLv.setAdapter(mAdapter);
        mRefreshLayout.setColorSchemeResources(R.color.colorAccent);
    }

    @Override
    public void onRefresh() {
        mAdapter.clear();
        loadData();
    }
}
