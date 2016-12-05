package com.jimei.xiaolumeimei.ui.fragment.main;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.jimei.library.utils.DisplayUtils;
import com.jimei.library.utils.JUtils;
import com.jimei.library.widget.BrandView;
import com.jimei.library.widget.SpaceItemDecoration;
import com.jimei.library.widget.banner.SliderLayout;
import com.jimei.library.widget.banner.SliderTypes.BaseSliderView;
import com.jimei.library.widget.banner.SliderTypes.DefaultSliderView;
import com.jimei.library.widget.scrolllayout.ScrollableLayout;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.MainActivityAdapter;
import com.jimei.xiaolumeimei.adapter.MainCategoryAdapter;
import com.jimei.xiaolumeimei.adapter.MainFragmentAdapter;
import com.jimei.xiaolumeimei.base.BaseBindingFragment;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.databinding.FragmentMainTabBinding;
import com.jimei.xiaolumeimei.entities.PortalBean;
import com.jimei.xiaolumeimei.entities.event.ShowShopEvent;
import com.jimei.xiaolumeimei.model.MainModel;
import com.jimei.xiaolumeimei.ui.activity.main.TabActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MamaActivity;
import com.jimei.xiaolumeimei.ui.fragment.product.ProductListFragment;
import com.jimei.xiaolumeimei.utils.JumpUtils;
import com.jimei.xiaolumeimei.utils.LoginUtils;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainTabFragment extends BaseBindingFragment<FragmentMainTabBinding>
        implements View.OnClickListener, ViewPager.OnPageChangeListener,
        ScrollableLayout.OnScrollListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String POST_URL = "?imageMogr2/format/jpg/quality/70";
    private String mamaid;
    private List<ProductListFragment> list = new ArrayList<>();
    private Map<String, String> map = new HashMap<>();
    private int rvTopHeight;
    private int mask;
    private MainCategoryAdapter mMainCategoryAdapter;
    private MainActivityAdapter mainActivityAdapter;

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
        addSubscription(MainModel.getInstance()
                .getPortalBean()
                .subscribe(portalBean -> {
                    initSliderLayout(portalBean);
                    initCategory(portalBean);
                    initActivity(portalBean);
                    initBrand(portalBean);
                    if (b.swipeLayout != null) {
                        b.swipeLayout.setRefreshing(false);
                    }
                    hideIndeterminateProgressDialog();
                }, e -> {
                    e.printStackTrace();
                    JUtils.Toast("数据加载有误,请下拉刷新!");
                    if (b.swipeLayout != null) {
                        b.swipeLayout.setRefreshing(false);
                    }
                    hideIndeterminateProgressDialog();
                }));
        showShop();
    }

    private void showShop() {
        if (LoginUtils.checkLoginState(mActivity)) {
            addSubscription(MainModel.getInstance()
                    .getProfile()
                    .subscribe(userInfoBean -> {
                        ((TabActivity) mActivity).setUserInfoNewBean(userInfoBean);
                        if (userInfoBean != null && userInfoBean.getXiaolumm() != null
                                && userInfoBean.getXiaolumm().getId() != 0) {
                            mamaid = userInfoBean.getXiaolumm().getId() + "";
                            b.mainShop.setVisibility(View.VISIBLE);
                        } else {
                            b.mainShop.setVisibility(View.INVISIBLE);
                        }
                    }, e -> {
                        e.printStackTrace();
                        b.mainShop.setVisibility(View.INVISIBLE);
                    }));
        } else {
            b.mainShop.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void initViews() {
        EventBus.getDefault().register(this);
        b.swipeLayout.setColorSchemeResources(R.color.colorAccent);

        b.recyclerView.setLayoutManager(new GridLayoutManager(mActivity, 4));
        b.recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        b.recyclerView.addItemDecoration(new SpaceItemDecoration(15, 15, 25, 25));
        mMainCategoryAdapter = new MainCategoryAdapter(mActivity);
        b.recyclerView.setAdapter(mMainCategoryAdapter);

        b.activityRv.setLayoutManager(new LinearLayoutManager(mActivity));
        b.activityRv.setOverScrollMode(View.OVER_SCROLL_NEVER);
        b.activityRv.addItemDecoration(new SpaceItemDecoration(0, 0, 6, 6));
        mainActivityAdapter = new MainActivityAdapter(mActivity);
        b.activityRv.setAdapter(mainActivityAdapter);

        SharedPreferences sharedPreferencesMask = mActivity.getSharedPreferences("maskActivity", 0);
        mask = sharedPreferencesMask.getInt("mask", 0);
        MainFragmentAdapter adapter = new MainFragmentAdapter(getChildFragmentManager(), list);
        list.add(ProductListFragment.newInstance(XlmmConst.TYPE_YESTERDAY, "昨天热卖"));
        list.add(ProductListFragment.newInstance(XlmmConst.TYPE_TODAY, "今天特卖"));
        list.add(ProductListFragment.newInstance(XlmmConst.TYPE_TOMORROW, "即将上新"));
        b.viewPager.setAdapter(adapter);
        b.viewPager.setOffscreenPageLimit(3);
        b.viewPager.setCurrentItem(1);
        b.tabLayout.setupWithViewPager(b.viewPager);
        b.tabLayout.setTabMode(TabLayout.MODE_FIXED);
        b.scrollableLayout.getHelper().setCurrentScrollableContainer(list.get(1));
    }


    @Override
    public void setListener() {
        b.mainShop.setOnClickListener(this);
        b.viewPager.addOnPageChangeListener(this);
        b.scrollableLayout.setOnScrollListener(this);
        b.swipeLayout.setOnRefreshListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_shop:
                Intent intent = new Intent(mActivity, MamaActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("mamaid", mamaid);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        b.swipeLayout.setEnabled(false);
    }

    @Override
    public void onPageSelected(int position) {
        try {
            b.scrollableLayout.getHelper().setCurrentScrollableContainer(list.get(position));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        b.swipeLayout.setEnabled(true);
    }

    @Override
    public void onScroll(int currentY, int maxY) {
        currentY = -currentY;
        if (rvTopHeight == (DisplayUtils.getScreenH(mActivity) * 2 - 16)) {
            rvTopHeight = b.rvTop.getTop();
        }
        if (0 > rvTopHeight + currentY) {
            b.rvTop.setVisibility(View.VISIBLE);
            b.rvTop.setOnClickListener(v -> {
                b.scrollableLayout.scrollTo(0, 0);
                list.get(b.viewPager.getCurrentItem()).goToTop();
            });
        } else {
            b.rvTop.setVisibility(View.GONE);
        }

        if ((-currentY) > 0) {
            b.swipeLayout.setEnabled(false);
        } else {
            b.swipeLayout.setEnabled(true);
        }
    }

    @Override
    public void onRefresh() {
        initData();
        try {
            switch (b.viewPager.getCurrentItem()) {
                case 0:
                    list.get(0).load(b.swipeLayout);
                    break;
                case 1:
                    list.get(1).load(b.swipeLayout);
                    break;
                case 2:
                    list.get(2).load(b.swipeLayout);
                    break;
            }
            b.scrollableLayout.getHelper().setCurrentScrollableContainer(list.get(b.viewPager.getCurrentItem()));
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public void initCategory(PortalBean postBean) throws RuntimeException {
        List<PortalBean.CategorysBean> categorys = postBean.getCategorys();
        mMainCategoryAdapter.updateWithClear(categorys);
    }

    public void initActivity(PortalBean postBean) throws RuntimeException {
        List<PortalBean.ActivitysBean> postActivityBean = postBean.getActivitys();
        if (null != postActivityBean && postActivityBean.size() > 0) {
            mainActivityAdapter.updateWithClear(postActivityBean);
            if (mask != postActivityBean.get(0).getId() && !TextUtils.isEmpty(
                    postActivityBean.get(0).getMask_link())) {
                MastFragment mastFragment = MastFragment.newInstance("mask");
                mastFragment.show(mActivity.getFragmentManager(), "mask");
            }
        }
    }

    public void initBrand(PortalBean postBean) throws RuntimeException {
        List<BrandView> brandViews = new ArrayList<>();
        b.brand.removeAllViews();
        brandViews.clear();
        if (postBean.getPromotion_brands() != null) {
            b.brand.setVisibility(View.VISIBLE);
            List<PortalBean.PromotionBrandsBean> brandPromotionEntities = postBean.getPromotion_brands();
            if (brandPromotionEntities.size() != 0) {
                BrandView brandView;
                for (int i = 0; i < brandPromotionEntities.size(); i++) {
                    brandView = new BrandView(mActivity);
                    brandViews.add(brandView);
                    b.brand.addView(brandView);
                }
                for (int i = 0; i < brandPromotionEntities.size(); i++) {
                    brandViews.get(i).setBrandtitleImage(brandPromotionEntities.get(i).getAct_logo());
                    brandViews.get(i)
                            .setBrandDesText(
                                    brandPromotionEntities.get(i).getExtras().getBrandinfo().getTail_title());
                    brandViews.get(i).setBrandTitle(brandPromotionEntities.get(i).getTitle());
                    brandViews.get(i).setBrandListImage(brandPromotionEntities.get(i).getAct_img());
                    final int finalI1 = i;
                    brandViews.get(i).setOnClickListener(v -> {
                        if (!TextUtils.isEmpty(brandPromotionEntities.get(finalI1).getAct_applink())) {
                            JumpUtils.push_jump_proc(mActivity,
                                    brandPromotionEntities.get(finalI1).getAct_applink());
                        }
                    });
                }
            }
        } else {
            b.brand.setVisibility(View.GONE);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshShop(ShowShopEvent event) {
        showShop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
