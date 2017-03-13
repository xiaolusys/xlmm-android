package com.jimei.xiaolumeimei.ui.fragment.product;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.jimei.library.utils.JUtils;
import com.jimei.library.widget.SpaceItemDecoration;
import com.jimei.library.widget.banner.SliderLayout;
import com.jimei.library.widget.banner.SliderTypes.BaseSliderView;
import com.jimei.library.widget.banner.SliderTypes.DefaultSliderView;
import com.jimei.library.widget.scrolllayout.ScrollableHelper;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.XlmmApp;
import com.jimei.xiaolumeimei.adapter.ActivityAdapter;
import com.jimei.xiaolumeimei.base.BaseBindingFragment;
import com.jimei.xiaolumeimei.databinding.FragmentActivityBinding;
import com.jimei.xiaolumeimei.entities.PortalBean;
import com.jimei.xiaolumeimei.service.ServiceResponse;
import com.jimei.xiaolumeimei.util.JumpUtils;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wisdom on 17/2/28.
 */

public class ActivityFragment extends BaseBindingFragment<FragmentActivityBinding>
    implements SwipeRefreshLayout.OnRefreshListener, ScrollableHelper.ScrollableContainer {
    private static final String POST_URL = "?imageMogr2/format/jpg/quality/70";
    private Map<String, String> map = new HashMap<>();
    private ActivityAdapter adapter;

    public static ActivityFragment newInstance(String title) {
        Bundle args = new Bundle();
        args.putString("title", title);
        ActivityFragment fragment = new ActivityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View getLoadingView() {
        return b.layout;
    }

    @Override
    public void setListener() {
        b.swipeLayout.setOnRefreshListener(this);
    }

    @Override
    public void initData() {
        addSubscription(XlmmApp.getMainInteractor(mActivity)
            .getPortalBean("categorys", new ServiceResponse<PortalBean>() {
                @Override
                public void onNext(PortalBean portalBean) {
                    adapter.update(portalBean.getActivitys());
                    initSliderLayout(portalBean.getPosters());
                    hideIndeterminateProgressDialog();
                    if (b.swipeLayout.isRefreshing()) {
                        b.swipeLayout.setRefreshing(false);
                    }

                }

                @Override
                public void onError(Throwable e) {
                    JUtils.Toast("数据加载失败，请下拉刷新重试!");
                    hideIndeterminateProgressDialog();
                    if (b.swipeLayout.isRefreshing()) {
                        b.swipeLayout.setRefreshing(false);
                    }
                }
            }));
    }

    private void initSliderLayout(List<PortalBean.PostersBean> posters) {
        map.clear();
        for (int i = 0; i < posters.size(); i++) {
            map.put(posters.get(i).getPic_link(), posters.get(i).getApp_link());
        }
        if (b.slider != null) {
            b.slider.stopAutoCycle();
            b.slider.removeAllSliders();
        }
        for (String name : map.keySet()) {
            DefaultSliderView textSliderView = new DefaultSliderView(mActivity);
            textSliderView.image(name + POST_URL).setScaleType(BaseSliderView.ScaleType.CenterInside);
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle().putString("extra", map.get(name));
            b.slider.addSlider(textSliderView);
            textSliderView.setOnSliderClickListener(slider -> {
                if (slider.getBundle() != null) {
                    MobclickAgent.onEvent(mActivity, "BannerID");
                    String link = slider.getBundle().getString("extra");
                    JumpUtils.push_jump_proc(mActivity, link);
                }
            });
        }
        b.slider.setPresetIndicator(SliderLayout.PresetIndicators.Left_Bottom);
        b.slider.setDuration(3000);
        b.slider.startAutoCycle();
    }

    @Override
    protected void initViews() {
        b.swipeLayout.setColorSchemeResources(R.color.colorAccent);

        b.recyclerView.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        b.recyclerView.addItemDecoration(new SpaceItemDecoration(0, 0, 0, 18));
        adapter = new ActivityAdapter(mActivity);
        b.recyclerView.setAdapter(adapter);

        b.layout.getHelper().setCurrentScrollableContainer(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_activity;
    }

    @Override
    public void onRefresh() {
        adapter.clear();
        showIndeterminateProgressDialog(true);
        initData();
    }

    @Override
    public View getScrollableView() {
        return b.recyclerView;
    }
}
