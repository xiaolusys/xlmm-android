package com.jimei.xiaolumeimei.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.entities.CategoryBean;
import com.jimei.xiaolumeimei.ui.activity.product.CategoryListActivity;
import com.jimei.xiaolumeimei.ui.activity.product.ProductListActivity;
import com.jimei.xiaolumeimei.utils.FileUtils;
import com.jimei.xiaolumeimei.utils.ViewUtils;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wisdom on 16/8/3.
 */
public class CategoryAdapter extends XRecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private Context context;
    private List<CategoryBean> mData;

    public CategoryAdapter(Context context, List<CategoryBean> mData) {
        this.context = context;
        this.mData = mData;
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

    public void clear(){
        mData.clear();
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CategoryBean childsBean = mData.get(position);
        holder.name.setText(childsBean.getName());
        String picAddress = Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/xlmmcategory/" + childsBean.getCid() + ".png";
        if (FileUtils.isFileExist(picAddress)) {
            holder.img.setImageBitmap(BitmapFactory.decodeFile(picAddress));
        } else {
            ViewUtils.loadImgToImgView(context, holder.img, childsBean.getCat_pic());
        }
        if (context instanceof ProductListActivity) {
            holder.item.setOnClickListener(v -> ((ProductListActivity) this.context).refreshData(childsBean.getCid(), true));
        }
        holder.item.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductListActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("type", childsBean.getCid());
            bundle.putString("title", childsBean.getName());
            intent.putExtras(bundle);
            context.startActivity(intent);
            if (context instanceof CategoryListActivity) {
                ((CategoryListActivity) context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends XRecyclerView.ViewHolder {
        View item;
        @Bind(R.id.img)
        ImageView img;
        @Bind(R.id.name)
        TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            item = itemView;
            AutoUtils.autoSize(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
