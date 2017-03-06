package com.jimei.xiaolumeimei.ui.activity.user;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.XlmmApp;
import com.jimei.xiaolumeimei.adapter.BaseTabAdapter;
import com.jimei.xiaolumeimei.base.BaseFragment;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.entities.CouponEntity;
import com.jimei.xiaolumeimei.service.ServiceResponse;
import com.jimei.xiaolumeimei.ui.fragment.user.CouponFragment;

import java.util.ArrayList;

import butterknife.Bind;
import rx.Observable;

public class AllCouponActivity extends BaseSwipeBackCompatActivity {
    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    @Bind(R.id.layout)
    LinearLayout layout;
    ArrayList<BaseFragment> fragments = new ArrayList<>();

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_all_coupon;
    }

    @Override
    public View getLoadingView() {
        return layout;
    }

    @Override
    protected void initData() {
        showIndeterminateProgressDialog(false);
        addSubscription(Observable.concat(XlmmApp.getUserInteractor(this).getCouponList(0), XlmmApp.getUserInteractor(this).getCouponList(0, 8),
            XlmmApp.getUserInteractor(this).getCouponList(3), XlmmApp.getUserInteractor(this).getCouponList(1))
            .subscribe(new ServiceResponse<ArrayList<CouponEntity>>() {
                @Override
                public void onNext(ArrayList<CouponEntity> couponEntities) {
                    if (fragments.size() == 0) {
                        fragments.add(CouponFragment.newInstance(XlmmConst.UNUSED_COUPON, couponEntities));
                    } else if (fragments.size() == 1) {
                        fragments.add(CouponFragment.newInstance(XlmmConst.GOOD_COUPON, couponEntities));
                    } else if (fragments.size() == 2) {
                        fragments.add(CouponFragment.newInstance(XlmmConst.PAST_COUPON, couponEntities));
                    } else if (fragments.size() == 3) {
                        fragments.add(CouponFragment.newInstance(XlmmConst.USED_COUPON, couponEntities));
                    }
                }

                @Override
                public void onCompleted() {
                    BaseTabAdapter mAdapter = new BaseTabAdapter(getSupportFragmentManager(), fragments);
                    viewPager.setAdapter(mAdapter);
                    viewPager.setOffscreenPageLimit(3);
                    tabLayout.setupWithViewPager(viewPager);
                    tabLayout.setTabMode(TabLayout.MODE_FIXED);
                    hideIndeterminateProgressDialog();
                }

                @Override
                public void onError(Throwable e) {
                    hideIndeterminateProgressDialog();
                }
            }));
    }

}
