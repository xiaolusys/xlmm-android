package com.jimei.xiaolumeimei.adapter;

import android.content.Context;
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
import com.jimei.xiaolumeimei.entities.CartsinfoBean;
import com.zhy.autolayout.utils.AutoUtils;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by itxuye(www.itxuye.com) on 2016/01/19.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class CartsAdapetr extends RecyclerView.Adapter<CartsAdapetr.CartsVH> {

  private List<CartsinfoBean> mList;
  private Context mContext;

  public CartsAdapetr(Context mContext) {
    this.mContext = mContext;
    mList = new ArrayList<>();
  }

  @Override
  public CartsVH onCreateViewHolder(ViewGroup parent, int viewType) {

    View view;

    view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_carts, parent, false);
    AutoUtils.autoSize(view);
    return new CartsVH(view);

  }

  public void update(List<CartsinfoBean> list) {

    mList.addAll(list);
    notifyDataSetChanged();
  }

  @Override public void onBindViewHolder(final CartsVH holder, int position) {

    CartsinfoBean cartsinfoBean = mList.get(position);

    holder.title.setText(cartsinfoBean.getTitle());
    holder.skuName.setText(cartsinfoBean.getSkuName());

    String headImg = cartsinfoBean.getPicPath();

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

    Glide.with(mContext)
        .load(head_img)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .placeholder(R.drawable.parceholder)
        .centerCrop()
        .override(120,120)
        .into(holder.cartImage);
  }

  @Override public int getItemCount() {
    return mList.size();
  }

  static class CartsVH extends RecyclerView.ViewHolder {
    int id = R.layout.item_carts;
    @Bind(R.id.cart_image) ImageView cartImage;
    @Bind(R.id.title) TextView title;
    @Bind(R.id.sku_name) TextView skuName;

    public CartsVH(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
