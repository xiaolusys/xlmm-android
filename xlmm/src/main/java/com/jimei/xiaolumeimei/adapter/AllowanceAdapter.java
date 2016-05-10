package com.jimei.xiaolumeimei.adapter;

/**
 * Created by wulei on 2016/1/24.
 */

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
import com.jimei.xiaolumeimei.entities.AllowanceBean;
import com.jude.utils.JUtils;
import com.zhy.autolayout.utils.AutoUtils;
import java.util.ArrayList;
import java.util.List;

public class AllowanceAdapter extends RecyclerView.Adapter<AllowanceAdapter.AllowanceVH>  {
  private static final String TAG = "AllowanceAdapter";
  private List<AllowanceBean.AllowanceEntity> mList = null;
  private Context mContext;

  public AllowanceAdapter(Context mContext) {
    this.mContext = mContext;
    mList = new ArrayList<AllowanceBean.AllowanceEntity>();
  }

  public AllowanceAdapter(Context mContext, List<AllowanceBean.AllowanceEntity> list) {
    this.mContext = mContext;
    this.mList = list;

  }

  public void updateWithClear(List<AllowanceBean.AllowanceEntity> list) {
    mList.clear();
    mList.addAll(list);
    notifyDataSetChanged();
  }

  public void update(List<AllowanceBean.AllowanceEntity> list) {
    JUtils.Log(TAG, "dataSource.size " + list.size());

    mList.clear();
    mList.addAll(list);
    notifyDataSetChanged();
  }

  public void updateStart(List<AllowanceBean.AllowanceEntity> list) {

    mList.addAll(0, list);
    notifyDataSetChanged();
  }

  public AllowanceBean.AllowanceEntity getData(int postion) {

    return mList.get(postion);
  }

  @Override public AllowanceVH onCreateViewHolder(ViewGroup parent, int viewType) {

    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_allowance, parent, false);
    AutoUtils.autoSize(view);
    return new AllowanceVH(view);
  }

  @Override public void onBindViewHolder(final AllowanceVH holder, int position) {

    final AllowanceBean.AllowanceEntity resultsEntity = mList.get(position);

    if (position == 0) {
      showCategory(holder);
    } else {
      boolean theCategoryOfLastEqualsToThis = mList.get(position - 1)
          .getCarry_date()
          .substring(1, 10)
          .equals(mList.get(position).getCarry_date().substring(1, 10));
      if (!theCategoryOfLastEqualsToThis) {
        showCategory(holder);
      } else {
        hideCategory(holder);
      }
    }

    holder.tv_day.setText(resultsEntity.getCarry_date().substring(0, 10));
    holder.tv_today_allowance.setText("总收益 "+ (float)(Math.round(resultsEntity.getDaylyClkAmount()*100)/100));
    holder.tv_time.setText(resultsEntity.getCarry_date().replace("T"," "));
    holder.tv_click_info.setText("用户通过微信链接点击进入");
    holder.tv_one_allowance.setText("+ " + (float)(Math.round(resultsEntity.getValue_money()*100)/100));
  }

  private void showCategory(AllowanceVH holder) {
    if (!isVisibleOf(holder.rl_sum)) holder.rl_sum.setVisibility(View.VISIBLE);
  }

  private void hideCategory(AllowanceVH holder) {
    if (isVisibleOf(holder.rl_sum)) holder.rl_sum.setVisibility(View.GONE);
  }


  private boolean isVisibleOf(View view) {
    return view.getVisibility() == View.VISIBLE;
  }

  @Override public int getItemCount() {
    return mList == null ? 0 : mList.size();
  }

  public interface onItemClickListener {
    void itemClick(View view, int position);
  }

  static class AllowanceVH extends RecyclerView.ViewHolder
      implements View.OnClickListener {
    //int id = R.layout.item_childlist;
    View card;
    @Bind(R.id.img_tip) ImageView img_tip;
    @Bind(R.id.tv_day) TextView tv_day;
    @Bind(R.id.tv_today_allowance) TextView tv_today_allowance;
    @Bind(R.id.tv_time) TextView tv_time;
    @Bind(R.id.tv_click_info) TextView tv_click_info;
    @Bind(R.id.tv_one_allowance) TextView tv_one_allowance;
    @Bind(R.id.rl_sum) RelativeLayout rl_sum;
    @Bind(R.id.rl_info) RelativeLayout rl_info;

    public AllowanceVH(View itemView) {
      super(itemView);
      card = itemView;
      ButterKnife.bind(this, itemView);
      itemView.setOnClickListener(this);
    }

    @Override public void onClick(View v) {

    }
  }
}
