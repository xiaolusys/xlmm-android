package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;
import butterknife.Bind;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.MMVisitorsAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.MMVisitorsBean;
import com.jimei.xiaolumeimei.model.MamaInfoModel;
import com.jimei.xiaolumeimei.utils.StatusBarUtil;
import com.jimei.xiaolumeimei.widget.DividerItemDecoration;
import com.jimei.xiaolumeimei.widget.DividerItemDecorationForFooter;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Created by wulei on 2016/2/4.
 */
public class MamaVisitorActivity extends BaseSwipeBackCompatActivity
    implements View.OnClickListener {

  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.xrv_mamafans) XRecyclerView xrv_mamafans;
  private int page = 2;
  private MMVisitorsAdapter mAdapter;
  private Subscription subscribe;
  private String from;

  @Override protected void setListener() {
    toolbar.setOnClickListener(this);
  }

  @Override protected void getBundleExtras(Bundle extras) {
    from = extras.getString("from");
  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.activity_mamavisitor;
  }

  @Override protected void initViews() {
    StatusBarUtil.setColor(this, getResources().getColor(R.color.colorAccent),0);
    toolbar.setTitle("");
    setSupportActionBar(toolbar);
    finishBack(toolbar);

    initRecyclerView();
  }

  @Override protected void initData() {
    showIndeterminateProgressDialog(false);
    subscribe = MamaInfoModel.getInstance()
        .getMamaVisitor(from, "1")
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<MMVisitorsBean>() {
          @Override public void onCompleted() {
            super.onCompleted();
            hideIndeterminateProgressDialog();
          }

          @Override public void onError(Throwable e) {
            super.onError(e);
            e.printStackTrace();
          }

          @Override public void onNext(MMVisitorsBean fansBeen) {

            if (fansBeen != null) {
              if (0 == fansBeen.getCount()) {
              } else {
                mAdapter.update(fansBeen.getResults());
              }

              if (null == fansBeen.getNext()) {
                //Toast.makeText(MamaVisitorActivity.this, "没有更多了", Toast.LENGTH_SHORT)
                //    .show();
                //xrv_mamafans.post(xrv_mamafans::loadMoreComplete);
                xrv_mamafans.setLoadingMoreEnabled(false);
              }
            }
          }
        });
  }

  private void initRecyclerView() {
    xrv_mamafans.setLayoutManager(new LinearLayoutManager(this));
    xrv_mamafans.addItemDecoration(
        new DividerItemDecorationForFooter(this, DividerItemDecoration.VERTICAL_LIST));
    xrv_mamafans.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
    xrv_mamafans.setLaodingMoreProgressStyle(ProgressStyle.SemiCircleSpin);
    xrv_mamafans.setArrowImageView(R.drawable.iconfont_downgrey);
    xrv_mamafans.setPullRefreshEnabled(false);
    xrv_mamafans.setLoadingMoreEnabled(true);

    mAdapter = new MMVisitorsAdapter(this);
    xrv_mamafans.setAdapter(mAdapter);

    xrv_mamafans.setLoadingListener(new XRecyclerView.LoadingListener() {
      @Override public void onRefresh() {

      }

      @Override public void onLoadMore() {
        loadMoreData(page + "");
        page++;
      }

      private void loadMoreData(String page) {
        Subscription subscribe = MamaInfoModel.getInstance()
            .getMamaVisitor(from, page)
            .subscribeOn(Schedulers.io())
            .subscribe(new ServiceResponse<MMVisitorsBean>() {
              @Override public void onNext(MMVisitorsBean fansBeen) {
                if (fansBeen != null) {
                  mAdapter.update(fansBeen.getResults());
                  if (null != fansBeen.getNext()) {
                  } else {
                    Toast.makeText(MamaVisitorActivity.this, "没有更多了", Toast.LENGTH_SHORT)
                        .show();
                    xrv_mamafans.post(xrv_mamafans::loadMoreComplete);
                  }
                }
              }

              @Override public void onCompleted() {
                super.onCompleted();
                xrv_mamafans.post(xrv_mamafans::loadMoreComplete);
              }
            });
        addSubscription(subscribe);
      }
    });
  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }

  @Override public void onClick(View v) {
    switch (v.getId()) {

    }
  }

  @Override protected void onStop() {
    super.onStop();
    if (subscribe != null && subscribe.isUnsubscribed()) {
      subscribe.unsubscribe();
    }
  }
}