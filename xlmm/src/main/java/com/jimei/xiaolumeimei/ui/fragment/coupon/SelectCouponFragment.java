package com.jimei.xiaolumeimei.ui.fragment.coupon;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.CouponListAdapter;
import com.jimei.xiaolumeimei.base.BaseFragment;
import com.jimei.xiaolumeimei.entities.CouponEntity;
import com.jimei.xiaolumeimei.model.UserModel;
import com.jimei.xiaolumeimei.ui.activity.user.SelectCouponActivity.SelectCouponAdapter;
import com.jimei.xiaolumeimei.widget.loadingdialog.XlmmLoadingDialog;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;

import java.util.ArrayList;

import rx.schedulers.Schedulers;

public class SelectCouponFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    private static final String TYPE = "type";

    private ListView listView;
    private int type;
    private CouponListAdapter adapter;
    private View emptyView;
    private XlmmLoadingDialog loadingdialog;
    private int num = 0;
    private SelectCouponAdapter mAdapter;
    private String selectId;
    private Double money;
    private ArrayList<CouponEntity> list;
    private double price;

    public SelectCouponFragment() {
    }

    public static SelectCouponFragment newInstance(int type, String title, SelectCouponAdapter mAdapter, String id, Double money, double coupon_price) {
        SelectCouponFragment fragment = new SelectCouponFragment();
        Bundle args = new Bundle();
        args.putInt(TYPE, type);
        args.putSerializable("adapter", mAdapter);
        args.putString("title", title);
        args.putString("id", id);
        args.putDouble("money", money);
        args.putDouble("price", coupon_price);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showIndeterminateProgressDialog(false);
        if (getArguments() != null) {
            type = getArguments().getInt(TYPE);
            mAdapter = (SelectCouponAdapter) getArguments().getSerializable("adapter");
            selectId = getArguments().getString("id", "");
            money = getArguments().getDouble("money");
            price = getArguments().getDouble("price");
            adapter = new CouponListAdapter(getContext());
            listView.setAdapter(adapter);
            loadMoreData();
            if (type == 0) {
                listView.setOnItemClickListener(this);
            }
        }
    }

    private void loadMoreData() {
        UserModel.getInstance()
                .getCouponList(0)
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<ArrayList<CouponEntity>>() {
                    @Override
                    public void onNext(ArrayList<CouponEntity> couponEntities) {
                        if (couponEntities.size() > 0) {
                            for (CouponEntity couponEntity : couponEntities) {
                                if (type == 0) {
                                    if (couponEntity.getUse_fee() <= (money + price)) {
                                        list.add(couponEntity);
                                    }
                                } else if (type == 1) {
                                    if (couponEntity.getUse_fee() > (money + price)) {
                                        list.add(couponEntity);
                                    }
                                }
                            }
                            num = list.size();
                            emptyView.setVisibility(View.GONE);
                            adapter.update(list, 0, selectId);
                            mAdapter.notifyDataSetChanged();
                        }
                        if (num <= 0) {
                            emptyView.setVisibility(View.VISIBLE);
                        }
                        hideIndeterminateProgressDialog();
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coupon, container, false);
        listView = ((ListView) view.findViewById(R.id.lv));
        emptyView = view.findViewById(R.id.empty_ll);
        list = new ArrayList<>();
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

    public void showIndeterminateProgressDialog(boolean horizontal) {
        loadingdialog = XlmmLoadingDialog.create(activity)
                .setStyle(XlmmLoadingDialog.Style.SPIN_INDETERMINATE)
                .setCancellable(!horizontal)
                .show();
    }

    public void hideIndeterminateProgressDialog() {
        try {
            loadingdialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getTitle() {
        String title = "";
        if (getArguments() != null) {
            title = getArguments().getString("title") + "(" + num + ")";
        }
        return title;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CouponEntity resultsEntity = list.get(position);
        String coupon_id = resultsEntity.getId() + "";
        double coupon_value = resultsEntity.getCoupon_value();
        Intent intent = new Intent();
        intent.putExtra("coupon_id", coupon_id);
        intent.putExtra("coupon_price", coupon_value);
        getActivity().setResult(getActivity().RESULT_OK, intent);
        getActivity().finish();
    }
}
