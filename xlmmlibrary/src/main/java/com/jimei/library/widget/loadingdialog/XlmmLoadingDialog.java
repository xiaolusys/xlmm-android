package com.jimei.library.widget.loadingdialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jimei.library.R;

/**
 * Created by itxuye on 2016/7/9.
 */
public class XlmmLoadingDialog {
    public enum Style {
        SPIN_INDETERMINATE
    }

    // To avoid redundant APIs, all HUD functions will be forward to
    // a custom dialog
    private ProgressDialog mProgressDialog;
    private float mDimAmount;
    private int mWindowColor;
    private float mCornerRadius;
    private boolean mCancellable;
    private Context mContext;

    private int mAnimateSpeed;
    private String mLabel;
    private String mDetailsLabel;
    private boolean mIsAutoDismiss;

    public XlmmLoadingDialog(Context context) {
        mContext = context;
        mProgressDialog = new ProgressDialog(context);
        mDimAmount = 0.1f;
        //noinspection deprecation
        mWindowColor = context.getResources().getColor(com.jimei.library.R.color.transparent);
        mAnimateSpeed = 1;
        mCornerRadius = 10;
        mIsAutoDismiss = true;

        setStyle(Style.SPIN_INDETERMINATE);
    }

    public static XlmmLoadingDialog create(Context context) {
        return new XlmmLoadingDialog(context);
    }

    public XlmmLoadingDialog setStyle(Style style) {
        View view = null;
        switch (style) {
            case SPIN_INDETERMINATE:
                view = new SpinView(mContext);
                break;
        }
        mProgressDialog.setView(view);
        return this;
    }

    public XlmmLoadingDialog setDimAmount(float dimAmount) {
        if (dimAmount >= 0 && dimAmount <= 1) {
            mDimAmount = dimAmount;
        }
        return this;
    }

    public XlmmLoadingDialog setWindowColor(int color) {
        mWindowColor = color;
        return this;
    }

    public XlmmLoadingDialog setCornerRadius(float radius) {
        mCornerRadius = radius;
        return this;
    }

    public XlmmLoadingDialog setAnimationSpeed(int scale) {
        mAnimateSpeed = scale;
        return this;
    }

    public XlmmLoadingDialog setLabel(String label) {
        mLabel = label;
        return this;
    }

    public XlmmLoadingDialog setCancellable(boolean isCancellable) {
        mCancellable = isCancellable;
        return this;
    }

    public XlmmLoadingDialog setAutoDismiss(boolean isAutoDismiss) {
        mIsAutoDismiss = isAutoDismiss;
        return this;
    }

    public XlmmLoadingDialog show() {
        if (!isShowing()) {
            mProgressDialog.show();
        }
        return this;
    }

    public boolean isShowing() {
        return mProgressDialog != null && mProgressDialog.isShowing();
    }

    public void dismiss() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    private class ProgressDialog extends Dialog {

        private Determinate mDeterminateView;
        private Indeterminate mIndeterminateView;
        private View mView;

        public ProgressDialog(Context context) {
            super(context);
            setOwnerActivity((Activity) context);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(com.jimei.library.R.layout.dialog_view);

            Window window = getWindow();
            window.setBackgroundDrawable(new ColorDrawable(0));
            window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.dimAmount = mDimAmount;
            window.setAttributes(layoutParams);

            setCanceledOnTouchOutside(true);
            setCancelable(mCancellable);

            initViews();
        }

        private void initViews() {
            BackgroundLayout background = (BackgroundLayout) findViewById(com.jimei.library.R.id.background);
            background.setBaseColor(mWindowColor);
            background.setCornerRadius(mCornerRadius);

            FrameLayout containerFrame = (FrameLayout) findViewById(com.jimei.library.R.id.container);
            int wrapParam = ViewGroup.LayoutParams.WRAP_CONTENT;
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(wrapParam, wrapParam);
            containerFrame.addView(mView, params);

            if (mIndeterminateView != null) {
                mIndeterminateView.setAnimationSpeed(mAnimateSpeed);
            }

            if (mLabel != null) {
                TextView labelText = (TextView) findViewById(R.id.label);
                labelText.setText(mLabel);
                labelText.setVisibility(View.VISIBLE);
            }
            if (mDetailsLabel != null) {
                TextView detailsText = (TextView) findViewById(R.id.details_label);
                detailsText.setText(mDetailsLabel);
                detailsText.setVisibility(View.VISIBLE);
            }
        }


        public void setView(View view) {
            if (view != null) {
                if (view instanceof Determinate) {
                    mDeterminateView = (Determinate) view;
                }
                if (view instanceof Indeterminate) {
                    mIndeterminateView = (Indeterminate) view;
                }
                mView = view;
            }
        }
    }
}
