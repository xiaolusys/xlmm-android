package com.jimei.xiaolumeimei.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.entities.AllOrdersBean;
import com.jimei.xiaolumeimei.ui.activity.trade.OrderDetailActivity;
import com.jimei.xiaolumeimei.widget.NestedListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wisdom on 16/7/15.
 */
public class AllOrderAdapter extends BaseAdapter {

    private Activity context;
    private List<AllOrdersBean.ResultsEntity> mList;

    public AllOrderAdapter(Activity context) {
        mList = new ArrayList<>();
        this.context = context;
    }

    public void updateWithClear(List<AllOrdersBean.ResultsEntity> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void update(List<AllOrdersBean.ResultsEntity> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void clearAll() {
        mList.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_order_list, parent, false);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OrderDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("orderinfo", mList.get(position).getId());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        OrderListAdapter adapter = new OrderListAdapter(context, mList.get(position).getOrders());
        adapter.setId(mList.get(position).getId());
        vh.listView.setAdapter(adapter);
        vh.tv_status.setText(mList.get(position).getStatusDisplay());
        vh.tv_payment.setText(mList.get(position).getPayment() + "");
        return convertView;
    }

    private class ViewHolder {
        LinearLayout linearLayout;
        TextView tv_status, tv_payment;
        NestedListView listView;

        public ViewHolder(View itemView) {
            linearLayout = ((LinearLayout) itemView.findViewById(R.id.ll));
            tv_status = ((TextView) itemView.findViewById(R.id.status));
            tv_payment = ((TextView) itemView.findViewById(R.id.payment));
            listView = ((NestedListView) itemView.findViewById(R.id.lv));
        }
    }
}
