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

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.entities.AllowanceBean;
import com.jimei.xiaolumeimei.entities.MamaLivenessBean;
import com.jude.utils.JUtils;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MamaLivenessAdapter extends RecyclerView.Adapter<MamaLivenessAdapter.LivenessVH>  {
  private static final String TAG = "MamaLivenessAdapter";
  private List<MamaLivenessBean.LivenessEntity> mList = null;
  private Context mContext;

  public MamaLivenessAdapter(Context mContext) {
    this.mContext = mContext;
    mList = new ArrayList<MamaLivenessBean.LivenessEntity>();
  }

  public MamaLivenessAdapter(Context mContext, List<MamaLivenessBean.LivenessEntity> list) {
    this.mContext = mContext;
    this.mList = list;

  }

  public void updateWithClear(List<MamaLivenessBean.LivenessEntity> list) {
    mList.clear();
    mList.addAll(list);
    notifyDataSetChanged();
  }

  public void update(List<MamaLivenessBean.LivenessEntity> list) {
    JUtils.Log(TAG, "dataSource.size " + list.size());

    mList.clear();
    mList.addAll(list);
    notifyDataSetChanged();
  }

  public void updateStart(List<MamaLivenessBean.LivenessEntity> list) {

    mList.addAll(0, list);
    notifyDataSetChanged();
  }

  public MamaLivenessBean.LivenessEntity getData(int postion) {

    return mList.get(postion);
  }

  @Override public LivenessVH onCreateViewHolder(ViewGroup parent, int viewType) {

    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_mamaliveness, parent, false);
    AutoUtils.autoSize(view);
    return new LivenessVH(view);
  }

  @Override public void onBindViewHolder(final LivenessVH holder, int position) {

    final MamaLivenessBean.LivenessEntity resultsEntity = mList.get(position);

    if (position == 0) {
      showCategory(holder);
    } else {
      boolean theCategoryOfLastEqualsToThis = mList.get(position - 1)
          .getDateField()
          .substring(1, 10)
          .equals(mList.get(position).getDateField().substring(1, 10));
      if (!theCategoryOfLastEqualsToThis) {
        showCategory(holder);
      } else {
        hideCategory(holder);
      }
    }

    holder.tv_day.setText(resultsEntity.getDateField().substring(0, 10));
    //holder.tv_time.setText(resultsEntity.getCreated().replace("T"," "));
    holder.tv_value_type.setText(resultsEntity.getValueTypeName());
    holder.tv_status_info.setText(resultsEntity.getStatusDisplay());
    holder.tv_one_liveness.setText("+ " + resultsEntity.getValueNum());
  }

  private void showCategory(LivenessVH holder) {
    if (!isVisibleOf(holder.rl_sum)) holder.rl_sum.setVisibility(View.VISIBLE);
  }

  private void hideCategory(LivenessVH holder) {
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

  static class LivenessVH extends RecyclerView.ViewHolder
      implements View.OnClickListener {
    //int id = R.layout.item_childlist;
    View card;

    @Bind(R.id.tv_day) TextView tv_day;
    @Bind(R.id.tv_value_type) TextView tv_value_type;
    @Bind(R.id.tv_status_info) TextView tv_status_info;
    @Bind(R.id.tv_one_liveness) TextView tv_one_liveness;
    @Bind(R.id.rl_sum) RelativeLayout rl_sum;
    @Bind(R.id.rl_info) RelativeLayout rl_info;

    public LivenessVH(View itemView) {
      super(itemView);
      card = itemView;
      ButterKnife.bind(this, itemView);
      itemView.setOnClickListener(this);
    }

    @Override public void onClick(View v) {

    }
  }
}
