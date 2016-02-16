package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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
public class MamaInfoActivity extends BaseSwipeBackCompatActivity implements View.OnClickListener{
  String TAG = "MamaInfoActivity";

  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.btn_jump) Button btn_jump;
  @Bind(R.id.imgUser) ImageView imgUser;
  @Bind(R.id.btn_two_dimen) Button btn_two_dimen;
  @Bind(R.id.tv_fansnum) TextView tv_fansnum;
  @Bind(R.id.btn_chooselist)Button btn_chooselist;
  @Bind(R.id.btn_store)Button btn_store;

  AgentInfoBean mamaAgentInfo;

  @Override protected void setListener() {
    btn_jump.setOnClickListener(this);
    toolbar.setOnClickListener(this);
    imgUser.setOnClickListener(this);

    btn_two_dimen.setOnClickListener(this);
    tv_fansnum.setOnClickListener(this);

    btn_chooselist.setOnClickListener(this);
    btn_store.setOnClickListener(this);
  }
  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.activity_mamainfo;
  }

  @Override protected void initViews() {
    toolbar.setTitle("");
    setSupportActionBar(toolbar);
    finishBack(toolbar);

  }

  @Override protected void initData() {
    MamaInfoModel.getInstance().getAgentInfoBean()
        .subscribeOn(Schedulers.newThread())
        .subscribe(new ServiceResponse<AgentInfoBean>() {
          @Override public void onNext(AgentInfoBean pointBean) {
            JUtils.Log(TAG,"AgentInfoBean="+ pointBean.toString());
            mamaAgentInfo = pointBean;
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
    Intent intent;
    switch (v.getId()) {
      case R.id.btn_jump:
        startActivity(new Intent(MamaInfoActivity.this, MainActivity.class));
        finish();
        break;
      case R.id.btn_two_dimen:
        intent = new Intent(MamaInfoActivity.this, TwoDimenCodeActivity.class);
        intent.putExtra("myurl", mamaAgentInfo.getShare_mmcode());
        startActivity(intent);

        break;
      case R.id.imgUser:
        intent = new Intent(MamaInfoActivity.this, WithdrawCashActivity.class);
        intent.putExtra("cash", mamaAgentInfo.getCash());
        startActivity(intent);
        break;
      case R.id.tv_fansnum:
        startActivity(new Intent(MamaInfoActivity.this, MamaFansActivity.class));
        break;

      case R.id.btn_chooselist:
        startActivity(new Intent(MamaInfoActivity.this, MMChooseListActivity.class));
        break;

      case R.id.btn_store:
        startActivity(new Intent(MamaInfoActivity.this, MaMaMyStoreActivity.class));
        break;
    }
  }
}