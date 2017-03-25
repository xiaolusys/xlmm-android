package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.library.utils.JUtils;
import com.jimei.library.widget.DividerItemDecoration;
import com.jimei.library.widget.scrolllayout.ScrollableHelper;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.XlmmApp;
import com.jimei.xiaolumeimei.adapter.MMCarryAdapter;
import com.jimei.xiaolumeimei.base.BaseMVVMActivity;
import com.jimei.xiaolumeimei.databinding.ActivityMmCarryBinding;
import com.jimei.xiaolumeimei.entities.MMCarryBean;
import com.jimei.xiaolumeimei.service.ServiceResponse;

import java.text.DecimalFormat;

/**
 * Created by wisdom on 17/3/21.
 */

public class MMCarryActivity extends BaseMVVMActivity<ActivityMmCarryBinding>
    implements ScrollableHelper.ScrollableContainer {
    private int page = 1;
    private MMCarryAdapter adapter;

    @Override
    protected void initData() {
        showIndeterminateProgressDialog(false);
        loadMoreData();
    }

    @Override
    protected void initViews() {
        b.xrv.setLayoutManager(new LinearLayoutManager(this));
        b.xrv.setPullRefreshEnabled(false);
        b.xrv.setLoadingMoreEnabled(true);
        b.xrv.addItemDecoration(
            new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        b.xrv.setLoadingMoreProgressStyle(ProgressStyle.BallPulse);
        adapter = new MMCarryAdapter(this);
        b.xrv.setAdapter(adapter);
        b.xrv.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                loadMoreData();
            }
        });
        b.scrollableLayout.getHelper().setCurrentScrollableContainer(this);
    }

    private void loadMoreData() {
        addSubscription(XlmmApp.getVipInteractor(this)
            .getCarryBean(page, new ServiceResponse<MMCarryBean>() {
                @Override
                public void onNext(MMCarryBean mmCarryBean) {
                    if (mmCarryBean != null) {
                        if (page == 1) {
                            int total = mmCarryBean.getTotal();
                            String totalStr = new DecimalFormat("0.00").format((double) total / 100);
                            b.tvAll.setText(totalStr);
                        }
                        adapter.update(mmCarryBean.getResults());
                        if (mmCarryBean.getNext() != null) {
                            page++;
                        } else {
                            JUtils.Toast("没有更多了");
                            b.xrv.setLoadingMoreEnabled(false);
                        }
                    }
                    b.xrv.loadMoreComplete();
                    hideIndeterminateProgressDialog();
                }
            }));
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_mm_carry;
    }

    @Override
    public View getLoadingView() {
        return b.scrollableLayout;
    }

    @Override
    public View getScrollableView() {
        return b.xrv;
    }
}
