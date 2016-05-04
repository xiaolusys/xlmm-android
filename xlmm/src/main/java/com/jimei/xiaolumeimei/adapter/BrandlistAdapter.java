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

  private List<BrandpromotionBean.ResultsBean> mList;

  private Context mContext;

  public void setListener(itemOnclickListener listener) {
    this.listener = listener;
  }

  private itemOnclickListener listener;


  public BrandlistAdapter(Context mContext) {
    JUtils.Log("MainActivity", "-----------BrandlistAdapter");
    this.mContext = mContext;
    mList = new ArrayList<>();
  }

  public void updateWithClear(List<BrandpromotionBean.ResultsBean> list) {
    mList.clear();
    mList.addAll(list);
    notifyDataSetChanged();
  }

  public void update(List<BrandpromotionBean.ResultsBean> list) {

    mList.addAll(list);
    notifyDataSetChanged();
  }

  @Override public BrandlistVH onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_brand, parent, false);
    return new BrandlistVH(view);
  }

  @Override public void onBindViewHolder(BrandlistVH holder, int position) {

    BrandpromotionBean.ResultsBean resultsEntity = mList.get(position);
    String picPath = resultsEntity.getProduct_img();
    //ViewUtils.loadImgToImgViewWithPlaceholder(mContext, holder.brandImag, picPath);
    Glide.with(mContext).load(picPath).diskCacheStrategy(DiskCacheStrategy.ALL)
        //.placeholder(R.drawable.parceholder)
        .centerCrop().into(holder.brandImag);

    holder.productLowestPrice.setText("¥" + resultsEntity.getProduct_lowest_price());
    holder.productStdSalePrice.setText("/¥" + resultsEntity.getProduct_std_sale_price());

    if (listener != null) {
      holder.cardview.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          listener.itemClick();
        }
      });
    }
  }

  @Override public int getItemCount() {
    return mList.size();
  }

  static class BrandlistVH extends RecyclerView.ViewHolder {

    private final View cardview;
    int id = R.layout.item_brand;
    @Bind(R.id.brand_imag) ImageView brandImag;
    @Bind(R.id.product_lowest_price) TextView productLowestPrice;
    @Bind(R.id.product_std_sale_price) TextView productStdSalePrice;

    public BrandlistVH(View itemView) {
      super(itemView);
      cardview = itemView;
      AutoUtils.autoSize(itemView);
      ButterKnife.bind(this, itemView);
    }
  }

 public interface itemOnclickListener{
    void itemClick();
  }
}
