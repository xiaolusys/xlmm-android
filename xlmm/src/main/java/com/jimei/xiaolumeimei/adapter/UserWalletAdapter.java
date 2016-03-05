package com.jimei.xiaolumeimei.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.entities.BudgetdetailBean;
import com.zhy.autolayout.utils.AutoUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by itxuye(www.itxuye.com) on 2016/02/26.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class UserWalletAdapter
    extends RecyclerView.Adapter<UserWalletAdapter.UserWalletVH> {

  private static final String TAG = MMChooseAdapter.class.getSimpleName();

  private List<BudgetdetailBean.ResultsEntity> mList;
  private Context mContext;

  public UserWalletAdapter(Context context) {
    this.mContext = context;
    mList = new ArrayList<>();
  }

  public void updateWithClear(List<BudgetdetailBean.ResultsEntity> list) {
    mList.clear();
    mList.addAll(list);
    notifyDataSetChanged();
  }

  public void update(List<BudgetdetailBean.ResultsEntity> list) {

    mList.addAll(list);
    notifyDataSetChanged();
  }

  @Override public UserWalletVH onCreateViewHolder(ViewGroup parent, int viewType) {
    View view;

    view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_userwallet, parent, false);
    AutoUtils.autoSize(view);
    return new UserWalletVH(view);
  }

  @Override public void onBindViewHolder(UserWalletVH holder, int position) {

    BudgetdetailBean.ResultsEntity resultsEntity = mList.get(position);

    holder.tvTime.setText(resultsEntity.getBudgetDate());
    holder.tvPerson.setText(resultsEntity.getBudgetDate());

    if (0 == resultsEntity.getBudgetType()) {

      holder.tvMoneychange.setText(resultsEntity.getBudegetDetailCash() + "元");
      holder.tvMoneychange.setTextColor(Color.parseColor("#F5B123"));
    } else if (1 == resultsEntity.getBudgetType()) {
      holder.tvMoneychange.setText("-" + resultsEntity.getBudegetDetailCash());
    }
  }

  @Override public int getItemCount() {
    return mList == null ? 0 : mList.size();
  }

  static class UserWalletVH extends RecyclerView.ViewHolder {

    int id = R.layout.item_userwallet;
    @Bind(R.id.tv_time) TextView tvTime;
    @Bind(R.id.tv_person) TextView tvPerson;
    @Bind(R.id.tv_moneychange) TextView tvMoneychange;

    public UserWalletVH(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
