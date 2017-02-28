package com.jimei.xiaolumeimei.ui.fragment.xiaolumama;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.jimei.library.widget.SpaceItemDecoration;
import com.jimei.library.widget.scrolllayout.ScrollableHelper;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.XlmmApp;
import com.jimei.xiaolumeimei.adapter.MainCategoryAdapter;
import com.jimei.xiaolumeimei.adapter.NinePicAdapter;
import com.jimei.xiaolumeimei.base.BaseBindingFragment;
import com.jimei.xiaolumeimei.databinding.FragmentPushCategoryBinding;
import com.jimei.xiaolumeimei.entities.PortalBean;
import com.jimei.xiaolumeimei.model.MamaInfoModel;
import com.jimei.xiaolumeimei.service.ServiceResponse;

import java.util.List;

/**
 * Created by wisdom on 16/10/9.
 */

public class PushCategoryFragment extends BaseBindingFragment<FragmentPushCategoryBinding> implements ScrollableHelper.ScrollableContainer {

    private MainCategoryAdapter mMainCategoryAdapter;
    private NinePicAdapter mNinePicAdapter;

    public static PushCategoryFragment newInstance(String codeLink) {
        Bundle args = new Bundle();
        args.putString("codeLink", codeLink);
        PushCategoryFragment fragment = new PushCategoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initData() {
        addSubscription(XlmmApp.getMainInteractor(mActivity)
            .getPortalBean("activitys,posters", new ServiceResponse<PortalBean>() {
                @Override
                public void onNext(PortalBean portalBean) {
                    List<PortalBean.CategorysBean> categorys = portalBean.getCategorys();
                    mMainCategoryAdapter.updateWithClear(categorys);
                    b.scrollableLayout.setVisibility(View.VISIBLE);
                    hideIndeterminateProgressDialog();
                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                    hideIndeterminateProgressDialog();
                }
            }));
        addSubscription(MamaInfoModel.getInstance()
            .getNinePicByOrdering()
            .subscribe(ninePicBean -> {
                if (ninePicBean != null) {
                    mNinePicAdapter.update(ninePicBean);
                }
            }, Throwable::printStackTrace));
    }

    @Override
    protected void initViews() {
        b.scrollableLayout.getHelper().setCurrentScrollableContainer(this);
        mNinePicAdapter = new NinePicAdapter(mActivity);
        if (getArguments() != null) {
            String codeLink = getArguments().getString("codeLink");
            mNinePicAdapter.setCodeLink(codeLink);
        }
        b.lv.setAdapter(mNinePicAdapter);
        b.recyclerView.setLayoutManager(new GridLayoutManager(mActivity, 4));
        b.recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        b.recyclerView.addItemDecoration(new SpaceItemDecoration(15, 15, 25, 25));
        mMainCategoryAdapter = new MainCategoryAdapter(mActivity);
        if (getArguments() != null) {
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

    @Override
    public View getScrollableView() {
        return b.lv;
    }

    @Override
    public View getLoadingView() {
        return b.scrollableLayout;
    }
}
