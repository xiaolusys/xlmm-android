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
import com.jimei.xiaolumeimei.adapter.OderCarryLogAdapter;
import com.jimei.xiaolumeimei.base.BaseLazyFragment;
import com.jimei.xiaolumeimei.entities.OderCarryBean;
import com.jimei.xiaolumeimei.model.MamaInfoModel;
import com.jimei.xiaolumeimei.service.ServiceResponse;

import butterknife.Bind;


/**
 * Created by itxuye(www.itxuye.com) on 2016/03/11.
 * <p>
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class CarryLogCommissionFragment extends BaseLazyFragment {
    @Bind(R.id.carrylogall_xry)
    XRecyclerView xRecyclerView;
    private OderCarryLogAdapter adapter;
    private int page = 2;

    public static CarryLogCommissionFragment newInstance(String title) {
        CarryLogCommissionFragment carryLogAllFragment = new CarryLogCommissionFragment();
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
                .getMamaAllOderCarryLogs("1")
                .subscribe(new ServiceResponse<OderCarryBean>() {

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
                    public void onNext(OderCarryBean carryLogListBean) {
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

        adapter = new OderCarryLogAdapter(getActivity());
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
                .getMamaAllOderCarryLogs(page)
                .subscribe(new ServiceResponse<OderCarryBean>() {
                    @Override
                    public void onNext(OderCarryBean carryLogListBean) {
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
    public View getLoadingView() {
        return xRecyclerView;
    }
}
