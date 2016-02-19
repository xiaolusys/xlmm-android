package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import butterknife.Bind;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.model.MamaInfoModel;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.squareup.okhttp.ResponseBody;
import rx.schedulers.Schedulers;

/**
 * Created by wulei on 2016/2/4.
 */
public class WithdrawCashResultActivity extends BaseSwipeBackCompatActivity implements View.OnClickListener{
  String TAG = "WithdrawCashResultActivity";

  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.btn_jump) Button btn_jump;
  @Bind(R.id.img_red_packet1) ImageView img_red_packet1;

  float cash;
  float withdraw_cash_fund = 0;
  boolean click_cash100 = false;
  boolean click_cash200 = false;

  @Override protected void setListener() {
    btn_jump.setOnClickListener(this);

  }
  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.activity_withdrawcash_result;
  }

  @Override protected void initViews() {
    toolbar.setTitle("");
    setSupportActionBar(toolbar);
    finishBack(toolbar);

    cash = getIntent().getExtras().getFloat("cash");
    if(Float.compare(cash , 100) == 0){
      img_red_packet1.setImageResource(R.drawable.img_redpacket100_1);
    }
    else if(Float.compare(cash , 200) == 0){
      img_red_packet1.setImageResource(R.drawable.img_redpacket200_1);
    }

  }

  @Override protected void initData() {

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
        JUtils.Log(TAG,"publish now");
        //startActivity(new Intent(WithdrawCashActivity.this, MainActivity.class));
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