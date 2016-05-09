package com.jimei.xiaolumeimei.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import butterknife.Bind;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.CouponListAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.entities.CouponBean;
import com.jimei.xiaolumeimei.model.UserModel;
import com.jimei.xiaolumeimei.ui.activity.main.MainActivity;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;

import java.util.ArrayList;
import java.util.List;

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
    @Bind(R.id.ll_unused)
    LinearLayout unusedLayout;
    @Bind(R.id.ll_used)
    LinearLayout usedLayout;
    @Bind(R.id.lv_unused_coupon)
    ListView lv_unused_coupon;
    @Bind(R.id.lv_used_coupon)
    ListView lv_used_coupon;
    int unused_num = 0;
    List<CouponBean.ResultsBean> list = new ArrayList<>();
    String selected_couponid;
    private CouponListAdapter mCouponAdapter;
    private CouponListAdapter mUsedCouponAdapter;
    private CouponListAdapter mPastCouponAdapter;

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

        mUsedCouponAdapter = new CouponListAdapter(this);
        lv_used_coupon.setAdapter(mUsedCouponAdapter);

        ListView lv_timeout_coupon = (ListView) findViewById(R.id.lv_timeout_coupon);
        mPastCouponAdapter = new CouponListAdapter(this);
        lv_timeout_coupon.setAdapter(mPastCouponAdapter);

        TextView tx_info = (TextView) findViewById(R.id.tx_info);
        tx_info.setText("亲，您暂时还没有优惠券哦~快去看看吧！");
    }

    //从server端获得所有订单数据，可能要查询几次
    @Override
    protected void initData() {
        UserModel.getInstance()
                .getUnusedCouponBean()
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<CouponBean>() {
                    @Override
                    public void onNext(CouponBean couponBean) {
                        List<CouponBean.ResultsBean> results = couponBean.getResults();
                        list.addAll(results);
                        unused_num = results.size();
                        if (0 != results.size()) {
                            rl_empty.setVisibility(View.INVISIBLE);
                            List<CouponBean.ResultsBean> unusedList = new ArrayList<>();
                            List<CouponBean.ResultsBean> usedList = new ArrayList<>();
                            for (CouponBean.ResultsBean result : results) {
                                if (result.getStatus() == 2) {
                                    usedList.add(result);
                                } else if (result.getStatus() == 0) {
                                    unusedList.add(result);
                                }
                            }
                            if (unusedList.size() > 0) {
                                unusedLayout.setVisibility(View.VISIBLE);
                                mCouponAdapter.update(unusedList, XlmmConst.UNUSED_COUPON, selected_couponid);
                            }
                            if (usedList.size() > 0) {
                                usedLayout.setVisibility(View.VISIBLE);
                                mUsedCouponAdapter.update(usedList, 3, "");
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
        CouponBean.ResultsBean resultsEntity = list.get(position);
        String coupon_id = resultsEntity.getId() + "";
        double coupon_value = resultsEntity.getCoupon_value();
        Intent intent = new Intent();
        intent.putExtra("coupon_id", coupon_id);
        intent.putExtra("coupon_price", coupon_value);
        setResult(RESULT_OK, intent);
        finish();
    }
}
