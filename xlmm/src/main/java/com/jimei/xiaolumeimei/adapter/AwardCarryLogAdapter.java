package com.jimei.xiaolumeimei.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jimei.library.utils.ViewUtils;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.entities.AwardCarryBean;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by itxuye(www.itxuye.com) on 2016/02/18.
 * <p>
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class AwardCarryLogAdapter
    extends RecyclerView.Adapter<AwardCarryLogAdapter.CarryLogListVH> {

    private Context context;
    private List<AwardCarryBean.ResultsEntity> mList;

    public AwardCarryLogAdapter(Context context) {
        mList = new ArrayList<>();
        this.context = context;
    }

    public void updateWithClear(List<AwardCarryBean.ResultsEntity> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void update(List<AwardCarryBean.ResultsEntity> list) {

        mList.addAll(list);
        notifyDataSetChanged();
    }

    private void showCategory(CarryLogListVH holder) {
        if (!isVisibleOf(holder.category)) holder.category.setVisibility(View.VISIBLE);
    }

    private void hideCategory(CarryLogListVH holder) {
        if (isVisibleOf(holder.category)) holder.category.setVisibility(View.GONE);
    }

    private boolean isVisibleOf(View view) {
        return view.getVisibility() == View.VISIBLE;
    }

    @Override
    public CarryLogListVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v =
            LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carryloglist, parent, false);
        return new CarryLogListVH(v);
    }

    @Override
    public void onBindViewHolder(CarryLogListVH holder, int position) {

        AwardCarryBean.ResultsEntity resultsEntity = mList.get(position);

        try {
            if (position == 0) {
                showCategory(holder);
            } else {
                boolean theCategoryOfLastEqualsToThis =
                    mList.get(position - 1).getmDate_field().equals(mList.get(position).getmDate_field());
                if (!theCategoryOfLastEqualsToThis) {
                    showCategory(holder);
                } else {
                    hideCategory(holder);
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        holder.shoptime.setText(resultsEntity.getmDate_field());
        if (null != resultsEntity.getContributorImg()) {
            ViewUtils.loadImgToImgViewWithTransformCircle(context,
                holder.picPath, resultsEntity.getContributorImg());
        } else {
            Glide.with(context).load(R.drawable.img_jiang).into(holder.picPath);
        }

        holder.totalCash.setText(
            "总收益 " + (float) (Math.round(resultsEntity.getTodayCarry() * 100)) / 100);

        holder.tichengCash.setText("+" + (float) (Math.round(resultsEntity.getCarryNum() * 100)) / 100);

        holder.timeNick.setText(resultsEntity.getContributorNick());
        holder.timeDisplay.setText(resultsEntity.getCreated().substring(11, 16));
        holder.wxordernick.setText(resultsEntity.getmCarryDescription());
        holder.tvStatus.setText(resultsEntity.getStatusDisplay());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class CarryLogListVH extends RecyclerView.ViewHolder {
        int id = R.layout.item_carryloglist;
        @Bind(R.id.shoptime)
        TextView shoptime;
        @Bind(R.id.total_cash)
        TextView totalCash;
        @Bind(R.id.category)
        RelativeLayout category;
        @Bind(R.id.pic_path)
        ImageView picPath;
        @Bind(R.id.time_nick)
        TextView timeNick;
        @Bind(R.id.time_display)
        TextView timeDisplay;
        @Bind(R.id.wxordernick)
        TextView wxordernick;
        @Bind(R.id.ticheng_cash)
        TextView tichengCash;
        @Bind(R.id.content)
        RelativeLayout content;
        @Bind(R.id.tv_status)
        TextView tvStatus;

        public CarryLogListVH(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
