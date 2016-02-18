package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import butterknife.Bind;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.AgentInfoBean;
import com.jimei.xiaolumeimei.model.MamaInfoModel;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.squareup.okhttp.ResponseBody;
import rx.schedulers.Schedulers;

/**
 * Created by wulei on 2016/2/4.
 */
public class WithdrawCashActivity extends BaseSwipeBackCompatActivity implements View.OnClickListener{
  String TAG = "WithdrawCashActivity";

  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.btn_jump) Button btn_jump;
  @Bind(R.id.btn_withdraw) Button btn_withdraw;
  @Bind(R.id.rl_has_cash) RelativeLayout rl_has_cash;
  @Bind(R.id.rl_has_no_cash) RelativeLayout rl_has_no_cash;
  @Bind(R.id.img_red_packet1) ImageView img_red_packet1;
  @Bind(R.id.img_red_packet2) ImageView img_red_packet2;

  float cash;
  float withdraw_cash_fund = 0;
  boolean click_cash100 = false;
  boolean click_cash200 = false;

  @Override protected void setListener() {
    btn_jump.setOnClickListener(this);
    btn_withdraw.setOnClickListener(this);
    img_red_packet1.setOnClickListener(this);
    img_red_packet2.setOnClickListener(this);
  }
  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.activity_withdrawcash;
  }

  @Override protected void initViews() {
    toolbar.setTitle("");
    setSupportActionBar(toolbar);
    finishBack(toolbar);
    cash = getIntent().getExtras().getFloat("cash");
    if(Float.compare(cash , 0) > 0){
      rl_has_no_cash.setVisibility(View.INVISIBLE);
    }
    else{
      rl_has_cash.setVisibility(View.INVISIBLE);
    }

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
      case R.id.btn_withdraw:
        JUtils.Log(TAG,"withdraw cash now");
        withdraw_cash(withdraw_cash_fund);
        break;
      case R.id.img_red_packet1:
        if(click_cash100) {
          click_cash100 = false;
          withdraw_cash_fund = 0;
          img_red_packet1.setImageResource(R.drawable.img_redpacket100_1);
        }
        else{
          click_cash100 = true;
          withdraw_cash_fund = 100;
          img_red_packet1.setImageResource(R.drawable.img_redpacket100_2);
        }
        if(click_cash200){
          img_red_packet2.setImageResource(R.drawable.img_redpacket200_1);
        }

        break;
      case R.id.img_red_packet2:
        if(click_cash200) {
          click_cash200 = false;
          withdraw_cash_fund = 0;
          img_red_packet2.setImageResource(R.drawable.img_redpacket200_1);
        }
        else{
          click_cash200 = true;
          withdraw_cash_fund = 200;
          img_red_packet2.setImageResource(R.drawable.img_redpacket200_2);
        }
        if(click_cash100){
          img_red_packet1.setImageResource(R.drawable.img_redpacket100_1);
        }

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

  private void withdraw_cash(float fund){
    String fund_type = "";
    if((Float.compare(fund, 100) != 0)
      ||(Float.compare(fund, 200) != 0)){
      JUtils.Toast("提现金额不够。");
    }
    else{
      if(Float.compare(fund, 100) == 0) {
        fund_type="c1";
      }
      else if(Float.compare(fund, 200) == 0){
        fund_type="c2";
      }

      MamaInfoModel.getInstance().withdraw_cash(fund_type)
          .subscribeOn(Schedulers.newThread())
          .subscribe(new ServiceResponse<ResponseBody>() {
            @Override public void onNext(ResponseBody resp) {
              JUtils.Log(TAG,"ResponseBody="+ resp.toString());

            }
          });
    }
  }
}