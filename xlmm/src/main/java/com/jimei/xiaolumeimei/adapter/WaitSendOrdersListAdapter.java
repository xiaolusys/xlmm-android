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

import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.entities.AllOrdersBean;
import com.jimei.xiaolumeimei.utils.ViewUtils;
import com.jimei.xiaolumeimei.widget.MyHorizontalScrollView;
import com.jimei.xiaolumeimei.widget.NestedListView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.ui.activity.trade.OrderDetailActivity;

public class WaitSendOrdersListAdapter extends BaseAdapter {
  private static final String TAG = "WaitRecvOrdersAdapter";
  private Activity context;
  List<HashMap<String, String>> data;
  private List<AllOrdersBean.ResultsEntity> mList;

  public WaitSendOrdersListAdapter(Activity context) {
    mList = new ArrayList<AllOrdersBean.ResultsEntity>();
    this.data = new ArrayList<HashMap<String, String>>();
    this.context = context;
  }

  public static void fillPicPath(List<String> mDatas,
      List<AllOrdersBean.ResultsEntity.OrdersEntity> good_list) {
    for (int i = 0; i < good_list.size(); i++) {
      mDatas.add(good_list.get(i).getPicPath());
    }
  }

  public void updateWithClear(List<AllOrdersBean.ResultsEntity> list) {
    mList.clear();
    mList.addAll(list);
    notifyDataSetChanged();
  }

  public void update(List<AllOrdersBean.ResultsEntity> list) {

    float payment = 0;
    String orderState = "";
    String picpath;
    int state;

    Log.d(TAG, "dataSource.size " + list.size());
    for (int i = 0; i < list.size(); i++) {
      HashMap<String, String> map = new HashMap<String, String>();
      payment = (float) list.get(i).getPayment();
      orderState = list.get(i).getStatusDisplay();
      picpath = list.get(i).getOrderPic();
      state = list.get(i).getStatus();

      map.put("payment", Float.toString(payment));
      map.put("orderState", orderState);
      map.put("img_url", picpath);
      map.put("state", Integer.toString(state));

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
    LinearLayout llayout;
    final int order_id = mList.get(position).getId();

    if (convertView == null) {
      Log.d(TAG, "convertView == null " );
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

      if(position == holder.position){
        Log.d(TAG, "same position update,do nothing " );
      }
      else {
        Log.d(TAG, "NOT same position update,CONVERT " );
        if(1== holder.goods_num){
          holder.ll_goods.setVisibility(View.GONE);
          Log.d(TAG, "ll_goods gone " );
        }
        else {
          holder.mHorizontalScrollView.setVisibility(View.GONE);
          Log.d(TAG, "mHorizontalScrollView gone " );
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

    Log.d(TAG, "payment  "+data.get(position).get("payment") + " " + data.get(position).get("orderState"));
    tx_order_sate.setText(data.get(position).get("orderState"));



    return convertView;
  }

  private void showTime(int state, String crtTime, View convertView, int order_id) {


    if (XlmmConst.ORDER_STATE_WAITPAY == state) {
      TextView tx_order_left_paytime =
          (TextView) convertView.findViewById(R.id.tx_order_left_paytime);
      cn.iwgang.countdownview.CountdownView cv_lefttime =
          (cn.iwgang.countdownview.CountdownView) convertView.findViewById(
              R.id.cv_lefttime);

      tx_order_left_paytime.setVisibility(View.VISIBLE);
      cv_lefttime.setVisibility(View.VISIBLE);
      cv_lefttime.start(calcLeftTime(crtTime));

    }
  }

  private long calcLeftTime(String crtTime) {
    long left = 0;
    Date now = new Date();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    try {
      crtTime = crtTime.replace("T"," ");
      Date crtdate = format.parse(crtTime);
      Log.d(TAG, " crtdate  " + crtdate.getTime() + "now "+now.getTime());
      if (crtdate.getTime() + 20 * 60 * 1000 - now.getTime() > 0) {
        left = crtdate.getTime() + 20 * 60 * 1000 - now.getTime();
      }
    } catch (Exception e) {
      Log.e(TAG, "left time get failed ");
      e.printStackTrace();
    }

    Log.d(TAG, "left time  " + left);

    return left;
  }

  private LinearLayout crt_one_goods_view(int position, int order_id) {
    Log.d(TAG, "crt_one_goods_view ");

    LayoutInflater inflater = LayoutInflater.from(context);
    LinearLayout ll_one_order =
        (LinearLayout) inflater.inflate(R.layout.one_order_item, null);
    ll_one_order.setOnClickListener(new View.OnClickListener() {

      @Override public void onClick(View v) {
        // TODO Auto-generated method stub
        Log.d(TAG, "onClick ");
        Intent intent = new Intent(context, OrderDetailActivity.class);
        intent.putExtra("orderinfo", order_id);
        Log.d(TAG, "LinearLayout transfer orderid  " + order_id + " to OrderDetailActivity");
        context.startActivity(intent);
        context.finish();
      }
    });

    ImageView img_goods = (ImageView) ll_one_order.findViewById(R.id.img_good);
    TextView tx_good_name = (TextView) ll_one_order.findViewById(R.id.tx_good_name);
    TextView tx_good_price = (TextView) ll_one_order.findViewById(R.id.tx_good_price);
    TextView tx_good_size = (TextView) ll_one_order.findViewById(R.id.tx_good_size);
    TextView tx_good_num = (TextView) ll_one_order.findViewById(R.id.tx_good_num);

    if(mList.get(position).getOrders().get(0).getTitle().length() >= 9) {
      tx_good_name.setText(mList.get(position).getOrders().get(0).getTitle().substring(0, 8) + "...");
    }
    else {
      tx_good_name.setText(mList.get(position).getOrders().get(0).getTitle());
    }
    tx_good_price.setText("¥" + mList.get(position).getOrders().get(0).getPayment());
    tx_good_size.setText("尺码:" + mList.get(position).getOrders().get(0).getSkuName());
    tx_good_num.setText(
        "x" + Integer.toString(mList.get(position).getOrders().get(0).getNum()));

    ViewUtils.loadImgToImgView(context, img_goods, data.get(position).get("img_url"));

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

          @Override public void onClick(View view, int position) {
            Intent intent = new Intent(context, OrderDetailActivity.class);
            intent.putExtra("orderinfo", order_id);
            Log.d(TAG, "mHorizontalScrollView transfer orderid  " + order_id + " to OrderDetailActivity");
            context.startActivity(intent);
          }
        });
    //设置适配器
    mHorizontalScrollView.initDatas(mAdapter);

    return mHorizontalScrollView;
  }

  public static class ViewHolder {
    int goods_num;
    int position;
    LinearLayout ll_goods;
    MyHorizontalScrollView mHorizontalScrollView;

  }
}

