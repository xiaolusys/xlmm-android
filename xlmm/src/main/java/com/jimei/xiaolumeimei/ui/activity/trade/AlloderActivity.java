package com.jimei.xiaolumeimei.ui.activity.trade;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import butterknife.Bind;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.AllOderAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.AllOdersBean;
import com.jimei.xiaolumeimei.model.TradeModel;
import com.jimei.xiaolumeimei.widget.SpaceItemDecoration;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import java.util.List;
import rx.schedulers.Schedulers;

/**
 * Created by 优尼世界 on 15/12/29.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class AlloderActivity extends BaseSwipeBackCompatActivity {

  @Bind(R.id.alloder_xry) RecyclerView xRecyclerView;
  @Bind(R.id.toolbar) Toolbar toolbar;
  TradeModel model = new TradeModel();
  private AllOderAdapter mAllOderAdapter;

  @Override protected void setListener() {

  }

  @Override protected void initData() {
    model.getAlloderBean()
        .subscribeOn(Schedulers.newThread())
        .subscribe(new ServiceResponse<AllOdersBean>() {
          @Override public void onNext(AllOdersBean allOdersBean) {
            List<AllOdersBean.ResultsEntity> results = allOdersBean.getResults();

            mAllOderAdapter.update(results);

            Log.i("itxuye", allOdersBean.toString());
          }
        });
  }

  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.alloders_activity;
  }

  @Override protected void initViews() {

    xRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    xRecyclerView.addItemDecoration(new SpaceItemDecoration(10));

    //xRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
    //xRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.SemiCircleSpin);
    //xRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);

    mAllOderAdapter = new AllOderAdapter(this);
    xRecyclerView.setAdapter(mAllOderAdapter);
  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }
}
