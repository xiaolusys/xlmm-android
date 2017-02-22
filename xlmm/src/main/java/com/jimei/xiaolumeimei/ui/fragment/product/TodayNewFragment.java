package com.jimei.xiaolumeimei.ui.fragment.product;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.jimei.library.utils.JUtils;
import com.jimei.library.widget.banner.SliderLayout;
import com.jimei.library.widget.banner.SliderTypes.BaseSliderView;
import com.jimei.library.widget.banner.SliderTypes.DefaultSliderView;
import com.jimei.library.widget.scrolllayout.ScrollableHelper;
import com.jimei.library.widget.scrolllayout.ScrollableLayout;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.CustomSnapHelper;
import com.jimei.xiaolumeimei.adapter.MainProductAdapter;
import com.jimei.xiaolumeimei.adapter.MainTabAdapter;
import com.jimei.xiaolumeimei.base.BaseBindingFragment;
import com.jimei.xiaolumeimei.databinding.FragmentTodayNewBinding;
import com.jimei.xiaolumeimei.entities.MainTodayBean;
import com.jimei.xiaolumeimei.entities.PortalBean;
import com.jimei.xiaolumeimei.model.MainModel;
import com.jimei.xiaolumeimei.ui.activity.main.MainActivity;
import com.jimei.xiaolumeimei.util.JumpUtils;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wisdom on 17/2/13.
 */

public class TodayNewFragment extends BaseBindingFragment<FragmentTodayNewBinding>
    implements ScrollableHelper.ScrollableContainer, SwipeRefreshLayout.OnRefreshListener,
    ScrollableLayout.OnScrollListener {

    private static final String POST_URL = "?imageMogr2/format/jpg/quality/70";
    private Map<String, String> map = new HashMap<>();
    private MainTabAdapter mainTabAdapter;
    private List<MainTodayBean> data = new ArrayList<>();
    private MainProductAdapter mainProductAdapter;

    public static TodayNewFragment newInstance(String title) {
        Bundle args = new Bundle();
        args.putString("title", title);
        TodayNewFragment fragment = new TodayNewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View getLoadingView() {
        return b.layout;
    }

    @Override
    public void initData() {
        MainModel instance = MainModel.getInstance();
        addSubscription(instance
            .getPortalBean("activitys,categorys")
            .subscribe(this::initSliderLayout, Throwable::printStackTrace));
        addSubscription(instance
            .getMainTodayList()
            .subscribe(list -> {
                data.clear();
                data.addAll(list);
                mainTabAdapter.updateWithClear(list);
                int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                int position = 0;
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getHour() <= hour) {
                        position = i;
                    }
                }
                mainTabAdapter.setCurrentPosition(position);
                b.recyclerTab.scrollToPosition(position);
                mainProductAdapter.updateWithClear(list.get(position).getItems());
                hideIndeterminateProgressDialog();
            }, e -> {
                hideIndeterminateProgressDialog();
            }));
    }

    @Override
    public void setListener() {
        b.swipeLayout.setOnRefreshListener(this);
        b.layout.setOnScrollListener(this);
    }

    @Override
    protected void initViews() {
        b.swipeLayout.setColorSchemeResources(R.color.colorAccent);
        b.recyclerProduct.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        mainProductAdapter = new MainProductAdapter(mActivity);
        b.recyclerProduct.setAdapter(mainProductAdapter);

        LinearLayoutManager manager = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
        b.recyclerTab.setLayoutManager(manager);
        mainTabAdapter = new MainTabAdapter(mActivity) {
            @Override
            public void itemClick(int position) {
                if (data.size() > position) {
                    mainProductAdapter.updateWithClear(data.get(position).getItems());
                } else {
                    JUtils.Toast("数据加载失败,请下拉刷新后重试!");
                }
            }
        };
        b.recyclerTab.setAdapter(mainTabAdapter);
        CustomSnapHelper mLinearSnapHelper = new CustomSnapHelper(mainTabAdapter);
        mLinearSnapHelper.attachToRecyclerView(b.recyclerTab);
        b.layout.getHelper().setCurrentScrollableContainer(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_today_new;
    }

    public void initSliderLayout(PortalBean postBean) throws RuntimeException {
        map.clear();
        List<PortalBean.PostersBean> posters = postBean.getPosters();
        for (int i = 0; i < posters.size(); i++) {
            map.put(posters.get(i).getPic_link(), posters.get(i).getApp_link());
        }
        if (b.slider != null) {
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
    }

    @Override
    public View getScrollableView() {
        return b.recyclerProduct;
    }

    @Override
    public void onRefresh() {
        b.swipeLayout.setRefreshing(false);
        showIndeterminateProgressDialog(false);
        initData();
    }

    @Override
    public void onScroll(int currentY, int maxY) {
        ((MainActivity) mActivity).setTabLayoutMarginTop((double) currentY / b.slider.getHeight());
        if (currentY > 0) {
            b.swipeLayout.setEnabled(false);
        } else {
            b.swipeLayout.setEnabled(true);
        }
    }
}
