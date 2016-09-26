package com.jimei.xiaolumeimei.base;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by itxuye on 2016/7/27.
 * <p>
 * databinding 的 viewHolder基类
 */
public class BaseRevHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {
    private T mBinding;

    public BaseRevHolder(T binding) {
        super(binding.getRoot());
        mBinding = binding;
        AutoUtils.auto(binding.getRoot());
    }

    public T getBinding() {
        return mBinding;
    }
}
