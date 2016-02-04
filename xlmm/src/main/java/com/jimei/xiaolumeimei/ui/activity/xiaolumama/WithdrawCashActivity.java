package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import butterknife.Bind;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.AgentInfoBean;
import com.jimei.xiaolumeimei.model.MamaInfoModel;
import com.jimei.xiaolumeimei.ui.activity.main.MainActivity;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import rx.schedulers.Schedulers;

/**
 * Created by wulei on 2016/2/4.
 */
public class WithdrawCashActivity extends BaseSwipeBackCompatActivity implements View.OnClickListener{
  String TAG = "WithdrawCashActivity";

  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.btn_jump) Button btn_jump;

  @Override protected void setListener() {
    btn_jump.setOnClickListener(this);
    toolbar.setOnClickListener(this);
  }
  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.activity_withdrawcash;
  }

  @Override protected void initViews() {
    toolbar.setTitle("");
    setSupportActionBar(toolbar);
    toolbar.setNavigationIcon(R.drawable.back);


  }

  @Override protected void initData() {
    MamaInfoModel.getInstance().getAgentInfoBean()
        .subscribeOn(Schedulers.newThread())
        .subscribe(new ServiceResponse<AgentInfoBean>() {
          @Override public void onNext(AgentInfoBean pointBean) {
            JUtils.Log(TAG,"AgentInfoBean="+ pointBean.toString());

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
      case R.id.btn_jump:
        JUtils.Log(TAG,"withdraw cash now");
        //startActivity(new Intent(WithdrawCashActivity.this, MainActivity.class));
        finish();
        break;
      case R.id.toolbar:
        finish();
        break;
    }
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()){
      case R.id.action_history:
        JUtils.Log(TAG,"withdraw cash history entry");
        startActivity(new Intent(this, WithdrawCashHistoryActivity.class));
        break;
      default:
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_withdrawcash,menu);
    return super.onCreateOptionsMenu(menu);
  }
}