package com.jimei.xiaolumeimei.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;

import com.jimei.xiaolumeimei.R;


/**
 * Created by itxuye on 2016/7/26.
 */
public abstract class BaseMVVMActivity<T extends ViewDataBinding> extends BaseActivity {
    protected T b;

    @Override
    public void initContentView() {
        b = DataBindingUtil.setContentView(this, getContentViewLayoutID());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.right_in, R.anim.right_out);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.right_in, R.anim.right_out);
    }

}
