package com.jimei.xiaolumeimei.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jimei.library.widget.loading.VaryViewHelperController;

/**
 * Created by wisdom on 16/9/8.
 */

public abstract class BaseBindingFragment<T extends ViewDataBinding> extends BaseFragment {

    protected T b;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (getContentViewId() != 0) {
            b = DataBindingUtil.inflate(inflater, getContentViewId(), container, false);
        } else {
            throw new IllegalArgumentException("You must return a right contentView layout resource Id");
        }
        if (mVaryViewHelperController == null) {
            mVaryViewHelperController = new VaryViewHelperController(getLoadingView());
        }
        initViews();
        isInitView = true;
        lazyLoadData();
        setListener();
        return b.getRoot();
    }

    public void setListener() {

    }

    public abstract View getLoadingView();
}