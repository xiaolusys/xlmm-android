package com.jimei.xiaolumeimei.adapter;

import android.content.Context;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.afollestad.materialdialogs.MaterialDialog;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.entities.NinePicBean;
import com.jimei.xiaolumeimei.rx.RXDownLoadImage;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.ImagePagerActivity;
import com.jimei.xiaolumeimei.widget.ninepicimagview.MultiImageView;
import com.jude.utils.JUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by itxuye(www.itxuye.com) on 2016/02/29.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class NinePicAdapter extends BaseAdapter {
  private Context mcontext;
  private List<NinePicBean> mlist = new ArrayList<>();

  public NinePicAdapter(Context mcontext) {
    this.mcontext = mcontext;
  }

  public List<NinePicBean> getDatas() {
    return mlist;
  }

  public void setDatas(List<NinePicBean> datas) {
    if (datas != null) {
      this.mlist = datas;
    }
  }

  @Override public int getCount() {
    return mlist == null ? 0 : mlist.size();
  }

  @Override public Object getItem(int position) {
    return mlist.get(position);
  }

  @Override public long getItemId(int position) {
    return position;
  }

  @Override public View getView(int position, View convertView, ViewGroup parent) {

    ViewHolder holder;
    if (convertView == null) {
      holder = new ViewHolder();
      convertView = View.inflate(mcontext, R.layout.item_ninepic, null);

      holder.headIv = (ImageView) convertView.findViewById(R.id.headIv);
      holder.urlTipTv = (TextView) convertView.findViewById(R.id.urlTipTv);
      holder.contentTv = (TextView) convertView.findViewById(R.id.contentTv);
      holder.save = (Button) convertView.findViewById(R.id.save);

      ViewStub linkOrImgViewStub =
          (ViewStub) convertView.findViewById(R.id.imageviewstub);

      linkOrImgViewStub.setLayoutResource(R.layout.viewstub_imgbody);
      linkOrImgViewStub.inflate();
      MultiImageView multiImageView =
          (MultiImageView) convertView.findViewById(R.id.multiImagView);
      if (multiImageView != null) {
        holder.multiImageView = multiImageView;
      }
      convertView.setTag(holder);
    } else {
      holder = (ViewHolder) convertView.getTag();
    }

    holder.contentTv.setText(mlist.get(position).getDescription());
    holder.urlTipTv.setText(mlist.get(position).getStartTime().replace("T", " "));

    //final List<String> photos = circleItem.getPhotos();
    List<String> picArry = mlist.get(position).getPicArry();

    //for (String pic : picArry) {
    //  String pica = pic + "?imageMogr2/thumbnail/578/format/jpg/quality/90";
    //  imageLists.add(pica);
    //}

    if (picArry != null && picArry.size() > 0) {
      holder.multiImageView.setVisibility(View.VISIBLE);
      holder.multiImageView.setList(picArry);
      holder.multiImageView.setOnItemClickListener(
          new MultiImageView.OnItemClickListener() {
            @Override public void onItemClick(View view, int position) {
              // 因为单张图片时，图片实际大小是自适应的，imageLoader缓存时是按测量尺寸缓存的
              //ImagePagerActivity.imageSize =
              //    new ImageSize(view.getMeasuredWidth(), view.getMeasuredHeight());
              ImagePagerActivity.startImagePagerActivity(mcontext, picArry, position);
            }
          });
    } else {
      holder.multiImageView.setVisibility(View.GONE);
    }

    holder.save.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {

        assert picArry != null;
        for (int i = 0; i < picArry.size(); i++) {

          saveImageToGallery(picArry.get(i), picArry.get(i));
        }

        new MaterialDialog.Builder(mcontext).
            content("图片已经保存，前往分享吧").
            positiveText("OK").
            callback(new MaterialDialog.ButtonCallback() {
              @Override public void onPositive(MaterialDialog dialog) {
                super.onPositive(dialog);
                dialog.dismiss();
              }
            }).show();
      }
    });

    return convertView;
  }

  private void saveImageToGallery(String url, String mImageTitle) {
    // @formatter:off
      RXDownLoadImage.saveImageAndGetPathObservable(mcontext, url,
            mImageTitle)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(uri -> {
                File appDir = new File(Environment.getExternalStorageDirectory(), "XlMMImage");
                //String msg = String.format(""),
                //        appDir.getAbsolutePath());
                //ToastUtils.showShort(msg);
            }, error -> JUtils.Toast(error.getMessage() + "\n再试试..."));
        // @formatter:on
    //addSubscription(s);
  }

  class ViewHolder {
    MultiImageView multiImageView;
    ImageView headIv;
    TextView urlTipTv;
    TextView contentTv;
    Button save;
  }
}
