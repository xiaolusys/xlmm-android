package com.jimei.xiaolumeimei.base;

import android.os.Bundle;
import android.view.View;

import com.jimei.library.utils.TUtil;
import com.jimei.library.widget.loading.VaryViewHelperController;
import com.jude.utils.JUtils;

/**
 * Created by itxuye on 2016/6/24.
 */
public abstract class BasePresenterActivity<T extends BasePresenter, E extends BaseModel>
        extends BaseActivity implements BaseView {
    public T mPresenter;
    public E mModel;
    VaryViewHelperController mVaryViewHelperController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getContentViewLayoutID() != 0) {
            setContentView(getContentViewLayoutID());
        } else {
            throw new IllegalArgumentException("You must return a right contentView layout resource Id");
        }
        if (mVaryViewHelperController == null) {
            mVaryViewHelperController = new VaryViewHelperController(getLoadingView());
        }
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        initViews();
        mPresenter.setVM(this, mModel);
        initData();
        setListener();
    }

    protected abstract View getLoadingView();

    @Override
    public void showLoading() {
        if (mVaryViewHelperController == null) {
            throw new IllegalStateException("no ViewHelperController");
        }
        mVaryViewHelperController.showLoading();
    }

    @Override
    public void showNetworkError() {
        if (!JUtils.isNetWorkAvilable()) {
            if (mVaryViewHelperController == null) {
                throw new IllegalStateException("no ViewHelperController");
            }
            mVaryViewHelperController.showNetworkError(view -> getDataCallBack());
        }
    }

    @Override
    public void refreshView() {
        if (mVaryViewHelperController == null) {
            throw new IllegalStateException("no ViewHelperController");
        }
        mVaryViewHelperController.restore();
    }

    protected abstract void getDataCallBack();

    @Override
    public void showEmpty(String msg) {
        if (mVaryViewHelperController == null) {
            throw new IllegalStateException("no ViewHelperController");
        }
        mVaryViewHelperController.showEmpty(msg);
    }

}
