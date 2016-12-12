package com.jimei.xiaolumeimei.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jimei.library.utils.JUtils;
import com.jimei.library.utils.ViewUtils;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.entities.ActivityGoodListBean;
import com.jimei.xiaolumeimei.ui.activity.product.ProductDetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wisdom on 16/12/9.
 */

class BoutiqueProductAdapter extends RecyclerView.Adapter<BoutiqueProductAdapter.ViewHolder> {
    private Context context;
    private List<ActivityGoodListBean.ProductsBean> mList;

    BoutiqueProductAdapter(Context context) {
        this.context = context;
        this.mList = new ArrayList<>();
    }

    public void updateWithClear(List<ActivityGoodListBean.ProductsBean> data) {
        mList.clear();
        mList.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_boutique_list_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position == mList.size()) {
            holder.layout.setVisibility(View.VISIBLE);
            holder.text.setText("");
            holder.img.setVisibility(View.GONE);
            holder.layout.setOnClickListener(v -> JUtils.Toast("点击了一下!"));
        } else {
            holder.layout.setVisibility(View.GONE);
            holder.img.setVisibility(View.VISIBLE);
            ActivityGoodListBean.ProductsBean bean = mList.get(position);
            ViewUtils.loadImgToImgView(context, holder.img, bean.getPic());
            holder.text.setText("¥" + bean.getLowestPrice());
            holder.img.setOnClickListener(v -> {
                Intent intent = new Intent(context, ProductDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("model_id", bean.getModelId());
                intent.putExtras(bundle);
                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList.size() + 1;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.img)
        ImageView img;
        @Bind(R.id.text)
        TextView text;
        @Bind(R.id.last_layout)
        LinearLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
