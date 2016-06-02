package com.jimei.xiaolumeimei.ui.activity.trade;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.Bind;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.GoodsListAdapter;
import com.jimei.xiaolumeimei.adapter.GoodsListAdapter2;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.AllOrdersBean;
import com.jimei.xiaolumeimei.entities.LogisticCompany;
import com.jimei.xiaolumeimei.entities.LogisticsBean;
import com.jimei.xiaolumeimei.entities.OrderDetailBean;
import com.jimei.xiaolumeimei.entities.PackageBean;
import com.jimei.xiaolumeimei.entities.ResultBean;
import com.jimei.xiaolumeimei.model.ActivityModel;
import com.jimei.xiaolumeimei.model.TradeModel;
import com.jimei.xiaolumeimei.widget.LogImageView;
import com.jimei.xiaolumeimei.widget.LogMsgView;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
import rx.schedulers.Schedulers;

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
    @Bind(R.id.right)
    ImageView rightImage;
    @Bind(R.id.company_layout)
    LinearLayout layout;
    @Bind(R.id.ll_container)
    LinearLayout containerLayout;

    private ArrayList<PackageBean> packageBeanList;
    private String packetid = "";
    private String company_code = "";
    private String time = "";
    private String tid = "";
    private String stateStr = "";
    private String key = "";
    private int referal_trade_id;
    private int address_id;
    private Dialog dialog;
    private String logisticsCompanyName;

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

    }

    private void fillDataToView(LogisticsBean logisticsBean) {
        if (logisticsBean.getName() != "" && logisticsBean.getName() != null) {
            companyTv.setText(logisticsBean.getName());
            companyTv.setTextColor(getResources().getColor(R.color.colorAccent));
        } else {
            companyTv.setText(logisticsCompanyName);
        }

        if ("已付款".equals(stateStr)) {
            rightImage.setVisibility(View.VISIBLE);
            layout.setOnClickListener(this);
        } else {
            rightImage.setVisibility(View.GONE);
        }

        if (logisticsBean.getOrder() != "" && logisticsBean.getOrder() != null) {
            orderTv.setText(logisticsBean.getOrder());
        } else {
            orderTv.setTextColor(getResources().getColor(R.color.colorAccent));
            orderTv.setText(stateStr);
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
        address_id = extras.getInt("address_id");
        logisticsCompanyName = extras.getString("logisticsCompanyName");
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_logistics;
    }

    @Override
    protected void initViews() {
        View view = getLayoutInflater().inflate(R.layout.pop_layout, null);
        dialog = new Dialog(this, R.style.dialog_style);
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(wlp);
        window.setWindowAnimations(R.style.dialog_anim);
        View closeIv = view.findViewById(R.id.close_iv);
        ListView listView = (ListView) view.findViewById(R.id.lv_logistics_company);

        closeIv.setOnClickListener(this);
        Subscription subscribe = ActivityModel.getInstance()
                .getLogisticCompany(referal_trade_id)
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<List<LogisticCompany>>() {
                    @Override
                    public void onNext(List<LogisticCompany> logisticCompanies) {
                        CompanyAdapter adapter = new CompanyAdapter(logisticCompanies, getApplicationContext());
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                String code = logisticCompanies.get(position).getCode();
                                ActivityModel.getInstance()
                                        .changeLogisticCompany(address_id, referal_trade_id + "", code)
                                        .subscribeOn(Schedulers.io())
                                        .subscribe(new ServiceResponse<ResultBean>() {
                                            @Override
                                            public void onNext(ResultBean resultBean) {
                                                switch (resultBean.getCode()) {
                                                    case 0:
                                                        companyTv.setText(logisticCompanies.get(position).getName());
                                                        break;
                                                }
                                                JUtils.Toast(resultBean.getInfo());
                                                changeDialogWindowState();
                                            }

                                            @Override
                                            public void onError(Throwable e) {
                                                JUtils.Toast(e.getMessage());
                                                changeDialogWindowState();
                                            }
                                        });
                            }
                        });
                    }
                });
        addSubscription(subscribe);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.company_layout:
                changeDialogWindowState();
                break;
            case R.id.close_iv:
                changeDialogWindowState();
                break;
        }
    }

    private void changeDialogWindowState() {
        if (dialog.isShowing()) {
            dialog.dismiss();
        } else {
            dialog.show();
        }
    }

    public class CompanyAdapter extends BaseAdapter {
        private List<LogisticCompany> logisticCompanies;
        private Context context;

        public CompanyAdapter(List<LogisticCompany> logisticCompanies, Context context) {
            this.logisticCompanies = logisticCompanies;
            this.context = context;
        }

        @Override
        public int getCount() {
            return logisticCompanies.size();
        }

        @Override
        public Object getItem(int position) {
            return logisticCompanies.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_logistics, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.nameTv.setText(logisticCompanies.get(position).getName());
            return convertView;
        }

        private class ViewHolder {
            TextView nameTv;

            public ViewHolder(View itemView) {
                nameTv = ((TextView) itemView.findViewById(R.id.name));

            }
        }
    }
}
