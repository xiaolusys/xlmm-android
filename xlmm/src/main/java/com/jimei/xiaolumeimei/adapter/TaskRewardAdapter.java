package com.jimei.xiaolumeimei.adapter;

import android.support.v7.widget.RecyclerView;
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

    public TaskRewardAdapter(WeekTaskRewardBean bean, int type) {
        this.bean = bean;
        this.type = type;
    }

    @Override
    public TaskRewardHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return null;
    }

    @Override
    public void onBindViewHolder(TaskRewardHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        if (type == XlmmConst.TYPE_REWARD_PERSONAL) {
            return bean.getPersonal_missions().size();
        } else {
            return bean.getGroup_missions().size();
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

        public TaskRewardHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
