package com.jimei.xiaolumeimei.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.entities.OderCarryBean;
import com.jimei.xiaolumeimei.utils.ViewUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by itxuye(www.itxuye.com) on 2016/02/18.
 * <p>
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class ShoppingListAdapter
    extends RecyclerView.Adapter<ShoppingListAdapter.ShoppingListVH> {

  private List<OderCarryBean.ResultsEntity> mList;
  private Context mContext;

  public ShoppingListAdapter(Context context) {
    this.mContext = context;
    mList = new ArrayList<>();
  }

  public void updateWithClear(List<OderCarryBean.ResultsEntity> list) {
    mList.clear();
    mList.addAll(list);
    notifyDataSetChanged();
  }

  public void update(List<OderCarryBean.ResultsEntity> list) {

    mList.addAll(list);
    notifyDataSetChanged();
  }

  @Override public ShoppingListVH onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_shoppinglist, parent, false);
    return new ShoppingListVH(v);
  }

  @Override public void onBindViewHolder(ShoppingListVH holder, int position) {

    OderCarryBean.ResultsEntity resultsEntity = mList.get(position);

    try {
      if (position == 0) {
        showCategory(holder);
      } else {
        boolean theCategoryOfLastEqualsToThis = mList.get(position - 1)
            .getDateField()
            .equals(mList.get(position).getDateField());
        if (!theCategoryOfLastEqualsToThis) {
          showCategory(holder);
        } else {
          hideCategory(holder);
        }
      }
    } catch (NullPointerException e) {
      e.printStackTrace();
    }

    holder.shoptime.setText(resultsEntity.getDateField());
    if (TextUtils.isEmpty(resultsEntity.getSkuImg())) {
      Glide.with(mContext)
          .load(R.mipmap.ic_launcher)
          .diskCacheStrategy(DiskCacheStrategy.ALL)
          //.placeholder(R.drawable.parceholder)
          .centerCrop()
          .into(holder.picPath);
    } else {
      ViewUtils.loadImgToImgView(mContext, holder.picPath, resultsEntity.getSkuImg());
    }
    holder.getStatusDisplay.setText(resultsEntity.getStatusDisplay());
    holder.wxordernick.setText(resultsEntity.getContributorNick());
    holder.timeDisplay.setText(resultsEntity.getCreated().substring(11, 16));
    holder.totalMoneyTv.setText("实付" + resultsEntity.getOrderValue());
    holder.flagTv.setText(resultsEntity.getCarryTypeName());
    double carryNum = resultsEntity.getCarryNum();
    if (carryNum >= 0) {
      holder.tichengCash.setText("+" + carryNum);
    } else {
      holder.tichengCash.setText(carryNum + "");
      holder.tichengCash.setTextColor(
          mContext.getResources().getColor(R.color.colorPrimary));
    }
    if (resultsEntity.getStatusDisplay().equals("已确定")) {
      holder.tichengCash.setTextColor(Color.BLACK);
    }
    holder.tichengCash.setText(
        "+" + (float) (Math.round(resultsEntity.getCarryNum() * 100)) / 100);
    holder.totalCash.setText(
        "总收益 " + (float) (Math.round(resultsEntity.getTodayCarry() * 100)) / 100);
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
    @Bind(R.id.category) LinearLayout category;
    @Bind(R.id.content) RelativeLayout content;
    @Bind(R.id.total_money) TextView totalMoneyTv;
    @Bind(R.id.flag) TextView flagTv;

    public ShoppingListVH(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
