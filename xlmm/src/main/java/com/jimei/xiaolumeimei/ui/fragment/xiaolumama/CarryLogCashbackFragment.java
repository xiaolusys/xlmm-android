package com.jimei.xiaolumeimei.ui.fragment.xiaolumama;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.library.utils.JUtils;
import com.jimei.library.widget.DividerItemDecoration;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.XlmmApp;
import com.jimei.xiaolumeimei.adapter.ClickCarryLogAdapter;
import com.jimei.xiaolumeimei.base.BaseLazyFragment;
import com.jimei.xiaolumeimei.entities.ClickCarryBean;
import com.jimei.xiaolumeimei.service.ServiceResponse;

import butterknife.Bind;

/**
 * Created by itxuye(www.itxuye.com) on 2016/03/11.
 * <p>
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class CarryLogCashbackFragment extends BaseLazyFragment {
    @Bind(R.id.carrylogall_xry)
    XRecyclerView xRecyclerView;
    private ClickCarryLogAdapter adapter;
    private int page = 2;

    public static CarryLogCashbackFragment newInstance(String title) {
        CarryLogCashbackFragment carryLogAllFragment = new CarryLogCashbackFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        carryLogAllFragment.setArguments(bundle);
        return carryLogAllFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void initData() {
        loadMoreData(1);
    }

    @Override
    protected void initViews() {
        xRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        xRecyclerView.addItemDecoration(
            new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        xRecyclerView.setRefreshProgressStyle(ProgressStyle.BallPulse);
        xRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallPulse);
        xRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
        xRecyclerView.setPullRefreshEnabled(false);
        xRecyclerView.setLoadingMoreEnabled(true);

        adapter = new ClickCarryLogAdapter(mActivity);
        xRecyclerView.setAdapter(adapter);

        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                loadMoreData(page);
                page++;
            }
        });
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_carrylogall;
    }

    private void loadMoreData(int page) {
        addSubscription(XlmmApp.getVipInteractor(mActivity)
            .getMamaAllClickCarryLogs(page, new ServiceResponse<ClickCarryBean>() {
                @Override
                public void onNext(ClickCarryBean carryLogListBean) {
                    if (carryLogListBean != null) {
                        if (carryLogListBean.getResults() != null) {
                            adapter.update(carryLogListBean.getResults());
                        }
                        if (carryLogListBean.getNext() == null) {
                            JUtils.Toast("没有更多了");
                            xRecyclerView.setLoadingMoreEnabled(false);
                        }
                    }
                    xRecyclerView.loadMoreComplete();
                    hideIndeterminateProgressDialog();
                }

                @Override
                public void onError(Throwable e) {
                    xRecyclerView.loadMoreComplete();
                    hideIndeterminateProgressDialog();
                }
            }));
    }

    @Override
    public View getScrollableView() {
        return xRecyclerView;
    }

    @Override
    public View getLoadingView() {
        return xRecyclerView;
    }
}
