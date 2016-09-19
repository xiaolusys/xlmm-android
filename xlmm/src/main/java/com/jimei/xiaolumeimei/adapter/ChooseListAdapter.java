package com.jimei.xiaolumeimei.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.entities.ChooseListBean;
import com.jimei.xiaolumeimei.ui.activity.product.ProductDetailActivity;
import com.jimei.xiaolumeimei.utils.ViewUtils;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wisdom on 16/9/10.
 */
public class ChooseListAdapter extends RecyclerView.Adapter<ChooseListAdapter.ViewHolder> {
    private List<ChooseListBean.ResultsBean> mList;
    private Context mContext;

    public ChooseListAdapter(Context context) {
        mList = new ArrayList<>();
        mContext = context;
    }

    public void clear() {
        mList.clear();
        notifyDataSetChanged();
    }

    public void update(List<ChooseListBean.ResultsBean> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void updateWithClear(List<ChooseListBean.ResultsBean> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chooselist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ChooseListBean.ResultsBean bean = mList.get(position);
        holder.name.setText(bean.getName());
        ViewUtils.loadImgToImgView(mContext, holder.imageChooselist,
                bean.getPic_path());
        holder.agentPrice.setText(
                "¥" + (float) (Math.round(bean.getLowest_agent_price() * 100)) / 100);
        holder.stdSalePrice.setText(
                "/¥" + (float) (Math.round(bean.getLowest_std_sale_price() * 100)) / 100);
        holder.rebetAmount.setText("你的" + bean.getRebet_amount_desc());
        holder.lockNum.setText(bean.getSale_num_desc());
        holder.vip.setText(bean.getLevel_info().getNext_agencylevel_desc());
        holder.vipMoney.setText(bean.getNext_rebet_amount_desc());
        holder.imageChooselist.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, ProductDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("model_id", bean.getId());
            intent.putExtras(bundle);
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        int id = R.layout.item_chooselist;
        @Bind(R.id.image_chooselist)
        ImageView imageChooselist;
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.agent_price)
        TextView agentPrice;
        @Bind(R.id.std_sale_price)
        TextView stdSalePrice;
        @Bind(R.id.rebet_amount)
        TextView rebetAmount;
        @Bind(R.id.lock_num)
        TextView lockNum;
        @Bind(R.id.vip)
        TextView vip;
        @Bind(R.id.vip_money)
        TextView vipMoney;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            AutoUtils.auto(itemView);
        }
    }
}
