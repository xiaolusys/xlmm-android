package com.jimei.xiaolumeimei.ui.fragment.product;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.jimei.library.utils.JUtils;
import com.jimei.library.widget.SpaceItemDecoration;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.XlmmApp;
import com.jimei.xiaolumeimei.adapter.ActivityAdapter;
import com.jimei.xiaolumeimei.base.BaseBindingFragment;
import com.jimei.xiaolumeimei.databinding.FragmentActivityBinding;
import com.jimei.xiaolumeimei.entities.PortalBean;
import com.jimei.xiaolumeimei.service.ServiceResponse;

/**
 * Created by wisdom on 17/2/28.
 */

public class ActivityFragment extends BaseBindingFragment<FragmentActivityBinding> implements SwipeRefreshLayout.OnRefreshListener {

    private ActivityAdapter adapter;

    public static ActivityFragment newInstance(String title) {
        Bundle args = new Bundle();
        args.putString("title", title);
        ActivityFragment fragment = new ActivityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View getLoadingView() {
        return b.recyclerView;
    }

    @Override
    public void setListener() {
        b.swipeLayout.setOnRefreshListener(this);
    }

    @Override
    public void initData() {
        addSubscription(XlmmApp.getMainInteractor(mActivity)
            .getPortalBean("categorys,posters", new ServiceResponse<PortalBean>() {
                @Override
                public void onNext(PortalBean portalBean) {
                    adapter.update(portalBean.getActivitys());
                    hideIndeterminateProgressDialog();
                    if (b.swipeLayout.isRefreshing()) {
                        b.swipeLayout.setRefreshing(false);
                    }

                }

                @Override
                public void onError(Throwable e) {
                    JUtils.Toast("数据加载失败，请下拉刷新重试!");
                    hideIndeterminateProgressDialog();
                    if (b.swipeLayout.isRefreshing()) {
                        b.swipeLayout.setRefreshing(false);
                    }
                }
            }));
    }

    @Override
    protected void initViews() {
        b.swipeLayout.setColorSchemeResources(R.color.colorAccent);

        b.recyclerView.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        b.recyclerView.addItemDecoration(new SpaceItemDecoration(0, 0, 0, 18));
        adapter = new ActivityAdapter(mActivity);
        b.recyclerView.setAdapter(adapter);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_activity;
    }

    @Override
    public void onRefresh() {
        adapter.clear();
        showIndeterminateProgressDialog(true);
        initData();
    }
}
