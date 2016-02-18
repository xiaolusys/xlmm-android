package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.CarryLogListAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.CarryLogListBean;
import com.jimei.xiaolumeimei.model.MMProductModel;
import com.jimei.xiaolumeimei.widget.DividerItemDecoration;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye(www.itxuye.com) on 2016/02/18.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class MMCarryLogListActivity extends BaseSwipeBackCompatActivity {

  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.tv_count) TextView tvCount;
  @Bind(R.id.carryloglist_xry) XRecyclerView carryloglistXry;
  private CarryLogListAdapter adapter;
  private int page = 2;

  @Override protected void setListener() {

  }

  @Override protected void initData() {
    MMProductModel.getInstance()
        .getCarryLogList("1")
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<CarryLogListBean>() {
          @Override public void onNext(CarryLogListBean carryLogListBean) {
            if (carryLogListBean != null) {

              adapter.update(carryLogListBean.getResults());
            }
          }
        });
  }

  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.activity_carrylog;
  }

  @Override protected void initViews() {
    toolbar.setTitle("");
    setSupportActionBar(toolbar);
    finishBack(toolbar);
    initRecyclerView();
  }

  private void initRecyclerView() {

    carryloglistXry.setLayoutManager(new LinearLayoutManager(this));
    carryloglistXry.addItemDecoration(
        new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
    carryloglistXry.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
    carryloglistXry.setLaodingMoreProgressStyle(ProgressStyle.SemiCircleSpin);
    carryloglistXry.setArrowImageView(R.drawable.iconfont_downgrey);
    carryloglistXry.setPullRefreshEnabled(false);
    carryloglistXry.setLoadingMoreEnabled(true);

    adapter = new CarryLogListAdapter(this);
    carryloglistXry.setAdapter(adapter);

    carryloglistXry.setLoadingListener(new XRecyclerView.LoadingListener() {
      @Override public void onRefresh() {

      }

      @Override public void onLoadMore() {
        loadMoreData(page + "");
        page++;
      }

      private void loadMoreData(String page) {
        MMProductModel.getInstance()
            .getCarryLogList(page)
            .subscribeOn(Schedulers.io())
            .subscribe(new ServiceResponse<CarryLogListBean>() {
              @Override public void onNext(CarryLogListBean carryLogListBean) {
                if (carryLogListBean != null) {
                  if (null != carryLogListBean.getNext()) {
                    adapter.update(carryLogListBean.getResults());
                  } else {
                    Toast.makeText(MMCarryLogListActivity.this, "没有更多了",
                        Toast.LENGTH_SHORT).show();
                    carryloglistXry.post(carryloglistXry::loadMoreComplete);
                  }
                }
              }

              @Override public void onCompleted() {
                super.onCompleted();
                carryloglistXry.post(carryloglistXry::loadMoreComplete);
              }
            });
      }
    });
  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // TODO: add setContentView(...) invocation
    ButterKnife.bind(this);
  }
}
