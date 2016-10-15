package com.jimei.xiaolumeimei.ui.activity.trade;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.GoodsListAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.AllOrdersBean;
import com.jimei.xiaolumeimei.entities.LogisticsBean;
import com.jimei.xiaolumeimei.entities.OrderDetailBean;
import com.jimei.xiaolumeimei.model.TradeModel;
import com.jimei.xiaolumeimei.widget.LogImageView;
import com.jimei.xiaolumeimei.widget.LogMsgView;
import com.jude.utils.JUtils;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.schedulers.Schedulers;

public class LogisticsActivity extends BaseSwipeBackCompatActivity {
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
    private int id;
    OrderDetailBean.PackageOrdersBean packageOrdersBean;
    private List<AllOrdersBean.ResultsEntity.OrdersEntity> data;

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
                addSubscription(TradeModel.getInstance()
                        .get_logistics_by_packagetid(packetid, company_code)
                        .subscribeOn(Schedulers.io())
                        .subscribe(this::fillDataToView, throwable -> {
                            fillDataToView(null);
                            JUtils.Toast("暂无物流进展!");
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

    private void fillDataToView(LogisticsBean logisticsBean) {
        addSubscription(TradeModel.getInstance()
                .getOrderDetailBean(id)
                .subscribeOn(Schedulers.io())
                .subscribe(orderDetailBean -> {
                    ArrayList<AllOrdersBean.ResultsEntity.OrdersEntity> orders = orderDetailBean.getOrders();
                    for (int i = 0; i < orders.size(); i++) {
                        if (orders.get(i).getPackage_order_id().equals(packageOrdersBean.getId())) {
                            data.add(orders.get(i));
                        }
                    }
                    mListView.setAdapter(new GoodsListAdapter(data, LogisticsActivity.this));
                    OrderDetailActivity.setListViewHeightBasedOnChildren(mListView);
                    hideIndeterminateProgressDialog();
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
        } else if (packageOrdersBean.getFinish_time() != null) {
            lastStateTv.setText("产品发货中");
            lastTimeTv.setText(packageOrdersBean.getFinish_time().replace("T", " "));
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
        if (packageOrdersBean.getFinish_time() != null) {
            logImageLayout.addView(new LogImageView(this));
            LogMsgView logMsgView = new LogMsgView(this);
            logMsgView.setMsg("产品发货中");
            logMsgView.setTime(packageOrdersBean.getFinish_time().replace("T", " "));
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
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(this.getClass().getSimpleName());
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
        MobclickAgent.onPause(this);
    }
}
