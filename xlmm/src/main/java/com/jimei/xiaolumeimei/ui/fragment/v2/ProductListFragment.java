package com.jimei.xiaolumeimei.ui.fragment.v2;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductListFragment extends BaseFragment {

    private View view;
    @Bind(R.id.xrv)
    XRecyclerView xRecyclerView;


    public static ProductListFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        ProductListFragment fragment = new ProductListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_product_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setDefaultFragmentTitle(String title) {

    }

    @Override
    public View getScrollableView() {
        return null;
    }
}
