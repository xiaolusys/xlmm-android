package com.jimei.xiaolumeimei.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.entities.LadyListBean;
import com.jimei.xiaolumeimei.ui.activity.product.ProductPopDetailActvityWeb;
import com.zhy.autolayout.utils.AutoUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 优尼世界 on 15/12/29.
 * <p>
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class LadyListActivityAdapter
        extends RecyclerView.Adapter<LadyListActivityAdapter.LadyListVH> {

    private List<LadyListBean.ResultsEntity> mList;

    private Context mContext;

    public LadyListActivityAdapter(Context mContext) {
        this.mContext = mContext;
        mList = new ArrayList<>();
    }

    public void updateWithClear(List<LadyListBean.ResultsEntity> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void update(List<LadyListBean.ResultsEntity> list) {

        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void clear() {
        mList.clear();
        notifyDataSetChanged();
    }

    public LadyListBean.ResultsEntity getData(int postion) {

        return mList.get(postion);
    }

    @Override
    public LadyListVH onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_todaylist, parent, false);
        return new LadyListVH(view);
    }

    @Override
    public void onBindViewHolder(final LadyListVH holder, int position) {

        final LadyListBean.ResultsEntity resultsEntity = mList.get(position);
        LadyListBean.ResultsEntity.ProductModelEntity productModel =
                resultsEntity.getProductModel();

        String headImg = resultsEntity.getHeadImg();

        boolean isSaleopen = resultsEntity.isIsSaleopen();

        try {
            if (isSaleopen) {

                boolean isSaleOut = productModel.isIsSaleOut();

                if (isSaleOut) {
                    holder.saleout.setVisibility(View.VISIBLE);
                } else {
                    holder.saleout.setVisibility(View.INVISIBLE);
                }
            } else {
                holder.saleout.setVisibility(View.VISIBLE);
            }

            if (productModel.getName().length() <= 9) {
                holder.childlistName.setText(productModel.getName());
            } else {
                holder.childlistName.setText(productModel.getName().substring(0, 8) + "...");
            }
        } catch (Exception ex) {

        }

        holder.childlistAgentPrice.setText("¥" + resultsEntity.getProductLowestPrice());
        holder.childlistStdsalePrice.setText("/¥" + resultsEntity.getStdSalePrice());

        String[] temp = headImg.split("http://image.xiaolu.so/");
        String head_img = "";
        if (temp.length > 1) {
            try {
                head_img += "http://image.xiaolu.so/"
                        + URLEncoder.encode(temp[1], "utf-8")
                        + "?imageMogr2/format/jpg/size-limit/30k/thumbnail/289/quality/90";
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        holder.card.setTag(new Object());
        Glide.with(mContext)
                .load(head_img)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.parceholder)
                .centerCrop()
                .into(holder.childlistImage)
                .getSize((width, height) -> {
                    if (!holder.card.isShown()) holder.card.setVisibility(View.VISIBLE);
                });

        holder.card.setOnClickListener(v -> {

            int modelId = mList.get(position).getModelId();
            Intent intent = new Intent(mContext, ProductPopDetailActvityWeb.class);

            SharedPreferences sharedPreferences =
                    mContext.getSharedPreferences("xlmmCookiesAxiba", Context.MODE_PRIVATE);
            String cookies = sharedPreferences.getString("cookiesString", "");
            String domain = sharedPreferences.getString("cookiesDomain", "");
            Bundle bundle = new Bundle();
            bundle.putString("cookies", cookies);
            bundle.putString("domain", domain);
            bundle.putString("Cookie", sharedPreferences.getString("Cookie", ""));
            bundle.putString("actlink", mList.get(position).getWebUrl());
            bundle.putInt("id", modelId);
            intent.putExtras(bundle);
            mContext.startActivity(intent);
        });
    }

    @Override
    public void onViewRecycled(LadyListVH holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    static class LadyListVH extends RecyclerView.ViewHolder {
        View card;
        @Bind(R.id.childlist_image)
        ImageView childlistImage;
        @Bind(R.id.childlist_name)
        TextView childlistName;
        @Bind(R.id.childlist_agent_price)
        TextView childlistAgentPrice;
        @Bind(R.id.childlist_stdsale_price)
        TextView childlistStdsalePrice;
        @Bind(R.id.saleout)
        TextView saleout;

        public LadyListVH(View itemView) {
            super(itemView);
            card = itemView;
            AutoUtils.autoSize(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
