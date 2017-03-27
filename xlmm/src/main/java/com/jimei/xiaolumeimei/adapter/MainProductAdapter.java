package com.jimei.xiaolumeimei.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jimei.library.utils.JUtils;
import com.jimei.library.utils.ViewUtils;
import com.jimei.library.widget.NoDoubleClickListener;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.XlmmApp;
import com.jimei.xiaolumeimei.base.BaseActivity;
import com.jimei.xiaolumeimei.entities.ActivityBean;
import com.jimei.xiaolumeimei.entities.MainTodayBean;
import com.jimei.xiaolumeimei.entities.ShareModelBean;
import com.jimei.xiaolumeimei.entities.WxQrcode;
import com.jimei.xiaolumeimei.service.ServiceResponse;
import com.jimei.xiaolumeimei.ui.activity.product.ProductDetailActivity;
import com.jimei.xiaolumeimei.ui.activity.user.LoginActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MMNinePicActivity;
import com.jimei.xiaolumeimei.util.LoginUtils;
import com.jimei.xiaolumeimei.util.ShareUtils;
import com.umeng.analytics.MobclickAgent;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wisdom on 17/2/14.
 */

public class MainProductAdapter extends RecyclerView.Adapter<MainProductAdapter.ViewHolder> {
    private List<MainTodayBean.ItemsBean> data;
    private BaseActivity context;

    public MainProductAdapter(BaseActivity context) {
        this.context = context;
        data = new ArrayList<>();
    }

    public void updateWithClear(List<MainTodayBean.ItemsBean> list) {
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
        View view = LayoutInflater.from(context).inflate(R.layout.item_main_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MainTodayBean.ItemsBean bean = data.get(position);
        ViewUtils.loadImgToImgView(context, holder.image, bean.getPic());
        holder.name.setText(bean.getName());
        String price = new DecimalFormat("0.0").format(bean.getPrice());
        holder.price.setText("售价: ¥" + price);
        String min = new DecimalFormat("0.0").format(bean.getProfit().getMin());
        String max = new DecimalFormat("0.0").format(bean.getProfit().getMax());
        if (bean.getProfit().getMin() == 0) {
            min = "0";
        }
        if (bean.getProfit().getMax() == 0) {
            max = "0";
        }
        holder.profit.setText("利润: ¥" + min + " ~ ¥" + max);
        holder.productLayout.setOnClickListener(view -> {
            MobclickAgent.onEvent(context, "click_product");
            try {
                int modelId = bean.getModel_id();
                Intent intent = new Intent(context, ProductDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("model_id", modelId);
                intent.putExtras(bundle);
                context.startActivity(intent);
            } catch (Exception ignored) {
            }
        });
        holder.textLayout.setOnClickListener(view -> {
                if (!LoginUtils.checkLoginState(context)) {
                    Bundle carBundle = new Bundle();
                    carBundle.putString("login", "main");
                    Intent intent = new Intent(context, LoginActivity.class);
                    intent.putExtras(carBundle);
                    context.startActivity(intent);
                } else {
                    XlmmApp.getVipInteractor(context)
                        .getWxCode(new ServiceResponse<WxQrcode>() {
                            @Override
                            public void onNext(WxQrcode wxQrcode) {
                                Intent intent = new Intent(context, MMNinePicActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("codeLink", wxQrcode.getQrcode_link());
                                bundle.putInt("model_id", bean.getModel_id());
                                intent.putExtras(bundle);
                                context.startActivity(intent);
                            }
                        });
                }
            }
        );
        holder.shareLayout.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                context.showIndeterminateProgressDialog(false);
                XlmmApp.getProductInteractor(context)
                    .getShareModel(bean.getModel_id(), new ServiceResponse<ShareModelBean>() {
                        @Override
                        public void onNext(ShareModelBean shareModel) {
                            context.hideIndeterminateProgressDialog();
                            ShareUtils.shareWithModel(shareModel, context);
                        }

                        @Override
                        public void onError(Throwable e) {
                            context.hideIndeterminateProgressDialog();
                            JUtils.Toast("分享失败,请点击重试!");
                        }
                    });
            }
        });
        holder.shareAllLayout.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                context.showIndeterminateProgressDialog(false);
                XlmmApp.getActivityInteractor(context)
                    .get_party_share_content(bean.getActivity_id() + "", new ServiceResponse<ActivityBean>() {
                        @Override
                        public void onNext(ActivityBean activityBean) {
                            context.hideIndeterminateProgressDialog();
                            ShareUtils.shareActivity(activityBean, context);
                        }

                        @Override
                        public void onError(Throwable e) {
                            context.hideIndeterminateProgressDialog();
                            JUtils.Toast("分享失败,请点击重试!");
                        }
                    });
            }
        });
    }


    @Override
    public int getItemCount() {
        return data.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name, price, profit;
        LinearLayout textLayout, shareLayout, productLayout, shareAllLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            productLayout = (LinearLayout) itemView.findViewById(R.id.product_layout);
            image = (ImageView) itemView.findViewById(R.id.img);
            name = (TextView) itemView.findViewById(R.id.name);
            price = (TextView) itemView.findViewById(R.id.price);
            profit = (TextView) itemView.findViewById(R.id.profit);
            textLayout = (LinearLayout) itemView.findViewById(R.id.text_layout);
            shareLayout = (LinearLayout) itemView.findViewById(R.id.share_layout);
            shareAllLayout = (LinearLayout) itemView.findViewById(R.id.share_all_layout);
        }
    }

}
