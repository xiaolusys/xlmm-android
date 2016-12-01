package com.jimei.xiaolumeimei.ui.fragment.main;


import android.view.View;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseBindingFragment;
import com.jimei.xiaolumeimei.databinding.FragmentMainTabBinding;

public class MainTabFragment extends BaseBindingFragment<FragmentMainTabBinding> {

    public static MainTabFragment newInstance() {
        return new MainTabFragment();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_main_tab;
    }

    @Override
    public View getLoadingView() {
        return b.layout;
    }

    @Override
    public void initData() {
        hideIndeterminateProgressDialog();
    }

    @Override
    protected void initViews() {

    }


}
