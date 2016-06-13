package com.jimei.xiaolumeimei.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.jimei.xiaolumeimei.ui.activity.product.ProductDetailActvityWeb;
import com.jimei.xiaolumeimei.ui.activity.product.TongkuanActivity;
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
public class LadyListAdapter extends RecyclerView.Adapter<LadyListAdapter.LadyListVH> {

    private List<LadyListBean.ResultsEntity> mList;
    private Context mContext;
    private Fragment context;

    public LadyListAdapter(Context mContext, Fragment context) {
        this.mContext = mContext;
        this.context = context;
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

    public LadyListBean.ResultsEntity getData(int postion) {
        return mList.get(postion);
    }

    @Override
    public LadyListVH onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_childlist, parent, false);
        return new LadyListVH(view);
    }

    @Override
    public void onBindViewHolder(final LadyListVH holder, int position) {

        final LadyListBean.ResultsEntity resultsEntity = mList.get(position);
        //ChildListBean.ResultsEntity.CategoryEntity category = resultsEntity.getCategory();
        LadyListBean.ResultsEntity.ProductModelEntity productModel =
                resultsEntity.getProductModel();

        String headImg = resultsEntity.getHeadImg();

        boolean isSaleopen = resultsEntity.isIsSaleopen();

        try {
            if (isSaleopen) {

                boolean isSaleOut = productModel.isIsSaleOut();
                boolean isSingleSpec = productModel.isIsSingleSpec();

                if (isSaleOut && isSingleSpec) {
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
        Glide.with(context)
                .load(head_img)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.parceholder)
                .centerCrop()
                .into(holder.childlistImage)
                .getSize((width, height) -> {
                    if (!holder.card.isShown()) holder.card.setVisibility(View.VISIBLE);
                });

        holder.card.setOnClickListener(v -> {

            String product_id = null;
            int model_id = 0;
            String name = null;
            Bundle bundle;

            try {
                product_id = mList.get(position).getId();
                model_id = mList.get(position).getModelId();
                name = mList.get(position).getProductModel().getName();
            } catch (Exception e) {
                e.printStackTrace();
            }

            bundle = new Bundle();
            bundle.putString("product_id", product_id);
            bundle.putInt("model_id", model_id);
            if (name != null) {
                bundle.putString("name", name.split("/")[0]);
            }

            try {
                if (mList.get(position).getProductModel().isIsSingleSpec()) {
                    Intent intent = new Intent(mContext, ProductDetailActvityWeb.class);
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                } else {
                    Intent intent = new Intent(mContext, TongkuanActivity.class);
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
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
        //int id = R.layout.item_childlist;
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
