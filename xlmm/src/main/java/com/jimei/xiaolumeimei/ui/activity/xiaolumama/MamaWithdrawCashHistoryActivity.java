package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.support.v7.widget.LinearLayoutManager;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.WithdrawCashHisAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.WithdrawCashHisBean;
import com.jimei.xiaolumeimei.model.MamaInfoModel;
import com.jimei.xiaolumeimei.widget.DividerItemDecoration;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import butterknife.Bind;
import rx.Subscription;
import rx.schedulers.Schedulers;


/**
 * Created by wulei on 2016/2/4.
 */
public class MamaWithdrawCashHistoryActivity extends BaseSwipeBackCompatActivity {
    String TAG = "MamaWithdrawCashHistoryActivity";

    @Bind(R.id.xrv)
    XRecyclerView xRecyclerView;
    private int page = 2;

    private WithdrawCashHisAdapter mHisAdapter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_withdrawcash_history;
    }

    @Override
    protected void initViews() {
        initRecyclerView();
    }

    @Override
    protected void initData() {
        showIndeterminateProgressDialog(false);
        Subscription subscribe = MamaInfoModel.getInstance()
                .getWithdrawCashHis("1")
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<WithdrawCashHisBean>() {
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        e.printStackTrace();
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        hideIndeterminateProgressDialog();
                    }

                    @Override
                    public void onNext(WithdrawCashHisBean pointBean) {
                        JUtils.Log(TAG, "WithdrawCashHisBean=" + pointBean.toString());
                        List<WithdrawCashHisBean.WithdrawCashRecord> results = pointBean.getResults();

                        if (0 == results.size()) {
                            JUtils.Log(TAG, "results.size()=0");
                        } else {
                            mHisAdapter.update(results);
                        }
                    }
                });
        addSubscription(subscribe);
    }

    private void initRecyclerView() {
        xRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        xRecyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        xRecyclerView.setRefreshProgressStyle(ProgressStyle.BallPulse);
        xRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallPulse);
        xRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
        xRecyclerView.setPullRefreshEnabled(false);
        xRecyclerView.setLoadingMoreEnabled(true);
        mHisAdapter = new WithdrawCashHisAdapter(this);
        xRecyclerView.setAdapter(mHisAdapter);

        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
            }

            @Override
            public void onLoadMore() {
                loadMoreData(page + "");
                page++;
            }

            private void loadMoreData(String page) {
                JUtils.Log(TAG, "第" + page);
                Subscription subscribe = MamaInfoModel.getInstance()
                        .getWithdrawCashHis(page)
                        .subscribeOn(Schedulers.io())
                        .subscribe(new ServiceResponse<WithdrawCashHisBean>() {
                            @Override
                            public void onNext(WithdrawCashHisBean withdrawCashHisBean) {
                                if (withdrawCashHisBean != null) {
                                    mHisAdapter.update(withdrawCashHisBean.getResults());
                                    if (withdrawCashHisBean.getNext() == null) {
                                        JUtils.Toast("没有更多了");
                                        xRecyclerView.setLoadingMoreEnabled(false);
                                        xRecyclerView.setRefreshing(false);
                                    }
                                    xRecyclerView.post(xRecyclerView::loadMoreComplete);
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                JUtils.Toast("没有更多了");
                                xRecyclerView.post(xRecyclerView::loadMoreComplete);
                                xRecyclerView.setLoadingMoreEnabled(false);
                                xRecyclerView.setRefreshing(false);
                            }
                        });
                addSubscription(subscribe);
            }
        });
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