package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;

import com.cpoopc.scrollablelayoutlib.ScrollableLayout;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.MamaLivenessAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.MamaLivenessBean;
import com.jimei.xiaolumeimei.model.MamaInfoModel;
import com.jimei.xiaolumeimei.widget.DividerItemDecoration;
import com.jimei.xiaolumeimei.widget.MyXRecyclerView;
import com.jude.utils.JUtils;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import butterknife.Bind;
import rx.schedulers.Schedulers;

/**
 * Created by wulei on 2016/2/4.
 */
public class MamaLivenessActivity extends BaseSwipeBackCompatActivity {
    String TAG = "MamaLivenessActivity";

    @Bind(R.id.lv_liveness)
    MyXRecyclerView lv_liveness;
    @Bind(R.id.tv_liveness)
    TextView tv_liveness;
    @Bind(R.id.scrollable_layout)
    ScrollableLayout scrollableLayout;

    private int page = 2;
    private MamaLivenessAdapter mAdapter;
    int liveness = 0;

    @Override
    protected void getBundleExtras(Bundle extras) {
        if (null != extras) {
            liveness = extras.getInt("liveness");
        }
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_mamaliveness;
    }

    @Override
    protected void initViews() {
        scrollableLayout.getHelper().setCurrentScrollableContainer(lv_liveness);
        initRecyclerView();
        tv_liveness.setText(liveness + "");
    }

    private void initRecyclerView() {
        lv_liveness.setLayoutManager(new LinearLayoutManager(this));
        lv_liveness.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        lv_liveness.setLoadingMoreProgressStyle(ProgressStyle.BallPulse);
        lv_liveness.setPullRefreshEnabled(false);
        lv_liveness.setLoadingMoreEnabled(true);

        mAdapter = new MamaLivenessAdapter();
        lv_liveness.setAdapter(mAdapter);

        lv_liveness.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                loadMoreData(page + "");
                page++;
            }


        });
    }

    private void loadMoreData(String page) {
        addSubscription(MamaInfoModel.getInstance()
                .getMamaLiveness(page)
                .subscribeOn(Schedulers.io())
                .subscribe(livenessBean -> {
                    if (livenessBean != null) {
                        mAdapter.update(livenessBean.getResults());
                        if (null == livenessBean.getNext()) {
                            JUtils.Toast("没有更多了");
                            lv_liveness.setLoadingMoreEnabled(false);
                        }
                    }
                    lv_liveness.loadMoreComplete();
                }, e -> JUtils.Log(e.getMessage())));
    }

    @Override
    protected void initData() {
        showIndeterminateProgressDialog(false);
        addSubscription(MamaInfoModel.getInstance()
                .getMamaLiveness("1")
                .subscribeOn(Schedulers.io())
                .subscribe(pointBean -> {
                    if (pointBean != null) {
                        JUtils.Log(TAG, "MamaLivenessBean=" + pointBean.toString());
                        List<MamaLivenessBean.ResultsEntity> results = pointBean.getResults();

                        if (0 == results.size()) {
                            JUtils.Log(TAG, "results.size()=0");
                        } else {
                            mAdapter.update(results);
                        }
                        if (null == pointBean.getNext()) {
                            lv_liveness.setLoadingMoreEnabled(false);
                        }
                    }
                    hideIndeterminateProgressDialog();
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