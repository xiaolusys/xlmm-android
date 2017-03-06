package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.cpoopc.scrollablelayoutlib.ScrollableLayout;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.library.utils.JUtils;
import com.jimei.library.widget.DividerItemDecoration;
import com.jimei.library.widget.MyXRecyclerView;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.XlmmApp;
import com.jimei.xiaolumeimei.adapter.MMVisitorsAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.MMVisitorsBean;
import com.jimei.xiaolumeimei.service.ServiceResponse;

import butterknife.Bind;

/**
 * Created by wulei on 2016/2/4.
 */
public class MamaVisitorActivity extends BaseSwipeBackCompatActivity {

    @Bind(R.id.xrv)
    MyXRecyclerView xrv;
    @Bind(R.id.tv_count)
    TextView countTv;
    @Bind(R.id.scrollable_layout)
    ScrollableLayout mScrollableLayout;
    private int page = 2;
    private MMVisitorsAdapter mAdapter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_mamavisitor;
    }

    @Override
    protected void initViews() {
        mScrollableLayout.getHelper().setCurrentScrollableContainer(xrv);
        initRecyclerView();
    }

    @Override
    public View getLoadingView() {
        return mScrollableLayout;
    }

    @Override
    protected void initData() {
        showIndeterminateProgressDialog(false);
        loadMoreData(1);
    }

    private void initRecyclerView() {
        xrv.setLayoutManager(new LinearLayoutManager(this));
        xrv.addItemDecoration(
            new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        xrv.setLoadingMoreProgressStyle(ProgressStyle.BallPulse);
        xrv.setPullRefreshEnabled(false);
        xrv.setLoadingMoreEnabled(true);
        mAdapter = new MMVisitorsAdapter(this);
        xrv.setAdapter(mAdapter);
        xrv.setLoadingListener(new XRecyclerView.LoadingListener() {
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

    private void loadMoreData(int page) {
        addSubscription(XlmmApp.getVipInteractor(this)
            .getMamaVisitor(page, new ServiceResponse<MMVisitorsBean>() {
                @Override
                public void onNext(MMVisitorsBean mmVisitorsBean) {
                    if (mmVisitorsBean != null) {
                        countTv.setText(mmVisitorsBean.getCount() + "");
                        mAdapter.update(mmVisitorsBean.getResults());
                        if (null == mmVisitorsBean.getNext()) {
                            JUtils.Toast("没有更多了");
                            xrv.setLoadingMoreEnabled(false);
                        }
                    }
                    xrv.loadMoreComplete();
                    hideIndeterminateProgressDialog();
                }

                @Override
                public void onError(Throwable e) {
                    xrv.loadMoreComplete();
                    hideIndeterminateProgressDialog();
                }
            }));
    }
}