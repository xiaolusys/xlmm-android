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

import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.entities.AllOrdersBean;
import com.jimei.xiaolumeimei.entities.CouponBean;
import com.jimei.xiaolumeimei.widget.MyHorizontalScrollView;
import com.jimei.xiaolumeimei.widget.NestedListView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.ui.activity.trade.OrderDetailActivity;

public class CouponListAdapter extends BaseAdapter {
    private static final String TAG = "CouponListAdapter";
    private Context context;
    List<HashMap<String, String>> data;
    private List<CouponBean.ResultsEntity> mList;

    public CouponListAdapter(Context context) {
        mList = new ArrayList<CouponBean.ResultsEntity>();
        this.data = new ArrayList<HashMap<String, String>>();
        this.context = context;
    }

    public void updateWithClear(List<CouponBean.ResultsEntity> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void update(List<CouponBean.ResultsEntity> list, int coupon_type) {
        float coupon_value = 0;
        String usestate = "";
        String crttime = "";
        String deadline = "";
        String usescope = "";
        String coupon_no= "";

        Log.d(TAG,"dataSource.size "+ list.size());
        for (int i = 0; i < list.size(); i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            coupon_value = (float)list.get(i).getCoupon_value();
            usestate = list.get(i).getTitle();

            crttime = list.get(i).getCreated();
            deadline = list.get(i).getDeadline();
            usescope = list.get(i).getCoupon_type_display();

            coupon_no = list.get(i).getCoupon_no();

            map.put("coupon_type", Integer.toString(coupon_type) );
            map.put("coupon_value", Float.toString(coupon_value) );
            map.put("usestate", usestate);
            map.put("crttime", crttime);
            map.put("deadline", deadline);
            map.put("usescope", usescope);
            map.put("coupon_no", coupon_no);

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

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_coupon, null);
            if(Integer.parseInt(data.get(position).get("coupon_type")) == XlmmConst.UNUSED_COUPON) {
                convertView.setBackgroundResource(R.drawable.bg_img_coupon);
            }
            else if(Integer.parseInt(data.get(position).get("coupon_type")) == XlmmConst.PAST_COUPON){
                convertView.setBackgroundResource(R.drawable.bg_img_pastcoupon);
            }
        }

        TextView tv_coupon_value = (TextView) convertView.findViewById(R.id.tv_coupon_value);
        TextView tv_coupon_info = (TextView) convertView.findViewById(R.id.tv_coupon_info);
        TextView tv_coupon_crttime = (TextView) convertView.findViewById(R.id.tv_coupon_crttime);
        TextView tv_coupon_deadline = (TextView) convertView.findViewById(R.id.tv_coupon_deadline);
        TextView tv_coupon_type = (TextView) convertView.findViewById(R.id.tv_coupon_type);
        TextView tv_coupon_no = (TextView) convertView.findViewById(R.id.tv_coupon_no);

        tv_coupon_value.setText("￥"+data.get(position).get("coupon_value"));
        tv_coupon_info.setText(data.get(position).get("usestate"));
        tv_coupon_crttime.setText("使用期限"+data.get(position).get("crttime").replace("T"," "));
        tv_coupon_deadline.setText("至"+data.get(position).get("deadline").replace("T"," "));
        tv_coupon_type.setText("使用范围"+data.get(position).get("usescope"));
        tv_coupon_no.setText("优惠编码"+data.get(position).get("coupon_no"));

        return convertView;
    }


}

