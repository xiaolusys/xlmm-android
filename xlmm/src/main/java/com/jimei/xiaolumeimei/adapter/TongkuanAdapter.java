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
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.entities.ProductBean;
import com.jimei.xiaolumeimei.ui.activity.product.ProductDetailActvity;
import com.jimei.xiaolumeimei.utils.ViewUtils;
import com.zhy.autolayout.utils.AutoUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 优尼世界 on 15/12/29.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class TongkuanAdapter extends RecyclerView.Adapter<TongkuanAdapter.TongkuanVH> {

  private List<ProductBean> mList;
  private Context mContext;

  public TongkuanAdapter(Context context) {
    this.mContext = context;

    mList = new ArrayList<>();
  }

  public void updateWithClear(List<ProductBean> list) {
    mList.clear();

    mList.addAll(list);
    notifyDataSetChanged();
  }

  public void update(List<ProductBean> list) {

    mList.addAll(list);
    notifyDataSetChanged();
  }

  @Override public TongkuanVH onCreateViewHolder(ViewGroup parent, int viewType) {
    View view;

    view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_todaylist, parent, false);
    AutoUtils.autoSize(view);
    return new TongkuanVH(view);
  }

  @Override public void onBindViewHolder(TongkuanVH holder, int position) {
    ProductBean productBean = mList.get(position);

    try {
      boolean isSaleOut = productBean.is_saleout;

      if (isSaleOut) {
        holder.saleout.setVisibility(View.VISIBLE);
      } else {
        holder.saleout.setVisibility(View.INVISIBLE);
      }

      if (productBean.name.length() <= 9) {
        holder.childlistName.setText(productBean.name);
      } else {
        holder.childlistName.setText(productBean.name.substring(0, 8) + "...");
      }

      holder.childlistAgentPrice.setText("¥" + productBean.agent_price);
      holder.childlistStdsalePrice.setText("/¥" + productBean.std_sale_price);
    } catch (Exception e) {
      e.printStackTrace();
    }

    String headImg = productBean.pic_path;

    //String[] temp = headImg.split("http://image.xiaolu.so/");
    //
    //String head_img = "";
    //
    //if (temp.length > 1) {
    //  try {
    //    head_img = "http://image.xiaolu.so/"
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

      Bundle bundle;

      try {
        product_id = mList.get(position).id;
      } catch (Exception e) {
        e.printStackTrace();
      }

      bundle = new Bundle();
      bundle.putString("product_id", product_id);
      Log.i("laopo jiaowo lai xunshan", product_id);
      Intent intent = new Intent(mContext, ProductDetailActvity.class);
      intent.putExtras(bundle);
      mContext.startActivity(intent);
    });
  }

  @Override public int getItemCount() {
    return mList.size();
  }

  static class TongkuanVH extends RecyclerView.ViewHolder {
    View card;
    @Bind(R.id.childlist_image) ImageView childlistImage;
    @Bind(R.id.childlist_name) TextView childlistName;
    @Bind(R.id.childlist_agent_price) TextView childlistAgentPrice;
    @Bind(R.id.childlist_stdsale_price) TextView childlistStdsalePrice;
    @Bind(R.id.saleout) TextView saleout;

    public TongkuanVH(View itemView) {
      super(itemView);
      card = itemView;
      ButterKnife.bind(this, itemView);
    }
  }
}
