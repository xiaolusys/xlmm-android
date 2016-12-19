package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.os.Bundle;
import android.view.View;

import com.jimei.library.utils.JUtils;
import com.jimei.library.widget.LogImageView;
import com.jimei.library.widget.LogMsgView;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseMVVMActivity;
import com.jimei.xiaolumeimei.databinding.ActivityOrderLogisticBinding;
import com.jimei.xiaolumeimei.entities.LogisticsBean;
import com.jimei.xiaolumeimei.model.TradeModel;

import java.util.List;

public class OrderLogisticActivity extends BaseMVVMActivity<ActivityOrderLogisticBinding> {
    private String company_code;
    private String packetid;

    @Override
    protected void initData() {
        b.tvCompany.setText(company_code);
        b.tvOrder.setText(packetid);
        if (packetid != null && company_code != null &&
                !"".equals(packetid) && !"".equals(company_code)) {
            showIndeterminateProgressDialog(false);
            addSubscription(TradeModel.getInstance()
                    .get_logistics_by_packagetid(packetid, company_code)
                    .subscribe(this::fillDataToView,
                            e -> {
                                JUtils.Toast("物流查询失败，请到相应官网查询！");
                                hideIndeterminateProgressDialog();
                            }
                    ));
        } else {
            b.tvOrderLastTime.setText("暂无物流信息");
            b.start.setVisibility(View.VISIBLE);
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
                    b.start.setVisibility(View.VISIBLE);
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
        JUtils.Toast("物流信息更新成功!");
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
    public View getLoadingView() {
        return b.layout;
    }
}
