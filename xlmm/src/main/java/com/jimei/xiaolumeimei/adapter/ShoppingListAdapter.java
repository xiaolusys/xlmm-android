package com.jimei.xiaolumeimei.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jimei.library.utils.ViewUtils;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.entities.OderCarryBean;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.OrderLogisticActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by itxuye(www.itxuye.com) on 2016/02/18.
 * <p>
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class ShoppingListAdapter
        extends RecyclerView.Adapter<ShoppingListAdapter.ShoppingListVH> {


    private List<OderCarryBean.ResultsEntity> mList;
    private Context mContext;

    public ShoppingListAdapter(Context context) {
        this.mContext = context;
        mList = new ArrayList<>();
    }

    public void updateWithClear(List<OderCarryBean.ResultsEntity> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void update(List<OderCarryBean.ResultsEntity> list) {

        mList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public ShoppingListVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_shoppinglist, parent, false);
        return new ShoppingListVH(v);
    }

    @Override
    public void onBindViewHolder(ShoppingListVH holder, int position) {
        holder.flagRl.setVisibility(View.GONE);
        OderCarryBean.ResultsEntity resultsEntity = mList.get(position);
        try {
            if (position == 0) {
                showCategory(holder);
            } else {
                boolean theCategoryOfLastEqualsToThis = mList.get(position - 1)
                        .getDate_field()
                        .equals(mList.get(position).getDate_field());
                if (!theCategoryOfLastEqualsToThis) {
                    showCategory(holder);
                } else {
                    hideCategory(holder);
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        holder.shoptime.setText(resultsEntity.getDate_field());
        if (TextUtils.isEmpty(resultsEntity.getSku_img())) {
            Glide.with(mContext)
                    .load(R.mipmap.ic_launcher)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    //.placeholder(R.drawable.place_holder)
                    .centerCrop()
                    .into(holder.picPath);
        } else {
            ViewUtils.loadImgToImgView(mContext, holder.picPath, resultsEntity.getSku_img());
        }
        holder.getStatusDisplay.setText(resultsEntity.getStatus_display());
        holder.wxordernick.setText(resultsEntity.getContributor_nick());
        holder.timeDisplay.setText(resultsEntity.getCreated().substring(11, 16));
        holder.totalMoneyTv.setText("实付" + resultsEntity.getOrder_value());
        if (resultsEntity.getCarry_type() == 2) {
            holder.flagTv.setText("APP");
            holder.flagRl.setVisibility(View.VISIBLE);
        } else if (resultsEntity.getCarry_type() == 3) {
            holder.flagTv.setText("下属订单");
            holder.flagRl.setVisibility(View.VISIBLE);
        } else {
            holder.flagRl.setVisibility(View.GONE);
        }
        holder.tichengCash.setText(
                "收益" + (float) (Math.round(resultsEntity.getCarry_num() * 100)) / 100);
        holder.totalCash.setText(
                "总收益 " + (float) (Math.round(resultsEntity.getToday_carry() * 100)) / 100);
        if ("确定收益".equals(resultsEntity.getStatus_display())) {
            holder.content.setOnClickListener(v -> {
                Intent intent = new Intent(mContext, OrderLogisticActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("company_code",resultsEntity.getCompany_code());
                bundle.putString("packetid",resultsEntity.getPacketid());
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            });
        }
    }

    private void showCategory(ShoppingListVH holder) {
        if (!isVisibleOf(holder.category)) holder.category.setVisibility(View.VISIBLE);
    }

    private void hideCategory(ShoppingListVH holder) {
        if (isVisibleOf(holder.category)) holder.category.setVisibility(View.GONE);
    }

    private boolean isVisibleOf(View view) {
        return view.getVisibility() == View.VISIBLE;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ShoppingListVH extends RecyclerView.ViewHolder {
        int id = R.layout.item_shoppinglist;
        @Bind(R.id.shoptime)
        TextView shoptime;
        @Bind(R.id.total_cash)
        TextView totalCash;
        @Bind(R.id.pic_path)
        ImageView picPath;
        @Bind(R.id.time_nick)
        TextView timeDisplay;
        @Bind(R.id.get_status_display)
        TextView getStatusDisplay;
        @Bind(R.id.wxordernick)
        TextView wxordernick;
        @Bind(R.id.ticheng_cash)
        TextView tichengCash;
        @Bind(R.id.category)
        LinearLayout category;
        @Bind(R.id.content)
        RelativeLayout content;
        @Bind(R.id.total_money)
        TextView totalMoneyTv;
        @Bind(R.id.flag)
        RelativeLayout flagRl;
        @Bind(R.id.flag_tv)
        TextView flagTv;

        public ShoppingListVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
