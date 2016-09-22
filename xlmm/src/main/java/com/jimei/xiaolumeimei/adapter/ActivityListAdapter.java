package com.jimei.xiaolumeimei.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.entities.PortalBean;
import com.jimei.xiaolumeimei.ui.activity.main.ActivityWebViewActivity;
import com.jimei.xiaolumeimei.ui.activity.user.LoginActivity;
import com.jimei.xiaolumeimei.utils.JumpUtils;
import com.jimei.xiaolumeimei.utils.LoginUtils;
import com.jimei.xiaolumeimei.utils.ViewUtils;
import com.jude.utils.JUtils;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wisdom on 16/9/9.
 */
public class ActivityListAdapter extends BaseAdapter {

    List<PortalBean.ActivitysBean> data;
    Context mContext;

    public ActivityListAdapter(Context context) {
        data = new ArrayList<>();
        mContext = context;
    }

    public void clear() {
        data.clear();
        notifyDataSetChanged();
    }

    public void update(List<PortalBean.ActivitysBean> list) {
        data.addAll(list);
        notifyDataSetChanged();
    }

    public void updateWithClear(List<PortalBean.ActivitysBean> list) {
        clear();
        update(list);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PortalBean.ActivitysBean bean = data.get(position);
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_activity, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        int screenWidth = JUtils.getScreenWidth();
        holder.img.setLayoutParams(new LinearLayout.LayoutParams(screenWidth,screenWidth/2));
        if (bean.getAct_img() != null && !"".equals(bean.getAct_img())) {
            ViewUtils.loadActivityToImgView(mContext, holder.img, bean.getAct_img());
            holder.img.setVisibility(View.VISIBLE);
        }else {
            holder.img.setVisibility(View.GONE);
        }
        if (bean.isLogin_required() && !LoginUtils.checkLoginState(mContext)) {
            JUtils.Toast("登录后才可参加活动");
            Bundle bundle = new Bundle();
            bundle.putString("login", "goactivity");
            bundle.putString("actlink", bean.getAct_link());
            bundle.putInt("id", bean.getId());
            bundle.putString("title", bean.getTitle());
            Intent intent = new Intent(mContext, LoginActivity.class);
            intent.putExtras(bundle);
            mContext.startActivity(intent);
        } else {
            holder.img.setOnClickListener(v ->
                    JumpUtils.jumpToWebViewWithCookies(mContext,
                            bean.getAct_link(),
                            bean.getId(), ActivityWebViewActivity.class,
                            bean.getTitle()));
        }
        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.iv_activity)
        ImageView img;

        public ViewHolder(View itemView) {
            AutoUtils.autoSize(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
