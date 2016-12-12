package com.jimei.xiaolumeimei.ui.fragment.main;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.jimei.library.utils.JUtils;
import com.jimei.library.widget.SpaceItemDecoration;
import com.jimei.library.widget.banner.SliderLayout;
import com.jimei.library.widget.banner.SliderTypes.BaseSliderView;
import com.jimei.library.widget.banner.SliderTypes.DefaultSliderView;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.MainActivityAdapter;
import com.jimei.xiaolumeimei.base.BaseBindingFragment;
import com.jimei.xiaolumeimei.databinding.FragmentBoutiqueListBinding;
import com.jimei.xiaolumeimei.entities.PortalBean;
import com.jimei.xiaolumeimei.model.MainModel;
import com.jimei.xiaolumeimei.utils.JumpUtils;
import com.umeng.analytics.MobclickAgent;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wisdom on 16/12/9.
 */

public class BoutiqueTabFragment extends BaseBindingFragment<FragmentBoutiqueListBinding> implements SwipeRefreshLayout.OnRefreshListener {

    private MainActivityAdapter adapter;
    private Map<String, String> map = new HashMap<>();
    private static final String POST_URL = "?imageMogr2/format/jpg/quality/70";

    public static BoutiqueTabFragment newInstance() {
        return new BoutiqueTabFragment();
    }

    @Override
    public View getLoadingView() {
        return b.swipeLayout;
    }

    @Override
    public void initData() {
        showIndeterminateProgressDialog(true);
        addSubscription(MainModel.getInstance()
                .getBoutiqueActivitys()
                .subscribe(portalBean -> {
                    if (portalBean != null) {
                        initSliderLayout(portalBean);
                        if (portalBean.getActivitys() != null) {
                            adapter.updateWithClear(portalBean.getActivitys());
                        }
                    }
                    hideIndeterminateProgressDialog();
                    if (b.swipeLayout.isRefreshing()) {
                        b.swipeLayout.setRefreshing(false);
                    }
                }, e -> {
                    hideIndeterminateProgressDialog();
                    if (b.swipeLayout.isRefreshing()) {
                        b.swipeLayout.setRefreshing(false);
                    }
                    if (e instanceof UnknownHostException) {
                        showNetworkError();
                    } else {
                        JUtils.Toast("数据加载有误,请下拉刷新!");
                    }
                }));
    }

    @Override
    protected void initViews() {
        b.swipeLayout.setColorSchemeResources(R.color.colorAccent);
        b.xrv.setLayoutManager(new LinearLayoutManager(mActivity));
        b.xrv.addItemDecoration(new SpaceItemDecoration(0, 0, 6, 6));
        adapter = new MainActivityAdapter(mActivity);
        b.xrv.setAdapter(adapter);
        b.scrollableLayout.getHelper().setCurrentScrollableContainer(b.xrv);
    }

    @Override
    public void setListener() {
        b.swipeLayout.setOnRefreshListener(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_boutique_list;
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
    public void onRefresh() {
        initData();
    }
}
