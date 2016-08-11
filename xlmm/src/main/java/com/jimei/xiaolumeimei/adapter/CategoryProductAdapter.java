package com.jimei.xiaolumeimei.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.entities.CategoryProductListBean;
import com.jimei.xiaolumeimei.ui.activity.product.ProductDetailActivity;
import com.jimei.xiaolumeimei.utils.ViewUtils;
import com.umeng.analytics.MobclickAgent;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wisdom on 16/8/6.
 */
public class CategoryProductAdapter extends XRecyclerView.Adapter<CategoryProductAdapter.ViewHolder> {

    private List<CategoryProductListBean.ResultsBean> data;
    private Context context;

    public CategoryProductAdapter(List<CategoryProductListBean.ResultsBean> data, Context context) {
        this.data = data;
        this.context = context;
    }

    public void update(List<CategoryProductListBean.ResultsBean> list) {
        data.addAll(list);
        notifyDataSetChanged();
    }

    public void updateWithClear(List<CategoryProductListBean.ResultsBean> list) {
        data.clear();
        data.addAll(list);
        notifyDataSetChanged();
    }

    public void clear(){
        data.clear();
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_todaylist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CategoryProductListBean.ResultsBean resultsBean = data.get(position);
        if ("will".equals(resultsBean.getSale_state())) {
            holder.salenotopen.setVisibility(View.VISIBLE);
            holder.saleout.setVisibility(View.GONE);
        } else if (resultsBean.isIs_saleout()) {
            holder.saleout.setVisibility(View.VISIBLE);
            holder.salenotopen.setVisibility(View.GONE);
        }
        ViewUtils.loadImgToImgViewWithPlaceholder(context, holder.childlistImage, resultsBean.getHead_img());
        holder.childlistName.setText(resultsBean.getName());
        holder.childlistAgentPrice.setText("¥" + resultsBean.getLowest_agent_price());
        holder.childlistStdsalePrice.setText("/¥" + resultsBean.getLowest_std_sale_price());
        holder.card.setOnClickListener(v -> {
            MobclickAgent.onEvent(context, "ProductID");
            int modelId = resultsBean.getId();
//            JumpUtils.jumpToWebViewWithCookies(context, resultsBean.getWeb_url(), modelId,
//                    ProductPopDetailActvityWeb.class);
            Intent intent = new Intent(context, ProductDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("model_id",modelId);
            intent.putExtras(bundle);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends XRecyclerView.ViewHolder {
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
        @Bind(R.id.salenotopen)
        TextView salenotopen;


        public ViewHolder(View itemView) {
            super(itemView);
            card = itemView;
            AutoUtils.autoSize(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
