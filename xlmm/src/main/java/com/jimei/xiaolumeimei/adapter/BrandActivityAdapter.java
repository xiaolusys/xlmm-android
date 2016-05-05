package com.jimei.xiaolumeimei.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.entities.BrandListBean;
import com.jimei.xiaolumeimei.ui.activity.product.ProductDetailActvityWeb;
import com.jude.utils.JUtils;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BrandActivityAdapter extends RecyclerView.Adapter<BrandActivityAdapter.BrandlistVH> {

    private List<BrandListBean.ResultsBean> mList;

    private Context mContext;

    public BrandActivityAdapter(Context mContext) {
        JUtils.Log("MainActivity", "-----------BrandlistAdapter");
        this.mContext = mContext;
        mList = new ArrayList<>();
    }

    public void updateWithClear(List<BrandListBean.ResultsBean> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void update(List<BrandListBean.ResultsBean> list) {

        mList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public BrandlistVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_todaylist, parent, false);
        return new BrandlistVH(view);
    }

    @Override
    public void onBindViewHolder(BrandlistVH holder, int position) {

        BrandListBean.ResultsBean resultsEntity = mList.get(position);
        String picPath = resultsEntity.getProduct_img();
        Glide.with(mContext).load(picPath).diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop().into(holder.brandImag);

        holder.productLowestPrice.setText("¥" + resultsEntity.getProduct_lowest_price());
        holder.productStdSalePrice.setText("/¥" + resultsEntity.getProduct_std_sale_price());
        holder.nameTv.setText(resultsEntity.getProduct_name());

        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ProductDetailActvityWeb.class);
                Bundle bundle = new Bundle();
                bundle.putString("product_id", resultsEntity.getProduct_id() + "");
                intent.putExtras(bundle);
                mContext.startActivity(intent);
                ((Activity) mContext).finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class BrandlistVH extends RecyclerView.ViewHolder {

        private final View cardview;
        int id = R.layout.item_todaylist;
        @Bind(R.id.childlist_image)
        ImageView brandImag;
        @Bind(R.id.childlist_agent_price)
        TextView productLowestPrice;
        @Bind(R.id.childlist_stdsale_price)
        TextView productStdSalePrice;
        @Bind(R.id.childlist_name)
        TextView nameTv;

        public BrandlistVH(View itemView) {
            super(itemView);
            cardview = itemView;
            AutoUtils.autoSize(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface itemOnclickListener {
        void itemClick();
    }
}
