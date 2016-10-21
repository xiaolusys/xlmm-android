package com.jimei.xiaolumeimei.ui.fragment.coupon;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.CouponListAdapter;
import com.jimei.xiaolumeimei.base.BaseFragment;
import com.jimei.xiaolumeimei.entities.CouponEntity;

import java.util.ArrayList;

public class SelectCouponFragment extends BaseFragment implements AdapterView.OnItemClickListener, View.OnClickListener {
    private static final String TYPE = "type";

    private ListView listView;
    private View emptyView;
    ArrayList<CouponEntity> couponEntities;
    private Button button;

    public SelectCouponFragment() {
    }

    public static SelectCouponFragment newInstance(int type, String title, String id, ArrayList<CouponEntity> list) {
        SelectCouponFragment fragment = new SelectCouponFragment();
        Bundle args = new Bundle();
        args.putInt(TYPE, type);
        args.putString("id", id);
        args.putString("title", title);
        args.putSerializable("list", list);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            int type = getArguments().getInt(TYPE);
            String selectId = getArguments().getString("id", "");
            couponEntities = new ArrayList<>();
            couponEntities.addAll((ArrayList<CouponEntity>) getArguments().getSerializable("list"));
            CouponListAdapter adapter = new CouponListAdapter(getContext());
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coupon, container, false);
        listView = ((ListView) view.findViewById(R.id.lv));
        emptyView = view.findViewById(R.id.empty_ll);
        button = ((Button) view.findViewById(R.id.btn));
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

    @Override
    public String getTitle() {
        String title;
        title = getArguments().getString("title") + "(" + couponEntities.size() + ")";
        return title;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CouponEntity resultsEntity = couponEntities.get(position);
        String coupon_id = resultsEntity.getId() + "";
        double coupon_value = resultsEntity.getCoupon_value();
        Intent intent = new Intent();
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
