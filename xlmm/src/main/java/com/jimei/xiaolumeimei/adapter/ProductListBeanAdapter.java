package com.jimei.xiaolumeimei.adapter;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jimei.library.utils.ViewUtils;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.entities.ProductListBean;
import com.jimei.xiaolumeimei.ui.activity.product.ProductDetailActivity;
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
        holder.name.setText(resultsBean.getName());
        holder.agentPrice.setText("¥" + resultsBean.getLowest_agent_price());
        holder.stdSalePrice.setText("/¥" + resultsBean.getLowest_std_sale_price());
        ViewUtils.loadImgToImgViewWithPlaceholderFragment(context,
                holder.image, resultsBean.getHead_img());
        holder.card.setOnClickListener(v -> {
            MobclickAgent.onEvent(mContext, "click_product");
            int modelId = resultsBean.getId();
            Intent intent = new Intent(mContext, ProductDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("model_id", modelId);
            intent.putExtras(bundle);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(context.getActivity(), holder.image, "wisdom");
                context.startActivity(intent, options.toBundle());
            } else {
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    static class ChildListVH extends RecyclerView.ViewHolder {
        View card;
        @Bind(R.id.product_list_image)
        ImageView image;
        @Bind(R.id.product_list_name)
        TextView name;
        @Bind(R.id.product_list_agent_price)
        TextView agentPrice;
        @Bind(R.id.product_list_stdsale_price)
        TextView stdSalePrice;
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
