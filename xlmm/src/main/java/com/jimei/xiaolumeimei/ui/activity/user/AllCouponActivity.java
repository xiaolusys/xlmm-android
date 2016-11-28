package com.jimei.xiaolumeimei.ui.activity.user;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.entities.CouponEntity;
import com.jimei.xiaolumeimei.model.UserModel;
import com.jimei.xiaolumeimei.ui.fragment.user.CouponFragment;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.Observable;

public class AllCouponActivity extends BaseSwipeBackCompatActivity {
    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    ArrayList<CouponFragment> fragments = new ArrayList<>();
    List<String> titles = new ArrayList<>();

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_all_coupon;
    }

    @Override
    protected void initViews() {
        showIndeterminateProgressDialog(false);
        addSubscription(Observable.concat(UserModel.getInstance().getCouponList(0), UserModel.getInstance().getCouponList(0, 8),
                UserModel.getInstance().getCouponList(3), UserModel.getInstance().getCouponList(1))
                .subscribe(new ServiceResponse<ArrayList<CouponEntity>>() {
                    @Override
                    public void onNext(ArrayList<CouponEntity> couponEntities) {
                        if (titles.size() == 0) {
                            titles.add("未使用(" + couponEntities.size() + ")");
                            fragments.add(CouponFragment.newInstance(XlmmConst.UNUSED_COUPON, couponEntities));
                        } else if (titles.size() == 1) {
                            titles.add("精品券(" + couponEntities.size() + ")");
                            fragments.add(CouponFragment.newInstance(XlmmConst.GOOD_COUPON, couponEntities));
                        } else if (titles.size() == 2) {
                            titles.add("已过期(" + couponEntities.size() + ")");
                            fragments.add(CouponFragment.newInstance(XlmmConst.PAST_COUPON, couponEntities));
                        } else if (titles.size() == 3) {
                            titles.add("已使用(" + couponEntities.size() + ")");
                            fragments.add(CouponFragment.newInstance(XlmmConst.USED_COUPON, couponEntities));
                        }
                    }

                    @Override
                    public void onCompleted() {
                        MainTabAdapter mAdapter = new MainTabAdapter(getSupportFragmentManager());
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

    public class MainTabAdapter extends FragmentPagerAdapter {

        public MainTabAdapter(FragmentManager fm) {
            super(fm);
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
            return titles.get(position);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(this.getClass().getSimpleName());
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
        MobclickAgent.onPause(this);
    }
}
