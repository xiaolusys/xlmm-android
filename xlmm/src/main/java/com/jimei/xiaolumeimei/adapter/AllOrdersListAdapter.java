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
import android.widget.ListView;
import android.widget.TextView;

import com.jimei.xiaolumeimei.entities.AllOrdersBean;
import com.jimei.xiaolumeimei.widget.NestedListView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.ui.activity.trade.OrderDetailActivity;

public class AllOrdersListAdapter extends BaseAdapter {
    private static final String TAG = "AllOrdersListAdapter";
    private Context context;
    List<HashMap<String, String>> data;
    private List<AllOrdersBean.ResultsEntity> mList;

    public AllOrdersListAdapter(Context context) {
        mList = new ArrayList<AllOrdersBean.ResultsEntity>();
        this.data = new ArrayList<HashMap<String, String>>();
        this.context = context;
    }

    public void updateWithClear(List<AllOrdersBean.ResultsEntity> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void update(List<AllOrdersBean.ResultsEntity> list) {

        float payment = 0;
        int orderState = 0;



        Log.d(TAG,"dataSource.size "+ list.size());
        for (int i = 0; i < list.size(); i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            payment = (float)list.get(i).getPayment();
            orderState = list.get(i).getStatus();


            map.put("payment", Float.toString(payment) );
            map.put("orderState", Integer.toString(orderState) );

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

        TextView tx_payment = null;
        TextView tx_order_sate = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.orders_list_item, null);
        }

        tx_payment = (TextView) convertView.findViewById(R.id.tx_order_actual_payment);
        tx_order_sate = (TextView) convertView.findViewById(R.id.tx_order_state);

        tx_payment.setText("实付金额"+data.get(position).get("payment"));
        tx_order_sate.setText("交易状态"+data.get(position).get("orderState"));

        NestedListView goods_listview = (NestedListView) convertView.findViewById(R.id.goods_listview);
        OrderGoodsListAdapter goods_adapter = new OrderGoodsListAdapter(context, mList.get(position).getOrders());
        goods_listview.setAdapter(goods_adapter);
        goods_listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                Log.d(TAG, "onItemClick "+arg2 + " " + arg3);
                Intent intent = new Intent(context, OrderDetailActivity.class);

                //intent.putExtra("orderinfo", order_detail_info);
                intent.putExtra("goodnum",arg2);
                context.startActivity(intent);
            }

        });

        return convertView;
    }
}

