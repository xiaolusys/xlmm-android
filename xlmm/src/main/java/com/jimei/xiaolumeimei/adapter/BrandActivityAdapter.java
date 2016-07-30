package com.jimei.xiaolumeimei.adapter;

import android.app.Activity;
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
import com.jimei.xiaolumeimei.entities.BrandListBean;
import com.jimei.xiaolumeimei.ui.activity.product.ProductDetailActvityWeb;
import com.jude.utils.JUtils;
import com.zhy.autolayout.utils.AutoUtils;
import java.util.ArrayList;
import java.util.List;

public class BrandActivityAdapter extends RecyclerView.Adapter<BrandActivityAdapter.BrandlistVH> {

  private List<BrandListBean.ProductsBean> mList;

  private Context mContext;

  public BrandActivityAdapter(Context mContext) {
    JUtils.Log("MainActivity", "-----------BrandActivityAdapter");
    this.mContext = mContext;
    mList = new ArrayList<>();
  }

  public void updateWithClear(List<BrandListBean.ProductsBean> list) {
    mList.clear();
    mList.addAll(list);
    notifyDataSetChanged();
  }

  public void update(List<BrandListBean.ProductsBean> list) {

    mList.addAll(list);
    notifyDataSetChanged();
  }

  @Override public BrandlistVH onCreateViewHolder(ViewGroup parent, int viewType) {
    View view =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todaylist, parent, false);
    return new BrandlistVH(view);
  }

  @Override public void onBindViewHolder(BrandlistVH holder, int position) {

    try {
      BrandListBean.ProductsBean resultsEntity = mList.get(holder.getAdapterPosition());
      String picPath = resultsEntity.getProductImg();
      Glide.with(mContext)
          .load(picPath)
          .diskCacheStrategy(DiskCacheStrategy.ALL)
          .centerCrop()
          .into(holder.brandImag);

      holder.productLowestPrice.setText("¥" + resultsEntity.getProductLowestPrice());
      holder.productStdSalePrice.setText("/¥" + resultsEntity.getProductStdSalePrice());
      holder.nameTv.setText(resultsEntity.getProductName());

      holder.cardview.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          Intent intent = new Intent(mContext, ProductDetailActvityWeb.class);
          Bundle bundle = new Bundle();
          bundle.putString("product_id", resultsEntity.getModelId() + "");
          intent.putExtras(bundle);
          mContext.startActivity(intent);
          ((Activity) mContext).finish();
        }
      });
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override public int getItemCount() {
    return mList.size();
  }

  static class BrandlistVH extends RecyclerView.ViewHolder {

    private final View cardview;
    int id = R.layout.item_todaylist;
    @Bind(R.id.childlist_image) ImageView brandImag;
    @Bind(R.id.childlist_agent_price) TextView productLowestPrice;
    @Bind(R.id.childlist_stdsale_price) TextView productStdSalePrice;
    @Bind(R.id.childlist_name) TextView nameTv;

    public BrandlistVH(View itemView) {
      super(itemView);
      cardview = itemView;
      AutoUtils.autoSize(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
