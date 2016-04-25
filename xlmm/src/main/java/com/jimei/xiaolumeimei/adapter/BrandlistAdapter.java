package com.jimei.xiaolumeimei.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.entities.BrandpromotionBean;
import com.jude.utils.JUtils;
import com.zhy.autolayout.utils.AutoUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by itxuye(www.itxuye.com) on 2016/04/23.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class BrandlistAdapter extends RecyclerView.Adapter<BrandlistAdapter.BrandlistVH> {

  private List<BrandpromotionBean.ResultsEntity> mList;

  private Context mContext;

  public BrandlistAdapter(Context mContext) {
    JUtils.Log("MainActivity", "-----------BrandlistAdapter");
    this.mContext = mContext;
    mList = new ArrayList<>();
  }

  public void updateWithClear(List<BrandpromotionBean.ResultsEntity> list) {
    mList.clear();
    mList.addAll(list);
    notifyDataSetChanged();
  }

  public void update(List<BrandpromotionBean.ResultsEntity> list) {

    mList.addAll(list);
    notifyDataSetChanged();
  }

  @Override public BrandlistVH onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_brand, parent, false);
    return new BrandlistVH(view);
  }

  @Override public void onBindViewHolder(BrandlistVH holder, int position) {

    BrandpromotionBean.ResultsEntity resultsEntity = mList.get(position);
    String picPath = resultsEntity.getPicPath();
    //ViewUtils.loadImgToImgViewWithPlaceholder(mContext, holder.brandImag, picPath);
    Glide.with(mContext).load(picPath).diskCacheStrategy(DiskCacheStrategy.ALL)
        //.placeholder(R.drawable.parceholder)
        .centerCrop().into(holder.brandImag);
  }


  @Override public int getItemCount() {
    return mList.size();
  }

  static class BrandlistVH extends RecyclerView.ViewHolder {

    int id = R.layout.item_brand;
    @Bind(R.id.brand_imag) ImageView brandImag;

    public BrandlistVH(View itemView) {
      super(itemView);
      AutoUtils.autoSize(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
