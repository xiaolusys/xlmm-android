package com.jimei.xiaolumeimei.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jimei.library.utils.JUtils;
import com.jimei.library.utils.ViewUtils;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.entities.PortalBean;
import com.jimei.xiaolumeimei.ui.activity.main.ActivityWebViewActivity;
import com.jimei.xiaolumeimei.ui.activity.user.LoginActivity;
import com.jimei.xiaolumeimei.utils.JumpUtils;
import com.jimei.xiaolumeimei.utils.LoginUtils;
import com.jimei.xiaolumeimei.widget.NoDoubleClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wisdom on 16/11/23.
 */

public class MainActivityAdapter extends RecyclerView.Adapter<MainActivityAdapter.ViewHolder> {
    private Activity activity;
    private List<PortalBean.ActivitysBean> mList;

    public MainActivityAdapter(Activity activity) {
        this.activity = activity;
        mList = new ArrayList<>();
    }

    public void updateWithClear(List<PortalBean.ActivitysBean> data) {
        mList.clear();
        mList.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_main_activity, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PortalBean.ActivitysBean bean = mList.get(position);
        if (bean.getAct_img() != null && !"".equals(bean.getAct_img())) {
            ViewUtils.loadActivityToImgView(activity, holder.img, bean.getAct_img());
            holder.img.setVisibility(View.VISIBLE);
        } else {
            holder.img.setVisibility(View.GONE);
        }
        if (bean.isLogin_required() && !LoginUtils.checkLoginState(activity)) {
            JUtils.Toast("登录后才可参加活动");
            Bundle bundle = new Bundle();
            bundle.putString("login", "goactivity");
            bundle.putString("actlink", bean.getAct_link());
            bundle.putInt("id", bean.getId());
            bundle.putString("title", bean.getTitle());
            Intent intent = new Intent(activity, LoginActivity.class);
            intent.putExtras(bundle);
            activity.startActivity(intent);
        } else {
            holder.img.setOnClickListener(
                    new NoDoubleClickListener() {
                        @Override
                        protected void onNoDoubleClick(View v) {
                            JumpUtils.jumpToWebViewWithCookies(activity,
                                    bean.getAct_link(),
                                    bean.getId(), ActivityWebViewActivity.class,
                                    bean.getTitle());
                        }
                    }
            );
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
        }
    }
}
