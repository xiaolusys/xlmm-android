package com.jimei.xiaolumeimei.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.jimei.xiaolumeimei.entities.ProductListBean;
import com.jimei.xiaolumeimei.ui.activity.ProductDetailActvity;
import com.jimei.xiaolumeimei.ui.activity.TongkuanActivity;
import com.zhy.autolayout.utils.AutoUtils;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by itxuye(www.itxuye.com) on 15/12/29.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class TodayAdapter extends RecyclerView.Adapter<TodayAdapter.TodayVH> {

  private List<ProductListBean.ResultsEntity> mList;
  private Context mContext;

  public TodayAdapter(Context context) {
    this.mContext = context;

    mList = new ArrayList<>();
  }

  public void updateWithClear(List<ProductListBean.ResultsEntity> femallist) {
    mList.clear();
    mList.addAll(femallist);
    notifyDataSetChanged();
  }

  public void update(List<ProductListBean.ResultsEntity> femallist) {

    mList.addAll(femallist);
    notifyDataSetChanged();
  }

  @Override public TodayVH onCreateViewHolder(ViewGroup parent, int viewType) {
    View view;

    view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_todaylist, parent, false);
    AutoUtils.autoSize(view);
    return new TodayVH(view);
  }

  @Override public void onBindViewHolder(final TodayVH holder, int position) {

    ProductListBean.ResultsEntity products = mList.get(position);

    ProductListBean.ResultsEntity.ProductModelEntity productModel =
        products.getProductModel();

    holder.childlistName.setText(productModel.getName());

    holder.childlistAgentPrice.setText("¥" + products.getAgentPrice());
    holder.childlistStdsalePrice.setText("/¥" + products.getStdSalePrice());

    String headImg = products.getHeadImg();

    String[] temp = headImg.split("http://image.xiaolu.so/");

    String head_img = "";

    if (temp.length > 1) {
      try {
        head_img = "http://image.xiaolu.so/"
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

      String product_id = null;
      int model_id = 0;
      String name = null;
      Bundle bundle;

      try {
        product_id = mList.get(position).getId();
        Log.i("dawang jiaowo lai xunshan", product_id);
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
      if (mList.get(position).getProductModel().isIsSingleSpec()) {
        Intent intent = new Intent(mContext, ProductDetailActvity.class);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
      } else {
        Intent intent = new Intent(mContext, TongkuanActivity.class);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
      }
    });
  }

  @Override public int getItemCount() {
    return mList.size();
  }

  static class TodayVH extends RecyclerView.ViewHolder {
    View card;
    @Bind(R.id.childlist_image) ImageView childlistImage;
    @Bind(R.id.childlist_name) TextView childlistName;
    @Bind(R.id.childlist_agent_price) TextView childlistAgentPrice;
    @Bind(R.id.childlist_stdsale_price) TextView childlistStdsalePrice;

    public TodayVH(View itemView) {
      super(itemView);
      card = itemView;
      ButterKnife.bind(this, itemView);
    }
  }
}
