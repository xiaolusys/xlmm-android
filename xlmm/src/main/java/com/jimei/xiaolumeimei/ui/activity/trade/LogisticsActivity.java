package com.jimei.xiaolumeimei.ui.activity.trade;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.Bind;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.GoodsListAdapter;
import com.jimei.xiaolumeimei.adapter.GoodsListAdapter2;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.AllOrdersBean;
import com.jimei.xiaolumeimei.entities.LogisticsBean;
import com.jimei.xiaolumeimei.entities.OrderDetailBean;
import com.jimei.xiaolumeimei.entities.PackageBean;
import com.jimei.xiaolumeimei.model.TradeModel;
import com.jimei.xiaolumeimei.widget.LogImageView;
import com.jimei.xiaolumeimei.widget.LogMsgView;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
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
    @Bind(R.id.ll_container)
    LinearLayout containerLayout;
    @Bind(R.id.tv_number)
    TextView numTv;

    private ArrayList<PackageBean> packageBeanList;
    private String packetid = "";
    private String company_code = "";
    private String time = "";
    private String tid = "";
    private String stateStr = "";
    private String key = "";
    private int referal_trade_id;

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
        if (!"".equals(packetid) && !"".equals(company_code)) {
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
                        }
                    });
            addSubscription(subscribe);
        } else if (!"".equals(tid)) {
            Subscription subscribe = TradeModel.getInstance()
                    .get_logistics(tid)
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
                            JUtils.Toast(e.getMessage());
                        }
                    });
            addSubscription(subscribe);
        } else {
            JUtils.Toast("获取物流信息失败,请联系小鹿美美客服查询!");
        }
        Subscription subscription = TradeModel.getInstance()
                .getOrderDetailBean(referal_trade_id)
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<OrderDetailBean>() {
                    @Override
                    public void onNext(OrderDetailBean orderDetailBean) {
                        if (orderDetailBean.getLogistics_company() != null) {
                            companyTv.setText(orderDetailBean.getLogistics_company().getName());
                            companyTv.setTextColor(getResources().getColor(R.color.colorAccent));
                        } else {
                            companyTv.setText("小鹿推荐");
                        }

                    }
                });
        addSubscription(subscription);
    }

    private void fillDataToView(LogisticsBean logisticsBean) {
        if (logisticsBean.getOrder() != "" && logisticsBean.getOrder() != null) {
            orderTv.setText(logisticsBean.getOrder());
        } else {
            orderTv.setTextColor(getResources().getColor(R.color.colorAccent));
            orderTv.setText(stateStr);
            numTv.setText("包裹状态");
        }

        if (packageBeanList.size() != 0) {
            List<PackageBean> data = new ArrayList<>();
            for (PackageBean packageBean : packageBeanList) {
                if (key.equals(packageBean.getPackage_group_key())) {
                    data.add(packageBean);
                }
            }
            mListView.setAdapter(new GoodsListAdapter(data, this));
            OrderDetailActivity.setListViewHeightBasedOnChildren(mListView);
        } else {
            Subscription subscription = TradeModel.getInstance()
                    .getOrderDetailBean(referal_trade_id)
                    .subscribeOn(Schedulers.io())
                    .subscribe(new ServiceResponse<OrderDetailBean>() {
                        @Override
                        public void onNext(OrderDetailBean orderDetailBean) {
                            ArrayList<AllOrdersBean.ResultsEntity.OrdersEntity> orders = orderDetailBean.getOrders();
                            mListView.setAdapter(new GoodsListAdapter2(orders, getApplicationContext()));
                            OrderDetailActivity.setListViewHeightBasedOnChildren(mListView);

                        }
                    });
            addSubscription(subscription);
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

    @Override
    protected void getBundleExtras(Bundle extras) {
        packetid = extras.getString("packetid");
        company_code = extras.getString("company_code");
        tid = extras.getString("tid");
        time = extras.getString("time");
        stateStr = extras.getString("state");
        packageBeanList = (ArrayList<PackageBean>) extras.getSerializable("list");
        referal_trade_id = extras.getInt("id");
        key = extras.getString("key");
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_logistics;
    }

    @Override
    protected void initViews() {
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
