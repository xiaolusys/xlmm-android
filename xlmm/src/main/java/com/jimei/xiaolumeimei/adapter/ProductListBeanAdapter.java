package com.jimei.xiaolumeimei.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.entities.ProductListBean;
import com.jimei.xiaolumeimei.ui.activity.product.ProductDetailActivity;
import com.jimei.xiaolumeimei.utils.ViewUtils;
import com.umeng.analytics.MobclickAgent;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wisdom on 16/8/16.
 */
public class ProductListBeanAdapter extends RecyclerView.Adapter<ProductListBeanAdapter.ChildListVH> {
    private List<ProductListBean.ResultsBean> mList;
    private Fragment context;
    private Context mContext;

    public ProductListBeanAdapter(Context mContext, Fragment context) {
        this.mContext = mContext;
        this.context = context;
        mList = new ArrayList<>();
    }

    public void updateWithClear(List<ProductListBean.ResultsBean> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void update(List<ProductListBean.ResultsBean> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void clear() {
        mList.clear();
        notifyDataSetChanged();
    }

    @Override
    public ChildListVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_todaylist, parent, false);
        return new ChildListVH(view);
    }

    @Override
    public void onBindViewHolder(final ChildListVH holder, int position) {
        ProductListBean.ResultsBean resultsBean = mList.get(position);
        String sale_state = resultsBean.getSale_state();
        try {
            if ("will".equals(sale_state)) {
                holder.saleStatus.setText("即将开售");
                holder.saleStatus.setVisibility(View.VISIBLE);
            } else if ("off".equals(sale_state)) {
                holder.saleStatus.setText("已下架");
                holder.saleStatus.setVisibility(View.VISIBLE);
            } else if ("on".equals(sale_state) && resultsBean.isIs_saleout()) {
                holder.saleStatus.setText("已抢光");
                holder.saleStatus.setVisibility(View.VISIBLE);
            } else {
                holder.saleStatus.setVisibility(View.GONE);
            }
            if (resultsBean.getName().length() <= 9) {
                holder.childlistName.setText(resultsBean.getName());
            } else {
                holder.childlistName.setText(resultsBean.getName().substring(0, 8) + "...");
            }

            holder.childlistAgentPrice.setText("¥" + resultsBean.getLowest_agent_price());
            holder.childlistStdsalePrice.setText("/¥" + resultsBean.getLowest_std_sale_price());
        } catch (Exception e) {
            e.printStackTrace();
        }
        ViewUtils.loadImgToImgViewWithPlaceholderFragment(context,
                holder.childlistImage, resultsBean.getHead_img());
        holder.card.setOnClickListener(v -> {
            MobclickAgent.onEvent(mContext, "ProductID");
            int modelId = resultsBean.getId();
            Intent intent = new Intent(mContext, ProductDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("model_id", modelId);
            intent.putExtras(bundle);
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    static class ChildListVH extends RecyclerView.ViewHolder {
        View card;
        @Bind(R.id.childlist_image)
        ImageView childlistImage;
        @Bind(R.id.childlist_name)
        TextView childlistName;
        @Bind(R.id.childlist_agent_price)
        TextView childlistAgentPrice;
        @Bind(R.id.childlist_stdsale_price)
        TextView childlistStdsalePrice;
        @Bind(R.id.sale_status)
        TextView saleStatus;

        public ChildListVH(View itemView) {
            super(itemView);
            card = itemView;
            AutoUtils.autoSize(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
