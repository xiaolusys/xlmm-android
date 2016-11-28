package com.jimei.xiaolumeimei.ui.fragment.user;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.CouponListAdapter;
import com.jimei.xiaolumeimei.base.BaseLazyFragment;
import com.jimei.xiaolumeimei.entities.CouponEntity;

import java.util.ArrayList;

import butterknife.Bind;

public class SelectCouponFragment extends BaseLazyFragment implements AdapterView.OnItemClickListener, View.OnClickListener {
    private static final String TYPE = "type";

    @Bind(R.id.lv)
    ListView listView;
    @Bind(R.id.empty_ll)
    View emptyView;
    ArrayList<CouponEntity> couponEntities;
    @Bind(R.id.btn)
    Button button;

    public static SelectCouponFragment newInstance(int type, String title, String id, ArrayList<CouponEntity> list) {
        SelectCouponFragment fragment = new SelectCouponFragment();
        Bundle args = new Bundle();
        args.putInt(TYPE, type);
        args.putString("id", id);
        args.putString("title", title);
        args.putSerializable("list", list);
        args.putInt("size", list.size());
        fragment.setArguments(args);
        return fragment;
    }

    public static SelectCouponFragment newInstance(int type, String title, String id, ArrayList<CouponEntity> list, int goodNum) {
        SelectCouponFragment fragment = new SelectCouponFragment();
        Bundle args = new Bundle();
        args.putInt(TYPE, type);
        args.putString("id", id);
        args.putString("title", title);
        args.putSerializable("list", list);
        args.putInt("goodNum", goodNum);
        args.putInt("size", list.size());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initData() {
        if (getArguments() != null) {
            int type = getArguments().getInt(TYPE);
            String selectId = getArguments().getString("id", "");
            couponEntities = new ArrayList<>();
            couponEntities.addAll((ArrayList<CouponEntity>) getArguments().getSerializable("list"));
            CouponListAdapter adapter = new CouponListAdapter(getContext());
            adapter.setClickable(false);
            listView.setAdapter(adapter);
            adapter.update(couponEntities, 0, selectId);
            if (couponEntities.size() > 0) {
                emptyView.setVisibility(View.GONE);
            } else {
                emptyView.setVisibility(View.VISIBLE);
            }
            if (type == 0) {
                listView.setOnItemClickListener(this);
                button.setVisibility(View.VISIBLE);
            } else {
                button.setVisibility(View.GONE);
            }
            button.setOnClickListener(this);
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

    public String getTitle() {
        String title = "";
        if (getArguments() != null) {
            String str = getArguments().getString("title");
            int size = getArguments().getInt("size");
            title = str + "(" + size + ")";
        }
        return title;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CouponEntity resultsEntity = couponEntities.get(position);
        String coupon_id = resultsEntity.getId() + "";
        double coupon_value = resultsEntity.getCoupon_value();
        Intent intent = new Intent();
        if (getArguments() != null) {
            int goodNum = getArguments().getInt("goodNum", 1);
            if (goodNum > 1 && couponEntities.size() >= goodNum) {
                StringBuilder sb = new StringBuilder();
                sb.append("").append(couponEntities.get(0).getId());
                for (int i = 1; i < goodNum; i++) {
                    sb.append("/").append(couponEntities.get(i).getId());
                }
                coupon_id = sb.toString();
                coupon_value = goodNum * resultsEntity.getCoupon_value();
                intent.putExtra("coupon_num", goodNum);
            }
        }
        intent.putExtra("coupon_id", coupon_id);
        intent.putExtra("coupon_price", coupon_value);
        getActivity().setResult(Activity.RESULT_OK, intent);
        getActivity().finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                Intent intent = new Intent();
                intent.putExtra("coupon_id", "");
                intent.putExtra("coupon_price", (double) 0);
                getActivity().setResult(Activity.RESULT_OK, intent);
                getActivity().finish();
                break;
        }
    }
}
