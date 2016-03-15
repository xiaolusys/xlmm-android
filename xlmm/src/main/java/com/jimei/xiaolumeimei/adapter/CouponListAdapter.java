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
import com.jude.utils.JUtils;

public class CouponListAdapter extends BaseAdapter {
    private static final String TAG = "CouponListAdapter";
    private Context context;
    private List<CouponBean.ResultsEntity> mList;
    private int mCouponTyp;
    private String mSelecteCouponid;

    public CouponListAdapter(Context context) {
        mList = new ArrayList<CouponBean.ResultsEntity>();
        this.context = context;
    }

    public void updateWithClear(List<CouponBean.ResultsEntity> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void update(List<CouponBean.ResultsEntity> list, int coupon_type, String selected_couponid) {
        float coupon_value = 0;
        String usestate = "";
        String crttime = "";
        String deadline = "";
        String usescope = "";
        String coupon_no= "";

        Log.d(TAG,"dataSource.size "+ list.size());
        mCouponTyp = coupon_type;
        mSelecteCouponid = selected_couponid;

        mList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
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
        Log.d(TAG,"getView ");

        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_coupon, null);
            if(mCouponTyp == XlmmConst.UNUSED_COUPON) {
                convertView.setBackgroundResource(R.drawable.bg_img_coupon);
            }
            else if(mCouponTyp == XlmmConst.PAST_COUPON){
                convertView.setBackgroundResource(R.drawable.bg_img_pastcoupon);
            }

            holder.tv_coupon_value = (TextView) convertView.findViewById(R.id.tv_coupon_value);
            holder.tv_coupon_info = (TextView) convertView.findViewById(R.id.tv_coupon_info);
            holder.tv_coupon_crttime = (TextView) convertView.findViewById(R.id.tv_coupon_crttime);
            holder.tv_coupon_deadline = (TextView) convertView.findViewById(R.id.tv_coupon_deadline);
            holder.tv_coupon_type = (TextView) convertView.findViewById(R.id.tv_coupon_type);
            holder.tv_coupon_no = (TextView) convertView.findViewById(R.id.tv_coupon_no);
            holder.img_selected= (ImageView) convertView.findViewById(R.id.img_selected);

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_coupon_value.setText("￥"+ Math.round(mList.get(position).getCoupon_value()*100)/100);
        holder.tv_coupon_info.setText(mList.get(position).getTitle());
        holder.tv_coupon_crttime.setText("使用期限"+mList.get(position).getCreated().replace("T"," "));
        holder.tv_coupon_deadline.setText("至"+mList.get(position).getDeadline().replace("T"," "));
        holder.tv_coupon_type.setText("使用范围"+mList.get(position).getCoupon_type_display());
        holder.tv_coupon_no.setText("优惠编码"+mList.get(position).getCoupon_no());
        JUtils.Log(TAG, "couponno= "+ mList.get(position).getId() + " selected couponno="+mSelecteCouponid);
        if((mSelecteCouponid != null) && (! mSelecteCouponid.isEmpty())
                && mSelecteCouponid.equals(Integer.toString(mList.get(position).getId()))){
            holder.img_selected.setVisibility(View.VISIBLE);
        }
        else{
            holder.img_selected.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }

    class ViewHolder {
        TextView tv_coupon_value;
        TextView tv_coupon_info;
        TextView tv_coupon_crttime;
        TextView tv_coupon_deadline;
        TextView tv_coupon_type;
        TextView tv_coupon_no;
        ImageView img_selected;
    }


}

