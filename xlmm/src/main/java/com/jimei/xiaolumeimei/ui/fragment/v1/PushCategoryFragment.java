package com.jimei.xiaolumeimei.ui.fragment.v1;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.MainCategoryAdapter;
import com.jimei.xiaolumeimei.base.BaseLazyFragment;
import com.jimei.xiaolumeimei.databinding.FragmentPushCategoryBinding;
import com.jimei.xiaolumeimei.entities.PortalBean;
import com.jimei.xiaolumeimei.model.MMProductModel;
import com.jimei.xiaolumeimei.widget.SpaceItemDecoration;

import java.util.List;

import rx.schedulers.Schedulers;

/**
 * Created by wisdom on 16/10/9.
 */

public class PushCategoryFragment extends BaseLazyFragment<FragmentPushCategoryBinding> {

    private MainCategoryAdapter mMainCategoryAdapter;

    public static PushCategoryFragment newInstance(String codeLink) {
        Bundle args = new Bundle();
        args.putString("codeLink", codeLink);
        PushCategoryFragment fragment = new PushCategoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initData() {
        addSubscription(MMProductModel.getInstance()
                .getPortalBean()
                .subscribeOn(Schedulers.io())
                .subscribe(portalBean -> {
                    List<PortalBean.CategorysBean> categorys = portalBean.getCategorys();
                    mMainCategoryAdapter.updateWithClear(categorys);
                }, Throwable::printStackTrace));

    }

    @Override
    protected void initViews() {
        b.recyclerView.setLayoutManager(new GridLayoutManager(mActivity, 4));
        b.recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        b.recyclerView.addItemDecoration(new SpaceItemDecoration(15, 15, 25, 25));
        mMainCategoryAdapter = new MainCategoryAdapter(mActivity);
        if (getArguments()!=null) {
            String codeLink = getArguments().getString("codeLink");
            mMainCategoryAdapter.setCodeLink(codeLink);
        }
        b.recyclerView.setAdapter(mMainCategoryAdapter);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_push_category;
    }

    @Override
    public String getTitle() {
        return "按分类展示";
    }
}
