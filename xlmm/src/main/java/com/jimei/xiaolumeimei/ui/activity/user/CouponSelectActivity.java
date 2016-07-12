package com.jimei.xiaolumeimei.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.CouponListAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.entities.CouponBean;
import com.jimei.xiaolumeimei.entities.CouponEntity;
import com.jimei.xiaolumeimei.model.UserModel;
import com.jimei.xiaolumeimei.ui.activity.main.MainActivity;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.schedulers.Schedulers;

public class CouponSelectActivity extends BaseSwipeBackCompatActivity
        implements View.OnClickListener, AdapterView.OnItemClickListener {
    String TAG = "CouponSelectActivity";

    @Bind(R.id.btn_jump)
    Button btn_jump;
    @Bind(R.id.btn_cancel_coupon)
    Button btn_cancel_coupon;
    @Bind(R.id.rlayout_order_empty)
    RelativeLayout rl_empty;
    @Bind(R.id.lv_unused_coupon)
    ListView lv_unused_coupon;
    int unused_num = 0;
    List<CouponEntity> list = new ArrayList<>();
    String selected_couponid;
    private CouponListAdapter mCouponAdapter;
    private int page = 2;

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height =
                totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    @Override
    protected void setListener() {
        btn_cancel_coupon.setOnClickListener(this);
        btn_jump.setOnClickListener(this);
        lv_unused_coupon.setOnItemClickListener(this);
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        selected_couponid = extras.getString("coupon_id");
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_selectcoupon;
    }

    @Override
    protected void initViews() {
        mCouponAdapter = new CouponListAdapter(this);
        lv_unused_coupon.setAdapter(mCouponAdapter);
        lv_unused_coupon.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    if (view.getLastVisiblePosition() == view.getCount() - 1) {
                        loadMoreData(page);
                        page++;
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        TextView tx_info = (TextView) findViewById(R.id.tx_info);
        tx_info.setText("亲，您暂时还没有优惠券哦~快去看看吧！");
    }

    //从server端获得所有订单数据，可能要查询几次
    @Override
    protected void initData() {
        loadMoreData(1);
    }

    private void loadMoreData(int page) {
        UserModel.getInstance()
                .getUnusedCouponBean(page)
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<CouponBean>() {
                    @Override
                    public void onNext(CouponBean couponBean) {
                        List<CouponEntity> results = couponBean.getResults();
                        list.addAll(results);
                        unused_num = results.size();
                        if (0 != results.size()) {
                            rl_empty.setVisibility(View.INVISIBLE);
                            List<CouponEntity> unusedList = new ArrayList<>();
                            for (CouponEntity result : results) {
                                if (result.getStatus() == 0) {
                                    unusedList.add(result);
                                }
                            }
                            if (unusedList.size() > 0) {
                                mCouponAdapter.update(unusedList, XlmmConst.UNUSED_COUPON,
                                        selected_couponid);
                            }
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
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btn_jump:
                intent = new Intent(CouponSelectActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btn_cancel_coupon:
                intent = new Intent();
                intent.putExtra("coupon_id", "");
                intent.putExtra("coupon_price", (double) 0);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CouponEntity resultsEntity = list.get(position);
        String coupon_id = resultsEntity.getId() + "";
        double coupon_value = resultsEntity.getCoupon_value();
        Intent intent = new Intent();
        intent.putExtra("coupon_id", coupon_id);
        intent.putExtra("coupon_price", coupon_value);
        setResult(RESULT_OK, intent);
        finish();
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
