package com.jimei.xiaolumeimei.ui.activity.user;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

import butterknife.Bind;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.CouponListAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.entities.CouponBean;
import com.jimei.xiaolumeimei.model.UserModel;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;

import java.util.ArrayList;
import java.util.List;

import rx.schedulers.Schedulers;

public class CouponActivity extends BaseSwipeBackCompatActivity {
    String TAG = "CouponActivity";
    @Bind(R.id.layout_coupon_empty)
    FrameLayout rl_empty;
    @Bind(R.id.sv_frame_coupon)
    ScrollView sv_frame_coupon;
    @Bind(R.id.ll_unused)
    LinearLayout unusedLayout;
    @Bind(R.id.ll_used)
    LinearLayout usedLayout;
    @Bind(R.id.ll_past)
    LinearLayout pastLayout;
    @Bind(R.id.lv_unused_coupon)
    ListView lv_unused_coupon;
    @Bind(R.id.lv_timeout_coupon)
    ListView lv_timeout_coupon;
    @Bind(R.id.lv_used_coupon)
    ListView lv_used_coupon;
    int unused_num = 0;
    List<CouponBean.ResultsEntity> list = new ArrayList<>();
    private CouponListAdapter mUnusedCouponAdapter;
    private CouponListAdapter mUsedCouponAdapter;
    private CouponListAdapter mPastCouponAdapter;

    @Override
    protected void setListener() {
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_coupon;
    }

    @Override
    protected void initViews() {

        mUnusedCouponAdapter = new CouponListAdapter(this);
        lv_unused_coupon.setAdapter(mUnusedCouponAdapter);

        mUsedCouponAdapter = new CouponListAdapter(this);
        lv_used_coupon.setAdapter(mUsedCouponAdapter);

        mPastCouponAdapter = new CouponListAdapter(this);
        lv_timeout_coupon.setAdapter(mPastCouponAdapter);
    }

    //从server端获得所有订单数据，可能要查询几次
    @Override
    protected void initData() {
        UserModel.getInstance().getUnusedCouponBean()
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<CouponBean>() {
                    @Override
                    public void onNext(CouponBean couponBean) {
                        List<CouponBean.ResultsEntity> results = couponBean.getResults();
                        list.addAll(results);
                        unused_num = results.size();
                        if (0 != results.size()) {
                            rl_empty.setVisibility(View.INVISIBLE);
                            List<CouponBean.ResultsEntity> unusedList = new ArrayList<>();
                            List<CouponBean.ResultsEntity> usedList = new ArrayList<>();
                            for (CouponBean.ResultsEntity result : results) {
                                if (result.getStatus() == 2) {
                                    usedList.add(result);
                                } else if(result.getStatus() == 0){
                                    unusedList.add(result);
                                }
                            }
                            if (unusedList.size() > 0) {
                                unusedLayout.setVisibility(View.VISIBLE);
                                mUnusedCouponAdapter.update(unusedList, XlmmConst.UNUSED_COUPON, "");
                            }
                            if (usedList.size() > 0) {
                                usedLayout.setVisibility(View.VISIBLE);
                                mUsedCouponAdapter.update(usedList, 3, "");
                            }
                        }

                        UserModel.getInstance().getPastCouponBean()
                                .subscribeOn(Schedulers.io())
                                .subscribe(new ServiceResponse<CouponBean>() {
                                    @Override
                                    public void onNext(CouponBean couponBean) {
                                        List<CouponBean.ResultsEntity> results = couponBean.getResults();
                                        if (0 == results.size()) {
                                            if (0 == unused_num) {
                                                sv_frame_coupon.setVisibility(View.INVISIBLE);
                                                rl_empty.setVisibility(View.VISIBLE);
                                            }
                                        } else {
                                            pastLayout.setVisibility(View.VISIBLE);
                                            rl_empty.setVisibility(View.INVISIBLE);
                                            mPastCouponAdapter.update(results, XlmmConst.PAST_COUPON, "");
                                        }

                                        Log.i(TAG, "过期的" + couponBean.toString());
                                    }
                                });
                    }
                });
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }
}
