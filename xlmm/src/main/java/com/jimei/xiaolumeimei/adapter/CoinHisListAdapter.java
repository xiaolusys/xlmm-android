package com.jimei.xiaolumeimei.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.entities.CoinHistoryListBean;

import java.util.ArrayList;
import java.util.List;

public class CoinHisListAdapter extends BaseAdapter {
    private static final String TAG = "PointListAdapter";
    private Context context;
    private List<CoinHistoryListBean.ResultsBean> mList;

    public CoinHisListAdapter(Context context) {
        mList = new ArrayList<>();
        this.context = context;
    }

    public void updateWithClear(List<CoinHistoryListBean.ResultsBean> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void update(List<CoinHistoryListBean.ResultsBean> list) {
        Log.d(TAG, "dataSource.size " + list.size());
        mList.addAll(list);
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
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(TAG, "getView ");
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_membership_point, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        CoinHistoryListBean.ResultsBean bean = mList.get(position);
        holder.logTime.setText(bean.getCreated().replace("T", " "));
        holder.info.setText(bean.getSubject());
        if ("收入".equals(bean.getIro_type())) {
            holder.value.setText("+" + bean.getAmount());
        } else if ("支出".equals(bean.getIro_type())) {
            holder.value.setText("-" + bean.getAmount());
        }
        return convertView;
    }


    private class ViewHolder {
        TextView logTime, info, value;

        public ViewHolder(View itemView) {
            logTime = (TextView) itemView.findViewById(R.id.tx_point_logtime);
            info = (TextView) itemView.findViewById(R.id.tx_point_info);
            value = (TextView) itemView.findViewById(R.id.tx_point_value);
        }
    }

}

