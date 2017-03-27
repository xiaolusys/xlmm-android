package com.jimei.xiaolumeimei.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jimei.library.widget.loading.VaryViewHelperController;
import com.jimei.library.widget.scrolllayout.ScrollableHelper;

import java.lang.reflect.Field;

import butterknife.ButterKnife;

public abstract class BaseLazyFragment extends BaseFragment implements ScrollableHelper.ScrollableContainer {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        if (getContentViewId() != 0) {
            view = inflater.inflate(getContentViewId(), container, false);
            ButterKnife.bind(this, view);
        } else {
            throw new IllegalArgumentException("You must return a right contentView layout resource Id");
        }
        if (mVaryViewHelperController == null) {
            mVaryViewHelperController = new VaryViewHelperController(getLoadingView());
        }
        initViews();
        isInitView = true;
        lazyLoadData();
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public abstract View getLoadingView();

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
