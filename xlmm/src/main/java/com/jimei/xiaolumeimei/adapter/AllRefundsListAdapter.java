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

import com.jimei.xiaolumeimei.utils.ViewUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.entities.AllRefundsBean;

public class AllRefundsListAdapter extends BaseAdapter {
    private static final String TAG = "AllRefundsListAdapter";
    private Context context;
    List<HashMap<String, String>> data;

    private List<AllRefundsBean.ResultsEntity> data_refund_list;

    public AllRefundsListAdapter(Context context) {
        data_refund_list = new ArrayList<AllRefundsBean.ResultsEntity>();
        this.data = new ArrayList<HashMap<String, String>>();
        this.context = context;
    }

    public void updateWithClear(List<AllRefundsBean.ResultsEntity> list) {
        data_refund_list.clear();
        data_refund_list.addAll(list);
        notifyDataSetChanged();
    }

    public void update(List<AllRefundsBean.ResultsEntity> list) {

        float refund_fee = 0;
        String refund_State = "";
        String refund_no = "";
        String img_url = "";
        String title = "";
        float std_sale_price = 0;
        float pay_price = 0;
        String model_id = "";
        int num = 0;

        Log.d(TAG,"dataSource.size "+ list.size());
        for (int i = 0; i < list.size(); i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            refund_no = list.get(i).getRefund_no();
            refund_State = list.get(i).getStatus_display();
            refund_fee = (float)list.get(i).getRefund_fee();
            img_url = list.get(i).getPic_path();
            title = list.get(i).getTitle();
            std_sale_price = (float)list.get(i).getTotal_fee();
            pay_price = (float)list.get(i).getPayment();
            model_id = list.get(i).getSku_name();
            num = list.get(i).getRefund_num();

            map.put("img_url", img_url );
            map.put("title", title );
            map.put("std_sale_price", Float.toString(std_sale_price) );
            map.put("pay_price", Float.toString(pay_price) );
            map.put("model_id", model_id );
            map.put("num", Integer.toString(num) );

            map.put("refund_no", (refund_no) );
            map.put("refund_State", refund_State );
            map.put("refund_fee", Float.toString(refund_fee) );
            data.add(map);


        }
        data_refund_list.addAll(list);
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

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.refunds_list_item, null);
        }

        TextView tx_refundno = (TextView) convertView.findViewById(R.id.tx_refund_no);
        TextView tx_refund_state = (TextView) convertView.findViewById(R.id.tx_refund_state);
        TextView tx_refundfee = (TextView) convertView.findViewById(R.id.tx_refundfee);

        tx_refundno.setText("订单编号："+data.get(position).get("refund_no"));
        tx_refund_state.setText(data.get(position).get("refund_State"));
        tx_refundfee.setText("￥"+data.get(position).get("refund_fee"));

        ImageView img_goods = (ImageView) convertView.findViewById(R.id.img_good);;
        TextView tx_good_name = (TextView) convertView.findViewById(R.id.tx_good_name);
        TextView tx_good_price = (TextView) convertView.findViewById(R.id.tx_good_price);
        TextView tx_good_size = (TextView) convertView.findViewById(R.id.tx_good_size);
        TextView tx_good_num = (TextView) convertView.findViewById(R.id.tx_good_num);

        ViewUtils.loadImgToImgView(context, img_goods, data.get(position).get("img_url") );
        tx_good_name.setText(data.get(position).get("title") );
        tx_good_price.setText("￥"+data.get(position).get("pay_price"));
        tx_good_size.setText(data.get(position).get("model_id"));
        tx_good_num.setText(data.get(position).get("num"));

        return convertView;
    }

    public int getOrderId(int position){
        return data_refund_list.get(position).getId();
    }
}

