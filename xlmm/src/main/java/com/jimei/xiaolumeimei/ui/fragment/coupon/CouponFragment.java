package com.jimei.xiaolumeimei.ui.fragment.coupon;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.CouponListAdapter;
import com.jimei.xiaolumeimei.base.BaseFragment;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.entities.CouponEntity;
import com.jimei.xiaolumeimei.model.UserModel;
import com.jimei.xiaolumeimei.ui.activity.user.AllCouponActivity.MainTabAdapter;
import com.jimei.xiaolumeimei.widget.loadingdialog.XlmmLoadingDialog;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;

import java.util.ArrayList;

import rx.schedulers.Schedulers;

public class CouponFragment extends BaseFragment {

    private static final String TYPE = "type";

    private ListView listView;
    private int type;
    private CouponListAdapter adapter;
    private View emptyView;
    private int status = 0;
    private XlmmLoadingDialog loadingdialog;
    private int num;
    private MainTabAdapter mAdapter;

    public static CouponFragment newInstance(int type, String title) {
        CouponFragment fragment = new CouponFragment();
        Bundle args = new Bundle();
        args.putInt(TYPE, type);
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showIndeterminateProgressDialog(false);
        if (getArguments() != null) {
            type = getArguments().getInt(TYPE);
            if (type == XlmmConst.UNUSED_COUPON) {
                status = 0;
            } else if (type == XlmmConst.PAST_COUPON) {
                status = 3;
            } else if (type == XlmmConst.USED_COUPON) {
                status = 1;
            }
            adapter = new CouponListAdapter(getContext());
            listView.setAdapter(adapter);
            loadMoreData();
            //mAdapter = (MainTabAdapter) getArguments().getSerializable("adapter");
        }
    }

    private void loadMoreData() {
        UserModel.getInstance()
                .getCouponList(status)
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<ArrayList<CouponEntity>>() {
                    @Override
                    public void onNext(ArrayList<CouponEntity> couponEntities) {
                        if (couponEntities.size() > 0) {
                            num = couponEntities.size();
                            emptyView.setVisibility(View.GONE);
                            adapter.update(couponEntities, type, "");
                            //mAdapter.notifyDataSetChanged();
                        } else {
                            num = 0;
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
}
