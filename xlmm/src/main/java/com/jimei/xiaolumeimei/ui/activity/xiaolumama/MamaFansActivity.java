package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import butterknife.Bind;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.MamaFansAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.MamaFansBean;
import com.jimei.xiaolumeimei.model.MamaInfoModel;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import java.util.List;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Created by wulei on 2016/2/4.
 */
public class MamaFansActivity extends BaseSwipeBackCompatActivity implements View.OnClickListener{
  String TAG = "MamaFansActivity";

  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.lv_mamafans) ListView lv_fans;

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
    mAdapter = new MamaFansAdapter(this);
    lv_fans.setAdapter(mAdapter);
  }

  @Override protected void initData() {
     subscribe = MamaInfoModel.getInstance()
        .getMamaFans()
        .subscribeOn(Schedulers.newThread())
        .subscribe(new ServiceResponse<List<MamaFansBean>>() {
          @Override public void onNext(List<MamaFansBean> fansBeen) {
            JUtils.Log(TAG, "size =" + fansBeen.size());

            if (0 == fansBeen.size()) {
              JUtils.Log(TAG, "results.size()=0");
            } else {
              mAdapter.update(fansBeen);
            }
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