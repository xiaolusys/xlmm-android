package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import butterknife.Bind;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.AllowanceAdapter;
import com.jimei.xiaolumeimei.adapter.WithdrawCashHisAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.AllowanceBean;
import com.jimei.xiaolumeimei.entities.WithdrawCashHisBean;
import com.jimei.xiaolumeimei.model.MamaInfoModel;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import java.util.List;
import rx.schedulers.Schedulers;

/**
 * Created by wulei on 2016/2/4.
 */
public class ShareAllowanceActivity extends BaseSwipeBackCompatActivity implements View.OnClickListener{
  String TAG = "ShareAllowanceActivity";

  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.lv_allowance) XRecyclerView lv_allowance;

  private AllowanceAdapter mAdapter;

  @Override protected void setListener() {
    toolbar.setOnClickListener(this);
  }
  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.activity_shareallowance;
  }

  @Override protected void initViews() {
    toolbar.setTitle("");
    setSupportActionBar(toolbar);
    finishBack(toolbar);

    mAdapter = new AllowanceAdapter(this);
    lv_allowance.setAdapter(mAdapter);
  }

  @Override protected void initData() {
    MamaInfoModel.getInstance().getAllowance()
        .subscribeOn(Schedulers.newThread())
        .subscribe(new ServiceResponse<AllowanceBean>() {
          @Override public void onNext(AllowanceBean pointBean) {
            JUtils.Log(TAG,"AllowanceBean="+ pointBean.toString());
            List<AllowanceBean.AllowanceEntity> results = pointBean.getAllowances();

            if (0 == results.size()) {
              JUtils.Log(TAG, "results.size()=0");

            } else {
              mAdapter.update(results);
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


}