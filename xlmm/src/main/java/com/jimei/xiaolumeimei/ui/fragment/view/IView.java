package com.jimei.xiaolumeimei.ui.fragment.view;

import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.jimei.xiaolumeimei.ui.fragment.presenter.IPresenter;


public interface IView {

    void create(LayoutInflater inflater, ViewGroup container);

    View getRootView();

    Toolbar getToolbar();

    void bindPresenter(IPresenter presenter);

    void bindEvent();

    void destroy();
}
