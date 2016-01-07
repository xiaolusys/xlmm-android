package com.jimei.xiaolumeimei.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.jimei.xiaolumeimei.model.LadyListBean;
import com.jimei.xiaolumeimei.ui.activity.ProductDetailActvity;
import com.jimei.xiaolumeimei.ui.activity.TongkuanActicity;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by itxuye(www.itxuye.com) on 15/12/29.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class LadyListAdapter extends RecyclerView.Adapter<LadyListAdapter.LadyListVH> {

  private List<LadyListBean.ResultsEntity> mList;

  private Context mContext;

  private onItemClickListener listener;

  public LadyListAdapter(Context mContext) {
    this.mContext = mContext;
    mList = new ArrayList<>();
  }

  public void setOnItemClickListener(onItemClickListener listener) {
    this.listener = listener;
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

  public void updateStart(List<LadyListBean.ResultsEntity> list) {

    mList.addAll(0, list);
    notifyDataSetChanged();
  }

  public LadyListBean.ResultsEntity getData(int postion) {

    return mList.get(postion);
  }

  @Override public LadyListVH onCreateViewHolder(ViewGroup parent, int viewType) {

    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_childlist, parent, false);

    return new LadyListVH(view);
  }

  @Override public void onBindViewHolder(final LadyListVH holder, int position) {

    final LadyListBean.ResultsEntity resultsEntity = mList.get(position);
    //ChildListBean.ResultsEntity.CategoryEntity category = resultsEntity.getCategory();
    LadyListBean.ResultsEntity.ProductModelEntity productModel =
        resultsEntity.getProductModel();

    String headImg = resultsEntity.getHeadImg();

    holder.childlistName.setText(productModel.getName());

    holder.childlistAgentPrice.setText("¥" + resultsEntity.getAgentPrice());
    holder.childlistStdsalePrice.setText("/" + resultsEntity.getStdSalePrice());

    String[] temp = headImg.split("http://image.xiaolu.so/");
    String head_img = "";
    if (temp.length > 1) {
      try {
        head_img += "http://image.xiaolu.so/"
            + URLEncoder.encode(temp[1], "utf-8")
            + "?imageMogr2/auto-orient/strip/size-limit/10k/q/85/thumbnail/600x700";
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

      int product_id = 0;
      int model_id = 0;
      String name = null;
      Bundle bundle;

      try {
        product_id = mList.get(position).getProductModel().getId();
        model_id = mList.get(position).getModelId();
        name = mList.get(position).getProductModel().getName();
      } catch (Exception e) {
        e.printStackTrace();
      }

      bundle = new Bundle();
      bundle.putInt("product_id", product_id);
      bundle.putInt("model_id", model_id);
      if (name != null) {
        bundle.putString("name", name.split("/")[0]);
      }
      if (mList.get(position).getProductModel().isIsSingleSpec()) {
        Intent intent = new Intent(mContext, TongkuanActicity.class);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
      } else {
        Intent intent = new Intent(mContext, ProductDetailActvity.class);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
      }
    });
  }

  @Override public void onViewRecycled(LadyListVH holder) {
    super.onViewRecycled(holder);
  }

  @Override public int getItemCount() {
    return mList == null ? 0 : mList.size();
  }

  public interface onItemClickListener {
    void itemClick(View view, int position);
  }

  static class LadyListVH extends RecyclerView.ViewHolder
      implements View.OnClickListener {
    //int id = R.layout.item_childlist;
    View card;
    @Bind(R.id.childlist_image) ImageView childlistImage;
    @Bind(R.id.childlist_name) TextView childlistName;
    @Bind(R.id.childlist_agent_price) TextView childlistAgentPrice;
    @Bind(R.id.childlist_stdsale_price) TextView childlistStdsalePrice;
    private onItemClickListener listener;//点击事件

    public LadyListVH(View itemView) {
      super(itemView);
      card = itemView;
      ButterKnife.bind(this, itemView);
      itemView.setOnClickListener(this);
    }

    @Override public void onClick(View v) {

    }
  }
}
