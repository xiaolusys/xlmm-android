package com.jimei.xiaolumeimei.base;

import android.os.Bundle;
import android.view.View;

import com.jimei.library.utils.JUtils;
import com.jimei.library.utils.TUtil;
import com.jimei.library.widget.loading.VaryViewHelperController;
import com.jimei.xiaolumeimei.R;

/**
 * Created by itxuye on 2016/6/24.
 */
public abstract class BasePresenterActivity<T extends BasePresenter, E>
        extends BaseActivity implements BaseView {
    public T mPresenter;
    public E mModel;
    VaryViewHelperController mVaryViewHelperController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
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

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    protected abstract View getLoadingView();

    @Override
    public void showNetworkError() {
        if (!JUtils.isNetWorkAvilable()) {
            hideIndeterminateProgressDialog();
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

}
