package com.jimei.xiaolumeimei.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jimei.library.utils.JUtils;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.entities.CouponEntity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CouponListAdapter extends BaseAdapter {
    private static final String TAG = "CouponListAdapter";
    private Context context;
    private List<CouponEntity> mList;
    private int mCouponTyp;
    private String mSelecteCouponid;

    public CouponListAdapter(Context context) {
        mList = new ArrayList<>();
        this.context = context;
    }

    public void updateWithClear(List<CouponEntity> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void update(List<CouponEntity> list, int coupon_type, String selected_couponid) {
        Log.d(TAG, "dataSource.size " + list.size());
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
        Log.d(TAG, "getView ");
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_coupon, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (mCouponTyp == XlmmConst.UNUSED_COUPON || mCouponTyp == XlmmConst.GOOD_COUPON) {
            holder.tv_coupon_value.setTextColor(Color.parseColor("#F05050"));
            holder.rl.setBackgroundResource(R.drawable.bg_img_coupon);
        } else if (mCouponTyp == XlmmConst.PAST_COUPON) {
            holder.tv_coupon_value.setTextColor(Color.parseColor("#B4B4B4"));
            holder.tv_coupon_info.setTextColor(Color.parseColor("#D2D2D2"));
            holder.tv_coupon_crttime.setTextColor(Color.parseColor("#D2D2D2"));
            holder.titleTv.setTextColor(Color.parseColor("#D2D2D2"));
            holder.use_fee.setTextColor(Color.parseColor("#D2D2D2"));
            holder.rl.setBackgroundResource(R.drawable.bg_img_pastcoupon);
        } else if (mCouponTyp == XlmmConst.USED_COUPON) {
            holder.tv_coupon_value.setTextColor(context.getResources().getColor(R.color.text_color_62));
            holder.rl.setBackgroundResource(R.drawable.bg_img_usedcoupon);
        } else {
            holder.tv_coupon_value.setTextColor(Color.parseColor("#646464"));
            holder.rl.setBackgroundResource(R.drawable.bg_img_dcoupon);
        }
        double coupon_value = mList.get(position).getCoupon_value();
        DecimalFormat df = new DecimalFormat("0.00");
        double format = Double.parseDouble(df.format(coupon_value));
        if (Math.round(format * 100) % 100 == 0) {
            holder.tv_coupon_value.setText("￥" + Math.round(format * 100) / 100);
        } else {
            holder.tv_coupon_value.setText("￥" + format);
        }
        holder.tv_coupon_info.setText(mList.get(position).getTitle());
        holder.titleTv.setText(mList.get(position).getPros_desc());
        String start_time = mList.get(position).getStart_time();
        String deadline = mList.get(position).getDeadline();
        String created = mList.get(position).getCreated();
        if (start_time != null) {
            holder.tv_coupon_crttime.setText("期限  " + start_time.replaceAll("T", " ")
                    + "  至  " + deadline.replaceAll("T", " "));
        } else {
            holder.tv_coupon_crttime.setText("期限  " + created.replaceAll("T", " ")
                    + "  至  " + deadline.replaceAll("T", " "));
        }
        JUtils.Log(TAG, "couponno= " + mList.get(position).getId() + " selected couponno=" + mSelecteCouponid);
        holder.use_fee.setText(mList.get(position).getUse_fee_des());
        if ((mSelecteCouponid != null) && (!mSelecteCouponid.isEmpty())
                && mSelecteCouponid.equals(Integer.toString(mList.get(position).getId()))) {
            holder.img_selected.setVisibility(View.VISIBLE);
        } else {
            holder.img_selected.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }

    class ViewHolder {
        TextView tv_coupon_value;
        TextView tv_coupon_info;
        TextView tv_coupon_crttime;
        ImageView img_selected;
        TextView use_fee;
        TextView titleTv;
        RelativeLayout rl;

        public ViewHolder(View itemView) {
            tv_coupon_value = (TextView) itemView.findViewById(R.id.tv_coupon_value);
            tv_coupon_info = (TextView) itemView.findViewById(R.id.tv_coupon_info);
            tv_coupon_crttime = (TextView) itemView.findViewById(R.id.tv_coupon_crttime);
            img_selected = (ImageView) itemView.findViewById(R.id.img_selected);
            use_fee = (TextView) itemView.findViewById(R.id.use_fee);
            titleTv = (TextView) itemView.findViewById(R.id.title);
            rl = (RelativeLayout) itemView.findViewById(R.id.rl);
        }
    }


}

