package com.jimei.xiaolumeimei.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.XlmmApp;
import com.jimei.xiaolumeimei.entities.MainTodayBean;
import com.jimei.xiaolumeimei.entities.ShareModelBean;
import com.jimei.xiaolumeimei.entities.WxQrcode;
import com.jimei.xiaolumeimei.service.ServiceResponse;
import com.jimei.xiaolumeimei.ui.activity.product.ProductDetailActivity;
import com.jimei.xiaolumeimei.ui.activity.user.LoginActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MMNinePicActivity;
import com.jimei.xiaolumeimei.util.LoginUtils;
import com.umeng.analytics.MobclickAgent;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by wisdom on 17/2/14.
 */

public class MainProductAdapter extends RecyclerView.Adapter<MainProductAdapter.ViewHolder> {
    private List<MainTodayBean.ItemsBean> data;
    private Context context;

    public MainProductAdapter(Context context) {
        this.context = context;
        data = new ArrayList<>();
    }

    public void updateWithClear(List<MainTodayBean.ItemsBean> list) {
        data.clear();
        data.addAll(list);
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
        holder.itemView.setOnClickListener(view -> {
            MobclickAgent.onEvent(context, "click_product");
            try {
                int modelId = Integer.parseInt(bean.getModel_id().trim());
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
                                bundle.putInt("model_id", Integer.parseInt(bean.getModel_id().trim()));
                                intent.putExtras(bundle);
                                context.startActivity(intent);
                            }
                        });
                }
            }
        );
        holder.shareLayout.setOnClickListener(view -> XlmmApp.getProductInteractor(context)
            .getShareModel(Integer.parseInt(bean.getModel_id().trim()), new ServiceResponse<ShareModelBean>() {
                @Override
                public void onNext(ShareModelBean shareModel) {
                    OnekeyShare oks = new OnekeyShare();
                    oks.disableSSOWhenAuthorize();
                    oks.setTitle(shareModel.getTitle());
                    oks.setTitleUrl(shareModel.getShare_link());
                    oks.setText(shareModel.getDesc() + shareModel.getShare_link());
                    oks.setImageUrl(shareModel.getShare_img());
                    oks.setUrl(shareModel.getShare_link());
                    Bitmap enableLogo =
                        BitmapFactory.decodeResource(context.getResources(), R.drawable.ssdk_oks_logo_copy);
                    View.OnClickListener listener = view -> {
                        JUtils.copyToClipboard(shareModel.getShare_link() + "");
                        JUtils.Toast("已复制链接");
                    };
                    oks.setCustomerLogo(enableLogo, "复制链接", listener);
                    oks.show(context);
                }

                @Override
                public void onError(Throwable e) {
                    JUtils.Toast("分享失败,请点击重试!");
                }
            }));
    }


    @Override
    public int getItemCount() {
        return data.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name, price, profit;
        LinearLayout textLayout, shareLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.img);
            name = (TextView) itemView.findViewById(R.id.name);
            price = (TextView) itemView.findViewById(R.id.price);
            profit = (TextView) itemView.findViewById(R.id.profit);
            textLayout = (LinearLayout) itemView.findViewById(R.id.text_layout);
            shareLayout = (LinearLayout) itemView.findViewById(R.id.share_layout);
        }
    }

}
