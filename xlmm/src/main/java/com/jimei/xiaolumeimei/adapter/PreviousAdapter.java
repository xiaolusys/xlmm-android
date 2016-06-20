package com.jimei.xiaolumeimei.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.entities.ProductListBean;
import com.jimei.xiaolumeimei.ui.activity.product.ProductPopDetailActvityWeb;
import com.jimei.xiaolumeimei.utils.ViewUtils;
import com.umeng.analytics.MobclickAgent;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 优尼世界 on 15/12/31.
 * <p>
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class PreviousAdapter extends RecyclerView.Adapter<PreviousAdapter.PreviousVH> {

  private List<ProductListBean.ResultsEntity> mList;
  private Fragment context;
  private Context mContext;

  public PreviousAdapter(Fragment context, Context mContext) {
    this.context = context;
    this.mContext = mContext;
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

  @Override public PreviousVH onCreateViewHolder(ViewGroup parent, int viewType) {
    View view;

    view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_todaylist, parent, false);
    return new PreviousVH(view);
  }

  @Override public void onBindViewHolder(PreviousVH holder, int position) {
    ProductListBean.ResultsEntity products = mList.get(position);

    ProductListBean.ResultsEntity.ProductModelEntity productModel =
        products.getProductModel();

    boolean isSaleopen = products.isIsSaleopen();
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

      holder.childlistAgentPrice.setText("¥" + products.getProductLowestPrice());
      holder.childlistStdsalePrice.setText("/¥" + products.getStdSalePrice());
    } catch (Exception e) {
      e.printStackTrace();
    }

    String headImg = products.getHeadImg();
    //String[] temp = headImg.split("http://image.xiaolu.so/");
    //String head_img = "";
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
    ViewUtils.loadImgToImgViewWithPlaceholderFragment(context, holder.childlistImage,
        headImg);

    holder.card.setOnClickListener(v -> {

      //String product_id = null;
      //int model_id = 0;
      //String name = null;
      //Bundle bundle;
      //
      //try {
      //  product_id = mList.get(position).getId();
      //  model_id = mList.get(position).getModelId();
      //  name = mList.get(position).getName();
      //} catch (Exception e) {
      //  e.printStackTrace();
      //}
      //
      //bundle = new Bundle();
      //bundle.putString("product_id", product_id);
      //bundle.putInt("model_id", model_id);
      //if (name != null) {
      //  bundle.putString("name", name.split("/")[0]);
      //}
      //if (productModel.isIsSingleSpec()) {
      //  Intent intent = new Intent(mContext, ProductDetailActvityWeb.class);
      //  intent.putExtras(bundle);
      //  mContext.startActivity(intent);
      //} else {
      //  Intent intent = new Intent(mContext, TongkuanActivity.class);
      //  intent.putExtras(bundle);
      //  mContext.startActivity(intent);
      //}

      Map<String, String> map = new HashMap<String, String>();
      map.put("modelId","modelName");

      map.put(productModel.getId(), productModel.getName());
      MobclickAgent.onEvent(mContext, "ProductID", map);


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

  @Override public int getItemCount() {
    return mList.size();
  }

  static class PreviousVH extends RecyclerView.ViewHolder {
    View card;
    @Bind(R.id.childlist_image) ImageView childlistImage;
    @Bind(R.id.childlist_name) TextView childlistName;
    @Bind(R.id.childlist_agent_price) TextView childlistAgentPrice;
    @Bind(R.id.childlist_stdsale_price) TextView childlistStdsalePrice;
    @Bind(R.id.saleout) TextView saleout;

    public PreviousVH(View itemView) {
      super(itemView);
      card = itemView;
      AutoUtils.autoSize(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
