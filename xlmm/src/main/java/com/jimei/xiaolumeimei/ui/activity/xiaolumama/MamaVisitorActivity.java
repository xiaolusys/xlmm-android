package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;
import android.widget.Toast;

import com.cpoopc.scrollablelayoutlib.ScrollableLayout;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.library.utils.JUtils;
import com.jimei.library.widget.DividerItemDecoration;
import com.jimei.library.widget.MyXRecyclerView;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.MMVisitorsAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.model.MamaInfoModel;
import com.umeng.analytics.MobclickAgent;

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
    protected void initData() {
        showIndeterminateProgressDialog(false);
        addSubscription(MamaInfoModel.getInstance()
                .getMamaVisitor(1)
                .subscribe(fansBeen -> {
                    hideIndeterminateProgressDialog();
                    if (fansBeen != null) {
                        countTv.setText(fansBeen.getCount() + "");
                        if (fansBeen.getCount() != 0) {
                            mAdapter.update(fansBeen.getResults());

                        }
                        if (fansBeen.getNext() == null) {
                            xrv.setLoadingMoreEnabled(false);
                        }
                    }
                }, e -> JUtils.Log(e.getMessage())));
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
        addSubscription(MamaInfoModel.getInstance()
                .getMamaVisitor(page)
                .subscribe(fansBeen -> {
                    if (fansBeen != null) {
                        mAdapter.update(fansBeen.getResults());
                        if (null == fansBeen.getNext()) {
                            Toast.makeText(MamaVisitorActivity.this, "没有更多了", Toast.LENGTH_SHORT).show();
                            xrv.setLoadingMoreEnabled(false);
                        }
                    }
                    xrv.loadMoreComplete();
                }, e -> JUtils.Log(e.getMessage())));
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