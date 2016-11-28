package com.jimei.library.widget.loading;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jimei.library.R;

public class VaryViewHelperController {

    private IVaryViewHelper helper;

    public VaryViewHelperController(View view) {
        this(new VaryViewHelper(view));
    }

    private VaryViewHelperController(IVaryViewHelper helper) {
        this.helper = helper;
    }

    public void showNetworkError(View.OnClickListener onClickListener) {
        View layout = helper.inflate(R.layout.pager_error);
        Button againBtn = (Button) layout.findViewById(R.id.pager_error_loadingAgain);
        if (null != onClickListener) {
            againBtn.setOnClickListener(onClickListener);
        }
        helper.showLayout(layout);
    }

    public void showEmpty(String emptyMsg) {
        View layout = helper.inflate(R.layout.page_no_data);
        TextView textView = (TextView) layout.findViewById(R.id.tv_no_data);
        if (!TextUtils.isEmpty(emptyMsg)) {
            textView.setText(emptyMsg);
        }
        helper.showLayout(layout);
    }

    public void showLoading() {
        View layout = helper.inflate(R.layout.pager_loading);
        helper.showLayout(layout);
    }

    public void restore() {
        helper.restoreView();
    }
}
