package com.jimei.xiaolumeimei.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.library.utils.ViewUtils;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.entities.ProductListBean;
import com.jimei.xiaolumeimei.ui.activity.product.ProductDetailActivity;
import com.jimei.xiaolumeimei.widget.NoDoubleClickListener;
import com.umeng.analytics.MobclickAgent;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wisdom on 16/8/6.
 */
public class ProductListAdapter extends XRecyclerView.Adapter<ProductListAdapter.ViewHolder> {

    private List<ProductListBean.ResultsBean> data;
    private Activity context;

    public ProductListAdapter(Activity context) {
        data = new ArrayList<>();
        this.context = context;
    }

    public void update(List<ProductListBean.ResultsBean> list) {
        data.addAll(list);
        notifyDataSetChanged();
    }

    public void updateWithClear(List<ProductListBean.ResultsBean> list) {
        data.clear();
        data.addAll(list);
        notifyDataSetChanged();
    }

    public void clear() {
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
        ProductListBean.ResultsBean resultsBean = data.get(position);
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
        String watermark_op = resultsBean.getWatermark_op();
        if (watermark_op != null && !"".equals(watermark_op)) {
            ViewUtils.loadImgToImgViewWithWaterMark(context, holder.image, resultsBean.getHead_img(), watermark_op);
        } else {
            ViewUtils.loadImgToImgViewWithPlaceholder(context, holder.image, resultsBean.getHead_img());
        }
        holder.name.setText(resultsBean.getName());
        holder.agentPrice.setText("¥" + resultsBean.getLowest_agent_price());
        holder.stdSalePrice.setText("/¥" + resultsBean.getLowest_std_sale_price());
        holder.card.setOnClickListener(
                new NoDoubleClickListener() {
                    @Override
                    protected void onNoDoubleClick(View v) {
                        MobclickAgent.onEvent(context, "click_product");
                        int modelId = resultsBean.getId();
                        Intent intent = new Intent(context, ProductDetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("model_id", modelId);
                        intent.putExtras(bundle);
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(context, holder.image, "wisdom");
//                            context.startActivity(intent, options.toBundle());
//                        } else {
//                            context.startActivity(intent);
//                        }
                        context.startActivity(intent);
                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends XRecyclerView.ViewHolder {
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


        public ViewHolder(View itemView) {
            super(itemView);
            card = itemView;
            AutoUtils.autoSize(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
