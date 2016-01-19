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
import com.jimei.xiaolumeimei.entities.CartsPayinfoBean;
import com.zhy.autolayout.utils.AutoUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by itxuye(www.itxuye.com) on 2016/01/19.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class CartsPayInfoAdapter
    extends RecyclerView.Adapter<CartsPayInfoAdapter.CartsPayInfoVH> {

  private List<CartsPayinfoBean> mList;
  private Context mContext;

  public CartsPayInfoAdapter(Context mContext) {
    this.mContext = mContext;
    mList = new ArrayList<>();
  }


  public void update(List<CartsPayinfoBean> list) {

    mList.addAll(list);
    notifyDataSetChanged();
  }

  @Override public CartsPayInfoVH onCreateViewHolder(ViewGroup parent, int viewType) {

    View view;

    view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_carts_pay, parent, false);
    AutoUtils.autoSize(view);
    return new CartsPayInfoVH(view);
  }

  @Override public void onBindViewHolder(CartsPayInfoVH holder, int position) {
    CartsPayinfoBean cartsPayinfoBean = mList.get(position);
    List<CartsPayinfoBean.CartListEntity> cartList = cartsPayinfoBean.getCartList();

    holder.title.setText(cartList.get(position).getTitle());
    holder.skuName.setText(cartList.get(position).getSkuName());
    Glide.with(mContext)
        .load(cartList.get(position).getPicPath())
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .placeholder(R.drawable.parceholder)
        .centerCrop()
        .override(120,120)
        .into(holder.cartImage);
  }

  @Override public int getItemCount() {
    return mList.size();
  }

  static class CartsPayInfoVH extends RecyclerView.ViewHolder {
    int id = R.layout.item_carts_pay;
    @Bind(R.id.cart_image) ImageView cartImage;
    @Bind(R.id.title) TextView title;
    @Bind(R.id.sku_name) TextView skuName;

    public CartsPayInfoVH(View itemView) {
      super(itemView);
      ButterKnife.bind(this,itemView);
    }
  }
}
