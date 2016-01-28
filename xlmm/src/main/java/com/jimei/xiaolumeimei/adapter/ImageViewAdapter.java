package com.jimei.xiaolumeimei.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.CommonAbsListviewBaseAdapter;
import com.jimei.xiaolumeimei.base.CommonViewHolder;
import com.jude.utils.JUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;

/**
 * Created by itxuye(www.itxuye.com) on 2016/01/27.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class ImageViewAdapter extends CommonAbsListviewBaseAdapter<String> {

  private CommonViewHolder holder;

  public ImageViewAdapter(Context context, List<String> list) {
    super(context, list);
    mList = new ArrayList<>();
  }

  public void update(List<String> list) {
    mList.clear();
    mList.addAll(list);
    notifyDataSetChanged();
  }

  @Override public View getView(int position, View convertView, ViewGroup parent) {

    holder =
        CommonViewHolder.get(mContext, convertView, parent, R.layout.item_longimagview,
            position);

    String s = mList.get(position);

    JUtils.Log("ImageViewAdapter1", "" + position + " " + holder);
    JUtils.Log("ImageViewAdapter1", s);
    if (s.startsWith("https://mmbiz.qlogo.cn")) {
      OkHttpUtils.get().url(s).build().execute(new BitmapCallback() {
        @Override public void onError(Call call, Exception e) {

        }

        @Override public void onResponse(Bitmap response) {
          //.setImage(ImageSource.bitmap(response));
          JUtils.Log("ImageViewAdapter", response.toString() + " " + holder);
          holder.setImageFromBitmap(R.id.longimageview, response);
        }
      });
    } else {
      String[] temp = s.split("http://image.xiaolu.so/");
      String head_img;
      if (temp.length > 1) {
        try {
          head_img = "http://image.xiaolu.so/" + URLEncoder.encode(temp[1], "utf-8");

          OkHttpUtils.get().url(head_img).build().execute(new BitmapCallback() {
            @Override public void onError(Call call, Exception e) {

            }

            @Override public void onResponse(Bitmap response) {
              //.setImage(ImageSource.bitmap(response));
              JUtils.Log("ImageViewAdapter1", response.toString());
              holder.setImageFromBitmap(R.id.longimageview, response);
            }
          });
        } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
        }
      }
    }

    return holder.getConvertView();
  }
}
