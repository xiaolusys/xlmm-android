package com.jimei.library.widget.banner.SliderTypes;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.io.File;

public abstract class BaseSliderView {

  protected Context mContext;
  protected OnSliderClickListener mOnSliderClickListener;
  private Bundle mBundle;
  /**
   * Error place holder image.
   */
  private int mErrorPlaceHolderRes;
  /**
   * Empty imageView placeholder.
   */
  private int mEmptyPlaceHolderRes;
  private String mUrl;
  private File mFile;
  private int mRes;
  private boolean mErrorDisappear;

  private ImageLoadListener mLoadListener;

  private String mDescription;

  private Picasso mPicasso;

  /**
   * Scale type of the image.
   */
  private ScaleType mScaleType = ScaleType.Fit;

  protected BaseSliderView(Context context) {
    mContext = context;
  }

  /**
   * the placeholder image when loading image from url or file.
   *
   * @param resId Image resource id
   */
  public BaseSliderView empty(int resId) {
    mEmptyPlaceHolderRes = resId;
    return this;
  }

  /**
   * determine whether remove the image which failed to download or load from file
   */
  public BaseSliderView errorDisappear(boolean disappear) {
    mErrorDisappear = disappear;
    return this;
  }

  /**
   * if you set errorDisappear false, this will set a error placeholder image.
   *
   * @param resId image resource id
   */
  public BaseSliderView error(int resId) {
    mErrorPlaceHolderRes = resId;
    return this;
  }

  /**
   * the description of a slider image.
   */
  public BaseSliderView description(String description) {
    mDescription = description;
    return this;
  }

  /**
   * set a url as a image that preparing to load
   */
  public BaseSliderView image(String url) {
    if (mFile != null || mRes != 0) {
      throw new IllegalStateException(
          "Call multi image function," + "you only have permission to call it once");
    }
    mUrl = url;
    return this;
  }

  /**
   * set a file as a image that will to load
   */
  public BaseSliderView image(File file) {
    if (mUrl != null || mRes != 0) {
      throw new IllegalStateException(
          "Call multi image function," + "you only have permission to call it once");
    }
    mFile = file;
    return this;
  }

  public BaseSliderView image(int res) {
    if (mUrl != null || mFile != null) {
      throw new IllegalStateException(
          "Call multi image function," + "you only have permission to call it once");
    }
    mRes = res;
    return this;
  }

  /**
   * lets users add a bundle of additional information
   */
  public BaseSliderView bundle(Bundle bundle) {
    mBundle = bundle;
    return this;
  }

  public String getUrl() {
    return mUrl;
  }

  public boolean isErrorDisappear() {
    return mErrorDisappear;
  }

  public int getEmpty() {
    return mEmptyPlaceHolderRes;
  }

  public int getError() {
    return mErrorPlaceHolderRes;
  }

  public String getDescription() {
    return mDescription;
  }

  public Context getContext() {
    return mContext;
  }

  /**
   * set a slider image click listener
   */
  public BaseSliderView setOnSliderClickListener(OnSliderClickListener l) {
    mOnSliderClickListener = l;
    return this;
  }

  /**
   * When you want to implement your own slider view, please call this method in the end
   * in `getView()` method
   *
   * @param v the whole view
   * @param targetImageView where to place image
   */
  protected void bindEventAndShow(final View v, ImageView targetImageView) {
    final BaseSliderView me = this;

    v.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (mOnSliderClickListener != null) {
          mOnSliderClickListener.onSliderClick(me);
        }
      }
    });

    if (targetImageView == null) return;


    if (mLoadListener != null) {
      mLoadListener.onStart(me);
    }
    Picasso p = (mPicasso != null) ? mPicasso : Picasso.with(mContext);
    RequestCreator rq;
    DrawableTypeRequest<String> load = Glide.with(mContext).load(mUrl);
    if (mUrl != null) {
      rq = p.load(mUrl);
    } else if (mFile != null) {
      rq = p.load(mFile);
    } else if (mRes != 0) {
      rq = p.load(mRes);
    } else {
      return;
    }

    if (rq == null) {
      return;
    }

//    try {
//      Bitmap response = rq.get();
//      int maxHeight = dp2px(mContext, 300);
//      if (response != null) {
//        int height =
//                (int) ((float) v.getWidth() / response.getWidth()
//                        * response.getHeight());
//        if (height > maxHeight) height = maxHeight;
//        RelativeLayout.LayoutParams layoutParams =
//                new RelativeLayout.LayoutParams(
//                        RelativeLayout.LayoutParams.MATCH_PARENT, height);
////        layoutParams.setMargins(0, dp2px(mContext, 10), 0, 0);
//        targetImageView.setLayoutParams(layoutParams);
//      }
//    } catch (Exception e) {
//      e.printStackTrace();
//    }

    if (getEmpty() != 0) {
      rq.placeholder(getEmpty());
    }

    if (getError() != 0) {
      rq.error(getError());
    }
    switch (mScaleType) {
      case Fit:
        rq.fit();
        break;
      case CenterCrop:
        rq.fit().centerCrop();
        break;
      case CenterInside:
        rq.fit().centerInside();
        break;
    }
    rq.into(targetImageView, new Callback() {
      @Override public void onSuccess() {

        if (v.findViewById(com.jimei.library.R.id.loading_bar) != null) {
          v.findViewById(com.jimei.library.R.id.loading_bar).setVisibility(View.INVISIBLE);
        }
      }

      @Override public void onError() {
        if (mLoadListener != null) {
          mLoadListener.onEnd(false, me);
        }
        if (v.findViewById(com.jimei.library.R.id.loading_bar) != null) {
          v.findViewById(com.jimei.library.R.id.loading_bar).setVisibility(View.INVISIBLE);
        }
      }
    });

  }

  public ScaleType getScaleType() {
    return mScaleType;
  }

  public BaseSliderView setScaleType(ScaleType type) {
    mScaleType = type;
    return this;
  }

  /**
   * the extended class have to implement getView(), which is called by the adapter,
   * every extended class response to render their own view.
   */
  public abstract View getView();

  /**
   * set a listener to get a message , if load error.
   */
  public void setOnImageLoadListener(ImageLoadListener l) {
    mLoadListener = l;
  }

  /**
   * when you have some extra information, please put it in this bundle.
   */
  public Bundle getBundle() {
    return mBundle;
  }

  /**
   * Get the last instance set via setPicasso(), or null if no user provided instance was
   * set
   *
   * @return The current user-provided Picasso instance, or null if none
   */
  public Picasso getPicasso() {
    return mPicasso;
  }

  public void setPicasso(Picasso picasso) {
    mPicasso = picasso;
  }



  public enum ScaleType {
    CenterCrop, CenterInside, Fit, FitCenterCrop
  }

  public interface OnSliderClickListener {
    void onSliderClick(BaseSliderView slider);
  }

  public interface ImageLoadListener {
    void onStart(BaseSliderView target);

    void onEnd(boolean result, BaseSliderView target);
  }

  public static int dp2px(Context context, int dp) {
    float scale = context.getResources().getDisplayMetrics().density;
    return (int) (dp * scale + 0.5f);
  }
}
