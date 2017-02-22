package com.jimei.xiaolumeimei.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jimei.library.widget.NestedListView;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.entities.AllOrdersBean;
import com.jimei.xiaolumeimei.ui.activity.trade.OrderDetailActivity;
import com.jimei.xiaolumeimei.widget.NoDoubleClickListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wisdom on 16/12/7.
 */

public class AllOrdersAdapter extends RecyclerView.Adapter<AllOrdersAdapter.ViewHolder> {

    private Activity context;
    private List<AllOrdersBean.ResultsEntity> mList;

    public AllOrdersAdapter(Activity context) {
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

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.linearLayout.setOnClickListener(
                new NoDoubleClickListener() {
                    @Override
                    protected void onNoDoubleClick(View v) {
                        Intent intent = new Intent(context, OrderDetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("orderinfo", mList.get(position).getId());
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }
                }
        );
        OrderListAdapter adapter = new OrderListAdapter(context, mList.get(position).getOrders());
        adapter.setId(mList.get(position).getId());
        holder.listView.setAdapter(adapter);
        holder.statusTv.setText(mList.get(position).getStatusDisplay());
        String payment = new DecimalFormat("#.00").format(mList.get(position).getPayment());
        holder.paymentTv.setText(payment);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ll)
        LinearLayout linearLayout;
        @Bind(R.id.status)
        TextView statusTv;
        @Bind(R.id.payment)
        TextView paymentTv;
        @Bind(R.id.lv)
        NestedListView listView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
