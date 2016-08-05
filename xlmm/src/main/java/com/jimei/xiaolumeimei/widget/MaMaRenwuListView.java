package com.jimei.xiaolumeimei.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.jimei.xiaolumeimei.R;

/**
 * Created by itxuye(www.itxuye.com) on 2016/08/03.
 */
public class MaMaRenwuListView extends RelativeLayout {
  private ImageView brandtitleImage;
  private ImageView brandListImage;
  private ImageView checkImage;
  private TextView brandText;
  private Context mContext;

  public MaMaRenwuListView(Context context) {
    this(context, null);
    this.mContext = context;
  }

  public MaMaRenwuListView(Context context, AttributeSet attrs) {
    this(context, null, 0);
    this.mContext = context;
  }

  public MaMaRenwuListView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    this.mContext = context;
    init(context, attrs);
  }

  private void init(Context context, AttributeSet attrs) {
    View view = LayoutInflater.from(context).inflate(R.layout.item_mamarenwulist, this, true);
    brandtitleImage = (ImageView) view.findViewById(R.id.image_done);
    brandListImage = (ImageView) view.findViewById(R.id.image_nodone);
    checkImage = (ImageView) view.findViewById(R.id.image_check);
    brandText = (TextView) view.findViewById(R.id.tv_listshow);

    TypedArray a = null;
    if (attrs != null) {
      try {
        a = context.obtainStyledAttributes(attrs, R.styleable.MamalistView);
        brandText.setText(a.getString(R.styleable.MamalistView_tv_renwulist));
      } finally {

        if (a != null) {
          a.recycle();
        }
      }
    }
  }

  public void setImageVisiBle(boolean isShow) {
    if (isShow) {
      brandtitleImage.setVisibility(VISIBLE);
      brandListImage.setVisibility(INVISIBLE);
    } else {
      brandtitleImage.setVisibility(INVISIBLE);
      brandListImage.setVisibility(VISIBLE);
    }
  }

  public void setImageCheckVisible(boolean isShow) {
    if (isShow) {
      checkImage.setVisibility(INVISIBLE);
    } else {
      checkImage.setVisibility(VISIBLE);
    }
  }

  public void setBrandDesText(String text) {
    brandText.setText(text);
  }
}
