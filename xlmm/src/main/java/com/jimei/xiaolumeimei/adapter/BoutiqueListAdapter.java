package com.jimei.xiaolumeimei.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jimei.library.utils.JUtils;
import com.jimei.library.utils.ViewUtils;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseActivity;
import com.jimei.xiaolumeimei.entities.ActivityGoodListBean;
import com.jimei.xiaolumeimei.entities.PortalBean;
import com.jimei.xiaolumeimei.ui.activity.main.ActivityWebViewActivity;
import com.jimei.xiaolumeimei.ui.activity.user.LoginActivity;
import com.jimei.xiaolumeimei.utils.JumpUtils;
import com.jimei.xiaolumeimei.utils.LoginUtils;
import com.jimei.xiaolumeimei.widget.NoDoubleClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wisdom on 16/12/9.
 */

public class BoutiqueListAdapter extends RecyclerView.Adapter<BoutiqueListAdapter.ViewHolder> {
    private List<PortalBean.ActivitysBean> mList;
    private List<ActivityGoodListBean.ProductsBean> productsBeanList;
    private BaseActivity context;

    public BoutiqueListAdapter(BaseActivity context) {
        this.mList = new ArrayList<>();
        this.productsBeanList = new ArrayList<>();
        this.context = context;
    }

    public void updateWithClear(List<PortalBean.ActivitysBean> data) {
        mList.clear();
        mList.addAll(data);
        notifyDataSetChanged();
    }

    public void addProductList(List<ActivityGoodListBean.ProductsBean> data) {
        productsBeanList.addAll(data);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_boutique_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PortalBean.ActivitysBean bean = mList.get(position);
        ViewUtils.loadActivityToImgView(context, holder.img, bean.getAct_img());
        if (bean.isLogin_required() && !LoginUtils.checkLoginState(context)) {
            JUtils.Toast("登录后才可参加活动");
            Bundle bundle = new Bundle();
            bundle.putString("login", "goactivity");
            bundle.putString("actlink", bean.getAct_link());
            bundle.putInt("id", bean.getId());
            bundle.putString("title", bean.getTitle());
            Intent intent = new Intent(context, LoginActivity.class);
            intent.putExtras(bundle);
            context.startActivity(intent);
        } else {
            holder.img.setOnClickListener(
                    new NoDoubleClickListener() {
                        @Override
                        protected void onNoDoubleClick(View v) {
                            JumpUtils.jumpToWebViewWithCookies(context,
                                    bean.getAct_link(),
                                    bean.getId(), ActivityWebViewActivity.class,
                                    bean.getTitle());
                        }
                    }
            );
        }
        if (holder.recyclerView.getAdapter() == null) {
            holder.recyclerView.setLayoutManager(
                    new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            holder.recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
            BoutiqueProductAdapter adapter = new BoutiqueProductAdapter(context);
            holder.recyclerView.setAdapter(adapter);
        }
        ((BoutiqueProductAdapter) holder.recyclerView.getAdapter()).updateWithClear(productsBeanList);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.img)
        ImageView img;
        @Bind(R.id.recycler_view)
        RecyclerView recyclerView;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
