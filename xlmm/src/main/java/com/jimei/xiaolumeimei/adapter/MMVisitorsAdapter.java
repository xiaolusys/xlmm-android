package com.jimei.xiaolumeimei.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jimei.library.utils.ViewUtils;
import com.jimei.library.widget.glidemoudle.CropCircleTransformation;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.entities.MMVisitorsBean;
import com.jude.utils.JUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by itxuye(www.itxuye.com) on 2016/03/22.
 * <p>
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class MMVisitorsAdapter extends RecyclerView.Adapter<MMVisitorsAdapter.ViewHolder> {
    private static final String TAG = "MamaFansAdapter";
    private Activity context;
    private List<MMVisitorsBean.ResultsEntity> mList;

    public MMVisitorsAdapter(Activity context) {
        mList = new ArrayList<>();
        this.context = context;
    }

    public void updateWithClear(List<MMVisitorsBean.ResultsEntity> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void update(List<MMVisitorsBean.ResultsEntity> list) {

        mList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_mama_visitor, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        JUtils.Log(TAG, "getView ");
        MMVisitorsBean.ResultsEntity resultsEntity = mList.get(position);
        String created = resultsEntity.getCreated().replace("T", " ");
        String time = created.substring(0, 10);
        holder.timeTv.setText(time);
        if (position == 0) {
            holder.timeLayout.setVisibility(View.VISIBLE);
        } else {
            if (time.equals(mList.get(position - 1).getCreated().substring(0, 10))) {
                holder.timeLayout.setVisibility(View.GONE);
            } else {
                holder.timeLayout.setVisibility(View.VISIBLE);
            }
        }
        holder.tvFans.setText(resultsEntity.getVisitorNick());
        holder.tvInfo.setText(resultsEntity.getVisitorDescription());
        holder.tvTime.setText(created.substring(5, 19));
        if (TextUtils.isEmpty(resultsEntity.getVisitorImg())) {
            Glide.with(context)
                    .load(R.mipmap.ic_launcher)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .bitmapTransform(new CropCircleTransformation(context))
                    .into(holder.imgFans);
        } else {
            ViewUtils.loadImgToImgViewWithTransformCircle(context, holder.imgFans,
                    resultsEntity.getVisitorImg());
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        int id = R.layout.item_mamafans;
        @Bind(R.id.text_view_time)
        TextView timeTv;
        @Bind(R.id.ll_layout_time)
        LinearLayout timeLayout;
        @Bind(R.id.img_fans)
        ImageView imgFans;
        @Bind(R.id.tv_fans)
        TextView tvFans;
        @Bind(R.id.tv_info)
        TextView tvInfo;
        @Bind(R.id.tv_time)
        TextView tvTime;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

