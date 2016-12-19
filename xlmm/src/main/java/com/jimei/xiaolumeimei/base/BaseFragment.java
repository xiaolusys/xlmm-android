package com.jimei.xiaolumeimei.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.jimei.library.utils.JUtils;
import com.jimei.library.widget.loading.VaryViewHelperController;
import com.jimei.library.widget.loadingdialog.XlmmLoadingDialog;
import com.umeng.analytics.MobclickAgent;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by wisdom on 16/11/1.
 */

public abstract class BaseFragment extends Fragment {
    public Activity mActivity;
    private CompositeSubscription mCompositeSubscription;
    private boolean mIsHidden = true;
    private static final String FRAGMENT_STORE = "STORE";
    private boolean isVisible = false;
    public boolean isInitView = false;
    private boolean isFirstLoad = true;
    private XlmmLoadingDialog loadingdialog;
    public VaryViewHelperController mVaryViewHelperController;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mIsHidden = savedInstanceState.getBoolean(FRAGMENT_STORE);
        }
        if (restoreInstanceState()) {
            processRestoreInstanceState(savedInstanceState);
        }
    }

    private void processRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if (isSupportHidden()) {
                ft.hide(this);
            } else {
                ft.show(this);
            }
            ft.commit();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            isVisible = true;
            lazyLoadData();
        } else {
            isVisible = false;
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    public boolean isSupportHidden() {
        return mIsHidden;
    }

    protected boolean restoreInstanceState() {
        return true;
    }

    public void lazyLoadData() {
        if (!isFirstLoad || !isVisible || !isInitView) {
            return;
        }
        refreshView();
        showNetworkError();
        isFirstLoad = false;
    }

    public abstract void initData();

    protected abstract void initViews();

    protected abstract int getContentViewId();

    public void addSubscription(Subscription s) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        this.mCompositeSubscription.add(s);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(FRAGMENT_STORE, isHidden());
    }


    @Override
    public void onDestroyView() {
        try {
            if (this.mCompositeSubscription != null) {
                this.mCompositeSubscription.unsubscribe();
                this.mCompositeSubscription = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroyView();
    }

    public void showIndeterminateProgressDialog(boolean horizontal) {
        if (loadingdialog == null) {
            loadingdialog = XlmmLoadingDialog.create(mActivity)
                    .setStyle(XlmmLoadingDialog.Style.SPIN_INDETERMINATE)
                    .setCancellable(!horizontal)
                    .show();
        }
    }

    public void hideIndeterminateProgressDialog() {
        try {
            if (loadingdialog != null) {
                loadingdialog.dismiss();
                loadingdialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getTitle() {
        if (getArguments() != null) {
            return getArguments().getString("title");
        }
        return "";
    }

    public void showNetworkError() {
        if (!JUtils.isNetWorkAvilable()) {
            hideIndeterminateProgressDialog();
            if (mVaryViewHelperController == null) {
                throw new IllegalStateException("no ViewHelperController");
            }
            mVaryViewHelperController.showNetworkError(view -> {
                refreshView();
                showNetworkError();
            });
        } else {
            showIndeterminateProgressDialog(true);
            initData();
        }
    }

    public void refreshView() {
        if (mVaryViewHelperController == null) {
            throw new IllegalStateException("no ViewHelperController");
        }
        mVaryViewHelperController.restore();
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(this.getClass().getSimpleName());
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
    }
}
