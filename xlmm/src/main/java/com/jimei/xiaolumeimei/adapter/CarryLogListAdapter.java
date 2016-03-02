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
import com.jimei.xiaolumeimei.entities.CarryLogListBean;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by itxuye(www.itxuye.com) on 2016/02/18.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class CarryLogListAdapter
    extends RecyclerView.Adapter<CarryLogListAdapter.CarryLogListVH> {

  private static final String TAG = MMChooseAdapter.class.getSimpleName();

  private List<CarryLogListBean.ResultsEntity> mList;
  private Context mContext;

  public CarryLogListAdapter(Context context) {
    this.mContext = context;
    mList = new ArrayList<>();
  }

  public void updateWithClear(List<CarryLogListBean.ResultsEntity> list) {
    mList.clear();
    mList.addAll(list);
    notifyDataSetChanged();
  }

  public void update(List<CarryLogListBean.ResultsEntity> list) {

    mList.addAll(list);
    notifyDataSetChanged();
  }

  private void showCategory(CarryLogListVH holder) {
    if (!isVisibleOf(holder.category)) holder.category.setVisibility(View.VISIBLE);
  }

  private void hideCategory(CarryLogListVH holder) {
    if (isVisibleOf(holder.category)) holder.category.setVisibility(View.GONE);
  }

  private boolean isVisibleOf(View view) {
    return view.getVisibility() == View.VISIBLE;
  }

  @Override public CarryLogListVH onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_carryloglist, parent, false);
    return new CarryLogListVH(v);
  }

  @Override public void onBindViewHolder(CarryLogListVH holder, int position) {

    CarryLogListBean.ResultsEntity resultsEntity = mList.get(position);

    if (position == 0) {
      showCategory(holder);
    } else {
      boolean theCategoryOfLastEqualsToThis = mList.get(position - 1)
          .getCreated()
          .substring(1, 10)
          .equals(mList.get(position).getCreated().substring(1, 10));
      if (!theCategoryOfLastEqualsToThis) {
        showCategory(holder);
      } else {
        hideCategory(holder);
      }
    }

    holder.shoptime.setText(resultsEntity.getCreated().substring(0, 10));
    //holder.picPath.setImageResource(R.drawable.carrylog_image);
    holder.totalCash.setText(
        "总收益 " + (float) (Math.round(resultsEntity.getDaylyInAmount() * 100) / 100));
    holder.tichengCash.setText("+" + resultsEntity.getValueMoney());
    holder.timeDisplay.setText(resultsEntity.getGetLogTypeDisplay());
    holder.wxordernick.setText(resultsEntity.getDesc());
  }

  @Override public int getItemCount() {
    return mList.size();
  }

  static class CarryLogListVH extends RecyclerView.ViewHolder {
    int id = R.layout.item_carryloglist;
    @Bind(R.id.shoptime) TextView shoptime;
    @Bind(R.id.total_cash) TextView totalCash;
    @Bind(R.id.category) RelativeLayout category;
    @Bind(R.id.pic_path) ImageView picPath;
    @Bind(R.id.time_display) TextView timeDisplay;
    @Bind(R.id.wxordernick) TextView wxordernick;
    @Bind(R.id.ticheng_cash) TextView tichengCash;
    @Bind(R.id.content) RelativeLayout content;

    public CarryLogListVH(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
