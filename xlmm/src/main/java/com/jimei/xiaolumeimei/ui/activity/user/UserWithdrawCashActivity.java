package com.jimei.xiaolumeimei.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.AgentInfoBean;
import com.jimei.xiaolumeimei.entities.UserInfoBean;
import com.jimei.xiaolumeimei.model.MamaInfoModel;
import com.jimei.xiaolumeimei.model.UserNewModel;
import com.jimei.xiaolumeimei.ui.activity.main.MainActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MamaWithdrawCashHistoryActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MamaWithdrawCashResultActivity;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.squareup.okhttp.ResponseBody;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Created by wulei on 2016/2/4.
 */
public class UserWithdrawCashActivity extends BaseSwipeBackCompatActivity
    implements View.OnClickListener {
  String TAG = "UserWithdrawCashActivity";
  static double MAX_WITHDROW_MONEY_EACH_TIME = 8.88;

  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.btn_bindwx) Button btn_bindwx;
  @Bind(R.id.btn_buy) Button btn_buy;
  @Bind(R.id.rl_unbindwx) RelativeLayout rl_unbindwx;
  @Bind(R.id.rl_has_cash) RelativeLayout rl_has_cash;
  @Bind(R.id.rl_not_enough_cash) RelativeLayout rl_not_enough_cash;
  @Bind(R.id.tv_reminder) TextView tv_reminder;

  double money;
  float withdraw_cash_fund = 0;
  boolean click_cash100 = false;
  boolean click_cash200 = false;
  private Subscription subscribe;

  @Override protected void setListener() {
    btn_bindwx.setOnClickListener(this);
    btn_buy.setOnClickListener(this);

  }

  @Override protected void getBundleExtras(Bundle extras) {
    if(null != extras) {
      money = extras.getDouble("money");
      tv_reminder.setText(Math.round(money *100)/100 + "");

    }
    else{
      UserNewModel.getInstance()
          .getProfile()
          .subscribeOn(Schedulers.io())
          .unsafeSubscribe(new Subscriber<UserInfoBean>() {
            @Override public void onCompleted() {

            }

            @Override public void onError(Throwable e) {

            }

            @Override public void onNext(UserInfoBean userNewBean) {
              if (userNewBean != null) {
                if (null != userNewBean.getUserBudget()) {
                  money = userNewBean.getUserBudget().getBudgetCash();
                }
                tv_reminder.setText(Math.round(money *100)/100 + "");

                if(userNewBean.getIsAttentionPublic() == 1) {
                  if (Double.compare(money, MAX_WITHDROW_MONEY_EACH_TIME) > 0) {
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
                  rl_unbindwx.setVisibility(View.VISIBLE);
                  rl_not_enough_cash.setVisibility(View.INVISIBLE);
                  rl_has_cash.setVisibility(View.INVISIBLE);
                }
              }
            }
          });

    }
  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.activity_userwithdrawcash;
  }

  @Override protected void initViews() {
    toolbar.setTitle("");
    setSupportActionBar(toolbar);
    finishBack(toolbar);


  }

  @Override protected void initData() {
    subscribe = MamaInfoModel.getInstance()
        .getAgentInfoBean()
        .subscribeOn(Schedulers.newThread())
        .subscribe(new ServiceResponse<AgentInfoBean>() {
          @Override public void onNext(AgentInfoBean pointBean) {
            JUtils.Log(TAG, "AgentInfoBean=" + pointBean.toString());
          }
        });
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
        //startActivity(new Intent(MamaWithdrawCashActivity.this, MainActivity.class));
        finish();
        break;
      case R.id.btn_buy:
        JUtils.Log(TAG, "buy now");
        startActivity(new Intent(UserWithdrawCashActivity.this, MainActivity.class));
        finish();
        break;

    }
  }


  private void withdraw_cash(float fund) {
    String fund_type = "";

    JUtils.Log(TAG, "withdraw cash =" + fund);
    if ((Float.compare(fund, 100) != 0) && (Float.compare(fund, 200) != 0)) {
      JUtils.Toast("提现金额不够。");
    } else {
      if (Float.compare(fund, 100) == 0) {
        fund_type = "c1";
      } else if (Float.compare(fund, 200) == 0) {
        fund_type = "c2";
      }

      subscribe = MamaInfoModel.getInstance()
          .withdraw_cash(fund_type)
          .subscribeOn(Schedulers.newThread())
          .subscribe(new ServiceResponse<ResponseBody>() {
            @Override public void onNext(ResponseBody resp) {
              JUtils.Log(TAG, "ResponseBody11=" + resp.toString());
              Intent intent =
                  new Intent(UserWithdrawCashActivity.this, MamaWithdrawCashResultActivity
                      .class);
              intent.putExtra("cash", fund);
              startActivity(intent);
            }
          });
    }
  }

  @Override protected void onStop() {
    super.onStop();
    if (subscribe != null && subscribe.isUnsubscribed()) {
      subscribe.unsubscribe();
    }
  }
}