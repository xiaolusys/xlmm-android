package com.jimei.xiaolumeimei.ui.fragment.view;

import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import com.jimei.xiaolumeimei.ui.fragment.presenter.IPresenter;

public abstract class ViewImpl implements IView {
    protected View mRootView;
    protected IPresenter mPresenter;

    @Override
    public void create(LayoutInflater inflater, ViewGroup container) {
        mRootView = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, mRootView.getRootView());
    }

    @Override
    public View getRootView() { return mRootView; }

    @Override
    public Toolbar getToolbar() { return null; }

    @Override
    public void bindPresenter(IPresenter presenter) { mPresenter = presenter; }

    @Override
    public void bindEvent() {}

    public abstract int getLayoutId();

}
