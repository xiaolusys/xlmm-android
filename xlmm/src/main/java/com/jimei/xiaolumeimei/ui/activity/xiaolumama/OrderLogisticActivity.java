package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.os.Bundle;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseMVVMActivity;
import com.jimei.xiaolumeimei.databinding.ActivityOrderLogisticBinding;
import com.jimei.xiaolumeimei.entities.LogisticsBean;
import com.jimei.xiaolumeimei.model.TradeModel;
import com.jimei.xiaolumeimei.widget.LogImageView;
import com.jimei.xiaolumeimei.widget.LogMsgView;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;

import java.util.List;

import rx.Subscription;
import rx.schedulers.Schedulers;

public class OrderLogisticActivity extends BaseMVVMActivity<ActivityOrderLogisticBinding> {
    private String company_code;
    private String packetid;

    @Override
    protected void initView() {
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void initData() {
        if (!"".equals(packetid) && !"".equals(company_code)) {
            showIndeterminateProgressDialog(false);
            Subscription subscribe = TradeModel.getInstance()
                    .get_logistics_by_packagetid(packetid, company_code)
                    .subscribeOn(Schedulers.io())
                    .subscribe(new ServiceResponse<LogisticsBean>() {

                        @Override
                        public void onNext(LogisticsBean logisticsBean) {
                            fillDataToView(logisticsBean);
                        }

                        @Override
                        public void onCompleted() {
                            JUtils.Toast("物流信息更新成功!");
                        }

                        @Override
                        public void onError(Throwable e) {
                            JUtils.Toast("更新失败!");
                            hideIndeterminateProgressDialog();
                        }
                    });
            addSubscription(subscribe);
        }else {
            b.tvOrderLastTime.setText("暂无物流信息");
        }
    }

    private void fillDataToView(LogisticsBean logisticsBean) {
        if (logisticsBean.getName() != null && logisticsBean.getOrder() != null) {
            b.tvCompany.setText(logisticsBean.getName());
            b.tvOrder.setText(logisticsBean.getOrder());
        }
        if ((logisticsBean.getData() != null) && (logisticsBean.getData().size() != 0)) {
            List<LogisticsBean.Msg> data1 = logisticsBean.getData();
            for (int i = 0; i < data1.size(); i++) {
                if (i == 0) {
                    b.tvOrderLastState.setText(data1.get(0).getContent());
                    b.tvOrderLastTime.setText(data1.get(0).getTime().replace("T", " "));
                } else {
                    b.logImageLayout.addView(new LogImageView(this));
                    LogMsgView logMsgView = new LogMsgView(this);
                    logMsgView.setMsg(data1.get(i).getContent());
                    logMsgView.setTime(data1.get(i).getTime().replace("T", " "));
                    b.logMsgLayout.addView(logMsgView);
                }
            }
        }
        hideIndeterminateProgressDialog();
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        company_code = extras.getString("company_code");
        packetid = extras.getString("packetid");
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_order_logistic;
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }
}
