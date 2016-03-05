package com.jimei.xiaolumeimei.adapter;

/**
 * Created by wulei on 15-12-17.
 * 商品订单数据适配
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jimei.xiaolumeimei.entities.AllOrdersBean;
import com.jimei.xiaolumeimei.entities.MembershipPointBean;
import com.jimei.xiaolumeimei.entities.PointLogBean;
import com.jimei.xiaolumeimei.widget.MyHorizontalScrollView;
import com.jimei.xiaolumeimei.widget.NestedListView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.ui.activity.trade.OrderDetailActivity;

public class MembershipPointListAdapter extends BaseAdapter {
    private static final String TAG = "PointListAdapter";
    private Context context;
    List<HashMap<String, String>> data;
    private List<PointLogBean.ResultsEntity> mList;

    public MembershipPointListAdapter(Context context) {
        mList = new ArrayList<PointLogBean.ResultsEntity>();
        this.data = new ArrayList<HashMap<String, String>>();
        this.context = context;
    }

    public void updateWithClear(List<PointLogBean.ResultsEntity> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void update(List<PointLogBean.ResultsEntity> list) {
        String time = "";
        String info = "";
        int pointvalue = 0;

        Log.d(TAG,"dataSource.size "+ list.size());
        for (int i = 0; i < list.size(); i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            //payment = (float)list.get(i).getPayment();
            pointvalue = list.get(i).getLog_value();


            map.put("logtime", (time) );
            map.put("info", (info) );
            map.put("pointvalue", Integer.toString(pointvalue) );

            data.add(map);
        }
        mList.addAll(list);
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
        Log.d(TAG,"getView ");

        TextView tx_time = null;
        TextView tx_pointinfo= null;
        TextView tx_pointvalue= null;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_membership_point, null);
        }

        tx_time = (TextView) convertView.findViewById(R.id.tx_point_logtime);
        tx_pointinfo = (TextView) convertView.findViewById(R.id.tx_point_info);
        tx_pointvalue = (TextView) convertView.findViewById(R.id.tx_point_value);

        tx_time.setText(data.get(position).get("logtime"));
        tx_pointinfo.setText(data.get(position).get("info"));
        tx_pointvalue.setText("+"+data.get(position).get("pointvalue")+"分");


        return convertView;
    }


}

