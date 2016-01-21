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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.entities.AllOrdersBean;
import com.jimei.xiaolumeimei.entities.OrderDetailBean;
import com.jimei.xiaolumeimei.utils.ViewUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderGoodsListAdapter extends BaseAdapter {
    private static final String TAG = "OrderGoodsListAdapter";
    private Context context;
    List<HashMap<String, String>> data;

    private List<OrderDetailBean.OrdersEntity> dataSource;

    public OrderGoodsListAdapter(Context context) {
        dataSource = new ArrayList<OrderDetailBean.OrdersEntity>();
        this.data = new ArrayList<HashMap<String, String>>();
        this.context = context;
    }

    public OrderGoodsListAdapter(Context context, List<OrderDetailBean.OrdersEntity> goodsList) {
        Log.d(TAG," create");
        this.context = context;
        this.dataSource = goodsList;
        String img_url = "";
        String title = "";
        float std_sale_price = 0;
        float agent_price = 0;
        String model_id = "";
        int num = 0;

        this.data = new ArrayList<HashMap<String, String>>();

        Log.d(TAG,"dataSource.size "+ dataSource.size());
        for (int i = 0; i < dataSource.size(); i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            img_url = dataSource.get(i).getPic_path();
            title = dataSource.get(i).getTitle();
            std_sale_price = (float)dataSource.get(i).getTotal_fee();
            agent_price = (float)dataSource.get(i).getPayment();
            model_id = dataSource.get(i).getSku_name();
            num = dataSource.get(i).getNum();

            map.put("img_url", img_url );
            map.put("title", title );
            map.put("std_sale_price", Float.toString(std_sale_price) );
            map.put("agent_price", Float.toString(agent_price) );
            map.put("model_id", model_id );
            map.put("num", Integer.toString(num) );

            data.add(map);
        }


    }

    public void updateWithClear(List<OrderDetailBean.OrdersEntity> list) {
        dataSource.clear();
        dataSource.addAll(list);
        notifyDataSetChanged();
    }

    public void update(List<OrderDetailBean.OrdersEntity> list) {
        String img_url = "";
        String title = "";
        float std_sale_price = 0;
        float agent_price = 0;
        String model_id = "";
        int num = 0;

        Log.d(TAG,"list.size "+ list.size());
        for (int i = 0; i < list.size(); i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            img_url = list.get(i).getPic_path();
            title = list.get(i).getTitle();
            std_sale_price = (float)list.get(i).getTotal_fee();
            agent_price = (float)list.get(i).getPayment();
            model_id = list.get(i).getSku_name();
            num = list.get(i).getNum();

            map.put("img_url", img_url );
            map.put("title", title );
            map.put("std_sale_price", Float.toString(std_sale_price) );
            map.put("agent_price", Float.toString(agent_price) );
            map.put("model_id", model_id );
            map.put("num", Integer.toString(num) );

            data.add(map);
        }

        dataSource.addAll(list);
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

        ImageView img_goods= null;
        TextView tx_good_name = null;
        TextView tx_good_price = null;
        TextView tx_good_size = null;
        TextView tx_good_num = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.one_order_item, null);
        }

        img_goods = (ImageView) convertView.findViewById(R.id.img_good);;
        tx_good_name = (TextView) convertView.findViewById(R.id.tx_good_name);
        tx_good_price = (TextView) convertView.findViewById(R.id.tx_good_price);
        tx_good_size = (TextView) convertView.findViewById(R.id.tx_good_size);
        tx_good_num = (TextView) convertView.findViewById(R.id.tx_good_num);

        tx_good_name.setText(data.get(position).get("title") );
        tx_good_price.setText(data.get(position).get("agent_price"));
        tx_good_size.setText(data.get(position).get("model_id"));
        tx_good_num.setText(data.get(position).get("num"));

        ViewUtils.loadImgToImgView(context, img_goods, data.get(position).get("img_url") );

        return convertView;
    }
}

