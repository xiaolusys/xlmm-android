package com.jimei.xiaolumeimei.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.preference.CheckBoxPreference;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.jimei.xiaolumeimei.R;

/**
 * Created by itxuye on 2016/8/6.
 */
public class SwitchPreferenceCompat extends CheckBoxPreference {

  private Context context;

  public SwitchPreferenceCompat(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    this.context = context;
    init();
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public SwitchPreferenceCompat(Context context, AttributeSet attrs, int defStyleAttr,
      int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    this.context = context;
    init();
  }

  public SwitchPreferenceCompat(Context context, AttributeSet attrs) {
    super(context, attrs);
    this.context = context;
    init();
  }

  public SwitchPreferenceCompat(Context context) {
    super(context);
    this.context = context;
    init();
  }

  private void init() {
    setWidgetLayoutResource(R.layout.preference_switch_layout);
  }

  @Override protected View onCreateView(ViewGroup parent) {
    View view = super.onCreateView(parent);
    try {
      // 行高
      //view.getLayoutParams().height = (int) DisplayUtils.dip2px(context, 44);

      LinearLayout linearLayout = (LinearLayout) view;
      linearLayout.setPadding(20, 0, 0, 0);

      RelativeLayout.LayoutParams layoutParams =
          new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
              ViewGroup.LayoutParams.WRAP_CONTENT);
      layoutParams.setMargins(30, 0, 0, 16);//4个参数按顺序分别是左上右下

      ViewGroup viewGroup = (ViewGroup) view;
      ViewGroup childViewGroup = (ViewGroup) viewGroup.getChildAt(1);

      TextView titleView = (TextView) childViewGroup.getChildAt(0);

      titleView.setLayoutParams(layoutParams);
      titleView.setTextSize(15);
      titleView.setTextColor(Color.BLACK);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return view;
  }
}
