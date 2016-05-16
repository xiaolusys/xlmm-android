package com.jimei.xiaolumeimei.adapter;

/**
 * Created by wulei on 15-12-17.
 * 商品订单数据适配
 */

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.entities.PointLogBean;

import java.util.ArrayList;
import java.util.List;

public class MembershipPointListAdapter extends BaseAdapter {
    private static final String TAG = "PointListAdapter";
    private Context context;
    private List<PointLogBean.ResultsBean> mList;

    public MembershipPointListAdapter(Context context) {
        mList = new ArrayList<>();
        this.context = context;
    }

    public void updateWithClear(List<PointLogBean.ResultsBean> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void update(List<PointLogBean.ResultsBean> list) {
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

        holder.logTime.setText("" + mList.get(position).getCreated().replace("T", " "));
        holder.info.setText("购物订单完成奖励积分");
        holder.value.setText("+" + mList.get(position).getLog_value() + "分");
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

