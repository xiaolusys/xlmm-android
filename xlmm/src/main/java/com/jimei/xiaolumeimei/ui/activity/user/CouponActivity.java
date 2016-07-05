package com.jimei.xiaolumeimei.ui.activity.user;

import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.CouponListAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.entities.CouponBean;
import com.jimei.xiaolumeimei.model.UserModel;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import butterknife.Bind;
import rx.schedulers.Schedulers;

public class CouponActivity extends BaseSwipeBackCompatActivity {
    String TAG = "CouponActivity";
    @Bind(R.id.layout_coupon_empty)
    FrameLayout rl_empty;
    @Bind(R.id.lv_unused_coupon)
    ListView lv_unused_coupon;
    private CouponListAdapter mUnusedCouponAdapter;
    private int page = 2;

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
        lv_unused_coupon.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    if (view.getLastVisiblePosition() == view.getCount() - 1) {
                        loadMoreData(page + "");
                        page++;
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }


    //从server端获得所有订单数据，可能要查询几次
    @Override
    protected void initData() {
        loadMoreData("1");
    }

    private void loadMoreData(String page) {
        UserModel.getInstance()
                .getUnusedCouponBean(page)
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<CouponBean>() {
                    @Override
                    public void onNext(CouponBean couponBean) {
                        List<CouponBean.ResultsBean> results = couponBean.getResults();
                        if (results.size() > 0) {
                            rl_empty.setVisibility(View.GONE);
                            mUnusedCouponAdapter.update(results, XlmmConst.UNUSED_COUPON, "");
                        }
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
