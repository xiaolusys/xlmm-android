package com.jimei.xiaolumeimei.ui.activity.trade;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.library.utils.JUtils;
import com.jimei.library.widget.SpaceItemDecoration;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.AllRefundsAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.AllRefundsBean;
import com.jimei.xiaolumeimei.model.TradeModel;
import com.jimei.xiaolumeimei.ui.activity.main.TabActivity;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import butterknife.Bind;

public class AllRefundsActivity extends BaseSwipeBackCompatActivity
        implements View.OnClickListener {

    @Bind(R.id.btn_jump)
    Button btn_jump;
    @Bind(R.id.rlayout_order_empty)
    RelativeLayout rl_empty;
    @Bind(R.id.xrv)
    XRecyclerView xrv;
    @Bind(R.id.tx_info)
    TextView tx_info;

    private AllRefundsAdapter adapter;
    private int page;
    private String next;

    @Override
    protected void setListener() {
        btn_jump.setOnClickListener(this);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_allrefunds;
    }

    @Override
    protected void initData() {
        showIndeterminateProgressDialog(false);
        page = 1;
        loadMoreData();
    }

    @Override
    protected void initViews() {
        adapter = new AllRefundsAdapter(this);
        xrv.setLayoutManager(new LinearLayoutManager(this));
        xrv.setLoadingMoreProgressStyle(ProgressStyle.BallPulse);
        xrv.setRefreshProgressStyle(ProgressStyle.BallPulse);
        xrv.addItemDecoration(new SpaceItemDecoration(0, 0, 0, 12));
        xrv.setAdapter(adapter);
        xrv.setLoadingListener(new XRecyclerView.LoadingListener() {
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
                    xrv.loadMoreComplete();
                }
            }
        });
        tx_info.setText("亲，您暂时还没有退货（款）订单哦~快去看看吧！");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_jump:
                Bundle bundle = new Bundle();
                bundle.putString("flag", "main");
                readyGo(TabActivity.class, bundle);
                finish();
                break;
        }
    }

    private void loadMoreData() {
        addSubscription(TradeModel.getInstance()
                .getRefundsBean(page)
                .subscribe(new ServiceResponse<AllRefundsBean>() {
                    @Override
                    public void onNext(AllRefundsBean allOrdersBean) {
                        if (allOrdersBean != null) {
                            List<AllRefundsBean.ResultsEntity> results = allOrdersBean.getResults();
                            if (results.size() == 0 && page == 1) {
                                rl_empty.setVisibility(View.VISIBLE);
                            } else if (page == 1) {
                                adapter.updateWithClear(results);
                            } else {
                                adapter.update(results);
                            }
                            next = allOrdersBean.getNext();
                            if (allOrdersBean.getNext() == null && adapter.getItemCount() != 0) {
                                JUtils.Toast("全部订单加载完成!");
                            }
                        }
                    }

                    @Override
                    public void onCompleted() {
                        hideIndeterminateProgressDialog();
                        xrv.loadMoreComplete();
                        xrv.refreshComplete();
                    }


                    @Override
                    public void onError(Throwable e) {
                        JUtils.Toast("数据加载失败");
                        hideIndeterminateProgressDialog();
                        xrv.loadMoreComplete();
                        xrv.refreshComplete();
                    }
                }));
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(this.getClass().getSimpleName());
        MobclickAgent.onResume(this);
    }
}
