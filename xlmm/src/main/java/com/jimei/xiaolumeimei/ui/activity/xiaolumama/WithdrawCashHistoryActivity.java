package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import butterknife.Bind;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.WithdrawCashHisAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.AgentInfoBean;
import com.jimei.xiaolumeimei.entities.WithdrawCashHisBean;
import com.jimei.xiaolumeimei.model.MamaInfoModel;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import java.util.List;
import rx.schedulers.Schedulers;

/**
 * Created by wulei on 2016/2/4.
 */
public class WithdrawCashHistoryActivity extends BaseSwipeBackCompatActivity implements View.OnClickListener{
  String TAG = "WithdrawCashHistoryActivity";

  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.lv_withdrawcash_his) ListView lv_withdrawcash_his;

  private WithdrawCashHisAdapter mHisAdapter;

  @Override protected void setListener() {
    toolbar.setOnClickListener(this);
  }
  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.activity_withdrawcash_history;
  }

  @Override protected void initViews() {
    toolbar.setTitle("");
    setSupportActionBar(toolbar);
    toolbar.setNavigationIcon(R.drawable.back);

    mHisAdapter = new WithdrawCashHisAdapter(this);
    lv_withdrawcash_his.setAdapter(mHisAdapter);
  }

  @Override protected void initData() {
    MamaInfoModel.getInstance().getWithdrawCashHis()
        .subscribeOn(Schedulers.newThread())
        .subscribe(new ServiceResponse<WithdrawCashHisBean>() {
          @Override public void onNext(WithdrawCashHisBean pointBean) {
            JUtils.Log(TAG,"WithdrawCashHisBean="+ pointBean.toString());
            List<WithdrawCashHisBean.WithdrawCashRecord> results = pointBean.getResults();

            if (0 == results.size()) {
              JUtils.Log(TAG, "results.size()=0");

            } else {
              mHisAdapter.update(results);
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

      case R.id.toolbar:
        finish();
        break;
    }
  }


}