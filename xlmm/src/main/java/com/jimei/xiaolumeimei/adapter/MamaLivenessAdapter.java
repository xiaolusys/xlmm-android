package com.jimei.xiaolumeimei.adapter;

/**
 * Created by wulei on 2016/1/24.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.entities.MamaLivenessBean;
import com.jude.utils.JUtils;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

public class MamaLivenessAdapter
        extends RecyclerView.Adapter<MamaLivenessAdapter.LivenessVH> {
    private static final String TAG = "MamaLivenessAdapter";
    private List<MamaLivenessBean.ResultsEntity> mList = new ArrayList<>();

    public void updateWithClear(List<MamaLivenessBean.ResultsEntity> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void update(List<MamaLivenessBean.ResultsEntity> list) {
        JUtils.Log(TAG, "dataSource.size " + mList.size());
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public MamaLivenessBean.ResultsEntity getData(int postion) {
        return mList.get(postion);
    }

    @Override
    public LivenessVH onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_mamaliveness, parent, false);
        AutoUtils.autoSize(view);
        return new LivenessVH(view);
    }

    @Override
    public void onBindViewHolder(final LivenessVH holder, int position) {

        final MamaLivenessBean.ResultsEntity resultsEntity = mList.get(position);

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

        holder.tvDay.setText(resultsEntity.getDateField());
        holder.todayActive.setText("日活跃 " + resultsEntity.getTodayCarry());
        holder.tvTime.setText(resultsEntity.getCreated().substring(11, 16));
        holder.tvStatusInfo.setText(resultsEntity.getStatusDisplay());
        holder.tvInfo.setText(resultsEntity.getValueDescription());
        holder.tvOneLiveness.setText("+" + resultsEntity.getValueNum());
    }

    private void showCategory(LivenessVH holder) {
        if (!isVisibleOf(holder.rlSum)) holder.rlSum.setVisibility(View.VISIBLE);
    }

    private void hideCategory(LivenessVH holder) {
        if (isVisibleOf(holder.rlSum)) holder.rlSum.setVisibility(View.GONE);
    }

    private boolean isVisibleOf(View view) {
        return view.getVisibility() == View.VISIBLE;
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    static class LivenessVH extends RecyclerView.ViewHolder {
        int id = R.layout.item_mamaliveness;
        View card;
        @Bind(R.id.tv_day)
        TextView tvDay;
        @Bind(R.id.today_active)
        TextView todayActive;
        @Bind(R.id.rl_sum)
        RelativeLayout rlSum;
        @Bind(R.id.tv_time)
        TextView tvTime;
        @Bind(R.id.tv_status_info)
        TextView tvStatusInfo;
        @Bind(R.id.tv_info)
        TextView tvInfo;
        @Bind(R.id.tv_one_liveness)
        TextView tvOneLiveness;

        public LivenessVH(View itemView) {
            super(itemView);
            card = itemView;
            ButterKnife.bind(this, itemView);
        }
    }
}
