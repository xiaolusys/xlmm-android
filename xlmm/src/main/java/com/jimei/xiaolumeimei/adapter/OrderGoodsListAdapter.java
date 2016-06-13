package com.jimei.xiaolumeimei.adapter;

/**
 * Created by wulei on 15-12-17.
 * 商品订单数据适配
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.entities.AllOrdersBean;
import com.jimei.xiaolumeimei.entities.OrderDetailBean;
import com.jimei.xiaolumeimei.entities.PackageBean;
import com.jimei.xiaolumeimei.entities.UserBean;
import com.jimei.xiaolumeimei.model.TradeModel;
import com.jimei.xiaolumeimei.ui.activity.trade.ApplyRefundActivity;
import com.jimei.xiaolumeimei.ui.activity.trade.ApplyReturnGoodsActivity;
import com.jimei.xiaolumeimei.ui.activity.trade.LogisticsActivity;
import com.jimei.xiaolumeimei.utils.ViewUtils;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;

import java.util.ArrayList;
import java.util.List;

import rx.schedulers.Schedulers;

public class OrderGoodsListAdapter extends BaseAdapter {
    private final String[] NUM = {"一", "二", "三", "四", "五", "六", "七", "八", "九", "十"};
    private static final String TAG = "OrderGoodsListAdapter";
    private Activity context;
    private List<AllOrdersBean.ResultsEntity.OrdersEntity> dataSource;
    private ArrayList<PackageBean> packageBeanList;
    private String timeStr;
    private String stateStr;
    private String tid;

    private OrderDetailBean orderDetailEntity;
    private int count = 0;
    private String packetid = "";
    private String company_code = "";


    public void setPackageBeanList(ArrayList<PackageBean> packageBeanList) {
        this.packageBeanList = packageBeanList;
    }

    public OrderGoodsListAdapter(Activity context) {
        dataSource = new ArrayList<>();
        this.context = context;
    }

    public void updateWithClear(List<AllOrdersBean.ResultsEntity.OrdersEntity> list) {
        dataSource.clear();
        dataSource.addAll(list);
        notifyDataSetChanged();
    }

    public void update(List<AllOrdersBean.ResultsEntity.OrdersEntity> list) {
        dataSource.addAll(list);
        notifyDataSetChanged();
    }

    public void setData(OrderDetailBean orderDetailBean) {
        orderDetailEntity = orderDetailBean;
        stateStr = orderDetailBean.getStatus_display();
        timeStr = orderDetailBean.getCreated().replace("T", " ");
        tid = orderDetailBean.getTid();
        dataSource.addAll(orderDetailBean.getOrders());
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return dataSource.size();
    }

    @Override
    public Object getItem(int position) {
        return dataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(TAG, "getView " + position);

        ImageView img_goods;
        TextView tx_good_name;
        TextView tx_good_price;
        TextView tx_good_size;
        TextView tx_good_num;
        int state = dataSource.get(position).getStatus();
        int refund_state = dataSource.get(position).getRefundStatus();

        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.item_order_detail_include_proc, null);
            if ((state == XlmmConst.ORDER_STATE_PAYED) || (state
                    == XlmmConst.ORDER_STATE_SENDED) || (state
                    == XlmmConst.ORDER_STATE_CONFIRM_RECEIVE)) {
                setBtnInfo(convertView, state, refund_state,
                        dataSource.get(position).isKillTitle());
                setBtnListener(convertView, state, refund_state, dataSource.get(position).getId(),
                        dataSource.get(position));
            } else {
                convertView.findViewById(R.id.rl_info).setVisibility(View.GONE);
            }
            LinearLayout layout = (LinearLayout) convertView.findViewById(R.id.logistics_layout);
            View view = convertView.findViewById(R.id.divider);
            String key = "";
            if (packageBeanList.size() > position) {
                key = packageBeanList.get(position).getPackage_group_key();
                if (position == 0) {
                    view.setVisibility(View.VISIBLE);
                    layout.setVisibility(View.VISIBLE);
                    count = 0;
                } else if (packageBeanList.get(position).getPackage_group_key().equals(packageBeanList.get(position - 1).getPackage_group_key())) {
                    view.setVisibility(View.GONE);
                    layout.setVisibility(View.GONE);
                } else {
                    view.setVisibility(View.VISIBLE);
                    layout.setVisibility(View.VISIBLE);
                    count++;
                }
                ((TextView) convertView.findViewById(R.id.tv_order_package)).setText("包裹" + NUM[count]);
                ((TextView) convertView.findViewById(R.id.tx_order_crtstate)).setText(packageBeanList.get(position).getAssign_status_display());
                packetid = packageBeanList.get(position).getOut_sid();
                company_code = packageBeanList.get(position).getLogistics_company_code();
            } else {
                if ("待付款".equals(stateStr)) {
                    view.setVisibility(View.VISIBLE);
                    layout.setVisibility(View.GONE);
                } else if (position == 0) {
                    view.setVisibility(View.VISIBLE);
                    layout.setVisibility(View.VISIBLE);
                } else {
                    view.setVisibility(View.GONE);
                    layout.setVisibility(View.GONE);
                }
                ((TextView) convertView.findViewById(R.id.tx_order_crtstate)).setText(stateStr);
                ((TextView) convertView.findViewById(R.id.tv_order_package)).setText("包裹一");
            }
            final String finalKey = key;
            if (!"待付款".equals(stateStr)) {
                layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, LogisticsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("packetid", packetid);
                        bundle.putString("time", timeStr);
                        bundle.putString("tid", tid);
                        bundle.putString("state", stateStr);
                        bundle.putString("key", finalKey);
                        bundle.putString("company_code", company_code);
                        bundle.putSerializable("list", packageBeanList);
                        bundle.putInt("id", orderDetailEntity.getId());
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }
                });
            }
        }

        img_goods = (ImageView) convertView.findViewById(R.id.img_good);
        tx_good_name = (TextView) convertView.findViewById(R.id.tx_good_name);
        tx_good_price = (TextView) convertView.findViewById(R.id.tx_good_price);
        tx_good_size = (TextView) convertView.findViewById(R.id.tx_good_size);
        tx_good_num = (TextView) convertView.findViewById(R.id.tx_good_num);

        if (dataSource.get(position).getTitle().length() >= 9) {
            tx_good_name.setText(dataSource.get(position).getTitle().substring(0, 8) + "...");
        } else {
            tx_good_name.setText(dataSource.get(position).getTitle());
        }
        tx_good_price.setText("¥" + dataSource.get(position).getPayment());
        tx_good_size.setText("尺码:" + dataSource.get(position).getSkuName());
        tx_good_num.setText("x" + dataSource.get(position).getNum());

        ViewUtils.loadImgToImgView(context, img_goods, dataSource.get(position).getPicPath());
        Log.d(TAG, " img_url " + dataSource.get(position).getPicPath());

        return convertView;
    }

    private void setBtnInfo(View convertView, int state, int refund_state,
                            boolean kill_title) {
        Log.d(TAG, " setBtnInfo" + state + " " + refund_state);

        Button btn = (Button) convertView.findViewById(R.id.btn_order_proc);
        TextView tv_order_state = (TextView) convertView.findViewById(R.id.tv_order_state);
        switch (state) {
            case XlmmConst.ORDER_STATE_PAYED:
            case XlmmConst.ORDER_STATE_CONFIRM_RECEIVE: {
                if (kill_title) {
                    btn.setVisibility(View.INVISIBLE);
                } else {
                    if (refund_state != XlmmConst.REFUND_STATE_NO_REFUND) {
                        btn.setVisibility(View.INVISIBLE);
                        tv_order_state.setVisibility(View.VISIBLE);
                        switch (refund_state) {
                            case XlmmConst.REFUND_STATE_BUYER_APPLY: {
                                tv_order_state.setText("已经申请退款");
                                break;
                            }
                            case XlmmConst.REFUND_STATE_SELLER_AGREED: {
                                tv_order_state.setText("卖家同意退款");
                                break;
                            }
                            case XlmmConst.REFUND_STATE_BUYER_RETURNED_GOODS: {
                                tv_order_state.setText("已经退货");
                                break;
                            }
                            case XlmmConst.REFUND_STATE_SELLER_REJECTED: {
                                tv_order_state.setText("卖家拒绝退款");
                                break;
                            }
                            case XlmmConst.REFUND_STATE_WAIT_RETURN_FEE: {
                                tv_order_state.setText("退款中");
                                break;
                            }
                            case XlmmConst.REFUND_STATE_REFUND_CLOSE: {
                                tv_order_state.setText("退款关闭");
                                break;
                            }
                            case XlmmConst.REFUND_STATE_REFUND_SUCCESS: {
                                tv_order_state.setText("退款成功");
                                break;
                            }
                            default:
                                break;
                        }
                    } else {
                        btn.setText("申请退款");
                        tv_order_state.setVisibility(View.INVISIBLE);
                    }
                }
                break;
            }
            case XlmmConst.ORDER_STATE_SENDED: {
                btn.setText("确认收货");
                tv_order_state.setVisibility(View.INVISIBLE);
                break;
            }
            default:
                break;
        }
    }

    private void setBtnListener(View convertView, int state, int refund_state, int goods_id,
                                AllOrdersBean.ResultsEntity.OrdersEntity goods_info) {
        Button btn = (Button) convertView.findViewById(R.id.btn_order_proc);
        switch (state) {
            case XlmmConst.ORDER_STATE_PAYED: {
                switch (refund_state) {
                    case XlmmConst.REFUND_STATE_NO_REFUND: {
                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //enter apply refund
                                Log.d(TAG, "enter apply refund ");
                                Intent intent = new Intent(context, ApplyRefundActivity.class);
                                intent.putExtra("goods_info", goods_info);
                                Log.d(TAG,
                                        "transfer good  " + goods_info.getId() + " to " + "ApplyRefundActivity");
                                context.startActivity(intent);
                                context.finish();
                            }
                        });
                        break;
                    }
                }
                break;
            }
            case XlmmConst.ORDER_STATE_SENDED: {
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //confirm receive goods
                        Log.d(TAG, "confirm receive goods ");
                        receive_goods(goods_id);
                    }
                });
                break;
            }

            case XlmmConst.ORDER_STATE_CONFIRM_RECEIVE: {
                switch (refund_state) {
                    case XlmmConst.REFUND_STATE_NO_REFUND: {
                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //enter apply return goods
                                Log.d(TAG, "enter apply return goods ");
                                Intent intent = new Intent(context, ApplyReturnGoodsActivity.class);
                                intent.putExtra("goods_info", goods_info);
                                Log.d(TAG, "transfer good  "
                                        + goods_info.getId()
                                        + " to "
                                        + "ApplyReturnGoodsActivity");
                                context.startActivity(intent);
                                context.finish();
                            }
                        });
                        break;
                    }
                }
            }
            break;
        }
    }

    private void receive_goods(int id) {
        TradeModel.getInstance()
                .receiveGoods(id)
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<UserBean>() {
                    @Override
                    public void onNext(UserBean userBean) {
                        Log.d(TAG, "returncode " + userBean.getCode());
                        context.finish();
                    }
                });
    }
}

