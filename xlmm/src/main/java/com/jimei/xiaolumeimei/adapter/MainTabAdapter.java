package com.jimei.xiaolumeimei.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.entities.MainTodayBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wisdom on 17/2/14.
 */

public abstract class MainTabAdapter extends RecyclerView.Adapter<MainTabAdapter.ViewHolder> {

    private List<MainTodayBean> data;
    private Context context;
    private int currentPosition;

    protected MainTabAdapter(Context context) {
        this.context = context;
        currentPosition = 0;
        data = new ArrayList<>();
    }

    public void updateWithClear(List<MainTodayBean> list) {
        data.clear();
        data.addAll(list);
        notifyDataSetChanged();
    }

    public void update(List<MainTodayBean> list) {
        data.addAll(list);
        notifyDataSetChanged();
    }

    void setCurrentPosition(int position) {
        currentPosition = position;
        notifyDataSetChanged();
    }

    @Override
    public MainTabAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_main_tab, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(data.get(position).getHour() + "点热销");
        holder.selected.setBackgroundResource(R.color.white);
        if (position == currentPosition) {
            holder.selected.setBackgroundResource(R.color.colorAccent);
            holder.textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            holder.textView.setTextColor(context.getResources().getColor(R.color.colorAccent));
            itemClick(currentPosition);
        } else {
            holder.textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            holder.selected.setBackgroundResource(R.color.white);
            holder.textView.setTextColor(context.getResources().getColor(R.color.text_color_62));
        }
        holder.itemView.setOnClickListener(v -> {
            itemClick(position);
            setCurrentPosition(position);
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public abstract void itemClick(int position);


    class ViewHolder extends RecyclerView.ViewHolder {
        private View selected;
        private TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text);
            selected = itemView.findViewById(R.id.selected);
        }
    }
}
