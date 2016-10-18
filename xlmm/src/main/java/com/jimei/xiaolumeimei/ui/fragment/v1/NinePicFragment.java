package com.jimei.xiaolumeimei.ui.fragment.v1;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.NinePicAdapter;
import com.jimei.xiaolumeimei.base.BaseLazyFragment;
import com.jimei.xiaolumeimei.databinding.FragmentNinePicBinding;
import com.jimei.xiaolumeimei.model.MMProductModel;

import rx.schedulers.Schedulers;

public class NinePicFragment extends BaseLazyFragment<FragmentNinePicBinding> implements SwipeRefreshLayout.OnRefreshListener {


    private NinePicAdapter mNinePicAdapter;

    public static NinePicFragment newInstance(String codeLink) {
        Bundle args = new Bundle();
        args.putString("codeLink", codeLink);
        NinePicFragment fragment = new NinePicFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initData() {
        if (getArguments() != null) {
            String codeLink = getArguments().getString("codeLink");
            mNinePicAdapter.setCodeLink(codeLink);
        }
        loadData();
    }

    private void loadData() {
        mNinePicAdapter.clear();
        addSubscription(MMProductModel.getInstance()
                .getNinePic(-1)
                .subscribeOn(Schedulers.io())
                .subscribe(ninePicBean -> {
                    if (ninePicBean != null) {
                        mNinePicAdapter.update(ninePicBean);
                    }
                    if (b.swipeLayout.isRefreshing()) {
                        b.swipeLayout.setRefreshing(false);
                    }
                }, Throwable::printStackTrace));
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
}
