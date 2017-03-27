package com.jimei.xiaolumeimei.ui.fragment.main;

import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.XlmmApp;
import com.jimei.xiaolumeimei.adapter.BaseTabAdapter;
import com.jimei.xiaolumeimei.base.BaseBindingFragment;
import com.jimei.xiaolumeimei.base.BaseFragment;
import com.jimei.xiaolumeimei.databinding.FragmentMainTabBinding;
import com.jimei.xiaolumeimei.entities.PortalBean;
import com.jimei.xiaolumeimei.service.ServiceResponse;
import com.jimei.xiaolumeimei.ui.activity.main.TabActivity;
import com.jimei.xiaolumeimei.ui.fragment.product.ActivityFragment;
import com.jimei.xiaolumeimei.ui.fragment.product.ProductFragment;
import com.jimei.xiaolumeimei.ui.fragment.product.TodayNewFragment;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wisdom on 17/3/6.
 */

public class MainTabFragment extends BaseBindingFragment<FragmentMainTabBinding> implements ViewPager.OnPageChangeListener {

    public static MainTabFragment newInstance() {
        return new MainTabFragment();
    }

    @Override
    public View getLoadingView() {
        return b.layout;
    }

    @Override
    public void setListener() {
        b.viewPager.addOnPageChangeListener(this);
    }

    @Override
    public void initData() {
        List<BaseFragment> fragments = new ArrayList<>();
        fragments.add(ActivityFragment.newInstance("精品活动"));
        fragments.add(TodayNewFragment.newInstance("每日焦点"));
        addSubscription(XlmmApp.getMainInteractor(mActivity)
            .getPortalBean("activitys,posters", new ServiceResponse<PortalBean>() {
                @Override
                public void onNext(PortalBean portalBean) {
                    List<PortalBean.CategorysBean> categorys = portalBean.getCategorys();
                    if (categorys != null && categorys.size() > 0) {
                        for (int i = 0; i < categorys.size(); i++) {
                            PortalBean.CategorysBean bean = categorys.get(i);
                            fragments.add(ProductFragment.newInstance(bean.getId(), bean.getName(),true));
                        }
                        BaseTabAdapter mAdapter = new BaseTabAdapter(getChildFragmentManager(), fragments);
                        b.viewPager.setAdapter(mAdapter);
                        b.viewPager.setOffscreenPageLimit(fragments.size());
                        b.tabLayout.setupWithViewPager(b.viewPager);
                        b.tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                    }
                    hideIndeterminateProgressDialog();
                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                    hideIndeterminateProgressDialog();
                }
            }));
    }

    @Override
    protected void initViews() {
        ((TabActivity) mActivity).mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                setTabLayoutMarginTop((double) msg.what / 100);
            }
        };
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_main_tab;
    }

    public void setTabLayoutMarginTop(double percent) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, b.tabLayout.getHeight());
        if (percent > 0) {
            b.viewPager.setScrollable(false);
            double height = percent * b.tabLayout.getHeight();
            params.setMargins(0, (int) -height, 0, 0);
            b.tabLayout.setLayoutParams(params);
        } else {
            b.viewPager.setScrollable(true);
            params.setMargins(0, 0, 0, 0);
        }
        b.tabLayout.setLayoutParams(params);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                MobclickAgent.onEvent(mActivity, "tab_activity");
                break;
            case 1:
                MobclickAgent.onEvent(mActivity, "tab_today");
                break;
            default:
                MobclickAgent.onEvent(mActivity, "tab_category_list");
                break;
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
