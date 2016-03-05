package com.jimei.xiaolumeimei.base;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Author  : itxuye(itxuye@gmail.com)|(http://itxuye.com)
 * <p/>
 * 通用的ViewHolder,使用SparseArray<View>优化
 */
public class CommonViewHolder {
  private SparseArray<View> mViews;
  private Context mContext;
  private int position;
  private View mConvertView;

  public CommonViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
    mViews = new SparseArray<>();
    this.mContext = context;
    this.position = position;
    this.mConvertView = LayoutInflater.from(mContext).inflate(layoutId, parent, false);
    mConvertView.setTag(this);
  }

  public static CommonViewHolder get(Context context, View convertView, ViewGroup parent,
      int layoutId, int position) {
    if (convertView == null) {
      return new CommonViewHolder(context, parent, layoutId, position);
    } else {
      CommonViewHolder holder = (CommonViewHolder) convertView.getTag();
      holder.position = position;
      return holder;
    }
  }

  /*
  通过viewId获取控件
   */
  public <T extends View> T findViewById(int viewId) {
    View view = mViews.get(viewId);
    if (view == null) {
      view = mConvertView.findViewById(viewId);
      mViews.put(viewId, view);
    }
    return (T) view;
  }

  public View getConvertView() {
    return mConvertView;
  }

  /*
      TextView
   */
  public CommonViewHolder setText(int viewId, String value) {
    TextView view = findViewById(viewId);
    view.setText(value);
    return this;
  }

  public CommonViewHolder setVisiBility(int viewId) {
    View view = findViewById(viewId);
    view.setVisibility(View.INVISIBLE);

    return this;
  }

  public CommonViewHolder setBackground(int viewId, int resId) {
    View view = findViewById(viewId);
    view.setBackgroundResource(resId);
    return this;
  }

  /*
  Picasso加载图片，网络访问推荐okHttp
   */
  public CommonViewHolder setImageFromUrl(Context context, int viewId, String url) {
    ImageView imageView = findViewById(viewId);
    //@formatter:off
        Glide.with(mContext)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .into(imageView);


        //@formatter:on
    return this;
  }

  /*
      通过drawableI加载图片
   */
  public CommonViewHolder setImageFromUrl(int viewId, int drawableId) {
    ImageView imageView = findViewById(viewId);
    imageView.setImageResource(drawableId);

    return this;
  }


  //public CommonViewHolder setImageFromBitmap(int viewId, Bitmap bitmap) {
  //  SubsamplingScaleImageView imageView = findViewById(viewId);
  //  imageView.setImage(ImageSource.bitmap(bitmap));
  //
  //  return this;
  //}

  public CommonViewHolder setClickListener(int viewId, View.OnClickListener listener) {
    View view = findViewById(viewId);
    view.setOnClickListener(listener);
    return this;
  }
}
