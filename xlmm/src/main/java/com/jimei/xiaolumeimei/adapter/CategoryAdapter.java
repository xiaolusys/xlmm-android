package com.jimei.xiaolumeimei.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.library.utils.FileUtils;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseActivity;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.entities.CategoryBean;
import com.jimei.xiaolumeimei.ui.activity.product.ProductListActivity;
import com.jimei.xiaolumeimei.widget.NoDoubleClickListener;
import com.zhy.autolayout.utils.AutoUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by wisdom on 16/8/3.
 */
public class CategoryAdapter extends XRecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private BaseActivity mActivity;
    private List<CategoryBean> mData;

    public CategoryAdapter(BaseActivity context) {
        this.mActivity = context;
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
        View view = LayoutInflater.from(mActivity).inflate(R.layout.item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CategoryBean childsBean = mData.get(position);
        Glide.with(mActivity).load(R.drawable.place_holder).crossFade().into(holder.img);
        holder.name.setText(childsBean.getName());
        String picAddress = XlmmConst.XLMM_DIR + "category/" + childsBean.getCid() + ".png";
        if (FileUtils.isFileExist(picAddress)) {
            Glide.with(mActivity).load(new File(picAddress)).crossFade().into(holder.img);
        } else {
            if (childsBean.getCat_pic() != null && !"".equals(childsBean.getCat_pic())) {
                OkHttpUtils.get().url(childsBean.getCat_pic()).build()
                    .execute(new FileCallBack(XlmmConst.XLMM_DIR + "category/", childsBean.getCid() + ".png") {
                        @Override
                        public void onError(Call call, Exception e, int id) {

                        }

                        @Override
                        public void onResponse(File response, int id) {
                            Glide.with(mActivity).load(new File(picAddress)).crossFade().into(holder.img);
                        }
                    });
            }
        }
        holder.item.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                Intent intent = new Intent(mActivity, ProductListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("type", childsBean.getCid());
                bundle.putString("title", childsBean.getName());
                intent.putExtras(bundle);
                mActivity.startActivity(intent);
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
