package com.jimei.xiaolumeimei.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.entities.CartsinfoBean;
import com.jimei.xiaolumeimei.glidemoudle.GlideRoundTransform;
import com.jimei.xiaolumeimei.model.CartsModel;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.squareup.okhttp.ResponseBody;
import com.zhy.autolayout.utils.AutoUtils;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye(www.itxuye.com) on 2016/01/19.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class CartsAdapetr extends RecyclerView.Adapter<CartsAdapetr.CartsVH> {
  public double total_price;
  CartsModel model = new CartsModel();
  private List<CartsinfoBean> mList;
  private Context mContext;

  public CartsAdapetr(Context mContext) {
    this.mContext = mContext;
    mList = new ArrayList<>();
  }

  @Override public CartsVH onCreateViewHolder(ViewGroup parent, int viewType) {

    View view;

    view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_carts, parent, false);
    AutoUtils.autoSize(view);
    return new CartsVH(view);
  }

  public void update(List<CartsinfoBean> list) {

    mList.addAll(list);
    notifyDataSetChanged();
  }

  public void updateWithClear(List<CartsinfoBean> list) {
    mList.clear();
    mList.addAll(list);
    notifyDataSetChanged();
  }

  @Override public void onBindViewHolder(final CartsVH holder, int position) {

    CartsinfoBean cartsinfoBean = mList.get(position);
    holder.title.setText(cartsinfoBean.getTitle());
    holder.skuName.setText("尺码:" + cartsinfoBean.getSkuName());
    //holder.color.setText(cartsinfoBean.get);
    holder.price1.setText("¥" + cartsinfoBean.getPrice() + "");
    holder.price2.setText("/¥" + cartsinfoBean.getStdSalePrice() + "");
    holder.count.setText(cartsinfoBean.getNum() + "");

    total_price =
        (cartsinfoBean.getTotalFee()) * (Double.parseDouble(cartsinfoBean.getNum()));
    String headImg = cartsinfoBean.getPicPath();

    String[] temp = headImg.split("http://image.xiaolu.so/");

    String head_img = "";

    if (temp.length > 1) {
      try {
        head_img = "http://image.xiaolu.so/"
            + URLEncoder.encode(temp[1], "utf-8")
            + "?imageMogr2/format/jpg/size-limit/30k/thumbnail/289/quality/90";
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }
    }

    Glide.with(mContext)
        .load(head_img)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .placeholder(R.drawable.parceholder)
        .centerCrop()
        .bitmapTransform(new GlideRoundTransform(mContext, 8))
        .into(holder.cartImage);

    holder.add.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {

        model.plus_product_carts(cartsinfoBean.getId())
            .subscribeOn(Schedulers.io())
            .subscribe(new ServiceResponse<ResponseBody>() {
              @Override public void onNext(ResponseBody responseBody) {
                super.onNext(responseBody);
                try {
                  String s = responseBody.string();
                  JUtils.Log("CartsAdapter", s);
                  model.getCartsList()
                      .subscribeOn(Schedulers.io())
                      .subscribe(new ServiceResponse<List<CartsinfoBean>>() {
                        @Override public void onNext(List<CartsinfoBean> list) {
                          super.onNext(list);
                          mList = list;
                          notifyItemChanged(position);
                        }
                      });

                  //holder.count.setText(cartsinfoBean.getNum() + "");
                } catch (IOException e) {
                  e.printStackTrace();
                }
              }
            });
      }
    });

    holder.delete.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (Integer.parseInt(cartsinfoBean.getNum()) > 1) {

          model.minus_product_carts(cartsinfoBean.getId())
              .subscribeOn(Schedulers.io())
              .subscribe(new ServiceResponse<ResponseBody>() {
                @Override public void onNext(ResponseBody responseBody) {
                  super.onNext(responseBody);

                  model.getCartsList()
                      .subscribeOn(Schedulers.io())
                      .subscribe(new ServiceResponse<List<CartsinfoBean>>() {
                        @Override public void onNext(List<CartsinfoBean> list) {
                          super.onNext(list);
                          mList = list;
                          notifyItemChanged(position);
                        }
                      });
                }
              });
        } else {
          new MaterialDialog.Builder(mContext).
              title("删除商品").
              content("您确定要删除吗？").
              positiveText("确定").
              negativeText("取消").
              callback(new MaterialDialog.ButtonCallback() {
                @Override public void onPositive(MaterialDialog dialog) {
                  model.delete_carts(cartsinfoBean.getId())
                      .subscribeOn(Schedulers.io())
                      .subscribe(new ServiceResponse<ResponseBody>() {
                        @Override public void onNext(ResponseBody responseBody) {
                          super.onNext(responseBody);
                          removeAt(position);
                        }
                      });
                  dialog.dismiss();
                }

                @Override public void onNegative(MaterialDialog dialog) {
                  dialog.dismiss();
                }
              }).show();
        }
      }
    });
  }

  @Override public int getItemCount() {
    return mList.size();
  }

  //删除某一项
  public void removeAt(int position) {
    mList.remove(position);
    notifyItemRemoved(position);
    notifyItemRangeChanged(position, mList.size());
  }

  static class CartsVH extends RecyclerView.ViewHolder {
    int id = R.layout.item_carts;
    @Bind(R.id.cart_image) ImageView cartImage;
    @Bind(R.id.title) TextView title;
    @Bind(R.id.sku_name) TextView skuName;
    //@Bind(R.id.color) TextView color;
    @Bind(R.id.price1) TextView price1;
    @Bind(R.id.price2) TextView price2;
    @Bind(R.id.ll) LinearLayout ll;
    @Bind(R.id.delete) TextView delete;
    @Bind(R.id.count) TextView count;
    @Bind(R.id.add) TextView add;

    public CartsVH(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
