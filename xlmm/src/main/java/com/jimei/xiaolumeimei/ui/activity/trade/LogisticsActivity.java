package com.jimei.xiaolumeimei.ui.activity.trade;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.Bind;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.GoodsListAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.LogisticsBean;
import com.jimei.xiaolumeimei.entities.OrderDetailBean;
import com.jimei.xiaolumeimei.entities.PackageBean;
import com.jimei.xiaolumeimei.model.TradeModel;
import com.jimei.xiaolumeimei.widget.LogImageView;
import com.jimei.xiaolumeimei.widget.LogMsgView;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.umeng.analytics.MobclickAgent;

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
    List<PackageBean> data;
    private String packetid = "";
    private String company_code = "";
    private String stateStr = "";
    private String key = "";
    private String tid = "";
    private int referal_trade_id;

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
        showIndeterminateProgressDialog(false);
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
                            fillDataToView(null);
                            JUtils.Toast("更新失败!");
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
            fillDataToView(null);
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
        data = new ArrayList<>();
        for (PackageBean packageBean : packageBeanList) {
            if (key.equals(packageBean.getPackage_group_key())) {
                data.add(packageBean);
            }
        }
        mListView.setAdapter(new GoodsListAdapter(data, this));
        OrderDetailActivity.setListViewHeightBasedOnChildren(mListView);
        if (logisticsBean != null) {
            if (logisticsBean.getOrder() != "" && logisticsBean.getOrder() != null) {
                orderTv.setText(logisticsBean.getOrder());
            } else {
                orderTv.setTextColor(getResources().getColor(R.color.colorAccent));
                orderTv.setText(stateStr);
                numTv.setText("包裹状态");
            }
            if ((logisticsBean.getData() != null) && (logisticsBean.getData().size() != 0)) {
                List<LogisticsBean.Msg> data1 = logisticsBean.getData();
                for (int i = 0; i < data1.size(); i++) {
                    if (i == 0) {
                        if (data.size() > 0) {
                            if (data.get(0).getCancel_time() != null) {
                                lastStateTv.setText("取消订单");
                                lastTimeTv.setText(data.get(0).getCancel_time().replace("T", " "));
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
                if (data.size() > 0) {
                    if (data.get(0).getFinish_time() != null) {
                        logImageLayout.addView(new LogImageView(this));
                        LogMsgView logMsgView = new LogMsgView(this);
                        logMsgView.setMsg("产品发货中");
                        logMsgView.setTime(data.get(0).getFinish_time().replace("T", " "));
                        logMsgLayout.addView(logMsgView);
                    }
                    if (data.get(0).getAssign_time() != null) {
                        logImageLayout.addView(new LogImageView(this));
                        LogMsgView logMsgView = new LogMsgView(this);
                        logMsgView.setMsg("仓库质检");
                        logMsgView.setTime(data.get(0).getAssign_time().replace("T", " "));
                        logMsgLayout.addView(logMsgView);
                    }
                    if (data.get(0).getBook_time() != null) {
                        logImageLayout.addView(new LogImageView(this));
                        LogMsgView logMsgView = new LogMsgView(this);
                        logMsgView.setMsg("订货中,订单暂时无法取消");
                        logMsgView.setTime(data.get(0).getBook_time().replace("T", " "));
                        logMsgLayout.addView(logMsgView);
                    }
                    if (data.get(0).getPay_time() != null) {
                        logImageLayout.addView(new LogImageView(this));
                        LogMsgView logMsgView = new LogMsgView(this);
                        logMsgView.setMsg("支付成功");
                        logMsgView.setTime(data.get(0).getPay_time().replace("T", " "));
                        logMsgLayout.addView(logMsgView);
                    }
                }
            } else {
                fillStatusView();
            }
        } else {
            fillStatusView();
        }
        hideIndeterminateProgressDialog();
    }

    private void fillStatusView() {
        if (data.size() > 0) {
            if (data.get(0).getCancel_time() != null) {
                lastStateTv.setText("取消订单");
                lastTimeTv.setText(data.get(0).getCancel_time().replace("T", " "));
                addFinishTime();
            } else if (data.get(0).getFinish_time() != null) {
                lastStateTv.setText("产品发货中");
                lastTimeTv.setText(data.get(0).getFinish_time().replace("T", " "));
                addAssignTime();
            } else if (data.get(0).getAssign_time() != null) {
                lastStateTv.setText("仓库质检");
                lastTimeTv.setText(data.get(0).getAssign_time().replace("T", " "));
                addBookTime();
            } else if (data.get(0).getBook_time() != null) {
                lastStateTv.setText("订货中,订单暂时无法取消");
                lastTimeTv.setText(data.get(0).getBook_time().replace("T", " "));
                addPaytime();
            } else if (data.get(0).getPay_time() != null) {
                lastStateTv.setText("支付成功");
                lastTimeTv.setText(data.get(0).getPay_time().replace("T", " "));
            } else {
                lastStateTv.setText("订单已成功创建!");
            }
        } else {
            lastStateTv.setText("订单已成功创建!");
        }
    }

    private void addFinishTime() {
        if (data.get(0).getFinish_time() != null) {
            logImageLayout.addView(new LogImageView(this));
            LogMsgView logMsgView = new LogMsgView(this);
            logMsgView.setMsg("产品发货");
            logMsgView.setTime(data.get(0).getFinish_time().replace("T", " "));
            logMsgLayout.addView(logMsgView);
        }
        addAssignTime();
    }

    private void addAssignTime() {
        if (data.get(0).getAssign_time() != null) {
            logImageLayout.addView(new LogImageView(this));
            LogMsgView logMsgView = new LogMsgView(this);
            logMsgView.setMsg("仓库质检");
            logMsgView.setTime(data.get(0).getAssign_time().replace("T", " "));
            logMsgLayout.addView(logMsgView);
        }
        addBookTime();
    }

    private void addBookTime() {
        if (data.get(0).getBook_time() != null) {
            logImageLayout.addView(new LogImageView(this));
            LogMsgView logMsgView = new LogMsgView(this);
            logMsgView.setMsg("订货中,订单暂时无法取消");
            logMsgView.setTime(data.get(0).getBook_time().replace("T", " "));
            logMsgLayout.addView(logMsgView);
        }
        addPaytime();
    }

    private void addPaytime() {
        if (data.get(0).getPay_time() != null) {
            logImageLayout.addView(new LogImageView(this));
            LogMsgView logMsgView = new LogMsgView(this);
            logMsgView.setMsg("支付成功");
            logMsgView.setTime(data.get(0).getPay_time().replace("T", " "));
            logMsgLayout.addView(logMsgView);
        }
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        packetid = extras.getString("packetid");
        company_code = extras.getString("company_code");
        stateStr = extras.getString("state");
        packageBeanList = (ArrayList<PackageBean>) extras.getSerializable("list");
        referal_trade_id = extras.getInt("id");
        key = extras.getString("key");
        tid = extras.getString("tid");
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
