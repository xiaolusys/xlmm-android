package com.jimei.xiaolumeimei.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.entities.MMCarryBean;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wisdom on 17/3/21.
 */

public class MMCarryAdapter extends RecyclerView.Adapter<MMCarryAdapter.ViewHolder> {
    private List<MMCarryBean.ResultsBean> data;
    private Context context;


    public MMCarryAdapter(Context context) {
        this.context = context;
        data = new ArrayList<>();
    }

    public void update(List<MMCarryBean.ResultsBean> list) {
        data.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_mm_carry, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MMCarryBean.ResultsBean bean = data.get(position);
        String time_data = bean.getCreated().substring(0, 10);
        if (position == 0) {
            holder.time.setVisibility(View.VISIBLE);
        } else if (time_data.equals(data.get(position - 1).getCreated().substring(0, 10))) {
            holder.time.setVisibility(View.GONE);
        } else {
            holder.time.setVisibility(View.VISIBLE);
        }
        holder.time.setText(time_data);
        holder.timeDetail.setText(bean.getCreated().substring(11));
        holder.desc.setText(bean.getType());
        int amount = bean.getAmount();
        String amountStr;
        if (amount > 0) {
            amountStr = "+" + new DecimalFormat("0.00").format((double) amount / 100);

        } else {
            amountStr = new DecimalFormat("0.00").format((double) amount / 100);
        }
        holder.amount.setText(amountStr);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.time)
        TextView time;
        @Bind(R.id.time_detail)
        TextView timeDetail;
        @Bind(R.id.desc)
        TextView desc;
        @Bind(R.id.amount)
        TextView amount;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
