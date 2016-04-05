/*
 * Copyright 2015 Eric Liu
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.jimei.xiaolumeimei.widget.dragrecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jimei.xiaolumeimei.entities.MMHavaChooseResultBean;
import com.jimei.xiaolumeimei.model.MMProductModel;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rx.schedulers.Schedulers;


public abstract class BaseAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> implements ItemTouchHelperAdapter {

    public Context mContext;
    public List<T> data = new ArrayList<>();
    public final LayoutInflater layoutInflater;

    public OnItemClickListener onItemClickListener;
    public OnItemLongClickListener onItemLongClickListener;
    private SuperRecyclerView recyclerView;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public BaseAdapter(Context context, List<T> data) {
        this.mContext = context;
        this.data = data;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public LayoutInflater getLayoutInflater() {
        return layoutInflater;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onBindViewHolder(VH holder, final int position) {
        if (holder.itemView != null) {
            if (onItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onItemClick(position);
                    }
                });
            }

            if (onItemLongClickListener != null) {
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        onItemLongClickListener.onItemLongClick(position);
                        return false;
                    }
                });
            }
        }
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onViewRecycled(VH holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public void onItemDismiss(int position) {


        data.remove(position);
        recyclerView.getBookendsAdapter().notifyItemRemoved(position);
//        notifyItemRangeChanged(position, data.size());
    }

    @Override
    public void onItemMove(int fromPosition, int targetPosition) {
        Collections.swap(data, fromPosition, targetPosition);
        recyclerView.getBookendsAdapter().notifyItemMoved(fromPosition, targetPosition);
//        notifyItemRangeChanged(fromPosition, targetPosition);
    }

    public void setRecyclerView(SuperRecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public interface OnItemClickListener {

        void onItemClick(int position);

    }

    public interface OnItemLongClickListener {

        void onItemLongClick(int position);

    }


}
