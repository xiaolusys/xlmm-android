package com.jimei.xiaolumeimei.ui.fragment.coupon;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.CouponListAdapter;
import com.jimei.xiaolumeimei.base.BaseFragment;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.entities.CouponEntity;

import java.util.ArrayList;
import java.util.List;

public class CouponFragment extends BaseFragment {

    private static final String TYPE = "type";

    private ListView listView;
    private int type;
    private CouponListAdapter adapter;
    private View emptyView;
    private List<CouponEntity> couponEntities;
    private TextView msgTv;

    public static CouponFragment newInstance(int type, ArrayList<CouponEntity> couponEntities) {
        CouponFragment fragment = new CouponFragment();
        Bundle args = new Bundle();
        args.putInt(TYPE, type);
        args.putSerializable("entity", couponEntities);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getInt(TYPE);
            couponEntities = ((List<CouponEntity>) getArguments().getSerializable("entity"));
            adapter = new CouponListAdapter(getContext());
            listView.setAdapter(adapter);
            loadMoreData();
        }
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coupon, container, false);
        listView = ((ListView) view.findViewById(R.id.lv));
        emptyView = view.findViewById(R.id.empty_ll);
        msgTv = ((TextView) view.findViewById(R.id.msg));
        return view;
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return null;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setDefaultFragmentTitle(String title) {

    }


    @Override
    public View getScrollableView() {
        return listView;
    }
}
