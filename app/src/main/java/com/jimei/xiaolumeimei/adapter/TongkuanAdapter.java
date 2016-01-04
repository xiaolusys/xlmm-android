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
import com.jimei.xiaolumeimei.model.ProductBean;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by itxuye(www.itxuye.com) on 15/12/29.
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
    return new TongkuanVH(view);
  }

  @Override public void onBindViewHolder(TongkuanVH holder, int position) {
    ProductBean productBean = mList.get(position);

    holder.childlistName.setText(productBean.name);

    holder.childlistAgentPrice.setText("¥" + productBean.agent_price);
    holder.childlistStdsalePrice.setText("/" + productBean.std_sale_price);

    String headImg = productBean.pic_path;

    String[] temp = headImg.split("http://image.xiaolu.so/");

    String head_img = "";

    if (temp.length > 1) {
      try {
        head_img = "http://image.xiaolu.so/"
            + URLEncoder.encode(temp[1], "utf-8")
            + "?imageMogr2/auto-orient/strip/size-limit/20k/q/85/thumbnail/600x700";
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }
    }

    holder.card.setTag(new Object());
    Glide.with(mContext)
        .load(head_img)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .centerCrop()
        .into(holder.childlistImage)
        .getSize((width, height) -> {
          if (!holder.card.isShown()) holder.card.setVisibility(View.VISIBLE);
        });


    holder.card.setOnClickListener(v -> {



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

    public TongkuanVH(View itemView) {
      super(itemView);
      card = itemView;
      ButterKnife.bind(this, itemView);
    }
  }
}
