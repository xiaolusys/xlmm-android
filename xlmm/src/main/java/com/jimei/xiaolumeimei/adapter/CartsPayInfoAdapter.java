package com.jimei.xiaolumeimei.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.CommonAbsListviewBaseAdapter;
import com.jimei.xiaolumeimei.base.CommonViewHolder;
import com.jimei.xiaolumeimei.entities.CartsPayinfoBean;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by itxuye(www.itxuye.com) on 2016/01/19.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class CartsPayInfoAdapter
    extends CommonAbsListviewBaseAdapter<CartsPayinfoBean.CartListEntity> {
  public CartsPayInfoAdapter(Context context,
      List<CartsPayinfoBean.CartListEntity> list) {
    super(context, list);
    mList = new ArrayList<>();
  }

  public void update(List<CartsPayinfoBean.CartListEntity> list) {
    mList.addAll(list);
    notifyDataSetChanged();
  }

  public void updateWithClear(List<CartsPayinfoBean.CartListEntity> list) {
    mList.clear();
    mList.addAll(list);
    notifyDataSetChanged();
  }

  @Override public View getView(int position, View convertView, ViewGroup parent) {

    CommonViewHolder holder =
        CommonViewHolder.get(mContext, convertView, parent, R.layout.item_carts_pay,
            position);

    CartsPayinfoBean.CartListEntity cartListEntity = mList.get(position);

    String picPath = cartListEntity.getPicPath();
    if (picPath.startsWith("https://mmbiz.qlogo.cn")) {
      holder.setImageFromUrl(mContext, R.id.cart_image, picPath);
    } else {
      String[] temp = picPath.split("http://image.xiaolu.so/");
      String head_img = "";
      if (temp.length > 1) {
        try {
          head_img = "http://image.xiaolu.so/"
              + URLEncoder.encode(temp[1], "utf-8")
              + "?imageMogr2/format/jpg/size-limit/30k/thumbnail/289/quality/90";
          holder.setImageFromUrl(mContext, R.id.cart_image, head_img);
        } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
        }
      }
    }
    if (cartListEntity.getTitle().length() <= 9) {
      holder.setText(R.id.title, cartListEntity.getTitle());
    } else {
      holder.setText(R.id.title, cartListEntity.getTitle().substring(0, 9) + "...");
    }

    holder.setText(R.id.tx_good_size, "尺码:" + cartListEntity.getSkuName());

    holder.setText(R.id.tv_num, "x" + cartListEntity.getNum() + "");
    holder.setText(R.id.price_tv, "¥" + cartListEntity.getPrice());
    return holder.getConvertView();
  }
}
