package com.jimei.xiaolumeimei.ui.activity.user;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseFragment;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.CouponSelectEntity;
import com.jimei.xiaolumeimei.model.UserModel;
import com.jimei.xiaolumeimei.ui.activity.main.ActivityWebViewActivity;
import com.jimei.xiaolumeimei.ui.fragment.coupon.SelectCouponFragment;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.umeng.analytics.MobclickAgent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.schedulers.Schedulers;

public class SelectCouponActivity extends BaseSwipeBackCompatActivity {
    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    String selected_couponid;
    String cart_ids;
    List<BaseFragment> fragments = new ArrayList<>();

    @Override
    protected void initData() {
        showIndeterminateProgressDialog(false);
        UserModel.getInstance()
                .getCouponSelectEntity(cart_ids)
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<CouponSelectEntity>() {
                    @Override
                    public void onNext(CouponSelectEntity couponSelectEntity) {
                        SelectCouponAdapter mAdapter = new SelectCouponAdapter(getSupportFragmentManager());
                        fragments.add(SelectCouponFragment.newInstance(0, "可用优惠券", selected_couponid, couponSelectEntity.getUsable_coupon()));
                        fragments.add(SelectCouponFragment.newInstance(1, "不可用优惠券", "", couponSelectEntity.getDisable_coupon()));
                        viewPager.setAdapter(mAdapter);
                        viewPager.setOffscreenPageLimit(2);
                        tabLayout.setupWithViewPager(viewPager);
                        tabLayout.setTabMode(TabLayout.MODE_FIXED);
                        hideIndeterminateProgressDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        hideIndeterminateProgressDialog();
                    }
                });
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        selected_couponid = extras.getString("coupon_id", "");
        cart_ids = extras.getString("cart_ids", "");
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_select;
    }

    public class SelectCouponAdapter extends FragmentPagerAdapter implements Serializable {
        FragmentManager fm;

        public SelectCouponAdapter(FragmentManager fm) {
            super(fm);
            this.fm = fm;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragments.get(position).getTitle();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(ActivityWebViewActivity.class.getSimpleName());
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(ActivityWebViewActivity.class.getSimpleName());
        MobclickAgent.onPause(this);
    }
}
