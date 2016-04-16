package com.jimei.xiaolumeimei.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.UserInfoBean;
import com.jimei.xiaolumeimei.entities.UserWithdrawResult;
import com.jimei.xiaolumeimei.model.UserModel;
import com.jimei.xiaolumeimei.model.UserNewModel;
import com.jimei.xiaolumeimei.ui.activity.main.MainActivity;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Created by wulei on 2016/2/4.
 */
public class UserWithdrawCashActivity extends BaseSwipeBackCompatActivity
    implements View.OnClickListener {
  String TAG = "UserWithdrawCashActivity";
  static double MAX_WITHDROW_MONEY_EACH_TIME = 8.88;

  @Bind(R.id.btn_bindwx) Button btn_bindwx;
  @Bind(R.id.btn_buy) Button btn_buy;
  @Bind(R.id.rl_unbindwx) RelativeLayout rl_unbindwx;
  @Bind(R.id.rl_has_cash) LinearLayout rl_has_cash;
  @Bind(R.id.rl_not_enough_cash) LinearLayout rl_not_enough_cash;
  @Bind(R.id.tv_reminder) TextView tv_reminder;
  @Bind(R.id.btn_withdraw) Button btn_withdraw;
  @Bind(R.id.img_dec) ImageView img_dec;
  @Bind(R.id.img_inc) ImageView img_inc;
  @Bind(R.id.tv_wxnickname) TextView tv_wxnickname;
  @Bind(R.id.tx_num) TextView tv_num;
  @Bind(R.id.btn_jump) Button jumpBtn;

  double money;
  double withdraw_cash_fund = 0;
  int withdraw_packet_num = 1;
  private Subscription subscribe;

  @Override protected void setListener() {
    btn_bindwx.setOnClickListener(this);
    btn_buy.setOnClickListener(this);
    btn_withdraw.setOnClickListener(this);
    img_dec.setOnClickListener(this);
    img_inc.setOnClickListener(this);
    jumpBtn.setOnClickListener(this);
  }

  @Override protected void getBundleExtras(Bundle extras) {
    if(null != extras) {
      money = extras.getDouble("money");

    }


  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.activity_userwithdrawcash;
  }

  @Override protected void initViews() {
  }

  @Override protected void initData() {

    Subscription subscribe1 =UserNewModel.getInstance()
        .getProfile()
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<UserInfoBean>() {
          @Override public void onNext(UserInfoBean userNewBean) {
            if (userNewBean != null) {
              if (null != userNewBean.getUserBudget()) {
                money = userNewBean.getUserBudget().getBudgetCash();
                JUtils.Log(TAG, "money:"+money);
              }
              tv_reminder.setText((float)(Math.round(money *100))/100 + "元");

              if(userNewBean.getIsAttentionPublic() == 1) {
                btn_bindwx.setVisibility(View.INVISIBLE);
                tv_wxnickname.setText(userNewBean.getNick());
                if (Double.compare(money, MAX_WITHDROW_MONEY_EACH_TIME) >= 0) {
                  rl_unbindwx.setVisibility(View.INVISIBLE);
                  rl_not_enough_cash.setVisibility(View.INVISIBLE);
                  rl_has_cash.setVisibility(View.VISIBLE);
                } else {
                  rl_unbindwx.setVisibility(View.INVISIBLE);
                  rl_not_enough_cash.setVisibility(View.VISIBLE);
                  rl_has_cash.setVisibility(View.INVISIBLE);
                }
              }
              else {
                JUtils.Log(TAG,"unbind wx");
                btn_bindwx.setVisibility(View.VISIBLE);
                rl_unbindwx.setVisibility(View.VISIBLE);
                rl_not_enough_cash.setVisibility(View.INVISIBLE);
                rl_has_cash.setVisibility(View.INVISIBLE);
              }
            }
          }
        });
    addSubscription(subscribe1);

  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }

  @Override public void onClick(View v) {
    switch (v.getId()) {
      case R.id.btn_bindwx:
        JUtils.Log(TAG, "bind wx now");
        startActivity(new Intent(UserWithdrawCashActivity.this, WxPubTwoDimenCodeActivity.class));
        finish();
        break;
      case R.id.btn_buy:
        JUtils.Log(TAG, "buy now");
        startActivity(new Intent(UserWithdrawCashActivity.this, MainActivity.class));
        finish();
        break;
      case R.id.btn_withdraw:
        JUtils.Log(TAG, "withdraw now");
        withdraw_cash_fund = withdraw_packet_num * MAX_WITHDROW_MONEY_EACH_TIME;
        withdraw_cash((float)withdraw_cash_fund);
        break;
      case R.id.img_inc:
        JUtils.Log(TAG, "inc now");
        if(Math.round(money / MAX_WITHDROW_MONEY_EACH_TIME) > withdraw_packet_num) {
          withdraw_packet_num++;
          tv_num.setText(""+withdraw_packet_num);
        }
        break;
      case R.id.img_dec:
        JUtils.Log(TAG, "dec now");
        if(withdraw_packet_num > 1){
          withdraw_packet_num--;
          tv_num.setText(""+withdraw_packet_num);
        }
        break;
      case R.id.btn_jump:
        JUtils.Log(TAG, "buy now");
        startActivity(new Intent(UserWithdrawCashActivity.this, MainActivity.class));
        finish();
        break;
    }
  }


  private void withdraw_cash(float fund) {

    JUtils.Log(TAG, "withdraw cash =" + fund);
      subscribe = UserModel.getInstance()
          .user_withdraw_cash(Float.toString(fund))
          .subscribeOn(Schedulers.io())
          .subscribe(new ServiceResponse<UserWithdrawResult>() {
            @Override public void onNext(UserWithdrawResult resp) {
              switch (resp.getCode()){
                case 0:
                  JUtils.Log(TAG, "SUCCESS");
                  JUtils.Toast(resp.getMessage());
                  finish();
                  break;
                default:
                  JUtils.Log(TAG, "failed:"+resp.getCode());
                  JUtils.Toast(resp.getMessage());
                  break;

              }

            }
          });
      addSubscription(subscribe);
  }

  @Override protected void onStop() {
    super.onStop();
    if (subscribe != null && subscribe.isUnsubscribed()) {
      subscribe.unsubscribe();
    }
  }
}