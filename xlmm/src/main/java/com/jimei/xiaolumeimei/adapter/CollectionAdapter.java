package com.jimei.xiaolumeimei.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.entities.CollectionBean;
import com.jimei.xiaolumeimei.entities.CollectionResultBean;
import com.jimei.xiaolumeimei.model.ProductModel;
import com.jimei.xiaolumeimei.ui.activity.product.ProductDetailActivity;
import com.jimei.xiaolumeimei.ui.fragment.v1.CollectionFragment;
import com.jimei.xiaolumeimei.utils.ViewUtils;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.schedulers.Schedulers;

/**
 * Created by wisdom on 16/7/28.
 */
public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.CollectionViewHolder> {
    private CollectionFragment mFragment;
    private Context mContext;
    private List<CollectionBean> mData;

    public CollectionAdapter(CollectionFragment mFragment, Context mContext, List<CollectionBean> mData) {
        this.mFragment = mFragment;
        this.mContext = mContext;
        this.mData = mData;
    }

    public void update(List<CollectionBean> list) {
        mData.addAll(list);
        notifyDataSetChanged();
    }

    public void updateWithClear(List<CollectionBean> list) {
        mData.clear();
        mData.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public CollectionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_collection, parent, false);
        return new CollectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CollectionViewHolder holder, int position) {
        CollectionBean bean = mData.get(position);
        CollectionBean.ModelproductBean modelproductBean = bean.getModelproduct();
        ViewUtils.loadImgToImgViewWithPlaceholderFragment(mFragment, holder.img, modelproductBean.getHead_img());
        holder.name.setText(modelproductBean.getName());
        holder.agentPrice.setText("¥" + modelproductBean.getLowest_agent_price());
        holder.salePrice.setText("/¥" + modelproductBean.getLowest_std_sale_price());
        holder.flag.setOnClickListener(v -> ProductModel.getInstance()
                .deleteCollection(modelproductBean.getId())
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<CollectionResultBean>() {
                    @Override
                    public void onNext(CollectionResultBean collectionResultBean) {
                        if (collectionResultBean != null) {
                            JUtils.Toast(collectionResultBean.getInfo());
                            if (collectionResultBean.getCode() == 0) {
                                mData.remove(position);
                                notifyDataSetChanged();
                                if (mData.size() == 0) {
                                    mFragment.showEmpty();
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        JUtils.Toast("取消失败!");
                    }
                }));
        holder.item.setOnClickListener(v -> {
//                JumpUtils.jumpToWebViewWithCookies(
//                mContext, modelproductBean.getWeb_url(),
//                bean.getId(), ProductPopDetailActvityWeb.class)
            Intent intent = new Intent(mContext, ProductDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("model_id", bean.getModelproduct().getId());
            intent.putExtras(bundle);
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class CollectionViewHolder extends RecyclerView.ViewHolder {
        View item;
        @Bind(R.id.img)
        ImageView img;
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.flag)
        RelativeLayout flag;
        @Bind(R.id.agent_price)
        TextView agentPrice;
        @Bind(R.id.sale_price)
        TextView salePrice;

        public CollectionViewHolder(View itemView) {
            super(itemView);
            item = itemView;
            AutoUtils.autoSize(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
