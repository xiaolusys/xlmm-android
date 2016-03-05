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
import com.jimei.xiaolumeimei.adapter.MamaFansAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.MamaFansBean;
import com.jimei.xiaolumeimei.model.MamaInfoModel;
import com.jimei.xiaolumeimei.widget.DividerItemDecoration;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Created by wulei on 2016/2/4.
 */
public class MamaFansActivity extends BaseSwipeBackCompatActivity implements View.OnClickListener{
  String TAG = "MamaFansActivity";

  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.xrv_mamafans) XRecyclerView xrv_mamafans;
  private int page = 2;
  private MamaFansAdapter mAdapter;
  private Subscription subscribe;

  @Override protected void setListener() {
    toolbar.setOnClickListener(this);
  }
  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.activity_mamafans;
  }

  @Override protected void initViews() {
    toolbar.setTitle("");
    setSupportActionBar(toolbar);
    finishBack(toolbar);


    initRecyclerView();
  }

  @Override protected void initData() {
     subscribe = MamaInfoModel.getInstance()
        .getMamaFans("1")
        .subscribeOn(Schedulers.newThread())
        .subscribe(new ServiceResponse<MamaFansBean>() {
          @Override public void onNext(MamaFansBean fansBeen) {
            JUtils.Log(TAG, "size =" + fansBeen.getCount());

            if (0 == fansBeen.getCount()) {
              JUtils.Log(TAG, "results.size()=0");
            } else {
              mAdapter.update(fansBeen.getFans());
            }
          }
        });
  }

  private void initRecyclerView() {
    xrv_mamafans.setLayoutManager(new LinearLayoutManager(this));
    xrv_mamafans.addItemDecoration(
        new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
    xrv_mamafans.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
    xrv_mamafans.setLaodingMoreProgressStyle(ProgressStyle.SemiCircleSpin);
    xrv_mamafans.setArrowImageView(R.drawable.iconfont_downgrey);
    xrv_mamafans.setPullRefreshEnabled(false);
    xrv_mamafans.setLoadingMoreEnabled(true);

    mAdapter = new MamaFansAdapter(this);
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
            .getMamaFans(page)
            .subscribeOn(Schedulers.io())
            .subscribe(new ServiceResponse<MamaFansBean>() {
              @Override public void onNext(MamaFansBean fansBeen) {
                super.onNext(fansBeen);
                if (fansBeen != null) {
                  if (null != fansBeen.getNext()) {
                    mAdapter.update(fansBeen.getFans());
                  } else {
                    Toast.makeText(MamaFansActivity.this, "没有更多了",
                        Toast.LENGTH_SHORT).show();
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

  @Override
  public void onClick(View v) {
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