package com.jimei.xiaolumeimei.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.entities.OrderDetailBean;

import java.util.List;

/**
 * Created by wisdom on 16/6/30.
 */
public class RefundTypeAdapter extends BaseAdapter {

    private Context context;
    private List<OrderDetailBean.ExtrasBean.RefundChoicesBean> refund_choices;

    public RefundTypeAdapter(Context context, List<OrderDetailBean.ExtrasBean.RefundChoicesBean> refund_choices) {
        this.context = context;
        this.refund_choices = refund_choices;
    }

    @Override
    public int getCount() {
        return refund_choices.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.item_refund_type, parent, false);
        ImageView icon = (ImageView) convertView.findViewById(R.id.refund_iv);
        TextView name = (TextView) convertView.findViewById(R.id.refund_tv);
        TextView desc = (TextView) convertView.findViewById(R.id.refund_tv_desc);
        name.setText(refund_choices.get(position).getName());
        desc.setText(refund_choices.get(position).getDesc());
        if ("budget".equals(refund_choices.get(position).getRefund_channel())) {
            icon.setImageResource(R.drawable.icon_fast_return);
        } else {
            icon.setImageResource(R.drawable.icon_return);
        }
        return convertView;
    }
}
