package com.jimei.xiaolumeimei.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.entities.BudgetdetailBean;
import com.jimei.xiaolumeimei.ui.activity.user.WalletDetailActivity;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by itxuye(www.itxuye.com) on 2016/02/26.
 * <p>
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class UserWalletAdapter extends RecyclerView.Adapter<UserWalletAdapter.UserWalletVH> {

    private List<BudgetdetailBean.ResultsEntity> mList;
    private Context mContext;

    public UserWalletAdapter(Context context) {
        this.mContext = context;
        mList = new ArrayList<>();
    }

    public void updateWithClear(List<BudgetdetailBean.ResultsEntity> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void update(List<BudgetdetailBean.ResultsEntity> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public UserWalletVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_userwallet, parent, false);
        AutoUtils.autoSize(view);
        return new UserWalletVH(view);
    }

    @Override
    public void onBindViewHolder(UserWalletVH holder, int position) {

        BudgetdetailBean.ResultsEntity resultsEntity = mList.get(position);

        holder.tvTime.setText(resultsEntity.getBudgetDate());
        holder.tvDesc.setText(resultsEntity.getDesc());

        if (0 == resultsEntity.getStatus()) {
            holder.tvStatus.setText("已确定");
        } else if (1 == resultsEntity.getStatus()) {
            holder.tvStatus.setText("已取消");
        } else if (2 == resultsEntity.getStatus()) {
            holder.tvStatus.setText("待确定");
        }

        if (0 == resultsEntity.getBudgetType()) {

            holder.tvMoneychange.setText("+ " + resultsEntity.getBudegetDetailCash() + "元  ");
//            holder.tvMoneychange.setTextColor(Color.parseColor("#F5B123"));
        } else if (1 == resultsEntity.getBudgetType()) {
            holder.tvMoneychange.setText("- " + resultsEntity.getBudegetDetailCash() + "元  ");
        }
        bindOnClickListener(holder.itemView, position);
    }

    private void bindOnClickListener(View itemView, int position) {
        BudgetdetailBean.ResultsEntity entity = mList.get(position);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, WalletDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("entity",entity);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }


    static class UserWalletVH extends RecyclerView.ViewHolder {

        int id = R.layout.item_userwallet;
        View itemView;
        @Bind(R.id.tv_time)
        TextView tvTime;
        @Bind(R.id.tv_desc)
        TextView tvDesc;
        @Bind(R.id.tv_status)
        TextView tvStatus;
        @Bind(R.id.tv_moneychange)
        TextView tvMoneychange;

        public UserWalletVH(View itemView) {
            super(itemView);
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);
        }
    }
}
