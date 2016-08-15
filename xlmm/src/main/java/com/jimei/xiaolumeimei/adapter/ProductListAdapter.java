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
import com.jimei.xiaolumeimei.entities.ProductListResultBean;
import com.jimei.xiaolumeimei.ui.activity.product.ProductDetailActivity;
import com.jimei.xiaolumeimei.utils.ViewUtils;
import com.jude.utils.JUtils;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wisdom on 16/8/8.
 */
public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ChildListVH> {
    private List<ProductListResultBean.ResultsEntity> mList;

    private Context mContext;

    public ProductListAdapter(Context mContext) {
        this.mContext = mContext;
        mList = new ArrayList<>();
    }

    public void updateWithClear(List<ProductListResultBean.ResultsEntity> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void update(List<ProductListResultBean.ResultsEntity> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public ChildListVH onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_todaylist, parent, false);
        return new ChildListVH(view);
    }

    @Override
    public void onViewRecycled(ChildListVH holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public void onBindViewHolder(final ChildListVH holder, int position) {

        final ProductListResultBean.ResultsEntity resultsEntity = mList.get(position);
        ProductListResultBean.ResultsEntity.ProductModelEntity productModel =
                resultsEntity.getProductModel();

        boolean isSaleopen = resultsEntity.isIsSaleopen();

        try {

            if (isSaleopen) {

                boolean isSaleOut = productModel.isIsSaleOut();

                if (isSaleOut) {
                    holder.saleout.setVisibility(View.VISIBLE);
                } else {
                    holder.saleout.setVisibility(View.INVISIBLE);
                }
            } else {
                holder.saleout.setVisibility(View.VISIBLE);
            }
        } catch (NullPointerException ex) {

        }

        String headImg = resultsEntity.getHeadImg();

        try {
            if (productModel.getName().length() <= 9) {
                holder.childlistName.setText(productModel.getName());
            } else {
                holder.childlistName.setText(productModel.getName().substring(0, 8) + "...");
            }

            holder.childlistAgentPrice.setText("¥" + resultsEntity.getLowestPrice());
            holder.childlistStdsalePrice.setText("/¥" + resultsEntity.getStdSalePrice());
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.card.setTag(new Object());
        ViewUtils.loadImgToImgViewWithPlaceholder(mContext, holder.childlistImage, headImg);
        holder.card.setOnClickListener(v -> {
            int modelId = mList.get(position).getModelId();
            JUtils.Log("" + mList.get(position));
//            JumpUtils.jumpToWebViewWithCookies(mContext, mList.get(position).getWebUrl(),
//                    modelId, ProductPopDetailActvityWeb.class);
            Intent intent = new Intent(mContext, ProductDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("model_id", modelId);
            intent.putExtras(bundle);
            mContext.startActivity(intent);

        });
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }


    static class ChildListVH extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        //int id = R.layout.item_childlist;
        View card;
        @Bind(R.id.childlist_image)
        ImageView childlistImage;
        @Bind(R.id.childlist_name)
        TextView childlistName;
        @Bind(R.id.childlist_agent_price)
        TextView childlistAgentPrice;
        @Bind(R.id.childlist_stdsale_price)
        TextView childlistStdsalePrice;
        @Bind(R.id.saleout)
        TextView saleout;

        public ChildListVH(View itemView) {
            super(itemView);
            card = itemView;
            AutoUtils.autoSize(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
