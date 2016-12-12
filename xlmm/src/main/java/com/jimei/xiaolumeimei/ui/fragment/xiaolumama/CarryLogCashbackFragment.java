package com.jimei.xiaolumeimei.ui.fragment.xiaolumama;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.library.utils.JUtils;
import com.jimei.library.widget.DividerItemDecoration;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.ClickCarryLogAdapter;
import com.jimei.xiaolumeimei.base.BaseLazyFragment;
import com.jimei.xiaolumeimei.entities.ClickcarryBean;
import com.jimei.xiaolumeimei.model.MamaInfoModel;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.umeng.analytics.MobclickAgent;

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
        addSubscription(MamaInfoModel.getInstance()
                .getMamaAllClickCarryLogs("1")
                .subscribe(new ServiceResponse<ClickcarryBean>() {

                    @Override
                    public void onCompleted() {
                        hideIndeterminateProgressDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideIndeterminateProgressDialog();
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(ClickcarryBean carryLogListBean) {
                        if (carryLogListBean != null) {
                            adapter.update(carryLogListBean.getResults());
                            if (null == carryLogListBean.getNext()) {
                                xRecyclerView.setLoadingMoreEnabled(false);
                            }
                            JUtils.Log("carrylog", carryLogListBean.toString());
                        }
                    }
                }));
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

        adapter = new ClickCarryLogAdapter();
        xRecyclerView.setAdapter(adapter);

        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                loadMoreData(page + "", getActivity());
                page++;
            }
        });
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_carrylogall;
    }

    private void loadMoreData(String page, Context context) {

        addSubscription(MamaInfoModel.getInstance()
                .getMamaAllClickCarryLogs(page)
                .subscribe(new ServiceResponse<ClickcarryBean>() {
                    @Override
                    public void onNext(ClickcarryBean carryLogListBean) {
                        if (carryLogListBean != null) {
                            if (null != carryLogListBean.getResults()) {
                                adapter.update(carryLogListBean.getResults());
                            }
                            if (null != carryLogListBean.getNext()) {
                            } else {
                                Toast.makeText(context, "没有更多了", Toast.LENGTH_SHORT).show();
                                xRecyclerView.post(xRecyclerView::loadMoreComplete);
                                xRecyclerView.setLoadingMoreEnabled(false);
                                ;
                            }
                        }
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        try {
                            xRecyclerView.post(xRecyclerView::loadMoreComplete);
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }
                    }
                }));
    }

    @Override
    public View getScrollableView() {
        return xRecyclerView;
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(this.getClass().getSimpleName());
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
    }

    @Override
    public View getLoadingView() {
        return xRecyclerView;
    }
}
