package com.jimei.xiaolumeimei.ui.fragment.trade;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.library.utils.JUtils;
import com.jimei.library.widget.SpaceItemDecoration;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.XlmmApp;
import com.jimei.xiaolumeimei.adapter.AllOrdersAdapter;
import com.jimei.xiaolumeimei.base.BaseBindingFragment;
import com.jimei.xiaolumeimei.databinding.FragmentOrderListBinding;
import com.jimei.xiaolumeimei.entities.AllOrdersBean;
import com.jimei.xiaolumeimei.entities.event.RefreshOrderListEvent;
import com.jimei.xiaolumeimei.service.ServiceResponse;
import com.jimei.xiaolumeimei.ui.activity.main.TabActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;


public class OrderListFragment extends BaseBindingFragment<FragmentOrderListBinding> implements View.OnClickListener {

    private int type;
    private AllOrdersAdapter adapter;
    private int page;
    private String next;
    private boolean isEvent;

    public static OrderListFragment newInstance(int type, String title) {
        OrderListFragment fragment = new OrderListFragment();
        Bundle args = new Bundle();
        args.putInt("type", type);
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getInt("type");
        }
        isEvent = false;
    }

    @Override
    public void initData() {
        showIndeterminateProgressDialog(false);
        page = 1;
        loadMoreData();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_order_list;
    }

    @Override
    public View getLoadingView() {
        return b.xrv;
    }

    @Override
    public void setListener() {
        b.btnJump.setOnClickListener(this);
    }

    @Override
    protected void initViews() {
        EventBus.getDefault().register(this);
        b.xrv.setLayoutManager(new LinearLayoutManager(mActivity));
        b.xrv.setLoadingMoreProgressStyle(ProgressStyle.BallPulse);
        b.xrv.setRefreshProgressStyle(ProgressStyle.BallPulse);
        b.xrv.addItemDecoration(new SpaceItemDecoration(0, 0, 0, 12));
        adapter = new AllOrdersAdapter(mActivity);
        b.xrv.setAdapter(adapter);
        b.xrv.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                loadMoreData();
            }

            @Override
            public void onLoadMore() {
                if (next != null && !"".equals(next)) {
                    page++;
                    loadMoreData();
                } else {
                    JUtils.Toast("没有更多了!");
                    b.xrv.loadMoreComplete();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_jump:
                Intent intent = new Intent(getActivity(), TabActivity.class);
                mActivity.startActivity(intent);
                mActivity.finish();
                break;
        }
    }


    private void loadMoreData() {
        addSubscription(XlmmApp.getTradeInteractor(mActivity)
            .getOrderList(type, page, new ServiceResponse<AllOrdersBean>() {
                @Override
                public void onNext(AllOrdersBean allOrdersBean) {
                    if (allOrdersBean != null) {
                        List<AllOrdersBean.ResultsEntity> results = allOrdersBean.getResults();
                        if (results.size() == 0 && page == 1) {
                            b.emptyLayout.setVisibility(View.VISIBLE);
                        } else if (page == 1) {
                            adapter.updateWithClear(results);
                        } else {
                            adapter.update(results);
                        }
                        next = allOrdersBean.getNext();
                        if (allOrdersBean.getNext() == null && adapter.getItemCount() != 0 && !isEvent) {
                            JUtils.Toast("全部订单加载完成!");
                        }
                        isEvent = false;
                    }
                    b.xrv.loadMoreComplete();
                    b.xrv.refreshComplete();
                    hideIndeterminateProgressDialog();
                }

                @Override
                public void onError(Throwable e) {
                    b.xrv.loadMoreComplete();
                    b.xrv.refreshComplete();
                    hideIndeterminateProgressDialog();
                }
            }));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reLoadData(RefreshOrderListEvent event) {
        isEvent = true;
        initData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
