package com.jimei.xiaolumeimei.adapter;

/**
 * Created by wulei on 15-12-17.
 * 商品订单数据适配
 */

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jimei.xiaolumeimei.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.entities.AllRefundsBean;

public class AllRefundsListAdapter extends BaseAdapter {
    private static final String TAG = "AllRefundsListAdapter";
    private Activity context;
    private List<AllRefundsBean.ResultsEntity> datas;

    public AllRefundsListAdapter(Activity context) {
        datas = new ArrayList<>();
        this.context = context;
    }

    public void update(List<AllRefundsBean.ResultsEntity> list) {
        Log.d(TAG, "update size " + list.size());
        datas.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
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
            convertView =
                    LayoutInflater.from(context).inflate(R.layout.refunds_list_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.no.setText("退款编号：" + datas.get(position).getRefund_no());
        holder.state.setText(datas.get(position).getStatus_display());
        holder.refundfee.setText("¥" + datas.get(position).getRefund_fee());

        ViewUtils.loadImgToImgView(context, holder.img_goods, datas.get(position).getPic_path());
        if (datas.get(position).getTitle().length() >= 9) {
            holder.name.setText(datas.get(position).getTitle().substring(0, 8) + "...");
        } else {
            holder.name.setText(datas.get(position).getTitle());
        }
        holder.price.setText("¥" + datas.get(position).getPayment());
        holder.size.setText("尺码:" + datas.get(position).getSku_name());
        holder.num.setText("");


        return convertView;
    }

    public int getGoodsId(int position) {
        return datas.get(position).getId();
    }

    public int getRefundStatus(int position) {
        return datas.get(position).getStatus();
    }

    private class ViewHolder {
        ImageView img_goods;
        TextView no, state, refundfee, name, price, size, num;

        public ViewHolder(View itemView) {
            no = (TextView) itemView.findViewById(R.id.tx_refund_no);
            state = (TextView) itemView.findViewById(R.id.tx_refund_state);
            refundfee = (TextView) itemView.findViewById(R.id.tx_refundfee);
            name = (TextView) itemView.findViewById(R.id.tx_good_name);
            price = (TextView) itemView.findViewById(R.id.tx_good_price);
            size = (TextView) itemView.findViewById(R.id.tx_good_size);
            num = (TextView) itemView.findViewById(R.id.tx_good_num);
            img_goods = ((ImageView) itemView.findViewById(R.id.img_good));
        }
    }
}

