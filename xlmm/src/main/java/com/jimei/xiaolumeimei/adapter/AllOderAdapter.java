package com.jimei.xiaolumeimei.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.entities.AllOdersBean;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by itxuye(www.itxuye.com) on 2016/01/12.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class AllOderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private List<AllOdersBean.ResultsEntity> mList;
  private Context context;

  public AllOderAdapter(Context context) {
    mList = new ArrayList<>();
    this.context = context;
  }

  public void updateWithClear(List<AllOdersBean.ResultsEntity> list) {
    mList.clear();
    mList.addAll(list);
    notifyDataSetChanged();
  }

  public void update(List<AllOdersBean.ResultsEntity> list) {

    mList.addAll(list);
    notifyDataSetChanged();
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    Log.i("xlmm", viewType + "");
    View view;
    if (viewType == 0) {
      view = LayoutInflater.from(parent.getContext())
          .inflate(R.layout.item_alloder_1, parent, false);

      return new AllOderVH1(view);
    } else if (viewType == 1) {
      view = LayoutInflater.from(parent.getContext())
          .inflate(R.layout.item_alloder_2, parent, false);

      return new AllOderVH2(view);
    }

    return null;
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    if (holder instanceof AllOderVH1) {
      AllOdersBean.ResultsEntity resultsEntity = mList.get(position);

      List<AllOdersBean.ResultsEntity.OrdersEntity> orders = resultsEntity.getOrders();
      AllOdersBean.ResultsEntity.OrdersEntity ordersEntity = orders.get(0);
      String picPath = ordersEntity.getPicPath();

      ((AllOderVH1) holder).oderName.setText(ordersEntity.getTitle());
      ((AllOderVH1) holder).orderChima.setText(ordersEntity.getSkuName());
      ((AllOderVH1) holder).oderPrice.setText(ordersEntity.getPayment() + "");

      ((AllOderVH1) holder).card.setTag(new Object());
      Glide.with(context)
          .load(picPath)
          .diskCacheStrategy(DiskCacheStrategy.ALL)
          .placeholder(R.drawable.parceholder)
          .centerCrop()
          .into(((AllOderVH1) holder).alloderImage);
    } else if (holder instanceof AllOderVH2) {

      AllOdersBean.ResultsEntity resultsEntity = mList.get(position);
      List<AllOdersBean.ResultsEntity.OrdersEntity> orders = resultsEntity.getOrders();

      List<ImageView> imageViewList = new ArrayList<>();
      List<String> pathList = new ArrayList<>();

      ImageView imageView = null;
      String picPath;
      ((AllOderVH2) holder).card.setTag(new Object());
      for (int i = 0; i < orders.size(); i++) {
        AllOdersBean.ResultsEntity.OrdersEntity ordersEntity = orders.get(i);
        picPath = ordersEntity.getPicPath();
        pathList.add(picPath);

        imageView = new ImageView(context);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(180, 180));
        imageViewList.add(imageView);

        //imageView.setTag(new Object());
      }

      for (int i = 0; i < orders.size(); i++) {
        Glide.with(context)
            .load(pathList.get(i))
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.parceholder)
            .centerCrop()
            .into(imageViewList.get(i));

        ((AllOderVH2) holder).orderContent.addView(imageViewList.get(i));
      }
    }
  }

  @Override public int getItemCount() {
    return mList == null ? 0 : mList.size();
  }

  @Override public int getItemViewType(int position) {
    List<AllOdersBean.ResultsEntity.OrdersEntity> orders =
        mList.get(position).getOrders();
    if (orders.size() == 1) {
      return 0;
    } else {
      return 1;
    }
  }

  class AllOderVH1 extends RecyclerView.ViewHolder {
    int id = R.layout.item_alloder_1;
    View card;
    @Bind(R.id.alloder_image) ImageView alloderImage;
    @Bind(R.id.oder_name) TextView oderName;
    @Bind(R.id.order_chima) TextView orderChima;
    @Bind(R.id.oder_price) TextView oderPrice;

    public AllOderVH1(View itemView) {
      super(itemView);
      card = itemView;
      ButterKnife.bind(this, itemView);
    }
  }

  class AllOderVH2 extends RecyclerView.ViewHolder {
    int id = R.layout.item_alloder_2;
    View card;
    @Bind(R.id.order_content) LinearLayout orderContent;

    public AllOderVH2(View itemView) {
      super(itemView);
      card = itemView;
      ButterKnife.bind(this, itemView);
    }
  }
}
