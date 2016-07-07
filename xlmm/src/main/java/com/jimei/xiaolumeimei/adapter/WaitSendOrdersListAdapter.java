package com.jimei.xiaolumeimei.adapter;

/**
 * Created by wulei on 15-12-17.
 * 商品订单数据适配
 */

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jimei.xiaolumeimei.entities.AllOrdersBean;
import com.jimei.xiaolumeimei.utils.ViewUtils;
import com.jimei.xiaolumeimei.widget.MyHorizontalScrollView;

import java.util.ArrayList;
import java.util.List;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.ui.activity.trade.OrderDetailActivity;
import com.jude.utils.JUtils;

public class WaitSendOrdersListAdapter extends BaseAdapter {
    private static final String TAG = "WaitSendListAdapter";
    private Activity context;
    private List<AllOrdersBean.ResultsEntity> mList;

    public WaitSendOrdersListAdapter(Activity context) {
        mList = new ArrayList<>();
        this.context = context;
    }

    public static void fillPicPath(List<String> mDatas,
                                   List<AllOrdersBean.ResultsEntity.OrdersEntity> good_list) {
        for (int i = 0; i < good_list.size(); i++) {
            mDatas.add(good_list.get(i).getPic_path());
        }
    }

    public void updateWithClear(List<AllOrdersBean.ResultsEntity> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void update(List<AllOrdersBean.ResultsEntity> list) {
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
        TextView tx_order_sate;
        ViewHolder holder;
        LinearLayout llayout;
        final int order_id = mList.get(position).getId();

        if (convertView == null) {
            Log.d(TAG, "convertView == null ");
            convertView = LayoutInflater.from(context).inflate(R.layout.orders_list_item, null);
            holder = new ViewHolder();

            //这个地方比较奇葩，只有1个商品时显示商品详细信息，多余1个商品时只显示商品图片
            llayout = (LinearLayout) convertView.findViewById(R.id.llayout_order_item);

            if (1 == mList.get(position).getOrders().size()) {
                LinearLayout ll_one_order = crt_one_goods_view(position, order_id);
                llayout.addView(ll_one_order);
                holder.goods_num = 1;

                holder.ll_goods = ll_one_order;
            } else {
                MyHorizontalScrollView mHorizontalScrollView = crt_more_goods_view(position, order_id);
                llayout.addView(mHorizontalScrollView);
                holder.goods_num = mList.get(position).getOrders().size();
                holder.mHorizontalScrollView = mHorizontalScrollView;
            }
            holder.position = position;

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            llayout = (LinearLayout) convertView.findViewById(R.id.llayout_order_item);

            if (position == holder.position) {
                Log.d(TAG, "same position update,do nothing ");
            } else {
                Log.d(TAG, "NOT same position update,CONVERT ");
                if (1 == holder.goods_num) {
                    holder.ll_goods.setVisibility(View.GONE);
                    Log.d(TAG, "ll_goods gone ");
                } else {
                    holder.mHorizontalScrollView.setVisibility(View.GONE);
                    Log.d(TAG, "mHorizontalScrollView gone ");
                }
                if (1 == mList.get(position).getOrders().size()) {
                    LinearLayout ll_one_order = crt_one_goods_view(position, order_id);
                    llayout.addView(ll_one_order);
                    holder.goods_num = 1;
                    holder.ll_goods = ll_one_order;
                } else {
                    MyHorizontalScrollView mHorizontalScrollView = crt_more_goods_view(position, order_id);
                    llayout.addView(mHorizontalScrollView);
                    holder.goods_num = mList.get(position).getOrders().size();
                    holder.mHorizontalScrollView = mHorizontalScrollView;
                }
                holder.position = position;

                convertView.setTag(holder);
            }
        }


        tx_order_sate = (TextView) convertView.findViewById(R.id.tx_order_state);
        tx_order_sate.setText(mList.get(position).getStatusDisplay());
        return convertView;
    }

    private LinearLayout crt_one_goods_view(int position, int order_id) {
        Log.d(TAG, "crt_one_goods_view ");

        LayoutInflater inflater = LayoutInflater.from(context);
        LinearLayout ll_one_order =
                (LinearLayout) inflater.inflate(R.layout.one_order_item, null);
        ll_one_order.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Log.d(TAG, "onClick ");
                Intent intent = new Intent(context, OrderDetailActivity.class);
                intent.putExtra("orderinfo", order_id);
                intent.putExtra("source", "WaitSend");
                JUtils.Log(TAG, "LinearLayout transfer orderid  " + order_id + " to OrderDetailActivity");
                context.startActivity(intent);
            }
        });

        ImageView img_goods = (ImageView) ll_one_order.findViewById(R.id.img_good);
        TextView tx_good_name = (TextView) ll_one_order.findViewById(R.id.tx_good_name);
        TextView tx_good_price = (TextView) ll_one_order.findViewById(R.id.tx_good_price);
        TextView tx_good_size = (TextView) ll_one_order.findViewById(R.id.tx_good_size);
        TextView tx_good_num = (TextView) ll_one_order.findViewById(R.id.tx_good_num);
        tx_good_name.setText(mList.get(position).getOrders().get(0).getTitle());
        tx_good_price.setText("¥" + mList.get(position).getOrders().get(0).getPayment());
        tx_good_size.setText(mList.get(position).getOrders().get(0).getSku_name());
        tx_good_num.setText(
                "x" + Integer.toString(mList.get(position).getOrders().get(0).getNum()));

        ViewUtils.loadImgToImgView(context, img_goods, mList.get(position).getOrderPic());

        return ll_one_order;
    }

    private MyHorizontalScrollView crt_more_goods_view(int position, int order_id) {
        Log.d(TAG, "crt_more_goods_view ");

        List<String> mDatas = new ArrayList<String>();
        fillPicPath(mDatas, mList.get(position).getOrders());
        Log.d(TAG,
                "goodsnum " + mList.get(position).getOrders().size() + " " + mDatas.size());

        LayoutInflater inflater = LayoutInflater.from(context);
        MyHorizontalScrollView mHorizontalScrollView =
                (MyHorizontalScrollView) inflater.inflate(R.layout.hscrollview_gallery, null);
        HorizontalScrollViewAdapter mAdapter =
                new HorizontalScrollViewAdapter(context, mDatas);
        //添加滚动回调
        mHorizontalScrollView.setCurrentImageChangeListener(
                new MyHorizontalScrollView.CurrentImageChangeListener() {
                    @Override
                    public void onCurrentImgChanged(int position, View viewIndicator) {

                    }
                });
        //添加点击回调
        mHorizontalScrollView.setOnItemClickListener(
                new MyHorizontalScrollView.OnItemClickListener() {

                    @Override
                    public void onClick(View view, int position) {
                        Intent intent = new Intent(context, OrderDetailActivity.class);
                        intent.putExtra("orderinfo", order_id);
                        intent.putExtra("source", "WaitSend");
                        Log.d(TAG, "mHorizontalScrollView transfer orderid  " + order_id + " to OrderDetailActivity");
                        context.startActivity(intent);
                    }
                });
        //设置适配器
        mHorizontalScrollView.initDatas(mAdapter);
        return mHorizontalScrollView;
    }

    public void clearAll() {
        mList.clear();
        notifyDataSetChanged();
    }

    public static class ViewHolder {
        int goods_num;
        int position;
        LinearLayout ll_goods;
        MyHorizontalScrollView mHorizontalScrollView;

    }
}

