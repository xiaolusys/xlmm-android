package com.jimei.xiaolumeimei.adapter;

/**
 * Created by wulei on 15-12-17.
 * 商品订单数据适配
 */

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.entities.AllOrdersBean;
import com.jimei.xiaolumeimei.entities.OrderDetailBean;
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
    public final String[] NUM = {"一", "二", "三", "四", "五", "六", "七", "八", "九", "十"};
    private static final String TAG = "OrderGoodsListAdapter";
    private Activity context;
    private List<AllOrdersBean.ResultsEntity.OrdersEntity> data;

    private OrderDetailBean orderDetailEntity;
    private int count;

    public OrderGoodsListAdapter(Activity context, OrderDetailBean orderDetailEntity) {
        this.orderDetailEntity = orderDetailEntity;
        data = new ArrayList<>();
        data.addAll(orderDetailEntity.getOrders());
        count = 0;
        this.context = context;
    }

    public void setData(OrderDetailBean orderDetailBean) {
        orderDetailEntity = orderDetailBean;
        data.addAll(orderDetailBean.getOrders());
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(TAG, "getView " + position);
        int state = data.get(position).getStatus();
        int refund_state = data.get(position).getRefund_status();
        convertView = LayoutInflater.from(context).inflate(R.layout.item_order_detail_include_proc, null);
        if ((state == XlmmConst.ORDER_STATE_PAYED) ||
                (state == XlmmConst.ORDER_STATE_SENDED) ||
                (state == XlmmConst.ORDER_STATE_CONFIRM_RECEIVE)) {
            setBtnInfo(convertView, state, refund_state,
                    data.get(position).isKill_title());
            setBtnListener(convertView, state, refund_state, data.get(position).getId(),
                    data.get(position), position);
        } else {
            convertView.findViewById(R.id.rl_info).setVisibility(View.GONE);
        }
        View divider = convertView.findViewById(R.id.divider);
        View logisticsLayout = convertView.findViewById(R.id.logistics_layout);
        if (position == 0) {
            count = 0;
            divider.setVisibility(View.VISIBLE);
            if (!"".equals(data.get(position).getPackage_order_id())) {
                logisticsLayout.setVisibility(View.VISIBLE);
                ((TextView) convertView.findViewById(R.id.tv_order_package)).setText("包裹" + NUM[count]);
            } else {
                logisticsLayout.setVisibility(View.GONE);
            }
        } else if (!data.get(position).getPackage_order_id().equals(data.get(position - 1).getPackage_order_id())) {
            divider.setVisibility(View.VISIBLE);
            logisticsLayout.setVisibility(View.VISIBLE);
            count++;
            ((TextView) convertView.findViewById(R.id.tv_order_package)).setText("包裹" + NUM[count]);
        } else {
            divider.setVisibility(View.GONE);
            logisticsLayout.setVisibility(View.GONE);
        }
        for (int i = 0; i < orderDetailEntity.getPackage_orders().size(); i++) {
            if (data.get(position).getPackage_order_id().equals(orderDetailEntity.getPackage_orders().get(i).getId())) {
                String assign_status_display = orderDetailEntity.getPackage_orders().get(i).getAssign_status_display();
                if (assign_status_display != null) {
                    ((TextView) convertView.findViewById(R.id.tx_order_crtstate)).setText(assign_status_display);
                }
                if ((orderDetailEntity.getPackage_orders().get(i).getBook_time() != null)
                        && (orderDetailEntity.getStatus() == 2)) {
                    Button btn = (Button) convertView.findViewById(R.id.btn_order_proc);
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new MaterialDialog.Builder(context)
                                    .cancelable(true)
                                    .positiveText("确认")
                                    .positiveColorRes(R.color.colorAccent)
                                    .callback(new MaterialDialog.ButtonCallback() {
                                        @Override
                                        public void onPositive(MaterialDialog dialog) {
                                            dialog.dismiss();
                                        }
                                    })
                                    .content("您的订单已经向工厂订货，暂不支持退款，请您耐心等待，在收货确认签收后申请退货，如有疑问请咨询小鹿美美公众号或客服4008235355。")
                                    .show();
                        }
                    });
                }
                final int finalI = i;
                convertView.findViewById(R.id.ll_item).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, LogisticsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", orderDetailEntity.getId());
                        bundle.putSerializable("packageOrdersBean", orderDetailEntity.getPackage_orders().get(finalI));
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }
                });
                break;
            }
        }
        ((TextView) convertView.findViewById(R.id.tx_good_name)).setText(data.get(position).getTitle());
        ((TextView) convertView.findViewById(R.id.tx_good_price)).setText("¥" + data.get(position).getPayment());
        ((TextView) convertView.findViewById(R.id.tx_good_size)).setText("尺码:" + data.get(position).getSku_name());
        ((TextView) convertView.findViewById(R.id.tx_good_num)).setText("x" + data.get(position).getNum());
        ImageView img_goods = (ImageView) convertView.findViewById(R.id.img_good);
        ViewUtils.loadImgToImgView(context, img_goods, data.get(position).getPic_path());
        Log.d(TAG, " img_url " + data.get(position).getPic_path());
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
                    convertView.findViewById(R.id.rl_info).setVisibility(View.GONE);
                } else {
                    if (refund_state != XlmmConst.REFUND_STATE_NO_REFUND) {
                        btn.setVisibility(View.INVISIBLE);
                        tv_order_state.setVisibility(View.VISIBLE);
                        switch (refund_state) {
                            case XlmmConst.REFUND_STATE_BUYER_APPLY:
                                tv_order_state.setText("已经申请退款");
                                break;
                            case XlmmConst.REFUND_STATE_SELLER_AGREED:
                                tv_order_state.setText("卖家同意退款");
                                break;
                            case XlmmConst.REFUND_STATE_BUYER_RETURNED_GOODS:
                                tv_order_state.setText("已经退货");
                                break;
                            case XlmmConst.REFUND_STATE_SELLER_REJECTED:
                                tv_order_state.setText("卖家拒绝退款");
                                break;
                            case XlmmConst.REFUND_STATE_WAIT_RETURN_FEE:
                                tv_order_state.setText("退款中");
                                break;
                            case XlmmConst.REFUND_STATE_REFUND_CLOSE:
                                tv_order_state.setText("退款关闭");
                                break;
                            case XlmmConst.REFUND_STATE_REFUND_SUCCESS:
                                tv_order_state.setText("退款成功");
                                break;
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
                                AllOrdersBean.ResultsEntity.OrdersEntity goods_info, int position) {
        Button btn = (Button) convertView.findViewById(R.id.btn_order_proc);
        switch (state) {
            case XlmmConst.ORDER_STATE_PAYED: {
                switch (refund_state) {
                    case XlmmConst.REFUND_STATE_NO_REFUND: {
                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.d(TAG, "enter apply refund ");
                                View view = context.getLayoutInflater().inflate(R.layout.pop_refund_layout, null);
                                Dialog dialog = new Dialog(context, R.style.dialog_style);
                                dialog.setContentView(view);
                                dialog.setCancelable(true);
                                Window window = dialog.getWindow();
                                WindowManager.LayoutParams wlp = window.getAttributes();
                                wlp.gravity = Gravity.BOTTOM;
                                wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
                                window.setAttributes(wlp);
                                window.setWindowAnimations(R.style.dialog_anim);
                                View closeIv = view.findViewById(R.id.close_iv);
                                ListView listView = (ListView) view.findViewById(R.id.lv_refund);
                                closeIv.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });
                                listView.setAdapter(new RefundTypeAdapter(context, orderDetailEntity.getExtras().getRefund_choices()));
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Intent intent = new Intent(context, ApplyRefundActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putInt("id", orderDetailEntity.getId());
                                        bundle.putInt("position", position);
                                        bundle.putString("refund_channel", orderDetailEntity.getExtras().getRefund_choices().get(position).getRefund_channel());
                                        bundle.putString("name", orderDetailEntity.getExtras().getRefund_choices().get(position).getName());
                                        bundle.putString("desc", orderDetailEntity.getExtras().getRefund_choices().get(position).getDesc());
                                        intent.putExtras(bundle);
                                        Log.d(TAG, "transfer good  " + goods_info.getId() + " to " + "ApplyRefundActivity");
                                        context.startActivity(intent);
                                        dialog.dismiss();
                                        context.finish();
                                    }
                                });
                                dialog.show();
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
                                Bundle bundle = new Bundle();
                                bundle.putInt("id", orderDetailEntity.getId());
                                bundle.putInt("position", position);
                                intent.putExtras(bundle);
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

