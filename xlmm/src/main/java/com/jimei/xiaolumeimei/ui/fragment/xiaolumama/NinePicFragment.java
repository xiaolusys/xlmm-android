package com.jimei.xiaolumeimei.ui.fragment.xiaolumama;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.NinePicAdapter;
import com.jimei.xiaolumeimei.base.BaseBindingFragment;
import com.jimei.xiaolumeimei.databinding.FragmentNinePicBinding;
import com.jimei.xiaolumeimei.model.MamaInfoModel;

public class NinePicFragment extends BaseBindingFragment<FragmentNinePicBinding> implements SwipeRefreshLayout.OnRefreshListener {


    private NinePicAdapter mNinePicAdapter;

    public static NinePicFragment newInstance(String codeLink) {
        Bundle args = new Bundle();
        args.putString("codeLink", codeLink);
        NinePicFragment fragment = new NinePicFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initData() {
        if (getArguments() != null) {
            String codeLink = getArguments().getString("codeLink");
            mNinePicAdapter.setCodeLink(codeLink);
        }
        loadData();
    }

    private void loadData() {
        mNinePicAdapter.clear();
        addSubscription(MamaInfoModel.getInstance()
                .getNinePic(-1)
                .subscribe(ninePicBean -> {
                    if (ninePicBean != null) {
                        mNinePicAdapter.update(ninePicBean);
                    }
                    if (b.swipeLayout.isRefreshing()) {
                        b.swipeLayout.setRefreshing(false);
                    }
                    hideIndeterminateProgressDialog();
                }, throwable -> {
                    throwable.printStackTrace();
                    hideIndeterminateProgressDialog();
                }));
    }

    @Override
    protected void initViews() {
        b.swipeLayout.setColorSchemeResources(R.color.colorAccent);
        b.swipeLayout.setOnRefreshListener(this);
        mNinePicAdapter = new NinePicAdapter(mActivity);
        b.lv.setAdapter(mNinePicAdapter);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_nine_pic;
    }

    @Override
    public String getTitle() {
        return "按日期展示";
    }

    @Override
    public void onRefresh() {
        loadData();
    }

    @Override
    public View getLoadingView() {
        return b.swipeLayout;
    }
}
