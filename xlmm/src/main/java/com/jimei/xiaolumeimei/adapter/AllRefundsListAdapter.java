package com.jimei.xiaolumeimei.adapter;

/**
 * Created by wulei on 15-12-17.
 * 商品订单数据适配
 */

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jimei.library.utils.ViewUtils;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.entities.AllRefundsBean;

import java.util.ArrayList;
import java.util.List;

public class AllRefundsListAdapter extends BaseAdapter {
    private static final String TAG = "AllRefundsListAdapter";
    private Activity context;
    private List<AllRefundsBean.ResultsEntity> datas;

    public AllRefundsListAdapter(Activity context) {
        datas = new ArrayList<>();
        this.context = context;
    }

    public void update(List<AllRefundsBean.ResultsEntity> list) {
        Log.d(TAG, "update size " + list.size());
        datas.addAll(list);
        notifyDataSetChanged();
    }

    public void clear() {
        datas.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(TAG, "getView ");
        ViewHolder holder;
        AllRefundsBean.ResultsEntity entity = datas.get(position);
        if (convertView == null) {
            convertView =
                    LayoutInflater.from(context).inflate(R.layout.refunds_list_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.state.setTextColor(context.getResources().getColor(R.color.colorAccent));
        holder.warn.setVisibility(View.GONE);
        if (entity.isHas_good_return()) {
            holder.flag.setImageResource(R.drawable.icon_return_goods_mini);
            holder.type.setText("退货退款");
        } else if ("".equals(entity.getRefund_channel())
                || "budget".equals(entity.getRefund_channel())) {
            holder.flag.setImageResource(R.drawable.icon_fast_return_mini);
            holder.type.setText("极速退款");
        } else {
            holder.flag.setImageResource(R.drawable.icon_return_mini);
            holder.type.setText("退款");
        }
        if (entity.getStatus() == 4 && entity.isHas_good_return()) {
            holder.state.setText("请寄回商品");
            holder.state.setTextColor(context.getResources().getColor(R.color.coupon_red));
            holder.warn.setVisibility(View.VISIBLE);
        } else {
            holder.state.setText(entity.getStatus_display());
        }
        ViewUtils.loadImgToImgView(context, holder.good, entity.getPic_path());
        holder.name.setText(entity.getTitle());
        holder.size.setText("尺寸:" + entity.getSku_name());
        holder.payment.setText("交易金额:" + entity.getPayment() + "x" + entity.getRefund_num());
        holder.refund.setText("退款金额:" + entity.getRefund_fee() + "x" + entity.getRefund_num());
        return convertView;
    }

    public int getGoodsId(int position) {
        return datas.get(position).getId();
    }

    public int getRefundStatus(int position) {
        return datas.get(position).getStatus();
    }

    private class ViewHolder {

        ImageView flag, good, warn;
        TextView type, state, name, size, payment, refund;

        public ViewHolder(View itemView) {
            flag = ((ImageView) itemView.findViewById(R.id.flag));
            type = ((TextView) itemView.findViewById(R.id.type));
            state = ((TextView) itemView.findViewById(R.id.state));
            good = ((ImageView) itemView.findViewById(R.id.good));
            warn = ((ImageView) itemView.findViewById(R.id.warn));
            name = ((TextView) itemView.findViewById(R.id.name));
            size = ((TextView) itemView.findViewById(R.id.size));
            payment = ((TextView) itemView.findViewById(R.id.payment));
            refund = ((TextView) itemView.findViewById(R.id.refund));
        }
    }
}

