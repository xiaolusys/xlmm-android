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
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.entities.ChildListBean;
import com.jimei.xiaolumeimei.ui.activity.product.TongkuanActivity;
import com.jimei.xiaolumeimei.utils.ViewUtils;
import com.zhy.autolayout.utils.AutoUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 优尼世界 on 15/12/29.
 * <p>
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class ChildListActivityAdapter
    extends RecyclerView.Adapter<ChildListActivityAdapter.ChildListVH> {

  private List<ChildListBean.ResultsEntity> mList;

  private Context mContext;

  private onItemClickListener listener;

  public ChildListActivityAdapter(Context mContext) {
    this.mContext = mContext;
    mList = new ArrayList<>();
  }

  public void setOnItemClickListener(onItemClickListener listener) {
    this.listener = listener;
  }

  public void updateWithClear(List<ChildListBean.ResultsEntity> list) {
    mList.clear();
    mList.addAll(list);
    notifyDataSetChanged();
  }

  public void update(List<ChildListBean.ResultsEntity> list) {

    mList.addAll(list);
    notifyDataSetChanged();
  }

  public void updateStart(List<ChildListBean.ResultsEntity> list) {

    mList.addAll(0, list);
    notifyDataSetChanged();
  }

  public ChildListBean.ResultsEntity getData(int postion) {

    return mList.get(postion);
  }

  @Override public ChildListVH onCreateViewHolder(ViewGroup parent, int viewType) {

    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_todaylist, parent, false);
    return new ChildListVH(view);
  }

  @Override public void onViewRecycled(ChildListVH holder) {
    super.onViewRecycled(holder);
  }

  @Override public void onBindViewHolder(final ChildListVH holder, int position) {

    final ChildListBean.ResultsEntity resultsEntity = mList.get(position);
    //ChildListBean.ResultsEntity.CategoryEntity category = resultsEntity.getCategory();
    ChildListBean.ResultsEntity.ProductModelEntity productModel =
        resultsEntity.getProductModel();

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
    } catch (NullPointerException ex) {

    }

    String headImg = resultsEntity.getHeadImg();

    try {
      if (productModel.getName().length() <= 9) {
        holder.childlistName.setText(productModel.getName());
      } else {
        holder.childlistName.setText(productModel.getName().substring(0, 8) + "...");
      }

      holder.childlistAgentPrice.setText("¥" + resultsEntity.getProductLowestPrice());
      holder.childlistStdsalePrice.setText("/¥" + resultsEntity.getStdSalePrice());
    } catch (Exception e) {
      e.printStackTrace();
    }

    //String[] temp = headImg.split("http://image.xiaolu.so/");
    //String head_img = "";
    //if (temp.length > 1) {
    //  try {
    //    head_img += "http://image.xiaolu.so/"
    //        + URLEncoder.encode(temp[1], "utf-8")
    //        + "?imageMogr2/format/jpg/size-limit/30k/thumbnail/289/quality/90";
    //  } catch (UnsupportedEncodingException e) {
    //    e.printStackTrace();
    //  }
    //}

    holder.card.setTag(new Object());
    //Glide.with(mContext)
    //    .load(head_img)
    //    .diskCacheStrategy(DiskCacheStrategy.ALL)
    //    .placeholder(R.drawable.parceholder)
    //    .centerCrop()
    //    .into(holder.childlistImage)
    //    .getSize((width, height) -> {
    //      if (!holder.card.isShown()) holder.card.setVisibility(View.VISIBLE);
    //    });
    ViewUtils.loadImgToImgViewWithPlaceholder(mContext, holder.childlistImage, headImg);
    holder.card.setOnClickListener(v -> {

      String product_id = null;
      int model_id = 0;
      String name = null;
      Bundle bundle;

      ChildListBean.ResultsEntity.ProductModelEntity model =
          mList.get(position).getProductModel();
      try {
        product_id = mList.get(position).getId();
        model_id = mList.get(position).getModelId();
        name = model.getName();
      } catch (Exception e) {
        e.printStackTrace();
      }

      bundle = new Bundle();
      bundle.putString("product_id", product_id);
      bundle.putInt("model_id", model_id);
      if (name != null) {
        bundle.putString("name", name.split("/")[0]);
      }
      if (model == null || !model.isIsSingleSpec()) {
        Intent intent = new Intent(mContext, TongkuanActivity.class);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
      } else {
        Intent intent = new Intent(mContext, ProductDetailActvityWeb.class);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
      }
    });
  }

  @Override public int getItemCount() {
    return mList == null ? 0 : mList.size();
  }

  public interface onItemClickListener {
    void itemClick(View view, int position);
  }

  static class ChildListVH extends RecyclerView.ViewHolder
      implements View.OnClickListener {
    //int id = R.layout.item_childlist;
    View card;
    @Bind(R.id.childlist_image) ImageView childlistImage;
    @Bind(R.id.childlist_name) TextView childlistName;
    @Bind(R.id.childlist_agent_price) TextView childlistAgentPrice;
    @Bind(R.id.childlist_stdsale_price) TextView childlistStdsalePrice;
    @Bind(R.id.saleout) TextView saleout;
    private onItemClickListener listener;//点击事件

    public ChildListVH(View itemView) {
      super(itemView);
      card = itemView;
      AutoUtils.autoSize(itemView);
      ButterKnife.bind(this, itemView);
      itemView.setOnClickListener(this);
    }

    @Override public void onClick(View v) {

    }
  }
}
