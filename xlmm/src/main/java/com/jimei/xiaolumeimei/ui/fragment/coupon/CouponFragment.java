package com.jimei.xiaolumeimei.ui.fragment.coupon;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.CouponListAdapter;
import com.jimei.xiaolumeimei.base.BaseLazyFragment;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.entities.CouponEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class CouponFragment extends BaseLazyFragment {

    private static final String TYPE = "type";

    @Bind(R.id.lv)
    ListView listView;
    private int type;
    private CouponListAdapter adapter;
    @Bind(R.id.empty_ll)
    View emptyView;
    private List<CouponEntity> couponEntities;
    @Bind(R.id.msg)
    TextView msgTv;

    public static CouponFragment newInstance(int type, ArrayList<CouponEntity> couponEntities) {
        CouponFragment fragment = new CouponFragment();
        Bundle args = new Bundle();
        args.putInt(TYPE, type);
        args.putSerializable("entity", couponEntities);
        fragment.setArguments(args);
        return fragment;
    }

    private void loadMoreData() {
        if (type == XlmmConst.UNUSED_COUPON) {
            msgTv.setText("您暂时没有未使用优惠券哦～");
        } else if (type == XlmmConst.PAST_COUPON) {
            msgTv.setText("您暂时没有已过期优惠券哦～");
        } else if (type == XlmmConst.USED_COUPON) {
            msgTv.setText("您暂时没有已使用优惠券哦～");
        } else if (type == XlmmConst.GOOD_COUPON) {
            msgTv.setText("您暂时没有精品优惠券哦～");
        }
        if (couponEntities.size() > 0) {
            emptyView.setVisibility(View.GONE);
            adapter.update(couponEntities, type, "");
        } else {
            emptyView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void initData() {
        if (getArguments() != null) {
            type = getArguments().getInt(TYPE);
            couponEntities = ((List<CouponEntity>) getArguments().getSerializable("entity"));
            adapter = new CouponListAdapter(getContext());
            listView.setAdapter(adapter);
            loadMoreData();
        }
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_coupon;
    }

    @Override
    public View getScrollableView() {
        return listView;
    }
}
