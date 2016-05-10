package com.jimei.xiaolumeimei.ui.activity.trade;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.LogisticsBean;
import com.jimei.xiaolumeimei.model.TradeModel;
import com.jimei.xiaolumeimei.widget.LogImageView;
import com.jimei.xiaolumeimei.widget.LogMsgView;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import java.util.List;
import rx.Subscription;
import rx.schedulers.Schedulers;

public class LogisticsActivity extends BaseSwipeBackCompatActivity {
  private static final java.lang.String TAG = LogisticsActivity.class.getSimpleName();
  @Bind(R.id.tv_company) TextView companyTv;
  @Bind(R.id.tv_order) TextView orderTv;
  @Bind(R.id.log_image_layout) LinearLayout logImageLayout;
  @Bind(R.id.log_msg_layout) LinearLayout logMsgLayout;
  @Bind(R.id.tv_order_last_time) TextView lastTimeTv;
  @Bind(R.id.tv_order_last_state) TextView lastStateTv;
  private String tid;
  private String time;

  @Override protected void setListener() {

  }

  @Override protected void initData() {
    if (tid != "") {
      Subscription subscribe = TradeModel.getInstance()
          .get_logistics(tid)
          .subscribeOn(Schedulers.io())
          .subscribe(new ServiceResponse<LogisticsBean>() {

            @Override public void onNext(LogisticsBean logisticsBean) {
              fillDataToView(logisticsBean);
            }

            @Override public void onCompleted() {
              JUtils.Toast("物流信息更新成功!");
            }

            @Override public void onError(Throwable e) {
            }
          });
      addSubscription(subscribe);
    } else {
      JUtils.Toast("获取物流信息失败!");
    }
  }

  private void fillDataToView(LogisticsBean logisticsBean) {
    if (logisticsBean.getName() != "") {
      companyTv.setText(logisticsBean.getName());
    } else {
      companyTv.setText("暂无物流信息");
    }
    if (logisticsBean.getOrder() != "") {
      orderTv.setText(logisticsBean.getOrder());
    } else {
      orderTv.setText("暂无物流信息");
    }
    if ((logisticsBean.getData() != null) && (logisticsBean.getData().size() != 0)) {
      List<LogisticsBean.Msg> data = logisticsBean.getData();
      for (int i = 0; i < data.size(); i++) {
        if (i == 0) {
          lastStateTv.setText(data.get(0).getContent());
          lastTimeTv.setText(data.get(0).getTime().replace("T", " "));
        } else {
          logImageLayout.addView(new LogImageView(this));
          LogMsgView logMsgView = new LogMsgView(this);
          logMsgView.setMsg(data.get(i).getContent());
          logMsgView.setTime(data.get(i).getTime().replace("T", " "));
          logMsgLayout.addView(logMsgView);
        }
      }
    } else {
      lastStateTv.setText("订单已成功创建!");
      lastTimeTv.setText(time);
    }
  }

  @Override protected void getBundleExtras(Bundle extras) {
    tid = extras.getString("tid");
    time = extras.getString("time");
    if (tid != null) {
      JUtils.Log(TAG, tid);
    }
  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.activity_logistics;
  }

  @Override protected void initViews() {
  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }
}
