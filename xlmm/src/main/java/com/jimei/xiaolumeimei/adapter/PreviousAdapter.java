package com.jimei.xiaolumeimei.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.entities.ProductListBean;
import com.jimei.xiaolumeimei.ui.activity.product.ProductPopDetailActvityWeb;
import com.jimei.xiaolumeimei.utils.JumpUtils;
import com.jimei.xiaolumeimei.utils.ViewUtils;
import com.umeng.analytics.MobclickAgent;
import com.zhy.autolayout.utils.AutoUtils;
import java.util.ArrayList;
import java.util.List;

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

    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todaylist, parent, false);
    return new PreviousVH(view);
  }

  @Override public void onBindViewHolder(PreviousVH holder, int position) {
    ProductListBean.ResultsEntity products = mList.get(position);

    ProductListBean.ResultsEntity.ProductModelEntity productModel = products.getProductModel();

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

    holder.card.setTag(new Object());

    ViewUtils.loadImgToImgViewWithPlaceholderFragment(context, holder.childlistImage, headImg);

    holder.card.setOnClickListener(v -> {
      MobclickAgent.onEvent(mContext, "ProductID");
      int modelId = mList.get(position).getModelId();
      JumpUtils.jumpToWebViewWithCookies(mContext, mList.get(position).getWebUrl(), modelId,
          ProductPopDetailActvityWeb.class);
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
