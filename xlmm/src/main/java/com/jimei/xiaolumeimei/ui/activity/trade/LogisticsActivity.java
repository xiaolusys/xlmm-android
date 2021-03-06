package com.jimei.xiaolumeimei.ui.activity.trade;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.jimei.library.utils.JUtils;
import com.jimei.library.widget.LogImageView;
import com.jimei.library.widget.LogMsgView;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.XlmmApp;
import com.jimei.xiaolumeimei.adapter.GoodsListAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.AllOrdersBean;
import com.jimei.xiaolumeimei.entities.LogisticsBean;
import com.jimei.xiaolumeimei.entities.OrderDetailBean;
import com.jimei.xiaolumeimei.service.ServiceResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class LogisticsActivity extends BaseSwipeBackCompatActivity implements View.OnClickListener {
    @Bind(R.id.tv_company)
    TextView companyTv;
    @Bind(R.id.tv_order)
    TextView orderTv;
    @Bind(R.id.log_image_layout)
    LinearLayout logImageLayout;
    @Bind(R.id.log_msg_layout)
    LinearLayout logMsgLayout;
    @Bind(R.id.tv_order_last_time)
    TextView lastTimeTv;
    @Bind(R.id.tv_order_last_state)
    TextView lastStateTv;
    @Bind(R.id.lv)
    ListView mListView;
    @Bind(R.id.layout)
    ScrollView layout;
    @Bind(R.id.order_layout)
    LinearLayout orderLayout;
    private int id;
    private String orderPacketId;
    OrderDetailBean.PackageOrdersBean packageOrdersBean;
    private List<AllOrdersBean.ResultsEntity.OrdersEntity> data;

    @Override
    protected void setListener() {
        orderLayout.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        showIndeterminateProgressDialog(false);
        data = new ArrayList<>();
        if (packageOrdersBean.getLogistics_company() != null) {
            companyTv.setText(packageOrdersBean.getLogistics_company().getName());
            String packetid = packageOrdersBean.getOut_sid();
            String company_code = packageOrdersBean.getLogistics_company().getCode();
            if (packetid != null && company_code != null &&
                !"".equals(packetid) && !"".equals(company_code)) {
                orderTv.setText(packetid);
                orderPacketId = packetid;
                addSubscription(XlmmApp.getTradeInteractor(this)
                    .getLogisticsByPacketId(packetid, company_code, new ServiceResponse<LogisticsBean>() {
                        @Override
                        public void onNext(LogisticsBean logisticsBean) {
                            fillDataToView(logisticsBean);
                        }

                        @Override
                        public void onError(Throwable e) {
                            fillDataToView(null);
                            JUtils.Toast("暂无物流进展!");
                        }
                    }));
            } else {
                orderTv.setText("未揽件");
                fillDataToView(null);
            }
        } else {
            companyTv.setText("小鹿推荐");
            orderTv.setText("未揽件");
            fillDataToView(null);
        }
    }

    @Override
    public View getLoadingView() {
        return layout;
    }

    private void fillDataToView(LogisticsBean logisticsBean) {
        addSubscription(XlmmApp.getTradeInteractor(this)
            .getOrderDetail(id, new ServiceResponse<OrderDetailBean>() {
                @Override
                public void onNext(OrderDetailBean orderDetailBean) {
                    ArrayList<AllOrdersBean.ResultsEntity.OrdersEntity> orders = orderDetailBean.getOrders();
                    for (int i = 0; i < orders.size(); i++) {
                        if (orders.get(i).getPackage_order_id().equals(packageOrdersBean.getId())) {
                            data.add(orders.get(i));
                        }
                    }
                    mListView.setAdapter(new GoodsListAdapter(data, LogisticsActivity.this));
                    OrderDetailActivity.setListViewHeightBasedOnChildren(mListView);
                    hideIndeterminateProgressDialog();
                }
            }));
        if (logisticsBean != null) {
            if ((logisticsBean.getData() != null) && (logisticsBean.getData().size() != 0)) {
                List<LogisticsBean.Msg> data1 = logisticsBean.getData();
                for (int i = 0; i < data1.size(); i++) {
                    if (i == 0) {
                        if (packageOrdersBean.getCancel_time() != null) {
                            lastStateTv.setText("取消订单");
                            lastTimeTv.setText(packageOrdersBean.getCancel_time().replace("T", " "));
                            logImageLayout.addView(new LogImageView(this));
                            LogMsgView logMsgView = new LogMsgView(this);
                            logMsgView.setMsg(data1.get(0).getContent());
                            logMsgView.setTime(data1.get(0).getTime().replace("T", " "));
                            logMsgLayout.addView(logMsgView);
                        } else {
                            lastStateTv.setText(data1.get(0).getContent());
                            lastTimeTv.setText(data1.get(0).getTime().replace("T", " "));
                        }
                    } else {
                        logImageLayout.addView(new LogImageView(this));
                        LogMsgView logMsgView = new LogMsgView(this);
                        logMsgView.setMsg(data1.get(i).getContent());
                        logMsgView.setTime(data1.get(i).getTime().replace("T", " "));
                        logMsgLayout.addView(logMsgView);
                    }
                }
                addFinishTime();
            } else {
                fillStatusView();
            }
        } else {
            fillStatusView();
        }
    }

    private void fillStatusView() {
        if (packageOrdersBean.getCancel_time() != null) {
            lastStateTv.setText("取消订单");
            lastTimeTv.setText(packageOrdersBean.getCancel_time().replace("T", " "));
            addFinishTime();
        } else if (packageOrdersBean.getWeight_time() != null) {
            lastStateTv.setText("产品发货中");
            lastTimeTv.setText(packageOrdersBean.getWeight_time().replace("T", " "));
            addAssignTime();
        } else if (packageOrdersBean.getAssign_time() != null) {
            lastStateTv.setText("仓库质检");
            lastTimeTv.setText(packageOrdersBean.getAssign_time().replace("T", " "));
            addBookTime();
        } else if (packageOrdersBean.getBook_time() != null) {
            lastStateTv.setText("订货中,订单暂时无法取消");
            lastTimeTv.setText(packageOrdersBean.getBook_time().replace("T", " "));
            addPaytime();
        } else if (packageOrdersBean.getPay_time() != null) {
            lastStateTv.setText("支付成功");
            lastTimeTv.setText(packageOrdersBean.getPay_time().replace("T", " "));
        } else {
            lastStateTv.setText("订单已成功创建!");
        }
    }

    private void addFinishTime() {
        if (packageOrdersBean.getWeight_time() != null) {
            logImageLayout.addView(new LogImageView(this));
            LogMsgView logMsgView = new LogMsgView(this);
            logMsgView.setMsg("产品发货中");
            logMsgView.setTime(packageOrdersBean.getWeight_time().replace("T", " "));
            logMsgLayout.addView(logMsgView);
        }
        addAssignTime();
    }

    private void addAssignTime() {
        if (packageOrdersBean.getAssign_time() != null) {
            logImageLayout.addView(new LogImageView(this));
            LogMsgView logMsgView = new LogMsgView(this);
            logMsgView.setMsg("仓库质检");
            logMsgView.setTime(packageOrdersBean.getAssign_time().replace("T", " "));
            logMsgLayout.addView(logMsgView);
        }
        addBookTime();
    }

    private void addBookTime() {
        if (packageOrdersBean.getBook_time() != null) {
            logImageLayout.addView(new LogImageView(this));
            LogMsgView logMsgView = new LogMsgView(this);
            logMsgView.setMsg("订货中,订单暂时无法取消");
            logMsgView.setTime(packageOrdersBean.getBook_time().replace("T", " "));
            logMsgLayout.addView(logMsgView);
        }
        addPaytime();
    }

    private void addPaytime() {
        if (packageOrdersBean.getPay_time() != null) {
            logImageLayout.addView(new LogImageView(this));
            LogMsgView logMsgView = new LogMsgView(this);
            logMsgView.setMsg("支付成功");
            logMsgView.setTime(packageOrdersBean.getPay_time().replace("T", " "));
            logMsgLayout.addView(logMsgView);
        }
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        id = extras.getInt("id");
        packageOrdersBean = ((OrderDetailBean.PackageOrdersBean) extras.getSerializable("packageOrdersBean"));
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_logistics;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.order_layout:
                if (orderPacketId != null && !orderPacketId.equals("")) {
                    JUtils.copyToClipboard(orderPacketId);
                    JUtils.Toast("单号已复制到粘贴板，可以到相应快递官网核对物流信息。");
                }
                break;
        }
    }
}
