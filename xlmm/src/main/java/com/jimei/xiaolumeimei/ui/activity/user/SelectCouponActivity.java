package com.jimei.xiaolumeimei.ui.activity.user;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.jimei.library.utils.JUtils;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.BaseTabAdapter;
import com.jimei.xiaolumeimei.base.BaseFragment;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.CouponSelectEntity;
import com.jimei.xiaolumeimei.model.UserModel;
import com.jimei.xiaolumeimei.ui.fragment.user.SelectCouponFragment;
import com.jimei.xiaolumeimei.service.ServiceResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class SelectCouponActivity extends BaseSwipeBackCompatActivity {
    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    @Bind(R.id.layout)
    LinearLayout layout;
    String selected_couponid;
    String cart_ids;
    private int goodNum;
    private boolean couponFlag;
    List<BaseFragment> fragments = new ArrayList<>();

    @Override
    protected void initData() {
        showIndeterminateProgressDialog(false);
        UserModel.getInstance()
            .getCouponSelectEntity(cart_ids)
            .subscribe(new ServiceResponse<CouponSelectEntity>() {
                @Override
                public void onNext(CouponSelectEntity couponSelectEntity) {
                    BaseTabAdapter mAdapter = new BaseTabAdapter(getSupportFragmentManager(), fragments);
                    if (couponFlag && goodNum > 1) {
                        fragments.add(SelectCouponFragment.newInstance(0, "可用优惠券", selected_couponid, couponSelectEntity.getUsable_coupon(), goodNum));
                    } else {
                        fragments.add(SelectCouponFragment.newInstance(0, "可用优惠券", selected_couponid, couponSelectEntity.getUsable_coupon()));
                    }
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
                    JUtils.Toast("获取优惠券信息失败!");
                    hideIndeterminateProgressDialog();
                }
            });
    }

    @Override
    public View getLoadingView() {
        return layout;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        selected_couponid = extras.getString("coupon_id", "");
        cart_ids = extras.getString("cart_ids", "");
        goodNum = extras.getInt("goodNum", 1);
        couponFlag = extras.getBoolean("couponFlag", false);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_select;
    }

}
