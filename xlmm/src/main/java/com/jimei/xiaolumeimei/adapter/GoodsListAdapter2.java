package com.jimei.xiaolumeimei.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.entities.AllOrdersBean;
import com.jimei.xiaolumeimei.utils.ViewUtils;

import java.util.ArrayList;

/**
 * Created by wisdom on 16/5/31.
 */
public class GoodsListAdapter2 extends BaseAdapter {

    private ArrayList<AllOrdersBean.ResultsEntity.OrdersEntity> orders;
    private Context context;

    public GoodsListAdapter2(ArrayList<AllOrdersBean.ResultsEntity.OrdersEntity> orders, Context context) {
        this.orders = orders;
        this.context = context;
    }

    @Override
    public int getCount() {
        return orders.size();
    }

    @Override
    public Object getItem(int position) {
        return orders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.one_order_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        AllOrdersBean.ResultsEntity.OrdersEntity entity = orders.get(position);
        ViewUtils.loadImgToImgView(context, holder.imageView, entity.getPicPath());
        holder.name.setText(entity.getTitle());
        holder.price.setText(entity.getPayment() + "");
        holder.num.setText("x" + entity.getNum());
        return convertView;
    }

    class ViewHolder {
        TextView name, price, num;
        ImageView imageView;


        public ViewHolder(View itemView) {
            imageView = ((ImageView) itemView.findViewById(R.id.img_good));
            name = ((TextView) itemView.findViewById(R.id.tx_good_name));
            price = ((TextView) itemView.findViewById(R.id.tx_good_price));
            num = ((TextView) itemView.findViewById(R.id.tx_good_num));
        }
    }
}
