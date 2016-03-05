package com.jimei.xiaolumeimei.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.TextView;
/**
 * Created by wulei on 2016/3/3.
 */
public class RotateTextView extends TextView{
  public RotateTextView(Context context) {
    super(context);
  }

  public RotateTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    //倾斜度45,上下左右居中
    canvas.rotate(20, getMeasuredWidth()/3, getMeasuredHeight()/2);
    super.onDraw(canvas);
  }
}
