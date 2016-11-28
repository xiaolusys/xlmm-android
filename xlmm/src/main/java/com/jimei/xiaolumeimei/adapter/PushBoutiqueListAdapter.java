package com.jimei.xiaolumeimei.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.library.utils.ViewUtils;
import com.jimei.library.widget.RoundCornerImageView;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.entities.BoutiqueListBean;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MMNinePicActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wisdom on 16/11/24.
 */

public class PushBoutiqueListAdapter extends XRecyclerView.Adapter<PushBoutiqueListAdapter.ViewHolder> {
    private List<BoutiqueListBean.ResultsBean> mList;
    private Context context;
    private String codeLink;

    public PushBoutiqueListAdapter(Context context) {
        this.context = context;
        mList = new ArrayList<>();
    }

    public void update(List<BoutiqueListBean.ResultsBean> data) {
        mList.addAll(data);
        notifyDataSetChanged();
    }

    public void updateWithClear(List<BoutiqueListBean.ResultsBean> data) {
        mList.clear();
        mList.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_push_boutique, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BoutiqueListBean.ResultsBean bean = mList.get(position);
        holder.name.setText(bean.getName());
        ViewUtils.loadImgToImgView(context, holder.img, bean.getHead_img());
        holder.layout.setOnClickListener(v -> {
            Intent intent = new Intent(context, MMNinePicActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("codeLink", codeLink);
            bundle.putInt("model_id", bean.getId());
            intent.putExtras(bundle);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setCodeLink(String codeLink) {
        this.codeLink = codeLink;
    }

    class ViewHolder extends XRecyclerView.ViewHolder {
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.img)
        RoundCornerImageView img;
        @Bind(R.id.layout)
        LinearLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
