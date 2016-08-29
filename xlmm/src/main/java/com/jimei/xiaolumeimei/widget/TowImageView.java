package com.jimei.xiaolumeimei.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.utils.ViewUtils;
import java.util.List;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by itxuye on 2016/8/29.
 */
public class TowImageView extends LinearLayout {
  private ImageView childImage, ladyImage;
  private Context context;

  public TowImageView(Context context) {
    this(context, null);
  }

  public TowImageView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public TowImageView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    this.context = context;
    LayoutInflater.from(context).inflate(R.layout.towimageview, this, true);
    ladyImage = (ImageView) findViewById(R.id.lady_img);
    childImage = (ImageView) findViewById(R.id.child_img);
    setImageViewResize(ladyImage);
    setImageViewResize(childImage);
  }

  public void setImageBitmaps(List<String> urls) {
    ViewUtils.loadImageWithOkhttpReturnBitmaps(context, urls)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action1<List<Bitmap>>() {
          @Override public void call(List<Bitmap> bitmaps) {
            ladyImage.setImageBitmap(bitmaps.get(0));
            childImage.setImageBitmap(bitmaps.get(1));
          }
        }, new Action1<Throwable>() {
          @Override public void call(Throwable throwable) {
            throwable.printStackTrace();
          }
        });
  }

  public void setImageViewResize(ImageView imageView) {
    imageView.setAdjustViewBounds(true);

    Resources resources = getResources();
    DisplayMetrics dm = resources.getDisplayMetrics();
    int screenWidth = dm.widthPixels / 2;

    //int screenWidth = DisplayUtils.getScreenW(context) / 2;
    LinearLayout.LayoutParams lp =
        new LinearLayout.LayoutParams(screenWidth, LinearLayout.LayoutParams.WRAP_CONTENT);
    //                                                        lp.width = screenWidth;
    //                                                        lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
    imageView.setLayoutParams(lp);

    imageView.setMaxWidth(screenWidth);
    imageView.setMaxHeight(screenWidth * 5);
  }

  public void setChildImageListener(ChildImageListener listener) {
    if (listener != null) {
      childImage.setOnClickListener(new OnClickListener() {
        @Override public void onClick(View view) {
          listener.onChildClick();
        }
      });
    }
  }

  public void setLadyImageListener(LadyImageListener listener) {
    if (listener != null) {
      ladyImage.setOnClickListener(new OnClickListener() {
        @Override public void onClick(View view) {
          listener.onLadyClick();
        }
      });
    }
  }

  public interface ChildImageListener{
    void onChildClick();
  }

  public interface LadyImageListener{
    void onLadyClick();
  }
}
