package com.jimei.xiaolumeimei.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.entities.CartsInfoBean;
import com.jimei.xiaolumeimei.entities.CodeBean;
import com.jimei.xiaolumeimei.model.CartsModel;
import com.jimei.xiaolumeimei.ui.activity.product.ProductDetailActivity;
import com.jimei.xiaolumeimei.ui.activity.trade.CartActivity;
import com.jimei.xiaolumeimei.utils.ViewUtils;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Response;
import rx.schedulers.Schedulers;

/**
 * Created by wisdom on 16/9/3.
 */
public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder> {
    private List<CartsInfoBean> mList;
    private CartActivity mActivity;

    public CartListAdapter(CartActivity mActivity, List<CartsInfoBean> mList) {
        this.mList = mList;
        this.mActivity = mActivity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.item_carts, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CartsInfoBean cartsInfoBean = mList.get(position);
        holder.title.setText(cartsInfoBean.getTitle());
        holder.skuName.setText("尺码:" + cartsInfoBean.getSku_name());
        holder.price1.setText("¥" + (float) (Math.round(cartsInfoBean.getPrice() * 100)) / 100);
        holder.price2.setText("/¥" + (float) (Math.round(cartsInfoBean.getStd_sale_price() * 100)) / 100);
        holder.count.setText(cartsInfoBean.getNum() + "");
        ViewUtils.loadImgToImgView(mActivity, holder.cartImage, cartsInfoBean.getPic_path());
        holder.cartImage.setOnClickListener(v -> {
            Intent intent = new Intent(mActivity, ProductDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("model_id", cartsInfoBean.getModel_id());
            intent.putExtras(bundle);
            mActivity.startActivity(intent);
            mActivity.finish();
        });
        holder.delete.setOnClickListener(v -> {
            if (cartsInfoBean.getNum() == 1) {
                new AlertDialog.Builder(mActivity)
                        .setTitle("删除商品")
                        .setMessage("您确定要删除吗？")
                        .setPositiveButton("确定", (dialog, which) -> {
                            dialog.dismiss();
                            mActivity.showIndeterminateProgressDialog(false);
                            mActivity.addSubscription(CartsModel.getInstance()
                                    .delete_carts(cartsInfoBean.getId() + "")
                                    .subscribeOn(Schedulers.io())
                                    .subscribe(new ServiceResponse<Response<CodeBean>>() {
                                        @Override
                                        public void onNext(Response<CodeBean> responseBody) {
                                            if (responseBody != null) {
                                                if (responseBody.isSuccessful()) {
                                                    mActivity.addHistory(cartsInfoBean);
                                                    mActivity.removeCartList(cartsInfoBean);
                                                } else {
                                                    JUtils.Toast(responseBody.body().getInfo());
                                                }
                                            } else {
                                                JUtils.Toast("操作未成功，请重新尝试");
                                            }
                                            mActivity.hideIndeterminateProgressDialog();
                                        }
                                    }));
                        })
                        .setNegativeButton("取消", (dialog, which) -> dialog.dismiss())
                        .show();
            } else {
                mActivity.showIndeterminateProgressDialog(false);
                mActivity.addSubscription(CartsModel.getInstance()
                        .minus_product_carts(cartsInfoBean.getId() + "")
                        .subscribeOn(Schedulers.io())
                        .subscribe(new ServiceResponse<Response<CodeBean>>() {
                            @Override
                            public void onNext(Response<CodeBean> responseBody) {
                                if (responseBody != null && responseBody.isSuccessful()) {
                                    CodeBean body = responseBody.body();
                                    if (body != null && body.getCode() == 0) {
                                        mActivity.setPriceText();
                                        cartsInfoBean.setNum(cartsInfoBean.getNum() - 1);
                                        holder.count.setText(cartsInfoBean.getNum() + "");
                                        notifyDataSetChanged();
                                    } else {
                                        JUtils.Toast(body != null ? body.getInfo() : "操作失败");
                                    }
                                } else {
                                    JUtils.Toast("操作未成功，请重新尝试");
                                }
                                mActivity.hideIndeterminateProgressDialog();
                            }
                        }));
            }
        });
        holder.add.setOnClickListener(v -> {
                    mActivity.showIndeterminateProgressDialog(false);
                    mActivity.addSubscription(CartsModel.getInstance()
                            .plus_product_carts(cartsInfoBean.getId() + "")
                            .subscribeOn(Schedulers.io())
                            .subscribe(new ServiceResponse<Response<CodeBean>>() {
                                @Override
                                public void onNext(Response<CodeBean> responseBody) {
                                    if (null != responseBody) {
                                        CodeBean body = responseBody.body();
                                        if (body != null && body.getCode() == 0) {
                                            mActivity.setPriceText();
                                            cartsInfoBean.setNum(cartsInfoBean.getNum() + 1);
                                            holder.count.setText(cartsInfoBean.getNum() + "");
                                            notifyDataSetChanged();
                                        } else {
                                            JUtils.Toast(body != null ? body.getInfo() : "操作失败");
                                        }
                                    } else {
                                        JUtils.Toast("操作未成功，请重新尝试");
                                    }
                                    mActivity.hideIndeterminateProgressDialog();
                                }
                            }));
                }
        );
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        int id = R.layout.item_carts;
        @Bind(R.id.cart_image)
        ImageView cartImage;
        @Bind(R.id.title)
        TextView title;
        @Bind(R.id.sku_name)
        TextView skuName;
        @Bind(R.id.price1)
        TextView price1;
        @Bind(R.id.price2)
        TextView price2;
        @Bind(R.id.delete)
        ImageView delete;
        @Bind(R.id.count)
        TextView count;
        @Bind(R.id.add)
        ImageView add;

        public ViewHolder(View itemView) {
            super(itemView);
            AutoUtils.auto(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
