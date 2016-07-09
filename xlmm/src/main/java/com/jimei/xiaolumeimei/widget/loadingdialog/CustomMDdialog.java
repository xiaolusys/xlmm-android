package com.jimei.xiaolumeimei.widget.loadingdialog;

import android.content.Context;
import android.util.TypedValue;
import android.view.ViewGroup;
import com.afollestad.materialdialogs.MaterialDialog;
import com.jimei.xiaolumeimei.R;

/**
 * Created by itxuye on 2016/7/9.
 */
public class CustomMDdialog extends MaterialDialog {

  public CustomMDdialog(Context context) {
    super(new MaterialDialog.Builder(context).customView(R.layout.dialog_view,false));
    ViewGroup.LayoutParams params = getWindow().getAttributes();
    params.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200,
        context.getResources().getDisplayMetrics());
    params.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200,
        context.getResources().getDisplayMetrics());

    getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    //setCancelable(false);
    this.show();
  }
}
