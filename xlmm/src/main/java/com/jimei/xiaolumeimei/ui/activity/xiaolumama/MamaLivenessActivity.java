package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.Bind;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.MamaLivenessAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.MamaLivenessBean;
import com.jimei.xiaolumeimei.model.MamaInfoModel;
import com.jimei.xiaolumeimei.widget.DividerItemDecoration;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import java.util.List;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Created by wulei on 2016/2/4.
 */
public class MamaLivenessActivity extends BaseSwipeBackCompatActivity
    implements View.OnClickListener {
  String TAG = "MamaLivenessActivity";

  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.lv_liveness) XRecyclerView lv_liveness;
  @Bind(R.id.tv_liveness)  TextView tv_liveness;

  private int page = 2;
  private MamaLivenessAdapter mAdapter;
  private Subscription subscribe;
  int liveness = 0;

  @Override protected void setListener() {
    toolbar.setOnClickListener(this);
  }

  @Override protected void getBundleExtras(Bundle extras) {

    if(null != extras){
      liveness = extras.getInt("liveness");

    }
  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.activity_mamaliveness;
  }

  @Override protected void initViews() {
    toolbar.setTitle("");
    setSupportActionBar(toolbar);
    finishBack(toolbar);
    initRecyclerView();

    tv_liveness.setText(liveness + "");
  }

  private void initRecyclerView() {
    lv_liveness.setLayoutManager(new LinearLayoutManager(this));
    lv_liveness.addItemDecoration(
        new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
    lv_liveness.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
    lv_liveness.setLaodingMoreProgressStyle(ProgressStyle.SemiCircleSpin);
    lv_liveness.setArrowImageView(R.drawable.iconfont_downgrey);
    lv_liveness.setPullRefreshEnabled(false);
    lv_liveness.setLoadingMoreEnabled(true);

    mAdapter = new MamaLivenessAdapter(this);
    lv_liveness.setAdapter(mAdapter);

    lv_liveness.setLoadingListener(new XRecyclerView.LoadingListener() {
      @Override public void onRefresh() {

      }

      @Override public void onLoadMore() {
        loadMoreData(page + "");
        page++;
      }

      private void loadMoreData(String page) {
        subscribe = MamaInfoModel.getInstance()
            .getMamaLiveness(page)
            .subscribeOn(Schedulers.io())
            .subscribe(new ServiceResponse<MamaLivenessBean>() {
              @Override public void onNext(MamaLivenessBean livenessBean) {
                super.onNext(livenessBean);
                if (livenessBean != null) {

                  mAdapter.update(livenessBean.getResults());
                  if (null == livenessBean.getNext()) {
                    Toast.makeText(MamaLivenessActivity.this, "没有更多了", Toast.LENGTH_SHORT)
                        .show();
                    lv_liveness.post(lv_liveness::loadMoreComplete);
                    lv_liveness.setLoadingMoreEnabled(false);
                  }
                }
              }

              @Override public void onCompleted() {
                super.onCompleted();
                lv_liveness.post(lv_liveness::loadMoreComplete);
              }
            });
      }
    });
  }

  @Override protected void initData() {
    showIndeterminateProgressDialog(false);
    subscribe = MamaInfoModel.getInstance()
        .getMamaLiveness("1")
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<MamaLivenessBean>() {
          @Override public void onNext(MamaLivenessBean pointBean) {
            JUtils.Log(TAG, "MamaLivenessBean=" + pointBean.toString());
            List<MamaLivenessBean.ResultsEntity> results = pointBean.getResults();

            if (0 == results.size()) {
              JUtils.Log(TAG, "results.size()=0");
            } else {
              mAdapter.update(results);
            }

            if (null == pointBean.getNext()) {
              Toast.makeText(MamaLivenessActivity.this, "没有更多了", Toast.LENGTH_SHORT)
                      .show();
              lv_liveness.post(lv_liveness::loadMoreComplete);
              lv_liveness.setLoadingMoreEnabled(false);
            }
          }

          @Override public void onCompleted() {
            super.onCompleted();
            hideIndeterminateProgressDialog();
          }
        });
    addSubscription(subscribe);
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
  }
}