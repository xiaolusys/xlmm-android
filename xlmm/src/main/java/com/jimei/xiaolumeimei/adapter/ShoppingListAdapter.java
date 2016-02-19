package com.jimei.xiaolumeimei.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.entities.ShoppingListBean;
import com.jimei.xiaolumeimei.utils.ViewUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by itxuye(www.itxuye.com) on 2016/02/18.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class ShoppingListAdapter
    extends RecyclerView.Adapter<ShoppingListAdapter.ShoppingListVH> {

  private static final String TAG = MMChooseAdapter.class.getSimpleName();

  private List<ShoppingListBean.ResultsEntity> mList;
  private Context mContext;

  public ShoppingListAdapter(Context context) {
    this.mContext = context;
    mList = new ArrayList<>();
  }

  public void updateWithClear(List<ShoppingListBean.ResultsEntity> list) {
    mList.clear();
    mList.addAll(list);
    notifyDataSetChanged();
  }

  public void update(List<ShoppingListBean.ResultsEntity> list) {

    mList.addAll(list);
    notifyDataSetChanged();
  }

  @Override public ShoppingListVH onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_shoppinglist, parent, false);
    return new ShoppingListVH(v);
  }

  @Override public void onBindViewHolder(ShoppingListVH holder, int position) {

    ShoppingListBean.ResultsEntity resultsEntity = mList.get(position);

    if (position == 0) {
      showCategory(holder);
    } else {
      boolean theCategoryOfLastEqualsToThis = mList.get(position - 1)
          .getShoptime()
          .substring(1, 10)
          .equals(mList.get(position).getShoptime().substring(1, 10));
      if (!theCategoryOfLastEqualsToThis) {
        showCategory(holder);
      } else {
        hideCategory(holder);
      }
    }

    holder.shoptime.setText(resultsEntity.getShoptime().substring(0, 10));
    ViewUtils.loadImgToImgView(mContext, holder.picPath, resultsEntity.getPicPath());
    holder.getStatusDisplay.setText(resultsEntity.getGetStatusDisplay());
    holder.wxordernick.setText(resultsEntity.getWxordernick());
    holder.timeDisplay.setText(resultsEntity.getTimeDisplay());
    holder.tichengCash.setText(resultsEntity.getTichengCash() + "");
    holder.totalCash.setText("总收益 "+ (float)(Math.round(resultsEntity.getDaylyAmount()*100)/100));
  }

  private void showCategory(ShoppingListVH holder) {
    if (!isVisibleOf(holder.category)) holder.category.setVisibility(View.VISIBLE);
  }

  private void hideCategory(ShoppingListVH holder) {
    if (isVisibleOf(holder.category)) holder.category.setVisibility(View.GONE);
  }


  private boolean isVisibleOf(View view) {
    return view.getVisibility() == View.VISIBLE;
  }

  @Override public int getItemCount() {
    return mList.size();
  }

  static class ShoppingListVH extends RecyclerView.ViewHolder {
    int id = R.layout.item_shoppinglist;
    @Bind(R.id.shoptime) TextView shoptime;
    @Bind(R.id.total_cash) TextView totalCash;
    @Bind(R.id.pic_path) ImageView picPath;
    @Bind(R.id.time_display) TextView timeDisplay;
    @Bind(R.id.get_status_display) TextView getStatusDisplay;
    @Bind(R.id.wxordernick) TextView wxordernick;
    @Bind(R.id.ticheng_cash) TextView tichengCash;
    @Bind(R.id.category) RelativeLayout category;
    @Bind(R.id.content) RelativeLayout content;

    public ShoppingListVH(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
