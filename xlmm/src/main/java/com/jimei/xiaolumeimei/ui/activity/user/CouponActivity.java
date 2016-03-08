package com.jimei.xiaolumeimei.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class CouponActivity extends BaseSwipeBackCompatActivity
    implements View.OnClickListener {
  String TAG = "CouponActivity";

  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.btn_jump) Button btn_jump;
  @Bind(R.id.rlayout_order_empty) RelativeLayout rl_empty;
  @Bind(R.id.sv_frame_coupon) ScrollView sv_frame_coupon;
  int unused_num = 0;
  List<CouponBean.ResultsEntity> list = new ArrayList<>();
  private CouponListAdapter mCouponAdapter;
  private CouponListAdapter mPastCouponAdapter;
  private ListView lv_unused_coupon;

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

  @Override protected void setListener() {
    btn_jump.setOnClickListener(this);
    toolbar.setOnClickListener(this);
  }

  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.activity_coupon;
  }

  @Override protected void initViews() {
    toolbar.setTitle("");
    setSupportActionBar(toolbar);
    finishBack(toolbar);

    lv_unused_coupon = (ListView) findViewById(R.id.lv_unused_coupon);

    mCouponAdapter = new CouponListAdapter(this);
    lv_unused_coupon.setAdapter(mCouponAdapter);

    ListView lv_timeout_coupon = (ListView) findViewById(R.id.lv_timeout_coupon);
    mPastCouponAdapter = new CouponListAdapter(this);
    lv_timeout_coupon.setAdapter(mPastCouponAdapter);

    TextView tx_info = (TextView) findViewById(R.id.tx_info);
    tx_info.setText("亲，您暂时还没有优惠券哦~快去看看吧！");
  }

  //从server端获得所有订单数据，可能要查询几次
  @Override protected void initData() {
    UserModel.getInstance().getUnusedCouponBean()
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<CouponBean>() {
          @Override public void onNext(CouponBean couponBean) {
            List<CouponBean.ResultsEntity> results = couponBean.getResults();
            list.addAll(results);
            unused_num = results.size();
            if (0 != results.size()) {
              rl_empty.setVisibility(View.INVISIBLE);
              mCouponAdapter.update(results, XlmmConst.UNUSED_COUPON);
            }

            UserModel.getInstance().getPastCouponBean()
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<CouponBean>() {
                  @Override public void onNext(CouponBean couponBean) {
                    List<CouponBean.ResultsEntity> results = couponBean.getResults();
                    if (0 == results.size()) {
                      if (0 == unused_num) {
                        sv_frame_coupon.setVisibility(View.INVISIBLE);
                        rl_empty.setVisibility(View.VISIBLE);
                      }
                    } else {
                      rl_empty.setVisibility(View.INVISIBLE);
                      mPastCouponAdapter.update(results, XlmmConst.PAST_COUPON);
                    }

                    Log.i(TAG, "过期的" + couponBean.toString());
                  }
                });
          }
        });
  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }

  @Override public void onClick(View v) {
    switch (v.getId()) {
      case R.id.btn_jump:
        Intent intent = new Intent(CouponActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        break;
    }
  }
}
