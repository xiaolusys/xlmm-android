package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cpoopc.scrollablelayoutlib.ScrollableHelper;
import com.cpoopc.scrollablelayoutlib.ScrollableLayout;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.MMVisitorsAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.MMVisitorsBean;
import com.jimei.xiaolumeimei.model.MamaInfoModel;
import com.jimei.xiaolumeimei.widget.DividerItemDecoration;
import com.jimei.xiaolumeimei.widget.DividerItemDecorationForFooter;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.umeng.analytics.MobclickAgent;

import butterknife.Bind;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Created by wulei on 2016/2/4.
 */
public class MamaVisitorActivity extends BaseSwipeBackCompatActivity
        implements ScrollableHelper.ScrollableContainer {

    @Bind(R.id.xrv)
    XRecyclerView xrv;
    @Bind(R.id.tv_count)
    TextView countTv;
    @Bind(R.id.scrollable_layout)
    ScrollableLayout mScrollableLayout;
    private int page = 2;
    private MMVisitorsAdapter mAdapter;
    private Subscription subscribe;


    @Override
    protected void setListener() {
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_mamavisitor;
    }

    @Override
    protected void initViews() {
        initRecyclerView();
    }

    @Override
    protected void initData() {
        showIndeterminateProgressDialog(false);
        subscribe = MamaInfoModel.getInstance()
                .getMamaVisitor(1)
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<MMVisitorsBean>() {
                    @Override
                    public void onNext(MMVisitorsBean fansBeen) {
                        if (fansBeen != null) {
                            countTv.setText(fansBeen.getCount() + "");
                            if (fansBeen.getCount() != 0) {
                                mAdapter.update(fansBeen.getResults());

                            }
                            if (fansBeen.getNext() == null) {
                                xrv.setLoadingMoreEnabled(false);
                            }
                        }
                        hideIndeterminateProgressDialog();
                    }
                });
    }

    private void initRecyclerView() {
        xrv.setLayoutManager(new LinearLayoutManager(this));
        xrv.addItemDecoration(
                new DividerItemDecorationForFooter(this, DividerItemDecoration.VERTICAL_LIST));
        xrv.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        xrv.setLoadingMoreProgressStyle(ProgressStyle.SemiCircleSpin);
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
        mScrollableLayout.getHelper().setCurrentScrollableContainer(this);
    }

    private void loadMoreData(int page) {
        addSubscription(MamaInfoModel.getInstance()
                .getMamaVisitor(page)
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<MMVisitorsBean>() {
                    @Override
                    public void onNext(MMVisitorsBean fansBeen) {
                        if (fansBeen != null) {
                            mAdapter.update(fansBeen.getResults());
                            if (null == fansBeen.getNext()) {
                                Toast.makeText(MamaVisitorActivity.this, "没有更多了", Toast.LENGTH_SHORT).show();
                                xrv.post(xrv::loadMoreComplete);
                                xrv.setLoadingMoreEnabled(false);
                            }
                        }
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        xrv.post(xrv::loadMoreComplete);
                    }
                }));
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (subscribe != null && subscribe.isUnsubscribed()) {
            subscribe.unsubscribe();
        }
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

    @Override
    public View getScrollableView() {
        return xrv;
    }
}