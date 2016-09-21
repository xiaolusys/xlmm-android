package com.jimei.xiaolumeimei.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.entities.WeekTaskRewardBean;
import com.zhy.autolayout.utils.AutoUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wisdom on 16/8/22.
 */
public class TaskRewardAdapter extends RecyclerView.Adapter<TaskRewardAdapter.TaskRewardHolder> {

    private WeekTaskRewardBean bean;
    private int type;
    private Context context;

    public TaskRewardAdapter(Context context, WeekTaskRewardBean bean, int type) {
        this.context = context;
        this.bean = bean;
        this.type = type;
    }

    @Override
    public TaskRewardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_task_reward, parent, false);
        return new TaskRewardHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskRewardHolder holder, int position) {
        if (position == 0) {
            holder.tv1.setText("奖励项目");
            holder.tv2.setText("目标数");
            holder.tv3.setText("完成数");
            holder.tv4.setText("奖金");
            holder.tv5.setText("状态");
        } else {
            if (type == XlmmConst.TYPE_REWARD_PERSONAL) {
                WeekTaskRewardBean.PersonalMissionsBean personalData = bean.getPersonal_missions().get(position - 1);
                holder.tv1.setText(personalData.getMission().getName());
                holder.tv2.setText(personalData.getTarget_value() + "");
                holder.tv3.setText(personalData.getFinish_value() + "");
                holder.tv4.setText("¥" + personalData.getAward_amount() / 100.00);
                holder.tv5.setText(personalData.getStatus_name());
            } else if (type == XlmmConst.TYPE_REWARD_TEAM) {
                WeekTaskRewardBean.GroupMissionsBean teamData = bean.getGroup_missions().get(position - 1);
                holder.tv1.setText(teamData.getMission().getName());
                holder.tv2.setText(teamData.getTarget_value() + "");
                holder.tv3.setText(teamData.getFinish_value() + "");
                holder.tv4.setText("¥" + teamData.getAward_amount() / 100.00);
                holder.tv5.setText(teamData.getStatus_name());
            }
        }
    }

    @Override
    public int getItemCount() {
        if (type == XlmmConst.TYPE_REWARD_PERSONAL) {
            return bean.getPersonal_missions().size() + 1;
        } else {
            return bean.getGroup_missions().size() + 1;
        }
    }

    class TaskRewardHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv1)
        TextView tv1;
        @Bind(R.id.tv2)
        TextView tv2;
        @Bind(R.id.tv3)
        TextView tv3;
        @Bind(R.id.tv4)
        TextView tv4;
        @Bind(R.id.tv5)
        TextView tv5;

        TaskRewardHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
