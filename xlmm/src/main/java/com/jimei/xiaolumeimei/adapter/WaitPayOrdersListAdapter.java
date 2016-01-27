package com.jimei.xiaolumeimei.adapter;

/**
 * Created by wulei on 15-12-17.
 * 商品订单数据适配
 */

import android.app.Activity;
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
import com.jimei.xiaolumeimei.utils.ViewUtils;
import com.jimei.xiaolumeimei.widget.MyHorizontalScrollView;
import com.jimei.xiaolumeimei.widget.NestedListView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.ui.activity.trade.OrderDetailActivity;

public class WaitPayOrdersListAdapter extends BaseAdapter {
  private static final String TAG = "WaitPayAdapter";
  private Activity context;
  List<HashMap<String, String>> data;
  private List<AllOrdersBean.ResultsEntity> mList;

  public WaitPayOrdersListAdapter(Activity context) {
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
    String orderState = "";

    Log.d(TAG, "dataSource.size " + list.size());
    for (int i = 0; i < list.size(); i++) {
      HashMap<String, String> map = new HashMap<String, String>();
      payment = (float) list.get(i).getPayment();
      orderState = list.get(i).getStatusDisplay();

      map.put("payment", Float.toString(payment));
      map.put("orderState", orderState);

      data.add(map);
    }
    mList.addAll(list);
    notifyDataSetChanged();
  }

  @Override public int getCount() {
    return data.size();
  }

  @Override public Object getItem(int position) {
    return data.get(position);
  }

  @Override public long getItemId(int position) {
    return position;
  }

  @Override public View getView(int position, View convertView, ViewGroup parent) {
    Log.d(TAG, "getView ");

    TextView tx_payment = null;
    TextView tx_order_sate = null;
    ViewHolder holder = null;
    final int order_id = mList.get(position).getId();

    if (convertView == null) {
      convertView = LayoutInflater.from(context).inflate(R.layout.orders_list_item, null);
      holder = new ViewHolder();

      LinearLayout llayout =
          (LinearLayout) convertView.findViewById(R.id.llayout_order_item);
      if (1 == mList.get(position).getOrders().size()) {
        LayoutInflater inflater = LayoutInflater.from(context);
        LinearLayout ll_one_order =
            (LinearLayout) inflater.inflate(R.layout.one_order_item, null);
        ll_one_order.setOnClickListener(new View.OnClickListener() {

          @Override public void onClick(View v) {
            // TODO Auto-generated method stub
            Log.d(TAG, "onClick ");
            Intent intent = new Intent(context, OrderDetailActivity.class);
            intent.putExtra("orderinfo", order_id);
            Log.d(TAG, "transfer orderid  " + order_id + " to OrderDetailActivity");
            context.startActivity(intent);
          }
        });

        ImageView img_goods = (ImageView) ll_one_order.findViewById(R.id.img_good);
        TextView tx_good_name = (TextView) ll_one_order.findViewById(R.id.tx_good_name);
        TextView tx_good_price = (TextView) ll_one_order.findViewById(R.id.tx_good_price);
        TextView tx_good_size = (TextView) ll_one_order.findViewById(R.id.tx_good_size);
        TextView tx_good_num = (TextView) ll_one_order.findViewById(R.id.tx_good_num);

        tx_good_name.setText(mList.get(position).getOrders().get(0).getTitle().substring(0, 8) + "...");
        tx_good_price.setText("¥" + mList.get(position).getOrders().get(0).getPayment());
        tx_good_size.setText(mList.get(position).getOrders().get(0).getSkuName());
        tx_good_num.setText(
            Integer.toString(mList.get(position).getOrders().get(0).getNum()));

        ViewUtils.loadImgToImgView(context, img_goods, data.get(position).get("img_url"));
        llayout.addView(ll_one_order);
        holder.ll_goods = ll_one_order;
      }
      else {
        List<String> mDatas = new ArrayList<String>();
        AllOrdersListAdapter.fillPicPath(mDatas, mList.get(position).getOrders());
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
              @Override public void onCurrentImgChanged(int position, View viewIndicator) {

              }
            });
        //添加点击回调
        mHorizontalScrollView.setOnItemClickListener(
            new MyHorizontalScrollView.OnItemClickListener() {

              @Override public void onClick(View view, int position) {
                Intent intent = new Intent(context, OrderDetailActivity.class);
                intent.putExtra("orderinfo", order_id);
                Log.d(TAG, "transfer orderid  " + order_id + " to OrderDetailActivity");
                context.startActivity(intent);
              }
            });
        //设置适配器
        mHorizontalScrollView.initDatas(mAdapter);
        llayout.addView(mHorizontalScrollView);
        holder.mHorizontalScrollView = mHorizontalScrollView;
      }
      convertView.setTag(holder);
    }
    else {
      holder = (ViewHolder) convertView.getTag();
    }



    tx_payment = (TextView) convertView.findViewById(R.id.tx_order_actual_payment);
    tx_order_sate = (TextView) convertView.findViewById(R.id.tx_order_state);

    tx_payment.setText("实付金额" + data.get(position).get("payment"));
    tx_order_sate.setText(data.get(position).get("orderState"));



    return convertView;
  }

  public static class ViewHolder {
    public LinearLayout ll_goods;
    MyHorizontalScrollView mHorizontalScrollView;
  }
}

