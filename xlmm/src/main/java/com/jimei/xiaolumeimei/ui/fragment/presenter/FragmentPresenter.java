package com.jimei.xiaolumeimei.ui.fragment.presenter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.jimei.xiaolumeimei.base.BaseLazyFragment;
import com.jimei.xiaolumeimei.ui.fragment.view.GenericHelper;
import com.jimei.xiaolumeimei.ui.fragment.view.IView;

public class FragmentPresenter<T extends IView> extends BaseLazyFragment
    implements IPresenter<T> {

  protected T mView;

  @Override protected View initViews(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = null;
    try {
      mView = getViewClass().newInstance();
      mView.create(inflater, container);
      mView.bindPresenter(this);
      view = mView.getRootView();
      initData();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return view;
  }

  @Override protected void initializeData() {//懒加载在这里,so在写个方法让子类去实现
    lazyData();
  }

  @Override public void onDestroyView() {
    onDestroyV();
    mView = null;
    super.onDestroyView();
  }

  protected void onDestroyV() {
  }

  protected void initData() {
  }

  protected void lazyData() {
  }

  @Override public Class<T> getViewClass() {
    return GenericHelper.getViewClass(getClass());
  }
}
