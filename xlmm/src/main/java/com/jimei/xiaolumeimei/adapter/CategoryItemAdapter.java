package com.jimei.xiaolumeimei.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.entities.CategoryBean;
import com.jimei.xiaolumeimei.ui.activity.product.ProductListActivity;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wisdom on 17/2/16.
 */

public class CategoryItemAdapter extends XRecyclerView.Adapter<CategoryItemAdapter.ViewHolder> {

    private List<CategoryBean> mData;
    private Context mContext;

    public CategoryItemAdapter(Context mContext) {
        this.mContext = mContext;
        mData = new ArrayList<>();
    }

    public void update(List<CategoryBean> list) {
        mData.addAll(list);
        notifyDataSetChanged();
    }

    public void updateWithClear(List<CategoryBean> list) {
        mData.clear();
        mData.addAll(list);
        notifyDataSetChanged();
    }

    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CategoryBean bean = mData.get(position);
        holder.name.setText(bean.getName());
        holder.name.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, ProductListActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("type", bean.getCid());
            bundle.putString("title", bean.getName());
            intent.putExtras(bundle);
            mContext.startActivity(intent);
        });
        GridLayoutManager manager = new GridLayoutManager(mContext, 3);
        holder.xrv.setLayoutManager(manager);
        holder.xrv.setOverScrollMode(View.OVER_SCROLL_NEVER);
        holder.xrv.setPullRefreshEnabled(false);
        holder.xrv.setLoadingMoreEnabled(false);
        CategoryAdapter adapter = new CategoryAdapter(mContext);
        adapter.updateWithClear(bean.getChilds());
        holder.xrv.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class ViewHolder extends XRecyclerView.ViewHolder {
        View item;
        @Bind(R.id.item_name)
        TextView name;
        @Bind(R.id.item_xrv)
        XRecyclerView xrv;

        public ViewHolder(View itemView) {
            super(itemView);
            item = itemView;
            AutoUtils.autoSize(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
